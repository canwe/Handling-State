package interfaces;

import model.NotifyMessage;
import model.xml.NotifyTemplate;

/**
 * Provides interface for statechart transition's notification messages handling
 * type EntityType is entity type
 * type ContextType is call context type
 */
public interface INotifyHandler<EntityType, ContextType> {
    /**
     * Resolves recipient's addresses which are specified for roles
     *
     * @param role    role name
     * @param entity  entity
     * @param context call context
     * @return resulting addresses array
     */
    String[] resolveAddress(String role,
                            EntityType entity,
                            ContextType context);

    /**
     * Parses notification message subject and body templates
     *
     * @param message  resulting message
     * @param template source message template
     * @param entity   entity
     * @param context  call context
     */
    void parseMessageTemplate(NotifyMessage message,
                              NotifyTemplate template,
                              EntityType entity,
                              ContextType context);
}
