package core;

import conf.EntityWorkflow;
import conf.HConfData;
import exceptions.WorkflowException;
import interfaces.*;
import model.NotifyMessage;
import model.TransitionInfo;
import model.xml.State;
import model.xml.StateChart;
import model.StateInfo;
import utils.Strings;

import javax.naming.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * @author victor.konopelko
 *         Date: 30.01.12
 */
public class HManager {

    IConfigProvider _configProvider;

    /// <summary>
    /// Default constructor. Creates instance inicializing with default instance of ConfigProvider.
    /// </summary>
    /// <remarks>
    /// This constructor finds configuration file "listma.config" in the current AppDomain.BaseDirectory
    /// </remarks>
    public HManager() throws WorkflowException {
        _configProvider = ConfigProvider.GetDefault();
    }

    /// <summary>
    /// Creates instance initializing with specified configuration file
    /// </summary>
    /// <param name="configFileName">configuration file name</param>
    public HManager(String configFileName) throws WorkflowException {
        if (Strings.isEmpty(configFileName))
            _configProvider = ConfigProvider.GetDefault();
        else
            _configProvider = new ConfigProvider(HConfData.Load(configFileName));
    }

    /// <summary>
    /// Creates instance initializing with specifing IConfigProvider instance
    /// </summary>
    /// <param name="configProvider"></param>
    public HManager(IConfigProvider configProvider) {
        if (configProvider == null) {
            throw new IllegalArgumentException("configProvider cannot be null");
        }
        _configProvider = configProvider;
    }

    private IOnSendMessageEventHandler onSendMessageEventHandler;

    public IOnSendMessageEventHandler getOnSendMessageEventHandler() {
        return onSendMessageEventHandler;
    }

    public void setOnSendMessageEventHandler(IOnSendMessageEventHandler onSendMessageEventHandler) {
        this.onSendMessageEventHandler = onSendMessageEventHandler;
    }

    /// <summary>
    /// Occurs when a transition notification message needs for send
    /// </summary>
    //public event SendMessageEventHandler SendMessage;

    //could be replaced with delegator
    public static interface IOnSendMessageEventHandler {
        void OnSendMessage(NotifyMessage message);
    }

    public void OnSendMessage(NotifyMessage message) {
        //if (SendMessage != null) SendMessage(message);
        if (onSendMessageEventHandler != null) onSendMessageEventHandler.OnSendMessage(message);
    }

    /// <summary>
    /// Returns array of transitions which available for current entity's state and current user
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity workflow adapter</param>
    /// <param name="context">call context object</param>
    /// <returns>array of TransitionInfo objects</returns>
    public <EntityType, ContextType> TransitionInfo[] GetAvailableTransitions(IWorkflowAdapter<EntityType> entity, ContextType context) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity");
        }
        StateChart chart = _configProvider.getStateChart(entity.getStateChartId());
        State state = chart.getState(entity.getCurrentState());
        List<TransitionInfo> list = new ArrayList<TransitionInfo>();
        for (int i = 0; i < state.getTransitions().size(); i++)
            if (AuthorizationService.authorize(entity, state.getTransitions().get(i), _configProvider, context))
                list.add(new TransitionInfo(state.getTransitions().get(i)));

        return list.toArray(new TransitionInfo[list.size()]);
    }

    /// <summary>
    /// Returns array of transitions which available for current entity's state and current user
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entity">entity workflow adapter</param>
    /// <returns>array of TransitionInfo objects</returns>
    public <EntityType> TransitionInfo[] GetAvailableTransitions(IWorkflowAdapter<EntityType> entity) throws WorkflowException {
        return this.<EntityType, Object>GetAvailableTransitions(entity, null);
    }

    /// <summary>
    /// Returns states of given entity workflow
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entity">business entity</param>
    /// <param name="initialOnly">if true, specifies only initial states for return</param>
    /// <returns>array of states are specified wor this entity type workflow</returns>
    public <EntityType> StateInfo[] GetWorkflowStates(EntityType entity, boolean initialOnly) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        //return GetWorkflowStates(entity.GetType().FullName, initialOnly);
        return GetWorkflowStates(entity.getClass().getName(), initialOnly);
    }

    /// <summary>
    /// Returns states of given entity workflow
    /// </summary>
    /// <param name="entityType">entity type String</param>
    /// <param name="initialOnly">if true, specifies only initial states for return</param>
    /// <returns>array of states are specified wor this entity type workflow</returns>
    public StateInfo[] GetWorkflowStates(String entityType, boolean initialOnly) throws WorkflowException {
        EntityWorkflow workflow = _configProvider.getEntityWorkflow(entityType);
        StateChart stateChart = _configProvider.getStateChart(workflow.getStateChartId());
        return EnumerateStates(stateChart, initialOnly);
    }

    /// <summary>
    /// Returns states of given entity workflow
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entityWorkflow">entity workflow adapter</param>
    /// <param name="initialOnly">if true, specifies only initial states for return</param>
    /// <returns>array of states are specified wor this entity type workflow</returns>
    public <EntityType> StateInfo[] GetWorkflowStates(IWorkflowAdapter<EntityType> entityWorkflow, boolean initialOnly) throws WorkflowException {
        if (entityWorkflow == null) {
            throw new IllegalArgumentException("entityWorkflow cannot be null");
        }
        StateChart stateChart = _configProvider.getStateChart(entityWorkflow.getStateChartId());
        return EnumerateStates(stateChart, initialOnly);
    }

    /// <summary>
    /// Starts workflow for given entity and initializes entity state according with workflow configuration
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity instance</param>
    /// <param name="context">call context object</param>
    /// <returns>entity workflow adapter instance</returns>
    public <EntityType, ContextType> IWorkflowAdapter<EntityType> StartWorkflow(EntityType entity, ContextType context) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        return this.<EntityType, ContextType>StartWorkflow(entity, (Class<EntityType>) entity.getClass(), context, entity.getClass().getName());
    }

    /// <summary>
    /// Starts workflow for given entity and initializes entity state according with workflow configuration
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity instance</param>
    /// <param name="context">call context object</param>
    /// <param name="entityType">entity type String</param>
    /// <returns>entity workflow adapter instance</returns>
    public <EntityType, ContextType> IWorkflowAdapter<EntityType> StartWorkflow(EntityType entity, Class<EntityType> entityTypeClass, ContextType context, String entityType) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        EntityWorkflow w = _configProvider.getEntityWorkflow(entityType);
        StateChart s = _configProvider.getStateChart(w.getStateChartId());
        IWorkflowAdapter<EntityType> res = EntityFactoryService.StartWorkflow(entity,
                _configProvider.<EntityType>getWorkflowFactory(entityType, entityTypeClass), w, s, context);
        return res;

    }

    /// <summary>
    /// Starts workflow for given entity and initializes entity state according with workflow configuration
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity instance</param>
    /// <param name="context">call context object</param>
    /// <param name="initialState">initial state Id</param>
    /// <returns>entity workflow adapter instance</returns>
    public <EntityType, ContextType> IWorkflowAdapter<EntityType> StartWorkflow(EntityType entity, ContextType context, StateInfo initialState) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        EntityWorkflow w = _configProvider.getEntityWorkflow(entity.getClass().getName());
        StateChart s = _configProvider.getStateChart(w.getStateChartId());
        IWorkflowAdapter<EntityType> res = EntityFactoryService.StartWorkflow(entity,
                _configProvider.<EntityType>getWorkflowFactory(entity.getClass().getName(), (Class<EntityType>)entity.getClass()),
                w, s, initialState, context);
        return res;
    }

    /// <summary>
    /// Starts workflow for given entity and initializes entity state according with workflow configuration
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity instance</param>
    /// <param name="context">call context object</param>
    /// <param name="entityType">entity type String</param>
    /// <param name="initialState">initial state Id</param>
    /// <returns>entity workflow adapter instance</returns>
    public <EntityType, ContextType> IWorkflowAdapter<EntityType> StartWorkflow(EntityType entity, Class<EntityType> entityTypeClass, ContextType context, String entityType, StateInfo initialState) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        EntityWorkflow w = _configProvider.getEntityWorkflow(entityType);
        StateChart s = _configProvider.getStateChart(w.getStateChartId());
        IWorkflowAdapter<EntityType> res = EntityFactoryService.StartWorkflow(entity,
                _configProvider.<EntityType>getWorkflowFactory(entityType, entityTypeClass),
                w, s, initialState, context);
        return res;
    }

    /// <summary>
    /// Returns workflow adapter for given entity
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entity">entity</param>
    /// <returns>entity workflow adapter instance</returns>
    public <EntityType> IWorkflowAdapter<EntityType> GetWorkflow(EntityType entity) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        //return GetWorkflow(entity, entity.GetType().FullName);
        return GetWorkflow(entity, entity.getClass().getName());
    }

    /// <summary>
    /// Returns workflow adapter for given entity
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entity">entity</param>
    /// <param name="entityType">entity type String</param>
    /// <returns>entity workflow adapter instance</returns>
    public <EntityType> IWorkflowAdapter<EntityType> GetWorkflow(EntityType entity, String entityType) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        if (Strings.isEmpty(entityType)) {
            throw new IllegalArgumentException("entityType cannot be null or empty");
        }
        System.out.println("EntityType = " + entityType);
        IWorkflowFactory<EntityType> factory = _configProvider.<EntityType>getWorkflowFactory(entityType, (Class<EntityType>) entity.getClass());
        EntityWorkflow workflow = _configProvider.getEntityWorkflow(entityType);
        IWorkflowAdapter<EntityType> result = EntityFactoryService.getWorkflow(entity, factory, workflow);
        return result;
    }

    /// <summary>
    /// Does workflow step and moves entity into next state
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity</param>
    /// <param name="transitionId">StateChart transition Id</param>
    /// <param name="context">context object</param>
    public <EntityType, ContextType> void DoStep(IWorkflowAdapter<EntityType> entity, String transitionId, ContextType context) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        if (Strings.isEmpty(transitionId)) {
            throw new IllegalArgumentException("transitionId cannot be null or empty");
        }
        StateChart stateChart = _configProvider.getStateChart(entity.getStateChartId());
        StateMachine<EntityType> machine = new StateMachine<EntityType>(this, entity, stateChart, _configProvider);
        //machine.SendMessage += new SendMessageEventHandler(OnSendMessage);
        machine.DoStep(transitionId, context);
    }

    /// <summary>
    /// Does workflow step and moves entity into next state
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entity">entity</param>
    /// <param name="transitionId">StateChart transition Id</param>
    public <EntityType> void DoStep(IWorkflowAdapter<EntityType> entity, String transitionId) throws WorkflowException {
        this.<EntityType, Object>DoStep(entity, transitionId, null);
    }

    /// <summary>
    /// Returns IPermissionProvider for manage UI permissions
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <param name="entity">entity</param>
    /// <returns>IPermissionProvider instance</returns>
    public <EntityType> IPermissionProvider GetPermissionProvider(IWorkflowAdapter<EntityType> entity) throws WorkflowException {
        return this.<EntityType, Object>GetPermissionProvider(entity, (Object) null);
    }

    /// <summary>
    /// Returns IPermissionProvider for manage UI permissions
    /// </summary>
    /// <typeparam name="EntityType">type of statefull entity</typeparam>
    /// <typeparam name="ContextType">type of call context</typeparam>
    /// <param name="entity">entity</param>
    /// <param name="context">context object</param>
    /// <returns>IPermissionProvider instance</returns>
    public <EntityType, ContextType> IPermissionProvider GetPermissionProvider(IWorkflowAdapter<EntityType> entity, ContextType context) throws WorkflowException {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        IRoleProvider<EntityType, ContextType> roleProvider = _configProvider.<EntityType, ContextType>getRoleProvider(entity.getEntityType(), (Class<EntityType>) entity.getEntity().getClass(), (Class<ContextType>) context.getClass());
        StateChart StateChart = _configProvider.getStateChart(entity.getStateChartId());
        return new PermissionProvider<EntityType, ContextType>(entity, roleProvider, StateChart, _configProvider.getDefaultPermissionLevel(), context);
    }

    private static StateInfo[] EnumerateStates(StateChart StateChart, boolean initialOnly) {
        List<StateInfo> list = new ArrayList<StateInfo>();
        for (State s : StateChart.getStates(initialOnly))
            list.add(new StateInfo(s));
        return list.toArray(new StateInfo[list.size()]);
    }

}
