package tests;

import junit.framework.Assert;
import model.xml.*;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class StateChartTest
{
    StateChart GetStatechart()
    {
        StateChart sc = new StateChart();
        sc.setId("TestStatechart");
        State[] states = new State[2];
        
        State state1 = new State();
        state1.setId("One");
        state1.setTitle("First State");
        state1.setOnEnterHandler("OneEnterHandler");
        state1.setOnExitHandler("OneExitHandler");
        state1.setDescription(new Description(){{setSource("src"); setText("description");}});
        
        Transition[] transitions = new Transition[2];
        Transition tr1 = new Transition();
        tr1.setId("T1");
        tr1.setTitle("To Second State");
        tr1.setTargetState("Two");
        tr1.setHandler("T1Handler");
        tr1.setPerformers(new ArrayList<Performer>(2){{add(new Performer(){{setRole("Role1");}}); add(new Performer(){{setRole("Role2");}});}});
        transitions[0] = tr1;

        Transition tr2 = new Transition();
        tr2.setId("T2");
        tr2.setTitle("Roundtrip");
        tr2.setTargetState("One");
        tr2.setHandler("T2Handler");
        transitions[1] = tr2;

        state1.setTransitions(Arrays.asList(transitions));

        State state2 = new State();
        state2.setId("Two");
        state2.setTitle("Second State");
        state2.setOnEnterHandler("OneEnterHandler");
        state2.setOnExitHandler("OneExitHandler");
        state2.setDescription(new Description(){{setSource("src"); setText("description");}});

        transitions = new Transition[2];
        tr1 = new Transition();
        tr1.setId("T3");
        tr1.setTitle("To First State");
        tr1.setTargetState("One");
        tr1.setHandler("T3Handler");
        transitions[0] = tr1;

        tr2 = new Transition();
        tr2.setId("T4");
        tr2.setTitle("Roundtrip");
        tr2.setTargetState("Two");
        tr2.setHandler("T2Handler");
        transitions[1] = tr2;

        state2.setTransitions(Arrays.asList(transitions));
        states[0] = state1;
        states[1] = state2;

        sc.setStates(Arrays.asList(states));
        
        return sc;
    }
    
    @Test
    public void StatechartSerializationTest() throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(StateChart.class);
        StateChart s1 = GetStatechart();

        final StringWriter writer = new StringWriter();
        // this is where we convert the object to XML
        context.createMarshaller().marshal(s1, writer);

        System.out.println(writer.toString());

        // this is where we convert the XML to object
        final StateChart s2 = (StateChart) context.createUnmarshaller().unmarshal(new StringReader(writer.toString()));

        Assert.assertEquals(s1.getId(), s2.getId());
        Assert.assertEquals(s1.getStates().size(), s2.getStates().size());
        Assert.assertEquals(s1.getStates().get(0).getId(), s2.getStates().get(0).getId());
        Assert.assertEquals(s1.getStates().get(0).getTitle(), s2.getStates().get(0).getTitle());
        Assert.assertEquals(s1.getStates().get(0).isInitial(), s2.getStates().get(0).isInitial());
        Assert.assertEquals(s1.getStates().get(0).getDescription().getSource(), s2.getStates().get(0).getDescription().getSource());
        Assert.assertEquals(s1.getStates().get(0).getDescription().getText(), s2.getStates().get(0).getDescription().getText());
        Assert.assertEquals(s1.getStates().get(0).getOnEnterHandler(), s2.getStates().get(0).getOnEnterHandler());
        Assert.assertEquals(s1.getStates().get(0).getOnExitHandler(), s2.getStates().get(0).getOnExitHandler());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().size(), s2.getStates().get(0).getTransitions().size());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().get(0).getId(), s2.getStates().get(0).getTransitions().get(0).getId());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().get(0).getTitle(), s2.getStates().get(0).getTransitions().get(0).getTitle());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().get(0).getTargetState(), s2.getStates().get(0).getTransitions().get(0).getTargetState());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().get(0).getHandler(), s2.getStates().get(0).getTransitions().get(0).getHandler());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().get(0).getPerformers().size(), s2.getStates().get(0).getTransitions().get(0).getPerformers().size());
        Assert.assertEquals(s1.getStates().get(0).getTransitions().get(0).getPerformers().get(0).getRole(), s2.getStates().get(0).getTransitions().get(0).getPerformers().get(0).getRole());
    }

}