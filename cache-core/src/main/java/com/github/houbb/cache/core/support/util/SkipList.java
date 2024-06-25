package com.github.houbb.cache.core.support.util;

/**
 * 跳表 接口
 * @author Celebridge
 * @since 0.0.13
 */
public interface SkipList<V> {

    /**
     * 查询 key 的信息
     * @param key 信息
     * @return 节点对象
     * @since 0.0.13
     */
    SkipNode<V> search(int key);


    /**
     * 删除 key 节点
     * @param key 信息
     * @since 0.0.13
     */
    void delete(int key);

    /**
     * 添加 node 节点
     * @param value 信息
     * @since 0.0.13
     */
    void insert(int key, V value);
}
