package com.work.yangyufan.hibox.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.Helpclass.Interface.PublicWay;
import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.fragment.PartTwoFragment;
import com.work.yangyufan.hibox.fragment.PartThreeFragment;
import com.work.yangyufan.hibox.fragment.PartOneFragment;
import com.work.yangyufan.hibox.sqlHelper.MyDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

//主要功能实现类
public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationBar.OnTabSelectedListener {

    public String account;

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private FragmentTransaction transaction;
    private PartTwoFragment partTwoFragment;
    private PartOneFragment partOneFragment;
    private PartThreeFragment partThreeFragment;
    private Fragment mFragment;//当前显示的Fragment
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());  //去除状态栏的颜色
        ButterKnife.bind(this);
        bottomNavigationBar.findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);
        //底部导航栏的初始化设置
        BottomNavigationBar();
        dbHelper = new MyDatabaseHelper(this,"HiBox_dbname.db",null,1);
        dbHelper.getWritableDatabase();
        PublicWay.activityList.add(this);

        Intent getIntent = getIntent();
        account = getIntent.getStringExtra("account");
    }

    @Override
    public void onClick(View v){

    }

    private void BottomNavigationBar(){
        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home,"主页"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_add,"添加"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_set,"我"))
                .initialise();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);  //按钮的样式
        bottomNavigationBar
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor("#ffffff")
                .setBarBackgroundColor("#EEEEEE");
        initFragment();
    }

    @Override
    public void onTabSelected(int position) {
        //未选中->选中
        switch (position){
            case 0:
                switchFragment(partOneFragment);
                partOneFragment.reUpdate();
                break;
            case 1:
                switchFragment(partTwoFragment);
                break;
            case 2:
                switchFragment(partThreeFragment);
                Log.v("111", account);
                //partThreeFragment.updateUI(account);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {
        //选中->未选中

    }

    @Override
    public void onTabReselected(int position) {
        //选中->选中
    }

    private void initFragment() {
        partOneFragment = new PartOneFragment();
        partTwoFragment = new PartTwoFragment();
        partThreeFragment = new PartThreeFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, partOneFragment).commit();

        mFragment = partOneFragment;

    }

    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if(mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment).add(R.id.frameLayout,fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "action_settings", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        return true;
    }
}
