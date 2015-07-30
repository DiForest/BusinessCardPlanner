package sam.businesscardplanner.BusinessGroup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 7/29/2015.
 */

    /**
     * Created by Administrator on 7/27/2015.
     */
    public class GroupsDatabaseHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String  DATABASE_NAME = "plannerDB";
        private static final String TABLE_BUSINESS_GROUP ="businessGroup";


        private static final String GROUP_KEY = "groupKey";
        private static final String KEY_GROUP_NAME = "groupName";
        private static final String KEY_GROUP_DESCRIPTION = "groupDescription";
        private static final String KEY_GROUP_CREATED_DATE = "groupCreatedDate";

        public GroupsDatabaseHandler(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db){
            String CREATE_BUSINESS_GROUP_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_GROUP + "(" +
                    GROUP_KEY + " INTEGER PRIMARY KEY" +
                    KEY_GROUP_NAME + " TEXT,"+
                    KEY_GROUP_DESCRIPTION + " TEXT," +
                    KEY_GROUP_CREATED_DATE + " TEXT" + ")";
            db.execSQL(CREATE_BUSINESS_GROUP_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_GROUP);
            onCreate(db);
        }

        public void addGroup(BusinessGroups group){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_GROUP_NAME, group._group_name);
            values.put(KEY_GROUP_DESCRIPTION, group._group_description);
            values.put(KEY_GROUP_CREATED_DATE, group._group_created_date);

            db.insert(TABLE_BUSINESS_GROUP, null, values);
            db.close();
        }

        public List<BusinessGroups> getAllGroup(){
            List<BusinessGroups> groupsList =  new ArrayList<BusinessGroups>();
            String selectQuery = "SELECT * FROM businessGroup ORDER BY name";

            SQLiteDatabase db =this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        BusinessGroups groups = new BusinessGroups();
                        groups.set_id(Integer.parseInt(cursor.getString(0)));
                        groups.set_name(cursor.getString(1));
                        groups.set_description(cursor.getString(2));
                        groups.set_created_date(cursor.getString(3));

                        groupsList.add(groups);
                    }while(cursor.moveToNext());
                }
            }
            db.close();
            return groupsList;
        }

        public BusinessGroups getGroup(int id){
            String query = "SELECT * FROM " + TABLE_BUSINESS_GROUP +
                    " WHERE " + GROUP_KEY + " = " + id;
            BusinessGroups groups = new BusinessGroups();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if(cursor != null){
                cursor.moveToFirst();

                groups.set_name(cursor.getString(1));
                groups.set_description(cursor.getString(2));
                groups.set_created_date(cursor.getString(3));
            }
            return groups;
        }

        public int updateGroups(BusinessGroups groups){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GROUP_KEY, groups.get_id());
            values.put(KEY_GROUP_NAME, groups.get_name());
            values.put(KEY_GROUP_DESCRIPTION, groups.get_description());
            values.put(KEY_GROUP_CREATED_DATE, groups.get_created_date());

            return db.update(TABLE_BUSINESS_GROUP, values, GROUP_KEY + " = ?",
                    new String[]{String.valueOf(groups.get_id())});
        }

        public void deteleGroups (BusinessGroups groups){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_BUSINESS_GROUP, GROUP_KEY + " =?",
                    new String[]{String.valueOf(groups.get_id())});
            db.close();
        }

        public int getGroupsCount(){
            String countQuery = "SELECT * FROM " + TABLE_BUSINESS_GROUP;
            SQLiteDatabase db =  this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            return cursor.getCount();
        }

    }

