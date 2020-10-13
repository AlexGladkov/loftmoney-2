package com.agladkov.loftmoney;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agladkov.loftmoney.R;
import com.agladkov.loftmoney.cells.MoneyCellAdapter;
import com.agladkov.loftmoney.cells.MoneyItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MoneyCellAdapter moneyCellAdapter = new MoneyCellAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.itemsView);
        recyclerView.setAdapter(moneyCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                false));

        generateData();
    }

    private void generateData() {
        List<MoneyItem> moneyItems = new ArrayList<>();

        moneyItems.add(new MoneyItem("Salary", "50000$"));
        moneyItems.add(new MoneyItem("Taxes", "25000$"));
        moneyItems.add(new MoneyItem("PS4", "1500$"));
        moneyItems.add(new MoneyItem("Food", "3500$"));

        moneyCellAdapter.setData(moneyItems);
    }
}
