package tests;

import core.ReflectionEntityWorkflow;
import exceptions.WorkflowException;
import junit.framework.Assert;
import obj.EnumState;
import obj.FPEntity;
import obj.VariousState;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReflectedEntityWorkflowTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public static final String STATE_FIELD_NAME = "_State";
    public static final String StatechartID_FIELD_NAME = "stateChartId";
    public static final String StatechartID_PROP_NAME = "stateChartId";
    public static final String StatechartID = "NativeValue";
    public static final String StatechartID2 = "NewStatechart";

    @Test
    public void ReflectionStatesTest() throws WorkflowException
    {
        VariousState entity = new VariousState();
        entity.stateIntField = 1;
        entity.stateStringField = "One";
        entity.stateEnumField = EnumState.One;

        ReflectionEntityWorkflow<VariousState> adapter = new ReflectionEntityWorkflow<VariousState>(entity, "stateIntField");
        Assert.assertEquals(entity, adapter.getEntity());
        Assert.assertEquals("1", adapter.getCurrentState());
        adapter.setCurrentState("2");
        Assert.assertEquals("2", adapter.getCurrentState());
        Assert.assertEquals(new Integer(2), entity.stateIntField);

        adapter = new ReflectionEntityWorkflow<VariousState>(entity, "stateIntField");
        Assert.assertEquals(entity, adapter.getEntity());
        Assert.assertEquals("2", adapter.getCurrentState());
        adapter.setCurrentState("3");
        Assert.assertEquals("3", adapter.getCurrentState());
        Assert.assertEquals(new Integer(3), entity.stateIntField);

        adapter = new ReflectionEntityWorkflow<VariousState>(entity, "stateStringField");
        Assert.assertEquals(entity, adapter.getEntity());
        Assert.assertEquals("One", adapter.getCurrentState());
        adapter.setCurrentState("Two");
        Assert.assertEquals("Two", adapter.getCurrentState());
        Assert.assertEquals("Two", entity.stateStringField);

        adapter = new ReflectionEntityWorkflow<VariousState>(entity, "stateStringField");
        Assert.assertEquals(entity, adapter.getEntity());
        Assert.assertEquals("Two", adapter.getCurrentState());
        adapter.setCurrentState("One");
        Assert.assertEquals("One", adapter.getCurrentState());
        Assert.assertEquals("One", entity.stateStringField);

        adapter = new ReflectionEntityWorkflow<VariousState>(entity, "stateEnumField");
        Assert.assertEquals(entity, adapter.getEntity());
        Assert.assertEquals("One", adapter.getCurrentState());
        adapter.setCurrentState("Two", EnumState.class);
        Assert.assertEquals("Two", adapter.getCurrentState());
        Assert.assertEquals(EnumState.Two, entity.stateEnumField);

        adapter = new ReflectionEntityWorkflow<VariousState>(entity, "stateEnumField");
        Assert.assertEquals(entity, adapter.getEntity());
        Assert.assertEquals("Two", adapter.getCurrentState());
        adapter.setCurrentState("One", EnumState.class);
        Assert.assertEquals("One", adapter.getCurrentState());
        Assert.assertEquals(EnumState.One, entity.stateEnumField);
    }

    @Test
    public void WrongArgument1Test() throws WorkflowException {
        exception.expect(IllegalArgumentException.class);
        ReflectionEntityWorkflow<VariousState> target = new ReflectionEntityWorkflow<VariousState>(null, null);
        Assert.assertNotNull(target);
    }

    @Test
    public void WrongArgument2Test() throws WorkflowException
    {
        exception.expect(IllegalArgumentException.class);
        ReflectionEntityWorkflow<VariousState> target = new ReflectionEntityWorkflow<VariousState>(new VariousState(), null);
        Assert.assertNotNull(target);
    }

    @Test
    public void WrongArgument3Test() throws WorkflowException
    {
        exception.expect(WorkflowException.class);
        ReflectionEntityWorkflow<VariousState> target = new ReflectionEntityWorkflow<VariousState>(new VariousState(), "NotexsistentField");
        Assert.assertNotNull(target);
    }

    @Test
    public void WrongArgument4Test() throws WorkflowException
    {
        ReflectionEntityWorkflow<VariousState> target = new ReflectionEntityWorkflow<VariousState>(new VariousState(), "dummy");
        Assert.assertNotNull(target);
    }

    @Test
    public void AdapterFieldsTest() throws WorkflowException
    {
        FPEntity entity = new FPEntity();
        ReflectionEntityWorkflow<FPEntity> adapter = new ReflectionEntityWorkflow<FPEntity>(entity, STATE_FIELD_NAME, StatechartID_FIELD_NAME);
        Assert.assertEquals("1", adapter.getCurrentState());
        Assert.assertEquals(StatechartID, adapter.getStateChartId());
    }

    @Test
    public void AdapterPropertiesTest() throws WorkflowException
    {
        FPEntity entity = new FPEntity();
        ReflectionEntityWorkflow<FPEntity> adapter = new ReflectionEntityWorkflow<FPEntity>(entity, STATE_FIELD_NAME, StatechartID_PROP_NAME);
        Assert.assertEquals("1", adapter.getCurrentState());
        Assert.assertEquals(StatechartID, adapter.getStateChartId());
        adapter = new ReflectionEntityWorkflow<FPEntity>(entity, STATE_FIELD_NAME, StatechartID_PROP_NAME);
    }

    @Test
    public void WrongParamsTest() throws WorkflowException
    {
        //exception.expect(WorkflowException.class);
        ReflectionEntityWorkflow<FPEntity> adapter = new ReflectionEntityWorkflow<FPEntity>(new FPEntity(),STATE_FIELD_NAME, null);
    }

    @Test
    public void WrongParamsTest2() throws WorkflowException
    {
        exception.expect(IllegalArgumentException.class);
        ReflectionEntityWorkflow<FPEntity> adapter = new ReflectionEntityWorkflow<FPEntity>(new FPEntity(), null, null);
    }
}


 



