package core;

import interfaces.IHandler;

public class RuntimeHandler<EntityType, ContextType>
        implements IHandler<EntityType, ContextType> {

    public RuntimeHandler(Action action) {
        _executeAction = action;
    }

    private Action _executeAction;

    public void execute(EntityType entity, ContextType context) {
        if (_executeAction != null) {
            _executeAction.exec(entity, context);
        }
    }

}
