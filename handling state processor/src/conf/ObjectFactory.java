package conf;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public HConfData createHConfData() {
        return new HConfData();
    }

    public EntityWorkflowData createEntityWorkflowData() {
        return new EntityWorkflowData();
    }

    public EntityWorkflowDatas createEntityWorkflowDatas() {
        return new EntityWorkflowDatas();
    }

}
