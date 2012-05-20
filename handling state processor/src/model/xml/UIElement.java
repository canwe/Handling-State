package model.xml;

import enums.UIPermissionLevel;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import exceptions.WorkflowException;

/**
 * UI element permissions
 */
@XmlType() //namespace = StateChart.xmlNamespace
public class UIElement {
    // UI element name
    private String name;

    private List<Permission> _permissions;

    // Permissions list
    private List<Permission> permissions;

    @XmlElement(name = "Permission")
    public List<Permission> getPermissions() {
        if (_permissions == null) {
            _permissions = new ArrayList<Permission>();
        } else {
            _permissions.clear();
        }
        _permissions.addAll(permissions == null ? Collections.<Permission>emptyList() : permissions);
        return _permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        if (permissions != null) {
            _permissions = new ArrayList<Permission>();
            _permissions.addAll(permissions);
        } else {
            _permissions = new ArrayList<Permission>();
        }
        this.permissions = new ArrayList<Permission>(_permissions);
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds permission
     *
     * @param role  role
     * @param level lvl
     * @throws exceptions.WorkflowException exception
     */
    public void addPermission(String role, UIPermissionLevel level) throws WorkflowException {
        if (getPermission(role) != null)
            throw new WorkflowException("Error on add permission for UI element. Permission for role '{0}' for UI element '{1}' already exists.",
                    new Object[]{role, this.getName()});
        Permission p = new Permission();
        p.setRole(role);
        p.setLevel(level);
        this.permissions.add(p);
    }

    /**
     * Gets permission by role name
     *
     * @param role role name
     * @return permission
     */
    public Permission getPermission(String role) {
        for (Permission p : permissions)
            if (p.getRole().equals(role)) {
                return p;
            }
        return null;
    }
}
