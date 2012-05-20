package tests;

import core.HManager;
import core.security.GenericIdentity;
import core.security.GenericPrincipal;
import core.security.Principals;
import exceptions.WorkflowException;
import interfaces.IWorkflowAdapter;
import junit.framework.Assert;
import model.NotifyMessage;
import model.StateInfo;
import model.TransitionInfo;
import obj.Order;
import obj.OrderState;
import obj.TestContext;
import obj.TestEntity;
import org.junit.Test;
import utils.BaseDirectory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTests {

    String configFile = BaseDirectory.get() + "\\conf\\HConf.xml";
    String pConfigFile = BaseDirectory.get() + "\\conf\\PerformersTest.xml";
    String nConfigFile = BaseDirectory.get() + "\\conf\\NotificationTest.xml";

    @Test
    public void SimpleWorkflowScenario() throws WorkflowException
    {
        Order order = Order.GetOrder();
        TestContext ctx = new TestContext();
        ctx.Text= "Step";
        HManager lm = new HManager(configFile);

        IWorkflowAdapter<Order> w = lm.GetWorkflow(order);
        TransitionInfo[] transitions = lm.GetAvailableTransitions(w);
        Assert.assertEquals(2, transitions.length);
        System.out.println("transitions[0].getId() = " + transitions[0].getId());
        lm.DoStep(w, transitions[0].getId(), ctx);
        Assert.assertEquals("Processing", w.getCurrentState());
        Assert.assertEquals("Result of transition handler work", "Step has been done", ctx.Text);
        Assert.assertEquals("Results of OnEnterHandler and OnExitHandler work", 2, order.History.size());
        Assert.assertEquals("Draft exit", order.History.get(0));
        Assert.assertEquals("Processing enter", order.History.get(1));
    }

    @Test
    public void StateAndTransitionHandlerTest() throws WorkflowException
    {
        Order order = Order.GetOrder();
        TestContext ctx = new TestContext();
        ctx.Text= "Step";
        HManager lm = new HManager(configFile);

        IWorkflowAdapter<Order> w = lm.StartWorkflow(order, ctx);//, new StateInfo("Draft"));
        Assert.assertEquals("Results of OnEnterHandler on workflow start", 1, order.History.size());
        Assert.assertEquals("Draft enter", order.History.get(0));

        order.Product = "";// in this case expect that handler throws exception
        try
        {
            lm.DoStep(w, "Cancel", ctx);
        }
        catch(Exception ex)
        {
            Assert.assertEquals("Order.Product is empty", ex.getMessage());
        }
        Assert.assertEquals("Transition handler run", "Step", ctx.Text);
        Assert.assertEquals(OrderState.Draft, order.State);
        Assert.assertEquals(1, order.History.size());

        order.Product = "CD-RW";
        order.Count = new BigDecimal(10); // in this case expect that handler don't change order state
        lm.DoStep(w, "Cancel", ctx);
        Assert.assertEquals(OrderState.Draft, order.State);
        Assert.assertEquals("Result of transition handler work", "Step has been done", ctx.Text);
        Assert.assertEquals(2, order.History.size());

        order.Count = BigDecimal.ZERO; // in this case expect that all handlers run
        ctx.Text = "Step";
        lm.DoStep(w, "Cancel", ctx);
        Assert.assertEquals(OrderState.Canceled, order.State);
        Assert.assertEquals("Result of transition handler work", "Step has been done", ctx.Text);
        Assert.assertEquals(4, order.History.size());
        Assert.assertEquals("Draft exit", order.History.get(2));
        Assert.assertEquals("Canceled enter", order.History.get(3));
    }

    @Test
    public void TwoWorkflowForOneEntity() throws WorkflowException
    {
        Order order = Order.GetOrder();
        TestContext ctx = new TestContext();
        ctx.Text= "Step";
        HManager lm = new HManager(configFile);

        IWorkflowAdapter<Order> w1 = lm.<Order>GetWorkflow(order);
        IWorkflowAdapter<Order> w2 = lm.<Order>GetWorkflow(order, "OrderApproval");
        Assert.assertEquals("Draft", w1.getCurrentState());
        Assert.assertEquals("", w2.getCurrentState());
        order.ApproveState = "WaitingApprove";
        order.Count = BigDecimal.ZERO; // cancel available if Order.Total == 0 only
        lm.DoStep(w1, "Cancel", ctx);
        if (order.State == OrderState.Canceled)
            lm.DoStep(w2, "Reject", ctx);
        else
            lm.DoStep(w2, "Approve", ctx);
        Assert.assertEquals("Rejected", w2.getCurrentState());
    }

    @Test
    public void StartWorkflowAndEntityInitialization() throws WorkflowException
    {
        TestEntity entity = new TestEntity();
        Assert.assertEquals("", entity.StateChart);
        Assert.assertEquals("", entity.State);

        HManager lm = new HManager(configFile);

        IWorkflowAdapter<TestEntity> workflow = lm.StartWorkflow(entity, (Object)null);
        Assert.assertEquals("Initial2", entity.State);
        Assert.assertEquals("StartWorkflowTest", entity.StateChart);
        workflow = lm.StartWorkflow(entity, (Object)null, new StateInfo("Initial1"));
        Assert.assertEquals("Initial1", entity.State);
        Assert.assertEquals("StartWorkflowTest", entity.StateChart);
    }

    @Test
    public void GetAvailableStatesAndTransitions() throws WorkflowException
    {
        TestEntity entity = new TestEntity();
        HManager lm = new HManager(configFile);

        IWorkflowAdapter<TestEntity> workflow = lm.StartWorkflow(entity, (Object)null, new StateInfo("Initial1"));
        StateInfo[] states = lm.GetWorkflowStates(entity, true);
        Assert.assertEquals(2, states.length);
        states = lm.GetWorkflowStates(entity, false);
        Assert.assertEquals(4, states.length);
        states = lm.GetWorkflowStates(workflow, false);
        Assert.assertEquals(4, states.length);
        states = lm.GetWorkflowStates(entity.getClass().getName(), false);
        Assert.assertEquals(4, states.length);
        TransitionInfo[] transitions = lm.GetAvailableTransitions(workflow);
        Assert.assertEquals(3, transitions.length);
        lm.DoStep(workflow, "ToInitial2");
        transitions = lm.GetAvailableTransitions(workflow);
        Assert.assertEquals(2, transitions.length);
        lm.DoStep(workflow, "ToFinal1");
        transitions = lm.GetAvailableTransitions(workflow);
        Assert.assertEquals(0, transitions.length);
    }

    @Test
    public void Performers() throws WorkflowException
    {
        Principals.setCurrentPrincipal(new GenericPrincipal(GenericIdentity.forUser("user"), new String[]{"Role1"}));
        HManager lm = new HManager(pConfigFile);
        TestEntity entity = new TestEntity();
        IWorkflowAdapter<TestEntity> workflow = lm.StartWorkflow(entity, (Object)null);
        TransitionInfo[] transitions = lm.GetAvailableTransitions(workflow);
        Assert.assertEquals(1, transitions.length);
        Assert.assertEquals("T1", transitions[0].getId());
        Principals.setCurrentPrincipal(new GenericPrincipal(GenericIdentity.forUser("user"), new String[]{"Role1", "Role2"}));
        transitions = lm.GetAvailableTransitions(workflow);
        Assert.assertEquals(2, transitions.length);
    }

    @Test
    public void NotAuthorizedForTransition() throws WorkflowException
    {
        Principals.setCurrentPrincipal(new GenericPrincipal(GenericIdentity.forUser("user"), new String[]{"Role1"}));
        HManager lm = new HManager(pConfigFile);
        TestEntity entity = new TestEntity();
        IWorkflowAdapter<TestEntity> workflow = lm.StartWorkflow(entity, (Object)null);
        String state = workflow.getCurrentState();
        lm.DoStep(workflow, "T2");
        Assert.assertEquals(state, workflow.getCurrentState());
        lm.DoStep(workflow, "T1");
        Assert.assertNotSame(state, workflow.getCurrentState());
    }

    @Test
    public void NotificationWithHandler() throws WorkflowException
    {
        final List<NotifyMessage> list = new ArrayList<NotifyMessage>();
        TestEntity entity = new TestEntity();
        TestContext context = new TestContext();
        HManager lm = new HManager(nConfigFile);
        lm.setOnSendMessageEventHandler(new HManager.IOnSendMessageEventHandler() {
            @Override
            public void OnSendMessage(NotifyMessage message) {
                list.add(message);
            }
        });
        IWorkflowAdapter<TestEntity> workflow = lm.StartWorkflow(entity, (Object)null);
        lm.DoStep(workflow, "ToFinal2", context);

        Assert.assertEquals(1, list.size());
        NotifyMessage message = list.get(0);
        Assert.assertEquals("Role1@mail.com; ", message.getTo());
        Assert.assertEquals("Role2@mail.com; ", message.getCc());
        Assert.assertEquals("Notify subject handled Test", message.getSubject());
        Assert.assertEquals("Notify2 body handled Test", message.getBody());
    }
}
