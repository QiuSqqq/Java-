package com.qius.exit1;

/**
 * 问题11：编写一个程序，读取一行文本并打印所有非ASCII字符及其Unicode值。
 * 
 * 本程序展示了如何：
 * 1. 使用Scanner从控制台读取包含各种字符的文本
 * 2. 检测并识别文本中的非ASCII字符
 * 3. 输出非ASCII字符的Unicode十六进制值
 * 4. 展示一些常见的非ASCII字符例子
 */
import java.util.Scanner;

public class exit1_11 {
    public static void main(String[] args) {
        /**
         * 创建Scanner对象并读取用户输入的文本
         * - 提示用户输入包含非ASCII字符的文本
         * - 显示输入的原始文本
         */
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入一行文本
        System.out.println("请输入一行文本（包含一些非ASCII字符，如中文、表情符号等）：");
        String text = scanner.nextLine();
        
        // 打印输入的文本
        System.out.println("\n您输入的文本是：" + text);
        
        /**
         * 解释ASCII和Unicode的关系
         * - 说明ASCII是Unicode的子集
         * - 定义非ASCII字符的范围
         */
        // 解释ASCII和Unicode
        System.out.println("\nASCII字符是Unicode的子集，范围是0-127（0x00-0x7F）。");
        System.out.println("任何大于127（0x7F）的Unicode值都是非ASCII字符。\n");
        
        /**
         * 遍历文本检测非ASCII字符
         * - 检查每个字符的Unicode值是否大于127
         * - 统计并显示非ASCII字符的信息
         * - 提供字符位置、字符本身和Unicode十六进制值
         */
        // 初始化计数器
        int nonAsciiCount = 0;
        
        // 遍历字符串中的每个字符
        System.out.println("非ASCII字符及其Unicode值：");
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            // 检查是否为非ASCII字符
            if (c > 127) {
                nonAsciiCount++;
                
                // 打印字符及其Unicode值
                String unicodeValue = String.format("U+%04X", (int) c);
                System.out.println("位置 " + i + ": 字符 '" + c + "' 的Unicode值是 " + unicodeValue);
            }
        }
        
        // 打印统计信息
        if (nonAsciiCount == 0) {
            System.out.println("\n没有找到非ASCII字符。");
        } else {
            System.out.println("\n总共找到 " + nonAsciiCount + " 个非ASCII字符。");
        }
        
        /**
         * 展示常见非ASCII字符示例
         * - 显示不同语言和特殊符号的字符
         * - 输出它们的Unicode十六进制值
         */
        // 展示一些常见的非ASCII字符及其Unicode值
        System.out.println("\n一些常见的非ASCII字符及其Unicode值：");
        System.out.println("中文 '中': U+" + Integer.toHexString(Character.codePointAt("中", 0)).toUpperCase());
        System.out.println("日文 'に': U+" + Integer.toHexString(Character.codePointAt("に", 0)).toUpperCase());
        System.out.println("欧元符号 '€': U+" + Integer.toHexString(Character.codePointAt("€", 0)).toUpperCase());
        System.out.println("版权符号 '©': U+" + Integer.toHexString(Character.codePointAt("©", 0)).toUpperCase());
        
        // 关闭Scanner
        scanner.close();
    }
} 