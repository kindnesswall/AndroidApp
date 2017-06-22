package ir.kindnesswall.customviews.textviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import ir.kindnesswall.helper.NumberTranslator;

public class TextViewDivarIcons extends TextView {

	Context ctx;

	public TextViewDivarIcons(Context context) {
		super(context);

		ctx = context;
		if (!isInEditMode()) {
			setFont();
		}
//		setColor();
	}

	public TextViewDivarIcons(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			setFont();
		}
//		setColor();
	}

	public TextViewDivarIcons(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			setFont();
		}
//		setColor();
	}

	private void setFont() {
		Typeface iranSans = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/divar.ttf");
		setTypeface(iranSans, Typeface.NORMAL);
	}

//    private void setColor() {
////		setTextColor();
//        setTextColor(ctx.getResources().getColor(R.color.pretty_black));
//    }

	@Override
	public void setText(CharSequence text, BufferType type) {

		if (text.toString().matches("-?\\d+(\\.\\d+)?") && !text.toString().substring(0, 1).equals("0")) {

			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

			symbols.setGroupingSeparator('\'');
			formatter.setDecimalFormatSymbols(symbols);
			String th = formatter.format(new BigDecimal(Double.valueOf(text.toString())));
//			String th2 = String.format("%,d", text.toString());
			text = (NumberTranslator.toPersian(th));
		} else {
			text = NumberTranslator.toPersian(text.toString());
		}

//        final SpannableStringBuilder spannable = new SpannableStringBuilder(text);
//        final ForegroundColorSpan span = new ForegroundColorSpan(Color.GREEN);
//        spannable.setSpan(span,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		super.setText(text, type);

	}
}