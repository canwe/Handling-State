package conf;

import interfaces.IWorkflowFactory;

import javax.xml.bind.annotation.XmlType;

/// <summary>
/// Entity workflow configuration class
/// </summary>
public class EntityWorkflow {
    /// <summary>
    /// Config data
    /// </summary>
    protected EntityWorkflowData _data;

    public EntityWorkflow(EntityWorkflowData data) {
        _data = data;
    }

    /// <summary>
    /// Initializes a new instance with entity type string, statechart Id and state attribute mapping
    /// </summary>
    /// <param name="entityType">The entity type string that associated entity with given workflow</param>
    /// <param name="statechartId">statechart Id</param>
    /// <param name="stateMap">entity state attribute mapping</param>
    public EntityWorkflow(String entityType,
                          String statechartId,
                          String stateMap) {
        _data = new EntityWorkflowData();
        _data.setEntityType(entityType);
        _data.setStateChartId(statechartId);
        _data.setStateMap(stateMap);
    }

    /// <summary>
    /// Sets stateshart Id mapping
    /// </summary>
    /// <param name="statechartMap">The name of entity attribute (field or property) that stores statechart Id</param>
    public void setStatechartMap(String stateChartMap) {
        if (null == stateChartMap || stateChartMap.isEmpty()) {
            throw new IllegalArgumentException("statechartMap");
        }
        _data.setStateChartMap(stateChartMap);
    }

    /// <summary>
    /// Registers type that implements IWorkflowFactory interface for this workflow at runtime
    /// </summary>
    /// <param name="wfType">The type that implements IWorkflowFactory</param>
    public <Type extends IWorkflowFactory> void registerWorkflowFactoryType(Type wfType) {
        if (wfType == null) {
            throw new IllegalArgumentException("WorkflowFactoryType");
        }
        _data.setWorkflowFactoryClass(wfType.getClass().getName());
    }

    /// <summary>
    /// Registers type that implements IRoleProvider interface for this workflow at runtime
    /// </summary>
    /// <param name="rpType">The type that implements IRoleProvider</param>
    public <Type extends IWorkflowFactory> void registerRoleProviderType(Type rpType) {
        if (rpType == null) {
            throw new IllegalArgumentException("RoleProviderType");
        }
        _data.setRoleProviderClass(rpType.getClass().getName());
    }

    /// <summary>
    /// Sets initial state
    /// </summary>
    /// <param name="stateId">state Id</param>
    public void setInitialState(String stateId) {
        if (null == stateId || stateId.isEmpty()) {
            throw new IllegalArgumentException("InitialState");
        }
        _data.setInitialState(getInitialState());
    }

    /// <summary>
    /// Gets entity type string
    /// </summary>
    public String getEntityType() {
        return _data.getEntityType();
    }

    /// <summary>
    /// Gets workflow factory class name
    /// </summary>
    public String getWorkflowFactoryClass() {
        return _data.getWorkflowFactoryClass();
    }

    /// <summary>
    /// Gets role provider class name
    /// </summary>
    public String getRoleProviderClass() {
        return _data.getRoleProviderClass();
    }

    /// <summary>
    /// Gets workflow statechart Id
    /// </summary>
    public String getStateChartId() {
        return _data.getStateChartId();
    }

    /// <summary>
    /// Gets initial state Id
    /// </summary>
    public String getInitialState() {
        return _data.getInitialState();
    }

    /// <summary>
    /// Gets entity state attribute name
    /// </summary>
    public String getStateMap() {
        return _data.getStateMap();
    }

    /// <summary>
    /// Gets entity attribute name that stores statechart Id
    /// </summary>
    public String getStateChartMap() {
        return _data.getStateChartMap();
    }

}
