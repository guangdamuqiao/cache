package com.github.houbb.cache.core.support.util;

/**
 * 跳表 接口
 * @author Celebridge
 * @since 0.0.13
 */
public interface SkipList<T> {

    /**
     * 查询 key 的信息
     * @param key 信息
     * @return 节点对象
     * @since 0.0.13
     */
    SkipNode<T> search(int key);

    /**
     * 查询对应 key 前一个节点的信息
     * @param key 信息
     * @return 节点对象
     * @since 0.0.13
     */
    SkipNode<T> searchBeforeKey(int key);

    /**
     * 删除 key 节点
     * @param key 信息
     * @since 0.0.13
     */
    void delete(int key);

    /**
     * 添加 node 节点
     * @param node 信息
     * @since 0.0.13
     */
    void add(SkipNode<T> node);
}
