package forms.config;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import helper.Lang;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PapagoTranslatorConfigGUI {

    private static final String TRANSLATABLE_INFO_URL = "https://developers.naver.com/docs/nmt/reference/#3--%EC%9A%94%EC%B2%AD-%EB%B3%80%EC%88%98";

    private JPanel rootPanel;
    private JTextField clientId;
    private JTextField clientSecret;
    private JComboBox from;
    private JComboBox to;
    private JLabel link;

    private PapagoTranslatorConfig config;

    public PapagoTranslatorConfigGUI() {
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                BrowserUtil.browse(TRANSLATABLE_INFO_URL);
                super.mouseReleased(e);
            }
        });
    }

    public void init(Project project){
        config = PapagoTranslatorConfig.getInstance(project);
        clientId.setText(config.getClientId());
        clientSecret.setText(config.getClientSecret());
        from.setSelectedIndex(config.getFromIndex());
        to.setSelectedIndex(config.getToIndex());

        if (from.getSelectedIndex() == 0 && to.getSelectedIndex() == 0) {
            from.setSelectedIndex(Lang.KO.getIdx());
            to.setSelectedIndex(Lang.EN.getIdx());
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public boolean isModified() {
        return ( !clientId.getText().equals(config.getClientId())
                || !clientSecret.getText().equals(config.getClientSecret())
                || from.getSelectedIndex() != config.getFromIndex()
                || to.getSelectedIndex() != config.getToIndex());
    }

    public void apply() throws ConfigurationException {
        checkValidation();

        config.setClientId(clientId.getText());
        config.setClientSecret(clientSecret.getText());
        config.setFromIndex(from.getSelectedIndex());
        config.setToIndex(to.getSelectedIndex());
    }

    public void checkValidation() throws ConfigurationException {
        if (clientId.getText().trim().equals("")) throw new ConfigurationException("Enter clientId");
        if (clientSecret.getText().trim().equals("")) throw new ConfigurationException("Enter clientSecret");
        if (from.getSelectedIndex() == to.getSelectedIndex())   throw new ConfigurationException("Source and target languages must be different.");

        Lang fromLang = Lang.getInstance(from.getSelectedIndex());
        Lang toLang = Lang.getInstance(to.getSelectedIndex());

        if ( !fromLang.isTranslatable(toLang) ) {
            String msg = String.format("Translation from %s to %s is not supported.", fromLang.getLanguage(), toLang.getLanguage());
            throw new ConfigurationException(msg);
        }
    }

}
