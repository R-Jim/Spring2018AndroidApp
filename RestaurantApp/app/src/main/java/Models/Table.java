package Models;

import java.util.List;

/**
 * Created by elpsychris on 03/06/2018.
 */

public class Table {
    private int tableNo;
    private boolean status;
    private Order order;


    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}