package com.github.houbb.cache.core.support.util.impl;

import com.github.houbb.cache.core.support.util.SkipList;
import com.github.houbb.cache.core.support.util.SkipNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 跳表实现
 *
 * @param <K> 为缓存中存储的key
 * @author Celebridge
 * @since 0.0.3
 */
public class SkipListImpl<K> implements SkipList<K>, Iterable<SkipNodeImpl<K>> {

    /**
     * 最大层数
     *
     * @since 0.0.3
     */
    private final int MAX_LEVEL;
    /**
     * 实际最大层数
     *
     * @since 0.0.3
     */

    private int levelCount = 1;

    /**
     * 头节点
     *
     * @since 0.0.3
     */
    private final SkipNodeImpl<K> head;

    /**
     * 跳表大小
     *
     * @since 0.0.3
     */
    private int size = 0;

    /**
    * @Description
    * @param MAX_LEVEL 最大层数
    * @Author Celebridge
    * @Date 2024/6/28
    */

    public SkipListImpl(int MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL;
        this.head = new SkipNodeImpl<K>(0, null, MAX_LEVEL);
    }
    /**
    * @Description 使用时间查询到节点并返回
    * @param key 该key非缓存中的key，而是约定的过期时间
    * @return com.github.houbb.cache.core.support.util.SkipNode<V>
    * @Author Celebridge
    * @Date 2024/6/28
    */
    public SkipNode<K> search(long key){
        SkipNodeImpl<K> p = (SkipNodeImpl<K>) head;
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.next.get(i) != null && p.next.get(i).getKey() < key){
                p = (SkipNodeImpl<K>) p.next.get(i);
            }
        }

        if(p.next.get(0) != null && p.next.get(0).getKey() == key){
            return p.next.get(0);    // 找到，则返回原始链表中的结点
        }else{
            return null;
        }
    }

    /**
    * @Description
    * @param key 过期时间
	* @param value 对应的cache中的key值
    * @Author Celebridge
    * @Date 2024/6/28
    */
    public void insert(long key, K value){
        //随机选择层数
        int level = randomLevel();
        SkipNodeImpl<K> newNode = new SkipNodeImpl<>(key, value, this.MAX_LEVEL);// 通过随机函数改变索引层的结点布置
        ArrayList<SkipNodeImpl<K>> update = new ArrayList<>();

        SkipNodeImpl<K> p = head;
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
        //更新实际最大层数
        if(levelCount < level){
            levelCount = level;
        }
        size++;
    }

    /**
    * @Description
    * @param key 过期时间
    * @Author Celebridge
    * @Date 2024/6/28
    */
    public void delete(long key){
        ArrayList<SkipNodeImpl<K>> update = new ArrayList<>(levelCount);
        SkipNodeImpl<K> p = head;
        //将每一层的待删除节点的前一个节点收集，哪怕后续节点不是待删除节点
        for(int i = levelCount - 1; i >= 0; --i){
            while(p.next.get(i) != null && p.next.get(i).getKey() < key){
                p = p.next.get(i);
            }
            update.add(p);
        }

        if(p.next.get(0) != null && p.next.get(0).getKey() == key){
            for(int i = levelCount - 1; i >= 0; --i){
                //指向待删除节点情况
                if(update.get(i).next.get(i) != null && update.get(i).next.get(i).getKey() == key){
                    update.get(i).next.set(i, update.get(i).next.get(i).next.get(i));
                }
            }
            size--;
        }
    }

    /**
    * @Description  返回跳表大小
    * @return int
    * @Author Celebridge
    * @Date 2024/6/28
    */
    @Override
    public int size(){
        return this.size;
    }

    /**
    * @Description 随机返回 1 到 MAX_LEVEL之间的整数
    * @return int
    * @Author Celebridge
    * @Date 2024/6/28
    */
    private int randomLevel(){
        return (int) (Math.random() * MAX_LEVEL) + 1;
    }

    //TODO 迭代器实现后删除
    public SkipNodeImpl<K> getHead() {
        return head;
    }

    @Override
    public Iterator<SkipNodeImpl<K>> iterator() {
        return new Iterator<SkipNodeImpl<K>>() {
            SkipNodeImpl<K> indexNode = head.next.get(0);
            @Override
            public boolean hasNext() {
                return indexNode != null;
            }
            @Override
            public SkipNodeImpl<K> next() {
                SkipNodeImpl<K> res = indexNode;
                indexNode = indexNode.next.get(0);
                return res;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}