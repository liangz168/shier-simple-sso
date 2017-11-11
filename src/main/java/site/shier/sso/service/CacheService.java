package site.shier.sso.service;

public interface CacheService {

    /**
     * 新增或修改缓存
     *
     * @param key
     * @param object
     * @return
     */
    Boolean put(String key, Object object);

    /**
     * 新增或修改缓存
     *
     * @param key
     * @param object
     * @return
     */
    Boolean put(String key, Object object, Integer second);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取缓存并转换为字符
     *
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 获取指定类型的缓存
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    Boolean remove(String key);

}
