package org.wso2.carbon.sample.xacml;

public class AuthorizationException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -6628323617671786989L;

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
