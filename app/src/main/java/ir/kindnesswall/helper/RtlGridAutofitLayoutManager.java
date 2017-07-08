package ir.kindnesswall.helper;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by lms-3 on 19/09/2016.
 */
public class RtlGridAutofitLayoutManager extends GridAutofitLayoutManager {

    public RtlGridAutofitLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RtlGridAutofitLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public RtlGridAutofitLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    protected boolean isLayoutRTL(){
        return true;
    }
}
