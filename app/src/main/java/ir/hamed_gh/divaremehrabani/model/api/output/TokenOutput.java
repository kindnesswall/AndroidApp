package ir.hamed_gh.divaremehrabani.model.api.output;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HamedGh on 3/8/2016.
 */

public class TokenOutput {

	@SerializedName("access_token")
	public String access_token;

	@SerializedName("token_type")
	public String token_type;

	@SerializedName("expires_in")
	public String expires_in;

	@SerializedName("userName")
	public String userName;

	@SerializedName("userId")
	public String userId;

	@SerializedName(".issued")
	public String issued;

	@SerializedName(".expires")
	public String expires;
}
