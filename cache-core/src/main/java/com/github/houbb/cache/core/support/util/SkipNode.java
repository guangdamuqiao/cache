package com.github.houbb.cache.core.support.util;

import java.util.ArrayList;

/**
 * 跳表节点 接口
 * @author Celebridge
 * @since 0.0.13
 */

public interface SkipNode<V> {
    /**
     * 获取对应的 key 信息
     * @since 0.0.13
     */
    void setKey(int key);
    /**
     * 设置对应的 value 信息
     * @since 0.0.13
     */
    void setValue(V value);

    /**
     * 获取对应的 key 信息
     * @since 0.0.13
     */
    long getKey();

    /**
     * 获取对应的 value 信息
     * @since 0.0.13
     */
    V getValue();
}
