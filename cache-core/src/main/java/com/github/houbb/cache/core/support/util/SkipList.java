package com.github.houbb.cache.core.support.util;

/**
 * 跳表 接口
 * @author Celebridge
 * @since 0.0.13
 */
public interface SkipList<K> {

    /**
     * 查询 key 的信息
     * @param key 信息
     * @return 节点对象
     * @since 0.0.13
     */
    SkipNode<K> search(long key);


    /**
     * 删除 key 节点
     * @param key 信息
     * @since 0.0.13
     */
    void delete(long key);

    /**
     * 添加 node 节点
     * @param value 信息
     * @since 0.0.13
     */
    void insert(long key, K value);

    /**
     * 返回跳表大小
     * @return int
     */
    int size();
}
