package ma.sig.events.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    public static final String EVENT_USER = "EVENT_USER";
    public static final String EVENT_ADMIN = "EVENT_ADMIN";
    public static final String EVENT_USER_MOBILE = "EVENT_USER_MOBILE";
    public static final String CAN_ACC_DELETE = "CAN_ACC_DELETE";
    public static final String CAN_ACC_VALIDATE = "CAN_ACC_VALIDATE";
    public static final String CAN_ACC_AV_SEARCH = "CAN_ACC_AV_SEARCH";
    public static final String CAN_ACC_MASS_UPDATE = "CAN_ACC_MASS_UPDATE";
    public static final String CAN_ACC_MASS_PRINT = "CAN_ACC_MASS_PRINT";
    public static final String CAN_ACC_IMPORT = "CAN_ACC_IMPORT";
    public static final String CAN_ACC_EXPORT = "CAN_ACC_EXPORT";
    public static final String CAN_ACC_ACTIVAT = "CAN_ACC_ACTIVAT";
    public static final String CAN_ACC_PRINT = "CAN_ACC_PRINT";
    public static final String CAN_ACC_REPRINT = "CAN_ACC_REPRINT";
    public static final String CAN_ACC_UPD_STATUS = "CAN_ACC_UPD_STATUS";
    public static final String CAN_ACC_SHOW_NOTES = "CAN_ACC_SHOW_NOTES";
    public static final String CAN_ACC_SHOW_INFOSUP = "CAN_ACC_SHOW_INFOSUP";
    public static final String CAN_ACC_SHOW_ATTACHMENT = "CAN_ACC_SHOW_ATTACHMENT";
    public static final String CAN_ACC_SHOW_ARCH_PHOTO = "CAN_ACC_SHOW_ARCH_PHOTO";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
