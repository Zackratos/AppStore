package org.zackratos.appstore.mvp;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/10/15.
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface PresenterScope {
}
