package model.metadata;

import model.xml.StateChart;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.io.File;

import exceptions.WorkflowException;
import utils.BaseDirectory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/// <summary>
/// Statechart cashe
/// </summary>
public class StateChartCache {
    public static final String STATE_CHARTS_FOLDER_APPLICATION_SETTING = "StatechartFolder";
    public static final String STATE_CHARTS_LOAD_ERROR = "Workflow initialization failed. Statechart file '{0}' does not exist.";

    private final Hashtable<String, StateChart> table = new Hashtable<String, StateChart>();
    private final String _dir;

    public StateChartCache(String stateChartDir) {
        if (null == stateChartDir || stateChartDir.isEmpty()) {
            _dir = BaseDirectory.get(); //later AppDomain.CurrentDomain.BaseDirectory;
        } else {
            _dir = stateChartDir;
        }
    }

    public StateChart getStateChart(String _statechartId) throws WorkflowException {
        StateChart result;
        synchronized (table) {
            if (table.contains(_statechartId)) {
                result = table.get(_statechartId);
            } else {
                String path = getPath(_statechartId);
                System.out.println("path = " + path);
                File pathFile = new File(path);
                if (pathFile.exists()) {
                    //using(TextReader reader = new StreamReader(path))
                    //{
                    //    result = Utils.XmlUtility.XmlStr2Obj < StateChart > (reader.ReadToEnd());
                    //    reader.Close();
                    //}


                    try {
                        // create JAXB context and instantiate marshaller
                        JAXBContext context = JAXBContext.newInstance(StateChart.class);
                        Unmarshaller um = context.createUnmarshaller();
                        result = (StateChart)  um.unmarshal(new FileReader(pathFile));
                    } catch (JAXBException e) {
                        e.printStackTrace();
                        throw new WorkflowException(STATE_CHARTS_LOAD_ERROR, new Object[]{path});
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        throw new WorkflowException(STATE_CHARTS_LOAD_ERROR, new Object[]{path});
                    }
                    table.put(_statechartId, result);
                } else {
                    throw new WorkflowException(STATE_CHARTS_LOAD_ERROR, new Object[]{path});
                }
            }
        }
        return result;
    }

    String getPath(String _statechartId) {
        //if (null == _dir || _dir.isEmpty()) {
        //    _dir = getBaseDirectory(); //later AppDomain.CurrentDomain.BaseDirectory;
        //}
        return BaseDirectory.get() + "\\" + _dir + "\\" + _statechartId + ".xml"; //later Path.Combine(_dir, _statechartId + ".xml");
    }

    public void addStatechart(StateChart stateChart) {
        synchronized (table) {
            table.put(stateChart.getId(), stateChart);
        }
    }
}