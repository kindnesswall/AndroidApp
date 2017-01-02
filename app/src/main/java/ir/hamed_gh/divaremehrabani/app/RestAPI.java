package ir.hamed_gh.divaremehrabani.app;

import java.util.List;

import ir.hamed_gh.divaremehrabani.model.api.Gift;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Hamed on 5/6/16.
 */
public interface RestAPI {

    @GET("Gift/{locationId}/{startIndex}/{lastIndex}")
    Call<List<Gift>> getGifts(@Path("locationId") String locationId,
                              @Path("startIndex") String startIndex,
                              @Path("lastIndex") String lastIndex);

    @POST
    Call<ResponseBody> uploadFile(@Body RequestBody photo, @Url String url);
}
