package com.onesoft.digitaledu.view.activity.infomanager.docsendrec;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.onesoft.digitaledu.R;
import com.onesoft.digitaledu.model.BaseEvent;
import com.onesoft.digitaledu.model.FilePickBean;
import com.onesoft.digitaledu.model.SingleSelectBean;
import com.onesoft.digitaledu.presenter.infomanager.docsendrec.SendDocPresenter;
import com.onesoft.digitaledu.utils.MaxLengthWatcher;
import com.onesoft.digitaledu.utils.RemindUtils;
import com.onesoft.digitaledu.view.activity.ToolBarActivity;
import com.onesoft.digitaledu.view.iview.template.IAddView;
import com.onesoft.digitaledu.widget.crop.GlideImageLoader;
import com.onesoft.digitaledu.widget.dialog.AddAttachmentDialog;
import com.onesoft.digitaledu.widget.dialog.SingleSelectAdapter;
import com.onesoft.digitaledu.widget.dialog.SingleSelectDialog;
import com.onesoft.digitaledu.widget.lookbigimage.LookBigPicActivity;
import com.onesoft.digitaledu.widget.treerecyclerview.activity.TreeActivity;
import com.onesoft.netlibrary.utils.FileUtils;
import com.onesoft.netlibrary.utils.LogUtil;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.onesoft.digitaledu.model.BaseEvent.SEND_DOCUMENT;

/**
 * 发送消息 公文
 * Created by Jayden on 2016/12/29.
 */
public class SendDocActivity extends ToolBarActivity<SendDocPresenter> implements IAddView {

    public static final int REQUEST_CODE_TEACHER = 0x112;

    private static final int REQUEST_FILE = 0x115;
    private static final int REQUEST_CAMERA = 0x114;

    private ImageView mImageType;
    private ImageView mImageTypeReceipt;
    private ImageView mImageRecipient;
    private TextView mTxtRecipient;//teacher1,teacher2,)
    private TextView mTxtReceipt;
    private TextView mTxtSelectCount;
    private EditText mEditTitle;
    private EditText mEditContent;
    private GridView mGridView;
    private FilePickGridAdapter mGridAdapter;
    private File mTmpFile;

    private String touserids = "";

    private List<SingleSelectBean> mSingleSelectBeen;
    private String mSelectMessageType = "1";//默认是  是有回执

    @Override
    protected void initPresenter() {
        mPresenter = new SendDocPresenter(this, this);
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.send_message));
        mSingleSelectBeen = new ArrayList<>();
        mSingleSelectBeen.add(new SingleSelectBean("是", "1"));
        mSingleSelectBeen.add(new SingleSelectBean("否", "0"));

        mGridAdapter = new FilePickGridAdapter(this);
        mGridAdapter.setAddClickListener(new FilePickGridAdapter.OnAddClickListener() {
            @Override
            public void onAdd() {//选择添加附件
                showSelectAttach();
            }

            @Override
            public void onOpenFile(String path) {//打开文件
                try {
                    FileUtils.openFile(new File(path), SendDocActivity.this);
                } catch (Exception e) {
                    Toast.makeText(SendDocActivity.this, "无法打开此文件", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onOpenImage(ArrayList<String> paths, int pos) {//查看大图
                LookBigPicActivity.showBigPic(SendDocActivity.this, paths, pos);
            }
        });
        mGridView.setAdapter(mGridAdapter);

        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<String> photoList) {
                List<FilePickBean> beanList = new ArrayList<>();
                for (String s : photoList) {
                    FilePickBean bean = new FilePickBean(FilePickBean.TYPE_IMAGE, s);
                    beanList.add(bean);
                }
                mGridAdapter.addList(beanList);
                setSelectCount(mGridAdapter.getDataCount());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onError() {
            }
        };

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .pathList(path)                         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 280, 280)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        mPageStateLayout.onSucceed();
    }

    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;

    // 授权管理
    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(SendDocActivity.this);
        }
    }

    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(SendDocActivity.this);
            } else {
                LogUtil.e("拒绝授权");
            }
        }
    }

    /**
     * 弹出选择附件弹框
     */
    private void showSelectAttach() {
        AddAttachmentDialog dialog = new AddAttachmentDialog(this);
        dialog.show();
        dialog.setListener(new AddAttachmentDialog.IAttachListener() {
            @Override
            public void onClickAlbum() {
                path.clear();//清楚之前选中的图片
                galleryConfig = galleryConfig.getBuilder().isOpenCamera(false)
                        .multiSelect(true)                      // 是否多选   默认：false
                        .multiSelect(true, FilePickGridAdapter.MAX_COUNT - mGridAdapter.getDataCount() - 1)
                        .maxSize(FilePickGridAdapter.MAX_COUNT - mGridAdapter.getDataCount() - 1)
                        .build();
                initPermissions();
            }

            @Override
            public void onClickShot() {
                if (FilePickGridAdapter.MAX_COUNT - mGridAdapter.getDataCount() - 1 > 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        mTmpFile = FileUtils.createTmpFile(SendDocActivity.this);
                        Uri photoUri = Uri.fromFile(mTmpFile); // 传递路径
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } else {
                        Toast.makeText(SendDocActivity.this, "No Camera", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    RemindUtils.showDialog(SendDocActivity.this, getResources().getString(R.string.max_count));
                }
            }

            @Override
            public void onClickFile() {
                if (FilePickGridAdapter.MAX_COUNT - mGridAdapter.getDataCount() - 1 > 0) {
                    new MaterialFilePicker()
                            .withActivity(SendDocActivity.this)
                            .withRequestCode(REQUEST_FILE)
                            .withHiddenFiles(true)
                            .start();
                } else {
                    RemindUtils.showDialog(SendDocActivity.this, getResources().getString(R.string.max_count));
                }
            }
        });
    }

    @Override
    public void initView() {
        mGridView = (GridView) findViewById(R.id.grid_files);
        mImageTypeReceipt = (ImageView) findViewById(R.id.img_is_receipt);
        mImageType = (ImageView) findViewById(R.id.img_attach);
        mTxtSelectCount = (TextView) findViewById(R.id.txt_select_file_count);
        mTxtReceipt = (TextView) findViewById(R.id.txt_is_receipt);
        mTxtRecipient = (TextView) findViewById(R.id.txt_recipient);
        mImageRecipient = (ImageView) findViewById(R.id.img_recipient);
        mEditTitle = (EditText) findViewById(R.id.edit_title);
        mEditContent = (EditText) findViewById(R.id.edit_content);

        mEditTitle.addTextChangedListener(new MaxLengthWatcher(12, mEditTitle));
        mEditContent.addTextChangedListener(new MaxLengthWatcher(120, mEditContent));
    }

    @Override
    public void initListener() {
        mImageTypeReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//是否回执
                SingleSelectDialog dialog = new SingleSelectDialog<SingleSelectBean>(SendDocActivity.this) {
                    @Override
                    public SingleSelectAdapter getAdapter() {
                        return new SingleSelectAdapter(SendDocActivity.this) {
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
                        mSelectMessageType = bean.id;
                        mTxtReceipt.setText(bean.name);
                    }
                });
            }
        });
        mImageRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//收件人
                TreeActivity.startRecipientActivity(SendDocActivity.this, REQUEST_CODE_TEACHER);
            }
        });
        mImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加附件弹框
                showSelectAttach();
            }
        });
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
        textView.setText(getString(R.string.send));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) { //发送
                    mPresenter.sendMessage(touserids, mEditTitle.getText().toString().trim(),
                            mEditContent.getText().toString().trim(), mSelectMessageType,
                            mGridAdapter.getData());
                }
            }
        });
        if (item != null) {
            item.setActionView(view);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_send_doc, null);
    }

    @Override
    public void onAddSuccess() {
        EventBus.getDefault().post(new BaseEvent(SEND_DOCUMENT, ""));//刷新发件箱列表
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_TEACHER && data != null) {
                touserids = data.getExtras().getString("id");
                mTxtRecipient.setText(data.getExtras().getString("name"));
            } else if (requestCode == REQUEST_CAMERA) {
                mGridAdapter.addItem(new FilePickBean(FilePickBean.TYPE_IMAGE, mTmpFile.getAbsolutePath()));
                setSelectCount(mGridAdapter.getDataCount());
            } else if (requestCode == REQUEST_FILE && data != null) {
                mGridAdapter.addItem(new FilePickBean(FilePickBean.TYPE_FILE, data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)));
                setSelectCount(mGridAdapter.getDataCount());
            }
        }
    }

    private void setSelectCount(int count) {
        if (count == 0) {
            mTxtSelectCount.setVisibility(View.INVISIBLE);
        } else {
            mTxtSelectCount.setVisibility(View.VISIBLE);
            mTxtSelectCount.setText(String.valueOf(count));
        }
    }
}
