package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Attach;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.digitaledu.view.BaseAbsListAdapter;
import com.onesoft.digitaledu.widget.dialog.MoreAttachDialog;
import com.onesoft.digitaledu.widget.lookbigimage.LookBigPicActivity;
import com.onesoft.netlibrary.utils.FileUtils;
import com.onesoft.netlibrary.utils.ImageHandler;
import com.onesoft.netlibrary.utils.PathUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 公文详情，附件列表
 */
public class DocDetailGridAdapter extends BaseAbsListAdapter<Attach> {
    public static final int REQUEST_CODE_DIR = 0x001;//获取文件夹路径
    private DocDownloadHelper mDocDownloadHelper;
    private LayoutInflater mInflater;

    public DocDetailGridAdapter(Activity context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        mDocDownloadHelper = new DocDownloadHelper();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Attach bean = getItem(position);
        if ("1".equals(bean.is_pic)) {//1才是图片，其它都是文件,//图片类型
            convertView = mInflater.inflate(R.layout.item_image_detail_grid, parent, false);
            ImageView mImageMore = (ImageView) convertView.findViewById(R.id.more_pick);
            ImageView mImageView = (ImageView) convertView.findViewById(R.id.image_pick);
            TextView mTextSize = (TextView) convertView.findViewById(R.id.txt_size_pick);
            mImageView.setImageResource(R.drawable.icon_annex_more);
            ImageHandler.getImage((Activity) mContext, mImageView,
                    bean.attach_url);
            mTextSize.setText(bean.size);
            mImageMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMoreDialog("1", position);
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lookBitPicture(bean);
                }
            });

            if (!mDocDownloadHelper.isFileExist(new File(PathUtil.getInstance().getDownloadDir(), bean.new_name))) {
                //不存在，则下载
                mDocDownloadHelper.downloadFile(mContext, bean.attach_url, PathUtil.getInstance().getDownloadDir(), bean.new_name, new DocDownloadHelper.IDownloadListener() {
                    @Override
                    public void onFinish() {

                    }
                });
            }
        } else {//文件类型
            convertView = mInflater.inflate(R.layout.item_file_detail_grid, parent, false);
            final ImageView mImage = (ImageView) convertView.findViewById(R.id.image);
            ImageView mImageMore = (ImageView) convertView.findViewById(R.id.more);
            TextView mTextName = (TextView) convertView.findViewById(R.id.txt_file_name);
            TextView mTextSize = (TextView) convertView.findViewById(R.id.txt_size);
            final View llBg = convertView.findViewById(R.id.ll_bg);
            mTextSize.setText(bean.size);
            mTextName.setText(bean.attach_name);
            mImageMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMoreDialog("0", position);
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//点击查看文件
                    lookFile(position);
                }
            });
            final File tempFile = new File(PathUtil.getInstance().getDownloadDir(), bean.new_name);
            if (!mDocDownloadHelper.isFileExist(tempFile)) {
                //不存在，则下载 btn_annex_download
                mDocDownloadHelper.downloadFile(mContext, bean.attach_url, PathUtil.getInstance().getDownloadDir(), bean.new_name, new DocDownloadHelper.IDownloadListener() {
                    @Override
                    public void onFinish() {//下载完，判断文件类型，显示相应的图标
                        if (FileUtils.isCanOpenFile(tempFile, (Activity) mContext)) {
                            ViewUtil.showImageByFileType(mImage, bean.new_name,llBg);
                        } else {
                            mImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_annex_download));
                        }
                    }
                });
            } else {
                if (FileUtils.isCanOpenFile(tempFile, (Activity) mContext)) {
                    ViewUtil.showImageByFileType(mImage, bean.new_name,llBg);
                } else {
                    mImage.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_annex_download));
                }
            }
        }
        return convertView;
    }

    /**
     * 查看文件
     *
     * @param position
     */
    private void lookFile(int position) {
        try {
            FileUtils.openFile(new File(PathUtil.getInstance().getDownloadDir(), getItem(position).new_name), (Activity) mContext);
        } catch (Exception e) {//无法打开此文件
            AttachCannotOpenActivity.startAttach(mContext, getItem(position));
            e.printStackTrace();
        }
    }

    /**
     * 查看大图
     *
     * @param bean
     */
    private void lookBitPicture(Attach bean) {
        ArrayList<String> paths = new ArrayList<>();
        for (Attach attach : getDatas()) {
            if ("1".equals(attach.is_pic)) {
                paths.add(attach.attach_url);
            }
        }
        if (paths.size() > 0) {//查看大图
            LookBigPicActivity.showBigPic(mContext, paths, paths.indexOf(bean.attach_url));
        }
    }

    private void showMoreDialog(final String type, final int pos) {
        final MoreAttachDialog dialog = new MoreAttachDialog(mContext);
        dialog.show();
        if ("1".equals(type)) {//图片
            dialog.setTextSave(mContext.getString(R.string.save_to_album));
        }
        dialog.setListener(new MoreAttachDialog.IAttachListener() {
            @Override
            public void onClickOpen() {
                if ("1".equals(type)) {//图片
                    lookBitPicture(getItem(pos));
                } else {
                    lookFile(pos);
                }
            }

            @Override
            public void onClickForward() {//转发
               if (mClickListener != null){
                   mClickListener.onForward(pos);
               }
            }

            @Override
            public void onClickSave() {
                if ("1".equals(type)) {//图片,保存至相册
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(PathUtil.getInstance().getDownloadDir(), getItem(pos).new_name));
                    intent.setData(uri);
                    mContext.sendBroadcast(intent);
                    Toast.makeText(mContext, mContext.getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                } else {//文件保存到指定文件夹
                    if (mClickListener != null){
                        mClickListener.onSaveFile(getItem(pos));
                    }
                }
            }
        });
    }

    private OnFileClickListener mClickListener;

    public void setClickListener(OnFileClickListener addClickListener) {
        mClickListener = addClickListener;
    }

    public interface OnFileClickListener {
        void onSaveFile(Attach attach);

        void onForward(int pos);
    }
}
