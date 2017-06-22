package ir.kindnesswall.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 6/10/17.
 */

public class ReportInput {

	@SerializedName("giftId")
	public String giftId;

	@SerializedName("message")
	public String message;

}
