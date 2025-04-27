package com.qius.exit2;

import java.util.ArrayList;

/**
 * 问题3：是否可以让修改器方法返回除void以外的其他值？能让访问器方法返回void吗？
 * 尽可能举例说明。
 */
public class exit2_3 {
    public static void main(String[] args) {
        System.out.println("问题3：修改器和访问器方法的返回值分析");
        System.out.println("------------------------------------");
        
        // 创建示例对象
        Counter counter = new Counter();
        StringBuilder stringBuilder = new StringBuilder("Hello");
        ArrayList<String> list = new ArrayList<>();
        
        // 1. 修改器方法返回非void值的例子
        System.out.println("1. 修改器方法可以返回非void值：");
        System.out.println("   原始计数器值: " + counter.getValue());
        
        int newValue = counter.increment(); // 修改器方法，返回新值
        System.out.println("   调用increment()后: " + newValue);
        
        int oldValue = counter.incrementAndGetOld(); // 修改器方法，返回旧值
        System.out.println("   调用incrementAndGetOld()后，返回旧值: " + oldValue);
        System.out.println("   当前计数器值: " + counter.getValue());
        
        System.out.println("\n   标准库中的例子：");
        System.out.println("   - StringBuilder.append() 返回StringBuilder本身");
        System.out.println("     原始: " + stringBuilder);
        StringBuilder result = stringBuilder.append(" World");
        System.out.println("     修改后: " + stringBuilder);
        System.out.println("     append()的返回值 == 原对象? " + (result == stringBuilder));
        
        System.out.println("\n   - ArrayList.add() 返回boolean值表示是否成功");
        boolean addResult = list.add("Item 1");
        System.out.println("     add()操作结果: " + addResult);
        
        System.out.println("\n   好处：");
        System.out.println("   - 可以实现方法链式调用: obj.method1().method2().method3()");
        System.out.println("   - 可以立即使用修改后的值而不需要再次获取");
        System.out.println("   - 可以提供额外信息（如操作是否成功）");
        
        // 2. 访问器方法返回void的例子
        System.out.println("\n2. 访问器方法是否可以返回void：");
        System.out.println("   - 从严格定义上说，访问器不应该返回void");
        System.out.println("   - 访问器的目的是获取对象的状态，不返回任何值就失去了意义");
        System.out.println("   - 但是，有一些特殊情况：");
        
        User user = new User("李明", 25);
        System.out.println("\n   例如，displayInfo()方法：");
        System.out.println("   - 它不修改对象状态");
        System.out.println("   - 但它通过输出而不是返回值来提供信息");
        user.displayInfo(); // 访问器方法，但返回void
        
        System.out.println("\n   这种情况可以被视为：");
        System.out.println("   1. 特殊类型的访问器（因为不修改状态）");
        System.out.println("   2. 或者更好的设计是让它返回信息，而不是直接打印");
        
        // 总结
        System.out.println("\n总结：");
        System.out.println("1. 修改器方法可以返回非void值，这在实际中很常见");
        System.out.println("   - 返回修改后的对象自身（用于链式调用）");
        System.out.println("   - 返回修改前/后的值");
        System.out.println("   - 返回操作结果或状态");
        
        System.out.println("2. 访问器方法通常不返回void，因为其目的是提供信息");
        System.out.println("   - 但在特定情况下，可能存在不直接返回值的访问器");
        System.out.println("   - 这些方法仍不修改对象状态，但通过其他方式（如控制台输出）提供信息");
        System.out.println("   - 从设计上讲，这种方法最好重构为返回信息而不是直接处理信息");
    }
}

/**
 * 计数器类，用于演示修改器方法返回非void值
 */
class Counter {
    private int count = 0;
    
    /**
     * 访问器方法 - 获取当前计数值
     * @return 当前计数值
     */
    public int getValue() {
        return count;
    }
    
    /**
     * 修改器方法 - 增加计数并返回新值
     * 这是一个既修改状态又返回值的方法
     * @return 增加后的新计数值
     */
    public int increment() {
        count++;
        return count; // 返回新值
    }
    
    /**
     * 修改器方法 - 增加计数但返回旧值
     * 这是一个既修改状态又返回值的方法
     * @return 增加前的旧计数值
     */
    public int incrementAndGetOld() {
        int oldValue = count;
        count++;
        return oldValue; // 返回旧值
    }
}

/**
 * 用户类，用于演示返回void的访问器
 */
class User {
    private String name;
    private int age;
    
    /**
     * 构造器
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    /**
     * 访问器方法 - 获取用户名
     * @return 用户名
     */
    public String getName() {
        return name;
    }
    
    /**
     * 访问器方法 - 获取年龄
     * @return 年龄
     */
    public int getAge() {
        return age;
    }
    
    /**
     * 这是一个特殊形式的"访问器"方法 - 它不修改对象状态，
     * 但它不返回任何值，而是直接打印信息。
     * 从严格的设计角度看，最好让它返回格式化的字符串，而不是直接打印。
     */
    public void displayInfo() {
        System.out.println("   用户: " + name + ", 年龄: " + age);
    }
} 
