package com.qius.exit6;

import java.util.ArrayList;

/**
 * 使用ArrayList实现的泛型Stack类
 * @param <E> 栈中元素的类型
 */
public class ArrayListStack<E> {
    // 使用ArrayList存储栈中的元素
    private ArrayList<E> elements;

    /**
     * 构造一个空栈
     */
    public ArrayListStack() {
        elements = new ArrayList<>();
    }

    /**
     * 将元素推入栈顶
     * @param element 要推入的元素
     */
    public void push(E element) {
        elements.add(element);
    }

    /**
     * 从栈顶弹出元素
     * @return 栈顶元素
     * @throws EmptyStackException 如果栈为空
     */
    public E pop() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }

    /**
     * 查看栈顶元素但不移除
     * @return 栈顶元素
     * @throws EmptyStackException 如果栈为空
     */
    public E peek() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * 检查栈是否为空
     * @return 如果栈为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * 返回栈中元素的个数
     * @return 栈中元素的个数
     */
    public int size() {
        return elements.size();
    }
} 