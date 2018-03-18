package model;

import java.util.concurrent.ThreadLocalRandom;

import data.model.Item;

/**
 * Created by Swomfire on 14-Mar-18.
 */

public class DishInItemList {
    private Item dish;
    private int quantity;
    private boolean selected;

    public Item getDish() {
        return dish;
    }

    public void setDish(Item dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public DishInItemList(Item dish, int quantity, boolean selected) {
        this.dish = dish;
        this.quantity = quantity;
        this.selected = selected;
    }
}
