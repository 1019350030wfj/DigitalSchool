package com.onesoft.digitaledu.view.template;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.TopBtn;
import com.onesoft.digitaledu.presenter.template.Template10Presenter;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.ITemplate10View;

import butterknife.BindView;

/**
 * 模版10，校区管理  添加
 * Created by Jayden on 2016/12/15.
 */

public class Template10CampusManagerActivity extends ToolBarActivity<Template10Presenter> implements ITemplate10View {

    private TopBtn mTopBtn;

    public static void startTemplate10(Context context, TopBtn topBtn) {
        Intent intent = new Intent(context, Template10CampusManagerActivity.class);
        intent.putExtra("topbtn", topBtn);
        context.startActivity(intent);
    }

    private void getDataFromForward() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mTopBtn = (TopBtn) getIntent().getExtras().getSerializable("topbtn");
        }
    }

    @BindView(R.id.edit_campus_number)
    EditText mEditCampusNumber;
    @BindView(R.id.edit_campus_name)
    EditText mEditCampusName;
    @BindView(R.id.edit_fixed_telephone)
    EditText mEditFixedTelephone;
    @BindView(R.id.edit_fax)
    EditText mEditFax;
    @BindView(R.id.edit_address)
    EditText mEditAddress;
    @BindView(R.id.edit_urban_district)
    EditText mEditUrbanDistrict;
    @BindView(R.id.edit_area_covered)
    EditText mEditAreaCovered;
    @BindView(R.id.rl_sex)
    RelativeLayout mRlSex;
    @BindView(R.id.edit_building_area)
    EditText mEditBuildingArea;
    @BindView(R.id.edit_total_student)
    EditText mEditTotalStudent;
    @BindView(R.id.rl_birthday)
    RelativeLayout mRlBirthday;
    @BindView(R.id.edit_district_responsible_person)
    EditText mEditDistrictResponsiblePerson;
    @BindView(R.id.edit_campus_principal)
    EditText mEditCampusPrincipal;
    @BindView(R.id.edit_campus_principal_one)
    EditText mEditCampusPrincipalOne;
    @BindView(R.id.edit_campus_principal_two)
    EditText mEditCampusPrincipalTwo;
    @BindView(R.id.edit_campus_principal_three)
    EditText mEditCampusPrincipalThree;
    @BindView(R.id.edit_content)
    EditText mEditContent;

    @Override
    protected void initPresenter() {
        getDataFromForward();
        mPresenter = new Template10Presenter(this, this);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_template_10, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper_setting, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_finish);
        View view = LayoutInflater.from(this).inflate(R.layout.menu_setting_wallpaper, null);
        TextView textView = (TextView) view.findViewById(R.id.riv_menu_main);
        textView.setText(getString(R.string.submit));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initData() {
        setTitle(getString(R.string.campus_add));
        mPageStateLayout.onSucceed();
    }

}
