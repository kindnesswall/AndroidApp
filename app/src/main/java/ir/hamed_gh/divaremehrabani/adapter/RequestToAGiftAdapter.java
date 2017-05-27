package ir.hamed_gh.divaremehrabani.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andexert.library.RippleView;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;

import ir.hamed_gh.divaremehrabani.R;
import ir.hamed_gh.divaremehrabani.activity.UserProfileActivity;
import ir.hamed_gh.divaremehrabani.constants.RequestName;
import ir.hamed_gh.divaremehrabani.helper.ApiRequest;
import ir.hamed_gh.divaremehrabani.helper.MaterialDialogBuilder;
import ir.hamed_gh.divaremehrabani.helper.Toasti;
import ir.hamed_gh.divaremehrabani.holder.RequestToAGiftHolder;
import ir.hamed_gh.divaremehrabani.model.api.RequestModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class RequestToAGiftAdapter extends RecyclerView.Adapter<RequestToAGiftHolder> implements ApiRequest.AdapterTagListener {

    private final ApiRequest apiRequest;
    private ArrayList<RequestModel> requestModels;
    private Context mContext;
    private MaterialDialog yesNoDialog;
    private RippleView yesBtnRipple;
    private ProgressView yesProgressView;
    private TextView yesTextView;
    private RippleView noBtnRipple;
    private MaterialDialog afterAcceptDialog;

    public RequestToAGiftAdapter(Context context, ArrayList<RequestModel> requestModels) {
        this.requestModels = requestModels;
        this.mContext = context;
        apiRequest = new ApiRequest(mContext, this);

        yesNoDialog = MaterialDialogBuilder.create(mContext).customView(R.layout.dialog_simple_yes_no, false).build();
        yesBtnRipple = (RippleView) yesNoDialog.findViewById(R.id.yes_ripple_btn_cardview);
        yesProgressView = (ProgressView) yesNoDialog.findViewById(R.id.yes_progressView);
        yesTextView = (TextView) yesNoDialog.findViewById(R.id.btn_yes);
        noBtnRipple = (RippleView) yesNoDialog.findViewById(R.id.no_ripple_btn_cardview);

    }

    @Override
    public RequestToAGiftHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_request_toagift, null);
        RequestToAGiftHolder mh = new RequestToAGiftHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(RequestToAGiftHolder myHolder, final int i) {

        myHolder.mPhoneTv.setText(requestModels.get(i).fromUser);

        myHolder.mDenyIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TextView) yesNoDialog.findViewById(R.id.message_textview)).setText("آیا از رد این درخواست مطمئن هستید؟");

                yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {

                        apiRequest.denyRequest(
                                requestModels.get(i).giftId,
                                requestModels.get(i).fromUserId
                        );

                        yesProgressView.setVisibility(View.VISIBLE);
                        yesTextView.setText("");
                    }
                });

                noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        yesNoDialog.dismiss();
                    }
                });

                yesNoDialog.show();
            }
        });

        myHolder.mAcceptIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TextView) yesNoDialog.findViewById(R.id.message_textview)).setText("آیا از تایید این درخواست مطمئن هستید؟");

                yesBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {

                        apiRequest.acceptRequest(
                                requestModels.get(i).giftId,
                                requestModels.get(i).fromUserId
                        );

                        yesProgressView.setVisibility(View.VISIBLE);
                        yesTextView.setText("");
                    }
                });

                noBtnRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        yesNoDialog.dismiss();
                    }
                });

                yesNoDialog.show();
            }
        });

        myHolder.mSmsIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(
                        new Intent(
                                Intent.ACTION_VIEW,
                                Uri.fromParts("sms", requestModels.get(i).fromUser, null)
                        )
                );
            }
        });

        myHolder.mCallIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "tel:" + requestModels.get(i).fromUser;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                mContext.startActivity(intent);

            }
        });

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ((BottomBarActivity) mContext).replaceFragment(
//                        new RequestsToAGiftFragment(), RequestsToAGiftFragment.class.getName()
//                );

                mContext.startActivity(UserProfileActivity.createIntent(requestModels.get(i).fromUserId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != requestModels ? requestModels.size() : 0);
    }

    @Override
    public void onResponse(Call call, Response response, int position, String tag) {

        if (response.body() instanceof ResponseBody) {
            if (tag.equals(RequestName.DenyRequest)) {
                requestModels.remove(position);
                notifyDataSetChanged();
            } else if (tag.equals(RequestName.AcceptRequest)) {
                afterAcceptDialog =
                        MaterialDialogBuilder
                                .create(mContext)
                                .customView(R.layout.dialog_after_accept_request, false)
                                .build();

                TextView message = (TextView) afterAcceptDialog.findViewById(R.id.message_textview);
                message.setText(
                        "هدیه شما به " + requestModels.get(position).fromUser  +  " اهدا شد."
                );

                ImageView callIV = (ImageView) afterAcceptDialog.findViewById(R.id.call_iv);
                callIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toasti.showS("callIV");
                    }
                });

                ImageView smsIV = (ImageView) afterAcceptDialog.findViewById(R.id.sms_iv);
                smsIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toasti.showS("smsIV");
                    }
                });

                ImageView profileIV = (ImageView) afterAcceptDialog.findViewById(R.id.profile_iv);
                profileIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toasti.showS("profileIV");
                    }
                });

                RippleView giftPageRv = (RippleView) afterAcceptDialog.findViewById(R.id.gift_page_ripple_btn);
                giftPageRv.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        Toasti.showS("giftPageRv");
                    }
                });

                RippleView okRv = (RippleView) afterAcceptDialog.findViewById(R.id.ok_ripple_btn);
                okRv.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        Toasti.showS("ok");
                    }
                });

                afterAcceptDialog.show();
            }
            yesNoDialog.dismiss();
        }

    }

    @Override
    public void onFailure(Call call, Throwable t, String tag) {

    }

}
