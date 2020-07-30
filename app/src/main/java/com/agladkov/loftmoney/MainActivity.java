package com.agladkov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.agladkov.loftmoney.cells.money.MoneyAdapter;
import com.agladkov.loftmoney.cells.money.MoneyAdapterClick;
import com.agladkov.loftmoney.cells.money.MoneyCellModel;
import com.agladkov.loftmoney.remote.MoneyItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoneyAdapter moneyAdapter;
    FloatingActionButton floatingActionButton;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.addNewExpense);
        recyclerView = findViewById(R.id.costsRecyclerView);
        moneyAdapter = new MoneyAdapter();
        moneyAdapter.setMoneyAdapterClick(new MoneyAdapterClick() {
            @Override
            public void onMoneyClick(MoneyCellModel moneyCellModel) {

            }

            @Override
            public void onValueClick(String value) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
            LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateExpenses();
    }

    private void generateExpenses() {
        final List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        String token = getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.TOKEN_KEY, "");

        Disposable disposable = ((LoftApp) getApplication()).getMoneyApi().getItems(token, "expense")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MoneyItem>>() {
                    @Override
                    public void accept(List<MoneyItem> moneyItems) throws Exception {
                        for (MoneyItem moneyItem : moneyItems) {
                            moneyCellModels.add(MoneyCellModel.getInstance(moneyItem));
                        }

                        moneyAdapter.setData(moneyCellModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG", "Error " + throwable.getLocalizedMessage());
                    }
                });

        compositeDisposable.add(disposable);
    }

    private List<MoneyCellModel> generateIncomes() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        moneyCellModels.add(new MoneyCellModel("Зарплата.Июнь", "70000 ₽", R.color.incomeColor));
        moneyCellModels.add(new MoneyCellModel("Премия", "7000 ₽", R.color.incomeColor));
        moneyCellModels.add(new MoneyCellModel("Олег наконец-то вернул долг",
            "300000 ₽", R.color.incomeColor));

        return moneyCellModels;
    }
}