package model;

import model.xml.State;

/**
 * State info class
 */
public class StateInfo {
    /**
     * Constructor
     */
    public StateInfo() {
    }

    /**
     * Constructor
     *
     * @param id
     */
    public StateInfo(String id) {
        _id = id;
    }

    /**
    * Constructor
    * @param state appropriate state for coupling info
     */
    public StateInfo(State state) {
        _id = state.getId();
        _title = state.getTitle();
    }

    private String _id;

    /**
     * Gets state Id
     */
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    private String _title;

    /**
     * Gets state Title
     */
    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }
}