package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题15：栈实现的图形填充算法
 * 本程序演示如何使用栈实现简单的图形填充算法（洪水填充/泛洪填充）
 * 
 * 泛洪填充算法是图形处理中的一个基础算法，用于填充连续的区域。
 * 例如在绘图软件中的"油漆桶"工具就是基于这个算法实现的。
 * 
 * 这个程序展示了栈在图形处理中的应用，通过模拟二维网格来展示填充过程：
 * 1. 使用字符数组表示二维网格
 * 2. 利用栈存储需要检查的位置
 * 3. 通过迭代而不是递归的方式实现填充
 */
public class exit6_15 {
    
    /**
     * 表示网格中的一个位置
     */
    public static class Position {
        int row;
        int col;
        
        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }
    
    /**
     * 泛洪填充算法，使用栈实现
     * @param grid 要填充的字符网格
     * @param startRow 起始行
     * @param startCol 起始列
     * @param targetChar 要替换的字符
     * @param replacementChar 替换用的字符
     */
    public static void floodFill(char[][] grid, int startRow, int startCol, char targetChar, char replacementChar) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 检查起始位置是否有效
        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols) {
            System.out.println("起始位置超出网格范围");
            return;
        }
        
        // 如果起始位置不是目标字符或者目标字符与替换字符相同，无需填充
        if (grid[startRow][startCol] != targetChar || targetChar == replacementChar) {
            return;
        }
        
        // 创建存储待处理位置的栈
        ArrayListStack<Position> stack = new ArrayListStack<>();
        stack.push(new Position(startRow, startCol));
        
        // 定义四个方向：上、右、下、左
        int[][] directions = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
        };
        
        // 填充过程
        while (!stack.isEmpty()) {
            // 从栈中取出一个位置
            Position current = stack.pop();
            int row = current.row;
            int col = current.col;
            
            // 如果该位置已被填充或者不是目标字符，跳过
            if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] != targetChar) {
                continue;
            }
            
            // 填充当前位置
            grid[row][col] = replacementChar;
            
            // 打印填充过程
            System.out.println("填充位置: (" + row + ", " + col + ")");
            printGrid(grid);
            
            // 将相邻的四个位置加入栈中
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // 只将有效的位置加入栈中
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == targetChar) {
                    stack.push(new Position(newRow, newCol));
                }
            }
        }
    }
    
    /**
     * 边界填充算法，只填充边界
     * @param grid 要填充的字符网格
     * @param targetChar 要替换的字符
     * @param replacementChar 替换用的字符
     */
    public static void boundaryFill(char[][] grid, char targetChar, char replacementChar) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 创建存储待处理位置的栈
        ArrayListStack<Position> stack = new ArrayListStack<>();
        
        // 将边界上的所有目标字符加入栈
        // 上边界和下边界
        for (int col = 0; col < cols; col++) {
            if (grid[0][col] == targetChar) {
                stack.push(new Position(0, col));
            }
            if (grid[rows - 1][col] == targetChar) {
                stack.push(new Position(rows - 1, col));
            }
        }
        
        // 左边界和右边界
        for (int row = 1; row < rows - 1; row++) {
            if (grid[row][0] == targetChar) {
                stack.push(new Position(row, 0));
            }
            if (grid[row][cols - 1] == targetChar) {
                stack.push(new Position(row, cols - 1));
            }
        }
        
        // 定义四个方向：上、右、下、左
        int[][] directions = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
        };
        
        // 填充过程
        while (!stack.isEmpty()) {
            // 从栈中取出一个位置
            Position current = stack.pop();
            int row = current.row;
            int col = current.col;
            
            // 如果该位置已被填充或者不是目标字符，跳过
            if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] != targetChar) {
                continue;
            }
            
            // 填充当前位置
            grid[row][col] = replacementChar;
            
            // 打印填充过程
            System.out.println("填充边界位置: (" + row + ", " + col + ")");
            printGrid(grid);
            
            // 将相邻的四个位置加入栈中
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // 只将有效的位置加入栈中
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == targetChar) {
                    stack.push(new Position(newRow, newCol));
                }
            }
        }
    }
    
    /**
     * 使用栈实现图案中空心区域的填充
     * @param grid 要填充的字符网格
     * @param targetChar 要替换的字符（空白区域）
     * @param boundaryChar 边界字符
     * @param replacementChar 替换用的字符
     */
    public static void fillHoles(char[][] grid, char targetChar, char boundaryChar, char replacementChar) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 创建辅助网格，用于标记已访问过的位置
        boolean[][] visited = new boolean[rows][cols];
        
        // 从四条边界开始搜索，标记所有能从边界到达的targetChar
        // 这些位置不是"洞"，是与外界相连的空白
        markReachableFromBoundary(grid, visited, targetChar);
        
        // 现在，任何未被访问过且为targetChar的位置就是"洞"
        System.out.println("填充内部空洞:");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == targetChar && !visited[row][col]) {
                    // 这是一个洞，填充它
                    floodFill(grid, row, col, targetChar, replacementChar);
                }
            }
        }
    }
    
    /**
     * 标记所有从边界可以到达的目标字符位置
     */
    private static void markReachableFromBoundary(char[][] grid, boolean[][] visited, char targetChar) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // 创建存储待处理位置的栈
        ArrayListStack<Position> stack = new ArrayListStack<>();
        
        // 将边界上的所有目标字符加入栈
        // 上边界和下边界
        for (int col = 0; col < cols; col++) {
            if (grid[0][col] == targetChar) {
                stack.push(new Position(0, col));
                visited[0][col] = true;
            }
            if (grid[rows - 1][col] == targetChar) {
                stack.push(new Position(rows - 1, col));
                visited[rows - 1][col] = true;
            }
        }
        
        // 左边界和右边界
        for (int row = 1; row < rows - 1; row++) {
            if (grid[row][0] == targetChar) {
                stack.push(new Position(row, 0));
                visited[row][0] = true;
            }
            if (grid[row][cols - 1] == targetChar) {
                stack.push(new Position(row, cols - 1));
                visited[row][cols - 1] = true;
            }
        }
        
        // 定义四个方向：上、右、下、左
        int[][] directions = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
        };
        
        // 标记过程
        while (!stack.isEmpty()) {
            // 从栈中取出一个位置
            Position current = stack.pop();
            int row = current.row;
            int col = current.col;
            
            // 将相邻的四个位置加入栈中
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // 只将有效的、未访问过的目标字符位置加入栈中
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols 
                    && grid[newRow][newCol] == targetChar && !visited[newRow][newCol]) {
                    stack.push(new Position(newRow, newCol));
                    visited[newRow][newCol] = true;
                }
            }
        }
    }
    
    /**
     * 打印字符网格
     * @param grid 要打印的网格
     */
    public static void printGrid(char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * 创建示例网格
     * @param example 示例编号
     * @return 创建的字符网格
     */
    public static char[][] createExampleGrid(int example) {
        switch (example) {
            case 1:
                // 简单的填充示例
                return new char[][] {
                    {'#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#'}
                };
                
            case 2:
                // 图形中有"空洞"的示例
                return new char[][] {
                    {'#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', '#', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', '#', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#'}
                };
                
            case 3:
                // 复杂图形的示例
                return new char[][] {
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', ' ', ' ', '#', '#', ' ', '#'},
                    {'#', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', '#', ' ', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', ' ', '#', ' ', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
                };
                
            default:
                return new char[][] {
                    {'#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#'}
                };
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("栈实现的图形填充算法 - 演示");
        System.out.println("1. 泛洪填充 - 从一个起点开始填充所有相连的区域");
        System.out.println("2. 边界填充 - 只填充图形的边界");
        System.out.println("3. 空洞填充 - 填充图形中的空洞");
        System.out.print("请选择填充算法(1-3): ");
        
        int algorithmChoice = input.nextInt();
        
        System.out.println("\n示例网格:");
        System.out.println("1. 简单的方形网格");
        System.out.println("2. 带有空洞的网格");
        System.out.println("3. 复杂图形的网格");
        System.out.print("请选择示例(1-3): ");
        
        int exampleChoice = input.nextInt();
        char[][] grid = createExampleGrid(exampleChoice);
        
        System.out.println("\n原始网格:");
        printGrid(grid);
        
        switch (algorithmChoice) {
            case 1:
                System.out.print("请输入起始行: ");
                int startRow = input.nextInt();
                System.out.print("请输入起始列: ");
                int startCol = input.nextInt();
                System.out.print("请输入填充字符: ");
                input.nextLine(); // 消耗换行符
                char fillChar = input.nextLine().charAt(0);
                
                System.out.println("\n开始泛洪填充...");
                floodFill(grid, startRow, startCol, ' ', fillChar);
                break;
                
            case 2:
                System.out.print("请输入填充字符: ");
                input.nextLine(); // 消耗换行符
                char boundaryChar = input.nextLine().charAt(0);
                
                System.out.println("\n开始边界填充...");
                boundaryFill(grid, ' ', boundaryChar);
                break;
                
            case 3:
                System.out.print("请输入填充字符: ");
                input.nextLine(); // 消耗换行符
                char holeChar = input.nextLine().charAt(0);
                
                System.out.println("\n开始空洞填充...");
                fillHoles(grid, ' ', '#', holeChar);
                break;
                
            default:
                System.out.println("无效的选择");
        }
        
        System.out.println("\n最终网格:");
        printGrid(grid);
        
        System.out.println("\n图形填充算法的应用:");
        System.out.println("1. 绘图软件中的\"油漆桶\"工具");
        System.out.println("2. 图像处理中的区域分割和识别");
        System.out.println("3. 游戏中的地图生成和探索");
        System.out.println("4. 计算机视觉中的对象识别和轮廓提取");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 