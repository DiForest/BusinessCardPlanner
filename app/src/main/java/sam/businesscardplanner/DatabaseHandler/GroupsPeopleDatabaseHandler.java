package sam.businesscardplanner.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 7/29/2015.
 */
public class GroupsPeopleDatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "plannerDB";
    private static final String TABLE_GROUPS_PEOPLE = "groups_people";

    private static final String TABLE_BUSINESS_CARD = "businessCard";
    private static final String TABLE_BUSINESS_CARD_ID = "id";

    private static final String TABLE_BUSINESS_GROUP = "businessGroup";
    private static final String TABLE_BUSINESS_GROUP_ID = "groupKey";

    private static final String GROUP_PEOPLE_ID = "groupPeopleKey";
    private static final String GROUP_FOREIGN_KEY = "groupForeignKey";
    private static final String PEOPLE_FOREIGN_KEY = "peopleForeignKey";


    public GroupsPeopleDatabaseHandler(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String CREATE_GROUPS_PEOPLE_TABLE = "CREATE TABLE " + TABLE_GROUPS_PEOPLE + " ( " +
               GROUP_PEOPLE_ID + " INTEGER PRIMARY KEY" +
               GROUP_FOREIGN_KEY + "INT, " +
               PEOPLE_FOREIGN_KEY + " INT, " +
               "FOREIGN KEY(" + GROUP_FOREIGN_KEY + ") REFERENCES "
               + TABLE_BUSINESS_GROUP + " ("+ TABLE_BUSINESS_GROUP_ID + "), " +
               "FOREIGN KEY (" + PEOPLE_FOREIGN_KEY + ") REFERENCES "
               + TABLE_BUSINESS_CARD + "(" + TABLE_BUSINESS_CARD_ID +")" + ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS_PEOPLE);
        onCreate(db);
    }

    public void addGroupMember(int groupID, int cardID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_FOREIGN_KEY, groupID);
        values.put(PEOPLE_FOREIGN_KEY, cardID);

        db.insert(TABLE_GROUPS_PEOPLE, null, values);
        db.close();
    }

    /*
    public void removeGroupMember(int groupID, int cardID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = TABLE_GROUPS_PEOPLE
        db.detele(TABLE_GROUPS_PEOPLE, TABLE_BUSINESS_GROUP_ID + "=?,"   )
    }
    */
}
