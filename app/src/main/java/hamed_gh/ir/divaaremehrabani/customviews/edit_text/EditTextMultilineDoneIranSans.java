package hamed_gh.ir.divaaremehrabani.customviews.edit_text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

public class EditTextMultilineDoneIranSans extends EditText {

    public EditTextMultilineDoneIranSans(Context context) {
        super(context);
        if (!isInEditMode())

            setFont();
    }

    public EditTextMultilineDoneIranSans(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())

            setFont();
    }

    public EditTextMultilineDoneIranSans(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())

            setFont();
    }

    private void setFont() {
        Typeface iranSans = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/iran_sans_regular.ttf");
        setTypeface(iranSans, Typeface.NORMAL);

    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        int imeActions = outAttrs.imeOptions & EditorInfo.IME_MASK_ACTION;
        if ((imeActions & EditorInfo.IME_ACTION_DONE) != 0) {
            // clear the existing action
            outAttrs.imeOptions ^= imeActions;
            // set the DONE action
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_DONE;
        }
        if ((outAttrs.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
        return connection;
    }
}
