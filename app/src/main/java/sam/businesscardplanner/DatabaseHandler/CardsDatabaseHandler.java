package sam.businesscardplanner.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sam.businesscardplanner.BusinessCard.BusinessCard;

/**
 * Created by Administrator on 7/19/2015.
 */
public class CardsDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "plannerDB";
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
    private static final String KEY_CATEGORY = "CATEGORY";

    public CardsDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String CREATE_BUSINESS_CARD_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_CARD + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                //KEY_IMAGE + " BLOB," +
                KEY_NAME + " TEXT," +
                KEY_COMPANY + " TEXT," +
                KEY_JOB + " TEXT," +
                KEY_ADDRESS + " TEXT," +
                KEY_PHONE + " TEXT," +
                KEY_EMAIL + " TEXT," +
                KEY_WORK_PHONE + " TEXT," +
                KEY_WORK_ADDRESS + " TEXT," +
                KEY_WORK_WEBSITE + " TEXT" +")";

        //data order: id, name, company, job, address,phone,email, work_phone, work address, website
        //group
        db.execSQL(CREATE_BUSINESS_CARD_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_CARD);
        onCreate(db);
    }

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
    //data order: id, name, company, job, address,phone,email, work_phone, work address, website
    //group

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

    public int updateBusinessCard(BusinessCard businessCard){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, businessCard.get_name());
        values.put(KEY_IMAGE, businessCard.get_image());

        return  db.update(TABLE_BUSINESS_CARD, values, KEY_ID + " = ?",
                new String[]{ String.valueOf(businessCard.get_id()) });
    }

    public void deteleBusinessCard (BusinessCard businessCard){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUSINESS_CARD, KEY_ID + " = ?",
                new String[]{String.valueOf(businessCard.get_id())});
        db.close();
    }

    public int getBusinessCardCount(){
        String countQuery = "SELECT * FROM "+ TABLE_BUSINESS_CARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
