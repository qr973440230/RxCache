package com.qr.core.library.rxcache.entity;

import com.qr.core.library.rxcache.enums.Source;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public final class Record <T>{
    // 数据源
    private Source source;
    // 数据
    private final T data;
    // 存活时间
    private long survivalTime;
    // 保存时的时间
    private final long persistedTime;
    private final String dataClassName;
    private final String dataCollectionClassName;
    private final String dataKeyMapClassName;

    public Record(T data,long survivalTime){
        this.data = data;
        this.survivalTime = survivalTime;
        this.persistedTime = System.currentTimeMillis();
        this.source = Source.MEMORY;

        boolean isList = Collection.class.isAssignableFrom(data.getClass());
        boolean isArray = data.getClass().isArray();
        boolean isMap = Map.class.isAssignableFrom(data.getClass());

        if(isList){
            dataKeyMapClassName = null;
            List list = (List)data;
            if(list.size() > 0){
                dataCollectionClassName = List.class.getName();
                dataClassName = list.get(0).getClass().getName();
            }else{
                dataCollectionClassName = null;
                dataClassName = null;
            }
        }else if(isArray){
            dataKeyMapClassName = null;
            Object[] array = (Object[]) data;
            if(array.length > 0){
                dataCollectionClassName = data.getClass().getName();
                dataClassName = array[0].getClass().getName();
            }else{
                dataCollectionClassName = null;
                dataClassName = null;
            }
        }else if(isMap){
            Map map = (Map) data;
            if(map.size() > 0){
                dataCollectionClassName = Map.class.getName();

                final Iterator<Map.Entry> iterator = ((Set<Map.Entry>)map.keySet()).iterator();
                Map.Entry firstEntry = iterator.next();
                Class valueClass = firstEntry.getValue().getClass();
                Class keyClass = firstEntry.getKey().getClass();

                while (iterator.hasNext() || valueClass == null && keyClass == null){
                    final Map.Entry next = iterator.next();
                    if(keyClass != null && keyClass != next.getKey().getClass()) keyClass = null;
                    if(valueClass != null && valueClass != next.getValue().getClass()) valueClass = null;
                }

                dataClassName = valueClass != null ? valueClass.getName() : null;
                dataKeyMapClassName = keyClass != null ? keyClass.getName() : null;

            }else{
                dataCollectionClassName = null;
                dataKeyMapClassName = null;
                dataClassName = null;
            }
        }else{
            dataCollectionClassName = null;
            dataKeyMapClassName = null;
            dataClassName = data.getClass().getName();
        }
    }

    public Source getSource() {
        return source;
    }

    public T getData() {
        return data;
    }

    public long getPersistedTime() {
        return persistedTime;
    }

    public String getDataClassName() {
        return dataClassName;
    }

    public String getDataCollectionClassName() {
        return dataCollectionClassName;
    }

    public String getDataKeyMapClassName() {
        return dataKeyMapClassName;
    }

    public long getSurvivalTime() {
        return survivalTime;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setSurvivalTime(long survivalTime) {
        this.survivalTime = survivalTime;
    }
}
