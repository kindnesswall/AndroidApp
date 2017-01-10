package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Category {
    @SerializedName("Title")
    public String title;

    @SerializedName("ImageUrl")
    public String imageUrl;

    @SerializedName("Id")
    public String categoryId;

    @SerializedName("CreateDateTime")
    public String createDateTime;

    @SerializedName("CreateDate")
    public String createDate;

    @SerializedName("CreateTime")
    public String createTime;
}
