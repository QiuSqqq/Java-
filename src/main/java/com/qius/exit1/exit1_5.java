package com.qius.exit1;

/**
 * 问题5：当将一个double类型的值转换为int类型的值时，如果该值可能大于最大int值时会发生什么？
 * 试试看。
 */
public class exit1_5 {
    public static void main(String[] args) {
        // 创建一些double值进行测试
        // Integer.MAX_VALUE表示int类型可以表示的最大值：2147483647
        double d1 = (double) Integer.MAX_VALUE; // 最大int值
        double d2 = (double) Integer.MAX_VALUE + 1; // 超过最大int值1
        double d3 = (double) Integer.MAX_VALUE + 1000; // 明显超过最大int值
        double d4 = Double.MAX_VALUE; // double最大值，远远超过int的范围
        
        // 转换并输出结果
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("-----------------------------------");
        
        // 测试第一个值：正好等于最大int值
        System.out.println("d1 = " + d1 + " (等于Integer.MAX_VALUE)");
        System.out.println("(int)d1 = " + (int)d1);
        System.out.println("-----------------------------------");
        
        // 测试第二个值：刚好超过最大int值1
        System.out.println("d2 = " + d2 + " (Integer.MAX_VALUE + 1)");
        System.out.println("(int)d2 = " + (int)d2);
        System.out.println("-----------------------------------");
        
        // 测试第三个值：明显超过最大int值
        System.out.println("d3 = " + d3 + " (Integer.MAX_VALUE + 1000)");
        System.out.println("(int)d3 = " + (int)d3);
        System.out.println("-----------------------------------");
        
        // 测试第四个值：double类型的最大值
        System.out.println("d4 = " + d4 + " (Double.MAX_VALUE)");
        System.out.println("(int)d4 = " + (int)d4);
        
        // 结论：
        // 当将超过int最大值的double转换为int时，结果为Integer.MAX_VALUE
        // 这是因为Java在类型转换时如果超出目标类型的范围，会使用目标类型的最大值
        System.out.println("\n结论：当将超过最大int值的double转换为int时，结果为" + (int)d4 + 
                         "，这是Integer.MAX_VALUE的值（" + Integer.MAX_VALUE + "）");
        
        // 注意：如果double值是负无穷大或NaN，转换为int会得到特定的值
        System.out.println("\n额外测试：");
        System.out.println("(int)Double.POSITIVE_INFINITY = " + (int)Double.POSITIVE_INFINITY);
        System.out.println("(int)Double.NEGATIVE_INFINITY = " + (int)Double.NEGATIVE_INFINITY);
        System.out.println("(int)Double.NaN = " + (int)Double.NaN);
    }
} 