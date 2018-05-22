package com.work.yangyufan.hibox.mainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.R;

/**
 *初始显示欢迎界面
 * YangYuFan 2018/5/16
 */

public class LoadingActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());
        ImageView iv = findViewById(R.id.iv);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        iv.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                LoadingActivity.this.finish();
                //淡出
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}