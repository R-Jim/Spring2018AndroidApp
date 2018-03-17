package model;

/**
 * Created by elpsychris on 03/06/2018.
 */

public class Table {
    private int tableNo;
    private boolean status;
    private Order order;

    public Table(int tableNo, boolean status, Order order) {
        this.tableNo = tableNo;
        this.status = status;
        this.order = order;
    }

    public Table() {

    }

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
