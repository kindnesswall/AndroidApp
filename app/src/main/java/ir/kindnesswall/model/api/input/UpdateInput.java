package ir.kindnesswall.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lms-3 on 28/06/2016.
 */
public class UpdateInput {
    @SerializedName("current_version")
    public String current_version;

    public UpdateInput(String current_version) {
        this.current_version = current_version;
    }
}
