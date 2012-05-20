package model.xml;

import exceptions.WorkflowException;

import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Transition
 */
@XmlType() //namespace = StateChart.xmlNamespace
public class Transition {
    // Id
    private String id;

    // Title
    private String title;

    // Target state Id
    private String targetState;

    // Transition handler class name
    private String handler;

    // Transition description
    private Description description = new Description();

    private Object runtimeHandler;

    private List<Performer> _performers;

    // Performers List
    private List<Performer> performers;

    @XmlElementWrapper(name = "Performers")
    @XmlElement(name = "Performer")
    public List<Performer> getPerformers() {
        return performers;
//        if (_performers == null) {
//            _performers = new ArrayList<Performer>();
//        } else {
//            _performers.clear();
//        }
//        _performers.addAll(performers == null ? Collections.<Performer>emptyList() : performers);
//        return _performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
        if (performers != null) {
            _performers = new ArrayList<Performer>();
            _performers.addAll(performers);
        } else {
            _performers = new ArrayList<Performer>();
        }
        // this.performers = new ArrayList<Performer>(_performers);
    }

    /**
     * Adds performer
     *
     * @param role role name
     * @throws exceptions.WorkflowException
     */
    public void addPerformer(String role) throws WorkflowException {
        if (getPerformer(role) != null)
            throw new WorkflowException("Error on add transition's performer. Performer role '{0}' already exists in transition '{1}'.",
                                        new Object[]{role, this.getId()});
        Performer p = new Performer();
        p.setRole(role);
        this.performers.add(p);
    }

    public Object getPerformer(String role) {
        for (Performer p : performers)
            if (p.getRole().equals(role)) {
                return p;
            }
        return null;
    }

    private List<Notification> _notifications;

    // Notifications list
    private List<Notification> notifications;

    @XmlElementWrapper(name = "Notifications")
    @XmlElement(name = "Notification")
    public List<Notification> getNotifications() {
        return notifications;
//        if (_notifications == null) {
//            _notifications = new ArrayList<Notification>();
//        } else {
//            _notifications.clear();
//        }
//        _notifications.addAll(notifications == null ? Collections.<Notification>emptyList() : notifications);
//        return _notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        if (notifications != null) {
            _notifications = new ArrayList<Notification>();
            _notifications.addAll(notifications);
        } else {
            _notifications = new ArrayList<Notification>();
        }
        //this.notifications = new ArrayList<Notification>(_notifications);
    }

    public void addNotification(Notification n) throws WorkflowException {
        if (getNotification(n.getTemplateId()) != null)
            throw new WorkflowException("Error on add transition's notification. Notification '{0}' already exists in transition '{1}'.",
                                        new Object[]{n.getTemplateId(), this.getId()});
        this.notifications.add(n);
    }

    public Notification getNotification(String templateId) {
        for (Notification n : notifications)
            if (n.getTemplateId().equals(templateId)) {
                return n;
            }
        return null;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlAttribute
    public String getTargetState() {
        return targetState;
    }

    public void setTargetState(String targetState) {
        this.targetState = targetState;
    }

    @XmlAttribute
    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @XmlElement(name = "Description")
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @XmlTransient
    public Object getRuntimeHandler() {
        return runtimeHandler;
    }

    public void setRuntimeHandler(Object runtimeHandler) {
        this.runtimeHandler = runtimeHandler;
    }
}