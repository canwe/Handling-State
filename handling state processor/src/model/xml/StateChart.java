package model.xml;

import exceptions.WorkflowException;

import java.util.*;
import javax.xml.bind.annotation.*;

/**
 * Statechart
 */
@XmlRootElement(name = "StateChart") //namespace = StateChart.xmlNamespace
@XmlType(name = "StateChart")
public class StateChart {
    /**
     * Xml namespace
     */
    public static final String xmlNamespace = "urn:Listma:Statechart";

    /**
     * Statechart Id
     */
    private String id;

    private List<State> _states;

    /**
     * States list
     */
    private List<State> states;

    @XmlElement(name = "State")
    public List<State> getStates() {
        return states;
//        if (_states == null) {
//            _states = new ArrayList<State>();
//        } else {
//            _states.clear();
//        }
//        _states.addAll(states == null ? Collections.<State>emptyList() : states);
//        return _states;
    }

    public void setStates(List<State> states) {
        this.states = states;
        if (states != null) {
            _states = new ArrayList<State>();
            _states.addAll(states);
        } else {
            _states = new ArrayList<State>();
        }
        //this.states = new ArrayList<State>(_states);
    }

    private List<NotifyTemplate> _notifyTemplates;

    /**
     * Notification message templates list
     */
    private List<NotifyTemplate> notifyTemplates;

    @XmlElementWrapper(name = "NotifyTemplates")
    @XmlElement(name = "NotifyTemplate")
    public List<NotifyTemplate> getNotifyTemplates() {
        return notifyTemplates;
//        if (_notifyTemplates == null) {
//            _notifyTemplates = new ArrayList<NotifyTemplate>();
//        } else {
//            _notifyTemplates.clear();
//        }
//        _notifyTemplates.addAll(notifyTemplates == null ? Collections.<NotifyTemplate>emptyList() : notifyTemplates);
//        return _notifyTemplates;
    }

    public void setNotifyTemplates(List<NotifyTemplate> notifyTemplates) {
        this.notifyTemplates = notifyTemplates;
        if (notifyTemplates != null) {
            _notifyTemplates = new ArrayList<NotifyTemplate>();
            _notifyTemplates.addAll(notifyTemplates);
        } else {
            _notifyTemplates = new ArrayList<NotifyTemplate>();
        }
        //this.notifyTemplates = new ArrayList<NotifyTemplate>(_notifyTemplates);
    }

    public NotifyTemplate getNotifyTemplate(String id) {
        for (NotifyTemplate n : notifyTemplates)
            if (n.getId().equals(id)) {
                return n;
            }
        return null;
    }

    public State getState(String id) {
        for (State s : this.states)
            if (s.getId().equals(id)) {
                return s;
            }
        return null;
    }

    public List<State> getStates(Boolean initialOnly) {
        List<State> states = new ArrayList<State>();
        for (State s : this.states) {
            if (!initialOnly | (s.isInitial() & initialOnly)) {
                states.add(s);
            }
        }
        return states;
    }

    public void addState(State state)
    throws WorkflowException {
        if (getState(state.getId()) != null)
            throw new WorkflowException("Error on add state into statechart. State '{0}' already exists in the statechart.",
                                        new Object[]{state.getId()});
        this.states.add(state);
    }

    public void addNotifyTemplate(String templateId, String subjectTemplate, String bodyTemplate)
    throws WorkflowException {
        if (getNotifyTemplate(templateId) != null)
            throw new WorkflowException("Error on add notify template into statechart. Notify template '{0}' already exists in the statechart.",
                                        new Object[]{templateId});
        NotifyTemplate nt = new NotifyTemplate();
        nt.setId(templateId);
        nt.setSubject(subjectTemplate);
        nt.setBody(bodyTemplate);
        this.notifyTemplates.add(nt);
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
