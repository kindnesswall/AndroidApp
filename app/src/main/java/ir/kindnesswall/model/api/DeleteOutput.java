package ir.kindnesswall.model.api;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by Hamed on 4/21/17.
 */

public class DeleteOutput extends ResponseBody {

	@Override
	public MediaType contentType() {
		return null;
	}

	@Override
	public long contentLength() {
		return 0;
	}

	@Override
	public BufferedSource source() {
		return null;
	}
}
