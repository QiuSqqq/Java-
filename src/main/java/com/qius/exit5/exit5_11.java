package com.qius.exit5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 问题11：编写一个程序比较自动资源管理和手动资源管理的区别
 */
public class exit5_11 {
    
    /**
     * 使用手动资源管理（传统的try-finally方式）读取文件
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException 如果发生IO错误
     */
    public static String readFileManual(String filePath) throws IOException {
        System.out.println("\n=== 使用手动资源管理读取文件 ===");
        
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        
        try {
            System.out.println("打开文件: " + filePath);
            reader = new BufferedReader(new FileReader(filePath));
            
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            
            System.out.println("成功读取文件");
            return content.toString();
            
        } finally {
            System.out.println("在finally块中关闭资源");
            
            // 手动关闭资源
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("成功关闭BufferedReader");
                } catch (IOException e) {
                    System.err.println("关闭BufferedReader时发生错误: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * 使用自动资源管理（try-with-resources）读取文件
     * @param filePath 文件路径
     * @return 文件内容
     * @throws IOException 如果发生IO错误
     */
    public static String readFileAutomatic(String filePath) throws IOException {
        System.out.println("\n=== 使用自动资源管理读取文件 ===");
        
        System.out.println("打开文件并自动管理资源: " + filePath);
        
        // 使用try-with-resources自动管理资源
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            
            System.out.println("成功读取文件");
            System.out.println("BufferedReader将被自动关闭");
            
            return content.toString();
        }
    }
    
    /**
     * 手动管理多个资源（传统方式）进行文件复制
     * @param sourcePath 源文件路径
     * @param destPath 目标文件路径
     * @throws IOException 如果发生IO错误
     */
    public static void copyFileManual(String sourcePath, String destPath) throws IOException {
        System.out.println("\n=== 使用手动资源管理复制文件 ===");
        
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
        try {
            System.out.println("打开源文件: " + sourcePath);
            reader = new BufferedReader(new FileReader(sourcePath));
            
            System.out.println("打开目标文件: " + destPath);
            writer = new BufferedWriter(new FileWriter(destPath));
            
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            
            writer.flush();
            System.out.println("成功复制文件");
            
        } finally {
            System.out.println("在finally块中关闭资源");
            
            // 注意关闭顺序：先打开的后关闭
            // 使用嵌套try块确保两个资源都能得到关闭尝试
            try {
                if (writer != null) {
                    try {
                        writer.close();
                        System.out.println("成功关闭BufferedWriter");
                    } catch (IOException e) {
                        System.err.println("关闭BufferedWriter时发生错误: " + e.getMessage());
                    }
                }
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        System.out.println("成功关闭BufferedReader");
                    } catch (IOException e) {
                        System.err.println("关闭BufferedReader时发生错误: " + e.getMessage());
                    }
                }
            }
        }
    }
    
    /**
     * 自动管理多个资源进行文件复制
     * @param sourcePath 源文件路径
     * @param destPath 目标文件路径
     * @throws IOException 如果发生IO错误
     */
    public static void copyFileAutomatic(String sourcePath, String destPath) throws IOException {
        System.out.println("\n=== 使用自动资源管理复制文件 ===");
        
        System.out.println("打开源文件和目标文件并自动管理资源");
        
        // 使用try-with-resources自动管理多个资源
        try (
            BufferedReader reader = new BufferedReader(new FileReader(sourcePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(destPath))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            
            writer.flush();
            System.out.println("成功复制文件");
            System.out.println("BufferedReader和BufferedWriter将被自动关闭，顺序为先关闭writer再关闭reader");
        }
    }
    
    /**
     * 演示可被抑制的异常
     * @param filePath 文件路径
     */
    public static void demonstrateSuppressedException(String filePath) {
        System.out.println("\n=== 演示被抑制的异常 ===");
        
        class ExceptionThrowingResource implements AutoCloseable {
            private final String name;
            private final boolean throwOnClose;
            
            public ExceptionThrowingResource(String name, boolean throwOnClose) {
                this.name = name;
                this.throwOnClose = throwOnClose;
                System.out.println("打开资源: " + name);
            }
            
            public void doOperation() throws IOException {
                System.out.println("使用资源: " + name);
                throw new IOException(name + " 在操作过程中抛出异常");
            }
            
            @Override
            public void close() throws Exception {
                System.out.println("关闭资源: " + name);
                if (throwOnClose) {
                    throw new IllegalStateException(name + " 在关闭时抛出异常");
                }
            }
        }
        
        try {
            // 这将演示当try块和close()方法都抛出异常时，会发生什么
            try (
                ExceptionThrowingResource resource1 = new ExceptionThrowingResource("Resource1", true);
                ExceptionThrowingResource resource2 = new ExceptionThrowingResource("Resource2", true)
            ) {
                resource2.doOperation();  // 这将抛出一个IOException
            }
        } catch (Exception e) {
            System.err.println("\n捕获到主异常: " + e.getClass().getName() + ": " + e.getMessage());
            
            // 获取并打印被抑制的异常
            Throwable[] suppressed = e.getSuppressed();
            if (suppressed.length > 0) {
                System.err.println("被抑制的异常数量: " + suppressed.length);
                for (int i = 0; i < suppressed.length; i++) {
                    System.err.println("被抑制异常 #" + (i + 1) + ": " +
                                     suppressed[i].getClass().getName() + ": " +
                                     suppressed[i].getMessage());
                }
            } else {
                System.err.println("没有被抑制的异常");
            }
        }
    }
    
    /**
     * 比较两种方法的代码复杂度和安全性
     */
    public static void compareApproaches() {
        System.out.println("\n=== 比较手动和自动资源管理 ===");
        
        System.out.println("1. 代码数量:");
        System.out.println("   - 手动资源管理: 更多的代码行，包括try和finally块，以及额外的null检查");
        System.out.println("   - 自动资源管理: 更少的代码行，更简洁，更易读");
        
        System.out.println("\n2. 安全性:");
        System.out.println("   - 手动资源管理: 容易出错，比如忘记关闭资源或异常处理不当");
        System.out.println("   - 自动资源管理: 更安全，即使发生异常，也能确保资源被关闭");
        
        System.out.println("\n3. 异常处理:");
        System.out.println("   - 手动资源管理: 需要嵌套try-catch块处理资源关闭时的异常");
        System.out.println("   - 自动资源管理: 支持被抑制的异常，保留原始异常和关闭时的异常");
        
        System.out.println("\n4. 维护性:");
        System.out.println("   - 手动资源管理: 当增加更多资源时，代码复杂度会显著增加");
        System.out.println("   - 自动资源管理: 易于扩展，添加新资源只需在try括号中添加声明");
        
        System.out.println("\n结论: 自动资源管理(try-with-resources)通常是更好的选择，提供更简洁、更安全的代码。");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 比较自动资源管理和手动资源管理 ===");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("输入要读取的文件路径 (例如 src/main/java/com/qius/exit5/numbers.txt): ");
            String filePath = scanner.nextLine();
            
            System.out.print("输入目标文件路径进行复制 (例如 src/main/java/com/qius/exit5/numbers_copy.txt): ");
            String destPath = scanner.nextLine();
            
            // 1. 使用手动资源管理读取文件
            try {
                String contentManual = readFileManual(filePath);
                System.out.println("读取到的内容长度: " + contentManual.length() + " 字符");
            } catch (IOException e) {
                System.err.println("手动资源管理读取失败: " + e.getMessage());
            }
            
            // 2. 使用自动资源管理读取文件
            try {
                String contentAutomatic = readFileAutomatic(filePath);
                System.out.println("读取到的内容长度: " + contentAutomatic.length() + " 字符");
            } catch (IOException e) {
                System.err.println("自动资源管理读取失败: " + e.getMessage());
            }
            
            // 3. 使用手动资源管理复制文件
            try {
                copyFileManual(filePath, destPath + ".manual");
            } catch (IOException e) {
                System.err.println("手动资源管理复制失败: " + e.getMessage());
            }
            
            // 4. 使用自动资源管理复制文件
            try {
                copyFileAutomatic(filePath, destPath + ".automatic");
            } catch (IOException e) {
                System.err.println("自动资源管理复制失败: " + e.getMessage());
            }
            
            // 5. 演示被抑制的异常
            demonstrateSuppressedException(filePath);
            
            // 6. 比较两种方法
            compareApproaches();
            
        } finally {
            scanner.close();
        }
    }
} 