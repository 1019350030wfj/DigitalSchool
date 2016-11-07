package com.onesoft.jaydenim.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.EaseUI;
import com.onesoft.jaydenim.EaseUI.EaseUserProfileProvider;
import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.domain.EaseUser;


public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     * @param id
     */
    public static void setUserAvatar(Context context, String id, ImageView imageView) {
        if (EMClient.getInstance().contactManager().getContactList()==null ||
                EMClient.getInstance().contactManager().getContactList().size() == 0 ||
                EMClient.getInstance().contactManager().getContactList().get(id) == null){
            return;
        }
        String avatar = EMClient.getInstance().contactManager().getContactList().get(id).getAvatar();
        try {
            Glide.with(context).load(avatar).placeholder(R.drawable.ease_default_avatar).into(imageView);
        } catch (Exception e) {
            //use default avatar
            Glide.with(context).load(avatar).diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.ease_default_avatar)
                    .into(imageView);
        }
    }

}
