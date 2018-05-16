package com.onesoft.digitaledu.widget.calendar.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.utils.TimeUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.widget.calendar.presenter.AgendaEditPresenter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;
import com.onesoft.digitaledu.widget.wheel.CustomDatePicker;
import com.onesoft.digitaledu.widget.wheel.CustomHourPicker;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 查看日程
 * type 1：家   2：工作   3：学校
 * Created by Jayden on 2016/12/27.
 */
public class AgendaEditActivity extends ToolBarActivity<AgendaEditPresenter> implements IAddView {

    private Agenda mAgenda;

    public static void startAgendaDetail(Context context, Agenda agenda) {
        Intent intent = new Intent(context, AgendaEditActivity.class);
        intent.putExtra("agenda", agenda);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mAgenda = (Agenda) getIntent().getExtras().getSerializable("agenda");
        }
    }

    @BindView(R.id.edit_title)
    EditText mEventTitle;
    @BindView(R.id.title_type)
    TextView mTitleType;
    @BindView(R.id.img_select)
    ImageView mImgSelect;
    @BindView(R.id.title_from_time)
    TextView mTitleFromTime;
    @BindView(R.id.title_from_time_hour)
    TextView mTitleFromTimeHour;
    @BindView(R.id.title_to_time)
    TextView mTitleToTime;
    @BindView(R.id.title_to_time_hour)
    TextView mTitleToTimeHour;
    @BindView(R.id.img_radio)
    CheckBox mImgRadio;
    @BindView(R.id.title_remind)
    TextView mTitleRemind;
    @BindView(R.id.img_remind)
    ImageView mImgRemind;
    @BindView(R.id.txt_site_url)
    TextView mTxtSiteUrl;
    @BindView(R.id.edit_site_url)
    EditText mEditSiteUrl;
    @BindView(R.id.edit_place)
    EditText mEditPlace;
    @BindView(R.id.edit_content)
    EditText mEditContent;

    private String mSelectMessageType = "1";//默认家
    private String mSelectRemind = "0";//默认无

    protected List<SingleSelectBean> mSingleSelectBeen;
    protected List<SingleSelectBean> mSingleRemind;
    protected CustomDatePicker fromDatePicker;
    protected CustomDatePicker toDatePicker;
    protected CustomHourPicker toHourPicker;
    protected CustomHourPicker fromHourPicker;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new AgendaEditPresenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_add_agenda, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public void initData() {
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean("家", "1"));
        mSingleSelectBeen.add(new SingleSelectBean("工作", "2"));
        mSingleSelectBeen.add(new SingleSelectBean("学校", "3"));

        mSingleRemind = new ArrayList<>();
        mSingleRemind.add(new SingleSelectBean("无", "0"));
        mSingleRemind.add(new SingleSelectBean("开始之前5分钟", "5"));
        mSingleRemind.add(new SingleSelectBean("开始之前15分钟", "15"));
        mSingleRemind.add(new SingleSelectBean("开始之前30分钟", "30"));
        mSingleRemind.add(new SingleSelectBean("开始之前1小时", "60"));
        mSingleRemind.add(new SingleSelectBean("开始之前1.5小时", "90"));
        mSingleRemind.add(new SingleSelectBean("开始之前2小时", "120"));
        mSingleRemind.add(new SingleSelectBean("开始之前3小时", "180"));

        setTitle(getString(R.string.edit_agenda));
        mEventTitle.setText(mAgenda.title);
        mTitleType.setText(mAgenda.imgType);
        mSelectMessageType = mAgenda.schedule_category_id;

        mTitleFromTime.setText(TimeUtil.getYMDFromServerData(mAgenda.startTime));
        mTitleFromTimeHour.setText(TimeUtil.getHMFromServerData(mAgenda.startTime));
        mTitleToTime.setText(TimeUtil.getYMDFromServerData(mAgenda.endTime));
        mTitleToTimeHour.setText(TimeUtil.getHMFromServerData(mAgenda.endTime));
        mImgRadio.setChecked(!"false".equals(mAgenda.ad));
        mTitleRemind.setText(mAgenda.rem);
        mSelectRemind = mAgenda.rem;
        mEditSiteUrl.setText(mAgenda.url);
        mEditPlace.setText(mAgenda.loc);
        mEditContent.setText(mAgenda.notes);
        mPageStateLayout.onSucceed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_finish);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_setting_wallpaper, null);
        TextView textView = (TextView) view.findViewById(R.id.riv_menu_main);
        textView.setText(getString(R.string.finish));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//完成
                mPresenter.addAgenda(mAgenda.id, mEventTitle.getText().toString().trim(), mSelectMessageType, mTitleFromTime.getText() + " " + mTitleFromTimeHour.getText(),
                        mTitleToTime.getText() + " " + mTitleToTimeHour.getText(), mEditPlace.getText().toString().trim(),
                        mEditContent.getText().toString().trim(),
                        mEditSiteUrl.getText().toString().trim(),
                        mImgRadio.isChecked(),
                        mSelectRemind);
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initListener() {
        mTitleFromTimeHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromHourPicker == null) {
                    fromHourPicker = new CustomHourPicker(AgendaEditActivity.this, new CustomHourPicker.ResultHandler() {
                        @Override
                        public void handle(String time) {
                            mTitleFromTimeHour.setText(time.split(" ")[1]);
                        }
                    }, "1910-01-01 00:00", "2910-01-01 00:00");
                    fromHourPicker.showSpecificTime(true); // 不显示时和分
                    fromHourPicker.hideYearMonthDay();
                    fromHourPicker.setIsLoop(true); // 允许循环滚动
                }
                // 日期格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                fromHourPicker.show(sdf.format(new Date()));
                fromHourPicker.setTitle(mTitleFromTimeHour.getText().toString());
            }
        });
        mTitleToTimeHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toHourPicker == null) {
                    toHourPicker = new CustomHourPicker(AgendaEditActivity.this, new CustomHourPicker.ResultHandler() {
                        @Override
                        public void handle(String time) {
                            mTitleToTimeHour.setText(time.split(" ")[1]);
                        }
                    }, "1910-01-01 00:00", "2910-01-01 00:00");
                    toHourPicker.showSpecificTime(true); // 不显示时和分
                    toHourPicker.hideYearMonthDay();
                    toHourPicker.setIsLoop(true); // 允许循环滚动
                }
                // 日期格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                toHourPicker.show(sdf.format(new Date()));
                toHourPicker.setTitle(mTitleToTimeHour.getText().toString());
            }
        });
        mTitleFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromDatePicker == null) {
                    fromDatePicker = new CustomDatePicker(AgendaEditActivity.this, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            mTitleFromTime.setText(time.split(" ")[0]);
                        }
                    }, "1910-01-01 00:00", "2910-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                    fromDatePicker.showSpecificTime(false); // 不显示时和分
                    fromDatePicker.setIsLoop(true); // 允许循环滚动
                }
                // 日期格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                fromDatePicker.show(sdf.format(new Date()));
                fromDatePicker.setTitle(mTitleFromTime.getText().toString());
            }
        });
        mTitleToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toDatePicker == null) {
                    toDatePicker = new CustomDatePicker(AgendaEditActivity.this, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            mTitleToTime.setText(time.split(" ")[0]);
                        }
                    }, "1910-01-01 00:00", "2910-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                    toDatePicker.showSpecificTime(false); // 不显示时和分
                    toDatePicker.setIsLoop(true); // 允许循环滚动
                }
                // 日期格式为yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                toDatePicker.show(sdf.format(new Date()));
                toDatePicker.setTitle(mTitleToTime.getText().toString());
            }
        });

        mImgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(AgendaEditActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(AgendaEditActivity.this) {
                            @Override
                            public void bindView(TextView textView, int pos) {
                                textView.setText(mSingleSelectBeen.get(pos).name);
                            }
                        };
                    }
                };
                dialog.show();
                dialog.setDatas(mSingleSelectBeen);
                dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
                    @Override
                    public void onConfirm(SingleSelectBean bean, int pos) {
                        mTitleType.setText(bean.name);
                        mSelectMessageType = bean.id;
                    }
                });
            }
        });
        mImgRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(AgendaEditActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(AgendaEditActivity.this) {
                            @Override
                            public void bindView(TextView textView, int pos) {
                                textView.setText(mSingleRemind.get(pos).name);
                            }
                        };
                    }
                };
                dialog.show();
                dialog.setDatas(mSingleRemind);
                dialog.setDialogListener(new SingleSelectDialog.IDialogClick<SingleSelectBean>() {
                    @Override
                    public void onConfirm(SingleSelectBean bean, int pos) {
                        mTitleRemind.setText(bean.name);
                        mSelectRemind = bean.id;
                    }
                });
            }
        });

        mImgRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mImgRadio.setChecked(isChecked);
            }
        });
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(BaseEvent.UPDATE_AGENDA_LIST, ""));
        finish();
    }
}
