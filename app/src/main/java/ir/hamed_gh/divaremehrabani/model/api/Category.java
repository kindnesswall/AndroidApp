package ir.hamed_gh.divaremehrabani.model.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class Category implements Parcelable {
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
	@SerializedName("title")
	public String title;
	@SerializedName("imageUrl")
	public String imageUrl;
	@SerializedName("id")
	public String categoryId;
	@SerializedName("createDateTime")
	public String createDateTime;
	@SerializedName("createDate")
	public String createDate;
	@SerializedName("createTime")
	public String createTime;

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
}
