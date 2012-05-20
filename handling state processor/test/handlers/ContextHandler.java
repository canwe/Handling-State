package handlers;

import interfaces.IHandler;
import obj.Order;
import obj.TestContext;

public class ContextHandler implements IHandler<Order, TestContext> {
    public void execute(Order entity, TestContext context) {
        System.out.println(String.format("Context is '%s',  Order state %s", context.Text, entity.State));
        context.Text += " has been done";
    }
}
