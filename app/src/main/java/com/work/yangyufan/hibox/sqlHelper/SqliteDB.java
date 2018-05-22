package com.work.yangyufan.hibox.sqlHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.work.yangyufan.hibox.normalClass.Record;
import com.work.yangyufan.hibox.normalClass.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yang_yu_fan on 2018/5/5.
 */

public class SqliteDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "HiBox_dbname";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static SqliteDB sqliteDB;

    private SQLiteDatabase db;

    private SqliteDB(Context context) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取SqliteDB实例
     * @param context
     */
    public synchronized static SqliteDB getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new SqliteDB(context);
        }
        return sqliteDB;
    }

    /**
     * 将User实例存储到数据库。
     */
    public int saveUser(User user) {
        if (user != null) {
            Cursor cursor = db.rawQuery("select * from User where email=?", new String[]{user.getEmail().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User(username,address,email,phone_number,password) values(?,?,?,?,?) ", new String[]{user.getUsername().toString(),user.getAddress().toString(), user.getEmail().toString(), user.getPhone().toString(), user.getPassword().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }
                return 1;
            }
        }
        else {
            return 0;
        }
    }

    /**
     * 从数据库读取User信息。
     */
    public User loadUser(String email) {
        User user = new User();
        //List<User> list = new ArrayList<User>();
        //Cursor cursor = db.query("User", null, "where email=? ", null, null, null, null);
        Cursor cursor = db.rawQuery("select * from User where email=?", new String[]{email});
        if (cursor.moveToFirst()) {
            do {
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone_number")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                //list.add(user);
            } while (cursor.moveToNext());
        }
        return user;
    }

    /***
     * 登录判断
     */
    public int Query(String pwd, String email)
    {

        HashMap<String,String> hashmap=new HashMap<String,String>();
        Cursor cursor =db.rawQuery("select * from User where email=?", new String[]{email});

        if (cursor.getCount()>0)
        {
            Cursor pwdcursor =db.rawQuery("select * from User where password=? and email=?",new String[]{pwd,email});
            if (pwdcursor.getCount()>0)
            {
                return 1;
            }
            else {
                return -1;
            }
        }
        else {
            return 0;
        }

    }

    /***
     * 将Record实例存储到数据库。
     */
    public int saveRecord(Record record) {
        if (record != null) {
            Cursor cursor = db.rawQuery("select * from Record where recordName=?", new String[]{record.getPname().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into Record(recordName, recordAbout, recordLabel, recordPosition) values(?,?,?,?) ", new String[]{record.getPname().toString(),record.getAbout().toString(), record.getLabel().toString(), record.getPosition().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }

                return 1;
            }
        }
        else {

            return 0;
        }
    }
    /**
     * 从数据库读取Record信息(已知recordName)
     */
    public Record loadRecord(String name) {
        Record record = new Record();
        Cursor cursor = db.query("Record", null, "recordName = "+name, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                record.setPname(cursor.getString(cursor.getColumnIndex("recordName")));
                record.setPosition(cursor.getString(cursor.getColumnIndex("recordPosition")));
                record.setAbout(cursor.getString(cursor.getColumnIndex("recordAbout")));
                record.setLabel(cursor.getString(cursor.getColumnIndex("recordLabel")));
            } while (cursor.moveToNext());
        }
        return record;
    }

    /***
     * 判断有几条record记录
     */
    public int QueryRecord() {

        int a;
        HashMap<String, String> hashmap = new HashMap<String, String>();
        Cursor cursor = db.rawQuery("select * from Record ", null);
        a = cursor.getCount();
        return a;
    }

    public Record loadRecord(){
        Record record = new Record();
        List<Record> recordList = new ArrayList<Record>();
        Cursor cursor = db.query("Record", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                record.setPname(cursor.getString(cursor.getColumnIndex("recordName")));
                record.setPosition(cursor.getString(cursor.getColumnIndex("recordPosition")));
                record.setAbout(cursor.getString(cursor.getColumnIndex("recordAbout")));
                record.setLabel(cursor.getString(cursor.getColumnIndex("recordLabel")));
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        return record;
    }


    //修改数据库中的个人信息
    public int  updateUser(User user) {
        if (user != null) {
            Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{user.getUsername().toString()});
            if (cursor.getCount() <= 0) {
                return -1;
            } else {
                try {
                    db.execSQL("update User set username=?, address=?, phone_number=?, password=?", new String[]{user.getUsername().toString(),user.getAddress().toString(),user.getPhone().toString(),user.getPassword().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }

                return 1;
            }
        }
        else {
            return 0;
        }
    }


}

