package interfaces;

import exceptions.WorkflowException;

/**
 * Provides generic factory interface for IWorkflowAdapter
 *
 * @typeparam EntityType entity type
 */
public interface IWorkflowFactory<EntityType> {
    /**
     * Creates IWorkflowAdapter for given entity
     *
     * @param entity   entity
     * @param stateMap entity attribute name (field or property) which stores entities state
     * @return IWorkflowAdapter for given entity
     */
    IWorkflowAdapter<EntityType> getWorkflow(EntityType entity,
                                             String stateMap) throws WorkflowException;

    /**
     * Provides generic factory interface for IWorkflowAdapter
     * This method is invoked by framework when StatechartMap attribute is defined
     * in the entity workflow configuration. StatechartMap provides statechart versioning support for
     * different instances of the entity.
     *
     * @param entity        entity
     * @param stateMap      entity attribute name (field or property) which stores entities state
     * @param statechartMap entity attribute name (field or property) which stores entities stateshart Id
     * @return IWorkflowAdapter for given entity
     */
    IWorkflowAdapter<EntityType> getWorkflow(EntityType entity,
                                             String stateMap,
                                             String statechartMap) throws WorkflowException;
}
