package com.onesoft.digitaledu.view.activity.infomanager.attendancelog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.presenter.infomanager.attendancelog.AttendanceLogDetailPresenter;
import com.onesoft.digitaledu.utils.TimeUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.IBaseView;
import com.onesoft.digitaledu.view.iview.infomanager.attendancelog.IAttendanceDetailView;
import com.onesoft.digitaledu.widget.calendar.activity.AgendaEditActivity;

import butterknife.BindView;

/**
 * 查看我的考勤日志
 * * Created by Jayden on 2017/01/04.
 */
public class AttendanceLogDetailActivity extends ToolBarActivity<AttendanceLogDetailPresenter> implements IBaseView, IAttendanceDetailView {

    @BindView(R.id.edit_name)
    TextView mEditName;
    @BindView(R.id.title_teacher_no)
    TextView mTitleTeacherNo;
    @BindView(R.id.title_from_time)
    TextView mTitleFromTime;
    @BindView(R.id.title_from_time_hour)
    TextView mTitleFromTimeHour;
    @BindView(R.id.title_to_time)
    TextView mTitleToTime;
    @BindView(R.id.title_to_time_hour)
    TextView mTitleToTimeHour;
    @BindView(R.id.title_state)
    TextView mTitleState;
    @BindView(R.id.edit_remark)
    TextView mEditRemark;
    private Agenda mAgenda;

    public static void startDetail(Context context, Agenda agenda) {
        Intent intent = new Intent(context, AttendanceLogDetailActivity.class);
        intent.putExtra("agenda", agenda);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mAgenda = (Agenda) getIntent().getExtras().getSerializable("agenda");
        }
    }

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new AttendanceLogDetailPresenter(this, this) {
        };
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_detail_attendancelog, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.look_attendance));
        mPresenter.getMessageDetail(mAgenda.id);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_finish);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_setting_wallpaper, null);
        TextView textView = (TextView) view.findViewById(R.id.riv_menu_main);
        textView.setVisibility(View.INVISIBLE);
        textView.setText(getString(R.string.edit));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//编辑
                AgendaEditActivity.startAgendaDetail(AttendanceLogDetailActivity.this, mAgenda);
                finish();
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onSuccess(final Agenda detail) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (detail != null) {
                    mEditName.setText(detail.teacher_name);
                    mTitleTeacherNo.setText(detail.the_teacher_id);
                    mTitleFromTime.setText(TimeUtil.getYMDFromServerData(detail.startTime));
                    mTitleFromTimeHour.setText(TimeUtil.getHMFromServerData(detail.startTime));
                    mTitleToTime.setText(TimeUtil.getYMDFromServerData(detail.endTime));
                    mTitleToTimeHour.setText(TimeUtil.getHMFromServerData(detail.endTime));
                    mTitleState.setText(detail.title);
                    mEditRemark.setText(detail.remark);
                    mPageStateLayout.onSucceed();
                } else {
                    mPageStateLayout.onEmpty();
                }
            }
        });

    }
}
