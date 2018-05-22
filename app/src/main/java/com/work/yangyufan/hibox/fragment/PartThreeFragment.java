package com.work.yangyufan.hibox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.mainActivity.AboutappsActivity;
import com.work.yangyufan.hibox.mainActivity.InfoActivity;
import com.work.yangyufan.hibox.mainActivity.MainActivity;
import com.work.yangyufan.hibox.mainActivity.MoreSettingsActivity;
import com.work.yangyufan.hibox.mainActivity.VersionActivity;

/**
 *第三部分
 */

public class PartThreeFragment extends Fragment implements View.OnClickListener {

    public TextView account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_layout_settings, container, false);

        //String yyf = "yyf@qq.com";
        LinearLayout linearLayout = view.findViewById(R.id.input_layout_4);
        LinearLayout linearLayout1 = view.findViewById(R.id.input_layout_about);
        LinearLayout linearLayout2 = view.findViewById(R.id.input_layout_version);
        LinearLayout linearLayout3 = view.findViewById(R.id.input_layout_name);
        MainActivity activity =(MainActivity)getActivity();
        Log.v("53321",activity.account);
        account = view.findViewById(R.id.id1);
        account.setText(activity.account);

        linearLayout.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){

            case R.id.input_layout_4:
                LinearLayout linearLayout = view.findViewById(R.id.input_layout_4);
                startActivity(new Intent(linearLayout.getContext(), MoreSettingsActivity.class));
                break;

            case R.id.input_layout_about:
                LinearLayout linearLayout1 = view.findViewById(R.id.input_layout_about);
                startActivity(new Intent(linearLayout1.getContext(), AboutappsActivity.class));
                break;

            case R.id.input_layout_version:
                LinearLayout linearLayout2 = view.findViewById(R.id.input_layout_version);
                startActivity(new Intent(linearLayout2.getContext(), VersionActivity.class));
                break;

            case R.id.input_layout_name:
                account = view.findViewById(R.id.id1);
                LinearLayout linearLayout3 = view.findViewById(R.id.input_layout_name);
                Intent intent = new Intent(linearLayout3.getContext(),InfoActivity.class);
                intent.putExtra("account", account.getText());
                startActivity(intent);
                break;
        }
    }

}
