package site.shier.sso.service.impl;

import lombok.extern.slf4j.Slf4j;
import site.shier.sso.service.CacheService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-10-31 下午7:30
 **/
@Slf4j
public class LocalCacheServiceImpl implements CacheService {

    private static Map<String, CacheEntity> cacheMap = new ConcurrentHashMap<>(512);

    private static Set<String> expireKeySet = new CopyOnWriteArraySet<>();

    private static final String SEPARATOR = "_SEPARATOR_";

    private static final Long CLEAR_TIME = 600L;

    private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

    public void init() {
        log.info("LocalCache init！");

        exec.scheduleAtFixedRate(() -> {
            log.info("================");
            log.info("开始清理过期key");
            Set<String> removeSet = new HashSet<>();
            expireKeySet.forEach(expireKey -> {
                String[] strArray = expireKey.split(SEPARATOR);
                Long expireTime = Long.parseLong(strArray[0]);
                String key = strArray[1];
                Long currentTimeMillis = System.currentTimeMillis();
                if (expireTime < currentTimeMillis) {
                    CacheEntity cacheEntity = cacheMap.get(key);
                    if (cacheEntity != null && cacheEntity.getExpireTime() != null && cacheEntity.getExpireTime() < currentTimeMillis) {
                        cacheMap.remove(key);
                    }
                    removeSet.add(expireKey);
                }
            });
            expireKeySet.removeAll(removeSet);
            log.info("清理过期key结束 ，共清理key：{} 个", removeSet.size());
        }, CLEAR_TIME, CLEAR_TIME, TimeUnit.SECONDS);

    }

    /**
     * 新增或修改缓存
     *
     * @param key
     * @param object
     * @return
     */
    @Override
    public Boolean put(String key, Object object) {
        cacheMap.put(key, new CacheEntity(object));
        return true;
    }

    /**
     * 新增或修改缓存
     *
     * @param key
     * @param object
     * @param second
     * @return
     */
    @Override
    public Boolean put(String key, Object object, Integer second) {
        Long expireTime = System.currentTimeMillis() + second * 1000;
        cacheMap.put(key, new CacheEntity(object, expireTime));
        expireKeySet.add(expireTime + SEPARATOR + key);
        return true;
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        CacheEntity cacheEntity = cacheMap.get(key);
        if (cacheEntity == null) {
            return null;
        }
        if (cacheEntity.getExpireTime() != null && cacheEntity.getExpireTime() < System.currentTimeMillis()) {
            return null;
        }
        return cacheEntity.getData();
    }

    /**
     * 获取缓存并转换为字符
     *
     * @param key
     * @return
     */
    @Override
    public String getString(String key) {
        Object object = get(key);
        return object == null ? null : String.valueOf(object);
    }

    /**
     * 获取指定类型的缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        return (T) get(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    @Override
    public Boolean remove(String key) {
        cacheMap.remove(key);
        return true;
    }

    private class CacheEntity {
        private Object data;

        private Long expireTime;

        public CacheEntity(Object data) {
            this.data = data;
        }

        public CacheEntity(Object data, Long expireTime) {
            this.data = data;
            this.expireTime = expireTime;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Long expireTime) {
            this.expireTime = expireTime;
        }
    }

}
