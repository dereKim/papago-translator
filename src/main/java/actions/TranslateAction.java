package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import forms.PreviewGUI;
import forms.config.PapagoTranslatorConfig;
import helper.PapagoRequest;

public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = CommonDataKeys.EDITOR.getData(event.getDataContext());
        if ( editor == null ) return;

        String text = editor.getSelectionModel().getSelectedText();

        if (text == null || text.equals(""))    return;

        PapagoTranslatorConfig config = PapagoTranslatorConfig.getInstance(event.getRequiredData(CommonDataKeys.PROJECT));

        String clientId = config.getClientId();
        String clientSecret = config.getClientSecret();

        PapagoRequest request = new PapagoRequest(clientId, clientSecret);
        String translatedText = request.getTranslatedText(text);

        if (translatedText == null || translatedText.equals("")) return;

        PreviewGUI middleForm = new PreviewGUI(event);
        middleForm.replaceSelectedText(translatedText);
    }
}


