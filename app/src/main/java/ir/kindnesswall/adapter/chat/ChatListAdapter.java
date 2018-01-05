package ir.kindnesswall.adapter.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.kindnesswall.R;
import ir.kindnesswall.activity.ChatActivity;
import ir.kindnesswall.fragment.chat.ChatConversationFragment;
import ir.kindnesswall.holder.ChatListHolder;
import ir.kindnesswall.model.Chat;

/**
 * Created by HamedGh on 3/8/2016.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListHolder> {

	private ArrayList<Chat> chats;
	private Context mContext;

	public ChatListAdapter(Context context, ArrayList<Chat> chats) {
		this.chats = chats;
		this.mContext = context;
	}

	@Override
	public ChatListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_chat_list, null);
		ChatListHolder mh = new ChatListHolder(v);
		return mh;

	}

	@Override
	public void onBindViewHolder(final ChatListHolder holder, final int position) {

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((ChatActivity) mContext).hideFab();
				((ChatActivity) mContext).replaceFragment(
						ChatConversationFragment.newInstance("1", "2", "3"),
						ChatConversationFragment.class.getName()
				);
			}
		});

	}

	@Override
	public int getItemCount() {

		return 20;
//		return (null != chats ? chats.size() : 0);
	}
}
