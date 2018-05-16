package com.onesoft.digitaledu.presenter.person;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.PersonInfo;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.view.iview.person.IEditPersonView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/12/1.
 */

public class PersonEditPresenter extends BasePresenter<IEditPersonView> {
    public PersonEditPresenter(Context context, IEditPersonView iView) {
        super(context, iView);
    }

    public void getPersonInfo() {
        String url = HttpUrl.PERSON_INFO + SPHelper.getUserId(mContext);
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<PersonInfo>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(BaseListBean<PersonInfo> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onSuccess(response.info.get(0));
                }
            }
        });
    }

    public void editInfo(String emial,String motto,String hobby,String specialty){
        String url = HttpUrl.PERSON_INFO_EDIT + SPHelper.getUserId(mContext);
        List<HttpHandler.Param> params = new ArrayList<HttpHandler.Param>();
        params.add(new HttpHandler.Param("email", emial));
        params.add(new HttpHandler.Param("motto", motto));
        params.add(new HttpHandler.Param("habby", hobby));
        params.add(new HttpHandler.Param("specialty", specialty));
        HttpHandler.getInstance(mContext).postAsync(mContext, url,
                params, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(BaseListBean<Object> response) {
                        if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                            iView.onSuccess();
                        }
                    }
                });
    }
}
