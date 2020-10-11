package com.agladkov.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agladkov.loftmoney.cells.MoneyCellAdapter;
import com.agladkov.loftmoney.cells.MoneyItem;
import com.agladkov.loftmoney.remote.MoneyRemoteItem;
import com.agladkov.loftmoney.remote.MoneyResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private MoneyCellAdapter moneyCellAdapter = new MoneyCellAdapter();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.itemsView);
        recyclerView.setAdapter(moneyCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                false));

        FloatingActionButton addNewIncomeView = findViewById(R.id.addNewExpense);
        addNewIncomeView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddMoneyActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateData();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    private void generateData() {
//        moneyItems.add(new MoneyItem("PS4", "1500$"));
//        moneyItems.add(new MoneyItem("Salary", "50000$"));
//        moneyItems.add(new MoneyItem("Taxes", "15000$"));
//        moneyItems.add(new MoneyItem("Medical Care", "8000$"));
//
//        moneyCellAdapter.setData(moneyItems);

        Disposable disposable = ((LoftApp) getApplication()).moneyApi.getMoneyItems("income")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    if (moneyResponse.getStatus().equals("success")) {
                        List<MoneyItem> moneyItems = new ArrayList<>();

                        for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
                            moneyItems.add(MoneyItem.getInstance(moneyRemoteItem));
                        }

                        moneyCellAdapter.setData(moneyItems);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.connection_lost), Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });

        compositeDisposable.add(disposable);
    }
}
