package com.onesoft.digitaledu.view.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.activity.MainActivity;


/**
 * 欢迎界面
 * Created by Jayden on 2016/7/26.
 */
public class WelcomeActivity extends Activity {

    private static final int DELAY_TIME = 1000;
    public static final String PREFERENCE_NAME = "jPreference";
    public static final String IS_LOGIN = "isLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        final boolean isAutoLogin = SPHelper.getIsLogin(this);

        findViewById(R.id.welcome_img).postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!isAutoLogin) {
                    startActivity(new Intent(WelcomeActivity.this,
                            LoginActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this,
                            MainActivity.class));
                }
                finish();
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, DELAY_TIME);
    }
}
