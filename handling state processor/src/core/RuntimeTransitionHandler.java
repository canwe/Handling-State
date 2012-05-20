package core;

import interfaces.ITransitionHandler;

public class RuntimeTransitionHandler<EntityType, ContextType>
  implements ITransitionHandler<EntityType, ContextType> {
    
    public RuntimeTransitionHandler(Action preValidateAction,
                                    Action executeAction,
                                    Func confirmStateChangeFunc) {
        this.preValidateAction = preValidateAction;
        this.executeAction = executeAction;
        this.confirmStateChangeFunc = confirmStateChangeFunc;
    }

    Action preValidateAction;
    Action executeAction;
    Func confirmStateChangeFunc;

    public void preValidate(EntityType entity) {
        if (preValidateAction != null) {
            preValidateAction.exec(entity);
        }
    }

    public Boolean confirmStateChange(EntityType entity, String targetState) {
        if (confirmStateChangeFunc != null) {
            return confirmStateChangeFunc.exec(entity, targetState);
        }
        else {
            return true;
        }
    }

    public void execute(EntityType entity, ContextType context) {
        if (executeAction != null) {
            executeAction.exec(entity, context);
        }
    }

}
