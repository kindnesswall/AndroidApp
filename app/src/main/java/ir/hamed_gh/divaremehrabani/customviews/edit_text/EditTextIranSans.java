package ir.hamed_gh.divaremehrabani.customviews.edit_text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextIranSans extends EditText {

	public EditTextIranSans(Context context) {
		super(context);
		if (!isInEditMode())

			setFont();
	}

	public EditTextIranSans(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())

			setFont();
	}

	public EditTextIranSans(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode())

			setFont();
	}

	private void setFont() {
		Typeface iranSans = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/IRANSansMobile_Light-4.1.ttf");
		setTypeface(iranSans, Typeface.NORMAL);

	}
}
