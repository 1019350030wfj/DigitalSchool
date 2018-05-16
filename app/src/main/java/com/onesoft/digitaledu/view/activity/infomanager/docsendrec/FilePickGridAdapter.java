package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.FilePickBean;
import com.onesoft.digitaledu.utils.Utils;
import com.onesoft.digitaledu.utils.ViewUtil;
import com.onesoft.netlibrary.utils.FileUtils;
import com.onesoft.netlibrary.utils.ImageHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件上传
 */
public class FilePickGridAdapter extends BaseAdapter {

    private Activity mContext;
    private LayoutInflater mInflater;
    public static final int MAX_COUNT = 10;
    private List<FilePickBean> data;

    private ArrayList<String> mImageList;

    public FilePickGridAdapter(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageList = new ArrayList<>();
    }

    public List<FilePickBean> getData() {
        return data;
    }

    public void addList(List<FilePickBean> list) {
        mImageList.clear();//添加之前把以前的删除
        if (data == null) {
            data = list;
            for (FilePickBean already : data) {//已经选中的文件路径集合
                if (already.getType() == FilePickBean.TYPE_IMAGE) {
                    mImageList.add(already.getPath());
                }
            }
        } else if (list != null && list.size() > 0) {
            List<String> alreadyList = new ArrayList<>();
            for (FilePickBean already : data) {//已经选中的文件路径集合
                alreadyList.add(already.getPath());
                if (already.getType() == FilePickBean.TYPE_IMAGE) {
                    mImageList.add(already.getPath());
                }
            }
            for (FilePickBean item : list) {//当前选中的文件，看是否要被添加到选中集合里面
                if (!alreadyList.contains(item.getPath())) {
                    data.add(item);
                    if (item.getType() == FilePickBean.TYPE_IMAGE) {
                        mImageList.add(item.getPath());
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void addItem(FilePickBean bean) {
        mImageList.clear();
        if (data == null) {
            data = new ArrayList<FilePickBean>();
        }
        List<String> alreadyList = new ArrayList<>();
        for (FilePickBean already : data) {//已经选中的文件路径集合
            alreadyList.add(already.getPath());
            if (already.getType() == FilePickBean.TYPE_IMAGE) {
                mImageList.add(already.getPath());
            }
        }
        if (!alreadyList.contains(bean.getPath())) {
            data.add(bean);
            if (bean.getType() == FilePickBean.TYPE_IMAGE) {
                mImageList.add(bean.getPath());
            }
            notifyDataSetChanged();
        }
    }

    public void deleteItem(int pos) {
        mImageList.remove(data.get(pos).getPath());//先删除图片集合
        data.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data == null || data.size() == 0) {
            return 0;
        } else if (data.size() < MAX_COUNT) {
            return data.size() + 1;
        } else {
            return MAX_COUNT;
        }
    }

    @Override
    public FilePickBean getItem(int i) {
        if (data != null && i < data.size()) {
            return data.get(i);
        }
        return null;
    }

    public int getDataCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FilePickBean bean = getItem(position);
        if (bean == null) {
            convertView = mInflater.inflate(R.layout.item_pick_grid_add, parent, false);
            convertView.findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAddClickListener != null) {//点击添加
                        mAddClickListener.onAdd();
                    }
                }
            });
        } else {
            File file = new File(bean.getPath());
            if (bean.getType() == FilePickBean.TYPE_FILE) {//文件类型
                convertView = mInflater.inflate(R.layout.item_file_pick_grid, parent, false);
                ImageView mImageView = (ImageView) convertView.findViewById(R.id.image);
                ImageView mImageDel = (ImageView) convertView.findViewById(R.id.delete);
                TextView mTextName = (TextView) convertView.findViewById(R.id.txt_file_name);
                TextView mTextSize = (TextView) convertView.findViewById(R.id.txt_size);
                View llBg = convertView.findViewById(R.id.ll_bg);
                mTextSize.setText(Utils.convertFileSize(file.length()));
                mTextName.setText(file.getName());
                if (FileUtils.isCanOpenFile(file, (Activity) mContext)) {
                    ViewUtil.showImageByFileType(mImageView, file.getName(),llBg);
                } else {
                    mImageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_annex_download));
                }
                mImageDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(position);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAddClickListener != null) {
                            mAddClickListener.onOpenFile(bean.getPath());
                        }
                    }
                });
            } else {//图片类型
                convertView = mInflater.inflate(R.layout.item_image_pick_grid, parent, false);
                ImageView mImageView = (ImageView) convertView.findViewById(R.id.image_pick);
                final ImageView mImageDel = (ImageView) convertView.findViewById(R.id.delete_pick);
                TextView mTextSize = (TextView) convertView.findViewById(R.id.txt_size_pick);
                ImageHandler.getFileImage(mContext, mImageView,
                        bean.getPath());
                mTextSize.setText(Utils.convertFileSize(file.length()));
                mImageDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(position);
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAddClickListener != null) {
                            mAddClickListener.onOpenImage(mImageList, mImageList.indexOf(bean.getPath()));
                        }
                    }
                });
            }
        }
        return convertView;
    }

    private OnAddClickListener mAddClickListener;

    public void setAddClickListener(OnAddClickListener addClickListener) {
        mAddClickListener = addClickListener;
    }

    public interface OnAddClickListener {
        void onAdd();

        void onOpenFile(String path);

        void onOpenImage(ArrayList<String> paths, int pos);
    }
}
