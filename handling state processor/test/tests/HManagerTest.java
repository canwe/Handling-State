package tests;

import conf.HConfData;
import core.ConfigProvider;
import core.HManager;
import exceptions.WorkflowException;
import interfaces.IConfigProvider;
import interfaces.IWorkflowAdapter;
import junit.framework.Assert;
import model.StateInfo;
import obj.Order;
import obj.TestContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utils.BaseDirectory;

import java.util.Date;

public class HManagerTest {

    String configFile = BaseDirectory.get() + "\\conf\\HConf.xml";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void GetEntityWorkflowGuardTest1() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(IllegalArgumentException.class);
        IWorkflowAdapter<Order> w1 = lm.<Order>GetWorkflow(null);
    }

    @Test
    public void GetEntityWorkflowGuardTest2() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(IllegalArgumentException.class);
        IWorkflowAdapter<Order> w1 = lm.<Order>GetWorkflow(new Order(), "");
    }

    @Test
    public void GetEntityWorkflowGuardTest3() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        IWorkflowAdapter<Order> w1 = lm.<Order>GetWorkflow(Order.GetOrder());
        Assert.assertNotNull(w1);
    }

    @Test
    public void GetEntityWorkflowGuardTest4() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(WorkflowException.class);
        IWorkflowAdapter<Date> w1 = lm.<Date>GetWorkflow(new Date());
    }

    @Test
    public void DoStepGuardTest1() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(IllegalArgumentException.class);
        lm.<Order>DoStep(null, "");
    }

    @Test
    public void DoStepGuardTest2() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        IWorkflowAdapter<Order> w1 = lm.<Order>GetWorkflow(Order.GetOrder());
        exception.expect(IllegalArgumentException.class);
        lm.<Order>DoStep(w1, "");
    }

    @Test
    public void DoStepGuardTest3() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        IWorkflowAdapter<Order> w1 = lm.<Order>GetWorkflow(Order.GetOrder());
        exception.expect(WorkflowException.class);
        lm.<Order>DoStep(w1, "UnexistentTransition");
    }

    @Test
    public void GetAvailableTransitionsTest() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(IllegalArgumentException.class);
        lm.<Order>GetAvailableTransitions(null);
    }

    @Test
    public void ConstructorGuardTest1() throws WorkflowException
    {
        IConfigProvider p = new ConfigProvider(HConfData.Load(configFile));
        HManager lm = new HManager(p);
        Assert.assertNotNull(lm);
    }

    @Test
    public void ConstructorGuardTest2()
    {
        IConfigProvider p = null;
        exception.expect(IllegalArgumentException.class);
        HManager lm = new HManager(p);
        Assert.assertNotNull(lm);
    }

    @Test
    public void ConstructorGuardTest3() throws WorkflowException
    {
        String n = null;
        HManager lm = new HManager(n);
        Assert.assertNotNull(lm);
    }

    @Test
    public void StartWorkflowGuardTest1() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(WorkflowException.class);
        IWorkflowAdapter<Order> w = lm.<Order, Object>StartWorkflow(Order.GetOrder(), (Object)null, new StateInfo("UnExistentState"));
    }
    
    @Test
    public void StartWorkflowGuardTest2() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(IllegalArgumentException.class);
        IWorkflowAdapter<Order> w = lm.<Order, Object>StartWorkflow((Order)null, (Object)null, new StateInfo("UnExistentState"));
    }

    @Test
    public void StartWorkflowGuardTest3() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        Order order = Order.GetOrder();
        IWorkflowAdapter<Order> w = lm.<Order, TestContext>StartWorkflow(order, Order.class, new TestContext(), order.getClass().getName(), new StateInfo("Draft"));
        Assert.assertEquals(Order.class, w.getEntity().getClass());
    }

    @Test
    public void StartWorkflowGuardTest4() throws WorkflowException
    {
        HManager lm = new HManager(configFile);
        exception.expect(IllegalArgumentException.class);
        IWorkflowAdapter<Order> w = lm.StartWorkflow((Order)null, Order.class, (TestContext)null, Order.class.getName(), new StateInfo("Draft"));
        Assert.assertEquals(Order.class, w.getEntity().getClass());
    }
    
}
