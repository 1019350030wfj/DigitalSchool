package com.onesoft.digitaledu.view.activity.person.download;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onesoft.digitaledu.R;
import com.onesoft.netlibrary.utils.ImageHandler;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.base.Status;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.OnRetryableFileDownloadStatusListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * 正在下载
 * Created by Jayden on 2016/11/5.
 */

public class DownloadingAdapter extends BaseAdapter implements OnRetryableFileDownloadStatusListener {

    // all download infos
    private List<DownloadFileInfo> mDownloadFileInfos = Collections.synchronizedList(new ArrayList<DownloadFileInfo>());
    // cached convert views
    private Map<String, View> mConvertViews = new LinkedHashMap<String, View>();
    // select download file infos
    private List<DownloadFileInfo> mSelectedDownloadFileInfos = new ArrayList<DownloadFileInfo>();

    private boolean mISDeleteMode;

    private Context mContext;

    public DownloadingAdapter(Context context) {
        mContext = context;
        mISDeleteMode = false;
        initDownloadFileInfos();
    }

    // init DownloadFileInfos
    private void initDownloadFileInfos() {
        this.mDownloadFileInfos = getNoDownloadedFiles();
        mConvertViews.clear();
        mSelectedDownloadFileInfos.clear();
    }

    public List<DownloadFileInfo> getDatas() {
        return mDownloadFileInfos;
    }

    private List<DownloadFileInfo> getNoDownloadedFiles() {
        List<DownloadFileInfo> downloadFileInfos = new ArrayList<>();
        for (DownloadFileInfo downloadFileInfo : FileDownloader.getDownloadFiles()) {
            if (downloadFileInfo.getStatus() != Status.DOWNLOAD_STATUS_COMPLETED) {
                downloadFileInfos.add(downloadFileInfo);
            }
        }
        return downloadFileInfos;
    }

    /**
     * update show
     */
    public void updateShow() {
        initDownloadFileInfos();
        notifyDataSetChanged();
    }

    /**
     * 是否是删除模式
     *
     * @param ISDeleteMode
     */
    public void setISDeleteMode(boolean ISDeleteMode) {
        if (mISDeleteMode != ISDeleteMode) {
            mISDeleteMode = ISDeleteMode;
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mDownloadFileInfos.size();
    }

    @Override
    public DownloadFileInfo getItem(int position) {
        return mDownloadFileInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final DownloadFileInfo downloadFileInfo = getItem(position);

        if (downloadFileInfo == null) {
            return null;
        }

        final String url = downloadFileInfo.getUrl();

        if (TextUtils.isEmpty(url)) {
            mConvertViews.remove(url);
            return null;
        }


        View cacheConvertView = mConvertViews.get(url);

        if (cacheConvertView == null) {
            cacheConvertView = View.inflate(parent.getContext(), R.layout.item_downloading, null);
            mConvertViews.put(url, cacheConvertView);
        }

        ImageView mImgAvater = (ImageView) cacheConvertView.findViewById(R.id.avatar);
        View mImgDelete = cacheConvertView.findViewById(R.id.img_del);
        TextView mTxtName = (TextView) cacheConvertView.findViewById(R.id.name);
        final TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);
        ImageView mImgLook = (ImageView) cacheConvertView.findViewById(R.id.img_look);
        View mViewDot = cacheConvertView.findViewById(R.id.view_dot);
        ProgressBar pbProgress = (ProgressBar) cacheConvertView.findViewById(R.id.pbProgress);
        final TextView tvDownloadSize = (TextView) cacheConvertView.findViewById(R.id.tvDownloadSize);
        TextView tvTotalSize = (TextView) cacheConvertView.findViewById(R.id.tvTotalSize);
        TextView tvPercent = (TextView) cacheConvertView.findViewById(R.id.tvPercent);

        if (mISDeleteMode) {//删除模式
            mImgAvater.setVisibility(View.INVISIBLE);
            mImgDelete.setVisibility(View.VISIBLE);
            if (downloadFileInfo.isDelete) {//被选中要删除
                mImgDelete.setBackgroundResource(R.drawable.icon_mess_sel2_pre);
                cacheConvertView.setBackgroundColor(mContext.getResources().getColor(R.color.color_fafafa));
            } else {
                mImgDelete.setBackgroundResource(R.drawable.icon_mess_sel2_nor);
                cacheConvertView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
            }
        } else {
            mImgAvater.setVisibility(View.VISIBLE);
            mImgDelete.setVisibility(View.INVISIBLE);
        }
        ImageHandler.getImage((Activity) mContext, mImgAvater, "", R.drawable.icon_my_word);
        mTxtName.setText(downloadFileInfo.getFileName());
        // download progress
        int totalSize = (int) downloadFileInfo.getFileSizeLong();
        int downloaded = (int) downloadFileInfo.getDownloadedSizeLong();
        double rate = (double) totalSize / Integer.MAX_VALUE;
        if (rate > 1.0) {
            totalSize = Integer.MAX_VALUE;
            downloaded = (int) (downloaded / rate);
        }

        pbProgress.setMax(totalSize);
        pbProgress.setProgress(downloaded);

        // file size
        double downloadSize = downloadFileInfo.getDownloadedSizeLong() / 1024f / 1024;
        double fileSize = downloadFileInfo.getFileSizeLong() / 1024f / 1024;

        tvDownloadSize.setText(((float) (Math.round(downloadSize * 100)) / 100) + "M/");
        tvTotalSize.setText(((float) (Math.round(fileSize * 100)) / 100) + "M");

        // downloaded percent
        double percent = downloadSize / fileSize * 100;
        tvPercent.setText(((float) (Math.round(percent * 100)) / 100) + "%");

        switch (downloadFileInfo.getStatus()) {
            // download file status:unknown
            case Status.DOWNLOAD_STATUS_UNKNOWN:
                tvText.setText(mContext.getString(R.string.main__can_not_download));
                break;
            // download file status:retrying
            case Status.DOWNLOAD_STATUS_RETRYING:
                tvText.setText(mContext.getString(R.string.main__retrying_connect_resource));
                mImgLook.setImageResource(R.drawable.btn_my_retry);
                break;
            // download file status:preparing
            case Status.DOWNLOAD_STATUS_PREPARING:
                tvText.setText(mContext.getString(R.string.main__getting_resource));
                break;
            // download file status:prepared
            case Status.DOWNLOAD_STATUS_PREPARED:
                tvText.setText(mContext.getString(R.string.main__connected_resource));
                break;
            // download file status:paused
            case Status.DOWNLOAD_STATUS_PAUSED:
                tvText.setText(mContext.getString(R.string.main__paused));
                mImgLook.setImageResource(R.drawable.btn_my_carryon);
                break;
            // download file status:downloading
            case Status.DOWNLOAD_STATUS_DOWNLOADING:
                if (tvText.getTag() != null) {
                    tvText.setText((String) tvText.getTag());
                } else {
                    tvText.setText(mContext.getString(R.string.main__downloading));
                    mImgLook.setImageResource(R.drawable.btn_my_pause);
                }
                break;
            // download file status:error
            case Status.DOWNLOAD_STATUS_ERROR:
                tvText.setText(mContext.getString(R.string.main__download_error));
                mImgLook.setImageResource(R.drawable.btn_my_retry);
                break;
            // download file status:waiting
            case Status.DOWNLOAD_STATUS_WAITING:
                tvText.setText(mContext.getString(R.string.main__waiting));
                break;
            // download file status:completed
            case Status.DOWNLOAD_STATUS_COMPLETED:
                tvDownloadSize.setText("");
                tvText.setText(mContext.getString(R.string.main__download_completed));
                break;
            // download file status:file not exist
            case Status.DOWNLOAD_STATUS_FILE_NOT_EXIST:
                tvDownloadSize.setText("");
                tvText.setText(mContext.getString(R.string.main__file_not_exist));
                break;
        }

        mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mISDeleteMode) {//删除模式
                    downloadFileInfo.isDelete = !downloadFileInfo.isDelete;
                    if (mCompleteListener != null){//因为要更新Activity选中几个要被删除
                        mCompleteListener.onDelete();
                    }
                    notifyDataSetChanged();
                }
            }
        });

        setBackgroundOnClickListener(cacheConvertView, mImgLook, downloadFileInfo);
        return cacheConvertView;
    }

    // set convertView click
    private void setBackgroundOnClickListener(final View lnlyDownloadItem, final View imgLook, final DownloadFileInfo downloadFileInfo) {
        imgLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                if (downloadFileInfo != null) {
                    switch (downloadFileInfo.getStatus()) {
                        // download file status:unknown
                        case Status.DOWNLOAD_STATUS_UNKNOWN:
                            break;
                        // download file status:error & paused
                        case Status.DOWNLOAD_STATUS_ERROR:
                        case Status.DOWNLOAD_STATUS_PAUSED:
                            ((ImageView) imgLook).setImageResource(R.drawable.btn_my_pause);
                            FileDownloader.start(downloadFileInfo.getUrl());
                            break;
                        // download file status:file not exist
                        case Status.DOWNLOAD_STATUS_FILE_NOT_EXIST:
                            // show dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(context.getString(R.string.main__whether_re_download)).setNegativeButton
                                    (context.getString(R.string.main__dialog_btn_cancel), null);
                            builder.setPositiveButton(context.getString(R.string.main__dialog_btn_confirm), new
                                    DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // re-download
                                            FileDownloader.reStart(downloadFileInfo.getUrl());
                                        }
                                    });
                            builder.show();
                            break;
                        // download file status:retrying & waiting & preparing & prepared & downloading
                        case Status.DOWNLOAD_STATUS_RETRYING:
                        case Status.DOWNLOAD_STATUS_WAITING:
                        case Status.DOWNLOAD_STATUS_PREPARING:
                        case Status.DOWNLOAD_STATUS_PREPARED:
                        case Status.DOWNLOAD_STATUS_DOWNLOADING:
                            // pause
                            FileDownloader.pause(downloadFileInfo.getUrl());
                            TextView tvText = (TextView) lnlyDownloadItem.findViewById(R.id.tvText);
                            ImageView mImgLook = (ImageView) lnlyDownloadItem.findViewById(R.id.img_look);
                            if (tvText != null) {
                                tvText.setText(context.getString(R.string.main__paused));
                                mImgLook.setImageResource(R.drawable.btn_my_carryon);
                            }
                            break;
                        // download file status:completed
                        case Status.DOWNLOAD_STATUS_COMPLETED:
                            TextView tvDownloadSize = (TextView) lnlyDownloadItem.findViewById(R.id.tvDownloadSize);
                            if (tvDownloadSize != null) {
                                tvDownloadSize.setText("");
                            }

                            final TextView tvText2 = (TextView) lnlyDownloadItem.findViewById(R.id.tvText);


                            tvText2.setText(context.getString(R.string.main__download_completed));
                            break;
                    }
                }
            }
        });
    }

    /**
     * add new download file info
     */
    public boolean addNewDownloadFileInfo(DownloadFileInfo downloadFileInfo) {
        if (downloadFileInfo != null) {
            if (!mDownloadFileInfos.contains(downloadFileInfo)) {
                boolean isFind = false;
                for (DownloadFileInfo info : mDownloadFileInfos) {
                    if (info != null && info.getUrl().equals(downloadFileInfo.getUrl())) {
                        isFind = true;
                        break;
                    }
                }
                if (isFind) {
                    return false;
                }
                mDownloadFileInfos.add(downloadFileInfo);
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    // ///////////////////////////////////////////////////////////

    // ----------------------download status callback----------------------
    @Override
    public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {

        if (downloadFileInfo == null) {
            return;
        }

        // add
        if (addNewDownloadFileInfo(downloadFileInfo)) {
            // add succeed
            notifyDataSetChanged();
        } else {
            String url = downloadFileInfo.getUrl();
            View cacheConvertView = mConvertViews.get(url);
            if (cacheConvertView != null) {
                TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);
                tvText.setText(cacheConvertView.getContext().getString(R.string.main__waiting));

                Log.d(TAG, "onFileDownloadStatusWaiting url：" + url + "，status(正常应该是" + Status
                        .DOWNLOAD_STATUS_WAITING + ")：" + downloadFileInfo.getStatus());
            } else {
                updateShow();
            }
        }
    }

    @Override
    public void onFileDownloadStatusRetrying(DownloadFileInfo downloadFileInfo, int retryTimes) {

        if (downloadFileInfo == null) {
            return;
        }

        String url = downloadFileInfo.getUrl();
        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {
            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);

            ImageView mImgLook = (ImageView) cacheConvertView.findViewById(R.id.img_look);
            if (tvText != null) {
                tvText.setText(cacheConvertView.getContext().getString(R.string.main__retrying_connect_resource) + "(" +
                        retryTimes + ")");
                mImgLook.setImageResource(R.drawable.btn_my_carryon);
            }

            Log.d(TAG, "onFileDownloadStatusRetrying url：" + url + "，status(正常应该是" + Status.DOWNLOAD_STATUS_RETRYING
                    + ")：" + downloadFileInfo.getStatus());
        } else {
            updateShow();
        }
    }

    @Override
    public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {

        if (downloadFileInfo == null) {
            return;
        }

        String url = downloadFileInfo.getUrl();
        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {
            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);
            tvText.setText(cacheConvertView.getContext().getString(R.string.main__getting_resource));

            Log.d(TAG, "onFileDownloadStatusPreparing url：" + url + "，status(正常应该是" + Status
                    .DOWNLOAD_STATUS_PREPARING + ")：" + downloadFileInfo.getStatus());
        } else {
            updateShow();
        }
    }

    @Override
    public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {

        if (downloadFileInfo == null) {
            return;
        }

        String url = downloadFileInfo.getUrl();
        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {
            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);
            tvText.setText(cacheConvertView.getContext().getString(R.string.main__connected_resource));

            Log.d(TAG, "onFileDownloadStatusPrepared url：" + url + "，status(正常应该是" + Status.DOWNLOAD_STATUS_PREPARED
                    + ")：" + downloadFileInfo.getStatus());
        } else {
            updateShow();
        }
    }

    @Override
    public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long
            remainingTime) {

        if (downloadFileInfo == null) {
            return;
        }

        String url = downloadFileInfo.getUrl();
        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {

            ProgressBar pbProgress = (ProgressBar) cacheConvertView.findViewById(R.id.pbProgress);
            TextView tvDownloadSize = (TextView) cacheConvertView.findViewById(R.id.tvDownloadSize);
            TextView tvTotalSize = (TextView) cacheConvertView.findViewById(R.id.tvTotalSize);
            TextView tvPercent = (TextView) cacheConvertView.findViewById(R.id.tvPercent);
            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);

            // download progress
            int totalSize = (int) downloadFileInfo.getFileSizeLong();
            int downloaded = (int) downloadFileInfo.getDownloadedSizeLong();
            double rate = (double) totalSize / Integer.MAX_VALUE;
            if (rate > 1.0) {
                totalSize = Integer.MAX_VALUE;
                downloaded = (int) (downloaded / rate);
            }
            // pbProgress.setMax(totalSize);
            pbProgress.setProgress(downloaded);

            // download size
            double downloadSize = downloadFileInfo.getDownloadedSizeLong() / 1024f / 1024f;
            double fileSize = downloadFileInfo.getFileSizeLong() / 1024f / 1024f;

            tvDownloadSize.setText(((float) (Math.round(downloadSize * 100)) / 100) + "M/");
            tvTotalSize.setText(((float) (Math.round(fileSize * 100)) / 100) + "M");

            // download percent
            double percent = downloadSize / fileSize * 100;
            tvPercent.setText(((float) (Math.round(percent * 100)) / 100) + "%");

            // download speed and remain times
            String speed = ((float) (Math.round(downloadSpeed * 100)) / 100) + "KB/s";
//            String speed = ((float) (Math.round(downloadSpeed * 100)) / 100) + "KB/s" + "  " + TimeUtil
//                    .seconds2HH_mm_ss(remainingTime);
            tvText.setText(speed);
            tvText.setTag(speed);

            setBackgroundOnClickListener(cacheConvertView, cacheConvertView.findViewById(R.id.img_look), downloadFileInfo);
        } else {
            updateShow();
        }

        Log.d(TAG, "onFileDownloadStatusDownloading url：" + url + "，status(正常应该是" + Status
                .DOWNLOAD_STATUS_DOWNLOADING + ")：" + downloadFileInfo.getStatus());
    }

    @Override
    public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {

        if (downloadFileInfo == null) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        Log.d(TAG, "onFileDownloadStatusPaused url：" + url + "，status(正常应该是" + Status.DOWNLOAD_STATUS_PAUSED + ")：" +
                downloadFileInfo.getStatus());

        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {
            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);

            ImageView mImgLook = (ImageView) cacheConvertView.findViewById(R.id.img_look);
            if (tvText != null) {
                tvText.setText(cacheConvertView.getContext().getString(R.string.main__paused));
                mImgLook.setImageResource(R.drawable.btn_my_carryon);
            }


            setBackgroundOnClickListener(cacheConvertView, cacheConvertView.findViewById(R.id.img_look), downloadFileInfo);
        } else {
            updateShow();
        }

    }

    @Override
    public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {

        if (downloadFileInfo == null) {
            return;
        }

        String url = downloadFileInfo.getUrl();
        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {

            TextView tvDownloadSize = (TextView) cacheConvertView.findViewById(R.id.tvDownloadSize);
            TextView tvPercent = (TextView) cacheConvertView.findViewById(R.id.tvPercent);
            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);

            tvDownloadSize.setText("");

            // download percent
            float percent = 1;
            tvPercent.setText(((float) (Math.round(percent * 100)) / 100 * 100) + "%");

            if (downloadFileInfo.getStatus() == Status.DOWNLOAD_STATUS_COMPLETED) {

                tvText.setText(cacheConvertView.getContext().getString(R.string.main__download_completed));
            }

            setBackgroundOnClickListener(cacheConvertView, cacheConvertView.findViewById(R.id.img_look), downloadFileInfo);
        }
        updateShow();

        if (mCompleteListener != null) {
            mCompleteListener.onComplete();
        }

        Log.d(TAG, "onFileDownloadStatusCompleted url：" + url + "，status(正常应该是" + Status.DOWNLOAD_STATUS_COMPLETED +
                ")：" + downloadFileInfo.getStatus());
    }

    @Override
    public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo,
                                           OnFileDownloadStatusListener.FileDownloadStatusFailReason failReason) {

        String msg = mContext.getString(R.string.main__download_error);

        if (failReason != null) {
            if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_NETWORK_DENIED.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__check_network);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__url_illegal);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__network_timeout);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__storage_space_is_full);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_CAN_NOT_WRITE.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__storage_space_can_not_write);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_FILE_NOT_DETECT.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__file_not_detect);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_BAD_HTTP_RESPONSE_CODE.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__http_bad_response_code);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_HTTP_FILE_NOT_EXIST.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__http_file_not_exist);
            } else if (OnFileDownloadStatusListener.FileDownloadStatusFailReason.TYPE_SAVE_FILE_NOT_EXIST.equals(failReason.getType())) {
                msg += mContext.getString(R.string.main__save_file_not_exist);
            }
        }

        if (downloadFileInfo == null) {
            showToast(msg + "，url：" + url);
            return;
        }

        url = downloadFileInfo.getUrl();
        View cacheConvertView = mConvertViews.get(url);
        if (cacheConvertView != null) {

            TextView tvText = (TextView) cacheConvertView.findViewById(R.id.tvText);

            tvText.setText(msg);
            showToast(msg + "，url：" + url);

            setBackgroundOnClickListener(cacheConvertView, cacheConvertView.findViewById(R.id.img_look), downloadFileInfo);

            Log.d(TAG, "onFileDownloadStatusFailed 出错回调，url：" + url + "，status(正常应该是" + Status.DOWNLOAD_STATUS_ERROR
                    + ")：" + downloadFileInfo.getStatus());
        } else {
            updateShow();
        }
    }

    private Toast mToast;

    // show toast
    private void showToast(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    private OnDownloadingListener mCompleteListener;

    public void setDownloadingListener(OnDownloadingListener listener) {
        this.mCompleteListener = listener;
    }

    public interface OnDownloadingListener {
        void onComplete();

        void onDelete();
    }
}
