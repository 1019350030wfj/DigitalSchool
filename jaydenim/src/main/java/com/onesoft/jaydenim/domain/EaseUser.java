package com.onesoft.jaydenim.domain;

import com.onesoft.jaydenim.utils.EaseCommonUtils;

/**
 * Created by Jayden on 2016/9/19.
 */

public class EaseUser extends EMContact {

    /**
     * initial letter for nickname
     */
    protected String initialLetter;

    public EaseUser(String username){
        this.user_id = username;
    }

    public String getInitialLetter() {
        if(initialLetter == null){
            EaseCommonUtils.setUserInitialLetter(this);
        }
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }

    public String getAvatar() {
        return photo;
    }

    public void setAvatar(String avatar) {
        this.photo = avatar;
    }

    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof EaseUser)) {
            return false;
        }
        return getUsername().equals(((EaseUser) o).getUsername());
    }
}
