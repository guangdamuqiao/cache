package com.github.houbb.cache.core.support.util.impl;

import com.github.houbb.cache.core.support.util.SkipNode;

import java.util.ArrayList;

/**
 * 跳表节点实现
 *
 * @param <K> 为缓存中存储的key
 * @author Celebridge
 * @since 0.0.3
 */
public class SkipNodeImpl<K> implements SkipNode<K> {

    /**
     *
     * 过期时间
     *
     * @since 0.0.3
     */
    private long key;

    /**
     * cache中对应key
     *
     * @since 0.0.3
     */
    private K value;

    /**
     * 后续节点
     *
     * @since 0.0.3
     */
    public ArrayList<SkipNodeImpl<K>> next;

    /**
    * @Description
    * @param key 过期时间
	* @param value cache中对应key
	* @param MAX_LEVEL 最大层数
    * @Author Celebridge
    * @Date 2024/6/28
    */
    public SkipNodeImpl(long key, K value, int MAX_LEVEL) {
        this.key = key;
        this.value = value;
        this.next = new ArrayList<>(MAX_LEVEL);
    }

    @Override
    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public void setValue(K value) {
        this.value = value;
    }

    @Override
    public long getKey() {
        return this.key;
    }

    @Override
    public K getValue() {
        return this.value;
    }
}
