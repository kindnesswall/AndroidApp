package ir.hamed_gh.divaremehrabani.app;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.crashlytics.android.Crashlytics;

//import io.fabric.sdk.android.Fabric;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class AppController extends Application {
	public static final String REGISTRATION_COMPLETE = "registrationComplete";
	public static final String PUSH_NOTIFICATION = "pushNotification";

	public static final int NOTIFICATION_ID = 100;
	public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
	private static NotificationManager notificationManager;

	public static final String TAG = AppController.class
			.getSimpleName();

	public static RestAPI service;
	public static AccountRestAPI accountService;
	private static Context context;
	private static AppController mInstance;

	private static SharedPreferences preferences;
	private static SharedPreferences.Editor editor;
	private Retrofit retrofit;
	private Retrofit accountRetrofit;

	private OkHttpClient httpClient;
	private Retrofit.Builder retrofitBuilder;

	public static NotificationManager getNotificationManager() {
		return notificationManager;
	}

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

	public static void storeInt(String key, Integer value) {
		editor.putInt(key, value);
		editor.apply();
	}

	public static void storeBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static ArrayList<String> getStoredNotifs(String notifications) {
		Set<String> set = preferences.getStringSet(notifications, new HashSet<String>());
		return new ArrayList<String>(set);
	}

	public static void storeNotifs(ArrayList<String> list, String key) {
		Set<String> set = new HashSet<>();
		set.addAll(list);
		editor.putStringSet(key,set);
		editor.apply();
	}

	public static void removeShared(String key)
	{
		editor.remove(key).apply();
	}


	public static Context getAppContext() {
		return AppController.context;
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	private boolean isCheckedUpdate = false;

	public boolean isCheckedUpdate() {
		return isCheckedUpdate;
	}

	public void setIsCheckedUpdate(boolean isCheckedUpdate) {
		this.isCheckedUpdate = isCheckedUpdate;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		Fabric.with(this, new Crashlytics());

		mInstance = this;
		AppController.context = getApplicationContext();

		preferences = this.getSharedPreferences("Prefs", MODE_PRIVATE);
		editor = preferences.edit();

//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .displayer(new FadeInBitmapDisplayer(500, true, true, true))
//                .build();
//
//        // Create global configuration and initialize ImageLoader with this config
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .defaultDisplayImageOptions(defaultOptions)
//                .build();
//
//        ImageLoader.getInstance().init(config);

		notificationManager = (NotificationManager) mInstance.getSystemService(Context.NOTIFICATION_SERVICE);

		retrofitInitialization();

//        AppController.storeString(Constants.CITY_ID,"1");

	}

	private void retrofitInitialization() {

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		// set your desired log level
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		httpClient = new OkHttpClient.Builder()
				.addInterceptor(
						new Interceptor() {
							@Override
							public Response intercept(Interceptor.Chain chain) throws IOException {
								Request request = chain.request().newBuilder()
//                                        .addHeader(Constants.ContentType,Constants.JSON_TYPE)
										.build();
								return chain.proceed(request);
							}
						})
				.addInterceptor(logging)
				.build();

		//Creating Rest Services
		retrofitBuilder = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient);

		retrofit = retrofitBuilder.baseUrl(URIs.BASE_URL + URIs.API_VERSION).build();
		accountRetrofit = retrofitBuilder.baseUrl(URIs.BASE_URL).build();

		service = retrofit.create(RestAPI.class);
		accountService = accountRetrofit.create(AccountRestAPI.class);
	}

}
