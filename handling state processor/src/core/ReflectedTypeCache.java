package core;

import exceptions.WorkflowException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class ReflectedTypeCache {

    //http://www.gridshore.nl/2009/10/27/some-notes-on-discovering-your-type-parameter-using-the-reflection-api/
    //http://serdom.eu/ser/2007/03/25/java-generics-instantiating-objects-of-type-parameter-without-using-class-literal
    //http://geekycoder.wordpress.com/2008/06/25/tipjava-instantiating-generic-type-parameter/
    //http://habrahabr.ru/blogs/java/66593/

    private static final Hashtable table = new Hashtable();

    public static Object getInstance(String typeName, Class... typeParameters) throws WorkflowException {
        Class t = getType(typeName);
//        if (null != t
//                && null != t.getTypeParameters()
//                && typeArguments != null
//                && typeArguments.length != 0) {
//            t = t.MakeGenericType(typeArguments);
//        }
        return instantiate(t);
    }

    private static <T> T instantiate(Class<T> t) throws WorkflowException {
        Constructor ctor = null;
        try {
            ctor = t.getConstructor(new Class[]{});
            return (T) ctor.newInstance(new Object[]{});
        } catch (NoSuchMethodException e) {
            throw new WorkflowException("", e);
        } catch (InvocationTargetException e) {
            throw new WorkflowException("", e);
        } catch (InstantiationException e) {
            throw new WorkflowException("", e);
        } catch (IllegalAccessException e) {
            throw new WorkflowException("", e);
        }
    }

    private static Class getType(String typeName) throws WorkflowException {
        Class result = null;
        if (table.contains(typeName))
            result = (Class) table.get(typeName);
        else {
            synchronized (table) {
                if (table.contains(typeName))
                    result = (Class) table.get(typeName);
                else {
                    result = constructHandlerType(typeName);
                    if (result != null) table.put(typeName, result);
                }
            }
        }
        return result;
    }

    private static Class constructHandlerType(String typeName) throws WorkflowException {
        Class res = null;
        try {
            System.out.println("Try to construct class for name '" + typeName + "'");
            //res = (Class<?>) Class.forName(typeName, true, int.class.getClassLoader());
            res = (Class<?>) Class.forName(typeName);
        }
        catch (ClassNotFoundException ex) {
            throw new WorkflowException(String.format("Could not load reflected type '{0}'", typeName), ex);
        }
        return res;
    }
}
