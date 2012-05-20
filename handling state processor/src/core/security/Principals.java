package core.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Principals {

    private static Map<Thread, GenericPrincipal> principals = new ConcurrentHashMap<Thread, GenericPrincipal> ();

    public static void setCurrentPrincipal(GenericPrincipal principal) {
        principals.put(Thread.currentThread(), principal);
    }

    public static GenericPrincipal getCurrentPrincipal() {
        return principals.get(Thread.currentThread());
    }
}
