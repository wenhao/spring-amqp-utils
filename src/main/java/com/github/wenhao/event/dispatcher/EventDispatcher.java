package com.github.wenhao.event.dispatcher;

public interface EventDispatcher<T> {
    void dispatch(T event);
}
