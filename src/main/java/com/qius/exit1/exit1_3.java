package com.qius.exit1;

/**
 * 问题3：只使用条件运算符，编写一个读取3个整数，并打印其中的最大值的程序。
 * 然后请使用Math.max方法重复该功能。
 * 
 * 本程序展示了如何：
 * 1. 使用Scanner从控制台读取三个整数
 * 2. 使用嵌套的条件运算符(? :)找出三个数中的最大值
 * 3. 使用Math.max()方法实现同样的功能
 * 4. 比较两种方法的优缺点和实现方式
 */
import java.util.Scanner;

public class exit1_3 {
    public static void main(String[] args) {
        /**
         * 创建Scanner对象用于读取用户输入，并提示用户输入三个整数
         */
        // 创建Scanner对象，用于从标准输入（键盘）读取数据
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入三个整数并读取
        System.out.print("请输入第一个整数：");
        int a = scanner.nextInt();
        
        System.out.print("请输入第二个整数：");
        int b = scanner.nextInt();
        
        System.out.print("请输入第三个整数：");
        int c = scanner.nextInt();
        
        /**
         * 使用条件运算符找出最大值
         * - 使用嵌套的三元运算符比较三个数
         * - 先比较a和b，然后根据结果继续比较
         */
        // 方法1：使用条件运算符找出最大值
        // 条件运算符语法: (条件) ? 值1 : 值2
        // 如果条件为真，返回值1，否则返回值2
        // 这里使用嵌套的条件运算符来比较三个数
        int max1 = (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
        // 解析：
        // 1. 首先比较a和b，如果a>b，则进一步比较a和c
        //    如果a>c，则最大值是a，否则是c
        // 2. 如果a不大于b，则进一步比较b和c
        //    如果b>c，则最大值是b，否则是c
        
        /**
         * 使用Math.max方法找出最大值
         * - 嵌套调用Math.max方法
         * - 先比较a和b，再将结果与c比较
         */
        // 方法2：使用Math.max方法找出最大值
        // Math.max方法返回两个参数中的最大值
        // 这里嵌套调用Math.max来比较三个数
        int max2 = Math.max(Math.max(a, b), c);
        // 解析：
        // 1. 内层Math.max(a, b)返回a和b中的最大值
        // 2. 外层Math.max将a和b的最大值与c进行比较，返回三者中的最大值
        
        // 输出两种方法的结果
        System.out.println("使用条件运算符找出的最大值：" + max1);
        System.out.println("使用Math.max方法找出的最大值：" + max2);
        
        // 关闭Scanner，释放资源
        scanner.close();
    }
} 