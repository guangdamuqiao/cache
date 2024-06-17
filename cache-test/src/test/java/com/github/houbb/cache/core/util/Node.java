package com.github.houbb.cache.core.util;

public class Node {
    int hashCode;
    int time;
    public Node(int hashCode, int time) {
        this.hashCode = hashCode;
        this.time = time;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
