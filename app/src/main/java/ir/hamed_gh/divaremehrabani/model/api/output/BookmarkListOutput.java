package ir.hamed_gh.divaremehrabani.model.api.output;

import com.google.gson.annotations.SerializedName;

import ir.hamed_gh.divaremehrabani.model.api.Gift;

/**
 * Created by Hamed on 3/3/17.
 */

public class BookmarkListOutput {

	@SerializedName("gift")
	public Gift gift;
}
