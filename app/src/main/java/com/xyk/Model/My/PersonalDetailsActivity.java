package com.xyk.Model.My;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xyk.App.App;
import com.xyk.App.BaseActivity;
import com.xyk.Dialog.SelectGeneralDialog;
import com.xyk.Entity.SexEntity;
import com.xyk.Entity.UserEntity;
import com.xyk.R;
import com.xyk.Utils.AppToast;
import com.xyk.Utils.HttpUtils.BaseCallback;
import com.xyk.Utils.HttpUtils.HttpClient;
import com.xyk.Utils.SharedPrefUtil;
import com.xyk.Utils.UrlUtils.Url;
import com.xyk.ViewUtils.NavigationBar;
import com.xyk.ViewUtils.SelectPicPopupWindow;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.xyk.Utils.UrlUtils.Url.DOMAIN_User;

/**
 * Administrator  ：zhouyuru
 * 2020/9/28
 * Describe ：个人信息详情
 */
public class PersonalDetailsActivity extends BaseActivity implements View.OnClickListener, NavigationBar.OnBackListen, OnRefreshListener {
    private NavigationBar nbAgre;

    //头像
    private ImageView tv_head_portrait;
    //    部门
    private TextView tv_bm;
    //    职位
    private TextView tv_gw;
    //    性别
    private TextView tv_sex;
    private LinearLayout ll_sex;
    //    手机号
    private TextView tv_phone;
    //    姓名
    private TextView tv_name;

    ImageView iv_qd;
    private SelectPicPopupWindow pop;
    /**
     * 相册
     */
    private static final int REQUST_CODE_101 = 101;
    /**
     * 相机
     */
    private static final int REQUST_CODE_102 = 102;
    /**
     * 拍照图片的地址
     */
    private Uri photoUri;
    /**
     * 图片临时存储位置
     */
    public String imagePath = App.getAppContext().getCacheDir().getPath() + "/image.jpg";
    private UserEntity.UsersData data;
    private SmartRefreshLayout srl_refresh;

    @Override
    protected int getContentView() {
        return R.layout.personal_details_activity;
    }

    @Override
    protected void initView() {
        srl_refresh = findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
        srl_refresh.autoRefresh();

        nbAgre = (NavigationBar) findViewById(R.id.nb_agre);
        nbAgre.setOnBackListen(this);
        tv_head_portrait = findViewById(R.id.tv_head_portrait);
        tv_head_portrait.setOnClickListener(this);
        iv_qd = findViewById(R.id.iv_qd);
        iv_qd.setOnClickListener(this);
        tv_sex = findViewById(R.id.tv_sex);
        ll_sex = findViewById(R.id.ll_sex);
        ll_sex.setOnClickListener(this);
        tv_phone = findViewById(R.id.tv_phone);
        tv_name = findViewById(R.id.tv_name);
        tv_bm = findViewById(R.id.tv_bm);
        tv_gw = findViewById(R.id.tv_gw);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_sex:
//                SexEntity sexEntity = new SexEntity();
//                SelectGeneralDialog selectGeneralDialog = new SelectGeneralDialog(mContext, sexEntity, tv_sex, "选择性别");
//                selectGeneralDialog.show();
//                break;
            case R.id.tv_head_portrait:
                showPicWindow();
                break;
            default:
                break;
        }

    }

    /**
     * 选择相册或相机
     */
    private void showPicWindow() {
        pop = new SelectPicPopupWindow(PersonalDetailsActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                switch (v.getId()) {
                    //点击拍照按钮
                    case R.id.item_popupwindows_camera:
                        goOpenCamera();
                        break;
                    //点击从相册中选择按钮
                    case R.id.item_popupwindows_Photo:
                        goWrite();
                        break;
                    default:
                        break;
                }
            }
        });
        //设置弹窗位置
        pop.showAtLocation(iv_qd, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 检查是否有相机权限
     */

    private void goOpenCamera() {
        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        // 在android 6.0之前会默认返回true
                        if (granted) {
                            // 用户已经同意该权限
                            LogUtil.d("用户已经同意该权限");
                            goCamera();
                        } else {
                            LogUtil.d("用户已经拒绝该权限");
                            AppToast.showToast("拒绝该权限将无法拍照上传");
                        }
                    }

                });
    }


    /**
     * 检查是否有读写权限
     */
    private void goWrite() {
        RxPermissions rxPermissions = new RxPermissions((Activity) mContext);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtil.d("用户已经同意该权限");
                            openPic();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtil.d("用户已经拒绝该权限");
                            AppToast.showToast("拒绝该权限将无法选择相册上传");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                            LogUtil.d("用户已经拒绝该权限");
                            AppToast.showToast("拒绝该权限将无法选择相册上传");
                        }
                    }

                });
    }

    /**
     * 打开相册
     */
    private void openPic() {
        Intent intent = new Intent();
        // 开启Pictures画面Type设定为image
        intent.setType("image/*");
        // 使用Intent.ACTION_GET_CONTENT这个Action
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 取得相片后返回本画面
        startActivityForResult(intent, REQUST_CODE_101);
    }

    /**
     * 跳转到相机
     */
    private void goCamera() {
        Intent intent = new Intent();
        // "android.media.action.IMAGE_CAPTURE"
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 需要说明一下，以下操作使用照相机拍照，
        // 拍照后的图片会存放在相册中的,这里使用的这种方式有一个好处就是获取的图片是拍照后的原图，
        // 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
        ContentValues values = new ContentValues();
        photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, REQUST_CODE_102);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inData();

    }

    private void inData() {
        if (!TextUtils.isEmpty(App.getRoleID()) && !TextUtils.isEmpty(App.getToken())) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("id", App.getRoleID());
            parame.put("token", App.getToken());
            HttpClient.getInstance().post(mContext, DOMAIN_User, parame, new BaseCallback<UserEntity>(UserEntity.class) {
                @Override
                public void onSuccess(UserEntity result) {
                    if (result.getInfo().equals("success")) {
                        //登录成功保存持久化用户数据
                        data = result.getData();
                    } else {
                        AppToast.showToast(result.getInfo());
                    }
                }

                @Override
                public void onError(String msg) {
                    AppToast.showToast(msg);

                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    showData(data);
                }
            });
        }
    }

    private void showData(UserEntity.UsersData data) {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }
        if (data != null) {
            String imgUrl = "http://" + Url.ip + data.getImg();
            tv_phone.setText(data.getTelephone());
            tv_name.setText(data.getUser_name());
            tv_bm.setText(data.getDepartment_name());
            tv_gw.setText(data.getRole_name());
            tv_sex.setText(data.getSex().equals("1") ? "男" : "女");
            Glide.with(this)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.mine_qst)
                    .error(R.mipmap.mine_qst)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(tv_head_portrait);
        }
    }
}
