package forms.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

@State( name="PapagoTranslatorConfig", storages = {@Storage("PapagoTranslatorConfig.xml")})
public class PapagoTranslatorConfig implements PersistentStateComponent<PapagoTranslatorConfig> {

    private String clientId = "";
    private String clientSecret = "";
    private int fromIndex;
    private int toIndex;

    @Override
    public PapagoTranslatorConfig getState() {
        return this;
    }

    public static PapagoTranslatorConfig getInstance(Project project){
        return ServiceManager.getService(project, PapagoTranslatorConfig.class);
    }

    @Override
    public void loadState(PapagoTranslatorConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }
}
