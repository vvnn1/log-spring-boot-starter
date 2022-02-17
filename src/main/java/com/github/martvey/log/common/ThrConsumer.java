package com.github.martvey.log.common;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 16:53
 */
public interface ThrConsumer<T,U,P> {
    void accept(T t, U u, P p);
}
