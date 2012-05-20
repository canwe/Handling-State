package handlers;

import interfaces.ITransitionHandler;
import obj.Order;
import obj.TestContext;
import utils.Strings;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderCancelHandler implements ITransitionHandler<Order, TestContext> {
    public void preValidate(Order entity) {
        if (Strings.isEmpty(entity.Product))
            throw new IllegalArgumentException("Order.Product is empty");
    }

    public Boolean confirmStateChange(Order entity, String targetState) {
        boolean res = BigDecimal.ZERO.setScale(2, RoundingMode.CEILING).equals(entity.getTotal());
        return res;
    }

    public void execute(Order entity, TestContext context) {
        context.Text += " has been done";
    }
}
