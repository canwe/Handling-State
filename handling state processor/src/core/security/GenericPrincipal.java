package core.security;

public class GenericPrincipal {
    
    private final GenericIdentity identity;
    private final String[] roles;

    public GenericPrincipal(GenericIdentity identity, String[] roles) {
        this.identity = identity;
        this.roles = null == roles ? new String[]{} : roles;
    }

    public GenericIdentity getIdentity() {
        return identity;
    }

    public String[] getRoles() {
        String[] dest = new String[roles.length];
        System.arraycopy(roles, 0, dest, 0, roles.length);
        return dest;
    }
    
    public boolean isInRole(String role) {
        for (String r : roles) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
