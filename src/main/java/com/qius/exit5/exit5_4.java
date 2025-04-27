package com.qius.exit5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 问题4：重复前面的练习，但不使用异常。
 * 让readValues和sumOfValues返回错误代码。
 */
public class exit5_4 {
    
    // 定义错误代码常量
    public static final int SUCCESS = 0;            // 操作成功
    public static final int FILE_NOT_FOUND = 1;     // 文件未找到
    public static final int IO_ERROR = 2;           // 输入/输出错误
    public static final int PARSE_ERROR = 3;        // 解析错误
    
    // 用于存储错误信息的字段
    private static String errorMessage = "";
    
    /**
     * 读取包含浮点数的文件并返回所有数值的列表
     * @param filename 要读取的文件名
     * @param values 用于存储读取值的列表（将由方法填充）
     * @return 错误代码，0表示成功
     */
    public static int readValues(String filename, ArrayList<Double> values) {
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(filename));
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
                    errorMessage = "第 " + lineNumber + " 行无法解析为浮点数: '" + line + "'";
                    return PARSE_ERROR;
                }
            }
            
            return SUCCESS;
            
        } catch (java.io.FileNotFoundException e) {
            errorMessage = "找不到文件: " + filename;
            return FILE_NOT_FOUND;
        } catch (IOException e) {
            errorMessage = "读取文件时发生I/O错误: " + e.getMessage();
            return IO_ERROR;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 如果已经有错误，保留原始错误信息
                    if (errorMessage.isEmpty()) {
                        errorMessage = "关闭文件时发生错误: " + e.getMessage();
                        return IO_ERROR;
                    }
                }
            }
        }
    }
    
    /**
     * 计算文件中所有浮点数的总和
     * @param filename 要读取的文件名
     * @param sum 用于存储总和的引用（将由方法填充）
     * @return 错误代码，0表示成功
     */
    public static int sumOfValues(String filename, double[] sum) {
        ArrayList<Double> values = new ArrayList<>();
        
        // 调用readValues方法获取值列表
        int result = readValues(filename, values);
        if (result != SUCCESS) {
            return result; // 传播错误代码
        }
        
        // 计算总和
        double total = 0.0;
        for (Double value : values) {
            total += value;
        }
        
        // 存储结果
        sum[0] = total;
        
        return SUCCESS;
    }
    
    /**
     * 获取最后一个错误的详细信息
     * @return 错误消息
     */
    public static String getErrorMessage() {
        return errorMessage;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== 文件浮点数求和程序（使用错误代码）===");
        System.out.print("请输入要读取的文件名: ");
        String filename = scanner.nextLine();
        
        // 使用数组作为输出参数（因为Java中基本类型是值传递）
        double[] sum = new double[1];
        
        // 调用sumOfValues方法计算总和
        int result = sumOfValues(filename, sum);
        
        // 根据结果代码处理
        switch (result) {
            case SUCCESS:
                System.out.println("文件中浮点数的总和为: " + sum[0]);
                
                // 为了更详细的输出，也可以打印所有读取的值
                ArrayList<Double> values = new ArrayList<>();
                readValues(filename, values); // 已知成功，所以不检查返回值
                System.out.println("文件中包含的浮点数: " + values);
                System.out.println("共读取了 " + values.size() + " 个浮点数");
                break;
                
            case FILE_NOT_FOUND:
                System.err.println("错误: 找不到文件");
                System.err.println("详细信息: " + getErrorMessage());
                System.err.println("请确认文件路径是否正确，以及是否有读取权限。");
                break;
                
            case IO_ERROR:
                System.err.println("错误: 读取文件时发生I/O错误");
                System.err.println("详细信息: " + getErrorMessage());
                break;
                
            case PARSE_ERROR:
                System.err.println("错误: 文件格式不正确");
                System.err.println("详细信息: " + getErrorMessage());
                break;
                
            default:
                System.err.println("发生未知错误，代码: " + result);
                break;
        }
        
        scanner.close();
    }
} 