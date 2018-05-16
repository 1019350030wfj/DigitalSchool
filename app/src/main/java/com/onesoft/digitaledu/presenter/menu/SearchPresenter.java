package com.onesoft.digitaledu.presenter.menu;

import android.content.Context;

import com.onesoft.digitaledu.model.KeyValueBean;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.presenter.utils.TemplateHandleDataHelper;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.menu.ISearchView;
import com.onesoft.netlibrary.model.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 操作按钮搜索
 * id=150
 * template_id=3
 * page 页数
 * query 名称
 * Created by Jayden on 2016/11/23.
 */

public class SearchPresenter extends BasePresenter<ISearchView> {
    private TemplateHandleDataHelper mTemplateHandleDataHelper;

    public SearchPresenter(Context context, ISearchView iView) {
        super(context, iView);
        mTemplateHandleDataHelper = new TemplateHandleDataHelper();
    }

    public void search(String content, int page, ListBean listBean, TopBtn topBtn) {
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id=" + topBtn.template_id
                + "&page=" + page + "&query=" + content + "&cid=" + listBean.id
                + "&user_id=" + SPHelper.getUserId(mContext);
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResponseCallback() {
            @Override //回调是异步的，如果有UI操作，记得切换到主线程
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(int code, String response) {
                //1、取top_btn 数组
                //2、sort 字符串， 排序菜单栏
                //3、list_field 字符串， 取出列表显示的字段
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonInfo = jsonObject.getJSONObject("info");
                    JSONObject jsonBtnField = jsonInfo.getJSONObject("btn_field");
                    JSONObject jsonFieldName = jsonInfo.getJSONObject("field_name");

                    String list_field = jsonBtnField.getString("list_field");
                         /*============这边获取main 列表数据=============*/
                    JSONArray mainArray = jsonInfo.getJSONArray("main");
                    List<ThirdToMainBean> keyValues = new ArrayList<ThirdToMainBean>();
                    String[] listField = list_field.split(",");
                    for (int ii1 = 0, length1 = mainArray.length(); ii1 < length1; ii1++) {
                        ThirdToMainBean thirdToMainBean = new ThirdToMainBean();
                        List<KeyValueBean> keyValueList = new ArrayList<KeyValueBean>();
                        JSONObject object = mainArray.getJSONObject(ii1);//每个item项显示的数据
                        if (object.has("building_code")) {
                            thirdToMainBean.building_code = object.getString("building_code");
                        }
                        if (object.has("building_name")) {
                            thirdToMainBean.building_name = object.getString("building_name");
                        }
                        if (object.has("item_btn")) {
                            thirdToMainBean.item_btn = object.getString("item_btn");
                        }
                        if (object.has("id")) {
                            thirdToMainBean.id = object.getString("id");
                        }
                        if (object.has("btn_remark")) {
                            thirdToMainBean.btn_remark = object.getString("btn_remark");
                        }
                        for (int j = 0, size = listField.length; j < size; j++) {
                            if (!object.has(listField[j])) {//如果没有这个字段，则跳过这一条数据
                                continue;
                            }
                            KeyValueBean keyValue = new KeyValueBean();
                            keyValue.key = jsonFieldName.getString(listField[j]);//获取字段名
                            Object tempObject = object.get(listField[j]);//获取字段对应的数据
                            if (tempObject instanceof String) {//如果是字符串
                                keyValue.value = (String) tempObject;//转换为字符串
                            } else if (tempObject instanceof JSONArray) {
                                JSONArray itemJsonArray = (JSONArray) tempObject;//如果是数组，说明是操作按钮
                                List<TopBtn> itemTopBtns = new ArrayList<TopBtn>();
                                for (int t = 0, tsize = itemJsonArray.length(); t < tsize; t++) {//循环遍历出每个操作按钮
                                    TopBtn topBtn = new TopBtn();
                                    JSONObject jsontop = itemJsonArray.getJSONObject(t);
                                    topBtn.template_id = jsontop.getString("template_id");
                                    topBtn.template_name = jsontop.getString("template_name");
                                    topBtn.menuID = jsontop.getString("menuID");
                                    topBtn.btn_type = jsontop.getString("btn_type");
                                    topBtn.template_url = jsontop.getString("template_url");
                                    topBtn.user_type = jsontop.getString("user_type");
                                    topBtn.view_field = jsontop.getString("view_field");
                                    topBtn.notice = jsontop.getString("notice");
                            /*============添加页要显示的字段=============*/
                                    mTemplateHandleDataHelper.convertServerDataToClientData(null, topBtn, jsonFieldName, object);
                            /*============添加页要显示的字段=============*/
                                    topBtn.parent_id = jsontop.getString("parent_id");
                                    topBtn.children_id = jsontop.getString("children_id");
                                    itemTopBtns.add(topBtn);
                                }
                                keyValue.value = itemTopBtns;
                            }
                            keyValueList.add(keyValue);//每个item项显示的数据
                        }
                        thirdToMainBean.mShownList = keyValueList;
                        keyValues.add(thirdToMainBean);//总共有几个列表item项
                    }
                    /*============这边获取main 列表数据=============*/

                    iView.onSuccess(keyValues);//数据解析完毕，回调到页面
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
