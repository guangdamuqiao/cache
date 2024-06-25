package com.github.houbb.cache.core.support.expire;

import com.github.houbb.cache.api.ICache;
import com.github.houbb.cache.api.ICacheExpire;
import com.github.houbb.cache.api.ICacheRemoveListener;
import com.github.houbb.cache.api.ICacheRemoveListenerContext;
import com.github.houbb.cache.core.constant.enums.CacheRemoveType;
import com.github.houbb.cache.core.support.listener.remove.CacheRemoveListenerContext;
import com.github.houbb.cache.core.support.util.SkipList;
import com.github.houbb.cache.core.support.util.SkipNode;
import com.github.houbb.cache.core.support.util.impl.SkipListImpl;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缓存过期-跳表实现策略
 *
 * 优点：定时删除时不用做过多消耗，支持惰性删除
 * 实现方法：将expireMap的value作为跳表节点，跳表支持自定义层数用于平衡空间和时间，通过key可以直接拿到跳表节点，
 * 删除可直接更改指针到待删除链表中惰性删除
 *
 * @author Celebridge
 * @since 0.0.3
 * @param <K> key
 * @param <V> value
 */
public class CacheExpireSortBySkipList<K,V> implements ICacheExpire<K,V> {

    /**
     * 单次清空的数量限制
     *
     * @since 0.0.3
     */
    private static final int LIMIT = 100;

    /**
     * 过期 map
     * value存储链表节点
     *
     * @since 0.0.3
     */
    private final Map<K, SkipNode<V>> expireMap = new HashMap<>();

    /**
     * 跳表用于按过期时间存储节点，快速定位添加或删除节点
     *
     * @since 0.0.3
     */
    private final SkipList<V> skipList = new SkipListImpl<>(16);

    /**
     * 缓存实现
     *
     * @since 0.0.3
     */
    private final ICache<K, V> cache;

    /**
     * 线程执行类
     *
     * @since 0.0.3
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public CacheExpireSortBySkipList(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     *
     * @since 0.0.3
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * 定时执行任务
     *
     * @since 0.0.3
     */
    private class ExpireThread implements Runnable {
        @Override
        public void run() {
            //TODO 从表头开始便利链表执行过期
        }
    }

    @Override
    public void expire(K key, long expireAt) {
        //TODO 使用key和exprieAt创建链表节点，插入链表中并放入expireMap
        SkipNode<V> node = null;
        expireMap.put(key, node);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if (CollectionUtil.isEmpty(keyList)) {
            return;
        }
        //TODO 直接从表头开始遍历并过期
    }

    @Override
    public Long expireTime(K key) {
        //TODO 返回过期时间
        return 0L;
    }

    /**
     * 过期处理 key
     *
     * @param key      key
     * @param expireAt 过期时间
     * @since 0.0.3
     */
    private void expireKey(final K key, final Long expireAt) {
        if (expireAt == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime >= expireAt) {
            expireMap.remove(key);
            // 再移除缓存，后续可以通过惰性删除做补偿
            V removeValue = cache.remove(key);

            // 执行淘汰监听器
            ICacheRemoveListenerContext<K, V> removeListenerContext = CacheRemoveListenerContext.<K, V>newInstance().key(key).value(removeValue).type(CacheRemoveType.EXPIRE.code());
            for (ICacheRemoveListener<K, V> listener : cache.removeListeners()) {
                listener.listen(removeListenerContext);
            }
        }
    }
}

