package ir.kindnesswall.model.api.output;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lms-3 on 28/06/2016.
 */
public class UpdateOutput implements Parcelable {

	public static final Creator<UpdateOutput> CREATOR = new Creator<UpdateOutput>() {
		@Override
		public UpdateOutput createFromParcel(Parcel source) {
			return new UpdateOutput(source);
		}

		@Override
		public UpdateOutput[] newArray(int size) {
			return new UpdateOutput[size];
		}
	};

	@SerializedName("version")
	public String version;
	@SerializedName("apk_url")
	public String apk_url;
	@SerializedName("force_update")
	public String force_update;
	@SerializedName("changes")
	public ArrayList<String> changes;

	public UpdateOutput(String version, String apk_url, String force_update, ArrayList<String> changes) {
		this.version = version;
		this.apk_url = apk_url;
		this.force_update = force_update;
		this.changes = changes;
	}

	protected UpdateOutput(Parcel in) {
		this.version = in.readString();
		this.apk_url = in.readString();
		this.force_update = in.readString();
		this.changes = in.createStringArrayList();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.version);
		dest.writeString(this.apk_url);
		dest.writeString(this.force_update);
		dest.writeStringList(this.changes);
	}
}
