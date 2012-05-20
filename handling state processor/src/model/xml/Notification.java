package model.xml;

import exceptions.WorkflowException;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Transfer notification
 */
@XmlType() //namespace = StateChart.xmlNamespace
public class Notification {
    /**
     * Notification template Id
     */
    private String templateId;

    /**
     * Notification handler class name
     */
    private String handler;

    private List<Recipient> _to;

    /**
     * Main recipients list
     */
    private List<Recipient> to;

    @XmlElementWrapper(name = "To")
    @XmlElement(name = "Recipient")
    public List<Recipient> getTo() {
        return to;
//        if (_to == null) {
//            _to = new ArrayList<Recipient>();
//        } else {
//            _to.clear();
//        }
//        _to.addAll(to == null ? Collections.<Recipient>emptyList() : to);
//        return _to;
    }

    public void setTo(List<Recipient> recipients) {
        this.to = recipients;
        if (recipients != null) {
            _to = new ArrayList<Recipient>();
            _to.addAll(recipients);
        } else {
            _to = new ArrayList<Recipient>();
        }
        //this.to = new ArrayList<Recipient>(_to);
    }

    private List<Recipient> _cc;

    /**
     * Optional recipients list
     */
    private List<Recipient> cc;

    @XmlElementWrapper(name = "Cc")
    @XmlElement(name = "Recipient")
    public List<Recipient> getCc() {
        return cc;
//        if (_cc == null) {
//            _cc = new ArrayList<Recipient>();
//        } else {
//            _cc.clear();
//        }
//        _cc.addAll(cc == null ? Collections.<Recipient>emptyList() : cc);
//        return _cc;
    }

    public void setCc(List<Recipient> recipients) {
        this.cc = recipients;
        if (recipients != null) {
            _cc = new ArrayList<Recipient>();
            _cc.addAll(recipients);
        } else {
            _cc = new ArrayList<Recipient>();
        }
        //this.cc = new ArrayList<Recipient>(_cc);
    }

    private Object runtimeHandler;

    public void addTo(Recipient recipient) throws WorkflowException {
        if (getTo(recipient.getRoleOrAddress()) != null)
            throw new WorkflowException("Error on add notification's recipient. Recipient '{0}' already exists in the 'To' list.",
                    new Object[]{recipient.getRoleOrAddress()});
        this.to.add(recipient);
    }

    public Recipient getTo(String roleOrAddress) {
        for (Recipient r : to)
            if (r.getAddress().equals(roleOrAddress) || r.getRole().equals(roleOrAddress)) {
                return r;
            }
        return null;
    }

    public void addCc(Recipient recipient) throws WorkflowException {
        if (getCc(recipient.getRoleOrAddress()) != null)
            throw new WorkflowException("Error on add notification's recipient. Recipient '{0}' already exists in the 'Cc' list.",
                    new Object[]{recipient.getRoleOrAddress()});
        this.cc.add(recipient);
    }

    public Recipient getCc(String roleOrAddress) {
        for (Recipient r : cc)
            if (r.getAddress().equals(roleOrAddress) || r.getRole().equals(roleOrAddress)) {
                return r;
            }
        return null;
    }

    @XmlAttribute(name = "templateId")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @XmlAttribute
    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @XmlTransient
    public Object getRuntimeHandler() {
        return runtimeHandler;
    }

    public void setRuntimeHandler(Object runtimeHandler) {
        this.runtimeHandler = runtimeHandler;
    }
}
