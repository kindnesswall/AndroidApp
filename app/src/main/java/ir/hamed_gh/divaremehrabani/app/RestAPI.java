package ir.hamed_gh.divaremehrabani.app;

import java.util.ArrayList;
import java.util.List;

import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.model.api.Category;
import ir.hamed_gh.divaremehrabani.model.api.Gift;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;
import ir.hamed_gh.divaremehrabani.model.api.User;
import ir.hamed_gh.divaremehrabani.model.api.input.BookmarkInput;
import ir.hamed_gh.divaremehrabani.model.api.input.ReportInput;
import ir.hamed_gh.divaremehrabani.model.api.input.RequestGiftInput;
import ir.hamed_gh.divaremehrabani.model.api.output.UpdateOutput;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HamedGh on 3/8/2016.
 */
public interface RestAPI {

	@GET("Gift/{"
			+ Constants.CITY_ID + "}/{"
			+ Constants.REGION_ID + "}/{"
			+ Constants.CATEGORY_ID + "}/{"
			+ Constants.StartIndex + "}/{"
			+ Constants.LastIndex + "}")
	Call<List<Gift>> getGifts(
			@Path(Constants.CITY_ID) String locationId,
			@Path(Constants.REGION_ID) String regoin,
			@Path(Constants.CATEGORY_ID) String categoryId,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex,
			@Query(Constants.SEARCH_TEXT) String searchText
	);

	@DELETE("Gift/{" + Constants.GIFT_ID + "}")
	Call<ResponseBody> deleteGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId);

	@GET("Gift/{" + Constants.GIFT_ID + "}")
	Call<Gift> getGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId);

	@DELETE("DeleteMyRequest/{" + Constants.GIFT_ID + "}")
	Call<ResponseBody> deleteMyRequest(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId
	);

	@PUT("AcceptRequest/{" + Constants.GIFT_ID + "}/{" + Constants.FROM_USER_ID + "}")
	Call<ResponseBody> acceptRequest(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId,
			@Path(Constants.FROM_USER_ID) String userId
	);

	@PUT("DenyRequest/{" + Constants.GIFT_ID + "}/{" + Constants.FROM_USER_ID + "}")
	Call<ResponseBody> denyRequest(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId,
			@Path(Constants.FROM_USER_ID) String userId
	);

	@POST("BookmarkGift")
	Call<ResponseBody> bookmark(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Body BookmarkInput bookmarkInput);

	@POST("Upload")
	Call<ResponseBody> uploadFile(
			@Header(Constants.Authorization) String authorization,
			@Header("fileName") String fileName,
			@Body RequestBody photo
	);

	@GET("Category/{"
			+ Constants.StartIndex + "}/{"
			+ Constants.LastIndex + "}/{"
			+ Constants.DensityId + "}"
	)
	Call<ArrayList<Category>> getCategories(
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex,
			@Path(Constants.DensityId) String DensityId
	);

	@POST("Gift")
	Call<Gift> registerGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Body Gift gift
	);


	@POST("ReportGift")
	Call<ResponseBody> reportGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Body ReportInput reportInput
	);

	@PUT("Gift/{" + Constants.GIFT_ID + "}")
	Call<ResponseBody> editGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId,
			@Body Gift gift
	);

	@GET("SentRequestList/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<RequestModel>> getSentRequestList(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@GET("BookmarkList/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<Gift>> getBookmarkList(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@POST("RequestGift/")
	Call<ResponseBody> sendRequestGift(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Body RequestGiftInput requestGiftInput);

	@GET("GiftsRequested/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<Gift>> getRequestsMyGifts(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@GET("User/{" + Constants.USER_ID + "}")
	Call<User> getUser(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.USER_ID) String userID
	);

	@GET("RecievedRequestList/{" + Constants.GIFT_ID + "}/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<RequestModel>> getRecievedRequestList(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.GIFT_ID) String giftId,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@GET("RegisteredGifts/{" + Constants.USER_ID + "}/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<Gift>> getMyRegisteredGifts(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.USER_ID) String userId,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@GET("DonatedGifts/{" + Constants.USER_ID + "}/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<Gift>> getMyDonatedGifts(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.USER_ID) String userId,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@GET("ReceivedGifts/{" + Constants.USER_ID + "}/{" + Constants.StartIndex + "}/{" + Constants.LastIndex + "}")
	Call<ArrayList<Gift>> getMyReceivedGifts(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Path(Constants.USER_ID) String userId,
			@Path(Constants.StartIndex) String startIndex,
			@Path(Constants.LastIndex) String lastIndex
	);

	@GET("GetUpdatedVersion/{" + Constants.VERSION_NAME + "}")
	Call<UpdateOutput> getUpdatedVersion(@Path(Constants.VERSION_NAME) int versionName);
}