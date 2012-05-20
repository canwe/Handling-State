package core;/// <summary>
/// Default implementation of  IWorkflowFactory interface
/// </summary>
/// <typeparam name="EntityType">entity type</typeparam>

import interfaces.IWorkflowFactory;
import interfaces.IWorkflowAdapter;
import exceptions.WorkflowException;

public class ReflectionFactory<EntityType> implements IWorkflowFactory<EntityType> {

    /// <summary>
    /// Creates IWorkflowAdapter for given entity
    /// </summary>
    /// <param name="entity">entity</param>
    /// <param name="stateMap">entity attribute name (field or property) which stores entities state</param>
    /// <returns>IWorkflowAdapter foe given entity</returns>
    public IWorkflowAdapter<EntityType> getWorkflow(EntityType entity,
                                                    String stateMap)
            throws WorkflowException {
        return new ReflectionEntityWorkflow<EntityType>(entity, stateMap);
    }

    /// <summary>
    /// Provides generic factory interface for IWorkflowAdapter
    /// </summary>
    /// <param name="entity">entity</param>
    /// <param name="stateMap">entity attribute name (field or property) which stores entities state</param>
    /// <param name="statechartMap">entity attribute name (field or property) which stores entities stateshart Id</param>
    /// <returns>IWorkflowAdapter foe given entity</returns>
    public IWorkflowAdapter<EntityType> getWorkflow(EntityType entity,
                                                    String stateMap,
                                                    String statechartMap)
            throws WorkflowException {
        return new ReflectionEntityWorkflow<EntityType>(entity, stateMap, statechartMap);
    }


}