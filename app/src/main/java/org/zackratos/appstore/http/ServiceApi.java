package org.zackratos.appstore.http;

import org.zackratos.appstore.login.LoginParams;
import org.zackratos.appstore.result.AppInfo;
import org.zackratos.appstore.result.BaseResult;
import org.zackratos.appstore.result.Category;
import org.zackratos.appstore.result.DownloadInfo;
import org.zackratos.appstore.result.IndexData;
import org.zackratos.appstore.result.LoginResult;
import org.zackratos.appstore.result.PageBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * Created by Administrator on 2017/10/18.
 */

public interface ServiceApi {

    String BASE_URL = "http://112.124.22.238:8081/course_api/cniaoplay/";

    @GET("index")
    Call<ResponseBody> index(@Query("p") String params);

    @GET("index")
    Observable<BaseResult<IndexData>> rxIndex(@Query("p") String params);

    @GET("toplist")
    Call<ResponseBody> topList(@Query("p") String params);

    @GET("toplist")
    Observable<BaseResult<PageBean<AppInfo>>> rxTopList(@Query("p") String params);

    @POST("login")
    Call<ResponseBody> login(@Body LoginParams params);

    @POST("login")
    Observable<BaseResult<LoginResult>> rxLogin(@Body LoginParams params);

    @GET("game")
    Call<ResponseBody> game(@Query("p") String params);

    @GET("game")
    Observable<BaseResult<PageBean<AppInfo>>> rxGame(@Query("p") String params);

    @GET("category")
    Call<ResponseBody> category(@Query("p") String params);

    @GET("category")
    Observable<BaseResult<List<Category>>> rxCategroy(@Query("p") String params);

    @GET("category/featured/{category_id}")
    Call<ResponseBody> featured(@Path("category_id") int id, @Query("p") String params);

    @GET("category/featured/{category_id}")
    Observable<BaseResult<PageBean<AppInfo>>> rxFeatured(@Path("category_id") int id, @Query("p") String params);

    @GET("app/{id}")
    Call<ResponseBody> app(@Path("id") int id, @Query("p") String params);

    @GET("download/{id}")
    Call<ResponseBody> download(@Path("id") int id, @Query("p") String params);

    @GET("download/{id}")
    Observable<BaseResult<DownloadInfo>> rxDownload(@Path("id") int id, @Query("p") String params);

    @GET("apps/updateinfo")
    Call<ResponseBody> updateInfo(@Query("p") String params);

    @GET("apps/updateinfo")
    Observable<BaseResult<List<AppInfo>>> rxUdateInfo(@Query("p") String params);

}
