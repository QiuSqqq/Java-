package com.qius.exit6;

import java.util.ArrayList;

/**
 * 使用ArrayList实现的泛型Stack类
 * 
 * 本类提供了：
 * 1. 基于ArrayList的栈数据结构的标准实现
 * 2. 支持泛型的入栈、出栈、查看栈顶等基本操作
 * 3. 良好的异常处理，在栈为空时抛出适当的异常
 */
public class ArrayListStack<E> {
    // 使用ArrayList存储栈中的元素
    private ArrayList<E> elements;

    /**
     * 构造一个空栈
     * - 初始化一个空的ArrayList用于存储元素
     */
    public ArrayListStack() {
        elements = new ArrayList<>();
    }

    /**
     * 将元素推入栈顶
     * - 在ArrayList末尾添加新元素
     */
    public void push(E element) {
        elements.add(element);
    }

    /**
     * 从栈顶弹出元素
     * - 移除并返回ArrayList的最后一个元素
     * - 当栈为空时抛出异常
     */
    public E pop() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }

    /**
     * 查看栈顶元素但不移除
     * - 返回ArrayList的最后一个元素但不删除
     * - 当栈为空时抛出异常
     */
    public E peek() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }

    /**
     * 检查栈是否为空
     * - 通过检查底层ArrayList是否为空来确定
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * 返回栈中元素的个数
     * - 返回底层ArrayList中的元素数量
     */
    public int size() {
        return elements.size();
    }
} 