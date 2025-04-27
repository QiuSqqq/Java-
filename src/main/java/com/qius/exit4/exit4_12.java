package com.qius.exit4;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 问题12：使用4.6.1小节中的MethodPrinter程序枚举int[]类的所有方法。
 * 如果能够识别出本章中错误描述的一个方法，则将获得加分。
 */
public class exit4_12 {
    public static void main(String[] args) {
        System.out.println("=== int[]的方法 ===");
        printMethods(int[].class);
        
        System.out.println("\n=== Object的方法 ===");
        printMethods(Object.class);
        
        System.out.println("\n=== 分析 ===");
        System.out.println("1. int[]是一个数组类型，由Java虚拟机在运行时自动生成");
        System.out.println("2. int[]类继承自Object类，所以拥有Object类的所有方法");
        System.out.println("3. 数组类没有自己特有的方法，但有一个特殊的public属性'length'");
        System.out.println("4. 数组类不能被继承，不能添加新方法");
        
        System.out.println("\n=== 识别教材中的错误描述 ===");
        System.out.println("在Java核心技术卷I的第4章中错误描述的方法是clone()方法。");
        System.out.println("教材中可能描述clone()方法需要实现Cloneable接口，对象才能被克隆。");
        System.out.println("但事实上，数组类型是特殊的：");
        System.out.println("1. 所有数组类型默认都实现了Cloneable接口");
        System.out.println("2. 可以直接调用数组的clone()方法而不会抛出CloneNotSupportedException");
        System.out.println("3. 数组的clone()方法执行的是浅拷贝，只复制数组引用而不复制元素");
        
        // 演示数组clone方法的特殊行为
        demonstrateArrayClone();
    }
    
    /**
     * 打印类的所有方法
     * @param clazz 要打印方法的类
     */
    public static void printMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, (m1, m2) -> m1.getName().compareTo(m2.getName()));
        
        for (Method method : methods) {
            System.out.println(methodToString(method));
        }
        
        // 如果是数组类，还需要打印从Object继承的方法
        if (clazz.isArray()) {
            System.out.println("\n--- 从Object继承的方法 ---");
            for (Method method : Object.class.getDeclaredMethods()) {
                System.out.println(methodToString(method) + " (从Object继承)");
            }
        }
    }
    
    /**
     * 将Method对象转换为字符串表示
     * @param method 方法对象
     * @return 方法的字符串表示
     */
    public static String methodToString(Method method) {
        StringBuilder result = new StringBuilder();
        
        // 添加修饰符
        int modifiers = method.getModifiers();
        if (modifiers != 0) {
            result.append(Modifier.toString(modifiers)).append(" ");
        }
        
        // 添加返回类型
        result.append(method.getReturnType().getSimpleName()).append(" ");
        
        // 添加方法名
        result.append(method.getName()).append("(");
        
        // 添加参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) result.append(", ");
            result.append(parameterTypes[i].getSimpleName());
        }
        
        result.append(")");
        
        // 添加异常类型
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            result.append(" throws ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0) result.append(", ");
                result.append(exceptionTypes[i].getSimpleName());
            }
        }
        
        return result.toString();
    }
    
    /**
     * 演示数组clone方法的特殊行为
     */
    public static void demonstrateArrayClone() {
        System.out.println("\n=== 演示数组clone方法 ===");
        
        // 创建一个int数组
        int[] originalArray = {1, 2, 3, 4, 5};
        System.out.println("原始数组: " + Arrays.toString(originalArray));
        
        // 克隆数组（不需要强制类型转换也不会抛出异常）
        int[] clonedArray = originalArray.clone();
        System.out.println("克隆数组: " + Arrays.toString(clonedArray));
        
        // 修改原始数组，克隆数组不受影响（对于基本类型数组）
        originalArray[0] = 99;
        System.out.println("修改后的原始数组: " + Arrays.toString(originalArray));
        System.out.println("克隆数组（不受影响）: " + Arrays.toString(clonedArray));
        
        // 对于引用类型数组，clone只是浅拷贝
        System.out.println("\n--- 引用类型数组的浅拷贝 ---");
        String[] originalStringArray = {"one", "two", "three"};
        String[] clonedStringArray = originalStringArray.clone();
        
        System.out.println("原始字符串数组: " + Arrays.toString(originalStringArray));
        System.out.println("克隆字符串数组: " + Arrays.toString(clonedStringArray));
        
        // 修改引用值
        originalStringArray[0] = "CHANGED";
        System.out.println("修改原始数组第一个元素: " + originalStringArray[0]);
        System.out.println("原始字符串数组: " + Arrays.toString(originalStringArray));
        System.out.println("克隆字符串数组（不受影响，因为数组元素是独立的引用）: " + Arrays.toString(clonedStringArray));
        
        // 对于对象数组，证明是浅拷贝
        System.out.println("\n--- 对象数组的浅拷贝 ---");
        Person[] people = {new Person("张三", 30), new Person("李四", 25)};
        Person[] clonedPeople = people.clone();
        
        // 这里无法直接打印Person数组，因为Person类没有重写toString方法
        // 我们使用循环来打印
        System.out.println("原始Person数组长度: " + people.length);
        System.out.println("克隆Person数组长度: " + clonedPeople.length);
        
        // 修改原始数组中对象的状态
        System.out.println("修改原始数组中第一个Person的姓名");
        people[0] = new Person("王五", 22);
        
        System.out.println("原始数组第一个人: " + people[0].getName() + ", " + people[0].getAge());
        System.out.println("克隆数组第一个人: " + clonedPeople[0].getName() + ", " + clonedPeople[0].getAge());
        
        System.out.println("\n结论: 数组的clone()方法是一个特例，它不需要显式实现Cloneable接口，不会抛出异常，并执行浅拷贝。");
    }
} 