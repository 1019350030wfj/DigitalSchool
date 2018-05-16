package com.onesoft.digitaledu.presenter.template;

import android.content.Context;
import android.text.TextUtils;

import com.onesoft.digitaledu.model.BaseListBean;
import com.onesoft.digitaledu.model.ClassroomType;
import com.onesoft.digitaledu.model.Department;
import com.onesoft.digitaledu.model.DownloadUrl;
import com.onesoft.digitaledu.model.MenuParent;
import com.onesoft.digitaledu.model.ParentDepartment;
import com.onesoft.digitaledu.model.SelectDistrict;
import com.onesoft.digitaledu.model.SelectMember;
import com.onesoft.digitaledu.model.SelectRole;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.model.net.HttpUrl;
import com.onesoft.digitaledu.view.iview.template.ICommonView;
import com.onesoft.netlibrary.model.HttpHandler;

import java.util.List;

import okhttp3.Request;

/**
 * 模版通用接口
 * Created by Jayden on 2016/11/23.
 */

public class TemplateCommonPresenter {

    private Context mContext;

    public TemplateCommonPresenter(Context context) {
        this.mContext = context;
    }

    public void downloadFileUrl(String id, String templateId, String cid, final ISelectList iView) {
        String url = HttpUrl.THREE_MENU_TO_DETAIL + id + "&template_id=" + templateId + "&cid=" + cid;
        HttpHandler.getInstance(mContext).getAsync(mContext, url, new HttpHandler.ResultCallback<BaseListBean<DownloadUrl>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<DownloadUrl> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetList(response.info);
                }
            }
        });
    }

    /**
     * 选择角色
     *
     * @param iView
     */
    public void getSelectRole(final ICommonView iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.CHOOSE_USER_ROLE, new HttpHandler.ResultCallback<BaseListBean<SelectRole>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<SelectRole> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetSelectRole(response.info);
                }
            }
        });
    }

    /**
     * 选择民族
     *
     * @param iView
     */
    public void getSelectNation(final ISelectNation iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_NATION, new HttpHandler.ResultCallback<BaseListBean<SingleSelectBean>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<SingleSelectBean> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetSelectRole(response.info);
                }
            }
        });
    }

    /**
     * 上级部门
     *
     * @param iView
     */
    public void getSelectParentDepartment(final IParentDepartment iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_TREE, new HttpHandler.ResultCallback<BaseListBean<ParentDepartment>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<ParentDepartment> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetParentDepartment(response.info);
                }
            }
        });
    }

    /**
     * 上级菜单
     *
     * @param iView
     */
    public void getSelectParentMenu(final ISelectList iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_TREE_MENU, new HttpHandler.ResultCallback<BaseListBean<MenuParent>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<MenuParent> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetList(response.info);
                }
            }
        });
    }

    /**
     * 选择成员
     *
     * @param iView
     */
    public void getSelectMember(final ISelectMember iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_MEMBER, new HttpHandler.ResultCallback<BaseListBean<SelectMember>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<SelectMember> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetSelectMember(response.info);
                }
            }
        });
    }

    /**
     * 所属校区
     *
     * @param iView
     */
    public void getSelectDistrict(final ISelectDistrict iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_DISTRICT, new HttpHandler.ResultCallback<BaseListBean<SelectDistrict>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<SelectDistrict> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetSelectDistrict(response.info);
                }
            }
        });
    }

    /**
     * 教室类型
     *
     * @param iView
     */
    public void getSelectClassroom(final IClassroom iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_CLASSROOM_TYPE, new HttpHandler.ResultCallback<BaseListBean<ClassroomType>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<ClassroomType> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetClassroom(response.info);
                }
            }
        });
    }

    /**
     * 所属部门
     *
     * @param iView
     */
    public void getSelectDepartment(final IDepartment iView) {
        HttpHandler.getInstance(mContext).getAsync(mContext, HttpUrl.SELECT_OFFICE_DEPART, new HttpHandler.ResultCallback<BaseListBean<Department>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseListBean<Department> response) {
                if (response != null && TextUtils.equals(HttpUrl.CODE_SUCCESS, response.statue)) {
                    iView.onGetDepartment(response.info);
                }
            }
        });
    }

    public interface ISelectList<T> {
        void onGetList(List<T> list);
    }

    public interface IDepartment {
        void onGetDepartment(List<Department> roles);
    }

    public interface IClassroom {
        void onGetClassroom(List<ClassroomType> roles);
    }

    public interface ISelectNation {
        void onGetSelectRole(List<SingleSelectBean> roles);
    }

    public interface ISelectDistrict {
        void onGetSelectDistrict(List<SelectDistrict> roles);
    }

    public interface ISelectMember {
        void onGetSelectMember(List<SelectMember> roles);
    }

    public interface IParentDepartment {
        void onGetParentDepartment(List<ParentDepartment> roles);
    }
}
