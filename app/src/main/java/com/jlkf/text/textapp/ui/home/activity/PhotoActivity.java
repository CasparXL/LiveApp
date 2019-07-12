package com.jlkf.text.textapp.ui.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.ActivityPhotoPickerBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.ui.dialog.MenuDialog;
import com.jlkf.text.textapp.ui.home.adapter.PhotoAdapter;
import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.decoration.Decoration;
import com.jlkf.text.textapp.util.image.FileUtil;
import com.jlkf.text.textapp.util.image.ImageSelect;
import com.jlkf.text.textapp.util.image.RealPathFromUriUtils;
import com.jlkf.text.textapp.util.permissions.OnPermission;
import com.jlkf.text.textapp.util.permissions.Permission;
import com.jlkf.text.textapp.util.permissions.XXPermissions;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * 功能:相册拍照以及裁剪
 * 依赖来源：https://github.com/LuckSiege/PictureSelector
 * 本项目中添加该功能所对应的依赖：implementation 'com.github.LuckSiege.PictureSelector:picture_library:v2.2.3'
 */
public class PhotoActivity extends BaseActivity<NoPresenter, ActivityPhotoPickerBinding> implements NoContract.View {


    PhotoAdapter adapter;
    private List<String> selectLists;
    private List<String> list;
    String imgPath = "";


    @Override
    public int setContentLayout() {
        return R.layout.activity_photo_picker;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);

    }

    @Override
    public void initView() {
        initAdapter();
    }

    @Override
    public void initIntent() {

    }

    public void initAdapter() {
        bindingView.include.tvTitle.setText("相册拍照");
        list = new ArrayList<>();
        list.add("拍照");
        list.add("选择相册");
        selectLists = new ArrayList<>();
        adapter = new PhotoAdapter(selectLists);
        bindingView.rvMenu.setLayoutManager(new GridLayoutManager(this, 3));
        bindingView.rvMenu.addItemDecoration(Decoration.GridDecoration(3, 20, true));
        bindingView.rvMenu.setAdapter(adapter);
        bindingView.include.ivBack.setOnClickListener(v -> finish());
        bindingView.btnPicker.setOnClickListener(v -> requestRxPermissions());
        bindingView.btnClear.setOnClickListener(v -> {
            selectLists.clear();
            adapter.notifyDataSetChanged();
        });
    }

    //请求权限
    @SuppressLint("CheckResult")
    private void requestRxPermissions() {
        XXPermissions.with(this).constantRequest().permission(Permission.Group.CAMERA).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll){
                    new MenuDialog.Builder(PhotoActivity.this)
                            .setList(list)
                            .setCancel(null) // 设置 null 表示不显示取消按钮
                            .setListener(new MenuDialog.OnListener() {
                                @Override
                                public void onSelected(Dialog dialog, int position, String text) {
                                    if (position == 1) {
                                        ImageSelect.doOpenAlbum(PhotoActivity.this);
                                    } else {
                                        imgPath = FileUtil.generateImgePathInStoragePath();
                                        ImageSelect.doOpenCamera(PhotoActivity.this,imgPath);
                                    }
                                }

                                @Override
                                public void onCancel(Dialog dialog) {

                                }
                            }).setGravity(Gravity.CENTER)
                            .show();
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }
    File uImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageSelect.TAKE_PHOTO:// 拍照后在这里回调
                    uImage = new File(FileUtil.saveBitmapByQuality(FileUtil.compressBySize(imgPath), 60));
                    if (uImage.length() == 0) {
                        toast("图片损坏，请重新上传");
                        return;
                    }
                    LogUtil.e("图片大小：" + uImage.length() / 1024 + "kb");
                    selectLists.add(uImage.getPath());
                    adapter.notifyDataSetChanged();
                    break;
                case ImageSelect.CHOOSE_PHOTO://选择相册的回调
                    if (data != null) {
                        imgPath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                        uImage = new File(FileUtil.saveBitmapByQuality(FileUtil.compressBySize(imgPath), 60));
                        if (uImage.length() == 0) {
                            toast("图片损坏，请重新上传");
                            return;
                        }
                        LogUtil.e("图片大小：" + uImage.length() / 1024 + "kb");
                        selectLists.add(uImage.getPath());
                        adapter.notifyDataSetChanged();
                    } else {
                        toast("图片损坏，请重新选择");
                    }
                    break;
            }
        }
    }


}
