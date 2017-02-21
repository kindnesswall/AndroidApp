package ir.hamed_gh.divaremehrabani.model.api.input;

import com.google.gson.annotations.SerializedName;

import ir.hamed_gh.divaremehrabani.model.api.StartLastIndex;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RecievedRequestListInput extends StartLastIndex {

    @SerializedName("giftId")
    public String giftId;

    public RecievedRequestListInput(String giftId, String startIndex, String lastIndex) {
        super(startIndex, lastIndex);
        this.giftId = giftId;
    }
}
