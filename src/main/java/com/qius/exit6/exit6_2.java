package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题2：进制转换器
 * 使用栈实现十进制数转换为任意进制的功能
 */
public class exit6_2 {
    
    /**
     * 将十进制数转换为指定进制的字符串表示
     * @param decimal 要转换的十进制整数
     * @param base 目标进制（2-36之间）
     * @return 转换后的字符串
     * @throws IllegalArgumentException 如果进制不在有效范围内
     */
    public static String convertToBase(int decimal, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("进制必须在2到36之间");
        }
        
        // 处理0的特殊情况
        if (decimal == 0) {
            return "0";
        }
        
        // 处理负数
        boolean isNegative = decimal < 0;
        if (isNegative) {
            decimal = -decimal;
        }
        
        // 使用栈存储余数
        ArrayListStack<Character> stack = new ArrayListStack<>();
        final String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        // 不断除以base，将余数入栈
        while (decimal > 0) {
            int remainder = decimal % base;
            stack.push(digits.charAt(remainder));
            decimal /= base;
        }
        
        // 构建结果字符串
        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append('-');
        }
        
        // 从栈中依次弹出字符，构建最终结果
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("进制转换器 - 使用栈实现");
        System.out.println("可以将十进制数转换为2-36之间的任意进制");
        
        while (true) {
            System.out.print("\n请输入一个十进制整数（输入0退出）: ");
            int decimal = input.nextInt();
            
            if (decimal == 0) {
                System.out.println("程序结束");
                break;
            }
            
            System.out.print("请输入目标进制（2-36）: ");
            int base = input.nextInt();
            
            try {
                String result = convertToBase(decimal, base);
                System.out.println(decimal + " 转换为" + base + "进制是: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("错误: " + e.getMessage());
            }
        }
        
        input.close();
    }
} 