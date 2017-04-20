package me.weyye.todaynews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.weyye.todaynews.R;
import me.weyye.todaynews.base.BaseMvpFragment;
import me.weyye.todaynews.model.News;
import me.weyye.todaynews.presenter.NewsListPresenter;
import me.weyye.todaynews.ui.activity.NewsDetailActivity;
import me.weyye.todaynews.ui.activity.VideoDetailActivity;
import me.weyye.todaynews.ui.adapter.NewsAdapter;
import me.weyye.todaynews.ui.view.LoadingFlashView;
import me.weyye.todaynews.utils.ConstanceValue;
import me.weyye.todaynews.view.INewsListView;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class NewsListFragment extends BaseMvpFragment<NewsListPresenter> implements INewsListView {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.loadingView)
    LoadingFlashView loadingView;
    private String mTitleCode = "";
    protected List<News> mDatas = new ArrayList<>();
    protected BaseQuickAdapter mAdapter;

    @Override
    protected NewsListPresenter createPresenter() {
        return new NewsListPresenter(this);
    }

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.layout_recyclerview, null);
        ButterKnife.bind(this, v);
        return v;
    }

    public static NewsListFragment newInstance(String code) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstanceValue.DATA, code);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void bindViews(View view) {
    }

    @Override
    protected void processLogic() {
        initCommonRecyclerView(createAdapter(), null);
        mTitleCode = getArguments().getString(ConstanceValue.DATA);
//        srl.measure(0, 0);
//        srl.setRefreshing(true);
    }

    protected BaseQuickAdapter createAdapter() {
        return mAdapter = new NewsAdapter(mDatas);
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (TextUtils.isEmpty(mTitleCode))
            mTitleCode = getArguments().getString(ConstanceValue.DATA);
        if (mvpPresenter.mvpView == null)
            mvpPresenter = createPresenter();
        getData();
    }

    private void getData() {
        if (mDatas.size() == 0) {

            //没加载过数据
            if (loadingView == null) loadingView = get(R.id.loadingView);
            loadingView.setVisibility(View.VISIBLE);
            loadingView.showLoading();
        }
        mvpPresenter.getNewsList(mTitleCode);
    }

    @Override
    protected void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                News news = mDatas.get(i);
                if (news.article_genre.equals(ConstanceValue.ARTICLE_GENRE_VIDEO)) {
                    //视频
                    Intent intent = new Intent(mContext, VideoDetailActivity.class);
                    intent.putExtra(ConstanceValue.URL, "http://m.toutiao.com".concat(news.source_url));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(ConstanceValue.URL, "http://m.toutiao.com".concat(news.source_url));
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onGetNewsListSuccess(List<News> response) {
        //由于最后一条重复 ，删除掉
        if (response.size() > 0) {
            response.remove(response.size() - 1);
            loadingView.setVisibility(View.GONE);
        }
        srl.setRefreshing(false);
        mDatas.addAll(0, response);
        mAdapter.notifyItemRangeChanged(0, response.size());
    }

    @Override
    public void onError() {
        srl.setRefreshing(false);
    }
}
