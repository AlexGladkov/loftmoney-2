package com.agladkov.loftmoney.screens;

public class MoneyValue {

    private String name;
    private double moneyValue;
    private String identifier;

    public MoneyValue(String name, double moneyValue, String identifier) {
        this.name = name;
        this.moneyValue = moneyValue;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public double getMoneyValue() {
        return moneyValue;
    }

    public String getIdentifier() {
        return identifier;
    }
}
