package ir.kindnesswall.app;

import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.model.api.input.LogoutInput;
import ir.kindnesswall.model.api.input.SetDeviceInput;
import ir.kindnesswall.model.api.output.RegisterOutput;
import ir.kindnesswall.model.api.output.TokenOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by HamedGh on 3/8/2016.
 */
public interface AccountRestAPI {

	@POST("Account/Register/{telephone}")
	Call<RegisterOutput> register(@Path("telephone") String telephone);

	@POST("Account/Logout")
	Call<ResponseBody> logout(
			@Header(Constants.ContentType) String contentType,
			@Header(Constants.Authorization) String authorization,
			@Body LogoutInput logoutInput
	);

	@FormUrlEncoded
	@POST("Account/Login")
	Call<TokenOutput> login(@Field("username") String username,
	                        @Field("password") String password,
	                        @Field("registerationId") String registerationId,
	                        @Field("deviceId") String deviceId,
	                        @Field("grant_type") String grant_type);

	@POST("Account/SetDevice")
	Call<ResponseBody> setDevice(
			@Header(Constants.ContentType) String contentType,
			@Body SetDeviceInput setDeviceInput
	);
}