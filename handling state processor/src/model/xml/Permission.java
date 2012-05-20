package model.xml;

import enums.UIPermissionLevel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

// UI element permission

@XmlType() //namespace = "Statechart.XmlNamespace"
public class Permission {

    /// Role name
    private String role;

    // Permission level
    private UIPermissionLevel level = UIPermissionLevel.HIDDEN;

    @XmlAttribute
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlAttribute
    public UIPermissionLevel getLevel() {
        return this.level;
    }

    public void setLevel(UIPermissionLevel level) {
        this.level = level;
    }
}