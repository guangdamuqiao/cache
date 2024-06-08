package com.github.houbb.cache.core.support.util;

/**
 * 跳表节点 接口
 * @author Celebridge
 * @since 0.0.13
 */

public interface SkipNode<T> {

    /**
     * 获取对应的 key 信息
     * @since 0.0.13
     */
    int getKey();

    /**
     * 获取对应的 value 信息
     * @since 0.0.13
     */
    T getValue();
}
