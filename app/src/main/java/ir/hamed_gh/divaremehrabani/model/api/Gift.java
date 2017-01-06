package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 11/11/16.
 */

public class Gift {
    @SerializedName("Id")
    public String giftId;

    @SerializedName("Title")
    public String title;

    @SerializedName("Description")
    public String description;

    @SerializedName("Price")
    public String price;

    @SerializedName("Address")
    public String address;

    @SerializedName("UserId")
    public String userId;

    @SerializedName("tel")
    public String tel;

    @SerializedName("GiftImages")
    public ArrayList<String> giftImages;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("updatedAt")
    public String updatedAt;

    @SerializedName("category")
    public Category category;

    @SerializedName("location")
    public Location location;

    @SerializedName("position")
    public Position position;
}
