package ir.hamed_gh.divaremehrabani.app;

import java.util.ArrayList;
import java.util.List;

import ir.hamed_gh.divaremehrabani.model.api.Category;
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

	@GET("Gift/{" + Constants.LOCATION_ID + "}/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<List<Gift>> getGifts(@Path(Constants.LOCATION_ID) String locationId,
	                          @Path(Constants.StartIndex) String startIndex,
	                          @Path(Constants.LastIndex) String lastIndex);

	@POST("Upload")
	Call<ResponseBody> uploadFile(
			@Header(Constants.Authorization) String authorization,
			@Header("fileName") String fileName,
			@Body RequestBody photo
	);

//    @POST("Upload")
//    Call<ResponseBody> uploadGiftImage(@Header("token") String token,
//                                    @Header("fileName") String fileName,
//                                    @Body RequestBody photo);

	@GET("Location")
	Call<List<Location>> getLocations();

	@GET("Location/{" + Constants.ID + "}")
	Call<Location> getLocation(@Path(Constants.ID) String locationId);

	@GET("Category")
	Call<ArrayList<Category>> getCategories();

	@POST("Gift")
	Call<Gift> registerGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Body Gift gift
	);

}
