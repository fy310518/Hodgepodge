package com.fy.baselibrary.retrofit;

import com.fy.baselibrary.entity.HomeBean;
import com.fy.baselibrary.entity.LoginBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * 通用的 api接口 </p>
 * Created by fangs on 2017/8/28.
 */
public interface ApiService {

    /**
     * 默认的超时时间
     */
    int DEFAULT_MILLISECONDS = 60000;

    /**
     * 服务器地址
     */
    String BASE_URL = "http://192.168.100.158:80/";

    /**
     * 图片 地址 (可选)
     */
    String IMG_BASE_URL = BASE_URL + "image";


    /**
     * 多图片上传
     *
     * @param token
     * @return
     */
    @Multipart
    @Headers({"url_name:user"})
    @POST("file/uploadImages")
//    @POST("http://192.168.100.123/hfs/")
    Observable<BeanModule<String>> uploadPostFile(@Part("token") RequestBody token,
                                                  @Part("type") RequestBody type,
                                                  @Part List<MultipartBody.Part> files);



    /**
     * 登录接口
     */
    @FormUrlEncoded
    @Headers({"url_name:user"})
    @POST("sys/loginToApp")
    Observable<BeanModule<LoginBean>> loginToApp(@FieldMap Map<String, Object> options);

    /**
     * 首页信息
     */
    @Headers({"url_name:user"})
    @GET("student/getHome")
    Observable<BeanModule<HomeBean>> getHome(@QueryMap Map<String, Object> options);



}
