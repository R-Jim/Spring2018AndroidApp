package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Swomfire on 17-Mar-18.
 */

public class StatusByStatusSeqId {

    @SerializedName("seqId")
    @Expose
    private Integer seqId;
    @SerializedName("statusId")
    @Expose
    private String statusId;
    @SerializedName("statusName")
    @Expose
    private String statusName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("links")
    @Expose
    private List<Object> links = null;

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }
}
