package conf;

import javax.xml.bind.annotation.*;
import java.util.Collection;

@XmlRootElement(name = "EntityWorkflows")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "EntityWorkflows")
public class EntityWorkflowDatas {

    @XmlElement(name = "EntityWorkflowData")
    private Collection<EntityWorkflowData> entityWorkflowDatas;

    public Collection<EntityWorkflowData> getEntityWorkflowDatas() {
        return entityWorkflowDatas;
    }

    public void setEntityWorkflowDatas(Collection<EntityWorkflowData> value) {
        this.entityWorkflowDatas = value;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entityWorkflowDatas == null) ? 0 : entityWorkflowDatas.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityWorkflowDatas other = (EntityWorkflowDatas) obj;
        if (entityWorkflowDatas == null) {
            if (other.entityWorkflowDatas != null)
                return false;
        } else if (!entityWorkflowDatas.equals(other.entityWorkflowDatas))
            return false;
        return true;
    }

    public String toString()
    {
        final String TAB = "    ";

        String retValue = "";

        retValue = "People ( "
                + super.toString() + TAB
                + "people = " + this.entityWorkflowDatas + TAB
                + " )";

        return retValue;
    }

}
