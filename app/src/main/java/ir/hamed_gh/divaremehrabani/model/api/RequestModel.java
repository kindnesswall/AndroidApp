package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 2/9/17.
 */

public class RequestModel {

    @SerializedName("giftId")
    public String giftId;

    @SerializedName("gift")
    public String gift;

    @SerializedName("fromUserId")
    public String fromUserId;

    @SerializedName("fromUser")
    public String fromUser;

    @SerializedName("toUserId")
    public String toUserId;

    @SerializedName("toUser")
    public String toUser;

    @SerializedName("toStatus")
    public String toStatus;

}
