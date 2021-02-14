package com.agladkov.loftmoney.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agladkov.loftmoney.AddMoneyActivity;
import com.agladkov.loftmoney.LoftApp;
import com.agladkov.loftmoney.R;
import com.agladkov.loftmoney.cells.MoneyCellAdapter;
import com.agladkov.loftmoney.cells.MoneyItem;
import com.agladkov.loftmoney.remote.MoneyRemoteItem;
import com.agladkov.loftmoney.remote.MoneyResponse;
import com.agladkov.loftmoney.screens.dashboard.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.containerView, new DashboardFragment())
            .commitNow();
    }
}
