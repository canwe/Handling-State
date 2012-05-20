package obj;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Order
{
    public Order() { }

    public String Number;
    public String Customer;
    public String Address;
    public String Product;
    public BigDecimal Count;
    public BigDecimal Price;
    private BigDecimal Total;
    public OrderState State;
    public String ApproveState;
    public List<String> History = new ArrayList<String>();

    public static Order GetOrder()
    {
        
        Order order = new Order();

        order.Address = "Bond Street 8, London, England";
        order.Count = new BigDecimal(1);
        order.Customer = "Smith, Joe";
        order.Number = "1/1";
        order.Price = new BigDecimal(12.2);
        order.Product = "CD-RW";
        order.State = OrderState.Draft;
        order.ApproveState = "";

        return order;
    }

    public BigDecimal getTotal() {
        BigDecimal bd = Count.multiply(Price);
        bd = bd.setScale(2, RoundingMode.CEILING);
        return bd;
    }
}