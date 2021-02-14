package com.agladkov.loftmoney.screens.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agladkov.loftmoney.AddMoneyActivity;
import com.agladkov.loftmoney.LoftApp;
import com.agladkov.loftmoney.R;
import com.agladkov.loftmoney.cells.MoneyCellAdapter;
import com.agladkov.loftmoney.cells.MoneyCellAdapterClick;
import com.agladkov.loftmoney.cells.MoneyItem;
import com.agladkov.loftmoney.screens.dashboard.EditModeListener;
import com.agladkov.loftmoney.screens.main.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MoneyFragment extends Fragment implements MoneyEditListener {

    private MoneyCellAdapter moneyCellAdapter = new MoneyCellAdapter();
    private MoneyViewModel moneyViewModel;
    private FloatingActionButton addNewExpense;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moneyCellAdapter.setMoneyCellAdapterClick(new MoneyCellAdapterClick() {
            @Override
            public void onCellClick(MoneyItem moneyItem) {
                if (moneyViewModel.isEditMode.getValue()) {
                    moneyItem.setSelected(!moneyItem.isSelected());
                    moneyCellAdapter.updateItem(moneyItem);
                    checkSelectedCount();
                }
            }

            @Override
            public void onLongCellClick(MoneyItem moneyItem) {
                if (!moneyViewModel.isEditMode.getValue())
                    moneyItem.setSelected(true);
                    moneyCellAdapter.updateItem(moneyItem);
                    moneyViewModel.isEditMode.postValue(true);
                    checkSelectedCount();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addNewExpense = view.findViewById(R.id.addNewExpense);

        configureViews(view);
        configureViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();

        moneyViewModel.loadIncomes(
            ((LoftApp) getActivity().getApplication()).moneyApi,
            getActivity().getSharedPreferences(getString(R.string.app_name), 0)
        );
    }

    @Override
    public void onClearEdit() {
        moneyViewModel.isEditMode.postValue(false);
        moneyViewModel.selectedCounter.postValue(-1);

        for (MoneyItem moneyItem : moneyCellAdapter.getMoneyItemList()) {
            if (moneyItem.isSelected()) {
                moneyItem.setSelected(false);
                moneyCellAdapter.updateItem(moneyItem);
            }
        }
    }

    @Override
    public void onClearSelectedClick() {
        moneyViewModel.isEditMode.postValue(false);
        moneyViewModel.selectedCounter.postValue(-1);
        moneyCellAdapter.deleteSelectedItems();
    }

    private void configureViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.itemsView);
        recyclerView.setAdapter(moneyCellAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
            false));

        FloatingActionButton addNewIncomeView = view.findViewById(R.id.addNewExpense);
        addNewIncomeView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddMoneyActivity.class);
            startActivity(intent);
        });
    }

    private void checkSelectedCount() {
        int selectedItemsCount = 0;
        for (MoneyItem moneyItem : moneyCellAdapter.getMoneyItemList()) {
            if (moneyItem.isSelected()) {
                selectedItemsCount++;
            }
        }

        moneyViewModel.selectedCounter.postValue(selectedItemsCount);
    }

    private void configureViewModel() {
        moneyViewModel = new ViewModelProvider(this).get(MoneyViewModel.class);
        moneyViewModel.moneyItemsList.observe(this, moneyItems -> {
            moneyCellAdapter.setData(moneyItems);
        });

        moneyViewModel.isEditMode.observe(this, isEditMode -> {
            addNewExpense.setVisibility(isEditMode ? View.GONE : View.VISIBLE);

            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onEditModeChanged(isEditMode);
            }
        });

        moneyViewModel.selectedCounter.observe(this, newCount -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onCounterChanged(newCount);
            }
        });

        moneyViewModel.messageString.observe(this, message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });

        moneyViewModel.messageInt.observe(this, message -> {
            if (message > 0) {
                showToast(getString(message));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
