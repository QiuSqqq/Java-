package com.qius.exit1; /**
 * 问题19：编写一个程序，读取二维整数数组，并确定它是否为幻方（即所有行、所有列和对角线的
 * 总和是否相等，相等的即为幻方）。二维数组按照行输入，并在用户输入空行时停止。
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class exit1_19 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 欢迎信息和说明
        System.out.println("幻方检测程序");
        System.out.println("幻方是一个n×n的矩阵，其中每行、每列以及两条对角线上的数字之和都相等。");
        System.out.println("请输入一个二维整数数组（按行输入，每行内的数字用空格分隔）。");
        System.out.println("输入空行表示输入结束。");
        System.out.println();
        
        // 读取二维数组
        int[][] matrix = readMatrix(scanner);
        
        // 如果矩阵为空或不是方阵，则不可能是幻方
        if (matrix == null || matrix.length == 0 || matrix.length != matrix[0].length) {
            System.out.println("输入错误：不是有效的方阵。");
            scanner.close();
            return;
        }
        
        // 打印输入的矩阵
        System.out.println("\n您输入的矩阵是：");
        printMatrix(matrix);
        
        // 检查是否为幻方
        boolean isMagicSquare = checkMagicSquare(matrix);
        
        // 打印结果
        if (isMagicSquare) {
            System.out.println("\n恭喜！这是一个幻方。");
        } else {
            System.out.println("\n这不是一个幻方。");
        }
        
        // 打印幻方的魔术和（如果是幻方的话）
        if (isMagicSquare) {
            int n = matrix.length;
            int magicSum = n * (n * n + 1) / 2; // 幻方的理论和公式
            System.out.println("这个" + n + "×" + n + "幻方的魔术和是：" + calculateRowSum(matrix, 0));
            System.out.println("理论上，" + n + "×" + n + "幻方的魔术和应该是：" + magicSum);
        }
        
        // 关闭Scanner
        scanner.close();
    }
    
    /**
     * 从用户输入读取二维整数数组
     * 
     * @param scanner Scanner对象，用于读取输入
     * @return 二维整数数组
     */
    private static int[][] readMatrix(Scanner scanner) {
        List<int[]> rows = new ArrayList<>();
        int rowLength = -1;
        
        System.out.println("请按行输入矩阵（输入空行结束）：");
        while (true) {
            String line = scanner.nextLine().trim();
            
            // 如果是空行，则结束输入
            if (line.isEmpty()) {
                break;
            }
            
            // 将行拆分为数字
            String[] elements = line.split("\\s+");
            int[] row = new int[elements.length];
            
            // 转换为整数
            for (int i = 0; i < elements.length; i++) {
                try {
                    row[i] = Integer.parseInt(elements[i]);
                } catch (NumberFormatException e) {
                    System.out.println("错误：'" + elements[i] + "'不是有效的整数。请重新输入该行。");
                    continue;
                }
            }
            
            // 检查所有行的长度是否相同
            if (rowLength == -1) {
                rowLength = row.length;
            } else if (row.length != rowLength) {
                System.out.println("错误：矩阵的所有行必须具有相同的长度。请重新输入该行。");
                continue;
            }
            
            // 添加到行列表
            rows.add(row);
        }
        
        // 如果没有输入任何行，则返回null
        if (rows.isEmpty()) {
            return null;
        }
        
        // 将List<int[]>转换为int[][]
        int[][] matrix = new int[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            matrix[i] = rows.get(i);
        }
        
        return matrix;
    }
    
    /**
     * 打印二维整数数组
     * 
     * @param matrix 要打印的二维数组
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                // 格式化输出，保持对齐
                System.out.printf("%4d", element);
            }
            System.out.println();
        }
    }
    
    /**
     * 计算指定行的和
     * 
     * @param matrix 二维数组
     * @param rowIndex 行索引
     * @return 该行所有元素的和
     */
    private static int calculateRowSum(int[][] matrix, int rowIndex) {
        int sum = 0;
        for (int j = 0; j < matrix[rowIndex].length; j++) {
            sum += matrix[rowIndex][j];
        }
        return sum;
    }
    
    /**
     * 计算指定列的和
     * 
     * @param matrix 二维数组
     * @param colIndex 列索引
     * @return 该列所有元素的和
     */
    private static int calculateColumnSum(int[][] matrix, int colIndex) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][colIndex];
        }
        return sum;
    }
    
    /**
     * 计算主对角线的和（从左上到右下）
     * 
     * @param matrix 二维数组
     * @return 主对角线所有元素的和
     */
    private static int calculateMainDiagonalSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][i];
        }
        return sum;
    }
    
    /**
     * 计算次对角线的和（从右上到左下）
     * 
     * @param matrix 二维数组
     * @return 次对角线所有元素的和
     */
    private static int calculateAntiDiagonalSum(int[][] matrix) {
        int sum = 0;
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            sum += matrix[i][n - 1 - i];
        }
        return sum;
    }
    
    /**
     * 检查矩阵是否为幻方
     * 
     * @param matrix 要检查的二维数组
     * @return 如果是幻方则返回true，否则返回false
     */
    private static boolean checkMagicSquare(int[][] matrix) {
        int n = matrix.length;
        
        // 计算第一行的和作为参考值
        int referenceSum = calculateRowSum(matrix, 0);
        
        // 检查每一行的和
        for (int i = 1; i < n; i++) {
            if (calculateRowSum(matrix, i) != referenceSum) {
                System.out.println("行 " + i + " 的和与第一行不相等。");
                return false;
            }
        }
        
        // 检查每一列的和
        for (int j = 0; j < n; j++) {
            if (calculateColumnSum(matrix, j) != referenceSum) {
                System.out.println("列 " + j + " 的和与参考值不相等。");
                return false;
            }
        }
        
        // 检查主对角线的和
        if (calculateMainDiagonalSum(matrix) != referenceSum) {
            System.out.println("主对角线的和与参考值不相等。");
            return false;
        }
        
        // 检查次对角线的和
        if (calculateAntiDiagonalSum(matrix) != referenceSum) {
            System.out.println("次对角线的和与参考值不相等。");
            return false;
        }
        
        // 如果所有检查都通过，则是幻方
        return true;
    }
} 