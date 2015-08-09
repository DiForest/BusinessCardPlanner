package sam.businesscardplanner.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;
import sam.businesscardplanner.BusinessGroup.BusinessGroups;

/**
 * Created by Administrator on 7/19/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "BusinessCardPlanner";

    //business card table elements
    private static final String TABLE_BUSINESS_CARD ="businessCard";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "profileImage";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_JOB = "job";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_WORK_ADDRESS = "workAddress";
    private static final String KEY_WORK_PHONE = "workPhone";
    private static final String KEY_WORK_WEBSITE = "workWebsite";

    //business group table elements
    private static final String TABLE_BUSINESS_GROUP ="businessGroup";
    private static final String KEY_GROUP_ID = "id";
    private static final String KEY_GROUP_NAME = "groupName";
    private static final String KEY_GROUP_CREATED_DATE = "groupCreatedDate";
    private static final String KEY_GROUP_MEMBER_NUMBER = "groupMemberNumber";

    //group and people table elements
    private static final String TABLE_GROUP_PEOPLE = "groupPeople";
    private static final String GP_ID = "id";
    private static final String GROUP_FOREIGN_KEY = "groupForeignKey";
    private static final String PEOPLE_FOREIGN_KEY = "peopleForeignKey";
    private static final String PEOPLE_NAME = "peopleName";

    //create card statement
    private static String CREATE_BUSINESS_CARD_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_CARD
            + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
            //KEY_IMAGE + " BLOB," +
            KEY_NAME + " TEXT, " +
            KEY_COMPANY + " TEXT, " +
            KEY_JOB + " TEXT, " +
            KEY_ADDRESS + " TEXT, " +
            KEY_PHONE + " TEXT, " +
            KEY_EMAIL + " TEXT, " +
            KEY_WORK_PHONE + " TEXT, " +
            KEY_WORK_ADDRESS + " TEXT, " +
            KEY_WORK_WEBSITE + " TEXT " +");";

    //create group statement
    private static String CREATE_BUSINESS_GROUP_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_GROUP
            + " (" + KEY_GROUP_ID + " INTEGER PRIMARY KEY, " +
            KEY_GROUP_NAME + " TEXT, "+
            KEY_GROUP_CREATED_DATE + " TEXT, " +
            KEY_GROUP_MEMBER_NUMBER + " INTEGER " + " );";

    //initialise the group and people table elements
    private static String CREATE_GROUP_AND_PEOPLE_TABLE = "CREATE TABLE " + TABLE_GROUP_PEOPLE + " ( " +
            GP_ID + " INTEGER PRIMARY KEY," +
            GROUP_FOREIGN_KEY + " INT, " +
            PEOPLE_FOREIGN_KEY + " INT, " +
            PEOPLE_NAME + " TEXT" +
            //"FOREIGN KEY ( " + GROUP_FOREIGN_KEY + " ) REFERENCES "
            //+ TABLE_BUSINESS_GROUP + " ( "+ KEY_ID + " ), " +
            //"FOREIGN KEY ( " + PEOPLE_FOREIGN_KEY + " ) REFERENCES "
            //+ TABLE_BUSINESS_CARD + "( " + KEY_GROUP_ID +" )" +
            ");";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create all the tables
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BUSINESS_CARD_TABLE);
        db.execSQL(CREATE_BUSINESS_GROUP_TABLE);
        db.execSQL(CREATE_GROUP_AND_PEOPLE_TABLE);
    }

    //upgradde database table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_PEOPLE);

        onCreate(db);
    }

    /* -------------------------   Business card table method    -------------------------------- */
    //add business card into database
    public void addBusinessCard(BusinessCard businessCard){
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME , businessCard.get_name());
        values.put(KEY_JOB, businessCard.get_job());
        values.put(KEY_COMPANY, businessCard.get_company());
        values.put(KEY_ADDRESS, businessCard.get_address());
        values.put(KEY_PHONE, businessCard.get_phone());
        values.put(KEY_EMAIL, businessCard.get_email());
        values.put(KEY_WORK_PHONE, businessCard.get_workPhone());
        values.put(KEY_WORK_ADDRESS, businessCard.get_workAddress());
        //values.put(KEY_IMAGE, businessCard._image);

        db.insert(TABLE_BUSINESS_CARD, null, values);
        db.close();
    }

    //get all the business card in the database
    public List<BusinessCard> getAllBusinessCard() {
        List<BusinessCard> businessCardList = new ArrayList<BusinessCard>();
        String selectQuery = "SELECT * FROM businessCard ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null ){
            if (cursor.moveToFirst()) {
                do {
                    BusinessCard businessCard = new BusinessCard();
                    businessCard.set_id(Integer.parseInt(cursor.getString(0)));
                    businessCard.set_name(cursor.getString(1));
                    businessCard.set_image(cursor.getBlob(2));

                    //add into list
                    businessCardList.add(businessCard);
                } while (cursor.moveToNext());
            }
        }else{
            BusinessCard businessCard = new BusinessCard();
            businessCard.set_name(" Current does not a card");
            businessCard.set_company(" ");
        }
        db.close();
        return businessCardList;
    }

    //get all the business card NAME only
    public List<String> getAllBusinessCardName(){
        List<String> cardList = new ArrayList<String>();
        String selectQuery = "SELECT "+ KEY_NAME + " FROM " + TABLE_BUSINESS_CARD ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    BusinessCard businessCard = new BusinessCard();
                    //get the business card name and add into list
                    cardList.add(businessCard.get_name());
                }while (cursor.moveToNext());
            }
            db.close();
        }
        return cardList;
    }

    //get ONE business card by id
    public BusinessCard getBusinessCard(int id){

        String query = "SELECT * FROM " + TABLE_BUSINESS_CARD +
                " WHERE "+ KEY_ID+ " = " + id;
        BusinessCard businessCard = new BusinessCard();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();

            businessCard.set_name(cursor.getString(1));
            businessCard.set_company(cursor.getString(2));
            businessCard.set_job(cursor.getString(3));
            businessCard.set_address(cursor.getString(4));
            businessCard.set_phone(cursor.getString(5));
            businessCard.set_email(cursor.getString(6));
            businessCard.set_workPhone(cursor.getString(7));
            businessCard.set_address(cursor.getString(8));
            businessCard.set_workWebsite(cursor.getString(9));
        }
        return businessCard;
    }

    //update the business card
    public int updateBusinessCard(BusinessCard businessCard){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, businessCard.get_name());
        values.put(KEY_IMAGE, businessCard.get_image());

        return  db.update(TABLE_BUSINESS_CARD, values, KEY_ID + " = ?",
                new String[]{ String.valueOf(businessCard.get_id()) });
    }

    //detele the business card
    public void deteleBusinessCard (BusinessCard businessCard){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUSINESS_CARD, KEY_ID + " = ?",
                new String[]{String.valueOf(businessCard.get_id())});
        db.close();
    }

    //get the total number of busines card in the database
    public int getBusinessCardCount(){
        String countQuery = "SELECT * FROM "+ TABLE_BUSINESS_CARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public List<BusinessCard> getAvailableBusinessCard(){


        return;
    }


    /* -------------------------------  Business Group Table  ----------------------------------- */
    //create and add new group
    public void addGroup(BusinessGroups group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_NAME, group.get_name());
        values.put(KEY_GROUP_CREATED_DATE, group.get_created_date());
        values.put(KEY_GROUP_MEMBER_NUMBER,group.get_group_member());

        db.insert(TABLE_BUSINESS_GROUP, null, values);
        db.close();
    }

    //get all the groups in the database
    public List<BusinessGroups> getAllGroup(){
        List<BusinessGroups> groupsList =  new ArrayList<BusinessGroups>();
        String selectQuery = "SELECT * FROM "+ TABLE_BUSINESS_GROUP + " ORDER BY "
                + KEY_GROUP_NAME ;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    BusinessGroups groups = new BusinessGroups();
                    groups.set_id(Integer.parseInt(cursor.getString(0)));
                    groups.set_name(cursor.getString(1));
                    groups.set_created_date(cursor.getString(2));
                    groups.set_group_member_number(Integer.parseInt(cursor.getString(3)));

                    groupsList.add(groups);
                }while(cursor.moveToNext());
            }
        }
        db.close();
        return groupsList;
    }

    //get ONE group information
    public BusinessGroups getGroup(int id){
        String query = "SELECT * FROM " + TABLE_BUSINESS_GROUP +
                " WHERE " + KEY_GROUP_ID + " = " + id;
        BusinessGroups groups = new BusinessGroups();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null){
            cursor.moveToFirst();

            groups.set_id(Integer.parseInt(cursor.getString(0)));
            groups.set_name(cursor.getString(1));
            groups.set_created_date(cursor.getString(2));
            groups.set_group_member_number(Integer.parseInt(cursor.getString(3)));
        }
        return groups;
    }

    //update the group info
    public int updateGroups(BusinessGroups groups){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_ID, groups.get_id());
        values.put(KEY_GROUP_NAME, groups.get_name());
        values.put(KEY_GROUP_CREATED_DATE, groups.get_created_date());
        values.put(KEY_GROUP_MEMBER_NUMBER, groups.get_group_member());

        return db.update(TABLE_BUSINESS_GROUP, values, KEY_GROUP_ID + " = ?",
                new String[]{String.valueOf(groups.get_id())});
    }

    //remove the group
    public void deteleGroups (BusinessGroups groups){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUSINESS_GROUP, KEY_GROUP_ID + " =?",
                new String[]{String.valueOf(groups.get_id())});
        db.close();
    }

    //count all the groups number
    public int getGroupsCount(){
        String countQuery = "SELECT * FROM " + TABLE_BUSINESS_GROUP;
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateGroupMember(int groupId){
        BusinessGroups groups = getGroup(groupId);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_ID, groups.get_id());
        values.put(KEY_GROUP_NAME, groups.get_name());
        values.put(KEY_GROUP_CREATED_DATE, groups.get_created_date());
        values.put(KEY_GROUP_MEMBER_NUMBER, groups.get_group_member() + 1);

        return db.update(TABLE_BUSINESS_GROUP, values, KEY_GROUP_ID + " = ?",
                new String[]{String.valueOf(groups.get_id())});
    }

    /* -------------------------------------Group and People Table -------------------------------*/

    //add the group member
    public void addMemberInGroup(int groupID, int cardID){

        SQLiteDatabase db = this.getWritableDatabase();
        BusinessCard bc = getBusinessCard(cardID);
        String name = bc.get_name();

        ContentValues values = new ContentValues();
        values.put(GROUP_FOREIGN_KEY, groupID);
        values.put(PEOPLE_FOREIGN_KEY, cardID);
        values.put(PEOPLE_NAME, name);

        db.insert(TABLE_GROUP_PEOPLE, null, values);
        db.close();

        updateGroupMember(groupID);

    }


    //get all the member in the group
    public List<BusinessCard> getAllMemberFromGroup(int groupID){
        List<BusinessCard> cardList = new ArrayList<BusinessCard>();
        String selectQuery = "SELECT * FROM "+ TABLE_GROUP_PEOPLE
                + " WHERE " + GROUP_FOREIGN_KEY + " = " + groupID
                + " ORDER BY " + PEOPLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null){
            if(cursor.moveToNext()){
                do{
                    //get the people foreign key from the table
                    int temp = Integer.parseInt(cursor.getString(1));
                    //use the id to get business card from business card db
                    BusinessCard businessCard = getBusinessCard(temp);
                    //add into a list
                    cardList.add(businessCard);
                }while (cursor.moveToNext());
            }
        }
        db.close();

        return cardList;
    }


    public int getCountMemberinGroup(int groupId){
        String countQuery = "SELECT * FROM "+ TABLE_GROUP_PEOPLE + " WHERE " +
                GROUP_FOREIGN_KEY + " = " + groupId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}
