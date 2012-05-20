package model;/// <summary>
/// State transition info class
/// </summary>

import model.xml.Transition;

public class TransitionInfo {
    /// <summary>
    /// Constructor
    /// </summary>
    public TransitionInfo() {
    }

    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="t">state transition</param>
    public TransitionInfo(Transition t) {
        this.id = t.getId();
        this.title = t.getTitle();
        this.targetState = t.getTargetState();
    }

    private String id = "";

    /// <summary>
    /// Gets transition Id
    /// </summary>
    public String getId() {
        return id;
    }

    private String title = "";

    /// <summary>
    /// Gets transition Title
    /// </summary>
    public String getTitle() {
        return title;
    }

    private String targetState = "";

    /// <summary>
    /// Gets transition's target state
    /// </summary>
    public String getTargetState() {
        return targetState;
    }
}