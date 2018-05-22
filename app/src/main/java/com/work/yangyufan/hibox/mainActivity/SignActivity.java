package com.work.yangyufan.hibox.mainActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.work.yangyufan.hibox.Helpclass.Interface.ImmersiveStatusBar;
import com.work.yangyufan.hibox.R;
import com.work.yangyufan.hibox.normalClass.User;
import com.work.yangyufan.hibox.sqlHelper.SqliteDB;

public class SignActivity extends Activity implements View.OnClickListener{

    private EditText inputAccount, inputAddress, inputNikeName, inputPhone, inputPsw, inputRePsw;

    private Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ImmersiveStatusBar.getInstance().Immersive(getWindow());  //去除状态栏颜色

        ImageView imgBack = findViewById(R.id.back_login);
        ImageView imgSave = findViewById(R.id.saveSign);
        inputAccount = findViewById(R.id.input_account);
        inputAddress = findViewById(R.id.input_address);
        inputNikeName = findViewById(R.id.input_nikeName);
        inputPhone = findViewById(R.id.input_phone);
        inputPsw = findViewById(R.id.input_psw);
        inputRePsw = findViewById(R.id.input_rePsw);

        imgBack.setOnClickListener(this);
        imgSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.back_login:
                //返回登录界面
                startActivity(new Intent(SignActivity.this, LoginActivity.class));
                SignActivity.this.finish();
                //淡出
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.saveSign:
                //保存注册
                if(validate()){
                    String email = inputAccount.getText().toString().trim();
                    String address = inputAddress.getText().toString().trim();
                    String name = inputNikeName.getText().toString().trim();
                    String phone = inputPhone.getText().toString().trim();
                    String password = inputPsw.getText().toString().trim();
                    Log.v("SignActivity", email + address + name + phone + password);
                    User user=new User();
                    user.setUsername(name);
                    user.setAddress(address);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setPassword(password);

                    int result= SqliteDB.getInstance(getApplicationContext()).saveUser(user);
                    if(result == 1){
                        final ProgressDialog progressDialog = new ProgressDialog(SignActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Registration...");
                        progressDialog.show();
                        // 这里是自动登录逻辑
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // 登录成功
                                        EditText inputAccount = findViewById(R.id.input_account);
                                        String account = inputAccount.getText().toString().trim();
                                        //返回登录界面并传值
                                        Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                                        intent.putExtra("account", account);
                                        startActivity(intent);
                                        SignActivity.this.finish();
                                        //淡出
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                        progressDialog.dismiss();
                                    }
                                }, 2000);
                    }
                    else if (result == -1){
                        Toast.makeText(getBaseContext(), "用户名已经存在！", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getBaseContext(), "异常", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    //限制用户输入规范嗝
    public boolean validate() {
        boolean valid = true;

        EditText inputAccount = findViewById(R.id.input_account);
        EditText inputAddress = findViewById(R.id.input_address);
        EditText inputNikeName = findViewById(R.id.input_nikeName);
        EditText inputPhone = findViewById(R.id.input_phone);
        EditText inputPsw = findViewById(R.id.input_psw);
        EditText inputRePsw = findViewById(R.id.input_rePsw);

        String name = inputAccount.getText().toString();
        String address = inputAddress.getText().toString();
        String email = inputNikeName.getText().toString();
        String mobile = inputPhone.getText().toString();
        String password = inputPsw.getText().toString();
        String reEnterPassword = inputRePsw.getText().toString();

        if (email.isEmpty() || email.length() < 3) {
            inputNikeName.setError("at least 3 characters");
            valid = false;
        } else {
            inputNikeName.setError(null);
        }

        if (address.isEmpty()) {
            inputAddress.setError("Enter Valid Address");
            valid = false;
        } else {
            inputAddress.setError(null);
        }

        if (name.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(name).matches()) {
            inputAccount.setError("enter a valid email address");
            valid = false;
        } else {
            inputAccount.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=11) {
            inputPhone.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPsw.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPsw.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            inputRePsw.setError("Password Do not match");
            valid = false;
        } else {
            inputRePsw.setError(null);
        }
        return valid;
    }

}
