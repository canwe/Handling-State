package duck;

import java.lang.reflect.Method;

class DuckHandler implements InOutHandler<Duck> {

    public DuckHandler(Duck t) {
        target = t;
        targetClass = target.getClass();
    }

    @Override
    public Object invoke(Object p, Method m, Object[] args) throws Throwable {
        Method me = targetClass.getMethod(m.getName(), m.getParameterTypes());
        return me.invoke(getTarget(), args);
    }

    public Duck getTarget() {
        return target;
    }

    private Duck target;
    private Class<?> targetClass;

}
