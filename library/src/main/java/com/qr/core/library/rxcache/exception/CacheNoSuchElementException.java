package com.qr.core.library.rxcache.exception;

public class CacheNoSuchElementException extends RuntimeException{
    private static final long serialVersionUID = 9132049709369273070L;

    public CacheNoSuchElementException(){
        super();
    }
    public CacheNoSuchElementException(String msg){
        super(msg);
    }
}
