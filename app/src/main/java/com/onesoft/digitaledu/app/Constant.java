package com.onesoft.digitaledu.app;

/**
 * Created by Jayden on 2016/12/14.
 */

public class Constant {
    public static final int VISIBLE_ITEM_TO_TOP = 5;

    //TODO  LogUtil 的开关记得关

    /*=====================动态编辑页 布局样式=====================*/
    public static final int LAYOUT_STYLE_TEXT = 0;//文字+文字
    public static final int LAYOUT_STYLE_EDIT = 1;//文字+编辑框(请输入)
    public static final int LAYOUT_STYLE_EDIT_LIMIT = 14;//文字+编辑框(输入标题不超过12个字)

    public static final int LAYOUT_STYLE_SELECT = 2;//文字+文字+右边箭头，弹出弹框

    public static final int LAYOUT_STYLE_EDIT_NEED = 4;//文字+编辑框(必填)
    public static final int LAYOUT_STYLE_BIRTHDAY = 5;//出生年月+编辑框(必填)
    public static final int LAYOUT_STYLE_REMARK = 7;//文字+"REMARK

    //文字+"必选"+右边箭头，弹出弹框 用户类型
    public static final int LAYOUT_STYLE_SELECT_NEED_USERTYPE = 3;
    //文字+"必选"+右边箭头，弹出弹框 性别
    public static final int LAYOUT_STYLE_SELECT_NEED_SEX = 6;
    //文字+"必选"+右边箭头，弹出弹框 用户角色
    public static final int LAYOUT_STYLE_SELECT_NEED_USER_ROLE = 8;
    //文字+"必选"+右边箭头，弹出弹框 汉族
    public static final int LAYOUT_STYLE_SELECT_NEED_NATION = 9;
    //文字+"必选"+右边箭头，弹出弹框 成员选择
    public static final int LAYOUT_STYLE_SELECT_MEMBER = 11;
    //文字+"必选"+右边加号，弹出弹框 用户树
    public static final int LAYOUT_STYLE_SELECT_USER_TREE= 13;

    //文字+"必选"+右边箭头，弹出弹框 上级部门
    public static final int LAYOUT_STYLE_SELECT_NEED_PARENT_DEPARTMENT = 10;

    //文字+"必选"+右边箭头，弹出弹框 所属校区
    public static final int LAYOUT_STYLE_SELECT_NEED_DISTRICT = 15;
    //文字+"必选"+右边箭头，弹出弹框 使用类别
    public static final int LAYOUT_STYLE_SELECT_NEED_USE_TYPE = 19;
    //文字+"必选"+右边箭头，弹出弹框 使用分类别
    public static final int LAYOUT_STYLE_SELECT_NEED_CLASSIFY = 16;
    //文字+"必选"+右边箭头，弹出弹框 用地类别
    public static final int LAYOUT_STYLE_SELECT_NEED_LAND_USE = 18;
    //文字+"必选"+右边箭头，弹出弹框 建筑物类别
    public static final int LAYOUT_STYLE_SELECT_NEED_BUILDING = 17;
    //文字+"必选"+右边箭头，弹出弹框 教室类型
    public static final int LAYOUT_STYLE_SELECT_NEED_CLASSROOM = 20;
    //文字+"必选"+右边箭头，弹出弹框 所属部门
    public static final int LAYOUT_STYLE_SELECT_NEED_DEPARTMENT = 22;
    //文字+"必选"+右边箭头，弹出弹框 教室楼层
    public static final int LAYOUT_STYLE_SELECT_NEED_FLOOR = 21;

    public static final int LAYOUT_STYLE_TEXT_DEFAULT = 12;//文字+文字(默认初始是通知，类型是0)
    //文字+"必选"+右边箭头，弹出弹框 上级菜单
    public static final int LAYOUT_STYLE_SELECT_MENU_PARENT = 23;

    //文字+"必选"+右边箭头，弹出弹框 是否显示
    public static final int LAYOUT_STYLE_IS_SHOW = 24;

    //文字+"必选"+右边箭头，弹出弹框 选择文件
    public static final int LAYOUT_STYLE_SELECT_FILE = 25;
    /*=====================动态编辑页 布局样式=====================*/
}
