package conf;

import core.ReflectionFactory;
import core.RoleProvider;

import javax.xml.bind.annotation.*;

/**
 * Class for store entity workflow configuration data
 */
@XmlRootElement(name = "EntityWorkflowData")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "EntityWorkflowData") //, namespace = HConfData.xmlNamespace
public class EntityWorkflowData {
    /**
     * Entity type string
     */
    private String entityType = "";

    /// <summary>
    /// Workflow factory class
    /// </summary>
    private String workflowFactoryClass = ReflectionFactory.class.getName();

    /// <summary>
    /// Role provider class
    /// </summary>
    private String roleProviderClass = RoleProvider.class.getName();

    /// <summary>
    /// Statechart Id
    /// </summary>
    private String stateChartId = "";

    /// <summary>
    /// Initial state Id
    /// </summary>
    private String initialState = "*";

    /// <summary>
    /// State attribute mapping
    /// </summary>
    private String stateMap = "";

    /// <summary>
    /// Statechart attribute mapping
    /// </summary>
    private String stateChartMap = "";

    @XmlAttribute
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @XmlAttribute
    public String getWorkflowFactoryClass() {
        return workflowFactoryClass;
    }

    public void setWorkflowFactoryClass(String workflowFactoryClass) {
        this.workflowFactoryClass = workflowFactoryClass;
    }

    @XmlAttribute
    public String getRoleProviderClass() {
        return roleProviderClass;
    }

    public void setRoleProviderClass(String roleProviderClass) {
        this.roleProviderClass = roleProviderClass;
    }

    @XmlAttribute
    public String getStateChartId() {
        return stateChartId;
    }

    public void setStateChartId(String stateChartId) {
        this.stateChartId = stateChartId;
    }

    @XmlAttribute
    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    @XmlAttribute
    public String getStateMap() {
        return stateMap;
    }

    public void setStateMap(String stateMap) {
        this.stateMap = stateMap;
    }

    @XmlAttribute
    public String getStateChartMap() {
        return stateChartMap;
    }

    public void setStateChartMap(String stateChartMap) {
        this.stateChartMap = stateChartMap;
    }
}
