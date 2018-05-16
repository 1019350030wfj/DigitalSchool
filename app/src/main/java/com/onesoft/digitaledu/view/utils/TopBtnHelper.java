package com.onesoft.digitaledu.view.utils;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.ListBean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.activity.BaseSearchActivity;

import java.util.List;

import static com.onesoft.digitaledu.R.id.txt_user_add;

/**
 * 0：添加  1：编辑  2：删除   3：导入   4：导出
 * 5：上传   6：搜索   7：批量删除 8：查看 9:
 * Created by Jayden on 2016/12/12.
 */

public class TopBtnHelper {

    public static final int TOP_BTN_ADD = 0;
    public static final int TOP_BTN_EDIT = 1;
    public static final int TOP_BTN_DELETE = 2;
    public static final int TOP_BTN_IMPORT = 3;
    public static final int TOP_BTN_EXPORT = 4;
    public static final int TOP_BTN_UPLOAD = 5;
    public static final int TOP_BTN_SEARCH = 6;
    public static final int TOP_BTN_BATCH_DELETE = 7;
    public static final int TOP_BTN_LOOK = 8;
    public static final int TOP_BTN_NINE = 9;
    public static final int TOP_BTN_DOWNLOAD = 10;

    public TopBtnHelper() {

    }

    /**
     * 处理导航栏的
     *
     * @param navView
     * @param topBtns
     */
    public void handleNav(final View navView, final List<TopBtn> topBtns, final ListBean listBean, final OnPopupItemClickListener listener) {
        if (topBtns == null || topBtns.size() == 0) {
            navView.setVisibility(View.INVISIBLE);
            return;
        }
        if (topBtns.size() > 1) {//如果数组大小大于1时：
            navView.setBackgroundResource(R.drawable.bg_btn_more);
            navView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //弹出PopupWindow
                    LinearLayout container = (LinearLayout) LayoutInflater.from(navView.getContext()).
                            inflate(R.layout.layout_nav_popup_container, null);
                    final PopupWindow popupWindow = new PopupWindow(container,
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 控制popupwindow点击屏幕其他地方消失
                    popupWindow.setBackgroundDrawable(navView.getContext().getResources().getDrawable(
                            R.drawable.icon_mess_popupbox));// 设置背景图片，不能在布局中设置，要通过代码来设置
                    popupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
                    popupWindow.showAsDropDown(navView, -100, 0);

                    for (TopBtn topBtn : topBtns) {//遍历topbtn。然后根据类型，动态添加
                        View item = LayoutInflater.from(navView.getContext()).
                                inflate(R.layout.layout_user_manager_popup, null);
                        TextView textView = (TextView) item.findViewById(txt_user_add);
                        String[] topContent = topBtn.template_name.split(" ");
                        if (topContent.length > 1) {
                            textView.setText(topBtn.template_name.split(" ")[1]);
                        } else {
                            textView.setText(topBtn.template_name);
                        }
                        clickByBtnType(true, textView, popupWindow, topBtn, listBean, listener);

                        container.addView(item);
                    }
                }
            });
        } else {
            clickByBtnType(false, navView, null, topBtns.get(0), listBean, listener);
        }
    }

    public void clickByBtnType(boolean isPopup, final View textView, final PopupWindow popupWindow, final TopBtn topBtn, final ListBean listBean, final OnPopupItemClickListener listener) {
        switch (Integer.parseInt(topBtn.btn_type)) {//通过btn_type来判断
            case TOP_BTN_ADD: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        if (listener != null) {
                            listener.onAdd(topBtn);
                        }
                    }
                });
                if (isPopup) {
                    Drawable nav_up = textView.getResources().getDrawable(R.drawable.icon_addother);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    ((TextView) textView).setCompoundDrawables(nav_up, null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_add_nor);
                }
                break;
            }
            case TOP_BTN_EDIT: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        if (listener != null) {
                            listener.onAdd(topBtn);
                        }
                    }
                });
                if (isPopup) {

                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_DELETE: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {
                    Drawable nav_up = textView.getResources().getDrawable(R.drawable.icon_mess_del);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    ((TextView) textView).setCompoundDrawables(nav_up, null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_IMPORT: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {
                    Drawable nav_up = textView.getResources().getDrawable(R.drawable.icon_group);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    ((TextView) textView).setCompoundDrawables(nav_up, null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_EXPORT: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {

                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_UPLOAD: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {

                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_SEARCH: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//搜索
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        BaseSearchActivity.startSearch(textView.getContext(), topBtn, listBean);
                    }
                });
                if (isPopup) {
                    Drawable nav_up = textView.getResources().getDrawable(R.drawable.icon_search);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    ((TextView) textView).setCompoundDrawables(nav_up, null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_BATCH_DELETE: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//添加部门
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        if (listener != null) {
                            listener.onBatchDelete(topBtn);
                        }
                    }
                });
                if (isPopup) {
                    Drawable nav_up = textView.getResources().getDrawable(R.drawable.icon_mess_del);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    ((TextView) textView).setCompoundDrawables(nav_up, null, null, null);
                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_LOOK: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {

                } else {
                    textView.setBackgroundResource(R.drawable.nav_btn_search_nor);
                }
                break;
            }
            case TOP_BTN_NINE: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {

                } else {
                    textView.setBackgroundResource(R.drawable.bg_btn_datarecovery);
                }
                break;
            }
            case TOP_BTN_DOWNLOAD: {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                if (isPopup) {

                } else {
                    textView.setBackgroundResource(R.drawable.bg_btn_datarecovery);
                }
                break;
            }
        }
    }

    public interface OnPopupItemClickListener {
        void onAdd(TopBtn topBtn);

        void onBatchDelete(TopBtn topBtn);
    }
}
