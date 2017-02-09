package ir.hamed_gh.divaremehrabani.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 2/9/17.
 */

public class RequestGiftInput {

    @SerializedName("giftId")
    public String giftId;

    public RequestGiftInput(String giftId) {
        this.giftId = giftId;
    }
}
