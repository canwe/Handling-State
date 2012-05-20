package interfaces;

/**
 * Provides interface of securable UI view
 */
public interface ISecurable {

    /**
     * Accepts defined permissions to all UI elements
     *
     * @param provider IPermissionProvider provides
     *                 UI elements permissions for current user
     */
    void acceptPermissions(IPermissionProvider provider);
}
