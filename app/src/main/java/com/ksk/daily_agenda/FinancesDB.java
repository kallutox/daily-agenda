package com.ksk.daily_agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kostja on 18.09.2017.
 */

public class FinancesDB {
    private static final String DATABASE_NAME = "finance.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "financeitems";

    public static final String KEY_ID = "_id";
    public static final String KEY_BUDGET = "budget";
    public static final String KEY_REVENUE = "revenue";

    public static final int COLUMN_BUDGET_INDEX = 1;
    public static final int COLUMN_REVENUE_INDEX = 2;

    private FinancesDBOpenHelper dbHelper;

    private static SQLiteDatabase db;

    public FinancesDB(Context context){
        dbHelper = new FinancesDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException{
        try{
            db = dbHelper.getWritableDatabase();
        }catch(SQLException e){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){db.close();}

    public static long insertFinanceItem(FinanceItem item){
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_BUDGET, item.getBudget());
        itemValues.put(KEY_REVENUE, item.getRevenue());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public ArrayList<FinanceItem> getAllFinanceItems(){
        ArrayList<FinanceItem> items = new ArrayList<FinanceItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID,KEY_BUDGET,KEY_REVENUE}, null,
                null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String budget = cursor.getString(COLUMN_BUDGET_INDEX);
                String revenue = cursor.getString(COLUMN_REVENUE_INDEX);

                items.add(new FinanceItem(budget,revenue));
            }while(cursor.moveToNext());
        }
        return items;
    }

    private class FinancesDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_BUDGET
                + " text not null, " + KEY_REVENUE + " text);";

        public FinancesDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
