package handlers;

import interfaces.IHandler;
import obj.Order;
import obj.TestContext;

public class StateEnterHandler implements IHandler<Order, TestContext> {
    public void execute(Order entity, TestContext context) {
        entity.History.add(entity.State.toString() + " enter");
    }
}
