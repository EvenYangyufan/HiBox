package com.work.yangyufan.hibox.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.adapter.RecycleAdapter;
import com.work.yangyufan.hibox.normalClass.Record;
import com.work.yangyufan.hibox.sqlHelper.MyDatabaseHelper;
import com.work.yangyufan.hibox.sqlHelper.SqliteDB;

import java.util.ArrayList;
import java.util.List;

/**
 *第一部分
 */

public class PartOneFragment extends Fragment {

    private List<Record> recordList = new ArrayList<>();
    private RecycleAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homesetfragment_layout, container, false);

        recordList.clear();
        if(SqliteDB.getInstance(getContext()).QueryRecord() != 0){
            MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext(),"HiBox_dbname",null,1);
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

        adapter = new RecycleAdapter(recordList,getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        TextView textView = view.findViewById(R.id.getAmount);
        int a = SqliteDB.getInstance(getContext()).QueryRecord();
        textView.setText(a + "条记录");

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void reUpdate(){
        adapter.update();
    }


    private  void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }



}
