package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Location {
    @SerializedName("parentId")
    String ParentId;

    @SerializedName("title")
    String Title;

    @SerializedName("latitude")
    String Latitude;

    @SerializedName("longitude")
    String Longitude;

    @SerializedName("id")
    String Id;
}
