package com.agladkov.loftmoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentA extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.titleView).setOnClickListener(v -> {
            if (getActivity() instanceof BaseRouter) {
                Fragment fragmentB = new FragmentB();
                Bundle params = new Bundle();
                MoneyClass moneyClass = new MoneyClass("Income", "3000");
                params.putParcelable("HELLO_KEY", moneyClass);
                fragmentB.setArguments(params);
                ((BaseRouter) getActivity()).routeTo(fragmentB);
            }
        });
    }
}
