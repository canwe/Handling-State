package duck;


import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DuckConverter {


    public static boolean CanCast(Class<?> i, Object o) {
        Class<?> c = o.getClass();
        for (Method method : i.getMethods()) {
            try {
                c.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
        return true;
    }


    @SuppressWarnings("unchecked")
    public static <I> I CastDuck(Class<I> c, Object o) {
        if (!CanCast(c, o)) {
            throw new ClassCastException();
        }
        Object proxy = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{c}, new DuckHandler((Duck)o));
        return (I) proxy;
    }


    @SuppressWarnings("unchecked")
    public static <I> I CastFrog(Class<I> c, Object o) {
        if (!CanCast(c, o)) {
            throw new ClassCastException();
        }
        Object proxy = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{c}, new FrogHandler((Frog)o));
        return (I) proxy;
    }


    @SuppressWarnings("unchecked")
    public static <C extends InOutHandler> Object UnCast(Class<C> c, Object proxyObject) {
        C h = (C) Proxy.getInvocationHandler(proxyObject);
        return h.getTarget();
    }


    public static void main(String[] args) {
        CastDuck(IQuackable.class, new Duck()).quack();
        CastFrog(IQuackable.class, new Frog()).quack();

        System.out.println(UnCast(DuckHandler.class, CastDuck(IQuackable.class, new Duck())));
    }


}
