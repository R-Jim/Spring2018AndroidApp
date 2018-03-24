package data.model;

/**
 * Created by elpsychris on 03/13/2018.
 */

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderRequest {
    @SerializedName("tableId")
    @Expose
    private Long tableId;
    @SerializedName("subscribers")
    @Expose
    private List<String> subscribers = new ArrayList<>();
    @SerializedName("orderDetailList")
    @Expose
    private List<OrderDetail> orderDetailList = null;

    public OrderRequest() {
        if (subscribers == null || subscribers.size() == 0) {
            String thisInstToken = FirebaseInstanceId.getInstance().getToken();
            if (subscribers.indexOf(thisInstToken) < 0) {
                subscribers.add(thisInstToken);
            }
        }
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}

