package model.xml;

import exceptions.WorkflowException;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * State
 */
@XmlRootElement
@XmlType() //namespace = StateChart.xmlNamespace
public class State {
    // State Id
    private String id;

    // State title
    private String title;

    // Can state to be initial
    private boolean initial = true;

    // OnStateEnter event handler class name
    private String onEnterHandler;

    // OnStateExit event handler class name
    private String onExitHandler;

    // State description
    private Description description = new Description();

    // Runtime OnStateEnter event handler class
    private Object runtimeEnterHandler;

    // Runtime OnStateExit event handler class
    private Object runtimeExitHandler;

    private List<Transition> _transitions = null;

    // State transitions list
    private List<Transition> transitions;

    @XmlElement(name = "Transition")
    public List<Transition> getTransitions() {
        //return transitions;
        if (_transitions == null) {
            _transitions = new ArrayList<Transition>();
        } else {
            _transitions.clear();
        }
        _transitions.addAll(transitions == null ? Collections.<Transition>emptyList() : transitions);
        return _transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        //this.transitions = transitions;
        if (transitions != null) {
            _transitions = new ArrayList<Transition>();
            _transitions.addAll(transitions);
        } else {
            _transitions = new ArrayList<Transition>();
        }
        this.transitions = new ArrayList<Transition>(_transitions);
    }

    private List<UIElement> _UIPermissions;

    // UI elements permissions list
    private List<UIElement> UIPermissions;

    @XmlElement
    public List<UIElement> getUIPermissions() {
        if (_UIPermissions == null) {
            _UIPermissions = new ArrayList<UIElement>();
        } else {
            _UIPermissions.clear();
        }
        _UIPermissions.addAll(UIPermissions == null ? Collections.<UIElement>emptyList() : UIPermissions);
        return _UIPermissions;
    }

    public void setUIPermissions(List<UIElement> UIPermissions) {
        if (UIPermissions != null) {
            _UIPermissions = new ArrayList<UIElement>();
            _UIPermissions.addAll(UIPermissions);
        } else {
            _UIPermissions = new ArrayList<UIElement>();
        }
        this.UIPermissions = new ArrayList<UIElement>(_UIPermissions);
    }

    public Transition getTransition(String transitionId) throws WorkflowException {
        for (Transition t : this.transitions) {
            if (t.getId().equals(transitionId)) {
                return t;
            }
        }
        throw new WorkflowException("Transition '{0}' is not exist.",
                new Object[]{transitionId});
    }

    public Transition findTransition(String transitionId) {
        for (Transition t : this.transitions) {
            if (t.getId().equals(transitionId)) {
                return t;
            }
        }
        return null;
    }

    public void addTransition(Transition t) throws WorkflowException {
        if (findTransition(t.getId()) != null) {
            throw new WorkflowException("Error on add transition into state. Transition '{0}' already exists in the state '{1}'.",
                    new Object[]{t.getId(), this.id});
        }
        this.transitions.add(t);
    }

    public void addUIElement(UIElement e) throws WorkflowException {
        if (getUIElement(e.getName()) != null) {
            throw new WorkflowException("Error on add UI element permissions. Ui element '{0}' already exists.",
                    new Object[]{e.getName()});
        }
        this.UIPermissions.add(e);
    }

    public UIElement getUIElement(String name) {
        for (UIElement uie : this.UIPermissions) {
            if (uie.getName().equals(name)) {
                return uie;
            }
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
    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    @XmlAttribute
    public String getOnEnterHandler() {
        return onEnterHandler;
    }

    public void setOnEnterHandler(String onEnterHandler) {
        this.onEnterHandler = onEnterHandler;
    }

    @XmlAttribute
    public String getOnExitHandler() {
        return onExitHandler;
    }

    public void setOnExitHandler(String onExitHandler) {
        this.onExitHandler = onExitHandler;
    }

    @XmlElement(name = "Description")
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @XmlTransient
    public Object getRuntimeEnterHandler() {
        return runtimeEnterHandler;
    }

    public void setRuntimeEnterHandler(Object runtimeEnterHandler) {
        this.runtimeEnterHandler = runtimeEnterHandler;
    }

    @XmlTransient
    public Object getRuntimeExitHandler() {
        return runtimeExitHandler;
    }

    public void setRuntimeExitHandler(Object runtimeExitHandler) {
        this.runtimeExitHandler = runtimeExitHandler;
    }
}
