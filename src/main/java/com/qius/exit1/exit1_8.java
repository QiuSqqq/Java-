package com.qius.exit1;

/**
 * 问题8：编写一个程序，读取字符串并打印其所有非空子串。
 * 
 * 本程序展示了如何：
 * 1. 使用Scanner读取用户输入的字符串
 * 2. 使用嵌套循环和String.substring()方法生成所有子串
 * 3. 处理字符串子串的索引范围和计数
 */

import java.util.Scanner;

public class exit1_8 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 提示用户输入一个字符串
        System.out.print("请输入一个字符串：");
        String input = scanner.nextLine();
        
        // 获取字符串的长度
        int length = input.length();
        
        // 如果输入的是空字符串，给出提示并退出
        if (length == 0) {
            System.out.println("输入的是空字符串，没有非空子串。");
            scanner.close();
            return;
        }
        
        // 打印提示信息
        System.out.println("\n字符串 \"" + input + "\" 的所有非空子串如下：");
        
        // 计算子串总数：n(n+1)/2，其中n是字符串长度
        int totalSubstrings = length * (length + 1) / 2;
        System.out.println("共有 " + totalSubstrings + " 个非空子串\n");
        
        // 使用两层循环打印所有子串
        int count = 0; // 用于计数已打印的子串数量
        
        // 外层循环：子串的起始位置
        for (int start = 0; start < length; start++) {
            // 内层循环：子串的结束位置（实际是结束位置+1）
            for (int end = start + 1; end <= length; end++) {
                // 使用substring方法获取子串
                // substring(start, end)返回从索引start到end-1的子串
                String substring = input.substring(start, end);
                count++;
                
                // 格式化打印子串，同时显示起始和结束位置
                System.out.println(count + ": " + substring + 
                                  " (从位置 " + start + " 到 " + (end - 1) + ")");
            }
        }
        
        // 关闭Scanner
        scanner.close();
    }
} 