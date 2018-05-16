package com.onesoft.digitaledu.model.net;

/**
 * Created by Jayden on 2016/11/29.
 */

public class HttpUrl {
    public static final String CODE_SUCCESS = "200";
    public static final String CODE_ERROR = "205";
    public static final int PAGE_SIZE = 10;//limit 每页数目，默认10条

    /*********************************************
     * 登录http://222.47.48.38:8080/new_digitalschool/index.php?r=login/index
     **********************************************/
    public static final String LOGIN_AVATAR_URL = "http://222.47.48.38:8080/new_digitalschool/api/login_photo.php?";
    public static final String LOGIN_URL = "http://222.47.48.38:8080/new_digitalschool/api/login.php?";
    /********************************************* 登录 **********************************************/

    /*********************************************
     * 一级菜单
     **********************************************/
    public static final String TOP_LEVEL_MENU = "http://222.47.48.38:8080/new_digitalschool/api/menu.php?user_role=";
    /*********************************************
     * 二级菜单
     **********************************************/
    public static final String TWO_LEVEL_MENU = "http://222.47.48.38:8080/new_digitalschool/api/secmenu.php?id=";
    /*********************************************
     * 三级菜单
     **********************************************/
    public static final String THIRD_LEVEL_MENU = "http://222.47.48.38:8080/new_digitalschool/api/threemenu.php?id=";
    /*********************************************
     * 个人信息
     **********************************************/
    public static final String PERSON_INFO = "http://222.47.48.38:8080/new_digitalschool/api/personal/person_info.php?user_id=";
    /*********************************************
     * 编辑个人信息
     **********************************************/
    public static final String PERSON_INFO_EDIT = "http://222.47.48.38:8080/new_digitalschool/api/personal/edit_person_info.php?user_id=";
    /*********************************************
     * 下载当前学期课程
     **********************************************/
    public static final String CURRENT_PERIOD = "http://222.47.48.38:8080/new_digitalschool/uploads/current_semester_coursetable.xls";
    /*********************************************
     *头像上传
     **********************************************/
    public static final String CHANGE_AVATAR = "http://222.47.48.38:8080/new_digitalschool/api/personal/edit_photo.php?user_id=";
    /*********************************************
     * 密码修改
     **********************************************/
    public static final String MODIFY_PWD = "http://222.47.48.38:8080/new_digitalschool/api/personal/update_pwd.php";

    /*********************************************
     * 收件箱列表 mapped_id 登录用户
     user_type 登录用户的类型
     （limit 每页数目，默认10条）
     page 页数

     **********************************************/
    public static final String INBOX_LIST = "http://222.47.48.38:8080/new_digitalschool/api/message/inbox_list.php?";
    /*********************************************
     * 发件箱列表 mapped_id 登录用户
     user_type 登录用户的类型
     （limit 每页数目，默认10条）
     page 页数

     **********************************************/
    public static final String SENDINGBOX_LIST = "http://222.47.48.38:8080/new_digitalschool/api/message/sendbox_list.php?";
    /********************************************* 2016/diary
     * 发送消息
     **********************************************/
    public static final String SEND_MESSAGE = "http://222.47.48.38:8080/new_digitalschool/api/message/send_message.php";
    /*********************************************
     * 发送消息  群组管理 群组发送
     **********************************************/
    public static final String SEND_MESSAGE_GROUP = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/group_sendmsg.php";
    /*********************************************
     * 发送消息  系统通讯录，群发
     **********************************************/
    public static final String SEND_MESSAGE_DEPART = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/send_message_depart.php";
    /*********************************************
     * 发送公文
     **********************************************/
    public static final String SEND_DOCUMENT = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/send_gw.php";

    /*********************************************
     * 收件箱详情 ?id=5&mapped_id=2&user_type=1
     **********************************************/
    public static final String INBOX_DETAIL = "http://222.47.48.38:8080/new_digitalschool/api/message/inbox_detail.php?";

    /*********************************************
     * 发件箱详情 id=2
     **********************************************/
    public static final String SENDBOX_DETAIL = "http://222.47.48.38:8080/new_digitalschool/api/message/sendbox_detail.php?id=";

    /*********************************************
     * 公文 发件箱详情 id=2
     **********************************************/
    public static final String SENDBOX_DETAIL_DOC = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/gwsendbox_detail.php?id=";

    /*********************************************
     * 公文 收件箱删除 id=2
     **********************************************/
    public static final String  INBOX_DELELTE_DOC= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/gwinbox_delete.php?id=";
    /*********************************************
     * 发件箱删除 id=2
     **********************************************/
    public static final String SENDBOX_DELELTE = "http://222.47.48.38:8080/new_digitalschool/api/message/sendbox_delete.php?id=";
    /*********************************************
     * 收件箱删除 id=2
     **********************************************/
    public static final String INBOX_DELELTE = "http://222.47.48.38:8080/new_digitalschool/api/message/inbox_delete.php?id=";

    /*********************************************
     * 公文 收件箱详情 cid=13&user_id=1
     **********************************************/
    public static final String INBOX_DETAIL_DOC = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/gwinbox_detail.php?";

    /*********************************************
     * 公文 发件箱删除 id=2
     **********************************************/
    public static final String SENDBOX_DELELTE_DOC = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/gwsendbox_delete.php?id=";
    /*********************************************
     * 收件箱回复 id=2
     **********************************************/
    public static final String REPLY_MESSAGE = "http://222.47.48.38:8080/new_digitalschool/api/message/reply_message.php";
    /*********************************************2016/diary
     * 三级菜单，要进入详情页的url 操作按钮列表\操作按钮搜索\操作按钮编辑\操作按钮详情
     **********************************************/
    public static final String THREE_MENU_TO_DETAIL = "http://222.47.48.38:8080/new_digitalschool/api/main.php?id=";
    /*********************************************2016/diary
     * 操作按钮编辑 按钮选择弹框数据
     **********************************************/
    public static final String SELECT_BTN = "http://222.47.48.38:8080/new_digitalschool/api/function/btn_select.php";
    /*********************************************2016/diary
     * 选择教师列表
     **********************************************/
    public static final String SELECT_TEACHER = "http://222.47.48.38:8080/new_digitalschool/api/function/select_teacher.php";
    /*********************************************2016/diary
     * 选择下属列表
     **********************************************/
    public static final String SELECT_XIASHU = "http://222.47.48.38:8080/new_digitalschool/api/gonggongjichu/quanxianguanli/select_xiashu.php?type=";
    /*********************************************2016/diary
     * 选择用户树
     **********************************************/
    public static final String CHOOSE_USER_TREE = "http://222.47.48.38:8080/new_digitalschool/api/function/choose_usertree.php";
    /*********************************************2016/diary
     * 选择用户树 这个用户是只有教师
     **********************************************/
    public static final String CHOOSE_TEACHER_TREE = "http://222.47.48.38:8080/new_digitalschool/api/function/choose_teachertree.php";
    /*********************************************2016/diary
     * 选择角色
     **********************************************/
    public static final String CHOOSE_USER_ROLE = "http://222.47.48.38:8080/new_digitalschool/api/function/user_role.php";
    /*********************************************2016/diary
     * 选择民族
     **********************************************/
    public static final String SELECT_NATION = "http://222.47.48.38:8080/new_digitalschool/api/function/select_nation.php";
    /*********************************************2016/diary
     * 上一级部门
     **********************************************/
    public static final String SELECT_TREE = "http://222.47.48.38:8080/new_digitalschool/api/gonggongjichu/bumenguanli/select_tree.php";
    /*********************************************2016/diary
     * 上一级菜单
     **********************************************/
    public static final String SELECT_TREE_MENU = "http://222.47.48.38:8080/new_digitalschool/api/gonggongjichu/caidanguanli/menu_tree.php";
    /*********************************************2016/diary
     * 成员选择
     **********************************************/
    public static final String SELECT_MEMBER = "http://222.47.48.38:8080/new_digitalschool/api/function/select_teacher.php?page=1";
    /*********************************************2016/diary
     * 所属校区
     **********************************************/
    public static final String SELECT_DISTRICT= "http://222.47.48.38:8080/new_digitalschool/api/function/select_district.php";
    /*********************************************2016/diary
     * 教室类型
     **********************************************/
    public static final String SELECT_CLASSROOM_TYPE= "http://222.47.48.38:8080/new_digitalschool/api/function/select_classroom_type.php";
    /*********************************************2016/diary
     * 调用查询办公楼部门编号
     **********************************************/
    public static final String SELECT_OFFICE_DEPART= "http://222.47.48.38:8080/new_digitalschool/api/function/select_office_depart.php";
    /*********************************************2016/diary
     * 自定义数据备份
     **********************************************/
    public static final String DATA_BACKUP= "http://222.47.48.38:8080/new_digitalschool/api/gonggongjichu/databackup/zidingyibeifen.php";
    /*********************************************2016/diary
     * 自定义数据备份
     **********************************************/
    public static final String DATA_BACKUP_POST= "http://222.47.48.38:8080/new_digitalschool/api/gonggongjichu/databackup/zidingyibeifen_sub.php";
    /*********************************************2016/diary
     * 系统通讯录
     **********************************************/
    public static final String SYSTEM_CONTACT= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/txl.php?type=1&user_id=";
    /*********************************************2016/diary
     * 个人通讯录
     **********************************************/
    public static final String PERSON_CONTACT= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/txl.php?type=2&user_id=";
    /*********************************************2016/diary
     * 个人通讯录搜索
     **********************************************/
    public static final String PERSON_CONTACT_SEARCH= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/person_txl_search.php?user_id=";
    /*********************************************2016/diary
     * 群组管理
     **********************************************/
    public static final String GROUPCONTACT= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/txl.php?type=3&user_id=";
    /*********************************************2016/diary
     * 群组管理  添加群组用户
     **********************************************/
    public static final String GROUP_ADD_USER= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/groupuser_add.php?group_id=";
    /*********************************************2016/diary
     * 系统通讯录  个人信息
     **********************************************/
    public static final String SYSTEM_CONTACT_INFO= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/xitong_txl_detail.php?cid=";
    /*********************************************2016/diary
     * 个人通讯录  添加
     **********************************************/
    public static final String PERSON_CONTACT_ADD= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/person_txl_add.php";
    /*********************************************2016/diary
     * 个人通讯录  删除
     **********************************************/
    public static final String PERSON_CONTACT_DELETE= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/person_txl_delete.php?cid=";
    /*********************************************2016/diary
     * 群组通讯录  删除
     **********************************************/
    public static final String GROUP_CONTACT_DELETE= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/groupuser_del.php?group_id=";
    /*********************************************2016/diary
     * 群组管理  添加群组
     **********************************************/
    public static final String GROUP_ADD= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/group_add.php";
    /*********************************************2016/diary
     * 群组管理  群组删除
     **********************************************/
    public static final String GROUP_DELETE= "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/group_delete.php?id=";

    /*********************************************
     * 编辑个人通讯录信息
     **********************************************/
    public static final String EDIT_CONTACT_PERSON_INFO = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/tongxunlu/person_txl_edit.php?cid=";

    /*********************************************
     *公文发件箱列表
     **********************************************/
    public static final String DOCUMENT_SEND_LIST = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/gwsendbox_list.php?";

    /*********************************************
     *公文收件箱列表
     **********************************************/
    public static final String DOCUMENT_IN_LIST = "http://222.47.48.38:8080/new_digitalschool/api/xinxiguanli/gongwenshoufa/gwinbox_list.php?";

    /*********************************************
     *意见反馈列表
     **********************************************/
    public static final String FEEDBACK_LIST = "http://222.47.48.38:8080/new_digitalschool/api/personal/feedback/feedback_select.php?page=";

    /*********************************************
     *意见反馈列表 删除
     **********************************************/
    public static final String FEEDBACK_DELETE = "http://222.47.48.38:8080/new_digitalschool/api/personal/feedback/feedback_delete.php?user_id=";

    /*********************************************
     *意见反馈列表 添加
     **********************************************/
    public static final String FEEDBACK_ADD = "http://222.47.48.38:8080/new_digitalschool/api/personal/feedback/feedback_add.php";

}
