package com.qius.exit1; /**
 * 问题13：编写一个switch语句，当给定一个指南针方向为"N""S""E"或"W"的字符串时，
 * 调整变量x和y。例如，"W"会让x减少1。
 */

import java.util.Scanner;

public class exit1_13 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 初始化坐标
        int x = 0;
        int y = 0;
        
        // 打印说明
        System.out.println("本程序使用switch语句根据指南针方向调整x和y坐标。");
        System.out.println("初始坐标: (" + x + ", " + y + ")");
        System.out.println("N: 北方 - 向上移动 (y+1)");
        System.out.println("S: 南方 - 向下移动 (y-1)");
        System.out.println("E: 东方 - 向右移动 (x+1)");
        System.out.println("W: 西方 - 向左移动 (x-1)");
        
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
            
            // 记录移动前的坐标
            int oldX = x;
            int oldY = y;
            
            // 使用switch语句调整x和y坐标
            switch (direction) {
                case "N": // 北方
                    y += 1; // y坐标增加1（向上移动）
                    break;
                case "S": // 南方
                    y -= 1; // y坐标减少1（向下移动）
                    break;
                case "E": // 东方
                    x += 1; // x坐标增加1（向右移动）
                    break;
                case "W": // 西方
                    x -= 1; // x坐标减少1（向左移动）
                    break;
                default: // 无效方向
                    System.out.println("无效的方向！请输入 N, S, E 或 W。");
                    break;
            }
            
            // 如果坐标发生了变化，则打印结果
            if (x != oldX || y != oldY) {
                // 打印移动结果
                System.out.println("移动方向: " + direction);
                System.out.println("从坐标 (" + oldX + ", " + oldY + ") 移动到 (" + x + ", " + y + ")");
                
                // 解释结果
                String explanation = "";
                switch (direction) {
                    case "N":
                        explanation = "向北移动了1个单位";
                        break;
                    case "S":
                        explanation = "向南移动了1个单位";
                        break;
                    case "E":
                        explanation = "向东移动了1个单位";
                        break;
                    case "W":
                        explanation = "向西移动了1个单位";
                        break;
                }
                System.out.println(explanation);
            }
        }
        
        // 打印最终坐标
        System.out.println("\n最终坐标: (" + x + ", " + y + ")");
        
        // 关闭Scanner
        scanner.close();
        System.out.println("程序已退出。");
        
        // 注：问题12和问题13的主要区别是：
        // 问题12: 使用switch表达式返回一个偏移量数组
        // 问题13: 使用switch语句直接修改x和y变量
    }
} 