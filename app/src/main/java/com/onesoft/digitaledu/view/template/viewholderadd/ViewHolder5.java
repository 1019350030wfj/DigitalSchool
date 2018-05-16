package com.onesoft.digitaledu.view.template.viewholderadd;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.widget.wheel.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * //出生年月+编辑框(必填)
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolder5 extends BaseViewHolder {

    protected TextView mTxtName;
    protected TextView mTxtContent;
    protected ImageView img_select;
    protected CustomDatePicker customDatePicker;

    public ViewHolder5(Context context) {
        mContext = context;
    }

    public View createView( ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_viewholder_3, parent, false);
        mTxtName = (TextView) view.findViewById(R.id.title);
        mTxtContent = (TextView) view.findViewById(R.id.title_content);
        img_select = (ImageView) view.findViewById(R.id.img_select);
        return view;
    }

    public void bindView(final TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        if (TextUtils.isEmpty(template10Bean.showValue)){
            mTxtContent.setHint("请输入"+mTopBtn.mTemplate10Been.get(position).name);
        } else {
            mTxtContent.setText(template10Bean.showValue);
        }
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDatePicker == null){
                    customDatePicker = new CustomDatePicker(mContext, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            template10Bean.showValue = time.split(" ")[0];
                            template10Bean.content = time.split(" ")[0];
                            mINotifyChange.onNotifyChange();
                        }
                    }, "1910-01-01 00:00", "2910-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                    customDatePicker.showSpecificTime(false); // 不显示时和分
                    customDatePicker.setIsLoop(true); // 允许循环滚动
                }
                // 日期格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                customDatePicker.show(sdf.format(new Date()));
            }
        });
    }
}
