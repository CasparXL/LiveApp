package com.jlkf.text.textapp.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jlkf.text.textapp.R;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能：视频播放器
 * 依赖来源：https://github.com/CarGuo/GSYVideoPlayer
 * 本项目中添加该功能所对应的依赖：implementation 'com.shuyu:GSYVideoPlayer:6.0.1'
 */
public class AudioPlayActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> {

    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer detailPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);
        ButterKnife.bind(this);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initVideoBuilderMode();
    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //mVideoPath = "rtmp://pili-live-rtmp.jlkfapp.com/jlkf-live/test"; //拉流端地址 直播地址
        //mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"; //拉流端地址CCTV 1
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        loadCover(imageView, "http://mpj.jlkfapp.com/data/upload/1/201811/2219285908872646.png");
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8")
                .setCacheWithPlay(true)
                .setVideoTitle(" ")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.ic_launcher)
                                .placeholder(R.mipmap.ic_launcher))
                .load(url)
                .into(imageView);
    }
}
