package com.github.houbb.cache.core.support.util.impl;

import com.github.houbb.cache.core.support.util.SkipNode;

import java.util.ArrayList;

public class SkipNodeImpl<V> implements SkipNode<V> {

    private int key;

    private V value;

    public ArrayList<SkipNodeImpl<V>> next;
    public SkipNodeImpl(int key, V value, int MAX_LEVEL) {
        this.key = key;
        this.value = value;
        this.next = new ArrayList<>(MAX_LEVEL);
    }

    @Override
    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
