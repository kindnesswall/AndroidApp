package ir.kindnesswall.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 6/27/17.
 */

public class ErrorOutput {

	@SerializedName("error")
	public String error;

	@SerializedName("error_description")
	public String description;

}
