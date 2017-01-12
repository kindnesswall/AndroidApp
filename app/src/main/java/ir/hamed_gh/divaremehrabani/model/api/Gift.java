package ir.hamed_gh.divaremehrabani.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 11/11/16.
 */

public class Gift implements Parcelable {

    @SerializedName("Title")
    public String title="";

    @SerializedName("Address")
    public String address="";

    @SerializedName("Description")
    public String description="";

    @SerializedName("Price")
    public String price="";

    @SerializedName("Status")
    public String status;

    @SerializedName("UserId")
    public String userId="";

    @SerializedName("CategoryId")
    public String categoryId;

    @SerializedName("Category")
    public String category;

    @SerializedName("LocationId")
    public String locationId;

    @SerializedName("Location")
    public String location;

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

    // No-arg Ctor
    public Gift(){}

    // Parcelling part
    public Gift(Parcel in){
        this.title = in.readString();
        this.address = in.readString();
        this.description = in.readString();
        this.price = in.readString();
        this.status = in.readString();
        this.userId = in.readString();
        this.categoryId = in.readString();
        this.category = in.readString();
        this.locationId = in.readString();
        this.location = in.readString();
        this.giftImages = (ArrayList<String>) in.readSerializable();
        this.giftId = in.readString();
        this.createDateTime = in.readString();
        this.createDate = in.readString();
        this.createTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(status);
        dest.writeString(userId);
        dest.writeString(categoryId);
        dest.writeString(category);
        dest.writeString(locationId);
        dest.writeString(location);
        dest.writeSerializable(giftImages);
        dest.writeString(giftId);
        dest.writeString(createDateTime);
        dest.writeString(createDate);
        dest.writeString(createTime);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Gift> CREATOR = new Parcelable.Creator<Gift>() {
        public Gift createFromParcel(Parcel pc) {
            return new Gift(pc);
        }
        public Gift[] newArray(int size) {
            return new Gift[size];
        }
    };
}