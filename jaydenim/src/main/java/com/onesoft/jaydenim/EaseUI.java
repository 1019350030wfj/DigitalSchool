package com.onesoft.jaydenim;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.domain.EaseEmojicon;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.utils.LogUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EaseUI {

    /**
     * the global EaseUI instance
     */
    private static EaseUI instance;

    private EaseUI() {
        setSettingsProvider(new EaseSettingsProvider() {
            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isSpeakerOpened() {
                return true;
            }
        });
    }

    /**
     * get instance of EaseUI
     *
     * @return
     */
    public static EaseUI getInstance() {
        if (instance == null) {
            synchronized (EaseUI.class) {
                if (instance == null) {
                    instance = new EaseUI();
                }
            }
        }
        return instance;
    }

    /**
     * application context
     */
    private Context appContext = null;

    public Context getContext() {
        return appContext;
    }

    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;

    /**
     * this function will initialize the SDK and easeUI kit
     *
     * @param context
     * @return
     */
    public synchronized boolean init(Context context) {
        if (sdkInited) {
            return true;
        }
        appContext = context;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        LogUtil.d("process app name : " + processAppName);

        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            LogUtil.d("enter the service process!");
            return false;
        }
        sdkInited = true;
        EMClient.getInstance().init(context);
        return true;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

    /**
     * 登录的时候
     * @param user 用户id
     * @param context
     * @param uids 好友id
     */
    public void login(String user, final Context context, String uids) {
        EMClient.getInstance().login(user, uids, context);
    }

    /**
     * user profile provider
     */
    private EaseUserProfileProvider userProvider;

    /**
     * get user profile provider
     *
     * @return
     */
    public EaseUserProfileProvider getUserProfileProvider() {
        return userProvider;
    }

    private EaseSettingsProvider settingsProvider;

    public void setSettingsProvider(EaseSettingsProvider settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    public EaseSettingsProvider getSettingsProvider() {
        return settingsProvider;
    }

    public void startIM(Context activity) {
        EMClient.getInstance().startService(activity);
    }

    /**
     * new message options provider
     */
    public interface EaseSettingsProvider {
        boolean isMsgNotifyAllowed(EMMessage message);

        boolean isMsgSoundAllowed(EMMessage message);

        boolean isMsgVibrateAllowed(EMMessage message);

        boolean isSpeakerOpened();
    }

    /**
     * User profile provider
     *
     * @author wei
     */
    public interface EaseUserProfileProvider {
        /**
         * return EaseUser for input username
         *
         * @param username
         * @return
         */
        EaseUser getUser(String username);
    }

    /**
     * Emojicon provider
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * return EaseEmojicon for input emojiconIdentityCode
         *
         * @param emojiconIdentityCode
         * @return
         */
        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);

        /**
         * get Emojicon map, key is the text of emoji, value is the resource id or local path of emoji icon(can't be URL on internet)
         *
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }

    private EaseEmojiconInfoProvider emojiconInfoProvider;

    /**
     * Emojicon provider
     *
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider() {
        return emojiconInfoProvider;
    }

    /**
     * set Emojicon provider
     *
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider) {
        this.emojiconInfoProvider = emojiconInfoProvider;
    }

    public void logout() {
        EMClient.getInstance().logout();
    }

    public void closeConnection() {
        EMClient.getInstance().closeConnection();
    }

    /*=========================好友相关=======================*/
    /**
     * get contact list
     * 获取好友列表
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        return EMClient.getInstance().contactManager().getContactList();
    }

    public void saveContact(EaseUser user) {
        EMClient.getInstance().contactManager().saveContact(user);
    }

    public void saveContactList(Map<String, EaseUser> map) {
        EMClient.getInstance().contactManager().saveContactList(map);
    }

    public void deleteContact(String frid) {
        EMClient.getInstance().contactManager().deleteContact(frid);
    }
       /*=========================好友相关=======================*/
}
