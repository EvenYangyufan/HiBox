package com.work.yangyufan.hibox.mainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.Helpclass.Interface.PublicWay;
import com.work.yangyufan.hibox.R;

public class MoreSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_settings);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());  //去除状态栏颜色

        LinearLayout linearLayout = findViewById(R.id.input_layout_4);
        PublicWay.activityList.add(this);
        //退出所有的activity
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< PublicWay.activityList.size(); i++){
                    if (null != PublicWay.activityList.get(i)) {
                        PublicWay.activityList.get(i).finish();
                    }
                }
            }
        });

    }
}

