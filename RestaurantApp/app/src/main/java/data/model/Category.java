package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Swomfire on 17-Mar-18.
 */

public class Category {

    @SerializedName("seqId")
    @Expose
    private Integer seqId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
