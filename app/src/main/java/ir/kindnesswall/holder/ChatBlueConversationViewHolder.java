package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.kindnesswall.R;

/**
 * Created by Mahshad on 1/1/2017 AD.
 */

public class ChatBlueConversationViewHolder extends RecyclerView.ViewHolder {

    public TextView mChatBlueConversationTV, mTimePmTV, mNamePersonTV;
    public RelativeLayout mChatBlueConversationLay;
    public ImageView mEmojiPersonChatIC;
    public ImageView mUnseenDotIC;


    public ChatBlueConversationViewHolder(View view) {
        super(view);
        mChatBlueConversationTV = (TextView) view.findViewById(R.id.chat_blue_conversation_tv);
        mTimePmTV = (TextView) view.findViewById(R.id.time_pm_tv);
        mChatBlueConversationLay = (RelativeLayout) view.findViewById(R.id.chat_conversation_lay);
        mUnseenDotIC = (ImageView) view.findViewById(R.id.dot_unseen_ic);

    }

}
