package com.jlkf.text.textapp.ui.home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.ui.home.adapter.PhotoAdapter;
import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.decoration.Decoration;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 功能:相册拍照以及裁剪
 * 依赖来源：https://github.com/LuckSiege/PictureSelector
 * 本项目中添加该功能所对应的依赖：implementation 'com.github.LuckSiege.PictureSelector:picture_library:v2.2.3'
 */
public class PhotoActivity extends BaseActivity {

    @BindView(R.id.rv_menu)
    RecyclerView rv_menu;
    PhotoAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> selectLists;

    @Override
    public int intiLayout() {
        return R.layout.activity_photo_picker;
    }

    @Override
    public void initView() {
        initAdapter();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
    public void initAdapter(){
        selectLists=new ArrayList<>();
        adapter = new PhotoAdapter(selectLists);
        rv_menu.setLayoutManager(new GridLayoutManager(this, 3));
        rv_menu.addItemDecoration(Decoration.GridDecoration(3, 20, true));
        rv_menu.setAdapter(adapter);

    }

    //请求权限
    private void requestRxPermissions(final String... permissions) {
        RxPermissions mRxPermissions = new RxPermissions(PhotoActivity.this);
        mRxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean granted) throws Exception {
                if (granted) {
                    PictureSelector.create(PhotoActivity.this).openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频
                            // .ofAudio()
                            .theme(com.luck.picture.lib.R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                            .maxSelectNum(10)// 最大图片选择数量
                            .minSelectNum(1)// 最小选择数量
                            .imageSpanCount(3)// 每行显示个数
                            .selectionMode(true ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                            .previewImage(true)// 是否可预览图片
                            .previewVideo(false)// 是否可预览视频
                            .enablePreviewAudio(false) // 是否可播放音频
                            .isCamera(true)// 是否显示拍照按钮
                            .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                            .enableCrop(false)// 是否裁剪
                            .compress(true)// 是否压缩
                            .synOrAsy(false)//同步true或异步false 压缩 默认同步
                            //.compressSavePath(getPath())//压缩图片保存地址
                            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                            .isGif(false)// 是否显示gif图片
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                            .circleDimmedLayer(false)// 是否圆形裁剪
                            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                            .openClickSound(false)// 是否开启点击声音
                            .selectionMedia(selectList)// 是否传入已选图片
                            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                            //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                            //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                            //.rotateEnabled(true) // 裁剪是否可旋转图片
                            //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                            //.videoQuality()// 视频录制质量 0 or 1
                            //.videoSecond()//显示多少秒以内的视频or音频也可适用
                            //.recordVideoSecond()//录制视频秒数 默认60s
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                } else {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    selectLists.clear();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        LogUtil.e("图片路径-----》" + media.getPath() + "==============>" + selectList.get(0).getPath());
                        selectLists.add(media.getPath());
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }



    @OnClick({R.id.iv_back, R.id.btn_picker, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_picker:
                requestRxPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
            case R.id.btn_clear:
                selectLists.clear();
                selectList.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureFileUtils.deleteCacheDirFile(PhotoActivity.this);
    }
}
