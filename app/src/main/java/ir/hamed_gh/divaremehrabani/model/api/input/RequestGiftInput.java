package ir.hamed_gh.divaremehrabani.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestGiftInput {

	@SerializedName("giftId")
	public String giftId;

	public RequestGiftInput(String giftId) {
		this.giftId = giftId;
	}
}
