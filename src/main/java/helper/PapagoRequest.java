package helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import forms.AlertGUI;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PapagoRequest {

    private final String SOURCE = "ko";
    private final String TARGET = "en";
    private final String URL = "https://openapi.naver.com/v1/papago/n2mt";
    private final String CLIENT_ID_KEY = "X-Naver-Client-Id";
    private final String SECRET_KEY = "X-Naver-Client-Secret";

    private String clientId;
    private String secret;

    private ObjectMapper om = new ObjectMapper();

    public PapagoRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.secret = clientSecret;
    }

    public PapagoResponse request(String text)throws IOException {

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(URL);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.addHeader(CLIENT_ID_KEY, clientId);
        post.addHeader(SECRET_KEY, secret);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("SOURCE", SOURCE));
        params.add(new BasicNameValuePair("TARGET", TARGET));
        params.add(new BasicNameValuePair("text", text));

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            String msg = String.format("Error: status code is %d.", response.getStatusLine().getStatusCode());

            AlertGUI alert = new AlertGUI();
            alert.setText(msg);
            alert.pack();
            Utils.setCenter(alert);
            alert.setVisible(true);
            return null;

        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        PapagoResponse res = om.readValue(result.toString(), PapagoResponse.class);

        return res;

    }

    public String getTranslateText(String text) {
        String translatedText = "";
        try {
            PapagoResponse response = request(text);

            if (response != null) {
                translatedText = response.getMessage().getResult().getTranslatedText();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return translatedText;
    }




}
