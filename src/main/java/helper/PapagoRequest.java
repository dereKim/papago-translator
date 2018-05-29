package helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PapagoRequest {

    private static final String URL = "https://openapi.naver.com/v1/papago/n2mt";
    private static final String CLIENT_ID_KEY = "X-Naver-Client-Id";
    private static final String SECRET_KEY = "X-Naver-Client-Secret";

    private String clientId;
    private String secret;
    private Lang fromLang;
    private Lang toLang;
    private ObjectMapper om = new ObjectMapper();

    public PapagoRequest(String clientId, String clientSecret, Lang fromLang, Lang toLang) {
        this.clientId = clientId;
        this.secret = clientSecret;
        this.fromLang = fromLang;
        this.toLang = toLang;
    }

    public String getTranslatedText(String text) {
        String translatedText = "";
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            headers.put(CLIENT_ID_KEY, clientId);
            headers.put(SECRET_KEY, secret);

            Map<String, String> params = new HashMap<>();
            params.put("source", fromLang.getCode());
            params.put("target", toLang.getCode());
            params.put("text", text);

            String result = Utils.Http.post(URL, headers, params);
            PapagoResponse response = om.readValue(result, PapagoResponse.class);

            if (response != null) {
                translatedText = response.getMessage().getResult().getTranslatedText();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return translatedText;
    }







}
