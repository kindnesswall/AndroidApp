package ir.hamed_gh.divaremehrabani.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 10/12/16.
 */

public class Place implements Parcelable {

    @SerializedName("name")
    public String name;

    @SerializedName("level")
    public String level;

    @SerializedName("container_id")
    public Integer container_id;

    @SerializedName("id")
    public String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.level);
        dest.writeValue(this.container_id);
        dest.writeString(this.id);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.name = in.readString();
        this.level = in.readString();
        this.container_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = in.readString();
    }

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
}
