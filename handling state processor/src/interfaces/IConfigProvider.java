package interfaces;

import conf.EntityWorkflow;
import enums.UIPermissionLevel;
import exceptions.WorkflowException;
import model.xml.StateChart;

/**
 * Provides base interface for manage Listma framework configuration
 */
public interface IConfigProvider {
    /**
     * Sets directory for load statechart files
     *
     * @param statechartDir
     */
    void setStateChartDir(String statechartDir) throws WorkflowException;

    /// <summary>
    /// Registers workflow configuration for entity type
    /// </summary>
    /// <param name="entityWorkflow"></param>
    void registerEntityWorkflow(EntityWorkflow entityWorkflow) throws WorkflowException;

    /// <summary>
    /// Registers statechart in runtime
    /// </summary>
    /// <param name="statechart"></param>
    void registerStateChart(StateChart statechart) throws WorkflowException;

    /// <summary>
    /// Gets UIPermissionLevel for UI elements that aren't specified in the statechart
    /// </summary>
    UIPermissionLevel getDefaultPermissionLevel();

    /// <summary>
    /// Sets UIPermissionLevel for UI elements that aren't specified in the statechart
    /// </summary>
    void setDefaultPermissionLevel(UIPermissionLevel uiPermissionLevel) throws WorkflowException;

    /// <summary>
    /// Returns workflow configuration for entity type
    /// </summary>
    /// <param name="entityType">entity type string</param>
    /// <returns>entity workflow configuration</returns>
    EntityWorkflow getEntityWorkflow(String entityType) throws WorkflowException;

    /// <summary>
    /// Returns workflow adapter factory for entity type
    /// </summary>
    /// <typeparam name="EntityType">entity type </typeparam>
    /// <param name="entityType">entity type string</param>
    /// <returns>workflow adapter factory instance</returns>
    <EntityType> IWorkflowFactory<EntityType> getWorkflowFactory(String entityType, Class<EntityType> entityTypeClass) throws WorkflowException;

    /// <summary>
    /// Returns roles provider for entity type
    /// </summary>
    /// <typeparam name="EntityType">entity type</typeparam>
    /// <typeparam name="ContextType">call context type</typeparam>
    /// <param name="entityType">entity type string</param>
    /// <returns>roles provider instance</returns>
    <EntityType, ContextType> IRoleProvider<EntityType, ContextType> getRoleProvider(String entityType, Class<EntityType> entityTypeClass, Class<ContextType> contextTypeClass) throws WorkflowException;

    /// <summary>
    /// Returns statechart by Id
    /// </summary>
    /// <param name="statechartId">statechart Id</param>
    /// <returns>statechart</returns>
    StateChart getStateChart(String statechartId) throws WorkflowException;
}
