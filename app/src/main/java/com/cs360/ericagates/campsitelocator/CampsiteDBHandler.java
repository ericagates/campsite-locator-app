package com.cs360.ericagates.campsitelocator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class CampsiteDBHandler extends SQLiteOpenHelper {
    //database name and version
    private static final int DB_VER = 1;
    private static final String DB_NAME = "campsiteDB.db";
    private static String DB_PATH = "";
    private SQLiteDatabase mDatabase;
    private Context mContext=null;


    //table
    public static final String TABLE_CAMPSITES = "campsites";
    public static final String TABLE_USERS = "users";

    //campsites table columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY_STATE = "city_state";
    public static final String COLUMN_ZIP = "zip";
    public static final String COLUMN_FEATURE = "feature";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_DETAILS = "details";

    //user table columns
    public static final String COLUMN_USERNAME= "username";
    public static final String COLUMN_PASSWORD = "password";

    //constructor
    public CampsiteDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DB_NAME, factory, DB_VER);

        if(Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getOpPackageName() + "/databases/";
        }
        mContext = context;
    }

    //this method close database if not null
    @Override
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    //this method checks if database exists
    private boolean checkDataBase(){
        SQLiteDatabase tempDB= null;
        try {
            String path = DB_PATH+DB_NAME;
            tempDB =SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e){}

        if (tempDB != null){
            tempDB.close();
        }
        return tempDB!=null?true:false;
    }


    //this method copies the database from the assets folder
    public void copyDataBase(){
        try{
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while((length=myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //this method opens the existing database in the assests folder
    public void openDataBase(){
        String path = DB_PATH+DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

    }

    //this method creates the database if already exists and if it does not, calls the copyDataBase method to create one
    public void createDataBase() {
        boolean isDBExist = checkDataBase();
        if (!isDBExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (Exception e) {
            }
        }
    }


    //this method creates the campsite table when the Database is initialized
    @Override
    public void onCreate(SQLiteDatabase db) {


    }



    // This method closes an open DB if a new one is created.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMPSITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //this method is used to encrypt the password
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    // This method is used to add a User record to the database.
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        String password = user.getPassword();
        String encryptedPassword = md5(password);
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, encryptedPassword);


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    //this method is used to check if the User exists
    public boolean checkUser(String email){
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    //this method is used to check if the Username and password match
    public boolean checkUser(String email, String password){
        String encryptedPassword = md5(password);
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " =?";
        String[] selectionArgs = { email, encryptedPassword };

        Cursor cursor = db.query(TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            return true;
        }
        return false;

    }


    //this method is used to check if the Campsite exists
    public boolean checkCampsite(String campsiteName){
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = { campsiteName };

        Cursor cursor = db.query(TABLE_CAMPSITES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            return false;
        }
        return true;

    }

    // This method is used to add a Campsite record to the database.
    public void addCampsite(Campsite campsite) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, campsite.getName());
        values.put(COLUMN_ADDRESS, campsite.getAddress());
        values.put(COLUMN_CITY_STATE, campsite.getCityState());
        values.put(COLUMN_ZIP, campsite.getZip());
        values.put(COLUMN_FEATURE, campsite.getFeature());
        values.put(COLUMN_PHONE, campsite.getPhone());
        values.put(COLUMN_DETAILS, campsite.getDetails());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CAMPSITES, null, values);
        db.close();
    }

    // implements the search/find functionality
    public ArrayList<Campsite> searchCampsite(String campsiteInfo, String searchType) {
        ArrayList<Campsite> campsites = new ArrayList<Campsite>();
        String query;
        if (searchType.equals("Name"))  {
            query = "SELECT * FROM " +
                    TABLE_CAMPSITES + " WHERE " + COLUMN_NAME + " LIKE \"" + campsiteInfo + "\"";
        } else if (searchType.equals("City, State")) {
            query = "SELECT * FROM " +
                    TABLE_CAMPSITES + " WHERE " + COLUMN_CITY_STATE + " LIKE \"" + campsiteInfo + "\"";
        } else {
            query = "SELECT * FROM " +
                    TABLE_CAMPSITES + " WHERE " + COLUMN_FEATURE + " LIKE \"" + campsiteInfo+ "\"";

        }


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);



        if (cursor == null) {
            campsites = null;
        } else {

            while (cursor.moveToNext()) {
                Campsite campsite = new Campsite();
                campsite.setID(Integer.parseInt(cursor.getString(0)));
                campsite.setName(cursor.getString(1));
                campsite.setAddress(cursor.getString(2));
                campsite.setCityState(cursor.getString(3));
                campsite.setZip(cursor.getString(4));
                campsite.setFeature(cursor.getString(5));
                campsite.setPhone(cursor.getString(6));
                campsite.setDetails(cursor.getString(7));
                campsites.add(campsite);

            }
        }
        cursor.close();
        db.close();
        return campsites;
    }


    // This method is used to Update a Campsite record to the database.
    public int updateCampsite(Campsite campsite) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        int result;

        String campsiteName = campsite.getName();

        values.put(COLUMN_ADDRESS, campsite.getAddress());
        values.put(COLUMN_CITY_STATE, campsite.getCityState());
        values.put(COLUMN_ZIP, campsite.getZip());
        values.put(COLUMN_FEATURE, campsite.getFeature());
        values.put(COLUMN_PHONE, campsite.getPhone());
        values.put(COLUMN_DETAILS, campsite.getDetails());


        result = db.update(TABLE_CAMPSITES, values, COLUMN_NAME + " LIKE \"" + campsiteName + "\"", null);

        db.close();
        return result;
    }

    // implements the delete campsite functionality
    public boolean deleteCampsite(String campsiteName) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_CAMPSITES +
                " WHERE " + COLUMN_NAME + " = \"" + campsiteName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        Campsite campsite = new Campsite();

        if (cursor.moveToFirst()) {
            campsite.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_CAMPSITES, COLUMN_ID + " = ?",
                new String[] {String.valueOf(campsite.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}

