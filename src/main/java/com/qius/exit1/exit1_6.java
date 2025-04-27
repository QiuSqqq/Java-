package com.qius.exit1; /**
 * 问题6：编写一个计算阶乘 n! = 1×2× ... × n 的程序，使用BigInteger。计算1000的阶乘。
 */

import java.math.BigInteger;

public class exit1_6 {
    public static void main(String[] args) {
        // 计算1000的阶乘
        int n = 1000;
        
        // 调用计算阶乘的方法
        BigInteger result = factorial(n);
        
        // 打印结果
        System.out.println(n + "的阶乘是：");
        System.out.println(result);
        
        // 打印结果的位数
        // BigInteger.toString()将大整数转换为字符串，length()获取字符串长度
        System.out.println("\n" + n + "的阶乘的位数是：" + result.toString().length());
    }
    
    /**
     * 计算阶乘的方法
     * @param n 要计算阶乘的数
     * @return n的阶乘结果，以BigInteger形式返回
     */
    public static BigInteger factorial(int n) {
        // 初始化结果为1
        // BigInteger.ONE是BigInteger类的常量，表示整数1
        BigInteger result = BigInteger.ONE;
        
        // 从1乘到n
        for (int i = 1; i <= n; i++) {
            // 将result与当前的i相乘
            // BigInteger.valueOf(i)将int转换为BigInteger
            // multiply方法用于BigInteger的乘法运算
            result = result.multiply(BigInteger.valueOf(i));
        }
        
        // 返回计算结果
        return result;
    }
} 