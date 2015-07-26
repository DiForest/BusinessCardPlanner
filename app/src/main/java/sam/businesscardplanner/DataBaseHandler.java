package sam.businesscardplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 7/19/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "plannerDB";
    private static final String TABLE_BUSINESS_CARD ="businessCard";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_JOB = "job";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_WORK_ADDRESS = "workAddress";
    private static final String KEY_WORK_PHONE = "workPhone";
    private static final String KEY_WORK_WEBSITE = "workWebsite";
    private static final String KEY_CATEGORY = "CATEGORY";

    public DataBaseHandler (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String CREATE_BUSINESS_CARD_TABLE = "CREATE TABLE "+ TABLE_BUSINESS_CARD + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_COMPANY + " TEXT," +
                KEY_JOB + " TEXT," +
                KEY_ADDRESS + " TEXT," +
                KEY_PHONE + " TEXT," +
                KEY_EMAIL + " TEXT," +
                KEY_WORK_PHONE + " TEXT," +
                KEY_WORK_ADDRESS + " TEXT," +
                KEY_WORK_WEBSITE + " TEXT," +
                KEY_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_BUSINESS_CARD_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS_CARD);
        onCreate(db);
    }

    public void addBusinessCard(BusinessCard businessCard){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME , businessCard._name);
        values.put(KEY_JOB, businessCard._job);
        values.put(KEY_COMPANY, businessCard._company);
        values.put(KEY_ADDRESS, businessCard._address);
        values.put(KEY_PHONE, businessCard._phone);
        values.put(KEY_EMAIL, businessCard._email);
        values.put(KEY_WORK_PHONE, businessCard._workPhone);
        values.put(KEY_WORK_ADDRESS, businessCard._workAddress);
        values.put(KEY_CATEGORY, businessCard._category);
        //values.put(KEY_IMAGE, businessCard._image);

        db.insert(TABLE_BUSINESS_CARD, null, values);
        db.close();
    }

    /*
    BusinessCard getBusinessCard(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BUSINESS_CARD, new String[] {KEY_ID,
                KEY_NAME, KEY_IMAGE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        BusinessCard businessCard = new BusinessCard(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getBlob(1));

        return businessCard;
    }
    */

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
