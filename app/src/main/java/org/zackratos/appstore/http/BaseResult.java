package org.zackratos.appstore.http;

/**
 *
 * Created by Administrator on 2017/10/22.
 */

public class BaseResult<T> {

    private int status;

    private String message;

    private T data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
