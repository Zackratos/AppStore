package org.zackratos.appstore.result;

import java.util.List;

/**
 *
 * Created by xiboke on 2017/10/23.
 */

public class PageBean<T> {

    private boolean hasMore;

    private List<T> datas;

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public List<T> getDatas() {
        return datas;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}
