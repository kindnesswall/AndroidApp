package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HamedGh on 3/8/2016.
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
