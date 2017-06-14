package ir.hamed_gh.divaremehrabani.model.api.input;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Taher on 2/2/2017.
 */

public class SetDeviceInput {
    @SerializedName("registerationId")
    public String regId;

    @SerializedName("deviceId")
    public String deviceId;

    public SetDeviceInput(String regId, String deviceId) {
        this.regId = regId;
        this.deviceId = deviceId;
    }
}
