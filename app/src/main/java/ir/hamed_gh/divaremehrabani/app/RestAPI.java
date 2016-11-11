package ir.hamed_gh.divaremehrabani.app;

import ir.hamed_gh.divaremehrabani.model.api.Items;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Hamed on 5/6/16.
 */
public interface RestAPI {

    @GET("classes/Item")
    Call<Items> getItems(@Query("skip") String skip,
                         @Query("limit") String limit);

    @POST
    Call<ResponseBody> uploadFile(@Body RequestBody photo, @Url String url);
}
