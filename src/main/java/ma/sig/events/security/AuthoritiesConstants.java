package ma.sig.events.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    public static final String EVENT_USER = "EVENT_USER";
    public static final String EVENT_ADMIN = "EVENT_ADMIN";
    public static final String CAN_ACC_DELETE = "CAN_ACC_DELETE";
    public static final String CAN_ACC_VALIDATE = "CAN_ACC_VALIDATE";
    public static final String CAN_ACC_AV_SEARCH = "CAN_ACC_AV_SEARCH";
    public static final String CAN_ACC_MASS_UPDATE = "CAN_ACC_MASS_UPDATE";
    public static final String CAN_ACC_IMPORT = "CAN_ACC_IMPORT";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
