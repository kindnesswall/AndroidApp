package ir.kindnesswall.model.api.output;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestGiftOutput extends ResponseBody {
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
