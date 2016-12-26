package ir.hamed_gh.divaremehrabani.model.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 11/11/16.
 */

public class Gifts {

    @SerializedName("results")
    ArrayList<Gift> gifts;

    public ArrayList<Gift> getGifts() {
        return gifts;
    }
}
