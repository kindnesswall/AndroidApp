package ir.hamed_gh.divaremehrabani.customviews.textviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import ir.hamed_gh.divaremehrabani.helper.NumberTranslator;

public class TextViewIranSansBold extends TextView {

    public TextViewIranSansBold(Context context) {
        super(context);
        if (!isInEditMode())

            setFont();
    }

    public TextViewIranSansBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())

            setFont();
    }

    public TextViewIranSansBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())

            setFont();
    }

    private void setFont() {
        Typeface iranSans = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/iran_sans_bold_second.ttf");
        setTypeface(iranSans, Typeface.NORMAL);

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = NumberTranslator.toPersian(text.toString());
        super.setText(text, type);
    }
}
