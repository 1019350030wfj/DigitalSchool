package com.onesoft.jaydenim.widget.chatrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onesoft.jaydenim.EMCallBack;
import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.ui.EaseShowNormalFileActivity;
import com.onesoft.jaydenim.utils.FileUtils;
import com.onesoft.jaydenim.utils.TextFormater;

import java.io.File;

public class EaseChatRowFile extends EaseChatRow{

    protected TextView fileNameView;
	protected TextView fileSizeView;
    protected TextView fileStateView;
    
    protected EMCallBack sendfileCallBack;
    
    protected boolean isNotifyProcessed;

    public EaseChatRowFile(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
	}

	@Override
	protected void onInflateView() {
	    inflater.inflate(message.direct() == EMMessage.RECEIVE ?
	            R.layout.ease_row_received_file : R.layout.ease_row_sent_file, this);
	}

	@Override
	protected void onFindViewById() {
	    fileNameView = (TextView) findViewById(R.id.tv_file_name);
        fileSizeView = (TextView) findViewById(R.id.tv_file_size);
        fileStateView = (TextView) findViewById(R.id.tv_file_state);
        percentageView = (TextView) findViewById(R.id.percentage);
	}


	@Override
	protected void onSetUpView() {
//	    fileMessageBody = (EMNormalFileMessageBody) message.getBody();
        String filePath = message.getLocalUrl();
        fileNameView.setText(message.getFileName());
        fileSizeView.setText(TextFormater.getDataSize(message.getFileSize()));
        if (message.direct() == EMMessage.RECEIVE) {
            File file = new File(filePath);
            if (file.exists()) {
                fileStateView.setText(R.string.Have_downloaded);
            } else {
                fileStateView.setText(R.string.Did_not_download);
            }
            return;
        }

        // until here, to sending message
//        handleSendMessage();
	}

	/**
	 * handle sending message
	 */
    protected void handleSendMessage() {
        setMessageSendCallback();
        switch (message.status()) {
        case SUCCESS:
            progressBar.setVisibility(View.INVISIBLE);
            if(percentageView != null)
                percentageView.setVisibility(View.INVISIBLE);
            statusView.setVisibility(View.INVISIBLE);
            break;
        case FAIL:
            progressBar.setVisibility(View.INVISIBLE);
            if(percentageView != null)
                percentageView.setVisibility(View.INVISIBLE);
            statusView.setVisibility(View.VISIBLE);
            break;
        case INPROGRESS:
            progressBar.setVisibility(View.VISIBLE);
            if(percentageView != null){
                percentageView.setVisibility(View.VISIBLE);
//                percentageView.setText(message.progress() + "%");
            }
            statusView.setVisibility(View.INVISIBLE);
            break;
        default:
            progressBar.setVisibility(View.INVISIBLE);
            if(percentageView != null)
                percentageView.setVisibility(View.INVISIBLE);
            statusView.setVisibility(View.VISIBLE);
            break;
        }
    }
	

	@Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        String filePath = message.getLocalUrl();
        File file = new File(filePath);
        if (file.exists()) {
            // open files if it exist
            FileUtils.openFile(file, (Activity) context);
        } else {
            // download the file
            context.startActivity(new Intent(context, EaseShowNormalFileActivity.class).putExtra("msgbody", message));
        }

    }
}
