package com.qius.exit4;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题10：Class类有6个方法，它们生成Class对象所表示类型的字符串表示。
 * 当应用于数组、泛型类型、内部类和基本类型时，它们有何不同？
 * 
 * 这6个方法是：
 * 1. getName()
 * 2. getSimpleName()
 * 3. getCanonicalName()
 * 4. getTypeName()
 * 5. toString()
 * 6. toGenericString()
 */
public class exit4_10<T> {
    // 定义一个泛型类型参数
    private T value;
    
    // 定义一些内部类/接口用于测试
    public class InnerClass {}
    public static class StaticNestedClass {}
    private interface InnerInterface {}
    
    public static void main(String[] args) {
        // 创建实例以便访问非静态内部类
        exit4_10<?> instance = new exit4_10<>();
        
        // 测试普通类
        System.out.println("=== 普通类 ===");
        testClass(String.class);
        
        // 测试匿名内部类
        Object anonymousObj = new Object() {};
        System.out.println("\n=== 匿名内部类 ===");
        testClass(anonymousObj.getClass());
        
        // 测试数组类型
        System.out.println("\n=== 数组类型 ===");
        testClass(int[].class);
        testClass(String[][].class);
        
        // 测试基本类型
        System.out.println("\n=== 基本类型 ===");
        testClass(int.class);
        testClass(void.class);
        
        // 测试泛型类型
        System.out.println("\n=== 泛型类型 ===");
        testClass(exit4_10.class);
        testClass(ArrayList.class);
        
        // 测试参数化类型
        testParameterizedTypes();
        
        // 测试内部类/嵌套类
        System.out.println("\n=== 内部类/嵌套类 ===");
        testClass(instance.new InnerClass().getClass());
        testClass(StaticNestedClass.class);
        testClass(InnerInterface.class);
        
        // 打印总结
        System.out.println("\n=== 总结 ===");
        System.out.println("1. getName():");
        System.out.println("   - 返回类的完全限定名");
        System.out.println("   - 对于数组，使用特殊符号表示（例如[I表示int[]）");
        System.out.println("   - 对于内部类，使用$分隔（例如outer$inner）");
        
        System.out.println("\n2. getSimpleName():");
        System.out.println("   - 返回源码中使用的简单类名，不包含包名和外部类");
        System.out.println("   - 对于匿名类，返回空字符串");
        System.out.println("   - 对于数组，返回组件类型的简单名加上[]");
        
        System.out.println("\n3. getCanonicalName():");
        System.out.println("   - 返回Java语言规范定义的标准名称");
        System.out.println("   - 对于匿名类，局部类和某些数组，可能返回null");
        System.out.println("   - 对于内部类，使用.分隔（例如outer.inner）");
        
        System.out.println("\n4. getTypeName():");
        System.out.println("   - 返回类型的名称，包括泛型参数信息");
        System.out.println("   - Java 8引入，主要用于处理泛型和类型推断");
        
        System.out.println("\n5. toString():");
        System.out.println("   - 返回字符串\"class\"或\"interface\"加上类的完全限定名");
        System.out.println("   - 格式为\"(class|interface) fully.qualified.Name\"");
        
        System.out.println("\n6. toGenericString():");
        System.out.println("   - 返回包含修饰符、泛型信息的完整类型描述");
        System.out.println("   - 包含public, abstract等修饰符信息");
        System.out.println("   - 包含类型参数信息，如<T>或<E extends Number>");
    }
    
    /**
     * 测试Class类的6个方法在给定类上的输出
     * @param clazz 要测试的类
     */
    private static void testClass(Class<?> clazz) {
        System.out.println("类: " + clazz);
        System.out.println("1. getName():          " + clazz.getName());
        System.out.println("2. getSimpleName():    " + clazz.getSimpleName());
        System.out.println("3. getCanonicalName(): " + clazz.getCanonicalName());
        System.out.println("4. getTypeName():      " + clazz.getTypeName());
        System.out.println("5. toString():         " + clazz.toString());
        System.out.println("6. toGenericString():  " + clazz.toGenericString());
        
        // 如果类有类型参数，打印它们
        TypeVariable<?>[] typeParams = clazz.getTypeParameters();
        if (typeParams.length > 0) {
            System.out.println("类型参数:");
            for (TypeVariable<?> typeParam : typeParams) {
                System.out.println("  - " + typeParam.getName());
            }
        }
    }
    
    /**
     * 测试参数化类型
     */
    private static void testParameterizedTypes() {
        System.out.println("\n=== 参数化类型 ===");
        
        // 创建一些具体的参数化类型实例
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        
        // 由于类型擦除，它们的Class对象是相同的
        System.out.println("ArrayList<String>和ArrayList<Integer>的Class对象相同?: " 
            + (stringList.getClass() == intList.getClass()));
        
        // 打印Class方法输出
        System.out.println("ArrayList<String>的类信息:");
        testClass(stringList.getClass());
        
        System.out.println("\nHashMap<String, Integer>的类信息:");
        testClass(map.getClass());
        
        // 注意：由于类型擦除，无法在运行时获取类型参数T的实际类型
        System.out.println("\n类型擦除导致无法通过Class直接获取泛型参数的具体类型");
        System.out.println("例如，无法区分List<String>和List<Integer>");
    }
} 