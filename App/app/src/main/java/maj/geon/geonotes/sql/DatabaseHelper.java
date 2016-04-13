package maj.geon.geonotes.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import maj.geon.geonotes.data.Notes;
import maj.geon.geonotes.util.GNConstants;

/**
 * Created by debjeet on 12/4/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

//    private int version = 1;

    private static final String CREATE_NOTES_TABLE="CREATE TABLE "+GNConstants.TABLE_NOTES + "("+ GNConstants.KEY_ID +" INTEGER PRIMARY KEY , "
            +GNConstants.TITLE+" TEXT ,"
            +GNConstants.MESSAGE+" TEXT ,"
            +GNConstants.OWNER+" TEXT ,"
            +GNConstants.RADIUS+ " REAL ,"
            +GNConstants.LATTITUDE + " REAL ,"
            +GNConstants.LONGITUDE + " REAL ,"
            +GNConstants.CREATED_AT + " DATETIME "
            +")";
    private static final String CREATE_USERS_TABLE="CREATE TABLE "+GNConstants.TABLE_USERS + "("+ GNConstants.KEY_ID +" INTEGER PRIMARY KEY , "
            +GNConstants.USERNAME+" TEXT ,"
            +GNConstants.USERCLASS+" TEXT "
            +")";

    public DatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, GNConstants.DATABASE_NAME, factory, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GNConstants.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + GNConstants.TABLE_NOTES);
        // create new tables
        onCreate(db);
    }

    // CURD operations
    public long insertNote(Notes note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(GNConstants.KEY_ID,note.getKey_id() );
        values.put(GNConstants.TITLE, note.getTitle());
        values.put(GNConstants.MESSAGE, note.getMessage());
        values.put(GNConstants.LATTITUDE,note.getLattitude());
        values.put(GNConstants.LONGITUDE,note.getLongitude());
        values.put(GNConstants.RADIUS,note.getRadius());
        values.put(GNConstants.CREATED_AT,note.getCreated_at().toString());
        values.put(GNConstants.OWNER,note.getOwner());
        return db.insert(GNConstants.TABLE_NOTES,null,values);
    }


}
