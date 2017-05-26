package ir.hamed_gh.divaremehrabani.helper;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by HamedGh on 3/8/2016.
 */

public class ProgressRequestBody extends RequestBody {

	private static final int DEFAULT_BUFFER_SIZE = 2048;
	MediaType contentType;
	int offset;
	private File mFile;
	private UploadCallbacks mListener;

	public ProgressRequestBody(final MediaType contentType,
	                           final File file,
	                           UploadCallbacks listener) {
		mFile = file;
		offset = 0;
		this.contentType = contentType;
		mListener = listener;
	}

	@Override
	public MediaType contentType() {
		// i want to upload only images
		return contentType;
	}

	@Override
	public long contentLength() throws IOException {
		return mFile.length();
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		long fileLength = mFile.length();
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		FileInputStream in = new FileInputStream(mFile);
		long uploaded = 0;

		try {
			int read;
			Handler handler = new Handler(Looper.getMainLooper());
			while ((read = in.read(buffer)) != -1) {

				// update progress on UI thread
				handler.post(new ProgressUpdater(uploaded, fileLength));

				uploaded += read;
				sink.write(buffer, offset, read);
			}
		} finally {
			in.close();
		}
	}

	public interface UploadCallbacks {
		void onProgressUpdate(int percentage);

		void onError();

		void onFinish();
	}

	private class ProgressUpdater implements Runnable {
		private long mUploaded;
		private long mTotal;

		public ProgressUpdater(long uploaded, long total) {
			mUploaded = uploaded;
			mTotal = total;
		}

		@Override
		public void run() {
			mListener.onProgressUpdate((int) (100 * mUploaded / mTotal));
		}
	}
}
