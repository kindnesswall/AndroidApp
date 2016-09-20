package hamed_gh.ir.divaaremehrabani.app;

import java.util.Map;

import hamed_gh.ir.divaaremehrabani.model.PhotoGalleryResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hamed on 5/6/16.
 */
public interface RestAPI {

	@POST("Gallery/GetPhotoGallery")
	@FormUrlEncoded
	Call<PhotoGalleryResponse> getPhotoGallery(@FieldMap Map<String, String> params);

}
