package hamed_gh.ir.divaaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 11/11/16.
 */

public class Items {

    @SerializedName("results")
    ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }
}
