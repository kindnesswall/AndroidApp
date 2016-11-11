package ir.hamed_gh.divaremehrabani.app;

import ir.hamed_gh.divaremehrabani.model.api.Items;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hamed on 5/6/16.
 */
public interface RestAPI {

    @GET("classes/Item")
    Call<Items> getItems(@Query("skip") String skip,
                         @Query("limit") String limit);

}
