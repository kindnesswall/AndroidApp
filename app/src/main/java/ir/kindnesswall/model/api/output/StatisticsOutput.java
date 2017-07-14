package ir.kindnesswall.model.api.output;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

/**
 * Created by Hamed on 6/23/17.
 */

public class StatisticsOutput {

	@SerializedName("statistics")
	public LinkedHashMap<String, String> statistics;

}
