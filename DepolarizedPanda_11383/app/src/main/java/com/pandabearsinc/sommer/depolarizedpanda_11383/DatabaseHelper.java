package com.pandabearsinc.sommer.depolarizedpanda_11383;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 26-09-2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Database values
    private static final String DATABASE_NAME = "DepolarizedPanda";
    private static final int DATABASE_VERSION = 3;

    //Overview table values
    private static final String OVERVIEW_TABLE_NAME = "MainTable";
    private static final String OVERVIEW_DATE_KEY_NAME = "DateTime";
    private static final String OVERVIEW_MOOD_KEY_NAME = "Mood";
    private static final String OVERVIEW_NOTE_KEY_NAME = "Note";
    private static final String OVERVIEW_AMOUNT_KEY_NAME = "Amount";
    private static final String OVERVIEW_MEDICINE_FK_NAME = "MedicineId";

    //Medicine table values
    private static final String MEDICINE_TABLE_NAME = "MedicineTable";
    private static final String MEDICINE_PRIMARY_KEY_NAME = "ID";
    private static final String MEDICINE_NAME_KEY_NAME = "Name";
    private static final String MEDICINE_DOSE_KEY_NAME = "Dose";

    private static final String DICTIONARY_MAINTABLE_CREATE =
            "CREATE TABLE " + OVERVIEW_TABLE_NAME + " (" +
                    OVERVIEW_DATE_KEY_NAME + " DATETIME PRIMARY KEY, " +
                    OVERVIEW_MOOD_KEY_NAME + " TEXT, " +
                    OVERVIEW_NOTE_KEY_NAME + " TEXT, " +
                    OVERVIEW_AMOUNT_KEY_NAME + " INTEGER, " +
                    OVERVIEW_MEDICINE_FK_NAME + " INTEGER);";

    private static final String DICTIONARY_MEDICINE_CREATE =
            "CREATE TABLE " + MEDICINE_TABLE_NAME + "(" +
                    MEDICINE_PRIMARY_KEY_NAME + " INTEGER PRIMARY KEY," +
                    MEDICINE_NAME_KEY_NAME + " TEXT," +
                    MEDICINE_DOSE_KEY_NAME + " TEXT);";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + OVERVIEW_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_MAINTABLE_CREATE);
        db.execSQL(DICTIONARY_MEDICINE_CREATE);
    }

    public void addRegistration(Registration registration)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Enable foreign key constraints
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

        List<Registration> registrationList = getAllRegistrations();
        boolean updatedTable = false;

        for (Iterator<Registration> i = registrationList.iterator(); i.hasNext(); )
        {
            Registration registrationFromList = i.next();
            if (registration.getDate().equals(registrationFromList.getDate()))
            {
                updateRegistration(registration);
                updatedTable = true;
            }
        }

        if(!updatedTable) {

            ContentValues values = new ContentValues();
            values.put(OVERVIEW_DATE_KEY_NAME, registration.getDate());
            values.put(OVERVIEW_MOOD_KEY_NAME, registration.getMood());
            values.put(OVERVIEW_NOTE_KEY_NAME, registration.getNote());
            values.put(OVERVIEW_AMOUNT_KEY_NAME, registration.getAmount());
            values.put(OVERVIEW_MEDICINE_FK_NAME, registration.getMedicine().getId());
            // Inserting Row
            long id = db.insert(OVERVIEW_TABLE_NAME, null, values);
            Log.d("RowID", Long.toString(id));
            //db.close(); // Closing database connection
        }
    }

    public Medicine getMedicineFromId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + MEDICINE_TABLE_NAME + " WHERE "
        + MEDICINE_PRIMARY_KEY_NAME + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);
        Medicine medicine = new Medicine();
        if (c.moveToFirst()) {

            do {
                medicine.setId(c.getInt(0));
                medicine.setName(c.getString(1));
                medicine.setDoseInMg(c.getDouble(2));
            } while (c.moveToNext());

        }
        return medicine;
    }

    public int addMedicine(Medicine medicine)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(MEDICINE_NAME_KEY_NAME, medicine.getName());
        values.put(MEDICINE_DOSE_KEY_NAME, medicine.getDoseInMg());
        // Inserting Row
        long medId = db.insert(MEDICINE_TABLE_NAME, null, values);
        db.close(); // Closing database connection

        return (int)medId;
    }

    public List<Registration> getAllRegistrations()
    {
        List<Registration> registrationList = new ArrayList<Registration>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + OVERVIEW_TABLE_NAME;
        Log.d("Select query", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Registration registration= new Registration();
                registration.setDate(cursor.getString(0));
                registration.setMood(cursor.getString(1));
                registration.setNote(cursor.getString(2));
                registration.setAmount(cursor.getInt(3));
                registration.setMedicine(getMedicineFromId(cursor.getInt(4)));
                // Adding registration to list
                registrationList.add(registration);
            } while (cursor.moveToNext());
        }

        // return registration list
        return registrationList;
    }

    public List<Medicine> getAllMedicines()
    {
        List<Medicine> medicineList = new ArrayList<Medicine>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + MEDICINE_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicine medicine = new Medicine();
                medicine.setId(cursor.getInt(0));
                medicine.setName(cursor.getString(1));
                medicine.setDoseInMg(cursor.getDouble(2));
                // Adding medicine to list
                medicineList.add(medicine);
            } while (cursor.moveToNext());
        }

        // return medicine list
        return medicineList;
    }

    public int updateRegistration(Registration registration) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OVERVIEW_DATE_KEY_NAME, registration.getDate());
        values.put(OVERVIEW_MOOD_KEY_NAME, registration.getMood());
        values.put(OVERVIEW_NOTE_KEY_NAME, registration.getNote());
        values.put(OVERVIEW_AMOUNT_KEY_NAME, registration.getAmount());
        values.put(OVERVIEW_MEDICINE_FK_NAME, registration.getMedicine().getId());

        // updating row
        return db.update(OVERVIEW_TABLE_NAME, values, OVERVIEW_DATE_KEY_NAME + " = ?",
                new String[] { String.valueOf(registration.getDate()) });
    }

    public void deleteAllRowsFromOverviewTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(OVERVIEW_TABLE_NAME, null, null);
    }

    public void deleteAllRowsFromMedicineTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEDICINE_TABLE_NAME, null, null);
    }

    public ArrayList<Registration> getHistoryFromDateToDate(String startDate, String endDate)
    {
        ArrayList<Registration> registrationList = new ArrayList<Registration>();
        SQLiteDatabase db = this.getWritableDatabase();

        String[] ColumnsToGet = {OVERVIEW_DATE_KEY_NAME, OVERVIEW_MOOD_KEY_NAME, OVERVIEW_NOTE_KEY_NAME, OVERVIEW_AMOUNT_KEY_NAME, OVERVIEW_MEDICINE_FK_NAME};
        String WhereClause = OVERVIEW_DATE_KEY_NAME + " >= " + "DATETIME('" + startDate + "')" + " AND " + OVERVIEW_DATE_KEY_NAME + " <= " + "DATETIME('" + endDate + "')";
        String OrderBy = OVERVIEW_DATE_KEY_NAME + " ASC";

        Cursor cursor = db.query(false, OVERVIEW_TABLE_NAME, ColumnsToGet, WhereClause, null, null, null, OrderBy, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Registration registration= new Registration();
                registration.setDate(cursor.getString(0));
                registration.setMood(cursor.getString(1));
                registration.setNote(cursor.getString(2));
                registration.setAmount(cursor.getInt(3));
                registration.setMedicine(getMedicineFromId(cursor.getInt(4)));
                // Adding registration to list
                registrationList.add(registration);
            } while (cursor.moveToNext());
        }

        // return registration list
        return registrationList;
    }
}
