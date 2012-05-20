package handlers;

import interfaces.IHandler;
import obj.Order;
import obj.TestContext;

public class StateHandler implements IHandler<Order, TestContext> {
    public void execute(Order entity, TestContext context) {
        entity.History.add(entity.State.toString() + " exit");
    }
}
