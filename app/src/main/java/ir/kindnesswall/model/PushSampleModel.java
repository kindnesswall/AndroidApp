package ir.kindnesswall.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 6/21/2017 AD.
 */

public class PushSampleModel {

    @SerializedName("data")
    public Data data;

    @SerializedName("userId")
    public String userId;

    @SerializedName("method")
    public String method;

    @SerializedName("message")
    public String message;

    @SerializedName("imageUrl")
    public String imageUrl;

    @SerializedName("title")
    public String title;

    public class Data{

        @SerializedName("message")
        public String message;

        @SerializedName("imageUrl")
        public String imageUrl;

        @SerializedName("title")
        public String title;
    }

}
