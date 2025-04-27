package com.qius.exit1;

/**
 * 问题4：编写一个程序，打印double类型的正的最小值和最大值。
 * （提示：查找Java API中的Math.nextUp方法。）
 */
public class exit1_4 {
    public static void main(String[] args) {
        // double类型的正的最小值
        // Double.MIN_VALUE是Java中预定义的常量，表示double类型的最小正数
        double minPositive = Double.MIN_VALUE;
        
        // double类型的最大值
        // Double.MAX_VALUE是Java中预定义的常量，表示double类型的最大值
        double maxValue = Double.MAX_VALUE;
        
        // 使用Math.nextUp确认最小正值
        // Math.nextUp(0.0)返回大于0.0的最小double值
        // 这也是double类型的正最小值的另一种获取方式
        double nextUpOfZero = Math.nextUp(0.0);
        
        // 打印double类型的正最小值
        System.out.println("double类型的正的最小值：" + minPositive);
        // 打印使用Math.nextUp得到的值
        System.out.println("使用Math.nextUp(0.0)得到的值：" + nextUpOfZero);
        // 打印double类型的最大值
        System.out.println("double类型的最大值：" + maxValue);
        
        // 验证最小正值和nextUp(0.0)是否相等
        // 这两种方法应该得到相同的结果
        System.out.println("Double.MIN_VALUE 和 Math.nextUp(0.0) 是否相等：" + (minPositive == nextUpOfZero));
        // 解释：
        // 1. Double.MIN_VALUE是大于0的最小double值，约为4.9e-324
        // 2. Double.MAX_VALUE是double能表示的最大正值，约为1.8e+308
        // 3. Math.nextUp(0.0)返回比0.0大的最接近的浮点数，等同于Double.MIN_VALUE
    }
} 