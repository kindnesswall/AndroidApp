package ir.kindnesswall.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

import java.util.Locale;

import ir.kindnesswall.app.AppController;

/**
 * Created by Hamed.Gh on 6/26/2015.
 */

public class DeviceInfo {

	private static DisplayMetrics getMetrics(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		return outMetrics;
	}

	public static float getHeightPx(Activity activity) {
		DisplayMetrics outMetrics = getMetrics(activity);
		return outMetrics.heightPixels;
	}

	public static float getWidthPx(Activity activity) {
		DisplayMetrics outMetrics = getMetrics(activity);
		return outMetrics.widthPixels;
	}

	public static float getHeightDp(Activity activity) {
		DisplayMetrics outMetrics = getMetrics(activity);
		float density = AppController.getAppContext().getResources().getDisplayMetrics().density;

		return outMetrics.heightPixels / density;
	}

	public static float getWidthDp(Activity activity) {
		DisplayMetrics outMetrics = getMetrics(activity);
		float density = AppController.getAppContext().getResources().getDisplayMetrics().density;

		return outMetrics.widthPixels / density;
	}

	public static String getAndroidID() {
		return Settings.Secure.getString(AppController.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	public static String getDeviceID(Context ctx) {
		final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static String getDPICatagory() {
		String densityString;
		int density = AppController.getAppContext().getResources().getDisplayMetrics().densityDpi;

		switch (density) {
			case DisplayMetrics.DENSITY_LOW:
				densityString = "ldpi";
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				densityString = "mdpi";
				break;
			case DisplayMetrics.DENSITY_HIGH:
				densityString = "hdpi";
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				densityString = "xhdpi";
				break;
			case DisplayMetrics.DENSITY_XXHIGH:
				densityString = "xxhdpi";
				break;
			case DisplayMetrics.DENSITY_XXXHIGH:
				densityString = "xxxhdpi";
				break;
			default:
				densityString = "xxhdpi";
				break;
		}
		return densityString;
	}

	public static int getDPI() {
		return AppController.getAppContext().getResources().getDisplayMetrics().densityDpi;
	}

	public static String getAppVersionName() {
		PackageInfo pInfo = null;
		try {
			pInfo = AppController.getAppContext().getPackageManager().getPackageInfo(AppController.getAppContext().getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pInfo.versionName;
	}

	public static int getAppVersionCode() {
		PackageInfo pInfo = null;
		try {
			pInfo = AppController.getAppContext().getPackageManager().getPackageInfo(AppController.getAppContext().getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pInfo.versionCode;
	}

	public static boolean isRTL() {
		return isRTL(Locale.getDefault());
	}

	public static boolean isRTL(Locale locale) {
		final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
		return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
				directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
	}

	public static boolean hasSupportRTL() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

}