package interfaces;

/**
 * Specified generic interface for user roles provider
 */
public interface IRoleProvider<EntityType, ContextType> {
    /**
     * Gets a value indicating whether the current user is
     * in the specified role in context of entity instance.
     *
     * @param role    role name
     * @param entity  entity
     * @param context call context object</param>
     * @return true if the user is in the specified role;
     *         false if the user is not in the specified role or is not authenticated.
     */
    public Boolean isInRole(String role, Class<EntityType> entity, Class<ContextType> context);
}