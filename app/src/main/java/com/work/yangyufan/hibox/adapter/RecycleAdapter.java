package com.work.yangyufan.hibox.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.normalClass.Record;
import com.work.yangyufan.hibox.sqlHelper.MyDatabaseHelper;
import com.work.yangyufan.hibox.sqlHelper.SqliteDB;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{

    private List<Record> recordList;

    private Context context;

    public RecycleAdapter(List<Record> recordList, Context context){

        this.recordList = recordList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * 将数据绑定到Item上。
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textView1.setText(recordList.get(position).getPname());
        holder.textView2.setText(recordList.get(position).getAbout());
        holder.textView3.setText(recordList.get(position).getLabel());
        holder.textView4.setText(recordList.get(position).getPosition());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除这个item
                deleteRecord(position);
            }
        });

        holder.edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改当前名称
                editInfo2(position);
            }
        });

        holder.edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo1(position);
            }
        });

        holder.edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo3(position);
            }
        });

    }

    public void update(){
        recordList.clear();
        if(SqliteDB.getInstance(context).QueryRecord() != 0){
            MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"HiBox_dbname",null,1);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            Cursor cursor=sqLiteDatabase.query("Record",null,null,null,null,null,null);
            cursor.moveToFirst();
            do{
                String name = cursor.getString(cursor.getColumnIndex("recordName"));
                String about = cursor.getString(cursor.getColumnIndex("recordAbout"));
                String label = cursor.getString(cursor.getColumnIndex("recordLabel"));
                String position = cursor.getString(cursor.getColumnIndex("recordPosition"));
                Record s = new Record();
                s.setPname(name);
                s.setAbout(about);
                s.setLabel(label);
                s.setPosition(position);
                recordList.add(s);//取出所有,填充到列表
            }while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }



    private void deleteRecord(final int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("删除提醒");
        builder.setMessage("您确定要删除"+recordList.get(position).getPname()+"吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"HiBox_dbname",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                database.delete("Record","recordName=?",new String[]{recordList.get(position).getPname()});
                recordList.remove(position);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void editInfo2(final int position) {
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改"+recordList.get(position).getLabel());
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //修改更新数据库
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"HiBox_dbname",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("recordLabel",editText.getText().toString());
                database.update("Record",values,"recordLabel=?",new String[]{recordList.get(position).getLabel()});

                //修改list内容
                recordList.get(position).setLabel(editText.getText().toString());
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void editInfo1(final int position) {
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改"+recordList.get(position).getAbout());
        builder.setView(editText);
        //   builder.setMessage("您确定要修改"+recordList.get(position).getName()+"吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //修改更新数据库
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"HiBox_dbname",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("recordAbout",editText.getText().toString());
                database.update("Record",values,"recordAbout=?",new String[]{recordList.get(position).getAbout()});

                //修改list内容
                recordList.get(position).setAbout(editText.getText().toString());
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void editInfo3(final int position) {
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改"+recordList.get(position).getPosition());
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //修改更新数据库
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"HiBox_dbname",null,1);
                SQLiteDatabase database=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("recordPosition",editText.getText().toString());
                database.update("Record",values,"recordPosition=?",new String[]{recordList.get(position).getPosition()});

                //修改list内容
                recordList.get(position).setPosition(editText.getText().toString());
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView delete, edit1, edit2, edit3;

        TextView textView1,textView2,textView3,textView4;

        public ViewHolder(final View itemView) {
            super(itemView);
            edit1 = itemView.findViewById(R.id.edit1);
            edit2 = itemView.findViewById(R.id.edit2);
            edit3 = itemView.findViewById(R.id.edit3);
            delete = itemView.findViewById(R.id.clear);
            textView1 = itemView.findViewById(R.id.input_account);
            textView2 = itemView.findViewById(R.id.input_address);
            textView3 = itemView.findViewById(R.id.input_nikeName);
            textView4 = itemView.findViewById(R.id.input_psw);
        }
    }


}
