package check_fields;

/**
 * @author Noris
 * @version 17
 * @date 2015/03/30
 */

public class FieldsNames {

    /**
     * ************************************************************************
     * SERVICES NAMES
     * ************************************************************************
     */

    public static final String SERVICE = "service";
    public static final String SERVICE_TYPE = "serviceType";

    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String CALL = "call";
    public static final String ROOMS = "rooms";
    public static final String CURRENT_ROOM = "currentRoom";
    public static final String GAME = "game";
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
    public static final String USERNAME = "username";

    // Register
    public static final String EMAIL = "email";

    // Register, Login
    public static final String PASSWORD = "password";

    // Call
    public static final String CALLER = "caller";
    public static final String CALLEE = "callee";
    public static final String PORT = "port";

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
    public static final String ROOM_NAME = "name";

    // Rooms
    // Service Type
    public static final String ROOMS_LIST = "list";
    public static final String ROOM_CREATE = "create";
    public static final String ROOM_JOIN = "join";
    // Others
    public static final String ROOM_CURRENT_PLAYERS = "currentPlayers";
    public static final String ROOM_CREATE_ERROR_INVALID_NAME = "createErrorInvalidname";
    public static final String ROOM_CREATE_ERROR_ALREADY_PRESENT = "createErrorAlreadyPresent";
    public static final String ROOM_JOIN_ERROR_FULL = "joinErrorFull";
    public static final String ROOM_JOIN_OTHERS = "joinErrorOthers";

    // Room
    // Service Type
    public static final String ROOM_PLAYER_LIST = "playerList";
    public static final String ROOM_ACTIONS = "action";
    // Others
    public static final String ROOM_TEAM = "team";
    public static final String ROOM_TEAM_COLOR = "teamColor";
    public static final String ROOM_ACTION = "action";
    public static final String ROOM_START = "start";
    public static final String ROOM_EXIT = "exit";

    // Game
    // Service Type
    public static final String GAME_STATUS = "status";
    public static final String GAME_MAP = "map";
    public static final String GAME_POSITIONS = "positions";
    // Others
    public static final String GAME_READY = "ready";
    public static final String GAME_GO = "go";
    public static final String GAME_WIDTH = "width";
    public static final String GAME_LENGTH = "length";
    public static final String GAME_HEIGHT = "height";
    public static final String GAME_ITEMS = "items";
    public static final String GAME_OBJECT = "object";
    public static final String GAME_TEXTURE = "texture";
    public static final String GAME_POSITION = "position";
    public static final String GAME_SIZE = "size";
    public static final String GAME_X = "x";
    public static final String GAME_Y = "y";
    public static final String GAME_Z = "z";
    public static final String GAME_PLAYER_NAME = "playerName";

    /**
     * ************************************************************************
     * ERRORS NAMES
     * ************************************************************************
     */

    public static final String NO_ERRORS = "noErrors";
    public static final String ERRORS = "errors";

    //Commons
    public static final String INVALID = "invalidField";
    public static final String OFFLINE = "offline";
    public static final String SERVER_ERROR = "serverError";
    public static final String MISSING_FIELD = "missingField";
}