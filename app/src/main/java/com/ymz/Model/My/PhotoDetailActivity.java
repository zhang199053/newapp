package com.ymz.Model.My;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ymz.App.BaseActivity;
import com.ymz.R;
import com.ymz.Utils.MyUtils;
import com.ymz.Utils.SystemUiVisibilityUtil;
import com.ymz.ViewUtils.NavigationBar;
import com.ymz.ViewUtils.photoView.PhotoView;
import com.ymz.ViewUtils.photoView.PhotoViewAttacher;
import com.ymz.ViewUtils.photoView.PullBackLayout;


/**
 * des:大图详情
 * Created by xsf
 * on 2016.09.14:35
 */
public class PhotoDetailActivity extends BaseActivity implements PullBackLayout.Callback {


    PhotoView photoTouchIv;
    PullBackLayout pullBackLayout;
    NavigationBar toolbar;
    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackground;
    public static final String PHOTO_DETAIL = "PHOTO_DETAIL";
    public static void startAction(Context context, String url) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_DETAIL, url);
        context.startActivity(intent);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_photo_detail;
    }

    @Override
    protected void initView() {
        pullBackLayout = findViewById(R.id.pull_back_layout);
        photoTouchIv = findViewById(R.id.photo_touch_iv);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnBackListen(this);
        pullBackLayout.setCallback(this);
        toolBarFadeIn();
        initToolbar();
        initBackground();
        loadPhotoIv();
        initImageView();
        setPhotoViewClickEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initToolbar() {
        toolbar.setOnBackListen(new NavigationBar.OnBackListen() {
            @Override
            public void back() {
                finish();
            }
        });
    }

    private void initImageView() {
        loadPhotoIv();
    }

    private void loadPhotoIv() {
        String url = getIntent().getStringExtra(PHOTO_DETAIL);
        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_ghc)
                .crossFade().into(photoTouchIv);
    }

    private void setPhotoViewClickEvent() {
        photoTouchIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }
        });
    }

    private void initBackground() {
        mBackground = new ColorDrawable(Color.TRANSPARENT);
        MyUtils.getRootView(this).setBackgroundDrawable(mBackground);
    }


    protected void hideOrShowToolbar() {
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(PhotoDetailActivity.this);
        } else {
            SystemUiVisibilityUtil.exit(PhotoDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();

        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    private void toolBarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackground.setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    @Override
    public void onClick(View v) {

    }
}
