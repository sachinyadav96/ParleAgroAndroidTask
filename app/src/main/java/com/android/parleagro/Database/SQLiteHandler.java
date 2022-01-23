package com.android.parleagro.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.android.parleagro.model.MachineModel;
import com.android.parleagro.model.PlantTypeModel;
import com.android.parleagro.model.RecordModel;
import com.android.parleagro.model.TimeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SQLiteHandler extends SQLiteOpenHelper {


    /**
     * DATE AND TIME
     */
    private static final String PER_TIME = "time";


    /**
     * PLANT TYPE TABLE
     */
    private static final String PLANT_TYPE_TABLE = "plant_type";
    private static final String PER_PRIMARY_PLANT_TYPE_ID = "plant_type_id";
    private static final String PER_PLANT_TYPE_TITLE = "plant_type_name";


    /**
     * MACHINE TYPE TABLE
     */
    private static final String MACHINE_TABLE = "machine";
    private static final String PER_PRIMARY_MACHINE_ID = "machine_id";
    private static final String PER_MACHINE_TITLE = "machine_name";

    /**
     * TIME TABLE
     */
    private static final String TIME_TABLE = "time";
    private static final String PER_PRIMARY_TIME_ID = "time_id";
    private static final String PER_TIME_VALUE = "time_value";

    /**
     * Record TABLE
     */
    private static final String INPUT_RECORD_TABLE = "record";
    private static final String PER_PRIMARY_RECORD_ID = "id";
    private static final String PER_RECORD_TIME_ID = "time_id";
    private static final String PER_RECORD_TIME_Value = "time_value";
    private static final String PER_RECORD_PLANT_ID = "plant_id";
    private static final String PER_RECORD_MACHINE_ID = "machine_id";
    private static final String PER_RECORD_Fill_Volume_All_Valves = "Fill_Volume_All_Valves";
    private static final String PER_RECORD_Fill_Volume_3_valves_every_Hour = "Fill_Volume_3_valves_every_Hour";
    private static final String PER_RECORD_Fill_Closure_Torque = "Closure_Torque";
    private static final String PER_RECORD_Closure_jump_test = "Closure_jump_test";
    private static final String PER_RECORD_Closure_Pifer_band = "Closure_Pifer_band";
    private static final String PER_RECORD_Closure_Secure_Seal = "Closure_Secure_Seal";
    private static final String PER_RECORD_Stress_crack_Test = "Stress_crack_Test";
    private static final String PER_RECORD_Drop_Test = "Drop_Test";
    private static final String PER_RECORD_Package_Appearance = "Package_Appearance";
    private static final String PER_RECORD_Date_Coding_Rub_test = "Date_Coding_Rub_test";
    private static final String PER_DATE = "date";


    private final Context mContext;


    /**
     * Add Plant TYPE
     */
    private final String PLANT_TYPE_TABLE_QUERY =
            "CREATE TABLE " + PLANT_TYPE_TABLE + " (" + PER_PRIMARY_PLANT_TYPE_ID + " INTEGER PRIMARY" +
                    " KEY, " + PER_PLANT_TYPE_TITLE + " TEXT )";

    /**
     * Add time
     */
    private final String TIME_TABLE_QUERY =
            "CREATE TABLE " + TIME_TABLE + " (" + PER_PRIMARY_TIME_ID + " INTEGER PRIMARY" +
                    " KEY, " + PER_TIME_VALUE + " TEXT )";


    /**
     * Add Machine Name
     */
    private final String MACHINE_TABLE_QUERY =
            "CREATE TABLE " + MACHINE_TABLE + " ("
                    + PER_PRIMARY_MACHINE_ID + " INTEGER PRIMARY KEY, "
                    + PER_MACHINE_TITLE + " TEXT, "
                    + PER_PRIMARY_PLANT_TYPE_ID + " INTEGER )";

    /**
     * Add Record
     */
    private final String RECORD_TABLE_QUERY =
            "CREATE TABLE " + INPUT_RECORD_TABLE + " ("
                    + PER_PRIMARY_RECORD_ID + " INTEGER PRIMARY KEY, "
                    + PER_RECORD_TIME_ID + " INTEGER, "
                    + PER_RECORD_TIME_Value + " TEXT, "
                    + PER_RECORD_PLANT_ID + " INTEGER, "
                    + PER_RECORD_MACHINE_ID + " INTEGER, "
                    + PER_RECORD_Fill_Volume_All_Valves + " INTEGER, "
                    + PER_RECORD_Fill_Volume_3_valves_every_Hour + " NUMERIC, "
                    + PER_RECORD_Fill_Closure_Torque + " INTEGER, "
                    + PER_RECORD_Closure_jump_test + " INTEGER, "
                    + PER_RECORD_Closure_Pifer_band + " INTEGER, "
                    + PER_RECORD_Closure_Secure_Seal + " INTEGER, "
                    + PER_RECORD_Stress_crack_Test + " INTEGER, "
                    + PER_RECORD_Drop_Test + " INTEGER, "
                    + PER_RECORD_Package_Appearance + " TEXT, "
                    + PER_RECORD_Date_Coding_Rub_test + " TEXT, "
                    + PER_DATE + " TEXT )";

    public SQLiteHandler(Context mContext) {
        super(mContext, "ParleAgro.db", null, 1);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PLANT_TYPE_TABLE_QUERY);
        db.execSQL(MACHINE_TABLE_QUERY);
        db.execSQL(TIME_TABLE_QUERY);
        db.execSQL(RECORD_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(mContext, "onUpgrade " + oldVersion + "   " + newVersion,
                Toast.LENGTH_SHORT).show();

        switch (oldVersion) {
            case 1:
                break;
        }
    }


    public void DeletePlantTypeModel(Integer plant_id) {
        String query =
                "DELETE FROM " + PLANT_TYPE_TABLE + " WHERE " + PER_PRIMARY_PLANT_TYPE_ID + " = " + plant_id;
        SQLiteDatabase sdb = getWritableDatabase();
        sdb.execSQL(query);
        sdb.close();
    }

    /**
     * Insert data into tables
     */
    public void insertIntoPlantType(PlantTypeModel plant) {
        String query = "INSERT INTO " + PLANT_TYPE_TABLE + " (" + PER_PLANT_TYPE_TITLE + ") VALUES (?)";
        SQLiteDatabase sdb = getWritableDatabase();
        SQLiteStatement statement = sdb.compileStatement(query);
        bindString(statement, 1, plant.getTitle());
        statement.executeInsert();
        statement.close();
        sdb.close();
    }

    public void insertIntoTime(TimeModel time) {
        String query = "INSERT INTO " + TIME_TABLE + " (" + PER_TIME_VALUE + ") VALUES (?)";
        SQLiteDatabase sdb = getWritableDatabase();
        SQLiteStatement statement = sdb.compileStatement(query);
        bindString(statement, 1, time.getTitle());
        statement.executeInsert();
        statement.close();
        sdb.close();
    }

    public void insertIntoMachine(MachineModel machine) {
        String query = "INSERT INTO " + MACHINE_TABLE + " (" + PER_MACHINE_TITLE + ", " + PER_PRIMARY_PLANT_TYPE_ID + ") VALUES (?,?)";
        SQLiteDatabase sdb = getWritableDatabase();
        SQLiteStatement statement = sdb.compileStatement(query);
        bindString(statement, 1, machine.getTitle());
        statement.bindLong(2, machine.getPantid());
        statement.executeInsert();
        statement.close();
        sdb.close();
    }

    public void insertIntoRecord(RecordModel record) {
        String query = "INSERT INTO " + INPUT_RECORD_TABLE + " ("
                + PER_RECORD_TIME_ID
                + ", " + PER_RECORD_TIME_Value
                + ", " + PER_RECORD_PLANT_ID
                + ", " + PER_RECORD_MACHINE_ID
                + ", " + PER_RECORD_Fill_Volume_All_Valves
                + ", " + PER_RECORD_Fill_Volume_3_valves_every_Hour
                + ", " + PER_RECORD_Fill_Closure_Torque
                + ", " + PER_RECORD_Closure_jump_test
                + ", " + PER_RECORD_Closure_Pifer_band
                + ", " + PER_RECORD_Closure_Secure_Seal
                + ", " + PER_RECORD_Stress_crack_Test
                + ", " + PER_RECORD_Drop_Test
                + ", " + PER_RECORD_Package_Appearance
                + ", " + PER_RECORD_Date_Coding_Rub_test
                + ", " + PER_DATE

                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteDatabase sdb = getWritableDatabase();
        SQLiteStatement statement = sdb.compileStatement(query);
        statement.bindLong(1, record.getTimeID());
        bindString(statement, 2, record.getTimeValue());
        statement.bindLong(3, record.getPlantID());
        statement.bindLong(4, record.getMachineID());
        statement.bindLong(5, record.getFill_Volume_All_Valves());
        statement.bindDouble(6, record.getFill_Volume_3_valves_every_Hour());
        statement.bindLong(7, record.getFill_Closure_Torque());
        statement.bindLong(8, record.getClosure_jump_test());
        statement.bindLong(9, record.getClosure_Pifer_band());
        statement.bindLong(10, record.getClosure_Secure_Seal());
        statement.bindLong(11, record.getStress_crack_Test());
        statement.bindLong(12, record.getDrop_Test());
        bindString(statement, 13, record.getPackage_Appearance());
        bindString(statement, 14, record.getDate_Coding_Rub_test());
        bindString(statement, 15, record.getDATE());

        statement.executeInsert();
        statement.close();
        sdb.close();
    }

    /**
     * Modals to get data
     */
    public ArrayList<PlantTypeModel> getPlantType() {
        SQLiteDatabase sdb = getReadableDatabase();
        String sql =
                "SELECT * FROM " + PLANT_TYPE_TABLE + " ORDER BY  " + PER_PRIMARY_PLANT_TYPE_ID + " " + "asc";
        Cursor cursor = sdb.rawQuery(sql, null);
        ArrayList<PlantTypeModel> PlantTypeModel = new ArrayList<>();
        while (cursor.moveToNext()) {
            PlantTypeModel plant = new PlantTypeModel();
            plant.setId(cursor.getInt(0));
            plant.setTitle(cursor.getString(1));
            PlantTypeModel.add(plant);
        }
        cursor.close();
        sdb.close();
        return PlantTypeModel;
    }

    public ArrayList<RecordModel> getRecordDate() {
        SQLiteDatabase sdb = getReadableDatabase();
        String sql =
                "SELECT * FROM " + INPUT_RECORD_TABLE + " ORDER BY  " + PER_PRIMARY_RECORD_ID + " " + "asc";
        Cursor cursor = sdb.rawQuery(sql, null);
        ArrayList<RecordModel> RecordModel = new ArrayList<>();
        while (cursor.moveToNext()) {
            RecordModel record = new RecordModel();
            record.setID(cursor.getInt(0));
            record.setTimeID(cursor.getInt(1));
            record.setTimeValue(cursor.getString(2));
            record.setPlantID(cursor.getInt(3));
            record.setMachineID(cursor.getInt(4));
            record.setFill_Volume_All_Valves(cursor.getInt(5));
            record.setFill_Volume_3_valves_every_Hour(cursor.getDouble(6));
            record.setFill_Closure_Torque(cursor.getInt(7));
            record.setClosure_jump_test(cursor.getInt(8));
            record.setClosure_Pifer_band(cursor.getInt(9));
            record.setClosure_Secure_Seal(cursor.getInt(10));
            record.setStress_crack_Test(cursor.getInt(11));
            record.setDrop_Test(cursor.getInt(12));
            record.setPackage_Appearance(cursor.getString(13));
            record.setDate_Coding_Rub_test(cursor.getString(14));
            record.setDATE(cursor.getString(15));
            RecordModel.add(record);
        }
        cursor.close();
        sdb.close();
        return RecordModel;
    }

    public ArrayList<TimeModel> getTime() {
        SQLiteDatabase sdb = getReadableDatabase();
        String sql =
                "SELECT * FROM " + TIME_TABLE + " ORDER BY  " + PER_PRIMARY_TIME_ID + " " + "asc";
        Cursor cursor = sdb.rawQuery(sql, null);
        ArrayList<TimeModel> TimeModel = new ArrayList<>();
        while (cursor.moveToNext()) {
            TimeModel time = new TimeModel();
            time.setId(cursor.getInt(0));
            time.setTitle(cursor.getString(1));
            TimeModel.add(time);
        }
        cursor.close();
        sdb.close();
        return TimeModel;
    }

    public ArrayList<MachineModel> getMachine(Integer SelectedPlantID) {
        SQLiteDatabase sdb = getReadableDatabase();
        String sql = ("SELECT * FROM " + MACHINE_TABLE + " " +
                "WHERE plant_type_id ='" + SelectedPlantID + "'" + " " +
                "ORDER BY " + PER_PRIMARY_MACHINE_ID + " " +
                "asc");
        Log.e("sql", sql);
        Cursor cursor = sdb.rawQuery(sql, null);
        ArrayList<MachineModel> machineModels = new ArrayList<>();
        while (cursor.moveToNext()) {
            MachineModel mach = new MachineModel();
            mach.setId(cursor.getInt(0));
            mach.setTitle(cursor.getString(1));
            mach.setPantid(cursor.getInt(2));
            machineModels.add(mach);
        }
        cursor.close();
        sdb.close();
        return machineModels;
    }

    /**
     * JSON Methods
     */
    public String getPlantTypeJSON() {
        ArrayList<PlantTypeModel> list = getPlantType();
        JSONArray ja = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                PlantTypeModel p = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("plant_type_id", p.getId());
                jo.put("plant_type_name", p.getTitle());
                ja.put(jo);
            }
            return ja.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getTimeJSON() {
        ArrayList<TimeModel> list = getTime();
        JSONArray ja = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                TimeModel t = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("time_id", t.getId());
                jo.put("time_value", t.getTitle());
                ja.put(jo);
            }
            return ja.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getRecordJSON() {
        ArrayList<RecordModel> list = getRecordDate();
        JSONArray ja = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                RecordModel r = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("id", r.getID());
                jo.put("time_id", r.getTimeID());
                jo.put("time_value", r.getTimeValue());
                jo.put("plant_id", r.getPlantID());
                jo.put("machine_id", r.getMachineID());
                jo.put("fill_all_v", r.getFill_Volume_All_Valves());
                jo.put("fill_3_v", r.getFill_Volume_3_valves_every_Hour());
                jo.put("closure_torque", r.getFill_Closure_Torque());
                jo.put("jump_test", r.getClosure_jump_test());
                jo.put("pifer_band", r.getClosure_Pifer_band());
                jo.put("secure_seal", r.getClosure_Secure_Seal());
                jo.put("crack_test", r.getStress_crack_Test());
                jo.put("drop_test", r.getDrop_Test());
                jo.put("package_appearance", r.getPackage_Appearance());
                jo.put("coding_rub_test", r.getDate_Coding_Rub_test());
                jo.put("date", r.getDATE());
                ja.put(jo);
            }
            return ja.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getMachineJSON(Integer SelectedPlantID) {
        ArrayList<MachineModel> list = getMachine(SelectedPlantID);
        JSONArray ja = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                MachineModel m = list.get(i);
                JSONObject jo = new JSONObject();
                jo.put("machine_id", m.getId());
                jo.put("machine_name", m.getTitle());
                jo.put("plant_type_id", m.getPantid());
                ja.put(jo);
            }
            return ja.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private void bindString(SQLiteStatement statement, int index, String value) {
        if (value == null)
            statement.bindNull(index);
        else
            statement.bindString(index, value);
    }


}
