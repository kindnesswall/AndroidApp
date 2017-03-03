package ir.hamed_gh.divaremehrabani.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 3/3/17.
 */

public class BookmarkInput {

	@SerializedName("giftId")
	public String giftId;

	public BookmarkInput(String giftId) {
		this.giftId = giftId;
	}
}
