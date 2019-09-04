package com.example.first;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.first.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername,etPassword,etPwdAgain;
    private Button btnRegister;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //1.获取界面控件
        initView();
        initToolBar();
        //2.button点击事件的监听
        btnRegister.setOnClickListener(new View.OnClickListener() {
            //3.处理点击事件
            @Override
            public void onClick(View view) {
                //3.1获取控件的值
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                String pswgagin=etPwdAgain.getText().toString();
                //3.2检查数据的有效性
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)||TextUtils.isEmpty(pswgagin)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(!password.equals(pswgagin)){
                    Toast.makeText(RegisterActivity.this,"两次密码必须一致",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this,"注册成功，已为您返回到登录界面",Toast.LENGTH_SHORT).show();
                    //3.3将注册信息存储
                    savePref(username, MD5Utils.md5(password));
                    //4.跳转到登录页面
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }

            }
        });
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
//            actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    private void savePref(String username,String password) {
        SharedPreferences.Editor editor = getSharedPreferences("userinfo",MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();

    }

    private void initView() {
        etUsername =findViewById(R.id.user);
        etPassword =findViewById(R.id.pass);
        etPwdAgain =findViewById(R.id.repass);
        btnRegister =findViewById(R.id.register);
    }
}
