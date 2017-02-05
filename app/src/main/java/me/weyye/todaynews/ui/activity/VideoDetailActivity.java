package me.weyye.todaynews.ui.activity;

import me.weyye.todaynews.R;
import me.weyye.todaynews.model.CommentList;
import me.weyye.todaynews.presenter.VideoDetailPresenter;
import me.weyye.todaynews.view.IVideoDetailView;

/**
 * Created by Administrator on 2016/11/24 0024.
 */

public class VideoDetailActivity extends BaseNewsActivity<VideoDetailPresenter> implements IVideoDetailView {

    @Override
    protected VideoDetailPresenter createPresenter() {
        return new VideoDetailPresenter(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_video_detail);
        super.loadViewLayout();
    }




    @Override
    public void onGetCommentSuccess(CommentList response) {
        getCommentSuccess(response);
    }
}
