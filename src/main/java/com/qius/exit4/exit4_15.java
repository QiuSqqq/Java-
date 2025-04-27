package com.qius.exit4;

import java.lang.reflect.Method;
import java.util.function.DoubleFunction;

/**
 * 问题15：编写一个方法，该方法为任意Method表示的具有double或Double类型参数的静态方法打印值表。
 * 除了Method对象之外，还接受下限、上限和步长。通过打印Math.sqrt和Double.toHexString表格来演示你的方法。
 * 使用DoubleFunction<Object>来替代Method重复该方法，并对比两种方法的安全性、效率和便利性。
 */
public class exit4_15 {
    public static void main(String[] args) {
        try {
            System.out.println("===== 使用Method对象方式打印值表 =====");
            
            // 获取Math.sqrt方法
            Method sqrtMethod = Math.class.getMethod("sqrt", double.class);
            System.out.println("\n--- Math.sqrt方法的值表 ---");
            printValueTable(sqrtMethod, 0.0, 10.0, 1.0);
            
            // 获取Double.toHexString方法
            Method toHexStringMethod = Double.class.getMethod("toHexString", double.class);
            System.out.println("\n--- Double.toHexString方法的值表 ---");
            printValueTable(toHexStringMethod, 0.0, 10.0, 2.0);
            
            // 获取Math.sin方法
            Method sinMethod = Math.class.getMethod("sin", double.class);
            System.out.println("\n--- Math.sin方法的值表 ---");
            printValueTable(sinMethod, 0.0, Math.PI * 2, Math.PI / 4);
            
            System.out.println("\n===== 使用DoubleFunction方式打印值表 =====");
            
            // 使用Lambda表达式表示函数
            System.out.println("\n--- Math.sqrt函数的值表 ---");
            printValueTable(Math::sqrt, 0.0, 10.0, 1.0);
            
            System.out.println("\n--- Double.toHexString函数的值表 ---");
            printValueTable(Double::toHexString, 0.0, 10.0, 2.0);
            
            System.out.println("\n--- Math.sin函数的值表 ---");
            printValueTable(Math::sin, 0.0, Math.PI * 2, Math.PI / 4);
            
            System.out.println("\n--- 自定义平方函数的值表 ---");
            printValueTable(x -> x * x, 0.0, 10.0, 1.0);
            
            // 比较两种方法的性能
            compareMethods();
            
            // 比较两种方法的安全性、效率和便利性
            compareMethodProperties();
            
        } catch (Exception e) {
            System.err.println("发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 为任意Method表示的具有double或Double类型参数的静态方法打印值表
     * 
     * @param method 要打印值表的Method对象
     * @param lowerBound 下限
     * @param upperBound 上限
     * @param step 步长
     * @throws Exception 如果方法调用失败
     */
    public static void printValueTable(Method method, double lowerBound, double upperBound, double step) throws Exception {
        // 检查方法参数类型
        if (method.getParameterCount() != 1 ||
            (method.getParameterTypes()[0] != double.class && method.getParameterTypes()[0] != Double.class)) {
            throw new IllegalArgumentException("方法必须有一个double或Double类型参数");
        }
        
        // 打印表头
        System.out.println("x\t|\t" + method.getName() + "(x)");
        System.out.println("----------------------------------");
        
        // 打印值表
        for (double x = lowerBound; x <= upperBound; x += step) {
            Object result = method.invoke(null, x);
            System.out.println(formatDouble(x) + "\t|\t" + result);
        }
    }
    
    /**
     * 使用DoubleFunction为任意函数打印值表
     * 
     * @param function 要打印值表的函数
     * @param lowerBound 下限
     * @param upperBound 上限
     * @param step 步长
     */
    public static void printValueTable(DoubleFunction<Object> function, double lowerBound, double upperBound, double step) {
        // 打印表头
        System.out.println("x\t|\tf(x)");
        System.out.println("----------------------------------");
        
        // 打印值表
        for (double x = lowerBound; x <= upperBound; x += step) {
            Object result = function.apply(x);
            System.out.println(formatDouble(x) + "\t|\t" + result);
        }
    }
    
    /**
     * 比较两种方法的性能
     */
    private static void compareMethods() throws Exception {
        System.out.println("\n===== 性能比较 =====");
        
        // 获取Math.sqrt方法
        Method sqrtMethod = Math.class.getMethod("sqrt", double.class);
        
        // 创建等效的DoubleFunction
        DoubleFunction<Double> sqrtFunction = Math::sqrt;
        
        // 迭代次数
        final int iterations = 10_000_000;
        
        // 预热
        for (int i = 0; i < 1_000_000; i++) {
            sqrtMethod.invoke(null, (double) i);
            sqrtFunction.apply(i);
        }
        
        // 测试Method方式
        long startTimeMethod = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            sqrtMethod.invoke(null, (double) i);
        }
        long endTimeMethod = System.nanoTime();
        long methodTime = endTimeMethod - startTimeMethod;
        
        // 测试DoubleFunction方式
        long startTimeFunction = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            sqrtFunction.apply(i);
        }
        long endTimeFunction = System.nanoTime();
        long functionTime = endTimeFunction - startTimeFunction;
        
        // 打印结果
        System.out.println("Method方式耗时: " + methodTime / 1_000_000.0 + " ms");
        System.out.println("DoubleFunction方式耗时: " + functionTime / 1_000_000.0 + " ms");
        System.out.println("Method方式是DoubleFunction方式的 " + String.format("%.2f", (double) methodTime / functionTime) + " 倍慢");
    }
    
    /**
     * 比较两种方法的安全性、效率和便利性
     */
    private static void compareMethodProperties() {
        System.out.println("\n===== 两种方法的比较 =====");
        
        System.out.println("1. 安全性比较:");
        System.out.println("   Method方式:");
        System.out.println("     - 在运行时检查方法存在性和参数类型");
        System.out.println("     - 可能抛出反射相关异常");
        System.out.println("     - 没有编译时类型检查");
        System.out.println("   DoubleFunction方式:");
        System.out.println("     - 提供编译时类型安全");
        System.out.println("     - 不需要异常处理");
        System.out.println("     - 如果方法不存在会产生编译错误");
        
        System.out.println("\n2. 效率比较:");
        System.out.println("   Method方式:");
        System.out.println("     - 需要反射调用，性能较低");
        System.out.println("     - 每次调用都有参数包装和拆箱的开销");
        System.out.println("     - 可能存在访问控制检查的开销");
        System.out.println("   DoubleFunction方式:");
        System.out.println("     - 直接方法调用，性能高");
        System.out.println("     - JVM可能会内联优化");
        System.out.println("     - 没有反射相关开销");
        
        System.out.println("\n3. 便利性比较:");
        System.out.println("   Method方式:");
        System.out.println("     - 可以动态选择和调用方法");
        System.out.println("     - 适合不知道在编译时会调用哪个方法的场景");
        System.out.println("     - 代码较复杂，需要处理异常");
        System.out.println("   DoubleFunction方式:");
        System.out.println("     - 代码简洁易读");
        System.out.println("     - 可以使用Lambda表达式或方法引用");
        System.out.println("     - 可以轻松组合函数");
        System.out.println("     - 编译时就确定调用的方法");
        
        System.out.println("\n4. 总结:");
        System.out.println("   - 如果在编译时知道要调用的方法，使用DoubleFunction更好");
        System.out.println("   - 如果需要动态选择方法（如根据用户输入），使用Method更合适");
        System.out.println("   - DoubleFunction在安全性、效率和便利性方面通常更有优势");
        System.out.println("   - Method的主要优势是灵活性和动态性");
    }
    
    /**
     * 格式化double值以便打印
     */
    private static String formatDouble(double value) {
        // 检查是否接近PI的整数倍
        if (Math.abs(value - Math.PI) < 1e-10) return "π";
        if (Math.abs(value - 2 * Math.PI) < 1e-10) return "2π";
        if (Math.abs(value - Math.PI / 2) < 1e-10) return "π/2";
        if (Math.abs(value - Math.PI / 4) < 1e-10) return "π/4";
        if (Math.abs(value - 3 * Math.PI / 4) < 1e-10) return "3π/4";
        if (Math.abs(value - 3 * Math.PI / 2) < 1e-10) return "3π/2";
        
        // 对于其他值，检查是否为整数
        if (value == Math.floor(value)) {
            return String.format("%.0f", value);
        }
        
        // 返回带两位小数的浮点数
        return String.format("%.2f", value);
    }
} 