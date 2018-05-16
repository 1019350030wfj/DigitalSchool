package com.onesoft.digitaledu.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by Jayden on 2016/11/18.
 */
public class LayoutListView extends LinearLayout {

    private BaseAdapter mAdapter;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public LayoutListView(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
    }

    public LayoutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setAdapter(BaseAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }

        removeAllViews();

        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mObserver);
            mAdapter.notifyDataSetInvalidated();
        }
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    public void bindLinearLayout() {
        removeAllViews();
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View v = mAdapter.getView(i, null, null);
            final int position = i;
            if (mOnItemClickListener != null) {
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(null, v, position, 0L);
                    }
                });
            }
            addView(v, i);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            bindLinearLayout();
        }

        @Override
        public void onInvalidated() {
            bindLinearLayout();
        }
    };
}
