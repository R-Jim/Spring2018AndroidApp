package model;

import java.util.List;

/**
 * Created by Swomfire on 18-Mar-18.
 */

public class Receipt {
    private int receiptId;
    private List<DishInReceipt> dishInReceipts;

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public List<DishInReceipt> getDishInReceipts() {
        return dishInReceipts;
    }

    public void setDishInReceipts(List<DishInReceipt> dishInReceipts) {
        this.dishInReceipts = dishInReceipts;
    }
}
