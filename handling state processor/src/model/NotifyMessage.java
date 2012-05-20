package model;

/**
 * State transition notification message
 */
public class NotifyMessage {

    /// <summary>
    /// Represents the method that will handle send notification message event
    /// </summary>
    /// <param name="message">sending message</param>
    //public delegate void SendMessageEventHandler(NotifyMessage message);

    // To address string
    private String _to;

    // CC address string
    private String _cc;

    // Message subject
    private String _subject;

    // Message body
    private String _body;

    /**
     * Constructor
     *
     * @param to     "to" address
     * @param cc     "cc" address
     * @param subj   subject
     * @param body   body
     */
    public NotifyMessage(String to, String cc, String subj, String body) {
        if (null == to || to.isEmpty()) {
            throw new IllegalArgumentException("to");
        }
        _to = to;
        _cc = cc;
        _subject = subj;
        _body = body;
    }

    public String getTo() {
        return _to;
    }

    public void setTo(String _to) {
        this._to = _to;
    }

    public String getCc() {
        return _cc;
    }

    public void setCc(String _cc) {
        this._cc = _cc;
    }

    public String getSubject() {
        return _subject;
    }

    public void setSubject(String _subject) {
        this._subject = _subject;
    }

    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }
}