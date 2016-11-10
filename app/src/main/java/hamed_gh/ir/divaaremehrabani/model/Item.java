package hamed_gh.ir.divaaremehrabani.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Item {
    @SerializedName("objectId")
    String objectId;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("address")
    String address;

    @SerializedName("tel")
    String tel;

    @SerializedName("createdAt")
    String createdAt;

    @SerializedName("updatedAt")
    String updatedAt;

    @SerializedName("category")
    Category category;

    @SerializedName("location")
    Location location;

    @SerializedName("position")
    Position position;
}
