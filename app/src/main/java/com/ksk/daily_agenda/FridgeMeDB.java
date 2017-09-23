package com.ksk.daily_agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Stefan on 23.09.2017.
 */

public class FridgeMeDB {
    private static final String DATABASE_NAME = "fridgeme.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "fridgeitems";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PIECES = "pieces";

    public static final int COLUMN_NAME_INDEX = 1;
    public static final int COLUMN_PIECES_INDEX = 2;

    private FridgeMeDB.FridgeMeDBOpenHelper dbHelper;

    private static SQLiteDatabase db;

    public FridgeMeDB(Context context){
        dbHelper = new FridgeMeDB.FridgeMeDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try{
            db = dbHelper.getWritableDatabase();
        }catch(SQLException e){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){db.close();}

    public static long insertFridgeItem(FridgeItem item){
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_NAME, item.getName());
        itemValues.put(KEY_PIECES, item.getPieces());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public ArrayList<FridgeItem> getAllFridgeItems(){
        ArrayList<FridgeItem> items = new ArrayList<>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID,KEY_NAME,KEY_PIECES}, null,
                null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(COLUMN_NAME_INDEX);
                int pieces = cursor.getInt(COLUMN_PIECES_INDEX);

                items.add(new FridgeItem(name,pieces));
            }while(cursor.moveToNext());
        }
        return items;
    }

    public boolean setPieces(FridgeItem item){
        String sql="update "+DATABASE_TABLE+" set pieces='"+item.getPieces()+"' where "+KEY_NAME+" like '"+item.getName()+"'";
        try{
            db.execSQL(sql);
            return true;
        }catch(SQLException ex){
            return false;
        }
    }

    private class FridgeMeDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAME
                + " text not null, " + KEY_PIECES + " text);";

        public FridgeMeDBOpenHelper(Context c, String dbname,
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
