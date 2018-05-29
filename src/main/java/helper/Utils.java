package helper;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import forms.AlertGUI;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static class UI{

        public static void setCenter(Window widow) {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            widow.setLocation(dim.width / 2 - widow.getSize().width / 2, dim.height / 2 - widow.getSize().height / 2);
        }

        public static void closeFrame(JPanel panel) {
            ((Window) panel.getRootPane().getParent()).dispose();
        }

        public static void alert(String msg) {
            AlertGUI alert = new AlertGUI();
            alert.setText(msg);
            alert.pack();
            setCenter(alert);
            alert.setVisible(true);
        }

        public static void replaceSelectedText(Editor editor, String text) {
            final Document document = editor.getDocument();
            final SelectionModel selectionModel = editor.getSelectionModel();

            final int start = selectionModel.getSelectionStart();
            final int end = selectionModel.getSelectionEnd();
            WriteCommandAction.runWriteCommandAction(editor.getProject(), () ->
                    document.replaceString(start, end, text)
            );
            selectionModel.removeSelection();
        }

    }

    public static class Http{

        public static String post(String url, Map<String, String> headers, Map<String, String> params)
                throws IOException {
            HttpClient client = HttpClientBuilder.create().build();

            HttpPost post = new HttpPost(url);
            headers.forEach((k, v) -> post.addHeader(k, v));

            List<NameValuePair> parameters = new ArrayList<>();
            params.forEach((k, v) -> parameters.add(new BasicNameValuePair(k, v)));

            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                String msg = String.format("Error: status code %d.", response.getStatusLine().getStatusCode());
                Utils.UI.alert(msg);
            }

            StringBuilder result = new StringBuilder();
            InputStreamReader is = new InputStreamReader(response.getEntity().getContent());

            try (BufferedReader rd = new BufferedReader(is)) {
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            }

            return result.toString();
        }
    }

    public static class Lang{
        public static String getLangCode(int idx) {
            switch (idx){
                case 1: return "ko";
                case 2: return "en";
                case 3: return "zh-CN";
                case 4: return "zh-TW";
                case 5: return "es";
                case 6: return "fr";
                case 7: return "vi";
                case 8: return "th";
                case 9: return "id";
            }

            return "";
        }
    }


}
