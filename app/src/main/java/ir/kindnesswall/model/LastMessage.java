package ir.kindnesswall.model;

import com.google.gson.annotations.SerializedName;

public class LastMessage{

    @SerializedName("message_id")
    public String id;

    @SerializedName("sender_id")
    public String sender_id;

    @SerializedName("text")
    public String text = "";

    @SerializedName("time")
    public String time;

}
