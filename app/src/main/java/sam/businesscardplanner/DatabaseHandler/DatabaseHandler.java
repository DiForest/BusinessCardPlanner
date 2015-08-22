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
import sam.businesscardplanner.BusinessGroup.GroupAndPeople;
import sam.businesscardplanner.BusinessSchedule.BusinessEvent;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "BusinessCardPlanner";

    private static final String TABLE_BUSINESS_CARD ="businessCard";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_BITMAP = "profileImage";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_JOB = "job";
    private static final String KEY_BUSINESS_TYPE = "businessType";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS_STREET = "street";
    private static final String KEY_ADDRESS_CITY = "city";
    private static final String KEY_ADDRESS_STATE = "state";
    private static final String KEY_WORK_STREET = "workStreet";
    private static final String KEY_WORK_CITY = "workCity";
    private static final String KEY_WORK_STATE = "workState";
    private static final String KEY_WORK_PHONE = "workPhone";
    private static final String KEY_WORK_WEBSITE = "workWebsite";
    private static final String KEY_CARD_CREATED_DATE = "cardCreateDate";

    private static final String TABLE_BUSINESS_GROUP ="businessGroup";
    private static final String KEY_GROUP_ID = "id";
    private static final String KEY_GROUP_NAME = "groupName";
    private static final String KEY_GROUP_CREATED_DATE = "groupCreatedDate";
    private static final String KEY_GROUP_MEMBER_NUMBER = "groupMemberNumber";
    private static final String KEY_GROUP_DESCRIPTION = "groupDescription";

    private static final String TABLE_GROUP_PEOPLE = "groupPeople";
    private static final String GP_ID = "id";
    private static final String GROUP_FOREIGN_KEY = "groupForeignKey";
    private static final String PEOPLE_FOREIGN_KEY = "peopleForeignKey";
    private static final String PEOPLE_NAME = "peopleName";

    private static final String TABLE_EVENT = "businessEvent";
    private static final String KEY_EVENT_ID = "id";
    private static final String KEY_EVENT_TITLE = "businessEventName";
    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_END_DATE = "endDate";
    private static final String KEY_END_TIME = "endTime";
    private static final String KEY_ALL_DAY_STATUS = "allDayStatus";
    private static final String KEY_INVITED_PEOPLE ="invitedPeople";
    private static final String KEY_EVENT_DESCRIPTION = "eventDescription";

    private static String CREATE_BUSINESS_CARD_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_CARD
            + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_NAME + " TEXT, " +
            KEY_COMPANY + " TEXT, " +
            KEY_JOB + " TEXT, " +
            KEY_BUSINESS_TYPE + " TEXT, "+
            KEY_PHONE + " TEXT, " +
            KEY_EMAIL + " TEXT, " +
            KEY_WORK_PHONE + " TEXT, " +
            KEY_ADDRESS_STREET + " TEXT, " +
            KEY_ADDRESS_CITY + " TEXT, "+
            KEY_ADDRESS_STATE + " TEXT, " +
            KEY_WORK_STREET + " TEXT, "+
            KEY_WORK_CITY + " TEXT, "+
            KEY_WORK_STATE + " TEXT, " +
            KEY_WORK_WEBSITE + " TEXT, " +
            KEY_IMAGE_BITMAP + " TEXT, " +
            KEY_CARD_CREATED_DATE + " INTEGER " + " );";

    //create group statement
    private static String CREATE_BUSINESS_GROUP_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_GROUP
            + " (" + KEY_GROUP_ID + " INTEGER PRIMARY KEY, " +
            KEY_GROUP_NAME + " TEXT, "+
            KEY_GROUP_CREATED_DATE + " INTEGER, " +
            KEY_GROUP_MEMBER_NUMBER + " INTEGER, " +
            KEY_GROUP_DESCRIPTION + " TEXT " + " );";

    //initialise the group and people table elements
    private static String CREATE_GROUP_AND_PEOPLE_TABLE = "CREATE TABLE " + TABLE_GROUP_PEOPLE + " ( " +
            GP_ID + " INTEGER PRIMARY KEY," +
            GROUP_FOREIGN_KEY + " INT, " +
            PEOPLE_FOREIGN_KEY + " INT, " +
            PEOPLE_NAME + " TEXT," +
            "FOREIGN KEY ( " + GROUP_FOREIGN_KEY + " ) REFERENCES "
            + TABLE_BUSINESS_GROUP + " ( "+ KEY_ID + " ), " +
            "FOREIGN KEY ( " + PEOPLE_FOREIGN_KEY + " ) REFERENCES "
            + TABLE_BUSINESS_CARD + "( " + KEY_GROUP_ID +" )" +
            ");";

    private static String CREATE_EVENT_TABLE = "CREATE TABLE "+ TABLE_EVENT + " ( " +
            KEY_EVENT_ID + " INTEGER PRIMARY KEY, " +
            KEY_EVENT_TITLE + " TEXT, " +
            KEY_ALL_DAY_STATUS + " INTEGER, " +
            KEY_INVITED_PEOPLE + " TEXT, " +
            KEY_EVENT_DESCRIPTION + " TEXT, " +
            KEY_START_DATE + " INTEGER, " +
            KEY_START_TIME + " INTEGER, " +
            KEY_END_DATE + " INTEGER, " +
            KEY_END_TIME + " INTEGER " +
            " );";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create all the tables
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BUSINESS_CARD_TABLE);
        db.execSQL(CREATE_BUSINESS_GROUP_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
        db.execSQL(CREATE_GROUP_AND_PEOPLE_TABLE);
        //db.execSQL(CREATE_EVENT_PEOPLE_TABLE);
    }

    //upgradde database table
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP_PEOPLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);

        onCreate(db);
    }
    /* -------
    **************
    Business card table method
    **************************************************
    ------------------------------------------------------------------------------- */
    public void addBusinessCard(BusinessCard businessCard){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME , businessCard.get_name());
        values.put(KEY_IMAGE_BITMAP, businessCard.get_image());
        values.put(KEY_JOB, businessCard.get_job());
        values.put(KEY_COMPANY, businessCard.get_company());
        values.put(KEY_BUSINESS_TYPE, businessCard.get__businessType());
        values.put(KEY_PHONE, businessCard.get_phone());
        values.put(KEY_EMAIL, businessCard.get_email());
        values.put(KEY_ADDRESS_CITY, businessCard.get_street());
        values.put(KEY_ADDRESS_CITY, businessCard.get_city());
        values.put(KEY_ADDRESS_STATE, businessCard.get_state());
        values.put(KEY_WORK_STREET, businessCard.get_workStreet());
        values.put(KEY_WORK_CITY, businessCard.get_workCity());
        values.put(KEY_WORK_STATE, businessCard.get_workState());
        values.put(KEY_WORK_PHONE, businessCard.get_workPhone());
        values.put(KEY_WORK_WEBSITE, businessCard.get_workWebsite());
        values.put(KEY_CARD_CREATED_DATE, businessCard.get_date());

        db.insert(TABLE_BUSINESS_CARD, null, values);
        db.close();
    }

    public List<BusinessCard> getAllBusinessCard() {
        List<BusinessCard> businessCardList = new ArrayList<BusinessCard>();
        String selectQuery = "SELECT * FROM " + TABLE_BUSINESS_CARD + " ORDER BY + " + KEY_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null ){
            if (cursor.moveToFirst()) {
                do {
                    BusinessCard businessCard = new BusinessCard();
                    businessCard.set_id(Integer.parseInt(cursor.getString(0)));
                    businessCard.set_name(cursor.getString(1));
                    businessCard.set_company(cursor.getString(2));
                    businessCard.set_job(cursor.getString(3));
                    businessCard.set_businessType(cursor.getString(4));
                    businessCard.set_phone(cursor.getString(5));
                    businessCard.set_email(cursor.getString(6));
                    businessCard.set_workPhone(cursor.getString(7));
                    businessCard.set_street(cursor.getString(8));
                    businessCard.set_city(cursor.getString(9));
                    businessCard.set_state(cursor.getString(10));
                    businessCard.set_workStreet(cursor.getString(11));
                    businessCard.set_workCity(cursor.getString(12));
                    businessCard.set_workState(cursor.getString(13));
                    businessCard.set_workWebsite(cursor.getString(14));
                    businessCard.set_image(cursor.getString(15));
                    businessCard.set_date(Integer.parseInt(cursor.getString(16)));

                    businessCardList.add(businessCard);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return businessCardList;
    }

    public List<BusinessCard> getAllBusinessCardInOrderDate() {
        List<BusinessCard> businessCardList = new ArrayList<BusinessCard>();
        String selectQuery = "SELECT * FROM " + TABLE_BUSINESS_CARD + "ORDER BY "
                + KEY_CARD_CREATED_DATE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null ){
            if (cursor.moveToFirst()) {
                do {
                    BusinessCard businessCard = new BusinessCard();
                    businessCard.set_id(Integer.parseInt(cursor.getString(0)));
                    businessCard.set_name(cursor.getString(1));
                    businessCard.set_company(cursor.getString(2));
                    businessCard.set_job(cursor.getString(3));
                    businessCard.set_businessType(cursor.getString(4));
                    businessCard.set_phone(cursor.getString(5));
                    businessCard.set_email(cursor.getString(6));
                    businessCard.set_workPhone(cursor.getString(7));
                    businessCard.set_street(cursor.getString(8));
                    businessCard.set_city(cursor.getString(9));
                    businessCard.set_state(cursor.getString(10));
                    businessCard.set_workStreet(cursor.getString(11));
                    businessCard.set_workCity(cursor.getString(12));
                    businessCard.set_workState(cursor.getString(13));
                    businessCard.set_workWebsite(cursor.getString(14));
                    businessCard.set_image(cursor.getString(15));
                    businessCard.set_date(Integer.parseInt(cursor.getString(16)));

                    //add into list
                    businessCardList.add(businessCard);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return businessCardList;
    }

    //get ONE business card by id
    public BusinessCard getBusinessCard(int id){

        String query = "SELECT * FROM " + TABLE_BUSINESS_CARD + " WHERE "+ KEY_ID + " = " + id;
        BusinessCard businessCard = new BusinessCard();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            businessCard.set_id(Integer.parseInt(cursor.getString(0)));
            businessCard.set_name(cursor.getString(1));
            businessCard.set_company(cursor.getString(2));
            businessCard.set_job(cursor.getString(3));
            businessCard.set_businessType(cursor.getString(4));
            businessCard.set_phone(cursor.getString(5));
            businessCard.set_email(cursor.getString(6));
            businessCard.set_workPhone(cursor.getString(7));
            businessCard.set_street(cursor.getString(8));
            businessCard.set_city(cursor.getString(9));
            businessCard.set_state(cursor.getString(10));
            businessCard.set_workStreet(cursor.getString(11));
            businessCard.set_workCity(cursor.getString(12));
            businessCard.set_workState(cursor.getString(13));
            businessCard.set_workWebsite(cursor.getString(14));
            businessCard.set_image(cursor.getString(15));
            businessCard.set_date(Integer.parseInt(cursor.getString(16)));
        }
        return businessCard;
    }

    public List<BusinessCard> searchBusinessCardByName(String name){

        String query = "SELECT * FROM " + TABLE_BUSINESS_CARD ;
        List<BusinessCard> businessCardList = new ArrayList<BusinessCard>();
        BusinessCard businessCard = new BusinessCard();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor != null){
           if( cursor.moveToFirst()){
                do {
                    String businessCardName = cursor.getString(1).toUpperCase();
                    if(businessCardName.equals(name.toUpperCase())) {
                        businessCard.set_id(Integer.parseInt(cursor.getString(0)));
                        businessCard.set_name(cursor.getString(1));
                        businessCard.set_company(cursor.getString(2));
                        businessCard.set_job(cursor.getString(3));
                        businessCard.set_businessType(cursor.getString(4));
                        businessCard.set_phone(cursor.getString(5));
                        businessCard.set_email(cursor.getString(6));
                        businessCard.set_workPhone(cursor.getString(7));
                        businessCard.set_street(cursor.getString(8));
                        businessCard.set_city(cursor.getString(9));
                        businessCard.set_state(cursor.getString(10));
                        businessCard.set_workStreet(cursor.getString(11));
                        businessCard.set_workCity(cursor.getString(12));
                        businessCard.set_workState(cursor.getString(13));
                        businessCard.set_workWebsite(cursor.getString(14));
                        businessCard.set_image(cursor.getString(15));
                        businessCard.set_date(Integer.parseInt(cursor.getString(16)));
                    }
                }while(cursor.moveToNext());
            }
        }
        db.close();
        return businessCardList;
    }

    public int updateBusinessCard(BusinessCard businessCard){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int itemId = businessCard.get_id();
        values.put(KEY_NAME , businessCard.get_name());
        values.put(KEY_IMAGE_BITMAP, businessCard.get_image());
        values.put(KEY_JOB, businessCard.get_job());
        values.put(KEY_COMPANY, businessCard.get_company());
        values.put(KEY_BUSINESS_TYPE, businessCard.get__businessType());
        values.put(KEY_PHONE, businessCard.get_phone());
        values.put(KEY_EMAIL, businessCard.get_email());
        values.put(KEY_ADDRESS_CITY, businessCard.get_street());
        values.put(KEY_ADDRESS_CITY, businessCard.get_city());
        values.put(KEY_ADDRESS_STATE, businessCard.get_state());
        values.put(KEY_WORK_STREET, businessCard.get_workStreet());
        values.put(KEY_WORK_CITY, businessCard.get_workCity());
        values.put(KEY_WORK_STATE, businessCard.get_workState());
        values.put(KEY_WORK_PHONE, businessCard.get_workPhone());
        values.put(KEY_WORK_WEBSITE, businessCard.get_workWebsite());

        return  db.update(TABLE_BUSINESS_CARD, values, KEY_ID
                + " = " + itemId, null);
    }

    public void deleteBusinessCard (int itemId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUSINESS_CARD, KEY_ID + " = " + itemId, null);
        db.close();
    }

    /* --------------------------
    -----  Business Group Table  --------------
    ---------------------------------------------------------------------------- */
    public void addGroup(BusinessGroups group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_NAME, group.get_name());
        values.put(KEY_GROUP_CREATED_DATE, group.get_created_date());
        values.put(KEY_GROUP_MEMBER_NUMBER,group.get_member_count());
        values.put(KEY_GROUP_DESCRIPTION,group.get_description());

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
                    groups.set_created_date(Integer.parseInt(cursor.getString(2)));
                    groups.set_member_count(Integer.parseInt(cursor.getString(3)));
                    groups.set_description(cursor.getString(4));

                    groupsList.add(groups);
                }while(cursor.moveToNext());
            }
        }
        db.close();
        return groupsList;
    }

    public List<BusinessGroups> getAllGroupInOrderDate(){
        List<BusinessGroups> groupsList =  new ArrayList<BusinessGroups>();
        String selectQuery = "SELECT * FROM "+ TABLE_BUSINESS_GROUP + " ORDER BY "
                + KEY_GROUP_CREATED_DATE ;

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    BusinessGroups groups = new BusinessGroups();
                    groups.set_id(Integer.parseInt(cursor.getString(0)));
                    groups.set_name(cursor.getString(1));
                    groups.set_created_date(Integer.parseInt(cursor.getString(2)));
                    groups.set_member_count(Integer.parseInt(cursor.getString(3)));
                    groups.set_description(cursor.getString(4));

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
            groups.set_created_date(Integer.parseInt(cursor.getString(2)));
            groups.set_member_count(Integer.parseInt(cursor.getString(3)));
            groups.set_description(cursor.getString(4));
        }
        return groups;
    }

    //update the group info
    public int updateGroups(BusinessGroups groups, int groupId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_ID, groups.get_id());
        values.put(KEY_GROUP_NAME, groups.get_name());
        values.put(KEY_GROUP_CREATED_DATE, groups.get_created_date());
        values.put(KEY_GROUP_MEMBER_NUMBER, groups.get_member_count());
        values.put(KEY_GROUP_DESCRIPTION, groups.get_description());

        return db.update(TABLE_BUSINESS_GROUP, values, KEY_GROUP_ID + " = " + groupId, null);
    }

    //remove the group
    public void deleteGroups (int groupId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUSINESS_GROUP, KEY_GROUP_ID + " = " + groupId, null);
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
        values.put(KEY_GROUP_MEMBER_NUMBER, groups.get_member_count() + 1);

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

    public boolean checkDuplicateMember(int itemId, int groupId){
        String selectQuery = "SELECT * FROM " + TABLE_GROUP_PEOPLE
                + " WHERE " + GROUP_FOREIGN_KEY + " = " + groupId
                + " AND " + PEOPLE_FOREIGN_KEY + " = " + itemId;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<GroupAndPeople> gapList = new ArrayList<GroupAndPeople>();
        GroupAndPeople gap = new GroupAndPeople();

        if(cursor!=null){
            if(cursor.moveToFirst()) {
                do {
                    gap.set_gp_ID(Integer.parseInt(cursor.getString(0)));
                    gap.set_group_FK(Integer.parseInt(cursor.getString(1)));
                    gap.set_people_FK(Integer.parseInt(cursor.getString(2)));
                    gap.set_people_name(cursor.getString(3));
                    gapList.add(gap);
                }while(cursor.moveToNext());
            }
        }
        db.close();

        if(gapList.size()>0){
            return true;
        }else
            return false;
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
                    int pplId = Integer.parseInt(cursor.getString(2));
                    //use the id to get business card from business card db
                    BusinessCard businessCard = getBusinessCard(pplId);
                    //add into a list
                    cardList.add(businessCard);
                }while (cursor.moveToNext());
            }
        }
        db.close();

        return cardList;
    }

    public void removeMember(int itemId, int groupId){
        GroupAndPeople gap = getMemberGroupRelationship(itemId,groupId);
        BusinessGroups groups = new BusinessGroups();
        groups = getGroup(groupId);

        int count = groups.get_member_count();
        int gapId = gap.get_gp_ID();
        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_MEMBER_NUMBER,count -1);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUP_PEOPLE, KEY_ID + " = " + gapId, null);
        db.update(TABLE_BUSINESS_GROUP,values, KEY_GROUP_ID + " = " + groupId,null);
        db.close();
    }

    public GroupAndPeople getMemberGroupRelationship(int itemId, int groupId){
        String selectQuery = "SELECT * FROM "+ TABLE_GROUP_PEOPLE
                + " WHERE " + GROUP_FOREIGN_KEY + " = " + groupId
                + " AND " + PEOPLE_FOREIGN_KEY + " = " + itemId;
        GroupAndPeople gap = new GroupAndPeople();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null){
            cursor.moveToFirst();
            gap.set_gp_ID(Integer.parseInt(cursor.getString(0)));
            gap.set_group_FK(Integer.parseInt(cursor.getString(1)));
            gap.set_people_FK(Integer.parseInt(cursor.getString(2)));
            gap.set_people_name(cursor.getString(3));
        }
        return gap;
    }

    public String getAllGroupOfMember(int itemId){
        String selectQuery = "SELECT * FROM " + TABLE_GROUP_PEOPLE + " WHERE "
                + PEOPLE_FOREIGN_KEY + " = " + itemId ;
        GroupAndPeople gap = new GroupAndPeople();
        BusinessGroups groups = new BusinessGroups();
        String groupNameList = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    gap.set_gp_ID(Integer.parseInt(cursor.getString(0)));
                    gap.set_group_FK(Integer.parseInt(cursor.getString(1)));
                    gap.set_people_FK(Integer.parseInt(cursor.getString(2)));
                    gap.set_people_name(cursor.getString(3));
                    groups = getGroup(gap.get_group_FK());
                    String name = groups.get_name();
                    groupNameList = groupNameList + name + "  ";
                }
                while (cursor.moveToNext());
            }
        }
        db.close();
        return groupNameList;
    }

    public List<Integer> getAllMemberId(int groupID){
        List<Integer> list = new ArrayList<Integer>();
        String selectQuery = "SELECT " + PEOPLE_FOREIGN_KEY + " FROM "+ TABLE_GROUP_PEOPLE
                + " WHERE " + GROUP_FOREIGN_KEY + " = " + groupID ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null){
            if(cursor.moveToNext()){
                do{
                    int pplId = Integer.parseInt(cursor.getString(1));
                    list.add(pplId);
                }while (cursor.moveToNext());
            }
        }
        db.close();

        return list;
    }

    public int getCountMemberinGroup(int groupId){
        String countQuery = "SELECT * FROM "+ TABLE_GROUP_PEOPLE + " WHERE " +
                GROUP_FOREIGN_KEY + " = " + groupId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    /* ----------------- event ------------------------*/

    public void addEvent(BusinessEvent be){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_EVENT_TITLE , be.get_calendar_tile());
        values.put(KEY_START_DATE, be.get_startDate());
        values.put(KEY_START_TIME, be.get_startTime());
        values.put(KEY_END_DATE, be.get_startDate());
        values.put(KEY_END_TIME, be.get_startTime());
        values.put(KEY_ALL_DAY_STATUS, be.get_all_day_status());
        values.put(KEY_INVITED_PEOPLE, be.get_invitedPeople());
        values.put(KEY_EVENT_DESCRIPTION, be.get_description());
        db.insert(TABLE_EVENT, null, values);
        db.close();
    }

    //get all the business card in the database
    public List<BusinessEvent> getAllEvent(String currrentDate) {

        String[] temp2 = currrentDate.split("/");
        int YY = Integer.parseInt(temp2[0]);
        int MM = Integer.parseInt(temp2[1]);
        int DD = Integer.parseInt(temp2[2]);

        int compareDate = YY * 10000 + MM *100 + DD;

        List<BusinessEvent> businessEventsList = new ArrayList<BusinessEvent>();
        String selectQuery = "SELECT * FROM businessEvent ORDER BY "
                + KEY_START_DATE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null ){
            if (cursor.moveToFirst()) {
                do {
                    BusinessEvent be = new BusinessEvent();
                    be.set_calendar_id(Integer.parseInt(cursor.getString(0)));
                    be.set_calendar_tile(cursor.getString(1));
                    be.set_All_day_status(Integer.parseInt(cursor.getString(2)));
                    be.set_invitedPeople(cursor.getString(3));
                    be.set_description(cursor.getString(4));
                    be.set_startDate(Integer.parseInt(cursor.getString(5)));
                    int start = Integer.parseInt(cursor.getString(5));
                    be.set_startTime(Integer.parseInt(cursor.getString(6)));
                    be.set_endDate(Integer.parseInt(cursor.getString(7)));
                    be.set_endTime(Integer.parseInt(cursor.getString(8)));

                    //add into list
                    if(start>=compareDate) {
                        businessEventsList.add(be);
                    }
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return businessEventsList;
    }

    public BusinessEvent getEvent(int eventId){
        String query = "SELECT * FROM " + TABLE_EVENT +
                " WHERE " + KEY_EVENT_ID + " = " + eventId;
        BusinessEvent be = new BusinessEvent();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null){
            cursor.moveToFirst();

            be.set_calendar_id(Integer.parseInt(cursor.getString(0)));
            be.set_calendar_tile(cursor.getString(1));
            be.set_All_day_status(Integer.parseInt(cursor.getString(2)));
            be.set_invitedPeople(cursor.getString(3));
            be.set_description(cursor.getString(4));
            be.set_startDate(Integer.parseInt(cursor.getString(5)));
            be.set_startTime(Integer.parseInt(cursor.getString(6)));
            be.set_endDate(Integer.parseInt(cursor.getString(7)));
            be.set_endTime(Integer.parseInt(cursor.getString(8)));
        }
        db.close();
        return be;
    }
}
