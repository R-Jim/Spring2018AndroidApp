package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Swomfire on 21-Mar-18.
 */

public class Receipt {

    @SerializedName("seqId")
    @Expose
    private Integer seqId;
    @SerializedName("total")
    @Expose
    private Object total;
    @SerializedName("issueDate")
    @Expose
    private Long issueDate;
    @SerializedName("paid")
    @Expose
    private Boolean paid;
    @SerializedName("dinerTableByTableSeqId")
    @Expose
    private Table dinerTableByTableSeqId;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Table getDinerTableByTableSeqId() {
        return dinerTableByTableSeqId;
    }

    public void Table(Table dinerTableByTableSeqId) {
        this.dinerTableByTableSeqId = dinerTableByTableSeqId;
    }

}
