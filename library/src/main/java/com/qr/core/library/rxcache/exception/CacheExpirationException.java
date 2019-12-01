package com.qr.core.library.rxcache.exception;

public class CacheExpirationException extends RuntimeException{
    private static final long serialVersionUID = 1954736501779775766L;
    public CacheExpirationException(String msg){
        super(msg);
    }
}
