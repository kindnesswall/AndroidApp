package ir.hamed_gh.divaremehrabani.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class Gift implements Parcelable {

	public static final Creator<Gift> CREATOR = new Creator<Gift>() {
		@Override
		public Gift createFromParcel(Parcel in) {
			return new Gift(in);
		}

		@Override
		public Gift[] newArray(int size) {
			return new Gift[size];
		}
	};
	@SerializedName("requestCount")
	public String requestCount = "";
	@SerializedName("title")
	public String title = "";
	@SerializedName("address")
	public String address = "";
	@SerializedName("bookmark")
	public boolean bookmark;
	@SerializedName("description")
	public String description = "";
	@SerializedName("price")
	public String price = "";
	@SerializedName("status")
	public String status;
	@SerializedName("userId")
	public String userId = "";
	@SerializedName("user")
	public String user = "";
	@SerializedName("receivedUserId")
	public String receivedUserId = "";
	@SerializedName("receivedUser")
	public String receivedUser = "";
	@SerializedName("categoryId")
	public String categoryId;
	@SerializedName("category")
	public String category;
	@SerializedName("cityId")
	public String locationId;
	@SerializedName("location")
	public String location;
	@SerializedName("regionId")
	public String regionId;
	@SerializedName("region")
	public String region;
	@SerializedName("giftImages")
	public ArrayList<String> giftImages = new ArrayList<>();
	@SerializedName("id")
	public String giftId = "";
	@SerializedName("createDateTime")
	public String createDateTime = "";
	@SerializedName("createDate")
	public String createDate = "";
	@SerializedName("createTime")
	public String createTime = "";

	public Gift() {

	}

	protected Gift(Parcel in) {
		title = in.readString();
		address = in.readString();
		bookmark = in.readByte() != 0;
		description = in.readString();
		price = in.readString();
		status = in.readString();
		userId = in.readString();
		user = in.readString();
		receivedUserId = in.readString();
		receivedUser = in.readString();
		categoryId = in.readString();
		category = in.readString();
		locationId = in.readString();
		location = in.readString();
		regionId = in.readString();
		region = in.readString();
		giftImages = in.createStringArrayList();
		giftId = in.readString();
		createDateTime = in.readString();
		createDate = in.readString();
		createTime = in.readString();
	}

	public Gift(String description,
	            String address,
	            String title,
	            String price,
	            String categoryId,
	            String locationId,
	            String regionId,
	            ArrayList<String> giftImages) {

		this.description = description;
		this.address = address;
		this.title = title;
		this.price = price;
		this.categoryId = categoryId;
		this.regionId = regionId;
		this.locationId = locationId;
		this.giftImages = giftImages;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(title);
		parcel.writeString(address);
		parcel.writeByte((byte) (bookmark ? 1 : 0));
		parcel.writeString(description);
		parcel.writeString(price);
		parcel.writeString(status);
		parcel.writeString(userId);
		parcel.writeString(user);
		parcel.writeString(receivedUserId);
		parcel.writeString(receivedUser);
		parcel.writeString(categoryId);
		parcel.writeString(category);
		parcel.writeString(locationId);
		parcel.writeString(location);
		parcel.writeString(regionId);
		parcel.writeString(region);
		parcel.writeStringList(giftImages);
		parcel.writeString(giftId);
		parcel.writeString(createDateTime);
		parcel.writeString(createDate);
		parcel.writeString(createTime);
	}
}