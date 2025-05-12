package com.qius.exit1;

/**
 * 问题20：编写一个程序，将给定n的一个帕斯卡三角形存储到ArrayList<ArrayList<Integer>>中。
 * 
 * 本程序展示了如何：
 * 1. 使用嵌套ArrayList实现帕斯卡三角形的存储结构
 * 2. 基于前一行计算当前行元素的递推关系实现
 * 3. 格式化打印帕斯卡三角形（居中对齐）
 * 4. 处理用户输入验证和异常情况
 */

import java.util.ArrayList;
import java.util.Scanner;

public class exit1_20 {
    public static void main(String[] args) {
        /**
         * 获取用户输入并验证
         * - 创建Scanner对象读取控制台输入
         * - 验证输入是否为合法整数
         * - 检查输入范围是否在有效区间
         */
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        // 循环，直到用户输入有效的n值
        int n;
        while (true) {
            System.out.print("\n请输入要生成的帕斯卡三角形的行数（1-30）：");
            
            // 检查输入是否为整数
            if (!scanner.hasNextInt()) {
                System.out.println("错误：请输入一个整数。");
                scanner.next(); // 清除无效输入
                continue;
            }
            
            n = scanner.nextInt();
            
            // 检查n是否在有效范围内
            if (n < 1) {
                System.out.println("错误：行数必须大于或等于1。");
            } else if (n > 30) {
                System.out.println("错误：为了避免数字过大，请输入不超过30的行数。");
            } else {
                break; // 输入有效，退出循环
            }
        }
        
        /**
         * 生成并显示帕斯卡三角形
         * - 调用生成方法创建三角形数据结构
         * - 使用格式化方法打印三角形
         * - 展示底层存储结构
         */
        // 生成帕斯卡三角形
        ArrayList<ArrayList<Integer>> pascalTriangle = generatePascalTriangle(n);
        
        // 打印帕斯卡三角形
        System.out.println("\n生成的" + n + "行帕斯卡三角形：");
        printPascalTriangle(pascalTriangle);
        
        // 打印存储结构
        System.out.println("\n帕斯卡三角形的存储结构（ArrayList<ArrayList<Integer>>）：");
        printStorageStructure(pascalTriangle);
        
        // 关闭Scanner
        scanner.close();
    }
    
    /**
     * 生成帕斯卡三角形并存储在嵌套的ArrayList中
     * - 创建外层ArrayList存储每一行
     * - 对每一行应用帕斯卡三角形的递推公式
     * - 处理每行的首尾元素（均为1）和中间元素
     * 
     * @param n 要生成的帕斯卡三角形的行数
     * @return 包含帕斯卡三角形的嵌套ArrayList
     */
    private static ArrayList<ArrayList<Integer>> generatePascalTriangle(int n) {
        // 创建一个外层ArrayList来存储每一行
        ArrayList<ArrayList<Integer>> triangle = new ArrayList<>(n);
        
        // 生成每一行
        for (int i = 0; i < n; i++) {
            // 创建一个内层ArrayList来存储当前行的数字
            ArrayList<Integer> row = new ArrayList<>(i + 1);
            
            // 第一个元素总是1
            row.add(1);
            
            // 计算中间元素（如果有的话）
            for (int j = 1; j < i; j++) {
                // 获取上一行的相邻元素
                ArrayList<Integer> prevRow = triangle.get(i - 1);
                // 当前元素 = 上一行中的左上方元素 + 右上方元素
                int newValue = prevRow.get(j - 1) + prevRow.get(j);
                row.add(newValue);
            }
            
            // 如果不是第一行，最后一个元素也是1
            if (i > 0) {
                row.add(1);
            }
            
            // 将当前行添加到三角形中
            triangle.add(row);
        }
        
        return triangle;
    }
    
    /**
     * 格式化打印帕斯卡三角形
     * - 计算最大数字宽度以保持对齐
     * - 为每行添加适当缩进使三角形居中
     * - 格式化输出确保视觉上的三角形形状
     * 
     * @param triangle 包含帕斯卡三角形的嵌套ArrayList
     */
    private static void printPascalTriangle(ArrayList<ArrayList<Integer>> triangle) {
        int n = triangle.size();
        
        // 计算最大数字的宽度，用于格式化输出
        int maxWidth = 1;
        if (n > 0) {
            // 最后一行的中间元素通常是最大的
            ArrayList<Integer> lastRow = triangle.get(n - 1);
            int middleIndex = lastRow.size() / 2;
            int maxNumber = lastRow.get(Math.min(middleIndex, lastRow.size() - 1));
            // 计算最大数字的位数
            maxWidth = String.valueOf(maxNumber).length();
        }
        
        // 为每行添加适当的缩进，使三角形居中
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = triangle.get(i);
            
            // 计算缩进空格数
            int padding = (n - i - 1) * (maxWidth + 1) / 2;
            for (int j = 0; j < padding; j++) {
                System.out.print(" ");
            }
            
            // 打印当前行的元素
            for (int num : row) {
                // 格式化输出，确保每个数字占用相同的宽度
                System.out.printf("%-" + (maxWidth + 1) + "d", num);
            }
            
            System.out.println();
        }
    }
    
    /**
     * 打印帕斯卡三角形的存储结构
     * - 以JSON类似格式显示嵌套ArrayList结构
     * - 清晰展示每行的元素
     * - 添加适当的分隔符使输出更易读
     * 
     * @param triangle 包含帕斯卡三角形的嵌套ArrayList
     */
    private static void printStorageStructure(ArrayList<ArrayList<Integer>> triangle) {
        System.out.println("triangle = [");
        for (int i = 0; i < triangle.size(); i++) {
            ArrayList<Integer> row = triangle.get(i);
            System.out.print("  [");
            for (int j = 0; j < row.size(); j++) {
                System.out.print(row.get(j));
                if (j < row.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
            if (i < triangle.size() - 1) {
                System.out.println(",");
            } else {
                System.out.println();
            }
        }
        System.out.println("]");
    }
} 