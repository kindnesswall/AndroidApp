package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Gift {
    @SerializedName("Id")
    public String giftId;

    @SerializedName("Title")
    public String title;

    @SerializedName("Description")
    String description;

    @SerializedName("Price")
    String price;

    @SerializedName("Address")
    String address;

    @SerializedName("UserId")
    String userId;

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
