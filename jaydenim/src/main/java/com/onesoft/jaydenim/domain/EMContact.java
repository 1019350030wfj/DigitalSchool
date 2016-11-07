package com.onesoft.jaydenim.domain;

/**
 * 联系人信息
 * Created by Jayden on 2016/9/19.
 */

public class EMContact{

    public String user_id;//自己的是userID，好友的是id
    public String user_type;//userType
    public String user_name;//name
    public String real_name;//username
    public String photo;
    public String sex;//sex
    public String province;//prov
    public String city;//city
    public String mobilephone;//mobilephone

    protected EMContact() {
    }

    public EMContact(String var1) {
        this.user_id = var1;
    }

    public String getUsername() {
        return this.user_id;
    }

    public void setNick(String var1) {
        this.real_name = var1;
    }

    public String getNick() {
        return this.getNickname();
    }

    public String getNickname() {
        return this.real_name == null?this.getUsername():this.real_name;
    }
}
