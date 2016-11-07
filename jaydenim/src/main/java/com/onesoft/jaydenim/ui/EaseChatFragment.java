package com.onesoft.jaydenim.ui;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.EaseConstant;
import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.domain.EMConversation;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.domain.EaseEmojicon;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.imaction.IMessageListener;
import com.onesoft.jaydenim.imaction.ReceiveAction;
import com.onesoft.jaydenim.utils.EaseCommonUtils;
import com.onesoft.jaydenim.utils.EaseUserUtils;
import com.onesoft.jaydenim.utils.PathUtil;
import com.onesoft.jaydenim.utils.ThreadUtils;
import com.onesoft.jaydenim.widget.EaseAlertDialog;
import com.onesoft.jaydenim.widget.EaseChatExtendMenu;
import com.onesoft.jaydenim.widget.EaseChatInputMenu;
import com.onesoft.jaydenim.widget.EaseChatInputMenu.ChatInputMenuListener;
import com.onesoft.jaydenim.widget.EaseChatMessageList;
import com.onesoft.jaydenim.widget.EaseVoiceRecorderView;
import com.onesoft.jaydenim.widget.EaseVoiceRecorderView.EaseVoiceRecorderCallback;
import com.onesoft.jaydenim.widget.chatrow.EaseCustomChatRowProvider;

import java.io.File;
import java.util.List;

/**
 * Created by Jayden on 2016/9/20.
 */
public class EaseChatFragment extends EaseBaseFragment {
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;

    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;

    protected Bundle fragmentArgs;
    protected int chatType;
    protected File cameraFile;
    protected String toChatUsername;
    protected String toChatUsernameNick;
    protected EaseChatMessageList messageList;
    protected EaseChatInputMenu inputMenu;
    protected EMMessage contextMenuMessage;
    protected int pagesize = 10;
    protected boolean isloading;
    protected boolean haveMoreData = true;
    protected EMConversation conversation;

    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;
    protected EaseVoiceRecorderView voiceRecorderView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected ListView listView;
    protected MyItemClickListener extendMenuItemClickListener;
    private boolean isMessageListInited;

    protected int[] itemStrings = {R.string.attach_take_pic, R.string.attach_picture};
    protected int[] itemdrawables = {R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE};

    /**
     * handle the click event for extend menu
     */
    class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

        @Override
        public void onClick(int itemId, View view) {
            if (chatFragmentHelper != null) {
                if (chatFragmentHelper.onExtendMenuItemClick(itemId, view)) {
                    return;
                }
            }
            switch (itemId) {
                case ITEM_TAKE_PICTURE:
                    selectPicFromCamera();
                    break;
                case ITEM_PICTURE:
                    selectPicFromLocal();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * select local image
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * capture new image
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }

    protected void setListItemClickListener() {
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarClick(username);
                }
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }

            @Override
            public void onResendClick(final EMMessage message) {
                new EaseAlertDialog(getActivity(), R.string.resend, R.string.confirm_resend, null,
                        new EaseAlertDialog.AlertDialogUser() {
                            @Override
                            public void onResult(boolean confirmed, Bundle bundle) {
                                if (!confirmed) {
                                    return;
                                }
                                resendMessage(message);
                            }
                        }, true).show();
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                contextMenuMessage = message;
                if (chatFragmentHelper != null) {
                    chatFragmentHelper.onMessageBubbleLongClick(message);
                }
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                if (chatFragmentHelper == null) {
                    return false;
                }
                return chatFragmentHelper.onMessageBubbleClick(message);
            }

        });
    }

    public void resendMessage(EMMessage message) {
        message.setMsgStatus(EMMessage.Status.CREATE);
        messageList.refresh();
    }

    protected void onMessageListInit() {
        messageList.init(toChatUsername, chatType, chatFragmentHelper != null ?
                chatFragmentHelper.onSetCustomChatRowProvider() : null);
        setListItemClickListener();

        messageList.getListView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });

        isMessageListInited = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        fragmentArgs = getArguments();
        // check if single chat or group chat
        chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        // userId you are chat with or group id
        toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);
        toChatUsernameNick = fragmentArgs.getString(EaseConstant.EXTRA_USER_NAME);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUi();
    }

    /**
     * init view
     */
    protected void initView() {
        // hold to record voice
        //noinspection ConstantConditions
        voiceRecorderView = (EaseVoiceRecorderView) getView().findViewById(R.id.voice_recorder);

        // message list layout
        messageList = (EaseChatMessageList) getView().findViewById(R.id.message_list);
        if (chatType != EaseConstant.CHATTYPE_SINGLE)
            messageList.setShowUserNick(true);
        listView = messageList.getListView();

        extendMenuItemClickListener = new MyItemClickListener();
        inputMenu = (EaseChatInputMenu) getView().findViewById(R.id.input_menu);
        registerExtendMenuItem();
        // init input menu
        inputMenu.init(null);
        inputMenu.setChatInputMenuListener(new ChatInputMenuListener() {

            @Override
            public void onSendMessage(String content) {
                sendTextMessage(content);
            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderCallback() {

                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
                    }
                });
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {
                sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
            }
        });

        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }
        if (chatFragmentHelper != null) {
            //set extension
            chatFragmentHelper.onSetMessageAttributes(message);
        }
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.setChatType(EMMessage.ChatType.GroupChat);
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.setChatType(EMMessage.ChatType.ChatRoom);
        }
        //send message
        EMClient.getInstance().chatManager().newMessage(message,true);
        //refresh ui
        refreshUi();
    }

    protected void sendBigExpressionMessage(String name, String identityCode) {
//        EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
//        sendMessage(message);
    }

    protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
        if (isYourFriend()) {
            EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
            sendMessage(message);
        }
    }

    protected void sendVoiceMessage(String filePath, int length) {
        if (isYourFriend()) {
            EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername, toChatUsernameNick);
            sendMessage(message);
        }
    }

    //send message
    protected void sendTextMessage(String content) {
        if (isYourFriend()) {
            EMMessage message = EMMessage.createTextSendMessage(content, toChatUsername, toChatUsernameNick);
            sendMessage(message);
        }
    }

    protected void sendFileMessage(String filePath) {
        if (isYourFriend()) {
            EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername, toChatUsernameNick);
            sendMessage(message);
        }
    }

    @Override
    protected void setUpView() {
        titleBar.setTitle(toChatUsernameNick);
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            // set title
            if (EaseUserUtils.getUserInfo(toChatUsername) != null) {
                EaseUser user = EaseUserUtils.getUserInfo(toChatUsername);
                if (user != null) {
                    titleBar.setTitle(user.getNick());
                }
            }
            titleBar.setRightImageResource(R.drawable.user_profile);
            titleBar.getRightLayout().setVisibility(View.VISIBLE);
        }

        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
            onConversationInit();
            onMessageListInit();
        }

        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    turnToPersonActivity(toChatUsername);
                }
            }
        });

        addReceiveMessageListener();
        setRefreshLayoutListener();
    }

    public void turnToPersonActivity(String username) {
    }

    private void addReceiveMessageListener() {
        ReceiveAction.getInstance().addMessageListener(ReceiveAction.CHAT_PAGE_CHAT, new IMessageListener() {
            @Override
            public void onMessageCallBack(Object object) {
                //refresh ui
                refreshUi();
            }
        });
    }

    public void refreshUi() {
        if (conversation == null) {
            conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername);
        }
        if (isMessageListInited && conversation != null) {
            messageList.refreshSelectLast();
        }
    }

    protected void setRefreshLayoutListener() {
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData && conversation != null) {
                    ThreadUtils.singleService.execute(new Runnable() {
                        @Override
                        public void run() {
                            List<EMMessage> messages;
                            try {
                                mTo -= (pagesize - 1);
                                messages = conversation.loadMoreMsgFromDB(toChatUsername, mFrom,
                                        mTo);
                            } catch (Exception e1) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                });
                                return;
                            }
                            if (messages.size() > 0) {
                                messageList.refreshSeekTo(messages.size() - 1);
                                if (messages.size() != pagesize) {
                                    haveMoreData = false;
                                }
                            } else {
                                haveMoreData = false;
                            }

                            isloading = false;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
                            Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 600);
    }

    private int mFrom;//分页开始的索引
    private int mTo;//分页到哪条数据的索引
    private int mMsgCount;//数据总条数

    protected void onConversationInit() {
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername);
        if (conversation != null) {
            conversation.markAllMessagesAsRead();
            // the number of messages loaded into conversation is getChatOptions().getNumberOfMessagesLoaded
            // you can change this number
            final List<EMMessage> msgs = conversation.getAllMessages();
            mMsgCount = msgs != null ? msgs.size() : 0;
            if (mMsgCount > 0) {
                mFrom = mMsgCount - 1;
                mTo = mMsgCount - pagesize - 1 >= 0 ? (mMsgCount - pagesize - 1) : 0;
                pagesize = 20;
                conversation.loadMoreMsgFromDB(toChatUsername, mFrom, mTo);
            }
        }
    }

    public void onBackPressed() {
        if (inputMenu.onBackPressed()) {
            getActivity().finish();
        }
    }

    /**
     * register extend menu, item id need > 3 if you override this method and keep exist item
     */
    protected void registerExtendMenuItem() {
        for (int i = 0; i < itemStrings.length; i++) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
        }
    }

    protected EaseChatFragmentHelper chatFragmentHelper;

    public void setChatFragmentListener(EaseChatFragmentHelper chatFragmentHelper) {
        this.chatFragmentHelper = chatFragmentHelper;
    }

    public interface EaseChatFragmentHelper {
        /**
         * set message attribute
         */
        void onSetMessageAttributes(EMMessage message);

        /**
         * enter to chat detail
         */
        void onEnterToChatDetails();

        /**
         * on avatar clicked
         *
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * on avatar long pressed
         *
         * @param username
         */
        void onAvatarLongClick(String username);

        /**
         * on message bubble clicked
         */
        boolean onMessageBubbleClick(EMMessage message);

        /**
         * on message bubble long pressed
         */
        void onMessageBubbleLongClick(EMMessage message);

        /**
         * on extend menu item clicked, return true if you want to override
         *
         * @param view
         * @param itemId
         * @return
         */
        boolean onExtendMenuItemClick(int itemId, View view);

        /**
         * on set custom chat row provider
         *
         * @return
         */
        EaseCustomChatRowProvider onSetCustomChatRowProvider();
    }

    protected void sendImageMessage(String imagePath) {
        if (isYourFriend()) {
            EMMessage message = EMMessage.createImageSendMessage(imagePath, toChatUsername, toChatUsernameNick);
            sendMessage(message);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile.exists())
                    sendImageMessage(cameraFile.getAbsolutePath());
            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            }
        }
    }

    /**
     * send image
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendImageMessage(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendImageMessage(file.getAbsolutePath());
        }

    }

    /**
     * send file
     *
     * @param filePath
     */
    protected void sendFileByUri(String filePath) {
//    protected void sendFileByUri(Uri uri) {
//        String filePath = SelectFileKitKatUtil.getPath(getActivity(), uri);
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] filePathColumn = { MediaStore.Files.FileColumns.DATA };
//            Cursor cursor = null;
//            try {
//                cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow("_data");
//                if (cursor.moveToFirst()) {
//                    filePath = cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            filePath = uri.getPath();
//        }
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(getActivity(), R.string.File_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        //limit the size < 10M
        if (file.length() > 16 * 1024 * 1024) {
            Toast.makeText(getActivity(), R.string.The_file_is_not_greater_than_10_m, Toast.LENGTH_SHORT).show();
            return;
        }
        sendFileMessage(filePath);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ReceiveAction.getInstance().removeMessageListener(ReceiveAction.CHAT_PAGE_CHAT);
    }

    public boolean isYourFriend() {//判断对方是否为你的好友，因为删除好友后，不能和对方聊天了
        if( EMClient.getInstance().getContactList().containsKey(toChatUsername)){
            return true;
        } else {
            Toast.makeText(getActivity(),"对方上不是你的好友，请先添加！",Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
