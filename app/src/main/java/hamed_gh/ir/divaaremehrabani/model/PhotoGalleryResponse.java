package hamed_gh.ir.divaaremehrabani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 5/6/16.
 */
public class PhotoGalleryResponse {

    @SerializedName("data")
    @Expose
    private Galleries data;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    /**
     * @return The data
     */
    public Galleries getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Galleries data) {
        this.data = data;
    }

    /**
     * @return The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * @param meta The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
