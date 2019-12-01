package com.qr.core.library.rxcache.cache.persistence;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.qr.core.library.rxcache.cache.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DiskPersistence implements Persistence {
    private final File cacheDirectory;
    private final ParserConfig parserConfig;

    @Inject
    public DiskPersistence(File cacheDirectory, ParserConfig parserConfig) {
        this.cacheDirectory = cacheDirectory;
        this.parserConfig = parserConfig;
    }

    @Override
    public void saveRecord(String key, Record record) {
        key = safetyKey(key);
        String jsonString = JSON.toJSONString(record, SerializerFeature.WriteClassName);

        FileWriter fileWriter = null;
        try {
            File file = new File(cacheDirectory, key);
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> Record<T> retrieveRecord(String key) {
        key = safetyKey(key);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(cacheDirectory, key));
            return JSON.parseObject(inputStream, IOUtils.UTF8,
                    new TypeReference<Record<T>>() {
                    }.getType(),
                    parserConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public void evict(String key) {
        key = safetyKey(key);
        final File file = new File(cacheDirectory, key);
        file.delete();
    }

    @Override
    public void evictAll() {
        File[] files = cacheDirectory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file != null) {
                file.delete();
            }
        }
    }

    @Override
    public List<String> allKeys() {
        List<String> keys = new ArrayList<>();
        File[] files = cacheDirectory.listFiles();
        if (files == null) {
            return keys;
        }

        for (File file : files) {
            if (file.isFile()) {
                keys.add(file.getName());
            }
        }

        return keys;
    }

    @Override
    public int storedMB() {
        long bytes = 0;
        File[] files = cacheDirectory.listFiles();
        if (files == null) {
            return 0;
        }
        for (File file : files) {
            bytes += file.length();
        }

        double mbs = Math.ceil((double) bytes / 1024 / 1024);
        return (int) mbs;
    }


    private String safetyKey(String key) {
        return key.replaceAll("/", "_");
    }
}

