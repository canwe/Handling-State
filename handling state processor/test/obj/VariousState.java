package obj;

import exceptions.NotImplementedException;

public class VariousState
{
    public Object dummy;
    public Integer stateIntField;
    public String stateStringField;
    public EnumState stateEnumField;

    public Integer getStateIntField() {
        return stateIntField;
    }

    public void setStateIntField(Integer stateIntField) {
        this.stateIntField = stateIntField;
    }

    public String getStateStringField() {
        return stateStringField;
    }

    public void setStateStringField(String stateStringField) {
        this.stateStringField = stateStringField;
    }

    public EnumState getStateEnumField() {
        return stateEnumField;
    }

    public void setStateEnumField(EnumState stateEnumField) {
        this.stateEnumField = stateEnumField;
    }

    public Object getDummy() {
        throw new NotImplementedException();
        //return dummy;
    }

    public void setDummy(Object dummy) {
        this.dummy = dummy;
    }
}
