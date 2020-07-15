package com.agladkov.loftmoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class DiagramFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final BalanceView balanceView = view.findViewById(R.id.balanceView);
        Button randomView = view.findViewById(R.id.randomButtonView);
        randomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balanceView.update(new Random().nextFloat(), new Random().nextFloat());
            }
        });
    }

    public static DiagramFragment getInstance() {
        return new DiagramFragment();
    }
}
