package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 11/11/16.
 */

public class Gift {

    @SerializedName("Title")
    public String title="";

    @SerializedName("Address")
    public String address="";

    @SerializedName("Description")
    public String description="";

    @SerializedName("Price")
    public String price="";

    @SerializedName("Status")
    String status;

    @SerializedName("UserId")
    public String userId="";

    @SerializedName("CategoryId")
    String categoryId;

    @SerializedName("Category")
    public Category category;

    @SerializedName("LocationId")
    public Location locationId;

    @SerializedName("Location")
    public Location location;

    @SerializedName("GiftImages")
    public ArrayList<String> giftImages;

    @SerializedName("Id")
    public String giftId="";

    @SerializedName("CreateDateTime")
    public String createDateTime="";

    @SerializedName("CreateDate")
    public String createDate="";

    @SerializedName("CreateTime")
    public String createTime="";
}