package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import model.Status;

/**
 * Created by elpsychris on 03/12/2018.
 */

public class Table {
    @SerializedName("seqId")
    @Expose
    private Integer seqId;
    @SerializedName("tableId")
    @Expose
    private String tableId;
    @SerializedName("statusByStatusSeqId")
    @Expose
    private StatusByStatusSeqId statusByStatusSeqId;
    @SerializedName("links")
    @Expose
    private List<Object> links = null;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public StatusByStatusSeqId getStatusByStatusSeqId() {
        return statusByStatusSeqId;
    }

    public void setStatusByStatusSeqId(StatusByStatusSeqId statusByStatusSeqId) {
        this.statusByStatusSeqId = statusByStatusSeqId;
    }

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }
   /* @SerializedName("seqId")
    @Expose
    private Integer seqId;

    @SerializedName("tableId")
    @Expose
    private String tableId;

    @SerializedName("statusByStatusSeqId")
    @Expose
    private Integer statusByStatusSeqId;

    private Status status;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Integer getStatusByStatusSeqId() {
        return statusByStatusSeqId;
    }

    public void setStatusByStatusSeqId(Integer statusByStatusSeqId) {
        this.statusByStatusSeqId = statusByStatusSeqId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Integer statusByStatusSeqId) {
        if (statusByStatusSeqId.equals(Status.AVAIABLE.getStatusId())) {
            this.status = Status.AVAIABLE;
        } else {
            this.status = Status.OCCUPIED;
        }
    }

    @Override
    public String toString() {
        return "Table{" +
                "seqId=" + seqId +
                ", tableId='" + tableId + '\'' +
                ", statusByStatusSeqId='" + statusByStatusSeqId +
                '}';
    }*/
}
