package org.zackratos.appstore;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class RxBus {
    // 主题
    private final Subject<Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始 Observable 的数据发射给观察者
    private RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final RxBus sInstance = new RxBus();
    }


    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserbable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
