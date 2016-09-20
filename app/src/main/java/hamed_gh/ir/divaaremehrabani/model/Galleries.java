package hamed_gh.ir.divaaremehrabani.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hamed on 5/6/16.
 */
public class Galleries {

	@SerializedName("gallery")
	@Expose
	private ArrayList<Gallery> gallery = new ArrayList<Gallery>();

	/**
	 * @return The gallery
	 */
	public ArrayList<Gallery> getGallery() {
		return gallery;
	}

	/**
	 * @param gallery The gallery
	 */
	public void setGallery(ArrayList<Gallery> gallery) {
		this.gallery = gallery;
	}

}
