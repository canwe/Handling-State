package core.security;

import utils.Strings;

public class GenericIdentity {
    
    private final String userName;

    private GenericIdentity(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    
    public static GenericIdentity forUser(String userName) {
        if (Strings.isEmpty(userName)) {
            throw new IllegalArgumentException("User cannot be empty");
        }
        return new GenericIdentity(userName);
    }
}
