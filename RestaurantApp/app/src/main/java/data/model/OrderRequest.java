package data.model;

/**
 * Created by elpsychris on 03/13/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @SerializedName("seq")
    @Expose
    private Integer seq;
    @SerializedName("receiptSeq")
    @Expose
    private String receiptSeq;
    @SerializedName("tableNo")
    @Expose
    private String tableNo;
    @SerializedName("itemSeq")
    @Expose
    private String itemSeq;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("quan")
    @Expose
    private Integer quan;
    @SerializedName("subscribers")
    @Expose
    private Object subscribers;
    @SerializedName("done")
    @Expose
    private Boolean done;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getReceiptSeq() {
        return receiptSeq;
    }

    public void setReceiptSeq(String receiptSeq) {
        this.receiptSeq = receiptSeq;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(String itemSeq) {
        this.itemSeq = itemSeq;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }

    public Object getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Object subscribers) {
        this.subscribers = subscribers;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}

