package com.github.houbb.cache.core.support.expire;

import com.github.houbb.cache.api.ICacheExpire;

import java.util.Collection;

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

    @Override
    public void expire(K key, long expireAt) {

    }

    @Override
    public void refreshExpire(Collection<K> keyList) {

    }

    @Override
    public Long expireTime(K key) {
        return null;
    }
}
