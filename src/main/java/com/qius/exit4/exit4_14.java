package com.qius.exit4;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 问题14：测试通过反射调用方法和常规方法调用之间的性能差异。
 */
public class exit4_14 {
    // 测试次数
    private static final int WARM_UP_ITERATIONS = 1_000_000; // 预热迭代次数
    private static final int TEST_ITERATIONS = 100_000_000; // 测试迭代次数
    
    public static void main(String[] args) {
        try {
            // 创建测试对象
            Calculator calculator = new Calculator();
            
            // 获取Calculator类的add方法
            Method addMethod = Calculator.class.getMethod("add", int.class, int.class);
            
            // 预热JVM（消除JIT编译的影响）
            warmUp(calculator, addMethod);
            
            // 测试常规方法调用性能
            long regularCallTime = testRegularMethodCall(calculator);
            
            // 测试反射方法调用性能（不设置可访问性）
            long reflectionCallTime = testReflectionMethodCall(calculator, addMethod, false);
            
            // 测试反射方法调用性能（设置可访问性）
            long reflectionAccessibleCallTime = testReflectionMethodCall(calculator, addMethod, true);
            
            // 格式化输出结果
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            
            System.out.println("\n=== 性能测试结果 ===");
            System.out.println("测试迭代次数: " + formatter.format(TEST_ITERATIONS));
            System.out.println("常规方法调用总时间: " + formatter.format(regularCallTime) + " ns");
            System.out.println("反射方法调用总时间 (不设置可访问性): " + formatter.format(reflectionCallTime) + " ns");
            System.out.println("反射方法调用总时间 (设置可访问性): " + formatter.format(reflectionAccessibleCallTime) + " ns");
            
            // 计算平均每次调用的耗时
            double regularAvg = (double) regularCallTime / TEST_ITERATIONS;
            double reflectionAvg = (double) reflectionCallTime / TEST_ITERATIONS;
            double reflectionAccessibleAvg = (double) reflectionAccessibleCallTime / TEST_ITERATIONS;
            
            System.out.println("\n=== 平均每次调用的耗时 ===");
            System.out.println("常规方法调用: " + String.format("%.2f", regularAvg) + " ns");
            System.out.println("反射方法调用 (不设置可访问性): " + String.format("%.2f", reflectionAvg) + " ns");
            System.out.println("反射方法调用 (设置可访问性): " + String.format("%.2f", reflectionAccessibleAvg) + " ns");
            
            // 计算性能差异比率
            double reflectionRatio = reflectionAvg / regularAvg;
            double reflectionAccessibleRatio = reflectionAccessibleAvg / regularAvg;
            
            System.out.println("\n=== 性能比较 ===");
            System.out.println("反射调用 (不设置可访问性) 是常规调用的 " + String.format("%.2f", reflectionRatio) + " 倍慢");
            System.out.println("反射调用 (设置可访问性) 是常规调用的 " + String.format("%.2f", reflectionAccessibleRatio) + " 倍慢");
            
            // 测试第一次调用的差异
            testFirstCallDifference();
            
        } catch (Exception e) {
            System.err.println("测试过程中发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * JVM预热
     */
    private static void warmUp(Calculator calculator, Method addMethod) throws Exception {
        System.out.println("正在预热JVM...");
        
        // 预热常规方法调用
        for (int i = 0; i < WARM_UP_ITERATIONS; i++) {
            calculator.add(i, i);
        }
        
        // 预热反射方法调用
        for (int i = 0; i < WARM_UP_ITERATIONS; i++) {
            addMethod.invoke(calculator, i, i);
        }
        
        System.out.println("预热完成");
    }
    
    /**
     * 测试常规方法调用性能
     */
    private static long testRegularMethodCall(Calculator calculator) {
        System.out.println("测试常规方法调用性能...");
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            calculator.add(i, i);
        }
        
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        
        System.out.println("常规方法调用测试完成");
        return elapsedTime;
    }
    
    /**
     * 测试反射方法调用性能
     */
    private static long testReflectionMethodCall(Calculator calculator, Method method, boolean makeAccessible) throws Exception {
        if (makeAccessible) {
            System.out.println("测试反射方法调用性能 (设置可访问性)...");
            method.setAccessible(true);
        } else {
            System.out.println("测试反射方法调用性能 (不设置可访问性)...");
        }
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            method.invoke(calculator, i, i);
        }
        
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        
        System.out.println("反射方法调用测试完成");
        return elapsedTime;
    }
    
    /**
     * 测试第一次调用的差异
     */
    private static void testFirstCallDifference() throws Exception {
        System.out.println("\n=== 测试第一次调用的差异 ===");
        
        // 创建新的测试对象以避免缓存影响
        Calculator calculator = new Calculator();
        Method addMethod = Calculator.class.getMethod("add", int.class, int.class);
        
        // 测试常规方法第一次调用
        long startTime = System.nanoTime();
        int regularResult = calculator.add(5, 7);
        long regularFirstCallTime = System.nanoTime() - startTime;
        
        // 重新获取方法以避免缓存影响
        addMethod = Calculator.class.getMethod("add", int.class, int.class);
        
        // 测试反射方法第一次调用
        startTime = System.nanoTime();
        int reflectionResult = (int) addMethod.invoke(calculator, 5, 7);
        long reflectionFirstCallTime = System.nanoTime() - startTime;
        
        System.out.println("常规方法第一次调用耗时: " + regularFirstCallTime + " ns, 结果: " + regularResult);
        System.out.println("反射方法第一次调用耗时: " + reflectionFirstCallTime + " ns, 结果: " + reflectionResult);
        System.out.println("第一次调用的差异倍数: " + String.format("%.2f", (double) reflectionFirstCallTime / regularFirstCallTime));
        
        System.out.println("\n=== 结论 ===");
        System.out.println("1. 反射方法调用比常规方法调用慢，特别是在第一次调用时");
        System.out.println("2. 设置方法的可访问性可以提高反射调用的性能");
        System.out.println("3. 对于需要大量重复调用的场景，反射的额外开销会特别明显");
        System.out.println("4. 但对于只调用几次的场景，反射的灵活性带来的好处可能超过其性能劣势");
    }
}

/**
 * 用于测试的计算器类
 */
class Calculator {
    /**
     * 简单的加法方法
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * 简单的减法方法
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * 简单的乘法方法
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * 简单的除法方法
     */
    public int divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("除数不能为零");
        return a / b;
    }
} 