package ir.kindnesswall.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 7/7/17.
 */

public class TeamMember {

	@SerializedName("name")
	public String name;

	@SerializedName("about")
	public String about;

	@SerializedName("telegram")
	public String telegram;

	@SerializedName("linkedin")
	public String linkedin;

	@SerializedName("imageUrl")
	public String imageUrl;

}
