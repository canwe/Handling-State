package duck;

import java.lang.reflect.InvocationHandler;

public interface InOutHandler<T> extends InvocationHandler {

    public T getTarget();

}
