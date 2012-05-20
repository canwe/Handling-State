package core;

import model.StateInfo;
import model.xml.State;
import model.xml.StateChart;
import exceptions.WorkflowException;
import interfaces.IWorkflowAdapter;
import interfaces.IWorkflowFactory;
import interfaces.IHandler;
import conf.EntityWorkflow;

import java.util.List;
import java.lang.reflect.InvocationTargetException;

public class EntityFactoryService {
    public static <T> IWorkflowAdapter<T> getWorkflow(T entity,
                                                      IWorkflowFactory<T> factory,
                                                      EntityWorkflow workflow) throws WorkflowException {
        IWorkflowAdapter<T> res;
        if (null == workflow.getStateChartMap() || workflow.getStateChartMap().isEmpty()) {
            res = factory.getWorkflow(entity, workflow.getStateMap());
            res.setStateChartId(workflow.getStateChartId());
        } else {
            res = factory.getWorkflow(entity, workflow.getStateMap(), workflow.getStateChartMap());
        }
        res.setEntityType(workflow.getEntityType());
        return res;
    }

    public static <T, C> IWorkflowAdapter<T> StartWorkflow(T entity,
                                                           IWorkflowFactory<T> factory,
                                                           EntityWorkflow workflow,
                                                           StateChart statechart,
                                                           C context)
            throws WorkflowException {
        IWorkflowAdapter<T> res = getWorkflow(entity, factory, workflow);
        init(res, workflow, statechart, workflow.getInitialState(), context);
        return res;

    }

    public static <T, C> IWorkflowAdapter<T> StartWorkflow(T entity,
                                                           IWorkflowFactory<T> factory,
                                                           EntityWorkflow workflow,
                                                           StateChart statechart,
                                                           StateInfo initialState,
                                                           C context) throws WorkflowException {
        IWorkflowAdapter<T> res = getWorkflow(entity, factory, workflow);
        init(res, workflow, statechart, initialState.getId(), context);
        return res;
    }

    private static <T, C> void init(IWorkflowAdapter<T> res,
                                    EntityWorkflow w,
                                    StateChart s,
                                    String initialState,
                                    C context)
            throws WorkflowException {
        res.setStateChartId(w.getStateChartId());
        if (null == initialState || initialState.isEmpty() || initialState.equals("*")) {
            List<State> states = s.getStates(true);
            for (State st : states) {
                initialState = st.getId();
                break;
            }
            if (initialState == null)
                throw new WorkflowException("Configuration error. Statechart '{0}' do not contain states marked as 'initial'.",
                        new Object[]{s.getId()});
        }
        State state = s.getState(initialState);
        if (state == null)
            throw new WorkflowException("Configuration error. InitialState '{0}' of '{1}' entity workflow do not exist in '{2}' statechart.",
                    new Object[]{initialState, w.getEntityType(), s.getId()});
        else {
            res.setCurrentState(initialState);
            if (!(null == state.getOnEnterHandler() || state.getOnEnterHandler().isEmpty())) {
                IHandler<T, C> enterHandler = null;
                //enterHandler = ReflectedTypeCache.getInstance(state.getOnEnterHandler());
                enterHandler = (IHandler<T, C>) ReflectedTypeCache.getInstance(state.getOnEnterHandler());
                if (enterHandler != null) {
                    enterHandler.execute(res.getEntity(), context);
                } else {
                    throw new WorkflowException("Configuration error.",
                            new Object[]{});
                }
            }
        }
    }
}