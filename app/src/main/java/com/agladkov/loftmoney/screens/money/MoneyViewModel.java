package com.agladkov.loftmoney.screens.money;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.agladkov.loftmoney.LoftApp;
import com.agladkov.loftmoney.cells.MoneyItem;
import com.agladkov.loftmoney.remote.MoneyApi;
import com.agladkov.loftmoney.remote.MoneyRemoteItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MoneyViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<MoneyItem>> moneyItemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isEditMode = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectedCounter = new MutableLiveData<>(-1);

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadIncomes(MoneyApi moneyApi, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.getMoneyItems("income", authToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(moneyRemoteItems -> {
                List<MoneyItem> moneyItems = new ArrayList<>();

                for (MoneyRemoteItem moneyRemoteItem : moneyRemoteItems) {
                    moneyItems.add(MoneyItem.getInstance(moneyRemoteItem));
                }

                moneyItemsList.postValue(moneyItems);
            }, throwable -> {
                messageString.postValue(throwable.getLocalizedMessage());
            }));
    }
}
