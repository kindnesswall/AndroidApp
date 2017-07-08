package ir.kindnesswall.fragment.mywall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.kindnesswall.R;
import ir.kindnesswall.activity.AppIntro;
import ir.kindnesswall.activity.BottomBarActivity;
import ir.kindnesswall.activity.LoginActivity;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.dialogfragment.ChoosePlaceDialogFragment;
import ir.kindnesswall.fragment.BaseFragment;
import ir.kindnesswall.fragment.mywall.mygifts.MyGiftsFragment;
import ir.kindnesswall.fragment.mywall.requests.MyRequestsFragment;
import ir.kindnesswall.helper.MaterialDialogBuilder;
import ir.kindnesswall.helper.Snackbari;
import ir.kindnesswall.interfaces.ChoosePlaceCallback;
import ir.kindnesswall.model.Place;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class MyWallFragment extends BaseFragment implements ChoosePlaceCallback {

	@Bind(R.id.location_tv)
	TextView mLocationTv;

	@Bind(R.id.log_in_out_txt)
	TextView mLog_in_out_txt;

	@Bind(R.id.location_lay)
	RelativeLayout locationLayout;

	@Bind(R.id.bookmark_lay)
	RelativeLayout bookmarkLayout;

	@Bind(R.id.statistic_lay)
	RelativeLayout statisticLayout;

	@Bind(R.id.my_gift_lay)
	RelativeLayout myGiftLay;

	@Bind(R.id.my_request_lay)
	RelativeLayout myRequestsLay;

	@Bind(R.id.log_in_out_lay)
	RelativeLayout mLog_in_out_lay;

	@Bind(R.id.about_lay)
	RelativeLayout mAboutLay;

	@Bind(R.id.rules_lay)
	RelativeLayout mRulesLay;

	@Bind(R.id.report_bug_lay)
	RelativeLayout mContactUsLay;

	@Bind(R.id.our_team_lay)
	RelativeLayout mOurTeamLay;

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (rootView != null) {
			if (rootView.getParent() != null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
			return rootView;
		}
		rootView = inflater.inflate(R.layout.fragment_my_wall, container, false);

		ButterKnife.bind(this, rootView);

		String token = AppController.getStoredString(Constants.Authorization);
		if (token != null) {
			((EditText) rootView.findViewById(R.id.token_et)).setText(token);
		}else {

		}

		init();

		setListeners();

		return rootView;
	}

	@Override
	protected void init() {
		super.init();

		mLocationTv.setText(AppController.getStoredString(Constants.MY_LOCATION_NAME));
	}

	@Override
	public void onResume() {
		super.onResume();

		if (AppController.getStoredString(Constants.Authorization) != null) {
//			mLog_in_out_lay.setVisibility(View.VISIBLE);

			mLog_in_out_lay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					MaterialDialog.Builder builder =
							MaterialDialogBuilder.create(getContext())
									.customView(R.layout.dialog_simple_yes_no, false);

					final MaterialDialog dialog = builder.build();
					((TextView) dialog.findViewById(R.id.message_textview)).setText(
							getContext().getResources().getString(R.string.dialog_exit_account)
					);

					RippleView yesBtnRipple = (RippleView) dialog.findViewById(R.id.yes_ripple_btn_cardview);
					yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
						@Override
						public void onComplete(RippleView rippleView) {

							apiRequest.logout();
//							mLog_in_out_lay.setVisibility(View.INVISIBLE);
							AppController.clearInfo();
							mLog_in_out_txt.setText("ورود");
							mLog_in_out_lay.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {

									startActivity(LoginActivity.createIntent());

								}
							});

							dialog.dismiss();
						}
					});

					RippleView noBtnRipple = (RippleView) dialog.findViewById(R.id.no_ripple_btn_cardview);
					noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
						@Override
						public void onComplete(RippleView rippleView) {
							dialog.dismiss();
						}
					});

					dialog.show();

				}
			});

			mLog_in_out_txt.setText(
					"خروج (" + AppController.getStoredString(Constants.TELEPHONE) + ")"
			);
		} else {
//			mLog_in_out_lay.setVisibility(View.INVISIBLE);


			mLog_in_out_txt.setText("ورود");
			mLog_in_out_lay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					startActivity(LoginActivity.createIntent());

				}
			});
		}

		((BottomBarActivity) getActivity()).mToolbarTitleTextView.setText("دیوار من");

	}

	@Override
	public void onResponse(Call call, Response response) {
		Snackbari.showS(mLog_in_out_lay, "شما با موفقیت از حساب خارج شدید");
	}

	private void setListeners() {

		mOurTeamLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((BottomBarActivity) getActivity()).replaceFragment(
						new OurTeamFragment(), OurTeamFragment.class.getName()
				);
			}
		});

		mContactUsLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/@Kindness_Wall_Admin"));
				startActivity(telegram);
			}
		});

		mAboutLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(AppIntro.createIntent());
			}
		});

		myGiftLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((BottomBarActivity) getActivity()).replaceFragment(
						new MyGiftsFragment(), MyGiftsFragment.class.getName()
				);
			}
		});

		myRequestsLay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((BottomBarActivity) getActivity()).replaceFragment(
						new MyRequestsFragment(), MyRequestsFragment.class.getName()
				);
			}
		});

		locationLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChoosePlaceDialogFragment choosePlaceDialogFragment = new ChoosePlaceDialogFragment();
				choosePlaceDialogFragment.setTargetFragment(MyWallFragment.this, 0);
				choosePlaceDialogFragment.show(fm, "fragment_name");
			}
		});

		statisticLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				((BottomBarActivity) getActivity()).replaceFragment(
						new StatisticFragment(), StatisticFragment.class.getName()
				);

			}
		});

		bookmarkLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((BottomBarActivity) getActivity()).replaceFragment(
						new BookmarkFragment(), BookmarkFragment.class.getName()
				);
			}
		});
	}

	@Override
	public void onCitySelected(Place city) {

		AppController.storeString(Constants.MY_LOCATION_ID, city.id);
		AppController.storeString(Constants.MY_LOCATION_NAME, city.name);

		mLocationTv.setText(city.name);

		Intent i = context.getPackageManager()
						.getLaunchIntentForPackage( context.getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	@Override
	public void onRegionSelected(Place region) {

	}
}
