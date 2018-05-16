package com.onesoft.digitaledu.presenter.infomanager.attendancelog;

import android.content.Context;
import android.support.annotation.NonNull;

import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.infomanager.attendancelog.IAttendanceDetailView;
import com.onesoft.netlibrary.model.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static com.onesoft.digitaledu.model.net.HttpUrl.THREE_MENU_TO_DETAIL;

/**
 * 考勤日志详情
 *
 * Created by Jayden on 2017/01/04.
 */

public class AttendanceLogDetailPresenter extends BasePresenter<IAttendanceDetailView> {
    public AttendanceLogDetailPresenter(Context context, IAttendanceDetailView iView) {
        super(context, iView);
    }

    public void getMessageDetail(String mesId){
        String url = THREE_MENU_TO_DETAIL + "789&template_id=109"
                + "&user_id=" + SPHelper.getUserId(mContext) + "&cid=" + mesId;
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

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (HttpUrl.CODE_SUCCESS.equals(jsonObject.getString("statue"))) {
                List<Agenda> keyValues = getAgendaList(jsonObject);
            /*============这边获取main 列表数据=============*/
                if (keyValues.size() > 0) {
                    iView.onSuccess(keyValues.get(0));
                } else {
                    iView.onSuccess(null);
                }
            } else {
                iView.onSuccess(null);
            }
        } catch (JSONException e) {
            iView.onSuccess(null);
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
            thirdToMainBean.teacher_name = object.getString("teacher_name");
            thirdToMainBean.the_teacher_id = object.getString("the_teacher_id");
            thirdToMainBean.remark = object.getString("remark");
            thirdToMainBean.type = object.getString("type");
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

}
