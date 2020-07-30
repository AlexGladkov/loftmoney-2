package com.agladkov.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class WelcomeActivity extends AppCompatActivity {

    AppCompatButton loginButtonView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        loginButtonView = findViewById(R.id.loginButtonView);

        configureLogin();
        checkToken();
    }

    private void checkToken() {
        String token = getSharedPreferences(getString(R.string.app_name), 0).getString("token", "");

        if (!TextUtils.isEmpty(token)) {
            routeToMain();
        }
    }

    private void configureLogin() {
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPreferences(getString(R.string.app_name), 0)
                    .edit()
                    .putString("token", "asdfasdf")
                    .apply();
                routeToMain();
            }
        });
    }

    private void routeToMain() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }
}
