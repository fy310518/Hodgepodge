package com.fy.baselibrary.retrofit;

/**
 * Created by Jam on 16-8-12
 * Description:
 */
public class ServerException extends Exception{

    public int code;

    public ServerException(String msg, int code){
        super(msg);
        this.code = code;
    }


}
