package com.example.lsyas.georecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lsyas on 08-05-2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProjectDB.db";
    public static final String TABLE1_NAME = "ProjectTitle";
    public static final String TABLE2_NAME = "ProjectContent";

    public static final String T1_Col1 = "ID";
    public static final String T1_Col2 = "NAME";
    //public static final String T1_Col3 = "Date";

    public static final String T2_Col1 = "ID";
    public static final String T2_Col2 = "LAT";
    public static final String T2_Col3 = "LONG";
    public static final String T2_Col4 = "COMMENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //  SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE1_NAME+"(NAME TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + TABLE2_NAME + "( ID INTEGER PRIMARY KEY   AUTOINCREMENT,LAT REAL ,LONG REAL,COMMENT TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);
    }

    public boolean InsertDataIntoProject(String in_name)    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(T1_Col2,in_name);
        //cv.put(T1_Col3,in_date);
        long res = db.insert(TABLE1_NAME,null,cv);
            if(res == -1)
                 return false;
        return true;
    }

    public boolean InsertProjectData(Double LAT,Double LONG,String COMMENT){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(T2_Col2, LAT);
        cv.put(T2_Col3,LONG);
        cv.put(T2_Col4,COMMENT);
        long res = db.insert(TABLE2_NAME,null,cv);
        if(res == -1)
            return false;
        return true;
    }

    public Cursor GetProjects(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE1_NAME, null);
        return res;
    }

    public Cursor GetTransactions(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE2_NAME,null);
        return res;
    }

    public boolean deletevalues(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE2_NAME); //delete all rows in a table
        db.close();
        return true;
    }
    public boolean exportDatabase() {
        //DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        }
        else {
            //We use the Download directory for saving our .csv file.
            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!exportDir.exists())
            {
                exportDir.mkdirs();
            }

            File file;
            PrintWriter printWriter = null;
            try
            {
                file = new File(exportDir, "MyCSVFile.csv");
                file.createNewFile();
                printWriter = new PrintWriter(new FileWriter(file));

                /**This is our database connector class that reads the data from the database.
                 * The code of this class is omitted for brevity.
                 */
                SQLiteDatabase db = this.getReadableDatabase(); //open the database for reading

                /**Let's read the first table of the database.
                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
                 * containing all records of the table (all fields).
                 * The code of this class is omitted for brevity.
                 */
                Cursor curCSV = db.rawQuery("select * from"+TABLE2_NAME, null);
                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
                printWriter.println("FIRST TABLE OF THE DATABASE");
                printWriter.println("ID,LONGITUDE,LATITUDE,CURRENCY");
                while(curCSV.moveToNext())
                {
                    //Long date = curCSV.getLong(curCSV.getColumnIndex("date"));
                    //String title = curCSV.getString(curCSV.getColumnIndex("title"));
                    Float lat = curCSV.getFloat(curCSV.getColumnIndex("LAT"));
                    Float longi = curCSV.getFloat(curCSV.getColumnIndex("LONG"));
                    //String description = curCSV.getString(curCSV.getColumnIndex("description"));

                    /**Create the line to write in the .csv file.
                     * We need a String where values are comma separated.
                     * The field date (Long) is formatted in a readable text. The amount field
                     * is converted into String.
                     */
                    String record =  T2_Col1+ "," + T2_Col2 ;
                    printWriter.println(record); //write the record in the .csv file
                }

                curCSV.close();
                db.close();
            }

            catch(Exception exc) {
                //if there are any exceptions, return false
                return false;
            }
            finally {
                if(printWriter != null) printWriter.close();
            }

            //If there are no errors, return true.
            return true;
        }
    }
}




