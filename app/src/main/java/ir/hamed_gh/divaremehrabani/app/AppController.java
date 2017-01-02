package ir.hamed_gh.divaremehrabani.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;

/**
 * Created by Hamed on 4/3/16.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();
    public static RestAPI service;
    private static Context context;
    private static AppController mInstance;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    public static Retrofit retrofit;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static String getStoredString(String key) {
        return preferences.getString(key, null);
    }

    public static int getStoredInt(String key) {
        return preferences.getInt(key, 0);
    }

    public static boolean getStoredBoolean(String key, boolean value) {
        return preferences.getBoolean(key, value);
    }

    public static void storeString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static void storeInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static void storeBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//		Fabric.with(this, new Crashlytics());

        mInstance = this;
        AppController.context = getApplicationContext();

        preferences = this.getSharedPreferences("Prefs", MODE_PRIVATE);
        editor = preferences.edit();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(500, true, true, true))
                .build();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);

        retrofitInitialization();

    }

    private void retrofitInitialization() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
//                                        .addHeader("X-Parse-Master-Key","MY_MASTER_KEY")
                                        .build();
                                return chain.proceed(request);
                            }
                        }).build();

        //Creating Rest Services
        retrofit = new Retrofit.Builder()
                .baseUrl(URIs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        service = retrofit.create(RestAPI.class);
    }

}
