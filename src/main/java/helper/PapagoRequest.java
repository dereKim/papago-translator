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

    private static final String SOURCE = "ko";
    private static final String TARGET = "en";
    private static final String URL = "https://openapi.naver.com/v1/papago/n2mt";
    private static final String CLIENT_ID_KEY = "X-Naver-Client-Id";
    private static final String SECRET_KEY = "X-Naver-Client-Secret";

    private String clientId;
    private String secret;
    private ObjectMapper om = new ObjectMapper();

    public PapagoRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.secret = clientSecret;
    }

    private PapagoResponse request(String text)throws IOException {

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(URL);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.addHeader(CLIENT_ID_KEY, clientId);
        post.addHeader(SECRET_KEY, secret);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("source", SOURCE));
        params.add(new BasicNameValuePair("target", TARGET));
        params.add(new BasicNameValuePair("text", text));

        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
// Potato
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            String msg = String.format("Error: status code is %d.", response.getStatusLine().getStatusCode());

            System.out.println(response.getStatusLine().getReasonPhrase());

            AlertGUI alert = new AlertGUI();
            alert.setText(msg);
            alert.pack();
            Utils.setCenter(alert);
            alert.setVisible(true);
            return null;

        }

        StringBuilder result = new StringBuilder();
        InputStreamReader is = new InputStreamReader(response.getEntity().getContent());

        try (BufferedReader rd = new BufferedReader(is)) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }

        return om.readValue(result.toString(), PapagoResponse.class);

    }

    public String getTranslatedText(String text) {
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
