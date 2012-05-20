package core;/// <summary>
/// Default implementation of the IPermissionProvider interface
/// </summary>
/// <typeparam name="EntityType">entity type</typeparam>
/// <typeparam name="ContextType">call context</typeparam>

import interfaces.IWorkflowAdapter;
import interfaces.IRoleProvider;
import interfaces.IPermissionProvider;
import enums.UIPermissionLevel;
import model.xml.State;
import model.xml.UIElement;
import model.xml.StateChart;
import model.xml.Permission;

public class PermissionProvider<EntityType, ContextType> implements IPermissionProvider {
    IWorkflowAdapter<EntityType> _entity;
    IRoleProvider<EntityType, ContextType> _roleProvider;
    ContextType _context;
    StateChart _statechart;
    UIPermissionLevel _defaultLevel;

    /// <summary>
    /// Constructor
    /// </summary>
    /// <param name="entity">entity</param>
    /// <param name="roleProvider">role provider Interface</param>
    /// <param name="statechart">statechart</param>
    /// <param name="defaultLevel">default permission level</param>
    /// <param name="context">call context</param>
    public PermissionProvider(IWorkflowAdapter<EntityType> entity, IRoleProvider<EntityType, ContextType> roleProvider, StateChart statechart, UIPermissionLevel defaultLevel, ContextType context) {
        _entity = entity;
        _roleProvider = roleProvider;
        _context = context;
        _statechart = statechart;
        _defaultLevel = defaultLevel;
    }

    /// <summary>
    /// Returns UIPermissionLevel for given UI element name
    /// </summary>
    /// <param name="elementName">UI element name</param>
    /// <returns>UI permission level</returns>
    public UIPermissionLevel Demand(String elementName) {
        State state = _statechart.getState(_entity.getCurrentState());
        for (UIElement e : state.getUIPermissions()) {
            if (null != e.getName() && e.getName().equals(elementName)) {
                UIPermissionLevel res = UIPermissionLevel.HIDDEN;
                for (Permission p : e.getPermissions()) {
                    if ("*".equals(p.getRole())) res = CalcPermisson(res, p.getLevel());
                    else if (_roleProvider.isInRole(p.getRole(), (Class<EntityType>) _entity.getEntity().getClass(), (Class<ContextType>) _context.getClass()))
                        res = CalcPermisson(res, p.getLevel());
                }
                return res;
            }
        }
        return _defaultLevel;
    }

    private UIPermissionLevel CalcPermisson(UIPermissionLevel l1, UIPermissionLevel l2) {
        return l1.getLevel() < l2.getLevel() ? l2 : l1;
    }
}