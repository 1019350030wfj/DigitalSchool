package com.onesoft.digitaledu.model;

import java.util.List;

/**
 * Created by Jayden on 2016/9/7.
 */

public class LoginBean {

    public String statue;
    public List<InfoBean> info;
    public String msg;

    public static class InfoBean {
        public String photo;
        public String user_id;
        public String mapped_id;
        public String user_name;
        public String real_name;
        public String user_type;
        public String user_role;
        public String sex;
        public String province;
        public String mobilephone;
        public String course_table;
        public long course_size;
    }
}
