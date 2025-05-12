package com.qius.exit1;

/**
 * 问题7：编写一个程序，读入0～4294967295的两个整数，将它们存储在int变量中，
 * 并计算和显示它们的无符号数的和、差、积、商和余数。不要将它们转换为long类型的值。
 * 
 * 本程序展示了如何：
 * 1. 使用Integer类处理无符号整数运算
 * 2. 演示Integer.parseUnsignedInt、toUnsignedString等方法
 * 3. 计算无符号整数的加减乘除和取余运算
 */
import java.util.Scanner;

public class exit1_7 {
    public static void main(String[] args) {
        /**
         * 创建Scanner对象并读取用户输入的两个无符号整数
         * - 使用Integer.parseUnsignedInt解析无符号整数
         * - 确保输入在0～4294967295范围内
         */
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入两个无符号整数
        System.out.println("请输入两个0～4294967295之间的整数：");
        
        // 读取第一个整数
        System.out.print("第一个整数：");
        String input1 = scanner.next();
        int num1 = Integer.parseUnsignedInt(input1);
        
        // 读取第二个整数
        System.out.print("第二个整数：");
        String input2 = scanner.next();
        int num2 = Integer.parseUnsignedInt(input2);
        
        /**
         * 计算并显示无符号整数的各种运算结果
         * - 包括加、减、乘、除和取余操作
         * - 使用Integer提供的无符号运算方法
         */
        // 计算无符号数的和
        long unsignedSum = Integer.toUnsignedLong(num1) + Integer.toUnsignedLong(num2);
        System.out.println("无符号和：" + unsignedSum);
        
        // 也可以直接在int中进行加法，然后将结果解释为无符号数
        int sum = num1 + num2;
        System.out.println("无符号和（使用int）：" + Integer.toUnsignedString(sum));
        
        // 计算无符号数的差
        if (Integer.compareUnsigned(num1, num2) < 0) {
            System.out.println("无符号差（num1 - num2）：无法计算，因为num1 < num2");
        } else {
            int diff = num1 - num2;
            System.out.println("无符号差（num1 - num2）：" + Integer.toUnsignedString(diff));
        }
        
        // 计算无符号数的积
        long product = Integer.toUnsignedLong(num1) * Integer.toUnsignedLong(num2);
        System.out.println("无符号积：" + product);
        
        // 计算无符号数的商
        int quotient = Integer.divideUnsigned(num1, num2);
        System.out.println("无符号商（num1 / num2）：" + Integer.toUnsignedString(quotient));
        
        // 计算无符号数的余数
        int remainder = Integer.remainderUnsigned(num1, num2);
        System.out.println("无符号余数（num1 % num2）：" + Integer.toUnsignedString(remainder));
        
        // 关闭Scanner
        scanner.close();
    }
} 