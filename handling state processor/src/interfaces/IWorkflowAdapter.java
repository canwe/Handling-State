package interfaces;

/**
 * Provides adapter interface for interaction entity
 * with Handling State Machine framework
 *
 * @typeparam T entity type
 */
public interface IWorkflowAdapter<T> {
    /**
     * Gets entity type string
     *
     * @return String
     */
    public String getEntityType();

    /**
     * Gets current entity state
     *
     * @return String
     */
    public String getCurrentState();

    /**
     * Gets statechart Id
     *
     * @return String
     */
    public String getStateChartId();

    /**
     * Sets entity type
     *
     * @param entityType String
     */
    public void setEntityType(String entityType);

    /**
     * Sets current entity state
     *
     * @param newState String
     */
    public void setCurrentState(String newState);

    /**
     * Sets statechart Id
     *
     * @param stateChartId String
     */
    public void setStateChartId(String stateChartId);

    /**
     * Gets wrapped entity
     *
     * @return Entity
     */
    T getEntity();
}
