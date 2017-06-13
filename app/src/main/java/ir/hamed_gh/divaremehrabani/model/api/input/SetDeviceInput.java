package ir.hamed_gh.divaremehrabani.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Taher on 2/2/2017.
 */

public class SetDeviceInput {
    @SerializedName("registeration_id")
    public String regId;

    @SerializedName("device_id")
    public String deviceId;

    public SetDeviceInput(String regId, String deviceId) {
        this.regId = regId;
        this.deviceId = deviceId;
    }
}
