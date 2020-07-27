package com.agladkov.loftmoney.cells.money;

import com.agladkov.loftmoney.R;
import com.agladkov.loftmoney.remote.MoneyItem;

public class MoneyCellModel {
    private String name;
    private String value;
    private Integer color;

    public MoneyCellModel(String name, String value, Integer color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public static MoneyCellModel getInstance(MoneyItem moneyItem) {
        return new MoneyCellModel(moneyItem.getName(), moneyItem.getPrice() + " ₽",
                moneyItem.getType().equals("expense") ? R.color.expenseColor : R.color.incomeColor
        );
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Integer getColor() {
        return color;
    }
}
