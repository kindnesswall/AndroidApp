package hamed_gh.ir.divaaremehrabani.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 10/12/16.
 */

public class Place {

    public String name;
    public String level;
    String ordering;
    String container_id;

    @SerializedName("long")
    String longitude;
    String latitude;

    String slug;
    ArrayList<String> neighbours;
    Boolean show_in_input;
    Boolean show_in_filter;

    @SerializedName("new")
    Boolean isNew;
    Integer radius;

    String id;

}
