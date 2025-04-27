package com.qius.exit5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 问题5：实现一个使用Scanner和PrintWriter，但不使用try-with-resources的方法，
 * 确保即使抛出异常，这些资源也能得到适当关闭。
 */
public class exit5_5 {
    
    /**
     * 从输入文件中读取内容并写入到输出文件，适当处理和关闭资源
     * @param inputFile 输入文件路径
     * @param outputFile 输出文件路径
     * @throws IOException 如果发生I/O错误
     */
    public static void copyFile(String inputFile, String outputFile) throws IOException {
        Scanner scanner = null;
        PrintWriter writer = null;
        
        try {
            // 初始化Scanner用于读取输入文件
            scanner = new Scanner(new File(inputFile));
            
            // 初始化PrintWriter用于写入输出文件
            writer = new PrintWriter(outputFile);
            
            // 从输入文件读取每一行并写入到输出文件
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.println(line);
            }
            
            // 确保所有内容都被写入
            writer.flush();
            
            System.out.println("文件复制成功: " + inputFile + " -> " + outputFile);
            
        } catch (FileNotFoundException e) {
            throw new IOException("无法找到指定的文件: " + e.getMessage(), e);
        } finally {
            // 在finally块中关闭资源，确保资源始终会被关闭
            try {
                // 先检查然后关闭Scanner
                if (scanner != null) {
                    scanner.close();
                }
            } finally {
                // 嵌套的finally块确保即使关闭Scanner抛出异常，也会尝试关闭PrintWriter
                if (writer != null) {
                    writer.close();
                    // PrintWriter的close()方法不会抛出IOException，但为了一致性保留了嵌套结构
                }
            }
        }
    }
    
    /**
     * 使用Scanner读取用户输入并写入到文件中，适当处理和关闭资源
     * @param outputFile 输出文件路径
     * @throws IOException 如果发生I/O错误
     */
    public static void writeUserInputToFile(String outputFile) throws IOException {
        Scanner consoleScanner = null;
        PrintWriter writer = null;
        
        try {
            // 初始化Scanner读取控制台输入
            consoleScanner = new Scanner(System.in);
            
            // 初始化PrintWriter用于写入文件
            writer = new PrintWriter(outputFile);
            
            System.out.println("请输入内容（输入'exit'结束）：");
            
            String line;
            while (true) {
                line = consoleScanner.nextLine();
                if ("exit".equalsIgnoreCase(line)) {
                    break;
                }
                writer.println(line);
            }
            
            writer.flush();
            System.out.println("用户输入已成功写入到文件: " + outputFile);
            
        } catch (FileNotFoundException e) {
            throw new IOException("无法创建输出文件: " + e.getMessage(), e);
        } finally {
            // 在finally块中关闭资源
            try {
                // 我们不希望关闭System.in，因此只在不是System.in的情况下关闭consoleScanner
                if (consoleScanner != null && consoleScanner != new Scanner(System.in)) {
                    consoleScanner.close();
                }
            } finally {
                // 嵌套的finally块确保即使关闭Scanner抛出异常，也会尝试关闭PrintWriter
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("=== 文件操作演示 ===");
            System.out.println("1. 复制文件");
            System.out.println("2. 将用户输入写入文件");
            System.out.print("请选择操作 (1/2): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符
            
            switch (choice) {
                case 1:
                    System.out.print("请输入源文件路径: ");
                    String inputFile = scanner.nextLine();
                    System.out.print("请输入目标文件路径: ");
                    String outputFile = scanner.nextLine();
                    
                    try {
                        copyFile(inputFile, outputFile);
                    } catch (IOException e) {
                        System.err.println("文件复制失败: " + e.getMessage());
                    }
                    break;
                    
                case 2:
                    System.out.print("请输入目标文件路径: ");
                    String userOutputFile = scanner.nextLine();
                    
                    try {
                        writeUserInputToFile(userOutputFile);
                    } catch (IOException e) {
                        System.err.println("写入文件失败: " + e.getMessage());
                    }
                    break;
                    
                default:
                    System.out.println("无效的选择，请输入1或2。");
                    break;
            }
            
        } finally {
            // 关闭主控制台Scanner
            scanner.close();
        }
    }
} 