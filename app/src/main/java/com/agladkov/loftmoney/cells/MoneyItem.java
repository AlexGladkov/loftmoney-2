package com.agladkov.loftmoney.cells;

import com.agladkov.loftmoney.remote.MoneyRemoteItem;

public class MoneyItem {

    private String title;
    private String value;
    private boolean isSelected;

    public MoneyItem(String title, String value) {
        this.title = title;
        this.value = value;
        this.isSelected = false;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static MoneyItem getInstance(MoneyRemoteItem moneyRemoteItem) {
        return new MoneyItem(moneyRemoteItem.getName(), moneyRemoteItem.getPrice() + "$");
    }
}
