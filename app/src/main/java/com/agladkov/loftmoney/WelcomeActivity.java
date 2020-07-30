package com.agladkov.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.agladkov.loftmoney.remote.AuthResponse;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends AppCompatActivity {

    AppCompatButton loginButtonView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
                compositeDisposable.add(((LoftApp) getApplication()).getAuthApi().performAuth(String.valueOf(new Random().nextInt()))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AuthResponse>() {
                        @Override
                        public void accept(AuthResponse authResponse) throws Exception {
                            if (authResponse != null) {
                                getSharedPreferences(getString(R.string.app_name), 0)
                                    .edit()
                                    .putString("token", authResponse.getAuthToken())
                                    .apply();

                                routeToMain();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                        }
                    }));
            }
        });
    }

    private void routeToMain() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }
}
