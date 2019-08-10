package com.ganargatul.submfour.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ganargatul.submfour.model.MovieTvItems;

import java.util.ArrayList;

import static com.ganargatul.submfour.db.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;


    public MovieHelper(Context context) {

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public static MovieHelper getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public ArrayList<MovieTvItems>getAllMovie(){
        ArrayList<MovieTvItems> movieTvItems = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null, DatabaseContract.MovieColoumn._ID ,null);
        cursor.moveToFirst();
        MovieTvItems mMovieTvItems;
        if (cursor.getCount() > 0 ){
            do {
                mMovieTvItems = new MovieTvItems();
                mMovieTvItems.setId(cursor.getInt(0));
                mMovieTvItems.setOverview(String.valueOf(cursor.getString(2)));
                mMovieTvItems.setPhoto(String.valueOf(cursor.getString(3)));
                mMovieTvItems.setTitle(String.valueOf(cursor.getString(1)));
                Log.d("idhelper", String.valueOf(Integer.valueOf(cursor.getInt(0))));
                movieTvItems.add(mMovieTvItems);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieTvItems;
    }
    public Boolean getOne(String name){
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " +DatabaseContract.MovieColoumn.TITLE+ " " + " LIKE " +"'"+name+"'" ;
//        Cursor cursor = database.query(DATABASE_TABLE,null,"'"+name+"'",null,null,null,null ,null);
        Cursor cursor = database.rawQuery(querySingleRecord,null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0 ){

           return true;
        }else if(cursor.getCount() == 0){
            return false;
        }
//        cursor.close();
        return false;
    }
    public long insertMovie(MovieTvItems mMovieTvItems){
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.MovieColoumn.TITLE,mMovieTvItems.getTitle());
        args.put(DatabaseContract.MovieColoumn.IMAGE,mMovieTvItems.getPhoto());
        args.put(DatabaseContract.MovieColoumn.OVERVIEW,mMovieTvItems.getOverview());
        Log.d("helper",mMovieTvItems.getOverview());
        return database.insert(DATABASE_TABLE,null,args);
    }

    public int deleteMovie(String title){
        return database.delete(TABLE_MOVIE, DatabaseContract.MovieColoumn.TITLE+ " = " + "'"+title+"'" , null);
    }

}
