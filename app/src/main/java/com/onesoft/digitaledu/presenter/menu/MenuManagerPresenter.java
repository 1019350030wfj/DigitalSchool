package com.onesoft.digitaledu.presenter.menu;

import android.content.Context;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.MenuDetail;
import com.onesoft.digitaledu.model.MenuTitle;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.menu.IMenuManagerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/11/22.
 */

public class MenuManagerPresenter extends BasePresenter<IMenuManagerView> {
    public MenuManagerPresenter(Context context, IMenuManagerView iView) {
        super(context, iView);
    }

    public void getData() {
        List<MenuTitle> menuTitles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MenuTitle menu = new MenuTitle();
            if (i == 0) {
                menu.isSelected = true;
                menu.title = "综合排序";
            } else if (i == 1) {
                menu.title = "编号";
            } else if (i == 2) {
                menu.title = "菜单名称";
            } else if (i == 3) {
                menu.title = "父级菜单";
            } else if (i == 4) {
                menu.title = "菜单图片";
            } else {
                menu.title = "热门程度";
            }
            menuTitles.add(menu);
        }
        List<MenuDetail> menuDetails = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MenuDetail menuDetail = new MenuDetail();
            menuDetail.number = String.valueOf(i);
            if (i == 0) {
                menuDetail.menuName = "职称评定管理";
                menuDetail.menuParent = "组织人事";
                menuDetail.menuImage = "menu_images.png";
                menuDetail.isShow = "是";
                List<Integer> srcs = new ArrayList<>();
                srcs.add(R.drawable.bg_btn_edity);
                srcs.add(R.drawable.bg_btn_restore);
                srcs.add(R.drawable.bg_btn_datarecovery);
                menuDetail.operatorSrc = srcs;
            } else if (i == 1) {
                menuDetail.menuName = "信息快捷管理";
                menuDetail.menuParent = "分班管理";
                menuDetail.menuImage = "menu_images.png";
                menuDetail.isShow = "是";
                List<Integer> srcs = new ArrayList<>();
                srcs.add(R.drawable.bg_btn_edity);
                menuDetail.operatorSrc = srcs;
            } else if (i == 3) {
                menuDetail.menuName = "通告公共管理";
                menuDetail.menuParent = "顶级菜单";
                menuDetail.menuImage = "up_images.png";
                menuDetail.isShow = "是";
                List<Integer> srcs = new ArrayList<>();
                srcs.add(R.drawable.bg_btn_edity);
                srcs.add(R.drawable.bg_btn_datarecovery);
                menuDetail.operatorSrc = srcs;
            } else if (i == 2) {
                menuDetail.menuName = "职称评定管理";
                menuDetail.menuParent = "组织人事";
                menuDetail.menuImage = "menu_images.png";
                menuDetail.isShow = "是";
            } else if (i == 4) {
                menuDetail.menuName = "总经理管理";
                menuDetail.menuParent = "部门人事";
                menuDetail.menuImage = "menu_001.png";
                menuDetail.isShow = "是";
                List<Integer> srcs = new ArrayList<>();
                srcs.add(R.drawable.bg_btn_restore);
                menuDetail.operatorSrc = srcs;
            } else if (i == 5) {
                menuDetail.menuName = "自行车管理";
                menuDetail.menuParent = "交通管理";
                menuDetail.menuImage = "bike.png";
                menuDetail.isShow = "否";
            } else if (i == 6) {
                menuDetail.menuName = "英语培训";
                menuDetail.menuParent = "语言教学";
                menuDetail.menuImage = "language.png";
                menuDetail.isShow = "是";
                List<Integer> srcs = new ArrayList<>();
                srcs.add(R.drawable.bg_btn_datarecovery);
                menuDetail.operatorSrc = srcs;
            } else {
                menuDetail.menuName = "职称评定管理";
                menuDetail.menuParent = "组织人事";
                menuDetail.menuImage = "menu_images.png";
                menuDetail.isShow = "是";
                List<Integer> srcs = new ArrayList<>();
                srcs.add(R.drawable.bg_btn_edity);
                srcs.add(R.drawable.bg_btn_restore);
                srcs.add(R.drawable.bg_btn_datarecovery);
                menuDetail.operatorSrc = srcs;
            }
            menuDetails.add(menuDetail);
        }
        iView.onSuccess(menuTitles, menuDetails);
    }
}
