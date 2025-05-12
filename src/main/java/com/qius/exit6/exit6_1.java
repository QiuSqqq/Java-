package com.qius.exit6;

/**
 * 第六章习题1：栈的基本操作演示
 * 回答关于栈的基本操作和核心方法
 * 
 * 本程序展示了如何：
 * 1. 解释栈(Stack)的基本概念和操作原理
 * 2. 演示栈的各种常见操作（入栈、出栈、查看栈顶、检查大小等）
 * 3. 说明栈在实际应用中的各种使用场景
 */
public class exit6_1 {
    public static void main(String[] args) {
        /**
         * 展示栈的基本概念和操作
         * - 介绍栈的"后进先出"(LIFO)原则
         * - 列出栈的基本操作：push, pop, peek等
         * - 说明栈的常见实现方式
         */
        System.out.println("栈(Stack)是一种基于\"后进先出\"(LIFO)原则的数据结构");
        System.out.println("\n栈的基本操作包括：");
        System.out.println("1. push(E element) - 将元素推入栈顶");
        System.out.println("2. pop() - 从栈顶弹出元素并返回");
        System.out.println("3. peek() - 查看栈顶元素但不移除");
        System.out.println("4. isEmpty() - 检查栈是否为空");
        System.out.println("5. size() - 返回栈中元素的数量");
        
        System.out.println("\n栈的实现方式：");
        System.out.println("1. 基于数组的实现");
        System.out.println("2. 基于链表的实现");
        System.out.println("3. Java集合框架中的Stack类或Deque接口的实现类");
        
        /**
         * 列举栈的实际应用场景
         * - 包括算法、编程和日常应用中的栈使用例子
         */
        System.out.println("\n栈的应用场景：");
        System.out.println("1. 表达式求值和语法解析");
        System.out.println("2. 括号匹配检查");
        System.out.println("3. 函数调用和递归实现");
        System.out.println("4. 撤销操作（如文本编辑器的撤销功能）");
        System.out.println("5. 深度优先搜索算法");
        System.out.println("6. 浏览器的前进/后退功能");
        
        /**
         * 演示栈的基本操作
         * - 创建一个整数栈并执行入栈、出栈等操作
         * - 展示栈的状态变化
         */
        // 简单的ArrayListStack使用示例
        ArrayListStack<Integer> stack = new ArrayListStack<>();
        
        System.out.println("\n栈操作示例：");
        System.out.println("将1, 2, 3依次入栈");
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        System.out.println("栈大小: " + stack.size());
        System.out.println("栈顶元素: " + stack.peek());
        System.out.println("弹出元素: " + stack.pop());
        System.out.println("弹出后栈顶元素: " + stack.peek());
        System.out.println("弹出元素: " + stack.pop());
        System.out.println("弹出元素: " + stack.pop());
        System.out.println("栈是否为空: " + stack.isEmpty());
    }
} 