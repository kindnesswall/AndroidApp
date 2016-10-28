package hamed_gh.ir.divaaremehrabani.helper;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by RezaRg on 4/28/15 at 10:06 AM.
 */

public class MetricConverter {

    public static int px2dp(Context ctx, int sizeInPx) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeInPx,
                ctx.getResources().getDisplayMetrics()
        );
    }

    public static float dp2px(Context ctx, int sizeInDp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) sizeInDp * density);
    }
}