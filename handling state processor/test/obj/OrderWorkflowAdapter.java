package obj;

import exceptions.NotImplementedException;
import interfaces.IWorkflowAdapter;

public class OrderWorkflowAdapter implements IWorkflowAdapter<Order> {

    Order entity;
    String stateChartId = "";

    public OrderWorkflowAdapter(Object order)
    {
        entity = (Order)order;
    }

    public String getEntityType() {
        return entity.getClass().getName();
    }

    public String getCurrentState() {
        return entity.State.toString();
    }

    public String getStateChartId() {
        return stateChartId;
    }

    public void setEntityType(String entityType) {

    }

    public void setCurrentState(String newState) {
        entity.State = (OrderState.valueOf(newState));
    }

    @Override
    public <T extends Enum<T>> void setCurrentState(String newState, Class<T> enumClass) {
        throw new NotImplementedException();
    }

    public void setStateChartId(String stateChartId) {
        this.stateChartId = stateChartId;
    }

    public Order getEntity() {
        return entity;
    }
}
