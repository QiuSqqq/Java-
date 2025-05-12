package com.qius.exit1;

/**
 * 问题2：编写一个程序，读取一个整数角度值（可能为正或负），并将其标准化为0度～359度的值。
 * 请先使用 % 运算符计算，然后使用floorMod重复实现该功能。
 * 
 * 本程序展示了如何：
 * 1. 使用Scanner从控制台读取整数角度值
 * 2. 使用取模运算符(%)将角度标准化到0-359度范围
 * 3. 使用Math.floorMod()方法实现同样的标准化效果
 * 4. 处理取模运算符对负数的特殊情况
 */
import java.util.Scanner;

public class exit1_2 {
    public static void main(String[] args) {
        /**
         * 创建Scanner对象用于读取用户输入，并提示用户输入一个整数角度值
         */
        // 创建Scanner对象，用于从标准输入（键盘）读取数据
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入一个整数角度值
        System.out.print("请输入一个整数角度值（可正可负）：");
        
        // 读取用户输入的整数角度值
        int angle = scanner.nextInt();
        
        /**
         * 使用取模运算符(%)标准化角度
         * - 先计算angle对360的余数
         * - 处理负数结果：如果余数小于0，加上360使其为正
         */
        // 方法1：使用 % 运算符标准化角度
        // Java中，%运算符的结果与被除数同号，所以负角度需要特殊处理
        int normalizedAngle1 = angle % 360;
        
        // 如果结果是负数，需要加上360使其变为正数
        if (normalizedAngle1 < 0) {
            normalizedAngle1 += 360;
        }
        
        /**
         * 使用Math.floorMod标准化角度
         * - floorMod方法总是返回非负结果
         * - 对于负数输入，自动进行适当处理
         */
        // 方法2：使用 floorMod 标准化角度
        // floorMod方法总是返回非负结果，所以无需额外处理
        // Math.floorMod会对负数进行特殊处理，使结果总是在0到模数之间
        int normalizedAngle2 = Math.floorMod(angle, 360);
        
        // 输出两种方法的结果
        System.out.println("使用 % 运算符标准化后的角度：" + normalizedAngle1);
        System.out.println("使用 floorMod 标准化后的角度：" + normalizedAngle2);
        
        // 关闭Scanner，释放资源
        scanner.close();
    }
} 