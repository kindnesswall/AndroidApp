package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Position {
    @SerializedName("__type")
    String __type;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;
}
