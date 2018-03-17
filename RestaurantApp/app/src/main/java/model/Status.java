package model;

/**
 * Created by Swomfire on 17-Mar-18.
 */

public enum  Status {
    OCCUPIED("OCC","Occupied"),AVAIABLE("AVA","Available");

    private int seqId;
    private String statusId;
    private String statusName;

    Status(String statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
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
}

