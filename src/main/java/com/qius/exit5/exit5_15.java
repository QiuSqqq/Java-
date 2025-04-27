package com.qius.exit5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

/**
 * 问题15：比较各种不同的异常处理技术
 * 包括：传统的try-catch、错误代码、可选值（Optional）和函数式方法
 */
public class exit5_15 {
    
    private static final String SAMPLE_FILE = "src/main/java/com/qius/exit5/numbers.txt";
    
    //========== 方法一：传统的try-catch异常处理 ==========
    
    /**
     * 使用传统的异常处理方式读取文件并计算总和
     * @param filename 文件路径
     * @return 文件中所有数字的总和
     * @throws IOException 如果发生IO错误
     */
    public static double sumFileWithExceptions(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            double sum = 0;
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    double value = Double.parseDouble(line.trim());
                    sum += value;
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("第 " + lineNumber + " 行无法解析为数字: " + line);
                }
            }
            
            return sum;
        }
    }
    
    /**
     * 演示使用传统异常处理的客户端代码
     */
    public static void demoTraditionalExceptions(String filename) {
        System.out.println("\n=== 演示传统的异常处理 ===");
        System.out.println("使用try-catch处理异常");
        
        try {
            double sum = sumFileWithExceptions(filename);
            System.out.println("文件中数字的总和: " + sum);
        } catch (IOException e) {
            System.err.println("IO错误: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("数字格式错误: " + e.getMessage());
        }
    }
    
    //========== 方法二：错误代码返回 ==========
    
    // 错误代码常量
    public static final int SUCCESS = 0;
    public static final int FILE_NOT_FOUND = 1;
    public static final int IO_ERROR = 2;
    public static final int PARSE_ERROR = 3;
    
    // 错误消息
    private static String errorMessage = "";
    
    /**
     * 使用错误代码返回方式读取文件并计算总和
     * @param filename 文件路径
     * @param result 用于存储结果的数组（输出参数）
     * @return 错误代码，0表示成功
     */
    public static int sumFileWithErrorCodes(String filename, double[] result) {
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(filename));
            double sum = 0;
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    double value = Double.parseDouble(line.trim());
                    sum += value;
                } catch (NumberFormatException e) {
                    errorMessage = "第 " + lineNumber + " 行无法解析为数字: " + line;
                    return PARSE_ERROR;
                }
            }
            
            result[0] = sum;
            return SUCCESS;
            
        } catch (java.io.FileNotFoundException e) {
            errorMessage = "找不到文件: " + filename;
            return FILE_NOT_FOUND;
        } catch (IOException e) {
            errorMessage = "读取文件时发生IO错误: " + e.getMessage();
            return IO_ERROR;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 如果已经有其他错误，保留原始错误信息
                    if (errorMessage.isEmpty()) {
                        errorMessage = "关闭文件时发生错误: " + e.getMessage();
                        return IO_ERROR;
                    }
                }
            }
        }
    }
    
    /**
     * 获取最后的错误消息
     * @return 错误消息
     */
    public static String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * 演示使用错误代码的客户端代码
     */
    public static void demoErrorCodes(String filename) {
        System.out.println("\n=== 演示错误代码返回方式 ===");
        System.out.println("使用整数错误代码和输出参数");
        
        double[] result = new double[1];
        int errorCode = sumFileWithErrorCodes(filename, result);
        
        switch (errorCode) {
            case SUCCESS:
                System.out.println("文件中数字的总和: " + result[0]);
                break;
            case FILE_NOT_FOUND:
                System.err.println("找不到文件: " + getErrorMessage());
                break;
            case IO_ERROR:
                System.err.println("IO错误: " + getErrorMessage());
                break;
            case PARSE_ERROR:
                System.err.println("解析错误: " + getErrorMessage());
                break;
            default:
                System.err.println("未知错误，代码: " + errorCode);
                break;
        }
    }
    
    //========== 方法三：使用Optional返回值 ==========
    
    /**
     * 表示文件求和操作的结果
     */
    public static class SumResult {
        private final double sum;
        private final String errorMessage;
        private final boolean success;
        
        private SumResult(double sum) {
            this.sum = sum;
            this.errorMessage = null;
            this.success = true;
        }
        
        private SumResult(String errorMessage) {
            this.sum = 0;
            this.errorMessage = errorMessage;
            this.success = false;
        }
        
        public static SumResult success(double sum) {
            return new SumResult(sum);
        }
        
        public static SumResult failure(String errorMessage) {
            return new SumResult(errorMessage);
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public double getSum() {
            return sum;
        }
        
        public String getErrorMessage() {
            return errorMessage;
        }
    }
    
    /**
     * 使用Optional和结果对象读取文件并计算总和
     * @param filename 文件路径
     * @return 包含结果或错误的Optional对象
     */
    public static Optional<SumResult> sumFileWithOptional(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            double sum = 0;
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    double value = Double.parseDouble(line.trim());
                    sum += value;
                } catch (NumberFormatException e) {
                    return Optional.of(SumResult.failure("第 " + lineNumber + " 行无法解析为数字: " + line));
                }
            }
            
            return Optional.of(SumResult.success(sum));
            
        } catch (java.io.FileNotFoundException e) {
            return Optional.of(SumResult.failure("找不到文件: " + filename));
        } catch (IOException e) {
            return Optional.of(SumResult.failure("读取文件时发生IO错误: " + e.getMessage()));
        }
    }
    
    /**
     * 演示使用Optional的客户端代码
     */
    public static void demoOptional(String filename) {
        System.out.println("\n=== 演示使用Optional返回值 ===");
        System.out.println("使用Optional和结果对象");
        
        Optional<SumResult> result = sumFileWithOptional(filename);
        
        result.ifPresent(r -> {
            if (r.isSuccess()) {
                System.out.println("文件中数字的总和: " + r.getSum());
            } else {
                System.err.println("操作失败: " + r.getErrorMessage());
            }
        });
        
        // 另一种处理方式
        SumResult sumResult = result.orElse(SumResult.failure("无法获取结果"));
        
        if (!sumResult.isSuccess()) {
            System.err.println("备用错误处理: " + sumResult.getErrorMessage());
        }
    }
    
    //========== 方法四：函数式方法 ==========
    
    /**
     * 使用函数式风格读取文件的行
     * @param filename 文件路径
     * @return 文件中的行列表或空列表（如果发生错误）
     */
    public static List<String> readLinesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            return lines;
        } catch (IOException e) {
            System.err.println("读取文件时发生错误: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * 尝试将字符串解析为双精度浮点数
     * @param s 要解析的字符串
     * @return 解析的双精度浮点数或0（如果解析失败）
     */
    public static double safeParseDouble(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            System.err.println("无法解析为数字: " + s);
            return 0;
        }
    }
    
    /**
     * 使用函数式风格计算文件中数字的总和
     * @param filename 文件路径
     * @return 总和
     */
    public static double sumFileWithFunctional(String filename) {
        return readLinesFromFile(filename).stream()
                .mapToDouble(exit5_15::safeParseDouble)
                .sum();
    }
    
    /**
     * 使用更高级的函数式方法，支持错误处理
     * @param filename 文件路径
     * @return 总和的结果
     */
    public static SumResult sumFileWithAdvancedFunctional(String filename) {
        // 使用一系列的函数转换
        Function<String, List<String>> readLines = file -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                List<String> lines = new ArrayList<>();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                
                return lines;
            } catch (IOException e) {
                throw new RuntimeException("读取文件时发生错误: " + e.getMessage(), e);
            }
        };
        
        Function<List<String>, List<Double>> parseLines = lines -> {
            List<Double> values = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                try {
                    values.add(Double.parseDouble(line.trim()));
                } catch (NumberFormatException e) {
                    throw new RuntimeException("第 " + (i + 1) + " 行无法解析为数字: " + line, e);
                }
            }
            return values;
        };
        
        Function<List<Double>, Double> sumValues = values -> 
            values.stream().mapToDouble(Double::doubleValue).sum();
        
        Function<String, SumResult> pipeline = readLines
                .andThen(parseLines)
                .andThen(sumValues)
                .andThen(SumResult::success);
        
        try {
            return pipeline.apply(filename);
        } catch (RuntimeException e) {
            return SumResult.failure(e.getMessage());
        }
    }
    
    /**
     * 演示使用函数式方法的客户端代码
     */
    public static void demoFunctional(String filename) {
        System.out.println("\n=== 演示函数式方法 ===");
        System.out.println("使用函数式风格和可组合转换");
        
        // 简单的函数式方法
        double sum = sumFileWithFunctional(filename);
        System.out.println("基本函数式方法计算的总和: " + sum);
        
        // 高级的函数式方法
        SumResult result = sumFileWithAdvancedFunctional(filename);
        if (result.isSuccess()) {
            System.out.println("高级函数式方法计算的总和: " + result.getSum());
        } else {
            System.err.println("高级函数式方法失败: " + result.getErrorMessage());
        }
    }
    
    //========== 比较四种方法 ==========
    
    /**
     * 比较不同的异常处理方法
     */
    public static void compareApproaches() {
        System.out.println("\n=== 比较不同的异常处理方法 ===");
        
        System.out.println("\n1. 传统的异常处理（try-catch）:");
        System.out.println("   优点:");
        System.out.println("   - Java标准方式，大多数开发者熟悉");
        System.out.println("   - 明确区分正常流程和错误处理");
        System.out.println("   - 可以在调用栈中传播错误信息");
        System.out.println("   缺点:");
        System.out.println("   - 可能导致代码中断和程序崩溃");
        System.out.println("   - 需要良好的文档说明哪些异常可能被抛出");
        System.out.println("   - 性能开销可能较大");
        
        System.out.println("\n2. 错误代码返回:");
        System.out.println("   优点:");
        System.out.println("   - 强制调用者处理错误情况");
        System.out.println("   - 在性能关键的场景中可能更高效");
        System.out.println("   - 常用于C和其他不支持异常的语言");
        System.out.println("   缺点:");
        System.out.println("   - 代码可读性降低");
        System.out.println("   - 需要额外的机制传递错误信息");
        System.out.println("   - 容易忽略检查返回值");
        
        System.out.println("\n3. Optional返回值:");
        System.out.println("   优点:");
        System.out.println("   - 明确表示操作可能失败");
        System.out.println("   - 提供流畅的API来处理成功和失败情况");
        System.out.println("   - 与函数式编程风格兼容");
        System.out.println("   缺点:");
        System.out.println("   - 不适合表示多种不同类型的错误");
        System.out.println("   - 可能会被误用（如不检查是否存在值）");
        System.out.println("   - 需要额外的封装对象");
        
        System.out.println("\n4. 函数式方法:");
        System.out.println("   优点:");
        System.out.println("   - 代码简洁，表达能力强");
        System.out.println("   - 可组合性高，便于创建复杂的处理管道");
        System.out.println("   - 适合数据转换和流处理");
        System.out.println("   缺点:");
        System.out.println("   - 学习曲线较陡");
        System.out.println("   - 调试可能更困难");
        System.out.println("   - 错误处理可能不够直观");
        
        System.out.println("\n推荐使用场景:");
        System.out.println("- 传统异常处理: 对于不常见的真正异常情况");
        System.out.println("- 错误代码: 对于历史代码或与C风格API交互");
        System.out.println("- Optional: 对于可能存在也可能不存在的值");
        System.out.println("- 函数式方法: 对于数据处理管道和转换链");
    }
    
    public static void main(String[] args) {
        System.out.println("=== 比较不同的异常处理技术 ===");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            // 获取要处理的文件名
            System.out.print("输入要处理的文件名 (默认: " + SAMPLE_FILE + "): ");
            String filename = scanner.nextLine().trim();
            if (filename.isEmpty()) {
                filename = SAMPLE_FILE;
            }
            
            System.out.println("将使用以下文件: " + filename);
            
            // 演示四种不同的异常处理方法
            demoTraditionalExceptions(filename);
            demoErrorCodes(filename);
            demoOptional(filename);
            demoFunctional(filename);
            
            // 比较方法
            compareApproaches();
            
        } finally {
            scanner.close();
        }
    }
} 