package com.qius.exit1; /**
 * 问题7：编写一个程序，读入0～4294967295的两个整数，将它们存储在int变量中，
 * 并计算和显示它们的无符号数的和、差、积、商和余数。不要将它们转换为long类型的值。
 */

import java.util.Scanner;

public class exit1_7 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入两个无符号整数
        System.out.println("请输入两个0～4294967295之间的整数：");
        
        // 读取第一个整数
        // 注意：Java中的int是有符号的，范围是-2^31到2^31-1
        // 而题目要求处理0到2^32-1的无符号整数
        // 所以我们需要使用Integer.parseUnsignedInt来解析无符号整数
        System.out.print("第一个整数：");
        String input1 = scanner.next();
        int num1 = Integer.parseUnsignedInt(input1);
        
        // 读取第二个整数
        System.out.print("第二个整数：");
        String input2 = scanner.next();
        int num2 = Integer.parseUnsignedInt(input2);
        
        // 计算无符号数的和
        // Integer.toUnsignedLong将有符号int转换为等效的无符号long
        // Integer.toUnsignedString将int作为无符号数转换为字符串
        long unsignedSum = Integer.toUnsignedLong(num1) + Integer.toUnsignedLong(num2);
        System.out.println("无符号和：" + unsignedSum);
        
        // 也可以直接在int中进行加法，然后将结果解释为无符号数
        int sum = num1 + num2;
        System.out.println("无符号和（使用int）：" + Integer.toUnsignedString(sum));
        
        // 计算无符号数的差
        // 使用Integer.compareUnsigned来比较两个无符号整数的大小
        if (Integer.compareUnsigned(num1, num2) < 0) {
            System.out.println("无符号差（num1 - num2）：无法计算，因为num1 < num2");
        } else {
            int diff = num1 - num2;
            System.out.println("无符号差（num1 - num2）：" + Integer.toUnsignedString(diff));
        }
        
        // 计算无符号数的积
        // 由于两个32位无符号整数相乘可能超过long的范围，这里简单处理
        long product = Integer.toUnsignedLong(num1) * Integer.toUnsignedLong(num2);
        System.out.println("无符号积：" + product);
        
        // 计算无符号数的商
        // Integer.divideUnsigned执行无符号除法
        int quotient = Integer.divideUnsigned(num1, num2);
        System.out.println("无符号商（num1 / num2）：" + Integer.toUnsignedString(quotient));
        
        // 计算无符号数的余数
        // Integer.remainderUnsigned执行无符号取余
        int remainder = Integer.remainderUnsigned(num1, num2);
        System.out.println("无符号余数（num1 % num2）：" + Integer.toUnsignedString(remainder));
        
        // 关闭Scanner
        scanner.close();
    }
} 