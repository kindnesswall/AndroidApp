package ir.hamed_gh.divaremehrabani.model;

/**
 * Created by Hamed on 1/17/17.
 */

public class GetGiftPathQuery {

    public String locationId;
    public String startIndex;
    public String lastIndex;
    public String categoryId;
    public String searchText;

    public GetGiftPathQuery(String locationId, String startIndex, String lastIndex, String categoryId, String searchText) {
        this.locationId = locationId;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
        this.categoryId = categoryId;
        this.searchText = searchText;
    }
}
