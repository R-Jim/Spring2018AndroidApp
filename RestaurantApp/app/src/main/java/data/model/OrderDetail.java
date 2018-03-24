package data.model;

/**
 * Created by Swomfire on 21-Mar-18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("itemSeq")
    @Expose
    private Long itemSeq;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public Long getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(Long itemSeq) {
        this.itemSeq = itemSeq;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}