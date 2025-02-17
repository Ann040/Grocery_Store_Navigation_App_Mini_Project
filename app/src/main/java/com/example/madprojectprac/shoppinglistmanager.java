package com.example.madprojectprac;

import java.util.ArrayList;

public class shoppinglistmanager {
    private static shoppinglistmanager instance;
    private ArrayList<String> shoppingList;

    private shoppinglistmanager() {
        shoppingList = new ArrayList<>();
    }

    public static shoppinglistmanager getInstance() {
        if (instance == null) {
            instance = new shoppinglistmanager();
        }
        return instance;
    }

    public void addItems(ArrayList<String> items) {
        shoppingList.addAll(items);
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public void clearList() {
        shoppingList.clear();
    }
}
