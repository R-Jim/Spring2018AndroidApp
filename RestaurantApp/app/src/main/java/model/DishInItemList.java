package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import data.model.Category;
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

    public static HashMap<String, List<DishInItemList>> convertHashmapToStringKey(HashMap<Category, List<DishInItemList>> listHashMap) {
        HashMap<String, List<DishInItemList>> stringListHashMap = new HashMap<>();
        for (Map.Entry<Category, List<DishInItemList>> entry : listHashMap.entrySet()) {
            stringListHashMap.put(entry.getKey().getCategoryId(), entry.getValue());
        }
        return stringListHashMap;
    }

    public static HashMap<Category, List<DishInItemList>> convertHashmapToCategoryKey(HashMap<String, List<DishInItemList>> listHashMap, List<Category> categories) {
        HashMap<Category, List<DishInItemList>> categoryListHashMap = new HashMap<>();
        for (Map.Entry<String, List<DishInItemList>> entry : listHashMap.entrySet()) {
            for (Category category : categories) {
                if (category.getCategoryId().equals(entry.getKey())) {
                    categoryListHashMap.put(category, entry.getValue());

                }
            }
        }
        return categoryListHashMap;
    }
}
