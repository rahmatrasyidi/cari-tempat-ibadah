package com.sig.caritempatibadah.database;

/**
 * Created by Rahmat Rasyidi Hakim on 12/11/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sig.caritempatibadah.model.Event;
import com.sig.caritempatibadah.model.History;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "caritempatibadah.db";

    public static final String EVENT_TABLE = "event";
    public static final String EVENT_COLUMN_ID = "id_event";
    public static final String EVENT_COLUMN_NAME = "name_event";
    public static final String EVENT_COLUMN_PLACE = "place_event";
    public static final String EVENT_COLUMN_ADDRESS = "address_event";
    public static final String EVENT_COLUMN_DESCRIPTION = "description_event";
    public static final String EVENT_COLUMN_TIME = "time_event";

    public static final String HISTORY_TABLE = "history";
    public static final String HISTORY_COLUMN_ID = "id_history";
    public static final String HISTORY_COLUMN_PLACE = "place_history";
    public static final String HISTORY_COLUMN_ADDRESS = "address_history";
    public static final String HISTORY_COLUMN_TESTIMONI = "testimoni_history";

    private static final String CREATE_TABLE_EVENT = "CREATE TABLE "+ EVENT_TABLE + "(" + EVENT_COLUMN_ID + " INTEGER PRIMARY KEY," + EVENT_COLUMN_NAME
            + " TEXT," + EVENT_COLUMN_DESCRIPTION +" TEXT," + EVENT_COLUMN_ADDRESS +" TEXT," + EVENT_COLUMN_TIME + " TEXT," + EVENT_COLUMN_PLACE + " TEXT" + ")";

    private static final String CREATE_TABLE_HISTORY = "CREATE TABLE "+ HISTORY_TABLE + "(" + HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY," + HISTORY_COLUMN_PLACE
            + " TEXT,"+ HISTORY_COLUMN_ADDRESS + " TEXT," + HISTORY_COLUMN_TESTIMONI +" TEXT" + ")";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        onCreate(db);
    }

    public boolean insertEvent (Event event)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_COLUMN_NAME, event.getName());
        contentValues.put(EVENT_COLUMN_PLACE, event.getPlace());
        contentValues.put(EVENT_COLUMN_ADDRESS, event.getAddress());
        contentValues.put(EVENT_COLUMN_DESCRIPTION, event.getDescription());
        contentValues.put(EVENT_COLUMN_TIME, event.getTime());

        db.insert(EVENT_TABLE, null, contentValues);
        return true;
    }

    public boolean insertHistory (History history)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HISTORY_COLUMN_PLACE, history.getPlace());
        contentValues.put(HISTORY_COLUMN_ADDRESS, history.getAddress());
        contentValues.put(HISTORY_COLUMN_TESTIMONI, history.getTestimoni());

        db.insert(HISTORY_TABLE, null, contentValues);
        return true;
    }

    public Cursor getDataEvent(String place){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from event where place_event="+place+"", null );
        return res;
    }

    public int numberOfRowsEvent(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EVENT_TABLE);
        return numRows;
    }

    public Cursor getDataHistory(String place){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from history where place_event="+place+"", null );
        return res;
    }

    public int numberOfRowsHistory(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, HISTORY_TABLE);
        return numRows;
    }

    public boolean updateEvent (Integer id,String name,String place, String desc, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_event", name);
        contentValues.put("place_event", place);
        contentValues.put("description_event", desc);
        contentValues.put("time_event", time);
        db.update("event", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateHistory (String place, String address, String testi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HISTORY_COLUMN_TESTIMONI, testi);
        db.update(HISTORY_TABLE, contentValues, HISTORY_COLUMN_PLACE + "=?" + " AND " + HISTORY_COLUMN_ADDRESS + "=?" , new String[]{place, address});
        return true;
    }

    public Integer deleteEvent (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("masuk sini", id+"");
        return db.delete(EVENT_TABLE,
                EVENT_COLUMN_ID + " = ? ",
                new String[]{ Integer.toString(id) });
    }

    public Integer deleteHistory(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(HISTORY_TABLE,
                HISTORY_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public List<Event> getAllEventsFromPlace(String place, String address)
    {
        List<Event> events = new ArrayList<Event>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from event where " + EVENT_COLUMN_PLACE + "=?" + " AND "+ EVENT_COLUMN_ADDRESS +"=?", new String[]{place, address});

        if(res.moveToFirst()){
            do {
                Event event = new Event();
                event.setId(res.getInt(res.getColumnIndex(EVENT_COLUMN_ID)));
                event.setName(res.getString(res.getColumnIndex(EVENT_COLUMN_NAME)));
                event.setPlace(res.getString(res.getColumnIndex(EVENT_COLUMN_PLACE)));
                event.setAddress(res.getString(res.getColumnIndex(EVENT_COLUMN_ADDRESS)));
                event.setDescription(res.getString(res.getColumnIndex(EVENT_COLUMN_DESCRIPTION)));
                event.setTime(res.getString(res.getColumnIndex(EVENT_COLUMN_TIME)));

                events.add(event);
            } while (res.moveToNext());
        }

        return events;
    }

    public List<Event> getAllEvents()
    {
        List<Event> events = new ArrayList<Event>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + EVENT_TABLE, null );
        if(res.moveToFirst()){
            do {
                Event event = new Event();
                event.setId(res.getInt(res.getColumnIndex(EVENT_COLUMN_ID)));
                event.setName(res.getString(res.getColumnIndex(EVENT_COLUMN_NAME)));
                event.setPlace(res.getString(res.getColumnIndex(EVENT_COLUMN_PLACE)));
                event.setAddress(res.getString(res.getColumnIndex(EVENT_COLUMN_ADDRESS)));
                event.setDescription(res.getString(res.getColumnIndex(EVENT_COLUMN_DESCRIPTION)));
                event.setTime(res.getString(res.getColumnIndex(EVENT_COLUMN_TIME)));

                events.add(event);
            } while (res.moveToNext());
        }
        return events;
    }


    public List<History> getAllHistories()
    {
        List<History> histories = new ArrayList<History>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + HISTORY_TABLE, null );
        if(res.moveToFirst()){
            do {
                History history = new History();
                history.setPlace(res.getString(res.getColumnIndex(HISTORY_COLUMN_PLACE)));
                history.setTestimoni(res.getString(res.getColumnIndex(HISTORY_COLUMN_TESTIMONI)));
                history.setAddress(res.getString(res.getColumnIndex(HISTORY_COLUMN_ADDRESS)));
                histories.add(history);
            } while (res.moveToNext());
        }
        return histories;
    }
}
