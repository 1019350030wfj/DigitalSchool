package com.onesoft.jaydenim.ui;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.onesoft.jaydenim.EMCallBack;
import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.imaction.ReceiveAction;
import com.onesoft.jaydenim.utils.FileUtils;

import java.io.File;

public class EaseShowNormalFileActivity extends EaseBaseActivity {
    private ProgressBar progressBar;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ease_activity_show_file);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final EMMessage messageBody = (EMMessage) getIntent().getSerializableExtra("msgbody");
        file = new File(messageBody.getLocalUrl());

        //download file
        ReceiveAction.getInstance().setMsgType(messageBody,
                        new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        FileUtils.openFile(file, EaseShowNormalFileActivity.this);
                                        finish();
                                    }
                                });
                            }

                            @Override
                            public void onProgress(final int progress, String status) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        progressBar.setProgress(progress);
                                    }
                                });
                            }

                            @Override
                            public void onError(int error, final String msg) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (file != null && file.exists() && file.isFile())
                                            file.delete();
                                        String str4 = getResources().getString(R.string.Failed_to_download_file);
                                        Toast.makeText(EaseShowNormalFileActivity.this, str4 + msg, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReceiveAction.getInstance().setEmCallBackNull();
    }
}
