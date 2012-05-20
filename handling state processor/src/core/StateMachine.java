package core;

import com.lordjoe.csharp.Delegator;
import exceptions.WorkflowException;
import interfaces.*;
import model.NotifyMessage;
import model.xml.NotifyTemplate;
import model.xml.StateChart;
import model.xml.State;
import model.xml.Transition;
import utils.Strings;

public class StateMachine<EntityType> {
    IWorkflowAdapter<EntityType> _entity;
    IConfigProvider _config;
    StateChart _statechart;
    HManager _hmanager;

    public StateMachine(HManager manager, IWorkflowAdapter<EntityType> entity, StateChart statechart, IConfigProvider config)
    {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        if (statechart == null) {
            throw new IllegalArgumentException("statechart cannot be null");
        }
        if (config == null) {
            throw new IllegalArgumentException("config cannot be null");
        }
        _entity = entity;
        _statechart = statechart;
        _config = config;
        _hmanager = manager;
        sendMessage = new Delegator(IOnSendMessageHandler.class);
    }

    public static interface IOnSendMessageHandler {

        void OnSendMessage(NotifyMessage message);
    }

    //public event SendMessageEventHandler SendMessage;
    public Delegator sendMessage;

    protected <ContextType> void DoStep(String transitionId, ContextType context)
    throws WorkflowException
    {

        State state = _statechart.getState(_entity.getCurrentState());
        if (state == null) {
            throw new WorkflowException("State '{0}' do not exist in the '{1}' statechart.", _entity.getCurrentState(), _entity.getStateChartId());
        }
        Transition t = state.getTransition(transitionId);
        if (t == null) {
            throw new WorkflowException("Transition '{0}' do not exist in the state '{1}' of '{2}' statechart.", transitionId, _entity.getCurrentState(), _entity.getStateChartId());
        }
        String targetState = _entity.getCurrentState();
        if (!AuthorizationService.authorize(_entity, t, _config, context)) return;
        IHandler<EntityType, ContextType> handler = null;
        ITransitionHandler<EntityType, ContextType> tHandler = null;
        if (t.getRuntimeHandler() != null)
        {
            handler = (IHandler<EntityType, ContextType>) t.getRuntimeHandler();
            tHandler = (ITransitionHandler<EntityType, ContextType>) t.getRuntimeHandler();
        }
        else if (!Strings.isEmpty(t.getHandler()))
        {
            //handler = ReflectedTypeCache.<IHandler<EntityType, ContextType>>getInstance(t.getHandler(), (Class<EntityType>)_entity.getEntity(),getClass(), (Class<ContextType>) context.getClass());
            handler = (IHandler<EntityType, ContextType>)ReflectedTypeCache.getInstance(t.getHandler(), (Class<EntityType>)_entity.getEntity().getClass(), (Class<ContextType>) context.getClass());
            try {
                tHandler = (ITransitionHandler<EntityType, ContextType>) handler;
            } catch (ClassCastException cce) {
                ;// silent quit
                tHandler = null;
            }
        }
        //prevalidate
        if (tHandler != null) tHandler.preValidate(_entity.getEntity());
        //state exit
        OnStateExit(context, state);
        //step handler execute
        if (handler != null) handler.execute(_entity.getEntity(), context);
        //define target state
        if (tHandler != null)
        {
            if (tHandler.confirmStateChange(_entity.getEntity(), t.getTargetState())) {
                targetState = t.getTargetState();
            }
        }
        else
        {
            targetState = t.getTargetState();
        }
        //notification
        this.<ContextType>NotificationHandle(context, t);
        //change state
        if (!Strings.isEqual(targetState, _entity.getCurrentState()))
        {
            State target = _statechart.getState(targetState);
            if (target == null) {
                throw new WorkflowException("Target state '{0}' do not exist in the '{1}' statechart.", targetState, _entity.getStateChartId());
            }
            _entity.setCurrentState(targetState);
            OnStateEnter(context, target);
        }
    }


    private <ContextType> void OnStateExit(ContextType context, State state) throws WorkflowException
    {
        IHandler<EntityType, ContextType> exitHandler = null;
        if (state.getRuntimeExitHandler() != null)
        {
            exitHandler = (IHandler<EntityType, ContextType>) state.getRuntimeExitHandler();
        }
        else if (!Strings.isEmpty(state.getOnExitHandler()))
        {
            //exitHandler = ReflectedTypeCache.<IHandler<EntityType, ContextType>>getInstance(state.getOnExitHandler(), (Class<EntityType>)_entity.getEntity(),getClass(), (Class<ContextType>) context.getClass());
            exitHandler = (IHandler<EntityType, ContextType>)ReflectedTypeCache.getInstance(state.getOnExitHandler(), (Class<EntityType>)_entity.getEntity().getClass(), (Class<ContextType>) context.getClass());
        }
        if(exitHandler != null) exitHandler.execute(_entity.getEntity(), context);
    }

    private <ContextType> void OnStateEnter(ContextType context, State state) throws WorkflowException
    {
        IHandler<EntityType, ContextType> enterHandler = null;
        if (state.getRuntimeEnterHandler() != null)
        {
            enterHandler = (IHandler<EntityType, ContextType>) state.getRuntimeEnterHandler();
        }
        else if (!Strings.isEmpty(state.getOnEnterHandler()))
        {
            //enterHandler = ReflectedTypeCache.<IHandler<EntityType, ContextType>>getInstance(state.getOnEnterHandler(), (Class<EntityType>)_entity.getEntity(),getClass(), (Class<ContextType>) context.getClass());
            enterHandler = (IHandler<EntityType, ContextType>)ReflectedTypeCache.getInstance(state.getOnEnterHandler(), (Class<EntityType>)_entity.getEntity().getClass(), (Class<ContextType>) context.getClass());
        }
        if (enterHandler != null) enterHandler.execute(_entity.getEntity(), context);
    }

    private <ContextType> void NotificationHandle(ContextType context, Transition t)
    throws WorkflowException
    {
        if (null == t.getNotifications()) return;
        for (int i = 0; i < t.getNotifications().size(); i++)
        {
            INotifyHandler<EntityType, ContextType> nHandler = null;
            if (t.getNotifications().get(i).getRuntimeHandler() != null)
            {
                nHandler = (INotifyHandler<EntityType, ContextType>) t.getNotifications().get(i).getRuntimeHandler();
            }
            else if (!Strings.isEmpty(t.getNotifications().get(i).getHandler()))
            {
                //nHandler = ReflectedTypeCache.<INotifyHandler<EntityType, ContextType>>getInstance(t.getNotifications().get(i).getHandler(), (Class<EntityType>)_entity.getEntity(),getClass(), (Class<ContextType>) context.getClass());
                nHandler = (INotifyHandler<EntityType, ContextType>)ReflectedTypeCache.getInstance(t.getNotifications().get(i).getHandler(), (Class<EntityType>)_entity.getEntity().getClass(), (Class<ContextType>) context.getClass());
            }
            NotifyTemplate template = _statechart.getNotifyTemplate(t.getNotifications().get(i).getTemplateId());
            if (template == null) {
                throw new WorkflowException("Notify Template '{0}' has not been found.", t.getNotifications().get(i).getTemplateId());
            }
            NotifyProcessor<EntityType, ContextType> np = new NotifyProcessor<EntityType, ContextType>(_entity.getEntity(), context, t.getNotifications().get(i), template, nHandler);
            NotifyMessage m = np.Process();
            //if (m != null && SendMessage != null) SendMessage(m);
            if (m != null && sendMessage != null) {
                IOnSendMessageHandler handler = (IOnSendMessageHandler)sendMessage.build(_hmanager, "OnSendMessage");
                handler.OnSendMessage(m);
            }
        }
    }


}
