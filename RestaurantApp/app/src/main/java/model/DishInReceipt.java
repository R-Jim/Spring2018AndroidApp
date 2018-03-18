package model;

import data.model.Item;

/**
 * Created by Swomfire on 18-Mar-18.
 */

public class DishInReceipt {
    private Item dish;
    private int quantity;

    public DishInReceipt() {
    }

    public DishInReceipt(Item dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

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
}
