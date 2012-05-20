package obj;

import interfaces.IWorkflowAdapter;
import interfaces.IWorkflowFactory;

public class OrderWorkflowFactory implements IWorkflowFactory<Order>{

    public IWorkflowAdapter<Order> getWorkflow(Order entity, String stateMap) {
        if (!"State".equals(stateMap)) {
            throw new IllegalArgumentException("Wrong state map");
        }
        return (IWorkflowAdapter<Order>) new OrderWorkflowAdapter(entity);
    }

    public IWorkflowAdapter<Order> getWorkflow(Order entity, String stateMap, String statechartMap) {
        return getWorkflow(entity, stateMap);
    }
}
