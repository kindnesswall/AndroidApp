package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 2/21/17.
 */

public class User {

	@SerializedName("firstName")
	public String firstName;

	@SerializedName("lastName")
	public String lastName;

	@SerializedName("email")
	public String email;

	@SerializedName("userName")
	public String userName;

	@SerializedName("mobileNumber")
	public String mobileNumber;

	@SerializedName("mobileNumberConfirmed")
	public String mobileNumberConfirmed;

	@SerializedName("imageUrl")
	public String imageUrl;

	@SerializedName("id")
	public String id;

	@SerializedName("createDateTime")
	public String createDateTime;

	@SerializedName("createDate")
	public String createDate;

	@SerializedName("createTime")
	public String createTime;

}
