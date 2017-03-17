package ir.hamed_gh.divaremehrabani.model;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class GetGiftPathQuery {

    public String cityId;
    public String regionId;
    public String startIndex;
    public String lastIndex;
    public String categoryId;
    public String searchText;

    public GetGiftPathQuery(
            String cityId,
            String regionId,
            String categoryId,
            String startIndex,
            String lastIndex,
            String searchText) {

        this.cityId = cityId;
        this.regionId = regionId;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
        this.categoryId = categoryId;
        this.searchText = searchText;

    }
}
