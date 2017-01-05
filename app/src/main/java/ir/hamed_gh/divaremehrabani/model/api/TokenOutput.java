package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 1/5/17.
 */

public class TokenOutput {

    @SerializedName("access_token")
    String access_token;

    @SerializedName("token_type")
    String token_type;

    @SerializedName("expires_in")
    String expires_in;

    @SerializedName("userName")
    String userName;

    @SerializedName(".issued")
    String issued;

    @SerializedName(".expires")
    String expires;
}
