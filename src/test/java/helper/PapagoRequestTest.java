package helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PapagoRequestTest {

    @Test
    public void jsonToObj(){
        String json = "{\"message\":{\"@type\":\"response\",\"@service\":\"naverservice.nmt.proxy\",\"@version\":\"1.0.0\",\"result\":{\"srcLangType\":\"en\",\"tarLangType\":\"ko\",\"translatedText\":\"메세지\"}}}";

        ObjectMapper om = new ObjectMapper();

        try {
            PapagoResponse res = om.readValue(json, PapagoResponse.class);

            assertTrue(!res.getMessage().getResult().getTranslatedText().equals(""));
        } catch (Exception ex) {
            ex.printStackTrace();

        }


    }
}
