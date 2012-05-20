package core;

import model.NotifyMessage;
import model.xml.NotifyTemplate;
import interfaces.INotifyHandler;

public class RuntimeNotifyHandler<EntityType, ContextType>
           implements INotifyHandler<EntityType, ContextType> {

    public RuntimeNotifyHandler(Func resolveAddressFunc,
                                Action parseMessageTemplateAction) {
        this.resolveAddressFunc = resolveAddressFunc;
        this.parseMessageTemplateAction = parseMessageTemplateAction;
    }

    Func resolveAddressFunc;
    Action parseMessageTemplateAction;


    public String[] resolveAddress(String role,
                                   EntityType entity,
                                   ContextType context) {
        if (this.resolveAddressFunc != null)
            return resolveAddressFunc.exec(role, entity, context);
        else
            return new String[]{};
    }

    public void parseMessageTemplate(NotifyMessage message,
                                     NotifyTemplate template,
                                     EntityType entity,
                                     ContextType context) {
        if (this.parseMessageTemplateAction != null)
            parseMessageTemplateAction.exec(message, template, entity, context);
    }

}
