package ma.sig.events.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    public static final String EVENT_USER = "EVENT_USER";
    public static final String EVENT_ADMIN = "EVENT_ADMIN";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
