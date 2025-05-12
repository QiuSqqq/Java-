package com.qius.exit5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 问题1-3：实现文件读取和处理的方法，包括异常处理
 * 1. 编写一个方法readValues，读取包含浮点数的文件
 * 2. 编写一个方法sumOfValues，调用readValues并返回文件中值的总和
 * 3. 编写一个程序，调用这些方法并处理异常
 * 
 * 本程序展示了如何：
 * 1. 使用文件读取和异常处理机制处理外部文件数据
 * 2. 实现数值解析和累加功能
 * 3. 使用try-catch-finally结构处理各种可能的异常情况
 * 4. 优雅地向用户提供错误反馈
 */
public class exit5_1to3 {
    
    /**
     * 读取包含浮点数的文件并返回所有数值的列表
     * - 打开并读取指定的文件
     * - 将每行内容解析为浮点数
     * - 将所有有效的浮点数收集到ArrayList中
     * - 提供详细的异常信息，包括行号
     */
    public static ArrayList<Double> readValues(String filename) throws IOException, NumberFormatException {
        ArrayList<Double> values = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // 跳过空行
                if (line.isEmpty()) {
                    continue;
                }
                
                try {
                    // 尝试将每行转换为浮点数
                    double value = Double.parseDouble(line);
                    values.add(value);
                } catch (NumberFormatException e) {
                    // 抛出包含行号信息的异常
                    throw new NumberFormatException("第 " + lineNumber + " 行无法解析为浮点数: '" + line + "'");
                }
            }
        }
        
        return values;
    }
    
    /**
     * 计算文件中所有浮点数的总和
     * - 调用readValues方法获取文件中的所有浮点数
     * - 遍历列表计算总和
     * - 传递可能出现的异常供上层处理
     */
    public static double sumOfValues(String filename) throws IOException, NumberFormatException {
        // 调用readValues方法获取值列表
        ArrayList<Double> values = readValues(filename);
        
        // 计算总和
        double sum = 0.0;
        for (Double value : values) {
            sum += value;
        }
        
        return sum;
    }
    
    public static void main(String[] args) {
        /**
         * 创建Scanner对象用于读取用户输入
         * - 获取要处理的文件名
         */
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== 文件浮点数求和程序 ===");
        System.out.print("请输入要读取的文件名: ");
        String filename = scanner.nextLine();
        
        /**
         * 尝试计算文件中的浮点数总和并处理可能出现的异常
         * - 调用sumOfValues和readValues方法完成主要功能
         * - 捕获并处理各种可能的异常，提供友好的错误信息
         * - 确保无论是否出现异常，都会释放Scanner资源
         */
        try {
            // 调用sumOfValues方法计算总和
            double sum = sumOfValues(filename);
            
            // 打印结果
            System.out.println("文件中浮点数的总和为: " + sum);
            
            // 为了更详细的输出，也可以打印所有读取的值
            ArrayList<Double> values = readValues(filename);
            System.out.println("文件中包含的浮点数: " + values);
            System.out.println("共读取了 " + values.size() + " 个浮点数");
            
        } catch (NoSuchFileException e) {
            System.err.println("错误: 找不到文件 '" + filename + "'");
            System.err.println("请确认文件路径是否正确，以及是否有读取权限。");
        } catch (IOException e) {
            System.err.println("错误: 读取文件时发生I/O错误");
            System.err.println("详细信息: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("错误: 文件格式不正确");
            System.err.println("详细信息: " + e.getMessage());
        } finally {
            // 关闭Scanner，释放资源
            scanner.close();
        }
    }
} 