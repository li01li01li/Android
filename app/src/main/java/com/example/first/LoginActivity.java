package com.example.first;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.first.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etUsername, etPassword;
    private Button buttonRegister, buttonLogin, buttonForget;
    private TextView tUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initToolBar();
        initView();
        getUserName();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //3.2 检查控件的有效性
                SharedPreferences pref = getSharedPreferences("userinfo", MODE_PRIVATE);
                String name = pref.getString("username", "");
                String pwd = pref.getString("password", "");
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!username.equals(name)) {
                    Toast.makeText(LoginActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                } else if (!MD5Utils.md5(password).equals(pwd)) {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 给bnt1添加点击响应事件
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    //启动
                    startActivity(intent);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //启动
                startActivity(intent);
            }
        });
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
           // actionBar.setDisplayHomeAsUpEnabled(true);//设置返回键
           actionBar.setHomeButtonEnabled(true);//设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }


    private void initView() {
        etUsername = findViewById(R.id.user);
        etPassword = findViewById(R.id.pass);
        buttonRegister = findViewById(R.id.register);
        buttonLogin = findViewById(R.id.login);
        buttonForget = findViewById(R.id.login_error);
        tUsername = findViewById(R.id.user);
    }

    private void getUserName() {
        Intent intent = getIntent();
        String username_register = intent.getStringExtra("username");
        if (username_register == null || username_register == "") {
            tUsername.setText("");
        } else {
            tUsername.setText(username_register);
        }
    }
}
