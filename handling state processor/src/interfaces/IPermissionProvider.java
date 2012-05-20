package interfaces;

import enums.UIPermissionLevel;

/**
 * Provides interface for UI permissions provider
 */
public interface IPermissionProvider {
    
    /**
     * Returns UIPermissionLevel for given UI element name
     *
     * @param elementName UI element name
     * @return permission level
     */
    UIPermissionLevel Demand(String elementName);
}
