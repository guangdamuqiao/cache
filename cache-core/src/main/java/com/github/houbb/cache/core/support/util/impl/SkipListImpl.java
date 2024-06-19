package com.github.houbb.cache.core.support.util.impl;

import com.github.houbb.cache.core.support.util.SkipList;
import com.github.houbb.cache.core.support.util.SkipNode;

public class SkipListImpl<T> implements SkipList<T> {
    @Override
    public SkipNode<T> search(int key) {
        return null;
    }

    @Override
    public SkipNode<T> searchBeforeKey(int key) {
        return null;
    }

    @Override
    public void delete(int key) {

    }

    @Override
    public void add(SkipNode<T> node) {

    }
}
