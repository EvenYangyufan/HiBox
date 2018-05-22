package com.work.yangyufan.hibox.sqlHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table User ("
            + "id integer primary key autoincrement, "
            + "username varchar(20), "
            + "address varchar(20), "
            + "email varchar(25), "
            + "phone_number varchar(15), "
            + "password varchar(20))";

    public static final String CREATE_RECORD = "create table Record ("
            + "id integer primary key autoincrement, "
            + "recordName varchar(20), "
            + "recordAbout varchar(50), "
            + "recordLabel varchar(20), "
            + "recordPosition varchar(20))";



    private Context mContext;

    //构造方法：第一个参数Context，第二个参数数据库名，第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，一般传入的都是null，第四个参数表示目前库的版本号（用于对库进行升级）
    public  MyDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory , int version){
        super(context,name ,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_RECORD);
        //创建成功
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
