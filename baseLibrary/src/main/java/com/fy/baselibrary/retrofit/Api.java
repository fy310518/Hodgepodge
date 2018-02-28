package com.fy.baselibrary.retrofit;

/**
 * api
 * Created by fangs on 2017/5/15.
 */
public interface Api {

    int DEFAULT_MILLISECONDS = 60000;             //默认的超时时间


    String BASE_URL = "http://192.168.100.158:80/";    //测试 地址
    String IMG_BASE_URL = BASE_URL + "image";           //图片 地址


//    String BASE_URL = "http://116.196.95.169:8080/";    //正式 地址
//    String IMG_BASE_URL = BASE_URL + "image";           //正式图片 地址

}
