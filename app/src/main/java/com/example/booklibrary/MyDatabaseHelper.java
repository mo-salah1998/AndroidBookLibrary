package com.example.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.RowId;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context ;
    private static final int DataBase_VERSION = 1;
    private static final String DataBaseName ="BookLibrary.db";


    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID ="_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLULMN_AUTHER = "book_author";
    private static final String COLULMN_PAGES = "book_pages";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DataBaseName, null,DataBase_VERSION );
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLULMN_AUTHER + " TEXT, " +
                        COLULMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addbook(String title , String author , int pages ){

        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,title);
        cv.put(COLULMN_AUTHER,author);
        cv.put(COLULMN_PAGES,pages);

        long result = bd.insert(TABLE_NAME,null,cv);
        if(result==-1){
            Toast.makeText(context, "faild", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"successfully!",Toast.LENGTH_LONG).show();
        }
    }


     public Cursor readAllData(){
        String query = "SELECT * fROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null ;

        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor ;

    }

    public void UpdateData(String row_id ,String title , String author , String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLULMN_AUTHER, author);
        cv.put(COLULMN_PAGES, pages);
        long result = db.update(TABLE_NAME, cv,"_id=?", new String[]{row_id});

        if (result != -1 ){
            Toast.makeText(context,"successfully updated !",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Failled updated !",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase() ;
        long result =db.delete(TABLE_NAME , "_id=?",new String[]{row_id});

        if(result == -1 ){
            Toast.makeText(context, "failled to delete ", Toast.LENGTH_SHORT).show();
        }else  {
            Toast.makeText(context, "successfully deleted ", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

    }





}
