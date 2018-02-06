package org.monkeyjesus.manager;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.Callable;

/**
 * Created by MonkeyJesus on 2018/2/6 0006.
 */
public class SystemRedisCache implements Cache {

    /**
     * Redis
     */
    private RedisTemplate<String, Object> redisTemplate;

    private String name;

    private int timeOut;

    public String getName() {
        return this.name;
    }

    public Object getNativeCache() {
        return null;
    }

    public ValueWrapper get(Object key) {
        // TODO Auto-generated method stub
        final String keyf = (String) key;
        Object object = null;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {

                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return toObject(value);

            }
        });
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    public void put(final Object o, final Object o1) {
        redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            redisConnection.set(o.toString().getBytes(), o1.toString().getBytes());
            return true;
        });
    }

    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    public void evict(Object key) {

        // TODO Auto-generated method stub
        final String keyf = (String) key;
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.del(keyf.getBytes());
            }
        });

    }

    public void clear() {
        // TODO Auto-generated method stub
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
