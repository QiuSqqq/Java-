package com.qius.exit1;

/**
 * 问题6：编写一个计算阶乘 n! = 1×2× ... × n 的程序，使用BigInteger。计算1000的阶乘。
 * 
 * 本程序展示了如何：
 * 1. 使用BigInteger处理超大整数计算
 * 2. 实现阶乘计算算法并处理大数值
 * 3. 计算并展示1000阶乘的结果和位数
 */
import java.math.BigInteger;

public class exit1_6 {
    public static void main(String[] args) {
        /**
         * 计算1000的阶乘
         * - 设定计算目标值
         * - 调用阶乘方法并获取结果
         */
        int n = 1000;
        
        // 调用计算阶乘的方法
        BigInteger result = factorial(n);
        
        /**
         * 显示计算结果
         * - 输出阶乘的完整值
         * - 计算并显示结果的位数
         */
        // 打印结果
        System.out.println(n + "的阶乘是：");
        System.out.println(result);
        
        // 打印结果的位数
        // BigInteger.toString()将大整数转换为字符串，length()获取字符串长度
        System.out.println("\n" + n + "的阶乘的位数是：" + result.toString().length());
    }
    
    /**
     * 计算阶乘的方法
     * - 从1乘到n，累积计算阶乘结果
     * - 使用BigInteger处理可能的超大结果
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