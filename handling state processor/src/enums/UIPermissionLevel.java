package enums;

/**
 * UI element permission level
 */

public enum UIPermissionLevel {

    // Element is hidden for user
    HIDDEN(0),

    // Element is read only for user
    READ(1),

    // Element is accessible
    WRITE(2);

    private Integer level;
    
    UIPermissionLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }
}
