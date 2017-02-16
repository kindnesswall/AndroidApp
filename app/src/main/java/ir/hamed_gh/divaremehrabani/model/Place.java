package ir.hamed_gh.divaremehrabani.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hamed on 10/12/16.
 */

public class Place {

    @SerializedName("name")
    public String name;

    @SerializedName("level")
    public String level;

    @SerializedName("container_id")
    public Integer container_id;

    @SerializedName("id")
    public String id;

//    String ordering;
//    @SerializedName("long")
//    String longitude;
//    String latitude;
//
//    String slug;
//    ArrayList<String> neighbours;
//    Boolean show_in_input;
//    Boolean show_in_filter;
//
//    @SerializedName("new")
//    Boolean isNew;
//    Integer radius;

}
