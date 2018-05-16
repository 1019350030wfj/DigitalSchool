package com.onesoft.digitaledu.widget.calendar.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.Agenda;
import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.utils.TimeUtil;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.IBaseView;

import butterknife.BindView;

/**
 * 查看日程
 * type 1：家   2：工作   3：学校
 * Created by Jayden on 2016/12/27.
 */
public class AgendaDetailActivity extends ToolBarActivity<BasePresenter> implements IBaseView {

    private Agenda mAgenda;

    public static void startAgendaDetail(Context context,Agenda agenda){
        Intent intent = new Intent(context,AgendaDetailActivity.class);
        intent.putExtra("agenda",agenda);
        context.startActivity(intent);
    }

    private void getDataFromForward(){
        if (getIntent() != null && getIntent().getExtras() != null){
            mAgenda = (Agenda) getIntent().getExtras().getSerializable("agenda");
        }
    }

    @BindView(R.id.edit_title)
    TextView mEventTitle;
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
    TextView mEditSiteUrl;
    @BindView(R.id.edit_place)
    TextView mEditPlace;
    @BindView(R.id.edit_content)
    TextView mEditContent;


    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new BasePresenter(this, this) {
        };
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_edit_agenda, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.look_agenda));
        mEventTitle.setText(mAgenda.title);
        mTitleType.setText(mAgenda.imgType);
        mTitleFromTime.setText(TimeUtil.getYMDFromServerData(mAgenda.startTime));
        mTitleFromTimeHour.setText(TimeUtil.getHMFromServerData(mAgenda.startTime));
        mTitleToTime.setText(TimeUtil.getYMDFromServerData(mAgenda.endTime));
        mTitleToTimeHour.setText(TimeUtil.getHMFromServerData(mAgenda.endTime));
        mImgRadio.setChecked(!"false".equals(mAgenda.ad));
        mTitleRemind.setText(mAgenda.rem);
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
        textView.setText(getString(R.string.edit));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//编辑
                AgendaEditActivity.startAgendaDetail(AgendaDetailActivity.this,mAgenda);
                finish();
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
