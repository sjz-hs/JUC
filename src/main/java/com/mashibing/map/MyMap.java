package com.mashibing.map;




/**
 * 与1.8的区别
 *  1. 红黑树
 *  2. hash算法的简化
 *  3. 死循环的解决  （扩容  ===》 +扩容大小 即为下标）
 *  4.节点插入顺序
 *  threshold  阈值  扩容时刻
 *
 * 1.7 死锁分析以及为什么1.8解决了
 *
 *
 *
 */
public class MyMap<K, V> {

    private Entry[] table;

    private static Integer CAPCITY = 8;

    private static  Integer  SIZE=0;

    public MyMap() {
        //初始化
        this.table = new Entry[CAPCITY];
    }

    public int size() {
        return SIZE;
    }


    public Object get(Object key) {
        //下标
        int hash = key.hashCode();
        int i = hash % CAPCITY;   //取模
        //遍历节点返回覆盖之前的值
        for (Entry<K,V> entry = table[i]; entry != null; entry = entry.next) {
            //判断当前Key  循环判断
            if (entry.k.equals(key)) {
                return entry.v;
            }
        }
        return null;
    }

    public Object put(K key, V value) {
        //下标
        int hash = key.hashCode();
        int i = hash % CAPCITY;   //取模
        //遍历节点返回覆盖之前的值
        for (Entry<K,V> entry = table[i]; entry != null; entry = entry.next) {
            //判断当前Key  循环判断
            if (entry.k.equals(key)) {
                V oldValue =entry.v;
                entry.v = value;      //覆盖
                return oldValue;
            }
        }
        // 覆盖的话不进行下面的逻辑
        addEntry(key,value, i);
        //返回值处理
        return null;
    }

    private void addEntry(K key,V value,int i) {
        SIZE++;
        //头插入下移
        Entry entry = new Entry(key, value, table[i]);
        table[i] = entry;
    }


    //节点
    class Entry<K, V> {
        private K k;
        private V v;
        private Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }
    }
}


