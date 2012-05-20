package model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;

// Notification recipient
@XmlType() //namespace = StateChart.xmlNamespace
public class Recipient {

    // Initializes the instance
    public Recipient() {
    }

    /**
     * Initializes the instance with role or address
     *
     * @param role recipient role
     * @param address recipient address
     */
    public Recipient(String role, String address) {
        if (role != null) {
            this.role = role;
        }
        if (address != null) {
            this.address = address;
        }
    }

    // Recipient role
    private String role;

    // Recipient address
    private String address;

    @XmlTransient
    public String getRoleOrAddress() {
        if (null != role && !role.isEmpty()) {
            return role;
        } else {
            return address;
        }
    }

    @XmlAttribute(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlAttribute(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
