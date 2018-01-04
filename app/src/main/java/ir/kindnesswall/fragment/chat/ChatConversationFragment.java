package ir.kindnesswall.fragment.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ir.kindnesswall.R;
import ir.kindnesswall.adapter.chat.ChatConversationAdapter;
import ir.kindnesswall.app.AppController;
import ir.kindnesswall.constants.Constants;
import ir.kindnesswall.customviews.edit_text.EditTextIranSans;
import ir.kindnesswall.customviews.textviews.TextViewIranSansRegular;
import ir.kindnesswall.helper.ApiRequest;
import ir.kindnesswall.helper.EndlessRecyclerViewScrollListener;
import ir.kindnesswall.model.JalaliCalendar;
import ir.kindnesswall.model.LastMessage;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Mahshad on 12/31/2016 AD.
 */

public class ChatConversationFragment extends Fragment implements ApiRequest.Listener {

	private static final String TAG = "ChatConversationFragment";
	private static final int RUNNING = 1;
	public static int positionSelected = -1;
	private RecyclerView mRecyclerView;
	private ChatConversationAdapter chatConversationAdapter;
	private Context mContext;
	private ArrayList<LastMessage> chatConversationArrayList = new ArrayList<>();
	private RelativeLayout mSendTxtLay;
	private View rootView;
	private ApiRequest apiRequest;
	private int startIndex = 0;
	private String userID, status, chatID;
	private String streamUrl = "";
	private Runnable runnable;
	private Handler handler;
	private EditTextIranSans mQuestionEditText;
	private TextViewIranSansRegular emptyTextView;
	private ImageView mIconSendBtn;
	private boolean seen = false;
	private String countUnseen;
	private int count = 1;
	private boolean scrollFlag = false;
	private LastMessage lastMessage;

	public static ChatConversationFragment newInstance(String chatId, String userID, int position, String countUnseen) {

		ChatConversationFragment chatConversationFragment = new ChatConversationFragment();

		Bundle args = new Bundle();
		args.putString(Constants.CHAT_ID, chatId);
		args.putString(Constants.USER_ID, userID);
		args.putString(Constants.COUNT_UNSEEN, countUnseen);

		positionSelected = position;
		chatConversationFragment.setArguments(args);

		return chatConversationFragment;
	}

	private void extractDataFromBundle() {
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			chatID = bundle.getString(Constants.CHAT_ID);


//                status = new Select(Chat_Table.status)
//                        .from(Chat.class)
//                        .where(Chat_Table.chat_id.eq(((ChatConversationActivity) this.getActivity()).getSelectedChatID()))
//                        .querySingle().status;
//				countUnseen = bundle.getString(Constants.COUNT_UNSEEN);
//				userID = bundle.getString(Constants.USER_ID);

		}
	}

	public void onBlockStatusChange() {
//        status = new Select(Chat_Table.status)
//                .from(Chat.class)
//                .where(Chat_Table.chat_id.eq(((ChatConversationActivity) this.getActivity()).getSelectedChatID()))
//                .querySingle().status;
		blockMode();
	}

	void findViews() {
		//progressView = (ProgressView) rootView.findViewById(R.id.circular_progress);
		mRecyclerView = (RecyclerView) rootView.findViewById(R.id.chat_main_view_rc);
		mQuestionEditText = (EditTextIranSans) rootView.findViewById(R.id.edit_text_question);

		emptyTextView = (TextViewIranSansRegular) rootView.findViewById(R.id.edit_text_empty);


		mIconSendBtn = (ImageView) rootView.findViewById(R.id.icon_send_btn);
		mSendTxtLay = (RelativeLayout) rootView.findViewById(R.id.lay_send_text);
	}

	private void setListeners() {

		mIconSendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (mQuestionEditText.getText().toString().equals("")) {

				} else {

					/////////////mitra/////
//                        displayNewMessage(((ChatConversationActivity) mContext).getSelectedChatID(),
//                                mQuestionEditText.getText().toString());
//                        //////////////////////
//
//                        apiRequest.sendMessage(
//                                new SendMessageInput(
//                                        ((ChatConversationActivity) mContext).getSelectedChatID(),
//                                        mQuestionEditText.getText().toString()
//                                )
//                        );
				}

				mQuestionEditText.setText("");

			}
		});

	}

	private void resetUI() {
		mQuestionEditText.setVisibility(View.VISIBLE);
	}

	void init() {
		extractDataFromBundle();
		seen = false;
		mContext = this.getActivity();
		apiRequest = new ApiRequest(mContext, this);

		ViewGroup.MarginLayoutParams marginLayoutParams =
				(ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
		marginLayoutParams.setMargins(0, 0, 0, 0);
		mRecyclerView.setLayoutParams(marginLayoutParams);

		LinearLayoutManager layoutManager = new LinearLayoutManager(AppController.getAppContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		layoutManager.setStackFromEnd(true);
		layoutManager.setReverseLayout(true);

		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				count++;
				callApiGetChatConversation(count);
//                scrolFlag = false;

			}
		});

		callApiGetChatConversation(count);
//        scrolFlag = true;

//		if (((ChatConversationActivity) this.getActivity()).getSelectedChatID() == null
//				|| ((ChatConversationActivity) this.getActivity()).getSelectedChatID().length() == 0) {
//			mSendTxtLay.setVisibility(View.GONE);
//			emptyTextView.setVisibility(View.VISIBLE);
//		} else {
			emptyTextView.setVisibility(View.GONE);
			chatConversationAdapter = new ChatConversationAdapter(
					this, mContext, chatConversationArrayList, "", countUnseen
			);

			mRecyclerView.setAdapter(chatConversationAdapter);

			blockMode();
//		}
	}

	public void showNewMessage() {

//		if (getActivity() != null) {
//			lastMessage = SQLite.select().from(Chat.class).where(Chat_Table.chat_id.is(
//					((ChatConversationActivity) this.getActivity()).getSelectedChatID()))
//					.querySingle().last_message;
//////////////////mitra
//		}

		chatConversationArrayList = chatConversationAdapter.updateIdNullMessageByText(chatConversationArrayList, lastMessage);

		chatConversationAdapter.notifyDataSetChanged();

		chatConversationAdapter.seeAllMessages();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (chatConversationAdapter.getItemCount() > 0) {
					mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
				}
			}
		}, 100);

		if (!AppController.getStoredString(Constants.USER_ID).equals(lastMessage.sender_id)) {
			seeConversation();
		}
	}


	private void blockMode() {

		if (status != null && status.equals("block")) {
			mSendTxtLay.setVisibility(View.GONE);
		} else {
			mSendTxtLay.setVisibility(View.VISIBLE);
		}
	}

	public void callApiGetChatConversation(int page) {

//		apiRequest.getChatConversation(new ChatConversationInput
//				(((ChatConversationActivity) this.getActivity()).getSelectedChatID(),
//						String.valueOf((page - 1) * Constants.limit + Constants.startIndex),
//						String.valueOf((page - 1) * Constants.limit + Constants.limit)));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_main_chat_view, container, false);

		findViews();
		if (savedInstanceState != null) {
			userID = savedInstanceState.getString("userID");
			chatID = savedInstanceState.getString("chatID");
		}

		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		setListeners();
	}

	@Override
	public void onResponse(Call call, Response response) {
//		if (response.body() instanceof ChatConversationOutput) {
//			if (!seen && ((ChatConversationActivity) mContext).getSelectedChatID().length() > 0) {
//				seeConversation();
//			}
//			onChatMainViewResponse(response);
//
//		} else if (response.body() instanceof StatusOutput) {
//			switch (((StatusOutput) response.body()).tag) {
//				case Constants.SEND_MESSAGE:
//					Toasti.showS(mContext, "پیام ارسال شد");
//					chatConversationAdapter.seeAllMessages();
//
//					break;
//				case Constants.SEE_CONVERSATION:
//					Toasti.showS(mContext, "پیام مشاهده شد");
//					break;
//			}
//		}
	}

	private void seeConversation() {

//        apiRequest.seeConversation(new SeeConversationInput(((ChatConversationActivity) mContext).getSelectedChatID()));
//
//        ((ChatConversationActivity) mContext).onSeeConversation();

		seen = true;

	}

	private void onChatMainViewResponse(Response response) {
//        ChatConversationOutput chatConversationOutput = (ChatConversationOutput) response.body();
//        switch (chatConversationOutput.status) {
//            case Status.DONE:
//                for (int i = 0; i < chatConversationOutput.result.list.size(); i++) {
//                    chatConversationArrayList.add(chatConversationOutput.result.list.get(i));
//                }
//
//                chatConversationAdapter.notifyDataSetChanged();
//
//                if (scrollFlag == false) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (chatConversationAdapter.getItemCount() > 0) {
//                                mRecyclerView.getLayoutManager().smoothScrollToPosition(mRecyclerView, null, 0);
//                                scrollFlag = true;
//                            }
//                        }
//                    }, 100);
//
//
//                } else {
//
//                }
//                break;
//
//        }
	}

	@Override
	public void onFailure(Call call, Throwable t) {
		chatConversationAdapter.notifyDataSetChanged();
	}

	public void updateChatView(String userID, String status, String countunseen) {

			emptyTextView.setVisibility(View.GONE);

//			if (status == null) {
//				((ChatConversationActivity) mContext).getSelectedChatID().equals(Constants.UNBLOCK);
//			} else {
//				this.status = status;
//			}
			seen = false;
			startIndex = 0;
			this.userID = userID;
			chatConversationArrayList.clear();
//			chatConversationAdapter = new ChatConversationAdapter(this, mContext, chatConversationArrayList, ((ChatConversationActivity) this.getActivity()).getSelectedChatID(), countunseen);
			mRecyclerView.setAdapter(chatConversationAdapter);
			apiRequest = new ApiRequest(mContext, this);
			callApiGetChatConversation(count);
			blockMode();
	}

	public void handleNotification() {
		if (chatConversationAdapter != null)
			chatConversationAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putString("userID", userID);
		savedInstanceState.putString("chatID", chatID);

	}


	@Override
	public void onPause() {
		super.onPause();
		if (handler != null) {
			if (runnable != null) {
				handler.removeCallbacks(runnable);
			}
			handler.removeMessages(RUNNING);
		}
	}


	//////////mitra
	public void displayNewMessage(String chatID, String messageText) {
		LastMessage newMessage = new LastMessage();

		///////sender_id
		newMessage.sender_id = AppController.getStoredString(Constants.USER_ID);
		//newMessage.sender_id="9";
		////////////text
		newMessage.text = messageText;


		/////time
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date currentDate = Calendar.getInstance().getTime();

//        Time time = new Time();
//        time.en_time = format.format(currentDate);
		//////todo message type=voice or text

		String jDate = JalaliCalendar.getJalaliDate(currentDate);

//        time.fa_time = jDate;
//        time.day_of_week = format.format(currentDate);

		newMessage.time = jDate;


//
		chatConversationArrayList.add(0, newMessage);
//        ArrayList<LastMessage>tempList=chatConversationAdapter.addChatList(newMessage);

		chatConversationAdapter.notifyDataSetChanged();

	}
}