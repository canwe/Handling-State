package core;

import conf.EntityWorkflow;
import conf.HConfData;
import enums.UIPermissionLevel;
import exceptions.WorkflowException;
import interfaces.IConfigProvider;
import interfaces.IRoleProvider;
import interfaces.IWorkflowFactory;
import model.metadata.StateChartCache;
import model.xml.StateChart;
import utils.BaseDirectory;

/**
 * @author victor.konopelko
 *         Date: 30.01.12
 */
public class ConfigProvider implements IConfigProvider {

    static IConfigProvider _default;
    private static final Object syncRoot = new Object();
    
    private static final String DEFAULT_CONF_DIR = "conf";

    /// <summary>
    /// Returns singleton instance which loads configuration data from "listma.config" file in the AppDomain.BaseDirectory
    /// </summary>
    /// <returns>ConfigProvider singletone instance</returns>
    public static IConfigProvider GetDefault() throws WorkflowException {
        if (_default == null) {
            synchronized (syncRoot) {

                if (_default == null) _default = new ConfigProvider(HConfData.Load(BaseDirectory.get() + "\\" + DEFAULT_CONF_DIR + "\\HConf.xml"));
            }
        }
        return _default;
    }

    private final HConfData _config;
    private StateChartCache _statechartCache;

    private StateChartCache getStateChartCache() {
        if (_statechartCache == null) {
            _statechartCache = new StateChartCache(_config.stateChartDir);
        }
        return _statechartCache;
    }

    /// <summary>
    /// Default parameterless constructor. Creates empty configuration
    /// </summary>
    public ConfigProvider() {
        _config = new HConfData();
    }

    /// <summary>
    /// Creates instance and loads configuration data from given file
    /// </summary>
    /// <param name="configFileName">configuration file name</param>
    public ConfigProvider(String configFileName) throws WorkflowException {
        _config = HConfData.Load(configFileName);
    }

    public ConfigProvider(HConfData config) {
        _config = config;
    }

    /// <summary>
    /// Sets directory location for loading statechart xml files
    /// </summary>
    /// <param name="statechartDir">directory location (path)</param>
    public void setStateChartDir(String statechartDir) throws WorkflowException {
        if (_statechartCache != null) {
            throw new WorkflowException("Can not set StateChartDir because statechart cache has already been initialized.");
        }
        _statechartCache = new StateChartCache(statechartDir);
    }

    /// <summary>
    /// Returns entity workflow configuration for given entity type
    /// </summary>
    /// <param name="entityType">entity type string</param>
    /// <returns>entity workflow</returns>
    public EntityWorkflow getEntityWorkflow(String entityType) throws WorkflowException {
        return _config.getEntityWorkflow(entityType);
    }

    /// <summary>
    /// Returns IWorkflowFactory for given entity type
    /// </summary>
    /// <typeparam name="EntityType">entity type</typeparam>
    /// <param name="entityType">entity type string</param>
    /// <returns>IWorkflowFactory</returns>
    public <EntityType> IWorkflowFactory<EntityType> getWorkflowFactory(String entityType, Class<EntityType> entityTypeClass) throws WorkflowException {
        //return ReflectedTypeCache.<IWorkflowFactory<EntityType>>getInstance(_config.getEntityWorkflowFactoryName(entityType), entityTypeClass);
        System.out.println("_config.getEntityWorkflowFactoryName(entityType) = " + _config.getEntityWorkflowFactoryName(entityType));
        System.out.println("entityTypeClass = " + entityTypeClass);
        System.out.println("entityType = " + entityType);
        return (IWorkflowFactory<EntityType>)ReflectedTypeCache.getInstance(_config.getEntityWorkflowFactoryName(entityType), entityTypeClass);
    }

    /// <summary>
    /// Returns IRoleProvider for given entity type
    /// </summary>
    /// <typeparam name="EntityType">entity type</typeparam>
    /// <typeparam name="ContextType">call context type</typeparam>
    /// <param name="entityType">entity type string</param>
    /// <returns>IRoleProvider interface</returns>
    public <EntityType, ContextType> IRoleProvider<EntityType, ContextType> getRoleProvider(String entityType, Class<EntityType> entityTypeClass, Class<ContextType> contextTypeClass) throws WorkflowException {
        //return ReflectedTypeCache.<IRoleProvider<EntityType, ContextType>>getInstance(_config.GetRoleProviderName(entityType), entityTypeClass, contextTypeClass);
        return (IRoleProvider<EntityType, ContextType>)ReflectedTypeCache.getInstance(_config.GetRoleProviderName(entityType), entityTypeClass, contextTypeClass);
    }

    /// <summary>
    /// Returns statechart by its Id
    /// </summary>
    /// <param name="statechartId">statechart Id</param>
    /// <returns>statechart</returns>
    public StateChart getStateChart(String statechartId) throws WorkflowException {
        return getStateChartCache().getStateChart(statechartId);
    }


    /// <summary>
    /// Registers entity workflow in the configuration at runtime
    /// </summary>
    /// <param name="entityWorkflow">entity workflow</param>
    public void registerEntityWorkflow(EntityWorkflow entityWorkflow) throws WorkflowException {
        _config.addEntityWorkflow(entityWorkflow);
    }

    /// <summary>
    /// Gets or sets default UIPermissionLevel for UI elements that are not configured in statechart
    /// </summary>
    public UIPermissionLevel getDefaultPermissionLevel() {
        return _config.DefaultPermissionLevel;
    }

    public void setDefaultPermissionLevel(UIPermissionLevel value) {
        _config.DefaultPermissionLevel = value;
    }

    /// <summary>
    /// Registers statechart in configuration at Runtime
    /// </summary>
    /// <param name="statechart">statechart</param>
    public void registerStateChart(StateChart statechart) {
        getStateChartCache().addStatechart(statechart);
    }

}
