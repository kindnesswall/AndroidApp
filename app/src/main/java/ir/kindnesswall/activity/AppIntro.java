package ir.kindnesswall.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;

public class AppIntro extends IntroActivity {

	public static Intent createIntent() {
		Intent intent = new Intent(AppController.getAppContext(), AppIntro.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);

		/* Show/hide button */
		setButtonBackVisible(false);
/* Use skip button behavior */
		setButtonBackFunction(BUTTON_BACK_FUNCTION_SKIP);
/* Use back button behavior */
//		setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);

		/* Show/hide button */
		setButtonNextVisible(true);
/* Use next and finish button behavior */
		setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);
/* Use next button behavior */
//		setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT);

		setPageScrollDuration(2500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setPageScrollInterpolator(android.R.interpolator.fast_out_slow_in);
        }


		addSlide(new SimpleSlide.Builder()
				.title("فرهنگ دیوار مهربانی")
				.description("نیاز داری، بردار. نیاز نداری، بذار :)")
				.image(R.drawable.intro_img_1)
				.background(R.color.colorPrimary)
				.backgroundDark(R.color.colorPrimary)
				.layout(R.layout.activity_app_into)
				.build());

		addSlide(new SimpleSlide.Builder()
				.title("ادغام تیم رایگان‌بخشی")
				.description("به منظور هم افزایی عملیاتی هر چه بیشتر، تیم رایگان بخشی که اختصاص به گسترش فرهنگ اهدا و هبه دارد به دیوار مهربانی پیوست")
				.image(R.drawable.intro_img_6)
				.background(R.color.colorPrimary)
				.backgroundDark(R.color.colorAccent)
				.layout(R.layout.activity_app_into)
				.build());

		addSlide(new SimpleSlide.Builder()
				.title("با همکاری دونیت")
				.description(
						"دونِیت یک پلتفرم جذب سرمایه جمعی ایرانی است که در حال حاضر بر روی تامین سرمایه پروژه هایی که تاثیرات اجتماعی دارند، تمرکز کرده است.\n" +
						"\n")
				.image(R.drawable.intro_img_5)
				.background(R.color.colorPrimary)
				.backgroundDark(R.color.colorAccent)
				.layout(R.layout.activity_app_into)
				.build());

		addSlide(new SimpleSlide.Builder()
				.title("همیشه رایگان")
				.description("برنامه دیوار مهربانی برای همیشه رایگان خواهد ماند؛ بدون هر گونه تبلیغات.")
				.image(R.drawable.intro_img_4)
				.background(R.color.colorPrimary)
				.backgroundDark(R.color.colorPrimary)
				.layout(R.layout.activity_app_into)
				.build());

		addSlide(new SimpleSlide.Builder()
                .title("متن باز")
                .description("کدهای برنامه همیشه در دسترس برنامه نویسان خواهد بود.")
                .image(R.drawable.intro_img_2)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimary)
                .layout(R.layout.activity_app_into)
                .build());

//		setPageScrollDuration(500);
//		autoplay(2500, INFINITE);

		AppController.storeBoolean(Constants.SHOW_INTRO_BEFOR,true);
	}
}
