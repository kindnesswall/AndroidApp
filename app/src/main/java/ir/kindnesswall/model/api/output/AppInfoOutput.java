package ir.kindnesswall.model.api.output;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lms-3 on 28/06/2016.
 */
public class AppInfoOutput implements Parcelable {

	@SerializedName("updateInfo")
	public UpdateInfo updateInfo;

	@SerializedName("smsCenter")
	public String smsCenter;

	protected AppInfoOutput(Parcel in) {
		smsCenter = in.readString();
	}

	public static final Creator<AppInfoOutput> CREATOR = new Creator<AppInfoOutput>() {
		@Override
		public AppInfoOutput createFromParcel(Parcel in) {
			return new AppInfoOutput(in);
		}

		@Override
		public AppInfoOutput[] newArray(int size) {
			return new AppInfoOutput[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(smsCenter);
	}

	public class UpdateInfo{
		@SerializedName("version")
		public String version;
		@SerializedName("apk_url")
		public String apk_url;
		@SerializedName("force_update")
		public String force_update;
		@SerializedName("changes")
		public ArrayList<String> changes;
	}
}
