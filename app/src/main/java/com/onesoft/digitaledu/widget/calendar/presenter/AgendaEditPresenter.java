package com.onesoft.digitaledu.widget.calendar.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static com.onesoft.digitaledu.model.net.HttpUrl.THREE_MENU_TO_DETAIL;

/**
 * 编辑日程
 * Created by Jayden on 2016/12/27.
 */

public class AgendaEditPresenter extends BasePresenter<IAddView> {

    public AgendaEditPresenter(Context context, IAddView iView) {
        super(context, iView);
    }

    /**
     * 编辑日程
     * start 开始时间（时间格式为2016-12-22 17:29:01）
     * @param title
     * @param schedule_category
     * @param start
     * @param end
     */
    public void addAgenda(String cid,String title,String schedule_category,String start,String end,
                            String loc,String notes,String url,boolean isAd,String rem){
        RemindUtils.showLoading(mContext);
        String urlReq = THREE_MENU_TO_DETAIL + "514";
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("cid", cid));
        params.add(new HttpHandler.Param("template_id", "105"));
        params.add(new HttpHandler.Param("title", title));
        params.add(new HttpHandler.Param("user_id", SPHelper.getUserId(mContext)));
        params.add(new HttpHandler.Param("schedule_category_id", schedule_category));
        params.add(new HttpHandler.Param("start", start));
        params.add(new HttpHandler.Param("end", end));
        params.add(new HttpHandler.Param("loc", loc));
        params.add(new HttpHandler.Param("notes", notes));
        params.add(new HttpHandler.Param("url", url));
        params.add(new HttpHandler.Param("ad", String.valueOf(isAd)));
        params.add(new HttpHandler.Param("rem", rem));
        HttpHandler.getInstance(mContext).postAsync(mContext, urlReq,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        RemindUtils.hideLoading();
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onAddSuccess();
                        } else {
                            RemindUtils.showDialog(mContext,response.msg);
                        }
                    }
                });
    }
}
