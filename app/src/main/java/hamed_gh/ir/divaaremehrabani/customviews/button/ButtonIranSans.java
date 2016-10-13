package hamed_gh.ir.divaaremehrabani.customviews.button;//package myCustomViews;//package myCustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonIranSans extends Button {

	public ButtonIranSans(Context context) {
		super(context);
		if (!isInEditMode())
			setFont();
	}

	public ButtonIranSans(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
			setFont();
	}

	public ButtonIranSans(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode())
			setFont();
	}


	private void setFont() {
		Typeface font = Typeface.createFromAsset(
				getContext().getAssets(), "fonts/IRANSansMobile_Light-4.1.ttf");

		setTypeface(font, Typeface.NORMAL);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
	}
}