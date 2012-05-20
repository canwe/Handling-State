package model.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

// Descriprion class
@XmlRootElement(name = "Description") //namespace = StateChart.xmlNamespace
@XmlType(name = "Description")
public class Description {

    // Path to the source file
    private String source;

    // Description text
    private String text;

    @XmlAttribute(name = "src")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @XmlValue
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
