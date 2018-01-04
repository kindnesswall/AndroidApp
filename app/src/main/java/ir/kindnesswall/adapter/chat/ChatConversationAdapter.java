package ir.kindnesswall.adapter.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.fragment.chat.ChatConversationFragment;
import ir.kindnesswall.holder.ChatBlueConversationViewHolder;
import ir.kindnesswall.holder.ChatGrayConversationViewHolder;
import ir.kindnesswall.model.LastMessage;

public class ChatConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	ChatConversationFragment chatConversationFragment;
	private Context mContext;
	private ArrayList<LastMessage> chat;
	private String chatId, countUnseen;

	public ChatConversationAdapter(ChatConversationFragment chatConversationFragment, Context mContext, ArrayList<LastMessage> chat, String chatId, String countUnseen) {
		this.chatConversationFragment = chatConversationFragment;
		this.mContext = mContext;
		this.chat = chat;
		this.chatId = chatId;
		this.countUnseen = countUnseen;
	}

	@Override
	public int getItemViewType(int position) {
		if (AppController.getStoredString(Constants.USER_ID).equals(chat.get(position).sender_id)) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View v;
		switch (viewType) {

			case 0:
				v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_blue_conversation, parent, false);

				ChatBlueConversationViewHolder chatBlueConversationViewHolder = new ChatBlueConversationViewHolder(v);

				return chatBlueConversationViewHolder;

			case 1:
				v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gray_conversation, parent, false);
				ChatGrayConversationViewHolder chatGrayConversationViewHolder = new ChatGrayConversationViewHolder(v);
				return chatGrayConversationViewHolder;

		}
		return null;
	}

	public void seeAllMessages() {
		countUnseen = "0";
		notifyDataSetChanged();
	}

	public ArrayList<LastMessage> addChatList(LastMessage newMsg) {
//        chat.clear();
		chat.add(0, newMsg);
		notifyDataSetChanged();
		return chat;
//        Toast.makeText(mContext,chat.get(chat.size() - 1).text,Toast.LENGTH_LONG);
	}

	/////////mitra

	public ArrayList<LastMessage> updateIdNullMessageByText(ArrayList<LastMessage> list, LastMessage newMsg) {
		LastMessage msg;

		for (int i = list.size() - 1; i >= 0; i--) {
			msg = list.get(i);
			if (msg.id == null && msg.text.equals(newMsg.text)) {
				list.set(i, newMsg);
				//  Toasti.showL(mContext,i+"");

				notifyDataSetChanged();

				return list;
			}
		}
		list.add(0, newMsg);
		notifyDataSetChanged();

		return list;
	}

//    private SeekBar mSeekbar;
//    private TextView mTotalTV;
//    private TextView mProgressTV;

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		switch (getItemViewType(position)) {

			case 0:
				setUpBlueRowConverstation((ChatBlueConversationViewHolder) holder, position);
				break;

			case 1:
				setUpGrayRowConverstation((ChatGrayConversationViewHolder) holder, position);
				break;
		}
	}

	private void setUpGrayRowConverstation(ChatGrayConversationViewHolder chatGrayConversationViewHolder, int position) {

		if (chat.get(position).text != null) {
			chatGrayConversationViewHolder.mChatGrayConversationTV.setText(chat.get(position).text);
			/////mitra

			if (chat.get(position).id == null) {
//                        chatGrayConversationViewHolder.mTimePmTV.setTextColor(R.color.blue_400);
				chatGrayConversationViewHolder.mTimePmTV.setText(chat.get(position).time + " sending");

			} else {
				chatGrayConversationViewHolder.mTimePmTV.setText(chat.get(position).time);
			}
		}

	}

	private void setUpBlueRowConverstation(ChatBlueConversationViewHolder chatBlueConversationViewHolder, int position) {

		chatBlueConversationViewHolder.mChatBlueConversationTV.setText((chat.get(position).text != null) ? chat.get(position).text : "");

		chatBlueConversationViewHolder.mTimePmTV.setText(chat.get(position).time);

		if (countUnseen != null && position < (Integer.parseInt(countUnseen))) {
			chatBlueConversationViewHolder.mUnseenDotIC.setVisibility(View.VISIBLE);
		} else {
			chatBlueConversationViewHolder.mUnseenDotIC.setVisibility(View.GONE);
		}

	}

	@Override
	public int getItemCount() {
		return chat.size();
	}

}