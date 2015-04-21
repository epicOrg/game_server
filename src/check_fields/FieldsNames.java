package check_fields;

/**
 * @author Noris
 * @version 12
 * @date 2015/03/30
 */

public class FieldsNames {

    /**
     * ************************************************************************
     * SERVICE NAMES
     * ************************************************************************
     */

    public static final String SERVICE = "service";
    public static final String SERVICE_TYPE = "serviceType";

    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String CALL = "call";
    public static final String ROOMS = "rooms";
    public static final String CURRENT_ROOM = "currentRoom";
    public static final String UNKNOWN = "unknown";

    /**
     * ************************************************************************
     * FIELDS NAMES
     * ************************************************************************
     */

    // Common
    public static final String IP_ADDRESS = "ipAddress";
    public static final String RESULT = "result";
    public static final String HASHCODE = "hashcode";
    public static final String LIST = "list";

    // Register
    public static final String EMAIL = "email";

    // Register, Login
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    // Call
    public static final String CALLER = "caller";
    public static final String CALLEE = "callee";
    public static final String PORT = "port";

    /**
     * ************************************************************************
     * ERROR NAMES
     * ************************************************************************
     */

    public static final String NO_ERRORS = "noErrors";
    public static final String ERRORS = "errors";

    //Common
    public static final String INVALID = "invalidField";
    public static final String OFFLINE = "offline";
    public static final String SERVER_ERROR = "serverError";

    // Register
    public static final String REGISTER_SHORT = "short";
    public static final String REGISTER_LONG = "long";
    public static final String REGISTER_INVALID_CHAR = "invalidChar";
    public static final String REGISTER_INVALID_DOMAIN = "invalidDomain";
    public static final String REGISTER_SERVER_ERROR = "serverError";
    public static final String REGISTER_ALREADY_USED = "alreadyUsed";

    // Rooms/Room
    //Others
    public static final String ROOM_MAX_PLAYERS = "maxPlayers";

    // Rooms
    // Service Type
    public static final String ROOMS_LIST = "list";
    public static final String ROOM_CREATE = "create";
    public static final String ROOM_JOIN = "join";
    // Others
    public static final String ROOM_NAME = "name";
    public static final String ROOM_CURRENT_PLAYERS = "currentPlayers";
    public static final String ROOM_CREATE_ERROR_INVALID_NAME = "createErrorInvalidname";
    public static final String ROOM_CREATE_ERROR_ALREADY_PRESENT = "createErrorAlreadyPresent";
    public static final String ROOM_JOIN_ERROR_FULL = "joinErrorFull";
    public static final String ROOM_JOIN_OTHERS = "joinErrorOthers";

    // Room
    // Service Type
    public static final String ROOM_PLAYER_LIST = "playerList";
    public static final String ROOM_START = "start";
    // Others

}