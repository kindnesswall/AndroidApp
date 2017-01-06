package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Location {
    @SerializedName("ParentId")
    String ParentId;

    @SerializedName("Title")
    String Title;

    @SerializedName("Latitude")
    String Latitude;

    @SerializedName("Longitude")
    String Longitude;

    @SerializedName("Id")
    String Id;
}
