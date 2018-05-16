package com.onesoft.digitaledu.presenter.utils;

import android.text.TextUtils;

import com.onesoft.digitaledu.app.Constant;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将服务器传过来的数据，根据模版id转换为对应模版所需要的数据和布局类型
 * Created by Jayden on 2016/12/19.
 */
public class TemplateHandleDataHelper {

    /**
     * 将服务器的数据转换为本地，
     * 添加页或者编辑页需要的数据格式
     *
     * @param mainBean
     * @param topBtn
     * @param jsonFieldName
     * @param object
     */
    public void convertServerDataToClientData(ThirdToMainBean mainBean, TopBtn topBtn, JSONObject jsonFieldName, JSONObject object) throws JSONException {
        String[] viewFieldArray = topBtn.view_field.split(",");
        List<Template10Bean> template10BeanList = new ArrayList<Template10Bean>();
        Map<String, Template10Bean> mClientMap = new HashMap<>();//客户端显示
        Map<String, Template10Bean> mServerMap = new HashMap<>();//服务器要的数据
        for (int fieldI = 0, fieldLength = viewFieldArray.length; fieldI < fieldLength; fieldI++) {
            if (!TextUtils.isEmpty(viewFieldArray[fieldI])) {
                if ("operate_temp".equals(viewFieldArray[fieldI])) {
                    continue;//如果是operate_temp的话，就忽略本次循环
                }
                Template10Bean template10Bean = new Template10Bean();
                template10Bean.key = viewFieldArray[fieldI];
                template10Bean.name = jsonFieldName.getString(viewFieldArray[fieldI].toString());
                if (object != null) {
                    template10Bean.originValue = object.get(viewFieldArray[fieldI].toString());
                }
                switch (topBtn.template_id) {//通过模版设置对应字段显示对应布局样式
                    case "23": {
                        if ("user_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                        } else if ("real_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                        } else if ("user_role_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT;
                            if (object != null) {
                                template10Bean.originValue = object.get("user_role");
                            }
                        }
                        break;
                    }
                    case "27": {//用户编辑
                        if ("user_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                            mClientMap.put(template10Bean.key, template10Bean);
                        } else if ("pwd".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("newpwd".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT;
                            mClientMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "26": {//用户添加
                        if ("user_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_USERTYPE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("idcard".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("birthday".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_BIRTHDAY;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("sex".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_SEX;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("user_role".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_USER_ROLE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("remark".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("nationid".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_NATION;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "49"://学生列表 编辑
                    case "42": //教师列表 编辑
                    case "47"://学生列表 添加
                    case "40": {//教师列表 添加
                        if ("idcard".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("birthday".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_BIRTHDAY;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("sex".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_SEX;
                            if (template10Bean.originValue != null) {
                                template10Bean.showEditValue = Integer.parseInt(template10Bean.originValue.toString()) == 1 ? "男" : "女";
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("remark".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("nationid".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_NATION;
                            if (object != null) {
                                template10Bean.showEditValue = object.get("nationid_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "53":
                    case "52": {//部门添加
                        if ("remark".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("depart_duty".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("depart_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("parent_depart".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_PARENT_DEPARTMENT;
                            if (object != null) {
                                template10Bean.showEditValue = object.get("parent_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "59": {//部门成员 编辑
                        if ("depart_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("depart_users_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_MEMBER;
                            if (object != null) {
                                template10Bean.showEditValue = object.get("depart_users_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "91": {//发送通知
                        if ("typeid".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT_DEFAULT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("touserids".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_USER_TREE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("title".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_LIMIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("content".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "65": {//建筑物管理编辑
                        if ("building_code".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("district_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_DISTRICT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("use_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT;
                            if (object != null) {
                                template10Bean.content = object.get("use_type").toString();
                                if ("1".equals(object.get("use_type").toString())) {
                                    template10Bean.originValue = "教学";
                                } else if ("2".equals(object.get("use_type").toString())) {
                                    template10Bean.originValue = "实训";
                                } else if ("3".equals(object.get("use_type").toString())) {
                                    template10Bean.originValue = "宿舍";
                                } else if ("4".equals(object.get("use_type").toString())) {
                                    template10Bean.originValue = "食堂";
                                } else if ("5".equals(object.get("use_type").toString())) {
                                    template10Bean.originValue = "办公";
                                } else if ("6".equals(object.get("use_type").toString())) {
                                    template10Bean.originValue = "综合";
                                }
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("use_type_other".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_CLASSIFY;
                            if (object != null) {
                                template10Bean.showEditValue = object.get("use_type_other_name").toString();
                                if ("1".equals(object.get("use_type").toString())) {
                                    template10Bean.extend = "0";
                                } else if ("2".equals(object.get("use_type").toString())) {
                                    template10Bean.extend = "1";
                                } else if ("3".equals(object.get("use_type").toString())) {
                                    template10Bean.extend = "2";
                                } else if ("4".equals(object.get("use_type").toString())) {
                                    template10Bean.extend = "3";
                                } else if ("5".equals(object.get("use_type").toString())) {
                                    template10Bean.extend = "4";
                                } else if ("6".equals(object.get("use_type").toString())) {
                                    template10Bean.extend = "5";
                                }
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_BUILDING;
                            if (object != null) {
                                template10Bean.showEditValue = object.get("type_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("area_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_LAND_USE;
                            if (object != null) {
                                template10Bean.showEditValue = object.get("area_type_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("repair_date".equals(viewFieldArray[fieldI]) || ("completiontime".equals(viewFieldArray[fieldI]))) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_BIRTHDAY;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("maintain".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "64": {//建筑物管理添加
                        if ("district_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_DISTRICT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("use_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_USE_TYPE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("use_type_other".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_CLASSIFY;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_BUILDING;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("area_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_LAND_USE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("repair_date".equals(viewFieldArray[fieldI]) || ("completiontime".equals(viewFieldArray[fieldI]))) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_BIRTHDAY;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("maintain".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "71": {//教室类型管理 编辑
                        if ("type_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "70": {//教室类型管理 添加
                        if ("type_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "98": {//教室 编辑
                        if ("room_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_CLASSROOM;
                            if (object != null){
                                template10Bean.showEditValue = object.get("classroom_type_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("status".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_FLOOR;
                            if (object != null){
                                template10Bean.showEditValue = object.get("status_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "97": {//教室 添加 mainBean
                        if ("house_uses".equals(viewFieldArray[fieldI]) || "area".equals(viewFieldArray[fieldI]) || "room_equipment".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("room_type".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_CLASSROOM;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("building_code".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT_DEFAULT;
                            if (mainBean != null) {
                                template10Bean.originValue = mainBean.building_code;
                                template10Bean.showValue = mainBean.building_code;
                                template10Bean.content = mainBean.building_code;
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("building_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT_DEFAULT;
                            if (mainBean != null) {
                                template10Bean.originValue = mainBean.building_name;
                                template10Bean.showValue = mainBean.building_name;
                                template10Bean.content = mainBean.building_name;
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("status".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_FLOOR;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("remark".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "78": {//办公室 添加
                        if ("house_uses".equals(viewFieldArray[fieldI]) || "area".equals(viewFieldArray[fieldI]) || "phone".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("depart_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_DEPARTMENT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("building_code".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT_DEFAULT;
                            if (mainBean != null) {
                                template10Bean.originValue = mainBean.building_code;
                                template10Bean.showValue = mainBean.building_code;
                                template10Bean.content = mainBean.building_code;
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("building_name".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_TEXT_DEFAULT;
                            if (mainBean != null) {
                                template10Bean.originValue = mainBean.building_name;
                                template10Bean.showValue = mainBean.building_name;
                                template10Bean.content = mainBean.building_name;
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("status".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_FLOOR;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("remark".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "79": {//办公室 编辑
                        if ("depart_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_DEPARTMENT;
                            if (object != null){
                                template10Bean.showEditValue = object.get("depart_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("status".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_NEED_FLOOR;
                            if (object != null){
                                template10Bean.showEditValue = object.get("status_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "157":{//菜单管理 编辑
                        if ("parent_id".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_MENU_PARENT;
                            if (object != null){
                                template10Bean.showEditValue = object.get("parentname").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("status".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_IS_SHOW;
                            if (object != null){
                                template10Bean.showEditValue = object.get("status_name").toString();
                            }
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "114"://招生工作制度编辑
                    case "113":{//招生工作制度添加
                        if ("title".equals(viewFieldArray[fieldI])){
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("content".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else{
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_FILE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                    case "137"://招生工作制度编辑
                    case "136":{//招生工作制度添加
                        if ("title".equals(viewFieldArray[fieldI])){
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_EDIT_NEED;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else if ("remark".equals(viewFieldArray[fieldI])) {
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_REMARK;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        } else{
                            template10Bean.mLayoutType = Constant.LAYOUT_STYLE_SELECT_FILE;
                            mClientMap.put(template10Bean.key, template10Bean);
                            mServerMap.put(template10Bean.key, template10Bean);
                        }
                        break;
                    }
                }

                template10BeanList.add(template10Bean);
            }
        }
        topBtn.mTemplate10Been = template10BeanList;
        topBtn.mClientUser = mClientMap;
        topBtn.mServerUser = mServerMap;
    }
}
