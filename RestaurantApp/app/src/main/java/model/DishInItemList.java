package model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Swomfire on 14-Mar-18.
 */

public class DishInItemList {
    private Dish dish;
    private int quantity;
    private boolean selected;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
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

    public DishInItemList(Dish dish, int quantity, boolean selected) {
        this.dish = dish;
        this.quantity = quantity;
        this.selected = selected;
    }
}
