package ir.kindnesswall.app;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import ir.kindnesswall.constants.Constants;
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
	public static RestAPI longTimeoutService;

	private static Context context;
	private static AppController mInstance;

	private static SharedPreferences preferences;
	private static SharedPreferences.Editor editor;
	private Retrofit retrofit;
	private Retrofit accountRetrofit;

	private OkHttpClient httpClient;
	private Retrofit.Builder retrofitBuilder;
	private Retrofit.Builder longTimeoutRetrofitBuilder;
	private Retrofit longTimeOutRetrofit;
	private OkHttpClient longTimeOutHttpClient;

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

		DownloadConfiguration configuration = new DownloadConfiguration();
		configuration.setMaxThreadNum(10);
		configuration.setThreadNum(3);
		DownloadManager.getInstance().init(getApplicationContext(), configuration);

		preferences = this.getSharedPreferences("Prefs", MODE_PRIVATE);
		editor = preferences.edit();

		notificationManager = (NotificationManager) mInstance.getSystemService(Context.NOTIFICATION_SERVICE);

		retrofitInitialization();
		longTimeoutRetrofitInitialization();
	}

	private void retrofitInitialization() {

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		// set your desired log level
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		httpClient = new OkHttpClient.Builder()
				.readTimeout(40, TimeUnit.SECONDS)
				.connectTimeout(40, TimeUnit.SECONDS)
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

	private void longTimeoutRetrofitInitialization() {

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		// set your desired log level
		logging.setLevel(HttpLoggingInterceptor.Level.BODY);

		longTimeOutHttpClient = new OkHttpClient.Builder()
				.readTimeout(120, TimeUnit.SECONDS)
				.connectTimeout(120, TimeUnit.SECONDS)
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
		longTimeoutRetrofitBuilder = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.client(longTimeOutHttpClient);
		longTimeOutRetrofit = longTimeoutRetrofitBuilder.baseUrl(URIs.BASE_URL + URIs.API_VERSION).build();

		longTimeoutService = longTimeOutRetrofit.create(RestAPI.class);
	}

	public static void clearInfo() {

		AppController.storeString(Constants.Authorization, null);
		AppController.storeString(Constants.USER_ID, null);
		AppController.storeString(Constants.TELEPHONE, null);
		AppController.storeString(Constants.EMAIL, null);
		deleteSavedGift();

	}

	public static void deleteSavedGift() {
		AppController.storeBoolean(Constants.MY_GIFT_SAVED, false);

		AppController.storeString(Constants.MY_GIFT_TITLE, null);
		AppController.storeString(Constants.MY_GIFT_PRICE, null);
		AppController.storeString(Constants.MY_GIFT_ADDRESS, null);
		AppController.storeString(Constants.MY_GIFT_DESCRIPTION, null);
		AppController.storeString(Constants.MY_GIFT_PRICE, null);

		AppController.storeString(Constants.MY_GIFT_CATEGORY_ID, null);
		AppController.storeString(Constants.MY_GIFT_CATEGORY_NAME, null);

		AppController.storeString(Constants.MY_GIFT_LOCATION_ID, null);
		AppController.storeString(Constants.MY_GIFT_LOCATION_NAME, null);

		AppController.storeString(Constants.MY_GIFT_REGION_ID, null);
		AppController.storeString(Constants.MY_GIFT_REGION_NAME, null);

		int numberOfMyImages = AppController.getStoredInt(Constants.MY_GIFT_IMAGE_NUMBER);
		for (int i = 0; i < numberOfMyImages; i++) {
			AppController.storeString(Constants.MY_GIFT_IMAGE + "_" + i, null);
		}
		AppController.storeInt(Constants.MY_GIFT_IMAGE_NUMBER, 0);
	}
}
