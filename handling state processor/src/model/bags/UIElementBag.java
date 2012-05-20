package model.bags;

import model.xml.UIElement;

/**
 * Bag class for UI element permissions building
 */
public class UIElementBag {

    /**
     * Constructor
     *
     * @param e UIElement
     * @param s state bag
     */
    public UIElementBag(UIElement e, StateBag s) {
        uiElement = e;
        stateBag = s;
    }

    private UIElement uiElement;
    private StateBag stateBag;

    public UIElement getUIElement() {
        return uiElement;
    }

    public void setUIElement(UIElement uiElement) {
        this.uiElement = uiElement;
    }

    public StateBag getStateBag() {
        return stateBag;
    }

    public void setStateBag(StateBag stateBag) {
        this.stateBag = stateBag;
    }
}
