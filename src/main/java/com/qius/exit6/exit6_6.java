package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题6：栈的逆序
 * 使用栈实现元素逆序功能
 */
public class exit6_6 {
    
    /**
     * 将栈中的元素逆序
     * @param stack 要逆序的栈
     * @param <E> 栈中元素的类型
     */
    public static <E> void reverseStack(ArrayListStack<E> stack) {
        // 如果栈为空或只有一个元素，不需要逆序
        if (stack.isEmpty() || stack.size() == 1) {
            return;
        }
        
        // 创建一个临时栈存储弹出的元素
        ArrayListStack<E> tempStack = new ArrayListStack<>();
        ArrayListStack<E> auxStack = new ArrayListStack<>();
        
        // 步骤1：将原栈中的所有元素弹出并压入临时栈
        while (!stack.isEmpty()) {
            tempStack.push(stack.pop());
        }
        
        // 步骤2：将临时栈中的所有元素弹出并压入辅助栈
        // 此时元素顺序已经与原栈相反
        while (!tempStack.isEmpty()) {
            auxStack.push(tempStack.pop());
        }
        
        // 步骤3：将辅助栈中的元素放回原栈
        while (!auxStack.isEmpty()) {
            stack.push(auxStack.pop());
        }
    }
    
    /**
     * 使用递归方式将栈底元素移到栈顶
     * @param stack 要操作的栈
     * @param <E> 栈中元素的类型
     */
    private static <E> void moveBottomToTop(ArrayListStack<E> stack) {
        // 如果栈为空，直接返回
        if (stack.isEmpty()) {
            return;
        }
        
        // 如果栈只有一个元素，不需要移动
        if (stack.size() == 1) {
            return;
        }
        
        // 弹出栈顶元素
        E top = stack.pop();
        
        // 递归处理剩余栈
        moveBottomToTop(stack);
        
        // 弹出当前栈底元素（递归后变成了栈顶）
        E bottom = stack.pop();
        
        // 将原栈顶元素放回
        stack.push(top);
        
        // 将原栈底元素放在栈顶
        stack.push(bottom);
    }
    
    /**
     * 使用递归方式逆序栈
     * @param stack 要逆序的栈
     * @param <E> 栈中元素的类型
     */
    public static <E> void recursiveReverseStack(ArrayListStack<E> stack) {
        // 如果栈为空或只有一个元素，不需要逆序
        if (stack.isEmpty() || stack.size() == 1) {
            return;
        }
        
        // 将栈底元素移到栈顶
        moveBottomToTop(stack);
        
        // 弹出移到顶部的元素（原栈底元素）
        E top = stack.pop();
        
        // 递归处理剩下的栈
        recursiveReverseStack(stack);
        
        // 将弹出的元素放回栈顶
        stack.push(top);
    }
    
    /**
     * 打印栈中的所有元素（从栈顶到栈底）
     * @param stack 要打印的栈
     * @param <E> 栈中元素的类型
     */
    public static <E> void printStack(ArrayListStack<E> stack) {
        if (stack.isEmpty()) {
            System.out.println("栈为空");
            return;
        }
        
        // 创建临时栈以存储弹出的元素
        ArrayListStack<E> tempStack = new ArrayListStack<>();
        StringBuilder sb = new StringBuilder("栈内元素（从栈顶到栈底）: ");
        
        // 弹出元素并存储到临时栈
        while (!stack.isEmpty()) {
            E element = stack.pop();
            sb.append(element).append(" ");
            tempStack.push(element);
        }
        
        // 将元素放回原栈
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        
        System.out.println(sb.toString());
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("栈的逆序操作示例");
        System.out.println("1. 使用临时栈实现逆序");
        System.out.println("2. 使用递归实现逆序");
        
        // 创建一个整数栈用于演示
        ArrayListStack<Integer> stack = new ArrayListStack<>();
        
        System.out.println("\n请输入要压入栈的整数个数: ");
        int count = input.nextInt();
        
        System.out.println("请依次输入" + count + "个整数: ");
        for (int i = 0; i < count; i++) {
            int num = input.nextInt();
            stack.push(num);
        }
        
        // 打印原始栈内容
        System.out.println("\n原始栈内容:");
        printStack(stack);
        
        // 选择逆序方法
        System.out.print("\n请选择逆序方法(1或2): ");
        int choice = input.nextInt();
        
        if (choice == 1) {
            reverseStack(stack);
            System.out.println("使用临时栈逆序后的栈内容:");
        } else {
            recursiveReverseStack(stack);
            System.out.println("使用递归逆序后的栈内容:");
        }
        
        // 打印逆序后的栈内容
        printStack(stack);
        
        System.out.println("\n栈逆序的应用场景:");
        System.out.println("1. 撤销操作的重做功能");
        System.out.println("2. 浏览历史记录的前进/后退功能");
        System.out.println("3. 算法中需要反向处理元素的场景");
        System.out.println("4. 递归算法的优化");
        
        input.close();
    }
} 