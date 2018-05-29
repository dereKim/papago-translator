package forms.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PapagoTranslatorConfiguable implements SearchableConfigurable {

    private PapagoTranslatorConfigGUI gui;
    private Project project;

    public PapagoTranslatorConfiguable(@NotNull Project project) {
        this.project = project;
    }


    @Nullable
    @Override
    public JComponent createComponent() {
        gui = new PapagoTranslatorConfigGUI();
        gui.init(project);
        return gui.getRootPanel();
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Papago Translator";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "forms.config.PapagoTranslatorConfiguable";
    }

    @NotNull
    @Override
    public String getId() {
        return "forms.config.PapagoTranslatorConfiguable";
    }


    @Override
    public void disposeUIResources() {
        gui = null;
    }

    @Override
    public boolean isModified() {
        return gui.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        gui.apply();
    }
}
