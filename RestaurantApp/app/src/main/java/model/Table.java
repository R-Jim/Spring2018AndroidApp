package model;

/**
 * Created by elpsychris on 03/06/2018.
 */

public class Table {
    private int seqId;
    private int tableId;
    private Status status;
    private Order order;

    public Table(int seqId, int tableId, Status status, Order order) {
        this.seqId = seqId;
        this.tableId = tableId;
        this.status = status;
        this.order = order;
    }

    public Table() {

    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
