package com.qius.exit1;

/**
 * 问题9：1.5.3小节的示例中，使用s.equals(t)比较两个字符串s和t，而不能使用s != t。
 * 提出一个不使用substring的不同示例。
 * 
 * 本程序展示了如何：
 * 1. 使用不同方式创建内容相同但引用不同的字符串
 * 2. 比较==和equals方法在字符串比较中的区别
 * 3. 演示字符串拼接的不同方式及其对象引用的影响
 */
public class exit1_9 {
    public static void main(String[] args) {
        /**
         * 创建内容相同但对象不同的字符串
         * - 使用字符串字面量和new关键字创建不同字符串对象
         * - 比较这些对象的内容和引用
         */
        // 创建两个内容相同但对象不同的字符串
        String s1 = "Hello";
        String s2 = new String("Hello");
        
        // 演示使用==和!=比较字符串的问题
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        System.out.println("s1和s2的内容相同吗？ " + s1.equals(s2)); // 比较内容
        System.out.println("s1和s2是同一个对象吗？ " + (s1 == s2));  // 比较引用
        
        /**
         * 演示不同拼接方式创建的字符串
         * - 展示编译器优化对字符串对象创建的影响
         * - 强制创建新对象的方法
         */
        // 创建内容相同但对象不同的字符串的另一种方式
        String s3 = "Java";
        String s4 = "J" + "ava"; // 编译器优化，实际上是同一个对象
        String s5 = "J" + new String("ava"); // 使用new强制创建新对象
        
        System.out.println("\ns3 = " + s3);
        System.out.println("s4 = " + s4);
        System.out.println("s5 = " + s5);
        
        // 使用==和equals比较s3、s4和s5
        System.out.println("s3 == s4: " + (s3 == s4)); // true，因为编译器优化
        System.out.println("s3 == s5: " + (s3 == s5)); // false，不同对象
        System.out.println("s3.equals(s4): " + s3.equals(s4)); // true，内容相同
        System.out.println("s3.equals(s5): " + s3.equals(s5)); // true，内容相同
        
        /**
         * 比较不同方式拼接的字符串
         * - 演示变量拼接和字面量拼接的区别
         * - 说明为什么总是应该使用equals进行内容比较
         */
        // 字符串拼接示例
        String part1 = "Hello";
        String part2 = "World";
        String combined1 = part1 + part2;
        String combined2 = "Hello" + "World";
        String literal = "HelloWorld";
        
        System.out.println("\ncombined1 = " + combined1);
        System.out.println("combined2 = " + combined2);
        System.out.println("literal = " + literal);
        
        // 比较拼接结果
        System.out.println("combined1 == combined2: " + (combined1 == combined2)); // 可能是false
        System.out.println("combined2 == literal: " + (combined2 == literal));     // 可能是true（编译器优化）
        System.out.println("combined1 == literal: " + (combined1 == literal));     // 可能是false
        
        // 但使用equals始终正确地比较内容
        System.out.println("combined1.equals(combined2): " + combined1.equals(combined2)); // true
        System.out.println("combined2.equals(literal): " + combined2.equals(literal));     // true
        System.out.println("combined1.equals(literal): " + combined1.equals(literal));     // true
    }
} 