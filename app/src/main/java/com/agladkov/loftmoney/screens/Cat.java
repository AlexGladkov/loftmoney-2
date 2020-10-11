package com.agladkov.loftmoney.screens;

import com.agladkov.loftmoney.Pet;

public class Cat extends Pet {

    private String poroda;

    public Cat(String name, String type, String poroda) {
        super(name, type);

        this.poroda = poroda;
    }

    public String getPoroda() {
        return poroda;
    }
}
