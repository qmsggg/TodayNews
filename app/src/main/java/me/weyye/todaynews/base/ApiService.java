package me.weyye.todaynews.base;

import java.util.List;

import me.weyye.todaynews.model.CommentList;
import me.weyye.todaynews.model.News;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
public interface ApiService {
    //baseUrl
//    String API_SERVER_URL = "http://192.168.0.116:88/";
    String HOST = "http://www.toutiao.com/";
    String API_SERVER_URL = HOST + "api/";

    String URL_ARTICLE_FEED = "article/feed/";
    String URL_COMMENT_LIST = "comment/list/";


    /**
     * 获取新闻数据列表
     */
    @GET(URL_ARTICLE_FEED + "?utm_source=toutiao&widen=1&max_behot_time_tmp=0&as=A1C528E25E76FB8&cp=582EC64FEBD84E1&max_behot_time=0")
    Observable<ResultResponse<List<News>>> getNews(@Query("category") String category);

    /**
     * 获取评论数据
     *
     * @param group_id
     * @param item_id
     * @param offset
     * @param count
     * @return
     */
    @GET(URL_COMMENT_LIST)
    Observable<ResultResponse<CommentList>> getComment(@Query("group_id") String group_id, @Query("item_id") String item_id, @Query("offset") String offset, @Query("count") String count);
}
