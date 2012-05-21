package tests;

import conf.EntityWorkflow;
import core.EntityFactoryService;
import core.ReflectionFactory;
import exceptions.WorkflowException;
import interfaces.IWorkflowAdapter;
import junit.framework.Assert;
import obj.FPEntity;
import org.junit.Test;

public class ReflectionFactoryTest {

    @Test
    public void FactoryTest() throws WorkflowException
    {
        ReflectionFactory<FPEntity> f = new ReflectionFactory<FPEntity>();
        FPEntity entity = new FPEntity();
        IWorkflowAdapter<FPEntity> w = EntityFactoryService.getWorkflow(entity, f, GetEntityWorkflow(false));
        Assert.assertEquals("ValueFromConfig", w.getStateChartId());
        w = EntityFactoryService.getWorkflow(entity, f, GetEntityWorkflow(true));
        Assert.assertEquals(ReflectedEntityWorkflowTest.StatechartID, w.getStateChartId());
        Assert.assertEquals("1", w.getCurrentState());
    }

    private static EntityWorkflow GetEntityWorkflow(boolean fullmap)
    {
        EntityWorkflow w = new EntityWorkflow(FPEntity.class.getName(), "ValueFromConfig", "_State");
        w.setInitialState("2");
        if(fullmap) {
            w.setStatechartMap("stateChartId");
        }
        return w;
    }

}
