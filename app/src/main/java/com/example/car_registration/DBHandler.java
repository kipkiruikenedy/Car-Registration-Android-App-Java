package com.example.car_registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "vehicledb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "vehicleTable";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our vehicle regi no  column
    private static final String REG_NO_COL = "number";

    // below variable id for our course duration column.
    private static final String TIME_IN_COL = "time";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REG_NO_COL + " TEXT,"
                + TIME_IN_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

//    // this method is use to add new course to our sqlite database.
//    public void addNewCourse(String number, String time) {
//
//        // on below line we are creating a variable for
//        // our sqlite database and calling writable method
//        // as we are writing data in our database.
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // on below line we are creating a
//        // variable for content values.
//        ContentValues values = new ContentValues();
//
//        // on below line we are passing all values
//        // along with its key and value pair.
//        values.put(REG_NO_COL, regNo.getText().toString());
//        values.put(TIME_IN_COL, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
//                Locale.getDefault()).format(new Date()));
//
//
//        // after adding all values we are passing
//        // content values to our table.
//        db.insert(TABLE_NAME, null, values);
//
//        // at last we are closing our
//        // database after adding database.
//        db.close();
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Cursor getCar(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT name FROM vehicledb WHERE id = ?", new String[] { String.valueOf(id) });
    }
}