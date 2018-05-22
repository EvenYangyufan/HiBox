package com.work.yangyufan.hibox.mainActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.sqlHelper.SqliteDB;

public class LoginActivity extends Activity implements View.OnClickListener{

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private CheckBox rememberPass;

    private EditText inputAccount, inputPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());  //去除状态栏颜色

        rememberPass = findViewById(R.id.remember_pass);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        TextView loginBtn = findViewById(R.id.main_btn_login);
        ImageView signBtn = findViewById(R.id.Sign);
        inputAccount = findViewById(R.id.input_1);
        inputPsw = findViewById(R.id.input_2);

        loginBtn.setOnClickListener(this);
        signBtn.setOnClickListener(this);

        //注册成功后把用户注册用的email传回登录界面
        Intent getIntent = getIntent();
        String account2 = getIntent.getStringExtra("account");
        inputAccount.setText(account2);

        boolean isRemember = pref.getBoolean("remember_password", false);
        if(isRemember){
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            inputAccount.setText(account);
            inputPsw.setText(password);
            rememberPass.setChecked(true);
        }

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            //登录事件
            case R.id.main_btn_login:
                editor = pref.edit();
                String account = inputAccount.getText().toString().trim();
                String password = inputPsw.getText().toString().trim();
                int result= SqliteDB.getInstance(getApplicationContext()).Query(password, account);
                Log.d("LoginActivity", result +"aa");

                if(result == 1){

                    if(rememberPass.isChecked()){
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password",password);
                    }else{
                        editor.clear();
                    }
                    editor.apply();

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    // 这里是自动登录逻辑
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // 登录成功
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("account", inputAccount.getText().toString().trim());
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                    //淡出
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    progressDialog.dismiss();
                                }
                            }, 2000);

                }
                else {
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
                break;
            //注册事件
            case R.id.Sign:
                //进入注册界面
                startActivity(new Intent(LoginActivity.this, SignActivity.class));
                LoginActivity.this.finish();
                //淡出
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            //others
            default:
                break;
        }
    }

}
