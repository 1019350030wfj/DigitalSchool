package com.onesoft.digitaledu.model;

import java.io.Serializable;

/**
 * Created by Jayden on 2016/12/1.
 */

public class PersonInfo implements Serializable{
    public String photo;
    public String user_id;
    public String mapped_id;
    public String user_name;//学号
    public String real_name;//昵称
    public String user_type;//1、教师 2、学生
    public String jobtitle;//职称
    public String sex;//1是男 0是女
    public String province;
    public String depart_name;
    public String nationid;
    public String bloodtype;
    public String birthday;
    public String idcard;
    public String mobilephone;
    public String address;
    public String email;
    public String motto;
    public String habby;
    public String specialty;
    public String nationid_name;


    //学生
    public String class_name;
    public String subject_name;
    public String koseki;
}
