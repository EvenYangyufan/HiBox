package com.work.yangyufan.hibox.mainActivity;

import android.app.Activity;
import android.os.Bundle;

import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.R;

public class AboutappsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());  //去除状态栏颜色

    }
}
