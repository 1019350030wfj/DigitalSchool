package com.onesoft.digitaledu.view.template.viewholderadd;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.widget.crop.GlideImageLoader;
import com.onesoft.digitaledu.widget.dialog.AddAttachmentDialog;
import com.onesoft.netlibrary.utils.LogUtil;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文字+"必选"+右边箭头，弹出弹框 选择文件
 * Created by Jayden on 2017/01/10.
 */
public class ViewHolder25 extends BaseViewHolder {

    public static final int REQUEST_FILE = 0x116;
    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;

    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;

    public ViewHolder25(Context context) {
        mContext = context;
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<String> photoList) {
                LogUtil.e("onsuccess");
                for (String s : photoList) {
                    LogUtil.e("onsuccess" + s);
                    updateTemplateBean(s);
                    mINotifyChange.onNotifyChange();
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onError() {
            }
        };

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(1)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 280, 280)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        EventBus.getDefault().register(this);
    }

    /**
     * 使用事件总线监听回调
     *
     * @param event
     */
    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(final BaseEvent event) {
        if (BaseEvent.UPDATE_FILE_SELECT == event.type) {
            updateTemplateBean(event.data);
            mINotifyChange.onNotifyChange();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public View createView(ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        view.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {
            }

            @Override
            public void onWindowDetached() {
                EventBus.getDefault().unregister(this);
            }
        });
        return view;
    }

    protected Template10Bean mTemplate10Bean;

    public void bindView(final TopBtn mTopBtn, final int position) {
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        setData(mTopBtn, position);
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectAttach();
            }
        });
    }

    /**
     * 弹出选择附件弹框
     */
    private void showSelectAttach() {
        AddAttachmentDialog dialog = new AddAttachmentDialog(mContext);
        dialog.show();
        dialog.setListener(new AddAttachmentDialog.IAttachListener() {
            @Override
            public void onClickAlbum() {
                path.clear();//清楚之前选中的图片
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open((Activity) mContext);
            }

            @Override
            public void onClickShot() {
                path.clear();//清楚之前选中的图片
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).openCamera((Activity) mContext);
            }

            @Override
            public void onClickFile() {
                new MaterialFilePicker()
                        .withActivity((Activity) mContext)
                        .withRequestCode(REQUEST_FILE)
                        .withHiddenFiles(true)
                        .start();
            }
        });
    }

    protected void setData(TopBtn mTopBtn, int position) {
        mTemplate10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtContent.setText(TextUtils.isEmpty(mTemplate10Bean.showValue)
                ? mContext.getString(R.string.need_select)
                : mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key).showValue);
    }

    protected void updateTemplateBean(String path) {
        File file = new File(path);
        mTemplate10Bean.showValue = file.getName();
        mTemplate10Bean.content = path;
    }

}
