package tests;

import conf.HConfData;
import exceptions.WorkflowException;
import junit.framework.Assert;
import obj.Order;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utils.BaseDirectory;

public class HConfTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void HConfLoadTest() throws WorkflowException {
        String fileName = BaseDirectory.get() + "\\conf\\HConf.xml";
        HConfData target = HConfData.Load(fileName);
        Assert.assertNotNull(target);
        Assert.assertEquals("conf", target.stateChartDir);
        Assert.assertEquals(3, target.getEntities().length);
        Assert.assertEquals(Order.class.getName(), target.getEntities()[0].getEntityType());
        //Assert.assertEquals("Listma.ReflectionFactory`1", target.Entities[1].WorkflowFactoryClass);
        //Assert.assertEquals("Listma.RoleProvider`2", target.Entities[0].RoleProviderClass);
        Assert.assertEquals("OrderApprovalWorkflow1", target.getEntities()[1].getStateChartId());
        Assert.assertEquals("*", target.getEntities()[1].getInitialState());
        Assert.assertEquals("ApproveState", target.getEntities()[1].getStateMap());
    }

    @Test
    public void HConfExceptionTest() throws WorkflowException {
        exception.expect(WorkflowException.class);
        HConfData target = HConfData.Load(" ");
        Assert.assertNotNull(target);
    }

}
