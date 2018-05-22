package com.work.yangyufan.hibox.Helpclass.Interface;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

/**
 * 帮助去掉状态栏的颜色
 * YangYuFan  2018/5/16
 */

public class ImmersiveStatusBar {

    private static ImmersiveStatusBar immersiveStatusbar;

    // 构造函数私有化
    private ImmersiveStatusBar() {

    }

    public static ImmersiveStatusBar getInstance() {

        if (immersiveStatusbar == null) {
            // 加锁提高使用效率
            synchronized (ImmersiveStatusBar.class) {
                if (immersiveStatusbar == null) {
                    immersiveStatusbar = new ImmersiveStatusBar();
                }
            }
        }
        return immersiveStatusbar;

    }

    /***
     * 状态栏透明化
     * @param window    Window对象
     */
    public void Immersive(Window window) {

        if (Build.VERSION.SDK_INT >= 21) {

            View view = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            view.setSystemUiVisibility(option);
            // 将状态栏设置成透明色
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        // 将ActionBar隐藏
       // actionBar.hide();
    }
}
