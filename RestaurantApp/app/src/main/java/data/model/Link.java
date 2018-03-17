package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Swomfire on 17-Mar-18.
 */

public class Link {
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("hreflang")
    @Expose
    private Object hreflang;
    @SerializedName("media")
    @Expose
    private Object media;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("deprecation")
    @Expose
    private Object deprecation;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Object getHreflang() {
        return hreflang;
    }

    public void setHreflang(Object hreflang) {
        this.hreflang = hreflang;
    }

    public Object getMedia() {
        return media;
    }

    public void setMedia(Object media) {
        this.media = media;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getDeprecation() {
        return deprecation;
    }

    public void setDeprecation(Object deprecation) {
        this.deprecation = deprecation;
    }


}
