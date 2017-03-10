package ir.hamed_gh.divaremehrabani.app;

import ir.hamed_gh.divaremehrabani.constants.Constants;
import ir.hamed_gh.divaremehrabani.model.api.output.TokenOutput;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    Call<ResponseBody> register(@Path("telephone") String telephone);

    @POST("Account/Logout")
    Call<ResponseBody> logout(
            @Header(Constants.ContentType) String contentType,
            @Header(Constants.Authorization) String authorization
    );

    @FormUrlEncoded
    @POST("Account/Login")
    Call<TokenOutput> login(@Field("username") String username,
                            @Field("password") String password,
                            @Field("grant_type") String grant_type);
}