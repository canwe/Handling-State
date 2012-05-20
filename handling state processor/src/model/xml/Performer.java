package model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

// Performer
@XmlType() //namespace = StateChart.xmlNamespace
public class Performer {

    // Performer role
    private String role;

    @XmlAttribute
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
