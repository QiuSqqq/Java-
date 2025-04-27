package com.qius.exit5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 问题7：编写一个程序展示更多的异常处理技术
 * 包括try-catch-finally、多重catch、异常链、自定义异常等
 */
public class exit5_7 {
    
    /**
     * 自定义异常类：配置异常
     */
    public static class ConfigurationException extends Exception {
        public ConfigurationException(String message) {
            super(message);
        }
        
        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 自定义异常类：验证异常
     */
    public static class ValidationException extends Exception {
        private String fieldName;
        
        public ValidationException(String fieldName, String message) {
            super(message);
            this.fieldName = fieldName;
        }
        
        public String getFieldName() {
            return fieldName;
        }
    }
    
    /**
     * 演示多重catch块处理不同类型的异常
     * @param fileName 要读取的文件名
     */
    public static void demonstrateMultipleCatch(String fileName) {
        System.out.println("\n=== 演示多重catch块 ===");
        
        FileInputStream fis = null;
        try {
            System.out.println("尝试打开文件: " + fileName);
            fis = new FileInputStream(fileName);
            
            // 尝试读取一个字节
            int data = fis.read();
            System.out.println("读取到数据: " + data);
            
            // 尝试将"abc"转换为整数，会抛出NumberFormatException
            int number = Integer.parseInt("abc");
            
        } catch (FileNotFoundException e) {
            // 处理文件未找到的情况
            System.err.println("错误: 找不到文件 " + fileName);
            System.err.println("异常信息: " + e.getMessage());
            
        } catch (IOException e) {
            // 处理其他IO异常
            System.err.println("错误: 读取文件时发生IO错误");
            System.err.println("异常信息: " + e.getMessage());
            
        } catch (NumberFormatException e) {
            // 处理数字格式异常
            System.err.println("错误: 数字格式不正确");
            System.err.println("异常信息: " + e.getMessage());
            
        } finally {
            // 确保文件被关闭
            System.out.println("执行finally块: 清理资源");
            if (fis != null) {
                try {
                    fis.close();
                    System.out.println("文件已关闭");
                } catch (IOException e) {
                    System.err.println("关闭文件时发生错误: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * 演示异常链：一个异常导致另一个异常
     * @param configFile 配置文件名
     */
    public static void demonstrateExceptionChaining(String configFile) {
        System.out.println("\n=== 演示异常链 ===");
        
        try {
            loadConfiguration(configFile);
            System.out.println("配置加载成功");
            
        } catch (ConfigurationException e) {
            System.err.println("配置加载失败: " + e.getMessage());
            
            // 获取并打印原始异常信息（异常链）
            Throwable cause = e.getCause();
            if (cause != null) {
                System.err.println("原始异常: " + cause.getClass().getName());
                System.err.println("原始异常信息: " + cause.getMessage());
            }
        }
    }
    
    /**
     * 加载配置文件，演示如何链接异常
     * @param configFile 配置文件路径
     * @throws ConfigurationException 如果配置无法加载
     */
    private static void loadConfiguration(String configFile) throws ConfigurationException {
        try {
            // 尝试读取配置文件
            FileInputStream fis = new FileInputStream(configFile);
            // ... 处理配置文件的代码 ...
            fis.close();
            
        } catch (FileNotFoundException e) {
            // 将原始异常链接到新的、更高级别的异常
            throw new ConfigurationException("无法找到配置文件: " + configFile, e);
            
        } catch (IOException e) {
            // 同样，链接IO异常
            throw new ConfigurationException("读取配置文件时发生错误", e);
        }
    }
    
    /**
     * 演示自定义异常和异常处理
     */
    public static void demonstrateCustomExceptions() {
        System.out.println("\n=== 演示自定义异常 ===");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("请输入用户名 (至少3个字符): ");
            String username = scanner.nextLine();
            
            System.out.print("请输入年龄: ");
            int age = scanner.nextInt();
            
            // 验证用户输入
            validateUserInput(username, age);
            
            System.out.println("验证通过！用户名: " + username + ", 年龄: " + age);
            
        } catch (ValidationException e) {
            System.err.println("验证错误 [字段: " + e.getFieldName() + "]: " + e.getMessage());
            
        } catch (InputMismatchException e) {
            System.err.println("输入错误: 请输入有效的数字作为年龄");
        }
    }
    
    /**
     * 验证用户输入，如果无效则抛出自定义异常
     * @param username 用户名
     * @param age 年龄
     * @throws ValidationException 如果验证失败
     */
    private static void validateUserInput(String username, int age) throws ValidationException {
        // 验证用户名
        if (username == null || username.length() < 3) {
            throw new ValidationException("username", "用户名必须至少包含3个字符");
        }
        
        // 验证年龄
        if (age < 0 || age > 120) {
            throw new ValidationException("age", "年龄必须在0到120之间");
        }
    }
    
    /**
     * 演示try-with-resources语法（Java 7+）
     * @param fileName 文件名
     */
    public static void demonstrateTryWithResources(String fileName) {
        System.out.println("\n=== 演示try-with-resources ===");
        
        // 使用try-with-resources自动关闭资源
        try (FileInputStream fis = new FileInputStream(fileName)) {
            
            System.out.println("文件已打开: " + fileName);
            int data = fis.read();
            System.out.println("读取到的第一个字节: " + data);
            
            // 即使在此处抛出异常，FileInputStream也会被自动关闭
            
        } catch (FileNotFoundException e) {
            System.err.println("错误: 找不到文件 " + fileName);
            
        } catch (IOException e) {
            System.err.println("错误: 读取文件时发生IO错误");
            System.err.println("异常信息: " + e.getMessage());
        }
        
        // 不需要finally块来关闭资源，try-with-resources会处理
    }
    
    public static void main(String[] args) {
        System.out.println("=== 异常处理技术演示 ===");
        
        // 1. 演示多重catch块
        demonstrateMultipleCatch("nonexistent_file.txt");
        
        // 2. 演示异常链
        demonstrateExceptionChaining("config.properties");
        
        // 3. 演示自定义异常
        demonstrateCustomExceptions();
        
        // 4. 演示try-with-resources
        String sampleFile = "src/main/java/com/qius/exit5/numbers.txt";
        demonstrateTryWithResources(sampleFile);
    }
} 