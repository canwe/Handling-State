package conf;

import enums.UIPermissionLevel;
import exceptions.WorkflowException;
import utils.BaseDirectory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/// <summary>
/// Configuration data class
/// </summary>
@XmlRootElement(name = "HConf") //, namespace = HConfData.xmlNamespace
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "HConf")
public class HConfData {
    /**
     * Xml namespace for serialization
     */
    public static final String xmlNamespace = "urn:Listma:configuration";

    public static final String HCONF_LOAD_ERROR_BAD_XML = "HConfData initialization failed. Bad xml '{0}'.";

    public static final String HCONF_LOAD_ERROR_BAD_DIR = "HConfData initialization failed. Bad dir '{0}'.";

    /// <summary>
    /// Initialized instance
    /// </summary>
    public HConfData() {
        //this.stateChartDir = "./";
        this.stateChartDir = "";
    }

    /**
     * Initialized instance with location of statecharts directory
     *
     * @param stateChartDir dir
     */
    public HConfData(String stateChartDir) {
        this.stateChartDir = stateChartDir;
    }

    /**
     * Statecharts directory
     */
    @XmlAttribute
    public String stateChartDir = "";

    /**
     * Default permissions level
     */
    @XmlAttribute
    public UIPermissionLevel DefaultPermissionLevel = UIPermissionLevel.HIDDEN;

    List<EntityWorkflowData> _EntitiesData = new ArrayList<EntityWorkflowData>();

    /// <summary>
    /// Entities workflow configuration data
    /// </summary>
    private List<EntityWorkflowData> entitiesData;

    @XmlElementWrapper(name = "EntityWorkflows") //, namespace = HConfData.xmlNamespace
    @XmlElement(name = "EntityWorkflowData") //, namespace = HConfData.xmlNamespace
    public List<EntityWorkflowData> getEntitiesData() {
        return entitiesData;
    }

    public void setEntitiesData(List<EntityWorkflowData> value) {
        this.entitiesData = value;
        _EntitiesData = new ArrayList<EntityWorkflowData>();
        if (value != null) {
            Collections.addAll(_EntitiesData, value.toArray(new EntityWorkflowData[value.size()]));
            //Collections.addAll(entitiesData, value.toArray(new EntityWorkflowData[value.size()]));
        }
        _Entities = null;
    }

    public void addEntitiesData(EntityWorkflowData item) {
        entitiesData.add(item);
    }

    public void removeEntitiesData(EntityWorkflowData item) {
        entitiesData.remove(item);
    }

    List<EntityWorkflow> _Entities = null;

    public EntityWorkflow[] getEntities() {
        if (_Entities == null) {
            _Entities = new ArrayList<EntityWorkflow>();
            for (EntityWorkflowData data : getEntitiesData())
                _Entities.add(new EntityWorkflow(data));
        }
        return _Entities.toArray(new EntityWorkflow[_Entities.size()]);
    }

    /// <summary>
    /// Gets default configuration file location
    /// </summary>
    /// <returns></returns>
    public static String getDefaultConfigLocation() {
        return BaseDirectory.get() + "HConf.xml";
    }

    /// <summary>
    /// Loads configuration data from given file
    /// </summary>
    /// <param name="fileName"></param>
    /// <returns></returns>
    public static HConfData Load(String fileName) throws WorkflowException {
        HConfData result = null;
        File file = new File(fileName);
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            try {
                // create JAXB context and instantiate marshaller
                JAXBContext context = JAXBContext.newInstance("conf");
                Unmarshaller um = context.createUnmarshaller();
                result = (HConfData)  um.unmarshal(new FileReader(file));
            } catch (JAXBException e) {
                e.printStackTrace();
                throw new WorkflowException(HCONF_LOAD_ERROR_BAD_XML, new Object[]{fileName});
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new WorkflowException(HCONF_LOAD_ERROR_BAD_DIR, new Object[]{fileName});
            }
        } else {
            throw new WorkflowException("Listma configuration failed. Config file '{0}' does not exist.", new Object[]{fileName});
        }
        return result;
    }

    static HConfData ConstructFromXml(String xml) throws WorkflowException {
        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(HConfData.class);
            Unmarshaller um = context.createUnmarshaller();
            HConfData result = (HConfData) um.unmarshal(new ByteArrayInputStream(xml.getBytes()));
            return result;
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new WorkflowException(HCONF_LOAD_ERROR_BAD_XML, new Object[]{xml});
        }
    }

    public EntityWorkflow getEntityWorkflow(String entityType) throws WorkflowException {
        for (EntityWorkflow e : getEntities()) {
            if (null != entityType && entityType.equals(e.getEntityType())) {
                return e;
            }
        }
        throw new WorkflowException("Entity type '{0}' is not registered.",
                new Object[]{entityType});
    }

    public String getEntityWorkflowFactoryName(String entityType) throws WorkflowException {
        for (EntityWorkflow e : getEntities()) {
            System.out.println("e.getEntityType() = " + e.getEntityType());
            if (null != entityType && entityType.equals(e.getEntityType())) {
                return e.getWorkflowFactoryClass();
            }
        }
        throw new WorkflowException("Entity type '{0}' is not registered.",
                new Object[]{entityType});
    }

    public void addEntityWorkflow(EntityWorkflow entityWorkflow) throws WorkflowException {
        if (entityWorkflow == null) {
            throw new IllegalArgumentException("orderWorkflow");
        }
        if (isEntityTypeRegistered(entityWorkflow.getEntityType())) {
            throw new WorkflowException("Workflow for entity type '{0}' already registered.", new Object[]{entityWorkflow.getEntityType()});
        }
        _Entities.add(entityWorkflow);
        _EntitiesData.add(entityWorkflow._data);
    }

    public String GetRoleProviderName(String entityType)
            throws WorkflowException {
        for (EntityWorkflow e : getEntities()) {
            if (null != entityType && entityType.equals(e.getEntityType())) {
                return e.getRoleProviderClass();
            }
        }
        throw new WorkflowException("Entity type '{0}' is not registered.",
                new Object[]{entityType});
    }

    public Boolean isEntityTypeRegistered(String entityType) {
        for (EntityWorkflow e : getEntities()) {
            if (null != entityType && entityType.equals(e.getEntityType())) {
                return true;
            }
        }
        return false;
    }
}
