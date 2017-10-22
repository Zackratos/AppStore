package org.zackratos.appstore.http;

import org.zackratos.appstore.main.recommend.IndexData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
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

}
