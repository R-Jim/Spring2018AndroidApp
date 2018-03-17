package model;

import java.util.List;

import data.model.Item;

/**
 * Created by elpsychris on 03/06/2018.
 */

public class Order {
    private int id;
    private List<Item> dish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getDish() {
        return dish;
    }

    public void setDish(List<Item> dish) {
        this.dish = dish;
    }
}
