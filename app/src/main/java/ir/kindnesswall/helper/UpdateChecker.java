package ir.kindnesswall.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationManagerCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aspsine.multithreaddownload.DownloadException;
import com.aspsine.multithreaddownload.DownloadManager;
import com.aspsine.multithreaddownload.DownloadRequest;

import java.io.File;
import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.helper.downloader.AppInfo;
import ir.kindnesswall.helper.downloader.DownloadService;


/**
 * Created by lms-3 on 28/08/2016.
 */
public class UpdateChecker {
	//Todo needs write and read permission ?? i nmanfest?
	//Todo: check needs android marshmallow permission file ?


	public UpdateDetail mUpdateDetail;
	String fileDownloadPath;
	Intent[] intents;
	Context context;
	TextView titleTextView, contentTextView, bodyTextView, positiveBtn, notNowBtn, neverBtn, progressPer;
	View cancelView;
	File mDownloadDir;
	DownloadManager mDownloadManager;
	NotificationManagerCompat mNotificationManager;
	MaterialDialog.Builder builder;
	MaterialDialog dialog;
	ProgressBar updaterProgressbar;
	boolean isForcedUpdate;


	public UpdateChecker(String appName, String latestVersion, String latestDownloadLink, String imageLink, ArrayList<String> releaseNotes) {
		if (imageLink != null && imageLink != "")
			mUpdateDetail = new UpdateDetail(appName, latestVersion, latestDownloadLink, imageLink, releaseNotes);
		else
			mUpdateDetail = new UpdateDetail(appName, latestVersion, latestDownloadLink, releaseNotes);
	}

	public static String getAppVersion(Context context) {
		String version = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(
					context.getPackageName(), 0);
			version = String.valueOf(info.versionCode);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	public void showUpdaterDialog(
			final Context context,
			String title,
			String body,
			ArrayList<String> textArray,
			String version,
			Intent[] intents,
			final boolean isForcedUpdate) {

		this.isForcedUpdate = isForcedUpdate;
		String sharePrefAnswer = AppController.getStoredString(
				Constants.VERSION_SKIP_UPDATE
		);

		if (sharePrefAnswer != null &&
				(getAppVersion(context).equals(version) || sharePrefAnswer.equals(version))&&
						!isForcedUpdate)
			return;

		this.intents = intents;
		this.context = context;

		mDownloadManager = DownloadManager.getInstance();
		mNotificationManager = NotificationManagerCompat.from(context);
		mDownloadDir = new File(Environment.getExternalStorageDirectory(), "Download");
		fileDownloadPath = mDownloadDir.getPath() + File.separator + mUpdateDetail.updateApk.getName() + ".apk";
		builder = MaterialDialogBuilder.create(context);
		dialog = builder.customView(R.layout.updater_dialog, false).show();
		dialog.setCancelable(!isForcedUpdate);

		findViews();
		init(title, body, textArray, version);

	}

	private void findViews() {
		titleTextView = (TextView) dialog.findViewById(R.id.title_dialog_tv);
		contentTextView = (TextView) dialog.findViewById(R.id.notes_text_dialog_tv);
		updaterProgressbar = (ProgressBar) dialog.findViewById(R.id.update_progressbar);
		bodyTextView = (TextView) dialog.findViewById(R.id.main_text_dialog_tv);
		positiveBtn = (TextView) dialog.findViewById(R.id.posetive_btn_text);
		notNowBtn = (TextView) dialog.findViewById(R.id.not_now_btn_text);
		neverBtn = (TextView) dialog.findViewById(R.id.never_btn_text);
		progressPer = (TextView) dialog.findViewById(R.id.progressPercentage);
		cancelView = dialog.findViewById(R.id.cancel_lay);
	}

	private void init(String title, String body, ArrayList<String> textArray, final String version) {
		titleTextView.setText(title);
		bodyTextView.setText(body);

		if (textArray != null) {
			StringBuilder strbuilder = new StringBuilder();
			strbuilder.append( "تغییرات اخیر" + ":\n");
			for (int i = 0; i < textArray.size(); i++) {
				strbuilder.append("- " + textArray.get(i) + "\n");
			}
			contentTextView.setText(strbuilder.toString());
			contentTextView.setMovementMethod(new ScrollingMovementMethod());
		}

		cancelView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelDownload();
			}
		});

		positiveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final File file = new File(fileDownloadPath);
				updaterProgressbar.setVisibility(View.VISIBLE);
				cancelView.setVisibility(View.VISIBLE);
				progressPer.setVisibility(View.VISIBLE);
				notNowBtn.setVisibility(View.GONE);
				positiveBtn.setVisibility(View.GONE);
				neverBtn.setVisibility(View.GONE);
				if (file.exists()) {
					installAPK(fileDownloadPath, context);
				} else {
					downloadAPK(0, mUpdateDetail.updateApk, mUpdateDetail.updateApk.getUrl());
				}
			}
		});

		if (isForcedUpdate) {

			notNowBtn.setVisibility(View.GONE);
			neverBtn.setVisibility(View.GONE);

		} else {

			notNowBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					dialog.dismiss();

				}
			});

			neverBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					AppController.storeString(Constants.VERSION_SKIP_UPDATE, version);
					dialog.dismiss();

				}
			});
		}

	}

	private void downloadAPK(final int position, final AppInfo appInfo, String tag) {

        //todo add image to notification bar
        /*if(appInfo.getUrl()==null || appInfo.getUrl()=="") {
            String imageUriStr = "";
            try {
                imageUriStr = ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + context.getResources().getResourcePackageName(R.mipmap.ic_launcher)
                        + '/' + context.getResources().getResourceTypeName(R.mipmap.ic_launcher) + '/' + context.getResources().getResourceEntryName(R.mipmap.ic_launcher );
                appInfo.setUrl(imageUriStr);
            }catch (Exception e){

            }

        }*/

		final DownloadRequest request = new DownloadRequest.Builder()
				.setName(appInfo.getName() + ".apk")
				.setUri(appInfo.getUrl())
				.setFolder(mDownloadDir)
				.build();
		mDownloadManager.download(request, tag, new UpdaterDownloadCallBackAPK(position, appInfo, mNotificationManager));

	}


	//    public  boolean isStoragePermissionGranted() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v("majid","Permission is granted");
//                return true;
//            } else {
//
//                Log.v("majid","Permission is revoked");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
//        }
//        else { //permission is automatically granted on sdk<23 upon installation
//            Log.v("majid","Permission is granted");
//            return true;
//        }
//
//
//    }


	//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (downloadId != 0) {
//            FileDownloader.getImpl().pause(downloadId);
//        }
//    }

	public void installAPK(String path, Context contextActivity) {
		File apkFile = new File(path);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		contextActivity.startActivity(intent);
	}

	public class UpdateDetail {
		public String appName = "";
		public String latestVersion = "";
		public String latestDownloadLink = "";
		public ArrayList<String> releaseNotes;
		public String imageLink = "";
		AppInfo updateApk;

		public UpdateDetail(String appName, String latestVersion, String latestDownloadLink, ArrayList<String> releaseNotes) {
			this.appName = appName;
			this.latestVersion = latestVersion;
			this.latestDownloadLink = latestDownloadLink;
			this.releaseNotes = releaseNotes;
			updateApk = new AppInfo("update_id", "Update-" + appName + "-V" + latestVersion, "", latestDownloadLink);
		}

		public UpdateDetail(String appName, String latestVersion, String latestDownloadLink, String imageLink, ArrayList<String> releaseNotes) {
			this.appName = appName;
			this.latestVersion = latestVersion;
			this.latestDownloadLink = latestDownloadLink;
			this.imageLink = imageLink;
			this.releaseNotes = releaseNotes;
			updateApk = new AppInfo("update_id", "Update-V" + latestVersion, imageLink, latestDownloadLink);
		}
	}

	private class UpdaterDownloadCallBackAPK extends DownloadService.DownloadCallBack {

		public UpdaterDownloadCallBackAPK(int position, AppInfo appInfo, NotificationManagerCompat notificationManager) {
			super(position, appInfo, notificationManager, context);
			updaterProgressbar.setMax(100);
			((Activity)context).runOnUiThread(new Runnable(){
				@Override
				public void run(){
					updaterProgressbar.setProgress(0);
					progressPer.setText("%0");
				}
			});
			positiveBtn.setEnabled(false);

		}

		@Override
		public void onCompleted() {
			super.onCompleted();
			installAPK(fileDownloadPath, context);
			updaterProgressbar.setProgress(100);
			progressPer.setText("%100");

			//todo  test some more : if is forceupdate dialog should not be dismissed and when app reinstalled updater won't be shown because of successfull update
			dialog.dismiss();

		}

		@Override
		public void onFailed(DownloadException e) {
			super.onFailed(e);
			cancelDownload();
			if (e.getErrorCode()==108) {
				Toasti.showS("فایل مورد نظر موجود نیست");
			}else {
				Toasti.showS("مشکلی در دانلود رخ داده است.");
			}
			//todo : in other way you should use finished Table
		}

		@Override
		public void onDownloadCanceled() {
			super.onDownloadCanceled();
			mDownloadManager.cancel(mUpdateDetail.latestDownloadLink);
			updaterProgressbar.setProgress(0);
			progressPer.setText("%0");
			positiveBtn.setEnabled(true);
//            final File file = new File(fileDownloadPath);
//            if (file.exists()) {
//                file.delete();
//            }
		}

		@Override
		public void onProgress(long finished, long total,final int progress) {
			super.onProgress(finished, total, progress);
			if (updaterProgressbar != null) {
				updaterProgressbar.setProgress(progress);
				progressPer.setText("%"+String.valueOf(progress));
			}
		}
	}

	private void cancelDownload() {
		mDownloadManager.cancel(mUpdateDetail.latestDownloadLink);//Todo i use this to redownload file & use fileExist
		progressPer.setText("%0");
		positiveBtn.setEnabled(true);
		updaterProgressbar.setVisibility(View.GONE);
		progressPer.setVisibility(View.GONE);
		cancelView.setVisibility(View.GONE);
		positiveBtn.setVisibility(View.VISIBLE);
		if (!isForcedUpdate) {
			notNowBtn.setVisibility(View.VISIBLE);
			neverBtn.setVisibility(View.VISIBLE);
		}
	}

}
