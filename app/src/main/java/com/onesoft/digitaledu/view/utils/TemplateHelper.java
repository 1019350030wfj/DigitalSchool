package com.onesoft.digitaledu.view.utils;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.DownloadUrl;
import com.onesoft.digitaledu.model.ThirdToMainBean;
import com.onesoft.digitaledu.model.ThirdToMainDetail;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.presenter.template.TemplateCommonPresenter;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.utils.SPHelper;
import com.onesoft.digitaledu.utils.Utils;
import com.onesoft.digitaledu.view.activity.ThirdMainRecoveryImplListActivity;
import com.onesoft.digitaledu.view.activity.publicfoundation.menu.OperatorBtnEditActivity;
import com.onesoft.digitaledu.view.module.AnnouceAddActivity;
import com.onesoft.digitaledu.view.module.AnnouceEditActivity;
import com.onesoft.digitaledu.view.template.Template10AddDynamicActivity;
import com.onesoft.digitaledu.view.template.Template11DynamicActivity;
import com.onesoft.digitaledu.view.template.Template16Activity;
import com.onesoft.digitaledu.view.template.Template23EditDynamicActivity;
import com.onesoft.digitaledu.view.template.Template26AddDynamicActivity;
import com.onesoft.digitaledu.view.template.Template27Activity;
import com.onesoft.digitaledu.view.template.Template42EditDynamicActivity;
import com.onesoft.digitaledu.widget.dialog.FileDownloadDialog;
import com.onesoft.digitaledu.widget.dialog.ThirdToMainItemDetailDialog;
import com.onesoft.netlibrary.model.HttpHandler;

import org.greenrobot.eventbus.EventBus;
import org.wlf.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_MAIN_TO_LIST;
import static com.onesoft.digitaledu.model.BaseEvent.UPDATE_THIRD_TO_MAIN;

/**
 * 删除方法，如果是动态页进去的动态页。
 * 请看删除方法，要根据模版id判断刷新哪个动态页
 * <p>
 * 根据模版id进入相应的页面
 * Created by Jayden on 2016/12/12.
 */

public class TemplateHelper {

    public TemplateHelper() {

    }

    /**
     * 根据模版id，进入相应的操作页面
     *
     * @param context
     * @param keyValueBean
     * @param topBtn
     */
    public void handleToTemplate(final Context context, ThirdToMainBean keyValueBean, final TopBtn topBtn) {
        if (TopBtnHelper.TOP_BTN_LOOK == Integer.parseInt(topBtn.btn_type)) {
            getDetail(context, keyValueBean, topBtn);//详情
            return;
        }

        if (TopBtnHelper.TOP_BTN_DELETE == Integer.parseInt(topBtn.btn_type)) {
            delete(context, keyValueBean, topBtn);//删除
            return;
        }

        if (TopBtnHelper.TOP_BTN_NINE == Integer.parseInt(topBtn.btn_type)) {
            ThirdMainRecoveryImplListActivity.startTemplate(context, keyValueBean, topBtn);//又进入动态列表
            return;
        }

        if (TopBtnHelper.TOP_BTN_DOWNLOAD == Integer.parseInt(topBtn.btn_type)) {
            TemplateCommonPresenter templateCommonPresenter = new TemplateCommonPresenter(context);
            templateCommonPresenter.downloadFileUrl(topBtn.menuID, topBtn.template_id, keyValueBean.id, new TemplateCommonPresenter.ISelectList<DownloadUrl>() {
                @Override
                public void onGetList(final List<DownloadUrl> list) {//获取文件url成功后
                    if (!Utils.isListEmpty(list)) {
                        FileDownloadDialog dialog = new FileDownloadDialog(context);
                        dialog.show();
                        dialog.setTextHint(context.getResources().getString(R.string.file_download_to_list));
                        dialog.setFileDownloadListener(new FileDownloadDialog.IFileDownload() {
                            @Override
                            public void onConfirm() {
                                List<String> boxBeanList = new ArrayList<>();
                                boxBeanList.add(list.get(0).download_url);//下载文件
                                FileDownloader.start(boxBeanList);
                            }
                        });
                    }
                }
            });

            return;
        }

        switch (topBtn.template_id) {
            case "2": {//操作按钮 编辑
                OperatorBtnEditActivity.startOperator(context, keyValueBean, topBtn);
                break;
            }
            case "10": { //校区管理添加
                Template10AddDynamicActivity.startTemplate10(context, topBtn);
                break;
            }
            case "16": {//权限下属分配设置 添加
                Template16Activity.startTemplate(context, keyValueBean, topBtn);
                break;
            }
            case "32": {//公共管理添加
                AnnouceAddActivity.startAnnouceAdd(context, keyValueBean, topBtn);
                break;
            }
            case "33": {//公共管理编辑
                AnnouceEditActivity.startAnnouceAdd(context, keyValueBean, topBtn);
                break;
            }
            case "11": {//校区管理 编辑
                Template11DynamicActivity.startTemplate(context, keyValueBean, topBtn);
                break;
            }
            case "23": { //已分配权限编辑
                Template23EditDynamicActivity.startTemplate(context, keyValueBean, topBtn);
                break;
            }
            case "27": { //用户编辑
                Template27Activity.startTemplate(context, keyValueBean, topBtn);
                break;
            }
            case "137"://招生工作计划编辑
            case "114"://招生工作制度编辑
            case "157"://菜单管理 编辑
            case "79"://办公室管理 编辑
            case "98"://教室管理 编辑
            case "71"://教室类型管理 编辑
            case "59"://部门成员编辑
            case "65"://建筑物管理 编辑
            case "53"://部门编辑
            case "49"://学生编辑
            case "42": { //教师编辑
                Template42EditDynamicActivity.startTemplate(context, keyValueBean, topBtn);
                break;
            }
            case "113"://招生工作制度添加
            case "136"://招生工作计划添加
            case "78"://办公室管理 添加
            case "97"://教室管理 添加
            case "70"://教室类型管理 添加
            case "64"://建筑物管理
            case "91"://发送通知
            case "52"://部门添加
            case "40":
            case "47":
            case "26": { //用户添加,
                Template26AddDynamicActivity.startTemplate(context, keyValueBean, topBtn);
                break;
            }
        }
    }

    /**
     * 删除
     *
     * @param context
     * @param bean
     * @param topBtn
     */
    private void delete(final Context context, ThirdToMainBean bean, final TopBtn topBtn) {
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id=" + topBtn.template_id
                + "&cid=" + bean.id + "&user_id=" + SPHelper.getUserId(context);
        HttpHandler.getInstance(context).getAsync(context, url, new HttpHandler.ResultCallback<BaseListBean<Object>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<Object> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    if ("99".equals(topBtn.template_id) || "80".equals(topBtn.template_id)) {//教室 删除, 办公室删除
                        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_MAIN_TO_LIST, ""));//刷新动态列表
                    } else {
                        EventBus.getDefault().post(new BaseEvent(UPDATE_THIRD_TO_MAIN, ""));//刷新动态列表
                    }
                } else {
                    RemindUtils.showDialog(context, response.msg);
                }
            }
        });
    }

    /**
     * 详情
     *
     * @param context
     * @param mainBean
     * @param topBtn
     */
    private void getDetail(final Context context, ThirdToMainBean mainBean, TopBtn topBtn) {
        String url = HttpUrl.THREE_MENU_TO_DETAIL + topBtn.menuID + "&template_id=" + topBtn.template_id
                + "&cid=" + mainBean.id;
        HttpHandler.getInstance(context).getAsync(context, url, new HttpHandler.ResultCallback<BaseListBean<ThirdToMainDetail>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<ThirdToMainDetail> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue) && response.info != null && response.info.size() > 0) {
                    ThirdToMainItemDetailDialog dialog = new ThirdToMainItemDetailDialog(context);
                    dialog.show();
                    dialog.setDatas(response.info.get(0));
                }
            }
        });
    }
}
