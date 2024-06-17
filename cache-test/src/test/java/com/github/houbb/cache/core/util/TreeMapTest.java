package com.github.houbb.cache.core.util;

import java.util.EmptyStackException;
import java.util.TreeMap;

public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap<Node, Integer> treeMap = new TreeMap<>((Node a, Node b) -> {
            if(a.hashCode == b.hashCode) return 0;
            return a.time - b.time;
        });
        Node a = new Node(1, 1);
        Node b = new Node(2, 2);
        System.out.println(treeMap.get(new Node(1, 3)));
    }
}
