package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 1/5/17.
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

    @SerializedName(".issued")
    public String issued;

    @SerializedName(".expires")
    public String expires;
}
