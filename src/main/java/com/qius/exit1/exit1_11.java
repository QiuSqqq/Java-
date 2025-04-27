package com.qius.exit1; /**
 * 问题11：编写一个程序，读取一行文本并打印所有非ASCII字符及其Unicode值。
 */

import java.util.Scanner;

public class exit1_11 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入一行文本
        System.out.println("请输入一行文本（包含一些非ASCII字符，如中文、表情符号等）：");
        String text = scanner.nextLine();
        
        // 打印输入的文本
        System.out.println("\n您输入的文本是：" + text);
        
        // 解释ASCII和Unicode
        System.out.println("\nASCII字符是Unicode的子集，范围是0-127（0x00-0x7F）。");
        System.out.println("任何大于127（0x7F）的Unicode值都是非ASCII字符。\n");
        
        // 初始化计数器
        int nonAsciiCount = 0;
        
        // 遍历字符串中的每个字符
        System.out.println("非ASCII字符及其Unicode值：");
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            // 检查是否为非ASCII字符
            // ASCII字符的范围是0-127
            if (c > 127) {
                nonAsciiCount++;
                
                // 打印字符及其Unicode值
                // Integer.toHexString将整数转换为十六进制字符串
                // String.format格式化输出，%04X表示输出4位十六进制数（不足4位前面补0）
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