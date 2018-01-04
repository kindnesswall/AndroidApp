package ir.kindnesswall.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.kindnesswall.R;

/**
 * Created by Mahshad on 1/1/2017 AD.
 */

public class ChatGrayConversationViewHolder extends RecyclerView.ViewHolder {

    public TextView mChatGrayConversationTV, mTimePmTV;
    public RelativeLayout mChatGrayConversationLay;


    public ChatGrayConversationViewHolder(View view) {
        super(view);
        mChatGrayConversationTV = (TextView) view.findViewById(R.id.chat_gray_conversation_tv);
        mTimePmTV = (TextView) view.findViewById(R.id.time_pm_tv);
        mChatGrayConversationLay = (RelativeLayout) view.findViewById(R.id.chat_conversation_lay);
    }
}
