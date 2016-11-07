package com.onesoft.jaydenim.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.utils.LogUtil;

import java.io.IOException;


public class EaseChatRowVoice extends EaseChatRowFile {

    private ImageView voiceImageView;
    private TextView voiceLengthView;
    private ImageView readStatusView;

    public EaseChatRowVoice(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.RECEIVE ?
                R.layout.ease_row_received_voice : R.layout.ease_row_sent_voice, this);
    }

    @Override
    protected void onFindViewById() {
        voiceImageView = ((ImageView) findViewById(R.id.iv_voice));
        voiceLengthView = (TextView) findViewById(R.id.tv_length);
        readStatusView = (ImageView) findViewById(R.id.iv_unread_voice);
    }

    @Override
    protected void onSetUpView() {
        int len = message.getLength();
        LogUtil.e("len = " + len);
        if (len > 0) {
            if (len > 100){
                len = 3;
            }
            voiceLengthView.setText(len + "\"");
            voiceLengthView.setVisibility(View.VISIBLE);
        } else {
//            final MediaPlayer player = new MediaPlayer();
//            try {
//                player.setDataSource(mFilePath);
//                player.prepare();
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (SecurityException e) {
//                e.printStackTrace();
//            } catch (IllegalStateException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    int size = player.getDuration();
//                    String timelong = size / 1000 + "s";
//                    mTextViewLong.setText(timelong);
//                }
//            });
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(message.getLocalUrl());
                mediaPlayer.prepare();
                LogUtil.e("mediaPlayer.getDuration() = " + mediaPlayer.getDuration());
                int duration = mediaPlayer.getDuration();
                int voiceLength = duration % 1000 == 0 ? duration/1000 : duration/1000 + 1;
                message.setLength(voiceLength);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            int tempLength = message.getLength();
            LogUtil.e("tempLength = " + tempLength);
            if (tempLength > 0) {
                if (tempLength > 100){
                    tempLength = 3;
                }
                voiceLengthView.setText(tempLength + "\"");
                voiceLengthView.setVisibility(View.VISIBLE);
            } else {
                voiceLengthView.setVisibility(View.INVISIBLE);
            }
        }
        if (EaseChatRowVoicePlayClickListener.playMsgId != null
                && EaseChatRowVoicePlayClickListener.playMsgId.equals(message.getMsgId()) && EaseChatRowVoicePlayClickListener.isPlaying) {
            AnimationDrawable voiceAnimation;
            if (message.direct() == EMMessage.RECEIVE) {
                voiceImageView.setImageResource(R.anim.voice_from_icon);
            } else {
                voiceImageView.setImageResource(R.anim.voice_to_icon);
            }
            voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
            voiceAnimation.start();
        } else {
            if (message.direct() == EMMessage.RECEIVE) {
                voiceImageView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
            } else {
                voiceImageView.setImageResource(R.drawable.ease_chatto_voice_playing);
            }
        }

        if (message.direct() == EMMessage.RECEIVE) {
//            if (message.isListened()) {
//                // hide the unread icon
//                readStatusView.setVisibility(View.INVISIBLE);
//            } else {
//                readStatusView.setVisibility(View.VISIBLE);
//            }
            LogUtil.d(TAG, "it is receive msg");
//            if (message.downloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
//                    message.downloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
//                progressBar.setVisibility(View.VISIBLE);
//                setMessageReceiveCallback();
//            } else {
//                progressBar.setVisibility(View.INVISIBLE);
//
//            }
            return;
        }

        // until here, handle sending voice message
        //handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        new EaseChatRowVoicePlayClickListener(message, voiceImageView, readStatusView, adapter, activity).onClick(bubbleLayout);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EaseChatRowVoicePlayClickListener.currentPlayListener != null && EaseChatRowVoicePlayClickListener.isPlaying) {
            EaseChatRowVoicePlayClickListener.currentPlayListener.stopPlayVoice();
        }
    }

}
