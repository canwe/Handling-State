package core;

import core.security.GenericPrincipal;
import core.security.Principals;
import interfaces.IRoleProvider;

/// <summary>
/// Default implementation of IRoleProvider interface
/// </summary>
/// <typeparam name="EntityType">entity type</typeparam>
/// <typeparam name="ContextType">call context type</typeparam>
public class RoleProvider<EntityType, ContextType> implements IRoleProvider<EntityType, ContextType> {
    /// <summary>
    /// Gets a value indicating whether the current user is in the specified role in context of entity instance.
    /// </summary>
    /// <param name="role">role name</param>
    /// <param name="entity">entity</param>
    /// <param name="context">call context object</param>
    /// <returns>true if the user is in the specified role; false if the user is not in the specified role or is not authenticated.</returns>
    public Boolean isInRole(String role, Class<EntityType> entity, Class<ContextType> context) {
        //TODO return System.Threading.Thread.CurrentPrincipal.IsInRole(role);
        GenericPrincipal currentPrincipal = Principals.getCurrentPrincipal();
        return currentPrincipal != null && currentPrincipal.isInRole(role);
    }

}