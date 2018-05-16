package com.onesoft.digitaledu.view.template.viewholderedit;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.onesoft.digitaledu.model.Template10Bean;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.view.template.viewholderadd.ViewHolder5;
import com.onesoft.digitaledu.widget.wheel.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jayden on 2016/12/21.
 */

public class ViewHolderEdit5 extends ViewHolder5 {

    public ViewHolderEdit5(Context context) {
        super(context);
    }

    public void bindView(final TopBtn mTopBtn, int position) {
        final Template10Bean template10Bean = mTopBtn.mServerUser.get(mTopBtn.mTemplate10Been.get(position).key);
        mTxtName.setText(mTopBtn.mTemplate10Been.get(position).name);
        mTxtContent.setText(template10Bean.originValue.toString());
        if (TextUtils.isEmpty(template10Bean.content)){
            template10Bean.content = template10Bean.originValue.toString();
        }
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customDatePicker == null){
                    customDatePicker = new CustomDatePicker(mContext, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            template10Bean.originValue = time.split(" ")[0];
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
