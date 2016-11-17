package com.onesoft.digitaledu.view.activity.person.wallpaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onesoft.digitaledu.R;

/**
 * Created by Jayden on 2016/11/17.
 */

public class WallPaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_SHOOT = 0;
    private static final int VIEWTYPE_DEFAULT = 1;
    private static final int VIEWTYPE_NORMAL = 2;

    private Context mContext;

    private int[] mDrawableArray = new int[]{R.drawable.thumbnail_001, R.drawable.thumbnail_002, R.drawable.thumbnail_003, R.drawable.thumbnail_004, R.drawable.thumbnail_005,
            R.drawable.thumbnail_006, R.drawable.thumbnail_007, R.drawable.thumbnail_008, R.drawable.thumbnail_009, R.drawable.thumbnail_010,
            R.drawable.thumbnail_011, R.drawable.thumbnail_012, R.drawable.thumbnail_013};

    public WallPaperAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_SHOOT) {
            return new ViewHolderShoot(LayoutInflater.from(mContext).inflate(R.layout.item_wallpaper_shoot, parent, false));
        } else if (viewType == VIEWTYPE_DEFAULT) {
            return new ViewHolderDefault(LayoutInflater.from(mContext).inflate(R.layout.item_wallpaper_default, parent, false));
        } else {
            return new ViewHolderNormal(LayoutInflater.from(mContext).inflate(R.layout.item_wallpaper, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolderNormal) {
            ((ViewHolderNormal) holder).mImageView.setImageResource(mDrawableArray[position - 2]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickItemListener != null) {
                    mOnClickItemListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDrawableArray.length + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWTYPE_SHOOT;
        } else if (position == 1) {
            return VIEWTYPE_DEFAULT;
        } else {
            return VIEWTYPE_NORMAL;
        }
    }

    private static class ViewHolderShoot extends RecyclerView.ViewHolder {

        public ViewHolderShoot(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    //跳转到相机界面
                }
            });
        }
    }

    private static class ViewHolderDefault extends RecyclerView.ViewHolder {


        public ViewHolderDefault(android.view.View itemView) {
            super(itemView);
        }
    }

    private static class ViewHolderNormal extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img_src);
        }
    }

    private OnClickItemListener mOnClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClickItem(int pos);
    }
}
