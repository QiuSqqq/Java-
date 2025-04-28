package com.qius.exit1;

/**
 * 问题21：改进average方法，以便至少通过一个参数来调用它。
 */
public class exit1_21 {
    public static void main(String[] args) {
        
        // 测试不同参数数量的情况
        System.out.println("测试一个参数: average(10) = " + average(10));
        System.out.println("测试两个参数: average(10, 20) = " + average(10, 20));
        System.out.println("测试三个参数: average(10, 20, 30) = " + average(10, 20, 30));
        System.out.println("测试多个参数: average(10, 20, 30, 40, 50) = " + average(10, 20, 30, 40, 50));
        
        // 测试数组参数
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("测试数组参数: averageArray(numbers) = " + averageArray(numbers));
        
        // 测试不同类型的参数
        System.out.println("\n测试不同类型的参数:");
        System.out.println("double类型参数: averageDouble(1.5, 2.5, 3.5) = " + averageDouble(1.5, 2.5, 3.5));
        
        // 测试边界情况
        try {
            System.out.println("\n测试零个参数（应该抛出异常）: average() = " + average());
        } catch (IllegalArgumentException e) {
            System.out.println("捕获到异常: " + e.getMessage());
        }
        
        // 演示Stream API
        System.out.println("\n使用Stream API计算平均值:");
        System.out.println("averageUsingStream(1, 2, 3, 4, 5) = " + averageUsingStream(1, 2, 3, 4, 5));
        
        // 解释实现原理
        System.out.println("\n实现原理:");
        System.out.println("1. 使用Java的可变参数(varargs)特性，允许方法接受任意数量的参数");
        System.out.println("2. 在方法内部检查参数数量，确保至少有一个参数");
        System.out.println("3. 可以使用方法重载为不同类型的参数提供不同版本的average方法");
    }
    
    /**
     * 计算整数参数的平均值（改进版本）
     * 使用可变参数允许任意数量的参数
     * 
     * @param numbers 一个或多个整数参数
     * @return 参数的平均值
     * @throws IllegalArgumentException 如果没有提供参数
     */
    public static double average(int... numbers) {
        // 检查是否至少提供了一个参数
        if (numbers.length == 0) {
            throw new IllegalArgumentException("至少需要一个参数");
        }
        
        // 计算所有参数的总和
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        
        // 返回平均值
        return (double) sum / numbers.length;
    }
    
    /**
     * 计算double类型参数的平均值
     * 使用可变参数允许任意数量的参数
     *
     * @param numbers 一个或多个double类型参数
     * @return 参数的平均值
     * @throws IllegalArgumentException 如果没有提供参数
     */
    public static double averageDouble(double... numbers) {
        // 检查是否至少提供了一个参数
        if (numbers.length == 0) {
            throw new IllegalArgumentException("至少需要一个参数");
        }
        
        // 计算所有参数的总和
        double sum = 0.0;
        for (double num : numbers) {
            sum += num;
        }
        
        // 返回平均值
        return sum / numbers.length;
    }
    
    /**
     * 另一种实现方式：使用Stream API（Java 8及以上）
     * 
     * @param numbers 一个或多个整数参数
     * @return 参数的平均值
     * @throws IllegalArgumentException 如果没有提供参数
     */
    public static double averageUsingStream(int... numbers) {
        // 检查是否至少提供了一个参数
        if (numbers.length == 0) {
            throw new IllegalArgumentException("至少需要一个参数");
        }
        
        // 使用Stream API计算平均值
        return java.util.Arrays.stream(numbers).average().orElse(0.0);
    }
    
    /**
     * 演示方法重载：接受整数数组的average方法
     * 
     * @param numbers 整数数组
     * @return 数组元素的平均值
     * @throws IllegalArgumentException 如果数组为空或为null
     */
    public static double averageArray(int[] numbers) {
        // 检查数组是否为null或为空
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("数组不能为null或为空");
        }
        
        // 计算所有元素的总和
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        
        // 返回平均值
        return (double) sum / numbers.length;
    }
} 