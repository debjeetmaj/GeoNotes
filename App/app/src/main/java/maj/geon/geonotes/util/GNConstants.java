package maj.geon.geonotes.util;

/**
 * Created by debjeet on 4/4/16.
 */
public class GNConstants {
    public static final String APP_NAME = "GEONOTES";
    public static final String EMAIL = "EMAIL";

    //Database Related Constants
    public static final String DATABASE_NAME = "geonotesDb";

    //Common Columns
    public static final String KEY_ID = "id";
    // Table names
    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_USERS = "users";

    // NOTES Table Columns
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String LATTITUDE = "lattitude";
    public static final String LONGITUDE = "longitude";
    public static final String OWNER = "owner";
    public static final String RADIUS = "RADIUS";
    public static final String CREATED_AT = "created_at";

    // USERS Table Columns
    public static final String USERNAME = "username";
//    public static final String EMAIL = "email";
    public static final String USERCLASS = "userclass";

    // server url
    public static final String server_URL = "http://192.168.0.2:9000";
}