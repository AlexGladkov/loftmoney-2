package com.agladkov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {

    String value;
    String name;

    EditText textValue;
    EditText textName;

    Button buttonSend;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textValue = findViewById(R.id.textValue);
        textName = findViewById(R.id.textName);
        buttonSend = findViewById(R.id.buttonAdd);

        textValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                value = s.toString();
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.TOKEN_KEY, "");

                Disposable disposable = ((LoftApp) getApplication()).getMoneyApi().addItem(token, value, name, "expense")
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                finish();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("TAG", "Error " + throwable.getLocalizedMessage());
                            }
                        });

                compositeDisposable.add(disposable);
            }
        });
    }

    private void checkInputs() {
        buttonSend.setEnabled(value != null && !value.isEmpty() && name != null && !name.isEmpty());
    }
}