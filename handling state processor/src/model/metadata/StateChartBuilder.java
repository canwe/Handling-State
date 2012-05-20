package model.metadata;

import conf.EntityWorkflow;
import core.Action;
import enums.UIPermissionLevel;
import exceptions.WorkflowException;
import interfaces.IConfigProvider;
import interfaces.IHandler;
import interfaces.INotifyHandler;
import model.NotifyMessage;
import model.bags.NotificationBag;
import model.bags.StateBag;
import model.bags.TransitionBag;
import model.bags.UIElementBag;
import model.xml.*;
import core.Func;
import core.RuntimeHandler;
import core.RuntimeTransitionHandler;
import core.RuntimeNotifyHandler;

/**
 * Helper class for runtime statechart building in "fluent interface" style
 */
public class StateChartBuilder {

    /**
     * Creates statechart for given entity workflow and registers it in the configuration
     *
     * @param config   configuration provider
     * @param workflow entity workflow for which statechart is created
     * @return creating statechart
     */
    public static StateChart buildStatechart(IConfigProvider config, EntityWorkflow workflow) throws WorkflowException {
        if (workflow == null) {
            throw new IllegalArgumentException("nullable workflow");
        }
        StateChart s = new StateChart();
        s.setId(workflow.getStateChartId());
        config.registerStateChart(s);
        return s;
    }

    /**
     * Adds state to statechart and begins state building
     *
     * @param s       statechart
     * @param id      state Id
     * @param title   state title
     * @param initial can state be initial
     * @return state bag
     * @throws exceptions.WorkflowException can occurs when adding State to StateChart
     */
    public static StateBag withState(StateChart s,
                                     String id,
                                     String title,
                                     Boolean initial)
            throws WorkflowException {
        if (null == id || id.isEmpty()) {
            throw new IllegalArgumentException("nullable id");
        }
        if (null == title || title.isEmpty()) {
            title = id;
        }
        State state = new State();
        state.setId(id);
        state.setTitle(title);
        state.setInitial(null != initial ? initial : false);
        s.addState(state);
        return new StateBag(state, s);
    }

    /**
     * Defines runtime handler for state enter event
     *
     * @param s      state bag
     * @param action The action delegate for event handling
     * @return state bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call contect type
     */
    public static <EntityType, ContextType> StateBag enterHandledBy(StateBag s,
                                                                    Action action) {
        s.getState().setRuntimeEnterHandler(new RuntimeHandler<EntityType, ContextType>(action));
        return s;
    }

    /**
     * Defines runtime handler for state enter event
     *
     * @param s       state bag
     * @param handler The class implements IHandler interface
     * @return state bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call contect type
     */
    public static <EntityType, ContextType>
    StateBag enterHandledBy(StateBag s,
                            IHandler<EntityType, ContextType> handler) {
        s.getState().setRuntimeEnterHandler(handler);
        return s;
    }

    /**
     * Defines runtime handler for state exit event
     *
     * @param s      state bag
     * @param action genegic action delegate for event handling
     * @return state bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call context type
     */
    public static <EntityType, ContextType> StateBag exitHandledBy(StateBag s,
                                                                   Action action) {
        s.getState().setRuntimeExitHandler(new RuntimeHandler<EntityType, ContextType>(action));
        return s;
    }

    /**
     * Defines runtime handler for state exit event
     *
     * @param s       state bag
     * @param handler The class implements IHandler interface
     * @return state bag
     * @typeparam EntityType entity type
     */
    public static <EntityType, ContextType> StateBag exitHandledBy(StateBag s,
                                                                   IHandler<EntityType, ContextType> handler) {
        s.getState().setRuntimeExitHandler(handler);
        return s;
    }

    /**
     * Returning method for finishing state building
     *
     * @param s state bag
     * @return statechart
     */
    public static StateChart ret(StateBag s) {
        return s.getParentChart();
    }

    /**
     * Adds transition to state and begins transition building
     *
     * @param s     state bag
     * @param id    transition Id
     * @param title transition title
     * @return transition bag
     */
    public static TransitionBag withTransition(StateBag s,
                                               String id,
                                               String title)
            throws WorkflowException {
        if (null == id || id.isEmpty()) {
            throw new IllegalArgumentException("nullable id");
        }
        if (null == title || title.isEmpty()) {
            title = id;
        }
        Transition t = new Transition();
        t.setId(id);
        t.setTitle(title);
        s.getState().addTransition(t);
        return new TransitionBag(t, s);
    }

    /**
     * Returning method for finishing transition building
     *
     * @param t ransition bag
     * @return state bag
     * @throws exceptions.WorkflowException when passed parameters isn't correct
     */
    public static StateBag ret(TransitionBag t)
            throws WorkflowException {
        if (null == t.getTransition().getTargetState() || t.getTransition().getTargetState().isEmpty()) {
            throw new WorkflowException("TargetState is a required transition attribute.");
        }
        return t.getStateBag();
    }

    /**
     * Defines transition target state
     *
     * @param t       transition bag
     * @param stateId target state Id
     * @return transition bag
     */
    public static TransitionBag toState(TransitionBag t, String stateId) {
        if (null == stateId || stateId.isEmpty()) {
            throw new IllegalArgumentException("nullable stateId");
        }
        t.getTransition().setTargetState(stateId);
        return t;
    }

    /**
     * Defines runtime handler for transition
     *
     * @param t       transition bag
     * @param handler The class implements IHandler or ITransitionHandler interface
     * @return transition bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call context type
     */
    public static <EntityType, ContextType> TransitionBag handledBy(TransitionBag t,
                                                                    IHandler<EntityType, ContextType> handler) {
        t.getTransition().setRuntimeHandler(handler);
        return t;
    }

    /**
     * Defines runtime handler for transition
     *
     * @param t      transition bag
     * @param action generic action delegate for event handling
     * @return transition bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call context type
     */
    public static <EntityType, ContextType> TransitionBag handledBy(TransitionBag t,
                                                                    Action action) {
        t.getTransition().setRuntimeHandler(new RuntimeHandler<EntityType, ContextType>(action));
        return t;
    }

    /**
     * Defines runtime handler for transition
     *
     * @param t                      transition bag
     * @param preValidateAction      generic action delegate for ITransitionHandler.PreValidate method
     * @param executeAction          generic action delegate for IHandler.Execute method
     * @param confirmStateChangeFunc generic function delegate for ITransitionHandler.ConfirmStateChange method
     * @return transition bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call context type
     */
    public static <EntityType, ContextType> TransitionBag handledBy(TransitionBag t,
                                                                    Action preValidateAction,
                                                                    Action executeAction,
                                                                    Func confirmStateChangeFunc) {
        t.getTransition()
         .setRuntimeHandler(new RuntimeTransitionHandler<EntityType, ContextType>(preValidateAction, executeAction, confirmStateChangeFunc));
        return t;
    }

    /**
     * Defines transition's performer
     *
     * @param t    transition bag
     * @param role role name
     * @return transition bag
     * @throws exceptions.WorkflowException may be throwed when trying add performer to transition
     */
    public static TransitionBag performedBy(TransitionBag t, String role)
            throws WorkflowException {
        if (null == role || role.isEmpty()) {
            throw new IllegalArgumentException("nullable role");
        }
        t.getTransition().addPerformer(role);
        return t;
    }

    /**
     * Add notification to transition and begins build notification
     *
     * @param t          transition bag
     * @param templateId notification template Id
     * @return notification bag
     * @throws exceptions.WorkflowException may be throwed when trying add notification to transition
     */
    public static NotificationBag withNotification(TransitionBag t, String templateId)
            throws WorkflowException {
        if (null == templateId || templateId.isEmpty()) {
            throw new IllegalArgumentException("nullable templateId");
        }
        Notification n = new Notification();
        n.setTemplateId(templateId);
        t.getTransition().addNotification(n);
        return new NotificationBag(n, t);
    }

    /**
     * Returning method for finishing notofocation building
     *
     * @param n notification bag
     * @return transition bag
     */
    public static TransitionBag ret(NotificationBag n) {
        return n.getTransitionBag();
    }

    /**
     * Adds "to" role recipient
     *
     * @param n    notification bag
     * @param role role name
     * @return notification bag
     * @throws exceptions.WorkflowException
     */
    public static NotificationBag toRole(NotificationBag n, String role)
            throws WorkflowException {
        if (null == role || role.isEmpty()) {
            throw new IllegalArgumentException("nullable role");
        }
        n.getNotification().addTo(new Recipient(role, null));
        return n;
    }

    /**
     * Adds "to" address recipient
     *
     * @param n       notification bag
     * @param address address
     * @return notification bag
     * @throws exceptions.WorkflowException
     */
    public static NotificationBag toAddress(NotificationBag n, String address)
            throws WorkflowException {
        if (null == address || address.isEmpty()) {
            throw new IllegalArgumentException("nullable address");
        }
        n.getNotification().addTo(new Recipient(null, address));
        return n;
    }

    /**
     * Adds "cc" role recipient
     *
     * @param n    notification bag
     * @param role role name
     * @return notification bag
     * @throws exceptions.WorkflowException
     */
    public static NotificationBag ccRole(NotificationBag n, String role)
            throws WorkflowException {
        if (null == role || role.isEmpty()) {
            throw new IllegalArgumentException("nullable role");
        }
        n.getNotification().addCc(new Recipient(role, null));
        return n;
    }

    /**
     * Adds "cc" address recipient
     *
     * @param n       notification bag
     * @param address address
     * @return notification bag
     * @throws exceptions.WorkflowException
     */
    public static NotificationBag ccAddress(NotificationBag n, String address)
            throws WorkflowException {
        if (null == address || address.isEmpty()) {
            throw new IllegalArgumentException("nullable address");
        }
        n.getNotification().addCc(new Recipient(null, address));
        return n;
    }

    /**
     * Defines runtime notification message handler
     *
     * @param n                          notification bag
     * @param resolveAddressFunc         generic func delegate for method INotifyHandler.ResolveAddress
     * @param parseMessageTemplateAction generic action delegate for method INotifyHandler.ParseMessageTemplate
     * @return notofication bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call context type
     */
    public static <EntityType, ContextType> NotificationBag handledBy(NotificationBag n,
                                                                      Func resolveAddressFunc,
                                                                      Action parseMessageTemplateAction) {
        n.getNotification()
         .setRuntimeHandler(new RuntimeNotifyHandler<EntityType, ContextType>(resolveAddressFunc, parseMessageTemplateAction));
        return n;
    }

    /**
     * Defines runtime notification message handler
     *
     * @param n       notification bag
     * @param handler INotifyHandler implementation object
     * @return notification bag
     * @typeparam EntityType entity type
     * @typeparam ContextType call context type
     */
    public static <EntityType, ContextType> NotificationBag handledBy(NotificationBag n,
                                                                      INotifyHandler<EntityType, ContextType> handler) {
        n.getNotification().setRuntimeHandler(handler);
        return n;
    }

    /**
     * Adds notify messge template to statechart
     *
     * @param s               statechart
     * @param templateId      template Id
     * @param subjectTemplate subject template
     * @param bodyTemplate    body template
     * @return statechart
     * @throws exceptions.WorkflowException
     */
    public static StateChart withNotifyTemplate(StateChart s,
                                                String templateId,
                                                String subjectTemplate,
                                                String bodyTemplate)
            throws WorkflowException {
        s.addNotifyTemplate(templateId, subjectTemplate, bodyTemplate);
        return s;
    }

    /**
     * Defines UI element and begins build UIElement
     *
     * @param s         statechart
     * @param uiElement UI element name
     * @return UIElement bag
     * @throws exceptions.WorkflowException can occurs
     */
    public static UIElementBag definePermissionsFor(StateBag s,
                                                    String uiElement)
            throws WorkflowException {
        UIElement e = new UIElement();
        e.setName(uiElement);
        s.getState().addUIElement(e);
        return new UIElementBag(e, s);
    }

    /**
     * Returning method for finishing notofocation building
     *
     * @param e UIElement bag
     * @return state bag
     */
    public static StateBag ret(UIElementBag e) {
        return e.getStateBag();
    }

    /**
     * Defines permission for UI element
     *
     * @param e     UIElement bag
     * @param role  role name
     * @param level permission level
     * @return UIElement bag
     * @throws exceptions.WorkflowException can occur when adding permission to UIElement
     */
    public static UIElementBag forRole(UIElementBag e,
                                       String role,
                                       UIPermissionLevel level)
            throws WorkflowException {
        e.getUIElement().addPermission(role, level);
        return e;
    }

}