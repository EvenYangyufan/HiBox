package com.work.yangyufan.hibox.mainActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.normalClass.User;
import com.work.yangyufan.hibox.sqlHelper.MyDatabaseHelper;
import com.work.yangyufan.hibox.sqlHelper.SqliteDB;

public class InfoActivity extends Activity implements View.OnClickListener{

    private TextView a, b, c, d, e;

    private TextView bb, cc, dd, ee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());  //去除状态栏颜色

        Intent getIntent = getIntent();
        String account = getIntent.getStringExtra("account");
        Log.v("info",account);
        a = findViewById(R.id.input_account);
        b = findViewById(R.id.input_address);
        c = findViewById(R.id.input_nikeName);
        d = findViewById(R.id.input_phone);
        e = findViewById(R.id.input_psw);

        bb = findViewById(R.id.button2);
        cc = findViewById(R.id.button3);
        dd = findViewById(R.id.button4);
        ee = findViewById(R.id.button5);

        User user;
        user = SqliteDB.getInstance(getBaseContext()).loadUser(account);
        a.setText(user.getEmail());
        b.setText(user.getAddress());
        c.setText(user.getUsername());
        d.setText(user.getPhone());
        e.setText(user.getPassword());

        bb.setOnClickListener(this);
        cc.setOnClickListener(this);
        dd.setOnClickListener(this);
        ee.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button2:
                final TextView textView = findViewById(R.id.input_address);
                final EditText editText = new EditText(InfoActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                builder.setTitle("修改"+textView.getText());
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //修改更新数据库
                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(InfoActivity.this,"HiBox_dbname",null,1);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("address",editText.getText().toString());
                        database.update("User",values,"address=?",new String[]{textView.getText().toString()});
                        textView.setText(editText.getText().toString());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;

            case R.id.button3:
                final TextView textView2 = findViewById(R.id.input_nikeName);
                final EditText editText2 = new EditText(InfoActivity.this);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(InfoActivity.this);
                builder2.setTitle("修改"+textView2.getText());
                builder2.setView(editText2);
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //修改更新数据库
                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(InfoActivity.this,"HiBox_dbname",null,1);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("username",editText2.getText().toString());
                        database.update("User",values,"username=?",new String[]{textView2.getText().toString()});
                        textView2.setText(editText2.getText().toString());
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder2.show();
                break;
            case R.id.button4:
                final TextView textView3 = findViewById(R.id.input_phone);
                final EditText editText3 = new EditText(InfoActivity.this);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(InfoActivity.this);
                builder3.setTitle("修改"+textView3.getText());
                builder3.setView(editText3);
                builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //修改更新数据库
                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(InfoActivity.this,"HiBox_dbname",null,1);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("phone_number",editText3.getText().toString());
                        database.update("User",values,"phone_number=?",new String[]{textView3.getText().toString()});
                        textView3.setText(editText3.getText().toString());
                    }
                });
                builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder3.show();
                break;

            case R.id.button5:
                final TextView textView4 = findViewById(R.id.input_psw);
                final EditText editText4 = new EditText(InfoActivity.this);
                AlertDialog.Builder builder4 = new AlertDialog.Builder(InfoActivity.this);
                builder4.setTitle("修改"+textView4.getText());
                builder4.setView(editText4);
                builder4.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //修改更新数据库
                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(InfoActivity.this,"HiBox_dbname",null,1);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("password",editText4.getText().toString());
                        database.update("User",values,"password=?",new String[]{textView4.getText().toString()});
                        textView4.setText(editText4.getText().toString());
                    }
                });
                builder4.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder4.show();
                break;

        }
    }
}
