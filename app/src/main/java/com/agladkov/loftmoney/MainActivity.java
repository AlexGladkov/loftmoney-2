package com.agladkov.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.agladkov.loftmoney.cells.money.MoneyAdapter;
import com.agladkov.loftmoney.cells.money.MoneyAdapterClick;
import com.agladkov.loftmoney.cells.money.MoneyCellModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoneyAdapter moneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.costsRecyclerView);
        moneyAdapter = new MoneyAdapter();
        moneyAdapter.setMoneyAdapterClick(new MoneyAdapterClick() {
            @Override
            public void onMoneyClick(MoneyCellModel moneyCellModel) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }

            @Override
            public void onValueClick(String value) {

            }
        });

        recyclerView.setAdapter(moneyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
            LinearLayoutManager.VERTICAL, false));

        moneyAdapter.setData(generateExpenses());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moneyAdapter.addData(generateIncomes());
                moneyAdapter.addData(generateIncomes());
                moneyAdapter.addData(generateIncomes());
                moneyAdapter.addData(generateIncomes());
            }
        }, 3000);
    }

    private List<MoneyCellModel> generateExpenses() {
        List<MoneyCellModel> moneyCellModels = new ArrayList<>();
        moneyCellModels.add(new MoneyCellModel("Молоко", "70 ₽", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Зубная щётка", "70 ₽", R.color.expenseColor));
        moneyCellModels.add(new MoneyCellModel("Сковородка с антипригарным покрытием",
            "1670 ₽", R.color.expenseColor));

        return moneyCellModels;
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