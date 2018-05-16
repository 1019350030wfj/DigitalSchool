package com.onesoft.digitaledu.widget.calendar.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.netlibrary.model.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static com.onesoft.digitaledu.model.net.HttpUrl.THREE_MENU_TO_DETAIL;

/**
 * 月视图
 * Created by Jayden on 2016/12/26.
 */
public class MonthPresenter extends BasePresenter<IMonthView> {

    public MonthPresenter(Context context, IMonthView iView) {
        super(context, iView);
    }

    /**
     * 通过年和月，获取日程
     * http://222.47.48.38:8080/new_digitalschool/api/main.php?id=514&template_id=102&query=&user_id=1&page=1
     *
     * @param year
     * @param month 不是两位数的必须0补齐
     */
    public void getYearMonth(String year, String month) {
        String url = THREE_MENU_TO_DETAIL + "514&template_id=102&page=1"
                + "&user_id=" + SPHelper.getUserId(mContext) + "&query=" + (year + "-" + (month.length() > 1 ? month : "0" + month));
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResponseCallback() {
            @Override //回调是异步的，如果有UI操作，记得切换到主线程
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(int code, String response) {
                handleResponse(response);
            }
        });
    }

    /**
     * 通过年、月和日，获取日程
     * http://222.47.48.38:8080/new_digitalschool/api/main.php?id=514&template_id=102&query=&user_id=1&page=1
     *
     * @param year
     * @param month
     */
    public void getYearMonthDay(String year, String month, String day) {
        String url = THREE_MENU_TO_DETAIL + "514&template_id=102&page=1"
                + "&user_id=" + SPHelper.getUserId(mContext) + "&query=" + (year + "-" + (month.length() > 1 ? month : "0" + month) + "-" + (day.length() > 1 ? day : "0" + day));
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResponseCallback() {
            @Override //回调是异步的，如果有UI操作，记得切换到主线程
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(int code, String response) {
                handleResponseDay(response);
            }
        });
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (HttpUrl.CODE_SUCCESS.equals(jsonObject.getString("statue"))) {
                List<Agenda> keyValues = getAgendaList(jsonObject);
            /*============这边获取main 列表数据=============*/
                if (keyValues.size() > 0) {
                    iView.onGetYearMonthDataSuccess(keyValues);
                } else {
                    iView.onGetYearMonthDataError("无日程");
                }
            } else {
                iView.onGetYearMonthDataError(jsonObject.getString("msg"));
            }
        } catch (JSONException e) {
            iView.onGetYearMonthDataError("无日程");
            e.printStackTrace();
        }
    }

    private void handleResponseDay(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (HttpUrl.CODE_SUCCESS.equals(jsonObject.getString("statue"))) {
                List<Agenda> keyValues = getAgendaList(jsonObject);
            /*============这边获取main 列表数据=============*/
                if (keyValues.size() > 0) {
                    iView.onGetDayDataSuccess(keyValues);
                } else {
                    iView.onGetDayDataError("无日程");
                }
            } else {
                iView.onGetDayDataError("无日程");
            }
        } catch (JSONException e) {
            iView.onGetDayDataError("无日程");
            e.printStackTrace();
        }
    }

    @NonNull
    private List<Agenda> getAgendaList(JSONObject jsonObject) throws JSONException {
        JSONObject jsonInfo = jsonObject.getJSONObject("info");
            /*============这边获取main 列表数据=============*/
        JSONArray mainArray = jsonInfo.getJSONArray("main");
        List<Agenda> keyValues = new ArrayList<Agenda>();
        for (int ii1 = 0, length1 = mainArray.length(); ii1 < length1; ii1++) {
            Agenda thirdToMainBean = new Agenda();
            JSONObject object = mainArray.getJSONObject(ii1);//每个item项显示的数据
            if (object.has("title")) {
                thirdToMainBean.title = object.getString("title");
            }
            if (object.has("start")) {
                thirdToMainBean.startTime = object.getString("start");
            }
            if (object.has("end")) {
                thirdToMainBean.endTime = object.getString("end");
            }
            if (object.has("ad")) {
                thirdToMainBean.ad = object.getString("ad");
            }
            if (object.has("rem")) {
                thirdToMainBean.rem = object.getString("rem");
            }
            if (object.has("url")) {
                thirdToMainBean.url = object.getString("url");
            }
            if (object.has("notes")) {
                thirdToMainBean.notes = object.getString("notes");
            }
            if (object.has("loc")) {
                thirdToMainBean.loc = object.getString("loc");
            }
            if (object.has("cid")) {
                thirdToMainBean.cid = object.getString("cid");
            }
            if (object.has("schedule_category_id")) {
                thirdToMainBean.schedule_category_id = object.getString("schedule_category_id");
            }
            if (object.has("id")) {
                thirdToMainBean.id = object.getString("id");
            }
            if (object.has("datenum")) {
                thirdToMainBean.day = object.getString("datenum");
            }
            if (object.has("schedule_category")) {
                thirdToMainBean.imgType = object.getString("schedule_category");
            }

            keyValues.add(thirdToMainBean);//总共有几个列表item项
        }
        return keyValues;
    }

    public void delete(String cid) {
        RemindUtils.showLoading(mContext);
        String url = THREE_MENU_TO_DETAIL + "514&template_id=106&cid=" + cid;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<Object> response) {
                RemindUtils.hideLoading();
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onDeleteSuccess();
                } else {
                    RemindUtils.showDialog(mContext, response.msg);
                }
            }
        });
    }
}
