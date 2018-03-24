package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Swomfire on 22-Mar-18.
 */

public class ReceiptDetail {
    @SerializedName("seqId")
    @Expose
    private Integer seqId;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("receiptByReceiptSeqId")
    @Expose
    private model.Receipt receiptByReceiptSeqId;
    @SerializedName("itemByItemSeqId")
    @Expose
    private Item itemByItemSeqId;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public model.Receipt getReceiptByReceiptSeqId() {
        return receiptByReceiptSeqId;
    }

    public void setReceiptByReceiptSeqId(model.Receipt receiptByReceiptSeqId) {
        this.receiptByReceiptSeqId = receiptByReceiptSeqId;
    }

    public Item getItemByItemSeqId() {
        return itemByItemSeqId;
    }

    public void setItemByItemSeqId(Item itemByItemSeqId) {
        this.itemByItemSeqId = itemByItemSeqId;
    }

}
