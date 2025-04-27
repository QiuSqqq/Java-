package com.qius.exit2;

import java.util.NoSuchElementException;

/**
 * 问题18：为前面类的队列类提供一个 迭代器 （iterator）——一个依次产生队列元素的对象。
 * 将Iterator定义为一个嵌套类，该类有next和hasNext方法。此外，还要提供Queue类的
 * iterator()方法，该方法生成Queue.Iterator。那么Iterator是否应该是静态的？
 */
public class exit2_18 {
    public static void main(String[] args) {
        System.out.println("问题18：为Queue类提供Iterator嵌套类");
        System.out.println("---------------------------------");
        
        // 创建一个队列并添加元素
        StringQueue queue = new StringQueue();
        queue.add("Java");
        queue.add("Python");
        queue.add("C++");
        queue.add("JavaScript");
        
        System.out.println("队列内容: " + queue);
        
        // 使用迭代器遍历队列
        System.out.println("\n1. 使用迭代器遍历队列：");
        StringQueue.Iterator iterator = queue.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println("元素: " + element);
        }
        
        // 测试空队列的迭代器
        System.out.println("\n2. 测试空队列的迭代器：");
        StringQueue emptyQueue = new StringQueue();
        StringQueue.Iterator emptyIterator = emptyQueue.iterator();
        System.out.println("空队列迭代器hasNext(): " + emptyIterator.hasNext());
        
        // 测试next()异常
        System.out.println("\n3. 测试迭代器next()方法在没有更多元素时的行为：");
        try {
            System.out.println("尝试在空队列上调用next()...");
            emptyIterator.next();
        } catch (NoSuchElementException e) {
            System.out.println("捕获到预期的异常: " + e.getClass().getName());
            System.out.println("异常消息: " + e.getMessage());
        }
        
        // 讨论Iterator是否应该是静态的
        System.out.println("\n4. 关于Iterator是否应该是静态的讨论：");
        System.out.println("在这个实现中，Iterator应该是非静态的（内部类），原因如下：");
        System.out.println("   a) 迭代器需要访问外部类Queue的实例变量（head等）");
        System.out.println("   b) 每个迭代器实例与特定的队列实例关联");
        System.out.println("   c) 迭代器需要跟踪队列的状态以实现遍历");
        
        System.out.println("\n如果Iterator是静态嵌套类：");
        System.out.println("   a) 必须显式传递Queue引用，增加了不必要的复杂性");
        System.out.println("   b) 失去了内部类自动访问外部类实例的便利性");
        System.out.println("   c) 需要更多的代码来维护队列与迭代器的关系");
        
        System.out.println("\n总结：");
        System.out.println("1. 当嵌套类需要访问外部类的实例变量或方法时，应该使用非静态内部类");
        System.out.println("2. Iterator与特定Queue实例的生命周期紧密相关，适合作为非静态内部类");
        System.out.println("3. Java标准库中的集合类迭代器也是作为非静态内部类实现的");
    }
}

/**
 * 字符串队列实现，支持迭代器。
 */
class StringQueue {
    // 队列的头节点
    private Node head;
    
    // 队列的尾节点
    private Node tail;
    
    // 队列中的元素数量
    private int size;
    
    /**
     * 创建一个空队列
     */
    public StringQueue() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * 将元素添加到队列尾部
     * 
     * @param element 要添加的字符串元素
     */
    public void add(String element) {
        Node newNode = new Node(element);
        
        if (tail == null) {
            // 队列为空
            head = newNode;
            tail = newNode;
        } else {
            // 添加到尾部
            tail.next = newNode;
            tail = newNode;
        }
        
        size++;
    }
    
    /**
     * 从队列头部移除并返回元素
     * 
     * @return 队列头部的元素，如果队列为空则返回null
     */
    public String remove() {
        if (head == null) {
            return null;
        }
        
        String element = head.value;
        head = head.next;
        
        if (head == null) {
            tail = null;
        }
        
        size--;
        return element;
    }
    
    /**
     * 获取队列中元素的数量
     * 
     * @return 队列中的元素数量
     */
    public int size() {
        return size;
    }
    
    /**
     * 判断队列是否为空
     * 
     * @return 如果队列为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * 创建并返回此队列的迭代器
     * 
     * @return 一个可以遍历队列元素的迭代器
     */
    public Iterator iterator() {
        return new Iterator();
    }
    
    /**
     * 返回队列的字符串表示
     * 
     * @return 队列的字符串表示，格式为"[元素1, 元素2, ...]"
     */
    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        
        while (current != null) {
            sb.append(current.value);
            
            if (current.next != null) {
                sb.append(", ");
            }
            
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 链表节点的静态嵌套类
     */
    private static class Node {
        String value;    // 节点存储的值
        Node next;       // 指向下一个节点的引用
        
        public Node(String value) {
            this.value = value;
            this.next = null;
        }
    }
    
    /**
     * 队列迭代器的非静态内部类
     * 提供遍历队列元素的功能
     * 
     * 使用非静态内部类的原因：
     * 1. 迭代器需要访问外部类StringQueue的实例变量（如head）
     * 2. 每个迭代器与特定的队列实例相关联
     * 3. 非静态内部类可以自动访问外部类的实例
     */
    public class Iterator {
        // 当前迭代位置的节点
        private Node current;
        
        /**
         * 创建一个从队列头部开始的迭代器
         */
        public Iterator() {
            // 初始化为队列的头节点
            current = head;
        }
        
        /**
         * 检查是否还有下一个元素
         * 
         * @return 如果还有更多元素返回true，否则返回false
         */
        public boolean hasNext() {
            return current != null;
        }
        
        /**
         * 返回下一个元素并前进迭代器
         * 
         * @return 队列中的下一个元素
         * @throws NoSuchElementException 如果没有更多元素
         */
        public String next() {
            if (current == null) {
                throw new NoSuchElementException("迭代器已到达队列末尾");
            }
            
            String value = current.value;
            current = current.next;
            return value;
        }
    }
} 
