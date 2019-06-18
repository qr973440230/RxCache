package com.qr.core.library.rxcache.entity;

import com.qr.core.library.rxcache.enums.Source;

public class Relay<T> {
    private final T data;
    private final Source source;
    private final boolean isEncrypted;

    public Relay(T data, Source source, boolean isEncrypted) {
        this.data = data;
        this.source = source;
        this.isEncrypted = isEncrypted;
    }

    public T getData() {
        return data;
    }

    public Source getSource() {
        return source;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }
}
