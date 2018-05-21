package forms.config;

import com.intellij.openapi.project.Project;
import forms.config.PapagoTranslatorConfig;

import javax.swing.*;

public class PapagoTranslatorConfigGUI {
    private JPanel rootPanel;
    private JTextField clientId;
    private JTextField clientSecret;

    private PapagoTranslatorConfig config;

    public PapagoTranslatorConfigGUI(){}

    public void init(Project project){
        config = PapagoTranslatorConfig.getInstance(project);
        clientId.setText(config.getClientId());
        clientSecret.setText(config.getClientSecret());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public boolean isModified() {
        if ( !clientId.getText().equals(config.getClientId())
                || !clientSecret.getText().equals(config.getClientSecret()) )
        {
            return true;
        } else {
            return false;
        }
    }

    public void apply(){
        config.setClientId(clientId.getText());
        config.setClientSecret(clientSecret.getText());
    }




}
