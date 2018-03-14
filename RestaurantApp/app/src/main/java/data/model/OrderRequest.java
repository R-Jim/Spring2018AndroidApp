package data.model;

/**
 * Created by elpsychris on 03/13/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest {

    @SerializedName("tableId")
    @Expose
    private Integer tableId;
    @SerializedName("item")
    @Expose
    private Item item;
    @SerializedName("quan")
    @Expose
    private Integer quan;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }

}

