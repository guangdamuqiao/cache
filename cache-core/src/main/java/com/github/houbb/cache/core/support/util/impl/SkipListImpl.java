package com.github.houbb.cache.core.support.util.impl;

import com.github.houbb.cache.core.support.util.SkipList;
import com.github.houbb.cache.core.support.util.SkipNode;
import com.github.houbb.cache.core.support.util.impl.SkipNodeImpl;

import java.util.ArrayList;
import java.util.Random;

// 跳表中存储的是正整数，并且存储的数据是不重复的
public class SkipListImpl<V> implements SkipList<V> {



    private final int MAX_LEVEL;    // 结点的个数

    private int levelCount = 1;   // 索引的层级数

    private SkipNodeImpl<V> head;    // 头结点

    private Random random = new Random();

    public SkipListImpl(int MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL;
        this.head = new SkipNodeImpl<V>(0, null, MAX_LEVEL);
    }
    // 查找操作
    public SkipNode<V> search(int key){
        SkipNodeImpl<V> p = (SkipNodeImpl<V>) head;
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.next.get(i) != null && p.next.get(i).getKey() < key){
                p = (SkipNodeImpl<V>) p.next.get(i);
            }
        }

        if(p.next.get(0) != null && p.next.get(0).getKey() == key){
            return p.next.get(0);    // 找到，则返回原始链表中的结点
        }else{
            return null;
        }
    }

    // 插入操作
    public void insert(int key, V value){
        int level = randomLevel();
        SkipNodeImpl<V> newNode = new SkipNodeImpl<>(key, value, this.MAX_LEVEL);// 通过随机函数改变索引层的结点布置
        ArrayList<SkipNodeImpl<V>> update = new ArrayList<>();

        SkipNodeImpl<V> p = head;
        for(int i = level - 1; i >= 0; --i){
            while(p.next.get(i) != null && p.next.get(i).getKey() < key){
                p = p.next.get(i);
            }
            update.add(p);
        }

        for(int i = 0; i < level; ++i){
            newNode.next.add(update.get(i).next.get(i));
            update.get(i).next.set(i, newNode);
        }
        if(levelCount < level){
            levelCount = level;
        }
    }

    // 删除操作
    public void delete(int key){
        ArrayList<SkipNodeImpl<V>> update = new ArrayList<>(levelCount);
        SkipNodeImpl<V> p = head;
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.next.get(i) != null && p.next.get(i).getKey() < key){
                p = p.next.get(i);
            }
            update.add(p);
        }

        if(p.next.get(0) != null && p.next.get(0).getKey() == key){
            for(int i = levelCount - 1; i >= 0; --i){
                if(update.get(i).next.get(i) != null && update.get(i).next.get(i).getKey() == key){
                    update.get(i).next.set(i, update.get(i).next.get(i).next.get(i));
                }
            }
        }
    }

    // 随机函数
    private int randomLevel(){
        int level = 1;
        for(int i = 1; i < MAX_LEVEL; ++i){
            if(random.nextInt() % 2 == 1){
                level++;
            }
        }

        return level;
    }
}