package ir.kindnesswall.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 6/24/2017 AD.
 */

public class LogoutInput {

    @SerializedName("registerationId")
    public String registerationId;

    public LogoutInput(String registerationId) {
        this.registerationId = registerationId;
    }
}
