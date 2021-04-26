package com.example.final_poc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String ID = "id";
    private static final String CONTACTS_COLUMN_FAVORITE = "favorite";
    private static User user;

    public DBHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CONTACTS_COLUMN_NAME + " text, " + CONTACTS_COLUMN_EMAIL + " text, "
                + CONTACTS_COLUMN_FAVORITE + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CONTACTS_COLUMN_NAME, user.getName());
        cv.put(CONTACTS_COLUMN_EMAIL, user.getEmail());
        cv.put(CONTACTS_COLUMN_FAVORITE, user.getFavorites());
        long insert = db.insert(USER_TABLE, null, cv);
        System.out.println(cv);

        //
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        System.out.println(cursor.getColumnIndex(ID));
        db.close();
        if(insert == -1){
            return false;
        }else {

            return true;
        }


    }
    
    public void remove(String email, String fav){
        String toDB = "";
        ArrayList<String> favarr = this.getFavArray(email);
        if(favarr.contains(fav)){
            favarr.remove(fav);

            for (String txt: favarr) {

                toDB += txt + "~";

            }

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM USER_TABLE WHERE email='" + email + "'";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            String name = cursor.getString(1);

            ContentValues cv = new ContentValues();
            //cv.put(ID, id);
            cv.put(CONTACTS_COLUMN_NAME, name);
            cv.put(CONTACTS_COLUMN_EMAIL, email);
            cv.put(CONTACTS_COLUMN_FAVORITE, toDB);

            System.out.println(fav + " next fav");
            db.update(USER_TABLE, cv, "email=?", new String[]{email});
            db.close();
            cursor.close();





        }else{
            System.out.println("not a favorite");
        }

        
        
        
    }

    //GET USER INFO BASED ON SPECIFIC NAME FROM TABLE
    public User get(String email){

        String query = "SELECT * FROM USER_TABLE WHERE email='" + email + "'";
        //String query = "SELECT name, email, favorite " +
          //      "FROM " + USER_TABLE + " WHERE name=" + user_nm + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            System.out.println(cursor.getString(1) + " name " + cursor.getString(2) + " email " + cursor.getString(3));

            String user_nm = cursor.getString(1);
            String fav = cursor.getString(3);
            System.out.println("email from get is " + email);

            user = new User(-1, user_nm, email, fav);

        }else{
            //no user to get




        }



        cursor.close();
        db.close();
        return user;
    }

    //add favorite to database
    public boolean addfav(String email, String fav){
        ArrayList<String> favArray = this.getFavArray(email);

        if(!favArray.contains(fav)) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM USER_TABLE WHERE email='" + email + "'";
            System.out.println("add fav check point");
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();





            String temp = cursor.getString(3);

            System.out.println(temp + " previous");

            //int id = cursor.getInt(0);
            String name = cursor.getString(1);


            fav = temp + fav + "~";

            ContentValues cv = new ContentValues();
            //cv.put(ID, id);
            cv.put(CONTACTS_COLUMN_NAME, name);
            cv.put(CONTACTS_COLUMN_EMAIL, email);
            cv.put(CONTACTS_COLUMN_FAVORITE, fav);

            System.out.println(fav + " next fav");
            String query1 = "UPDATE USER_TABLE SET column = " + "'" + fav + "'" + " WHERE email=" + "'" + email + "'";
            db.update(USER_TABLE, cv, "email=?", new String[]{email});
            db.close();
            cursor.close();
            return true;
        }else{
            //fav already added.
            return  false;
        }




    }




    public boolean check(String email){
        String query = "SELECT * FROM USER_TABLE WHERE email ='" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        System.out.println("cursor" + cursor);
        System.out.println("cursor first " + cursor.moveToFirst());
        db.close();

        if(cursor.moveToFirst()) {
            System.out.println("there is a user here");
            return false;
        }else{
            return true;
        }


    }

    public ArrayList<String> getFavArray(String email){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM USER_TABLE WHERE email='" + email + "'";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        ArrayList<String> favArr = new ArrayList<String>();
        String fav = cursor.getString(3);

        String newfav = "";
        for(int i = 0; i < fav.length(); i++){

            if(!(fav.charAt(i) == '~')){
                newfav += fav.charAt(i);

            }else{
                favArr.add(newfav);
                newfav = "";

            }




        }
        System.out.println(favArr);
        return favArr;




    }


}
