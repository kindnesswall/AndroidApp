package ir.kindnesswall.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HamedGh on 3/8/2016.
 */

public class Place implements Parcelable {

	public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
		@Override
		public Place createFromParcel(Parcel source) {
			return new Place(source);
		}

		@Override
		public Place[] newArray(int size) {
			return new Place[size];
		}
	};
	@SerializedName("name")
	public String name;
	@SerializedName("level")
	public String level;
	@SerializedName("container_id")
	public String container_id;

//    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
//        @Override
//        public Place createFromParcel(Parcel source) {
//            return new Place(source);
//        }
//
//        @Override
//        public Place[] newArray(int size) {
//            return new Place[size];
//        }
//    };
	@SerializedName("id")
	public String id;

	public Place() {
	}

	protected Place(Parcel in) {
		this.name = in.readString();
		this.level = in.readString();
		this.container_id = in.readString();
		this.id = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.level);
		dest.writeString(this.container_id);
		dest.writeString(this.id);
	}
}
