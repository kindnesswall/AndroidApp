package ir.hamed_gh.divaremehrabani.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 11/11/16.
 */

public class Category implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.categoryId);
        dest.writeString(this.createDateTime);
        dest.writeString(this.createDate);
        dest.writeString(this.createTime);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.categoryId = in.readString();
        this.createDateTime = in.readString();
        this.createDate = in.readString();
        this.createTime = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
