package interfaces;

/**
 * Provides generic interface for statecharts events handlers
 * EntityType is entity type
 * ContextType is call context type
 */
public interface IHandler<EntityType, ContextType> {

    /**
     * This method is called when statechart event raises
     *
     * @param entity  entity
     * @param context call context
     */
    void execute(EntityType entity, ContextType context);
}