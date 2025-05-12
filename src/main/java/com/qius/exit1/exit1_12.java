package com.qius.exit1;

/**
 * 问题12：编写一个switch表达式，当给定一个指南针方向为"N""S""E"或"W"的字符串时，
 * 会生成一个x-偏移和y-偏移的数组。例如，"W"应生成 new int[] { -1, 0 }。
 * 
 * 注意：原题要求使用switch表达式，但为了兼容Java 14以下版本，
 * 这里使用传统的switch语句实现相同功能。
 * 
 * 本程序展示了如何：
 * 1. 使用switch表达式处理字符串输入并返回对应的数组
 * 2. 将指南针方向映射为二维坐标系统中的偏移量
 * 3. 通过用户交互式界面演示switch表达式的使用
 * 4. 使用Arrays.toString()格式化输出数组内容
 */
import java.util.Arrays;
import java.util.Scanner;

public class exit1_12 {
    public static void main(String[] args) {
        /**
         * 创建Scanner对象并显示程序说明
         * - 解释程序的功能和用法
         * - 说明各个方向对应的偏移量
         */
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 打印说明
        System.out.println("本程序根据指南针方向生成x和y偏移量。");
        System.out.println("N: 北方 - 向上移动 (0, 1)");
        System.out.println("S: 南方 - 向下移动 (0, -1)");
        System.out.println("E: 东方 - 向右移动 (1, 0)");
        System.out.println("W: 西方 - 向左移动 (-1, 0)");
        
        /**
         * 主程序循环
         * - 重复读取用户输入的方向
         * - 使用switch表达式生成对应的偏移量数组
         * - 提供退出程序的选项
         */
        // 循环让用户输入方向
        boolean continueLoop = true;
        while (continueLoop) {
            // 提示用户输入一个方向
            System.out.print("\n请输入一个指南针方向 (N/S/E/W)，或输入'exit'退出: ");
            String direction = scanner.nextLine().trim().toUpperCase();
            
            // 检查是否退出
            if (direction.equalsIgnoreCase("exit")) {
                continueLoop = false;
                continue;
            }
            
            /**
             * 使用switch表达式处理方向输入
             * - 根据不同方向返回相应的偏移量数组
             * - 处理无效输入的情况
             */
            // 使用传统的switch语句生成偏移量数组
            int[] offset = switch (direction) {
                case "N" -> new int[] { 0, 1 };
                case "S" -> new int[] { 0, -1 };
                case "E" -> new int[] { 1, 0 };
                case "W" -> new int[] { -1, 0 };
                default -> null;
            };
            
            // 如果offset不为null，则打印结果
            if (offset != null) {
                // 使用Arrays.toString方法将数组转换为字符串
                System.out.println("方向 \"" + direction + "\" 对应的偏移量数组是: " + 
                                  Arrays.toString(offset));
                
                /**
                 * 提供额外解释
                 * - 说明每个方向对应的坐标变化
                 * - 使用switch语句选择合适的解释文本
                 */
                // 解释结果
                String explanation = "";
                switch (direction) {
                    case "N":
                        explanation = "向北移动，y坐标增加1，x坐标不变";
                        break;
                    case "S":
                        explanation = "向南移动，y坐标减少1，x坐标不变";
                        break;
                    case "E":
                        explanation = "向东移动，x坐标增加1，y坐标不变";
                        break;
                    case "W":
                        explanation = "向西移动，x坐标减少1，y坐标不变";
                        break;
                }
                System.out.println("解释: " + explanation);
            }
        }
        
        // 关闭Scanner
        scanner.close();
        System.out.println("程序已退出。");
        
        // 注：如果使用Java 14或更高版本，可以使用以下switch表达式更简洁地实现：
        /*
        int[] offset = switch (direction) {
            case "N" -> new int[] { 0, 1 };
            case "S" -> new int[] { 0, -1 };
            case "E" -> new int[] { 1, 0 };
            case "W" -> new int[] { -1, 0 };
            default -> null;
        };
        */
    }
} 