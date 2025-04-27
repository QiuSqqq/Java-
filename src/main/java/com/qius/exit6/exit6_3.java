package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题3：迷宫求解器
 * 使用栈实现深度优先搜索算法解决迷宫问题
 */
public class exit6_3 {
    // 定义方向：上、右、下、左
    private static final int[][] DIRECTIONS = {
        {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };
    
    // 迷宫表示：0表示通路，1表示墙壁
    private static int[][] maze;
    
    // 迷宫的尺寸
    private static int rows;
    private static int cols;
    
    /**
     * 表示迷宫中的一个位置
     */
    private static class Position {
        int row;
        int col;
        int dir; // 下一个尝试的方向索引
        
        Position(int row, int col) {
            this.row = row;
            this.col = col;
            this.dir = 0; // 初始方向为0（上）
        }
        
        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }
    
    /**
     * 使用深度优先搜索解决迷宫问题
     * @param startRow 起点行
     * @param startCol 起点列
     * @param endRow 终点行
     * @param endCol 终点列
     * @return 如果找到路径则返回true，否则返回false
     */
    public static boolean solveMaze(int startRow, int startCol, int endRow, int endCol) {
        // 创建一个栈来存储路径
        ArrayListStack<Position> stack = new ArrayListStack<>();
        
        // 标记已访问的位置，避免重复访问
        boolean[][] visited = new boolean[rows][cols];
        
        // 将起点加入栈中并标记为已访问
        Position start = new Position(startRow, startCol);
        stack.push(start);
        visited[startRow][startCol] = true;
        
        while (!stack.isEmpty()) {
            // 获取当前位置（不弹出）
            Position current = stack.peek();
            
            // 如果到达终点，返回成功
            if (current.row == endRow && current.col == endCol) {
                return true;
            }
            
            // 尝试所有可能的方向
            boolean found = false;
            
            while (current.dir < DIRECTIONS.length && !found) {
                int newRow = current.row + DIRECTIONS[current.dir][0];
                int newCol = current.col + DIRECTIONS[current.dir][1];
                current.dir++; // 将方向索引加1，下次尝试下一个方向
                
                // 检查新位置是否有效
                if (isValidPosition(newRow, newCol) && maze[newRow][newCol] == 0 && !visited[newRow][newCol]) {
                    // 将新位置加入栈中
                    Position nextPos = new Position(newRow, newCol);
                    stack.push(nextPos);
                    visited[newRow][newCol] = true;
                    found = true;
                }
            }
            
            // 如果没有找到下一个可行的位置，回溯
            if (!found) {
                stack.pop();
            }
        }
        
        // 如果栈为空，说明没有找到路径
        return false;
    }
    
    /**
     * 检查位置是否在迷宫范围内
     */
    private static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    /**
     * 打印迷宫
     */
    private static void printMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 0) {
                    System.out.print("  "); // 通路
                } else {
                    System.out.print("██"); // 墙壁
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        // 创建一个示例迷宫
        maze = new int[][] {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };
        
        rows = maze.length;
        cols = maze[0].length;
        
        System.out.println("迷宫求解器 - 使用栈实现深度优先搜索");
        System.out.println("迷宫地图：");
        printMaze();
        
        int startRow = 1;
        int startCol = 1;
        int endRow = 5;
        int endCol = 5;
        
        System.out.println("\n起点: (" + startRow + ", " + startCol + ")");
        System.out.println("终点: (" + endRow + ", " + endCol + ")");
        
        boolean pathFound = solveMaze(startRow, startCol, endRow, endCol);
        
        if (pathFound) {
            System.out.println("\n已找到路径！");
        } else {
            System.out.println("\n未找到路径！");
        }
    }
} 