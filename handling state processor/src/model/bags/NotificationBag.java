package model.bags;

import model.xml.Notification;

/**
 * Bag class for notification building
 */
public class NotificationBag {

    /**
     * Constructor
     *
     * @param n notification
     * @param t transition bag
     */
    public NotificationBag(Notification n, TransitionBag t) {
        notification = n;
        transitionBag = t;
    }

    private Notification notification;
    private TransitionBag transitionBag;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public TransitionBag getTransitionBag() {
        return transitionBag;
    }

    public void setTransitionBag(TransitionBag transitionBag) {
        this.transitionBag = transitionBag;
    }
}