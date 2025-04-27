package com.qius.exit6;

import java.util.Scanner;

/**
 * 测试ArrayListStack类的演示程序
 */
public class StackDemo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // 创建一个存储字符串的栈
        ArrayListStack<String> stack = new ArrayListStack<>();
        
        System.out.println("栈操作演示：");
        System.out.println("1. 向栈中添加元素");
        System.out.println("2. 从栈中弹出元素");
        System.out.println("3. 查看栈顶元素");
        System.out.println("4. 检查栈是否为空");
        System.out.println("5. 显示栈中元素个数");
        System.out.println("0. 退出程序");
        
        int choice;
        do {
            System.out.print("\n请选择操作 (0-5): ");
            choice = input.nextInt();
            input.nextLine(); // 消耗换行符
            
            switch (choice) {
                case 1:
                    System.out.print("请输入要添加的元素: ");
                    String element = input.nextLine();
                    stack.push(element);
                    System.out.println("元素 \"" + element + "\" 已添加到栈中");
                    break;
                    
                case 2:
                    try {
                        String popped = stack.pop();
                        System.out.println("弹出的元素: " + popped);
                    } catch (java.util.EmptyStackException e) {
                        System.out.println("错误：栈为空，无法弹出元素");
                    }
                    break;
                    
                case 3:
                    try {
                        String top = stack.peek();
                        System.out.println("栈顶元素: " + top);
                    } catch (java.util.EmptyStackException e) {
                        System.out.println("错误：栈为空，无法查看栈顶元素");
                    }
                    break;
                    
                case 4:
                    System.out.println("栈" + (stack.isEmpty() ? "为空" : "不为空"));
                    break;
                    
                case 5:
                    System.out.println("栈中元素个数: " + stack.size());
                    break;
                    
                case 0:
                    System.out.println("程序结束");
                    break;
                    
                default:
                    System.out.println("无效的选择，请重新输入");
            }
            
        } while (choice != 0);
        
        input.close();
    }
} 