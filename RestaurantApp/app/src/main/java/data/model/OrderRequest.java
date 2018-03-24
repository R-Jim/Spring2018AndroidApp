package data.model;

/**
 * Created by elpsychris on 03/13/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

public class OrderRequest {
    @SerializedName("tableId")
    @Expose
    private Long tableId;
    @SerializedName("subscribers")
    @Expose
    private Set<String> subscribers = null;
    @SerializedName("orderDetailList")
    @Expose
    private List<OrderDetail> orderDetailList = null;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Set<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<String> subscribers) {
        this.subscribers = subscribers;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}

