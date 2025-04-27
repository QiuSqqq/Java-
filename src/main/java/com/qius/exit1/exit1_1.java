package com.qius.exit1;

/**
 * 问题1：编写一个程序，读取一个整数并将其以二进制、八进制和十六进制形式打印出来。
 * 计算该整数的倒数，并以十六进制浮点数形式打印。
 * 
 * 本程序展示了如何：
 * 1. 使用Scanner从控制台读取整数输入
 * 2. 将整数转换为不同进制的字符串表示：
 *    - Integer.toBinaryString(): 转换为二进制
 *    - Integer.toOctalString(): 转换为八进制
 *    - Integer.toHexString(): 转换为十六进制
 * 3. 计算整数的倒数并使用Double.toHexString()转换为十六进制浮点数表示
 */
import java.util.Scanner;

public class exit1_1 {
    public static void main(String[] args) {
        /**
         * 创建Scanner对象用于读取用户输入，并提示用户输入一个整数
         */
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个整数：");
        int number = scanner.nextInt();
        
        /**
         * 将整数转换为各种进制表示并输出
         * - Integer.toBinaryString(): 转换为二进制
         * - Integer.toOctalString(): 转换为八进制
         * - Integer.toHexString(): 转换为十六进制
         */
        System.out.println("二进制表示：" + Integer.toBinaryString(number));
        System.out.println("八进制表示：" + Integer.toOctalString(number));
        System.out.println("十六进制表示：" + Integer.toHexString(number));
        
        /**
         * 计算整数的倒数，并转换为十六进制浮点数表示
         * - 使用1.0确保结果是double类型
         * - Double.toHexString()将浮点数转换为十六进制浮点表示
         */
        double reciprocal = 1.0 / number;
        System.out.println("倒数的十六进制浮点数表示：" + Double.toHexString(reciprocal));
        
        // 关闭Scanner，释放资源
        scanner.close();
    }
} 