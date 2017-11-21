package ir.kindnesswall.model;

import java.util.ArrayList;

import ir.kindnesswall.adapter.ShowcaseMoreInfoAdapter;
import ir.kindnesswall.model.api.Gift;

/**
 * Created by Hamed on 11/21/17.
 */

public class GiftListModel {

	public ArrayList<Gift> gifts = new ArrayList<>();

	public int start_index = 1;
	public ShowcaseMoreInfoAdapter showcaseMoreInfoAdapter;// = new ShowcaseMoreInfoAdapter(films);

}
