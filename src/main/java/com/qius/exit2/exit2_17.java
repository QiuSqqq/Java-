package com.qius.exit2;

/**
 * 问题17：实现一个Queue类。该类是一个字符串的无边界队列。
 * 提供一个add方法，该方法在队列的尾部添加元素；提供一个remove方法，该方法在队列的头部删除元素。
 * 将元素存储到链接列表的节点中。将Node定义为嵌套类。该嵌套类应该是静态的还是非静态的？
 */
public class exit2_17 {
    public static void main(String[] args) {
        System.out.println("问题17：实现Queue类及嵌套的Node类");
        System.out.println("-------------------------------");
        
        // 创建一个字符串队列
        Queue queue = new Queue();
        
        // 测试添加元素
        System.out.println("1. 添加元素到队列：");
        queue.add("第一个");
        queue.add("第二个");
        queue.add("第三个");
        
        // 打印队列内容
        System.out.println("队列内容: " + queue);
        System.out.println("队列大小: " + queue.size());
        
        // 测试移除元素
        System.out.println("\n2. 从队列中移除元素：");
        System.out.println("移除的元素: " + queue.remove());
        System.out.println("移除后队列内容: " + queue);
        System.out.println("队列大小: " + queue.size());
        
        // 再次添加和移除元素
        System.out.println("\n3. 再次操作队列：");
        queue.add("第四个");
        System.out.println("添加'第四个'后的队列: " + queue);
        System.out.println("移除的元素: " + queue.remove());
        System.out.println("移除的元素: " + queue.remove());
        System.out.println("移除后队列内容: " + queue);
        
        // 测试空队列
        System.out.println("\n4. 测试空队列：");
        Queue emptyQueue = new Queue();
        System.out.println("空队列移除元素: " + emptyQueue.remove());
        
        // 讨论Node嵌套类是否应该是静态的
        System.out.println("\n5. 关于Node嵌套类是否应该是静态的讨论：");
        System.out.println("在这个实现中，Node嵌套类应该是静态的，原因如下：");
        System.out.println("   a) Node类不需要访问外部Queue类的实例变量或非静态方法");
        System.out.println("   b) Node只是一个数据容器，存储值和下一个节点的引用");
        System.out.println("   c) 静态嵌套类更高效，因为它不持有对外部类实例的引用");
        System.out.println("   d) 静态嵌套类的内存占用更小");
        System.out.println("   e) 从概念上讲，节点是队列实现的细节，而不是队列概念的一部分");
        
        System.out.println("\n如果Node是非静态内部类，可能导致以下问题：");
        System.out.println("   a) 每个Node对象会持有一个对Queue的引用，增加内存开销");
        System.out.println("   b) 可能导致内存泄漏，因为节点会间接持有队列引用");
        System.out.println("   c) 在不需要访问外部类成员的情况下使用非静态内部类是不必要的");
        
        System.out.println("\n总结：当嵌套类不需要访问外部类的非静态成员时，应优先使用静态嵌套类。");
    }
}

/**
 * 字符串的无边界队列实现。
 * 使用链表结构存储元素，支持在尾部添加元素和从头部移除元素。
 */
class Queue {
    // 队列的头节点（最先添加的元素）
    private Node head;
    
    // 队列的尾节点（最后添加的元素）
    private Node tail;
    
    // 队列中的元素数量
    private int size;
    
    /**
     * 创建一个空队列
     */
    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * 将元素添加到队列的尾部
     * 
     * @param element 要添加的字符串元素
     */
    public void add(String element) {
        // 创建一个新节点
        Node newNode = new Node(element);
        
        if (tail == null) {
            // 如果队列为空，设置head和tail为新节点
            head = newNode;
            tail = newNode;
        } else {
            // 否则，将新节点添加到尾部，并更新tail
            tail.next = newNode;
            tail = newNode;
        }
        
        size++;
    }
    
    /**
     * 从队列的头部移除并返回元素
     * 
     * @return 队列头部的元素，如果队列为空，则返回null
     */
    public String remove() {
        if (head == null) {
            // 队列为空，返回null
            return null;
        }
        
        // 保存头部节点的值
        String element = head.value;
        
        // 更新head指向下一个节点
        head = head.next;
        
        // 如果移除后队列为空，将tail也设置为null
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
     * 
     * 使用静态嵌套类的原因：
     * 1. Node不需要访问Queue的实例变量或方法
     * 2. 节点只是一个数据容器，用于存储值和下一个节点的引用
     * 3. 静态嵌套类更高效，不会持有对外部类实例的引用
     */
    private static class Node {
        // 节点存储的值
        String value;
        
        // 指向下一个节点的引用
        Node next;
        
        /**
         * 创建一个节点，存储指定的值
         * 
         * @param value 要存储的字符串值
         */
        public Node(String value) {
            this.value = value;
            this.next = null;
        }
    }
} 
