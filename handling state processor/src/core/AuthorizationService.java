package core;

import exceptions.WorkflowException;
import interfaces.IConfigProvider;
import interfaces.IWorkflowAdapter;
import interfaces.IRoleProvider;
import model.xml.Performer;
import model.xml.Transition;

public class AuthorizationService {

    public static <T, C> Boolean authorize(IWorkflowAdapter<T> entity,
                                           Transition t,
                                           IConfigProvider _config,
                                           C context) throws WorkflowException {

        if (null == t.getPerformers() || t.getPerformers().size() == 0) {
            return true;
        }
        Class<T> entityTypeClass = (Class<T>) entity.getEntity().getClass(); 

        IRoleProvider<T, C> roleProvider;
        if (null == context) {
            roleProvider = _config.getRoleProvider(entity.getEntityType(), entityTypeClass, null);
        } else {
            roleProvider = _config.getRoleProvider(entity.getEntityType(), entityTypeClass, (Class<C>) context.getClass());
        }

        for (Performer p : t.getPerformers()) {
            if (null == context) {
                if (roleProvider.isInRole(p.getRole(), entityTypeClass, null)) {
                    return true;
                }
            } else {
                if (roleProvider.isInRole(p.getRole(), entityTypeClass, (Class<C>) context.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }
}