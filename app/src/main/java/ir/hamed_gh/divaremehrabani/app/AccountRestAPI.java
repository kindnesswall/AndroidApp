package ir.hamed_gh.divaremehrabani.app;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hamed on 5/6/16.
 */
public interface AccountRestAPI {

    @POST("Account/Register/{telephone}")
    Call<ResponseBody> register(@Path("telephone") String telephone);

    @POST("Account/Logout")
    Call logout();

    @POST("token")
    Call token();
}