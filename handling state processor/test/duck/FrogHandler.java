package duck;

import java.lang.reflect.Method;

public class FrogHandler implements InOutHandler<Frog> {

    public FrogHandler(Frog t) {
        target = t;
        targetClass = target.getClass();
    }

    @Override
    public Object invoke(Object p, Method m, Object[] args) throws Throwable {
        Method me = targetClass.getMethod(m.getName(), m.getParameterTypes());
        return me.invoke(getTarget(), args);
    }

    public Frog getTarget() {
        return target;
    }

    private Frog target;
    private Class<?> targetClass;

}
