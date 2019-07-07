package com.qr.core.library.rxcache.cache;

import com.qr.core.library.rxcache.enums.Source;

public final class Reply<T>{
    private final T data;
    private final Source source;

    public Reply(T data, Source source) {
        this.data = data;
        this.source = source;
    }

    public T getData() {
        return data;
    }

    public Source getSource() {
        return source;
    }
}
