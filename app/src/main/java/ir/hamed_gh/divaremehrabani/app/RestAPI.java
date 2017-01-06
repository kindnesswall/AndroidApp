package ir.hamed_gh.divaremehrabani.app;

import java.util.List;

import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.Location;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hamed on 5/6/16.
 */
public interface RestAPI {

    @GET("Gift/{locationId}/{startIndex}/{lastIndex}")
    Call<List<Gift>> getGifts(@Path("locationId") String locationId,
                              @Path("startIndex") String startIndex,
                              @Path("lastIndex") String lastIndex);

    @POST("Upload")
    Call<ResponseBody> uploadFile(
            @Header("Authorization") String authorization,
            @Header("fileName") String fileName,
            @Body RequestBody photo
    );

//    @POST("Upload")
//    Call<ResponseBody> uploadGiftImage(@Header("token") String token,
//                                    @Header("fileName") String fileName,
//                                    @Body RequestBody photo);

    @GET("Location")
    Call<List<Location>> getLocations();

    @GET("Location/{Id}")
    Call<Location> getLocation(@Path("Id") String locationId);



}
