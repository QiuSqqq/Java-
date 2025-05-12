package com.qius.exit1;

/**
 * 问题4：编写一个程序，打印double类型的正的最小值和最大值。
 * （提示：查找Java API中的Math.nextUp方法。）
 * 
 * 本程序展示了如何：
 * 1. 获取double类型的最小正值和最大值
 * 2. 使用两种方式获取最小正值：Double.MIN_VALUE和Math.nextUp(0.0)
 * 3. 验证两种方法获取的最小正值是否相等
 */
public class exit1_4 {
    public static void main(String[] args) {
        /**
         * 获取double类型的最小正值和最大值
         * - Double.MIN_VALUE表示double类型的最小正数
         * - Double.MAX_VALUE表示double类型的最大正数
         */
        double minPositive = Double.MIN_VALUE;
        double maxValue = Double.MAX_VALUE;
        
        /**
         * 使用Math.nextUp方法获取最小正值
         * - Math.nextUp(0.0)返回大于0.0的最小double值
         * - 这是获取double类型最小正值的另一种方式
         */
        double nextUpOfZero = Math.nextUp(0.0);
        
        // 打印double类型的正最小值和最大值
        System.out.println("double类型的正的最小值：" + minPositive);
        System.out.println("使用Math.nextUp(0.0)得到的值：" + nextUpOfZero);
        System.out.println("double类型的最大值：" + maxValue);
        
        // 验证最小正值和nextUp(0.0)是否相等
        System.out.println("Double.MIN_VALUE 和 Math.nextUp(0.0) 是否相等：" + (minPositive == nextUpOfZero));
    }
} 