package org.monkeyjesus.service.impl;

import org.monkeyjesus.service.CacheTestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by MonkeyJesus on 2018/2/6 0006.
 */
@Service
public class CacheTestServiceImpl implements CacheTestService {

    @Override
    @Cacheable(value = "service.getCacheNumber", key="'number'")
    public String getCacheNumber() {
        return "dfgfd";
    }
}
