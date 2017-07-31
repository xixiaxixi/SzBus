package xixiaxixi.szbus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LineDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SZBUS.db"; //数据库名称
    private static final int VERSION = 1; //数据库版本
    public LineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final String CREATE_EXPRESSION = "create table if not exists LineInfo" +
            "[id]        integer primary key autoincrement   " +
            "[busNo]     text    " +
            "[fromTo]    text    " +
            "[rUrl]      text    " +
            "[aUrl]      text    " +
            "[fromId]    text    " +
            "[toId]      text    ";



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
