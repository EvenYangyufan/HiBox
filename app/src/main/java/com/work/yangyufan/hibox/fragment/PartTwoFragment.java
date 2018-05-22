package com.work.yangyufan.hibox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.normalClass.Record;
import com.work.yangyufan.hibox.sqlHelper.SqliteDB;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import static android.app.Activity.RESULT_OK;

/**
 *第二部分
 */

public class PartTwoFragment extends Fragment {

    private int REQUEST_CODE_SCAN = 111;
    private EditText result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addinfofragment_layout, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.input_layout_scan);
        TextView textView = view.findViewById(R.id.input_save);
        TextView textView1 = view.findViewById(R.id.input_clear);
        final EditText editTexta = view.findViewById(R.id.getCode);
        final EditText editText1a = view.findViewById(R.id.input_2);
        final EditText editText2a = view.findViewById(R.id.input_label);
        final EditText editText3a = view.findViewById(R.id.input_position);
        result = view.findViewById(R.id.getCode);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = editTexta.getText().toString().trim();
                String about = editText1a.getText().toString().trim();
                String label = editText2a.getText().toString().trim();
                String position = editText3a.getText().toString().trim();
                Record record = new Record();
                record.setPname(number);
                record.setAbout(about);
                record.setLabel(label);
                record.setPosition(position);
                if(number.isEmpty() || about.isEmpty() || label.isEmpty() || position.isEmpty()){
                    Toast.makeText(getContext(),"请输入未完成的信息！", Toast.LENGTH_SHORT).show();
                }else{
                    int result = SqliteDB.getInstance(getContext()).saveRecord(record);
                    if(result == 1){
                        Toast.makeText(getContext(), "添加成功", Toast.LENGTH_LONG).show();
                        editTexta.setText("");
                        editText1a.setText("");
                        editText2a.setText("");
                        editText3a.setText("");



                    }else if(result == -1){
                        Toast.makeText(getContext(), "该名称已经存在！", Toast.LENGTH_LONG).show();
                        //把名称的editText中的信息去掉
                        editTexta.setText("");
                    }else{
                        Toast.makeText(getContext(), "异常了,卸载吧。", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = view.findViewById(R.id.input_layout_scan);
                Intent intent = new Intent(linearLayout.getContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1a.setText("");
                editText2a.setText("");
                editText3a.setText("");
                editTexta.setText("");
                int lines = SqliteDB.getInstance(view.getContext()).QueryRecord();
                Toast.makeText(getContext(),lines+"清除成功 ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String content;
        super.onActivityResult(requestCode, resultCode, data);
        int REQUEST_CODE_SCAN = 111;
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getContext(),"扫描结果为：" + content, Toast.LENGTH_SHORT).show();
                result.setText(content);
            }
        }
    }
}
