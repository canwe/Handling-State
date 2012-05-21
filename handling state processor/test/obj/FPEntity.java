package obj;

import tests.ReflectedEntityWorkflowTest;

public class FPEntity {

    public int _State = 1;

    public String stateChartId = ReflectedEntityWorkflowTest.StatechartID;

    public String getStateChartId() {
        return stateChartId;
    }

    public void setStateChartId(String value) {
        stateChartId = value;
    }

    public int get_State() {
        return _State;
    }

    public void set_State(int _State) {
        this._State = _State;
    }
}
