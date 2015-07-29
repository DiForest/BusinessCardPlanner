package sam.businesscardplanner.BusinessGroup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        private static final String TABLE_GROUP_AND_CARDS = "groupAndCard";

        private static final String GROUP_KEY = "groupKey";
        private static final String GROUP_NAME = "groupName";
        private static final String GROUP_DESCRIPTION = "groupDescription";

        private static final String GROUP_CARD_KEY = "groupAndCardKey";

        public GroupsDatabaseHandler(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db){

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void createGroupTable(){

        }

        public void createGroupAndCardTable(){

        }
    }

