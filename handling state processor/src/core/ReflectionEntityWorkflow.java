package core;

import exceptions.NotImplementedException;
import exceptions.WorkflowException;
import interfaces.IWorkflowAdapter;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectionEntityWorkflow<T> implements IWorkflowAdapter<T> {

    private Member _statechartMember;
    private Member _stateMember;
    private String _statechartId;
    private String _entityType;
    private T _entity;

    public ReflectionEntityWorkflow(T entity,
                                    String stateMemberName)
            throws WorkflowException {
        if (null == entity) {
            throw new IllegalArgumentException("entity");
        }
        if (null == stateMemberName || stateMemberName.isEmpty()) {
            throw new IllegalArgumentException("stateMemberName");
        }
        _entity = entity;
        List<Member> list = getMemberInfo(entity.getClass(), stateMemberName);
        if (list.size() == 0) {
            throw new WorkflowException("Wrong state mapping. Member '{0}.{0}' is not exist.",
                    new Object[]{entity.getClass().getName(), stateMemberName});
        }
        _stateMember = list.get(0);
        ValidateMemberInfo(_stateMember);
        _entityType = _entity.getClass().getName();
    }

    public ReflectionEntityWorkflow(T entity,
                                    String stateMemberName,
                                    String statechartIdMemberName)
            throws WorkflowException {
        this(entity, stateMemberName);
        List<Member> list = getMemberInfo(entity.getClass(), statechartIdMemberName);
        if (list.size() > 0) {
            _statechartMember = list.get(0);
            ValidateMemberInfo(_statechartMember);
        }
    }

    private void ValidateMemberInfo(Member member)
            throws WorkflowException {
        if (!(member instanceof Field)) {
            throw new WorkflowException("Wrong state mapping '{0}'. Must be field or property name.", _stateMember.getName());
        }
    }


    public T getEntity() {
        return _entity;
    }

    public String getCurrentState() {
        if (_stateMember instanceof Field) {
            try {
                return ((Field) _stateMember).get(_entity).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public void setCurrentState(String state) {
        if (_stateMember instanceof Field) {
            Field field = (Field) _stateMember;
            try {
                if (field.isEnumConstant() || field.getType().isEnum()) {
                    //Class<? extends Enum<?>> cls = (Class<? extends Enum<?>>) field.getType();
                    //field.set(_entity, Enum.valueOf(cls, state));
                    throw new NotImplementedException();
                } else {
                    //field.set(_entity, Convert.ChangeType(state, field.getType()));
                    try {
                        Constructor<?> constructor = field.getType().getConstructor(String.class);
                        if (null != constructor) {
                            field.set(_entity, constructor.newInstance(state));
                        }
                        return;
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //field.set(_entity, state.getClass().asSubclass(field.getType()).cast(state));
                    field.set(_entity, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T extends Enum<T>> void setCurrentState(String state, Class<T> enumClass) {
        if (_stateMember instanceof Field) {
            Field field = (Field) _stateMember;
            try {
                if (field.isEnumConstant() || field.getType().isEnum()) {
                    field.set(_entity, Enum.valueOf(enumClass, state));
                } else {
                    throw new NotImplementedException();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStateChartId(String stateChartId) {
        if (_statechartMember != null) {
            if (_statechartMember instanceof Field) {
                try {
                    ((Field) _statechartMember).set(getEntity(), stateChartId);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else
            _statechartId = stateChartId;

    }

    public String getStateChartId() {
        if (_statechartMember == null) return _statechartId;
        if (_statechartMember instanceof Field) {
            try {
                return (String) ((Field) _statechartMember).get(getEntity());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public String getEntityType() {
        return _entityType;
    }

    public void setEntityType(String entityType) {
        _entityType = entityType;
    }

    private List<Member> getMemberInfo(Class clazz, String memberName) {
        if (null == clazz) {
            return new ArrayList<Member>();
        } else {
            Method[] methods = clazz.getMethods();
            Field[] fields = clazz.getFields();
            Constructor[] constructors = clazz.getConstructors();
            List<Member> members = Collections.synchronizedList(new ArrayList<Member>());
            for (Method method : methods) {
                if (method.getName().equals(memberName)) {
                    members.add(method);
                }
            }
            for (Field field : fields) {
                if (field.getName().equals(memberName)) {
                    members.add(field);
                }
            }
            for (Constructor constructor : constructors) {
                if (constructor.getName().equals(memberName)) {
                    members.add(constructor);
                }
            }
            return members;
        }
    }

}
