package interfaces;

/// <summary>
/// Provides enhanced interface for statechart transition's handler
/// </summary>
/// <typeparam name="EntityType">entity type</typeparam>
/// <typeparam name="ContextType">call context type</typeparam>
public interface ITransitionHandler<EntityType, ContextType>
         extends IHandler<EntityType, ContextType> {
    /// <summary>
    /// Forces a exception at run time if the entity is in invalid state
    /// </summary>
    /// <param name="entity">entity</param>
    void preValidate(EntityType entity);

    /// <summary>
    /// Returns true if the entity is ready to move into the target state
    /// </summary>
    /// <param name="entity">entity </param>
    /// <param name="targetState">target state name</param>
    /// <returns>true if the entity is ready to move into the target state; false otherwise</returns>
    Boolean confirmStateChange(EntityType entity,
                               String targetState);
}
