package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题17：基于栈的迷宫求解器
 * 本程序使用栈实现迷宫寻路算法，找出从起点到终点的一条路径
 * 
 * 迷宫寻路是栈的经典应用之一，本程序展示了如何使用栈来实现深度优先搜索：
 * 1. 使用栈存储探索路径
 * 2. 每当遇到岔路口，选择一个方向并将当前位置压入栈中
 * 3. 如果进入了死胡同，通过弹栈回溯到上一个有未探索方向的位置
 * 4. 最终找到终点或确定没有可行路径
 */
public class exit6_17 {
    
    /**
     * 表示迷宫中的位置
     */
    public static class Position {
        private int row;
        private int col;
        
        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public int getRow() {
            return row;
        }
        
        public int getCol() {
            return col;
        }
        
        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return row == position.row && col == position.col;
        }
    }
    
    /**
     * 迷宫求解器类
     */
    public static class MazeSolver {
        private char[][] maze;        // 迷宫数组
        private boolean[][] visited;  // 记录已访问位置
        private Position start;       // 起点
        private Position end;         // 终点
        private int rows;             // 行数
        private int cols;             // 列数
        
        // 表示四个移动方向：上、右、下、左
        private static final int[][] DIRECTIONS = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
        };
        
        // 表示方向的名称
        private static final String[] DIRECTION_NAMES = {
            "上", "右", "下", "左"
        };
        
        // 迷宫中的字符表示
        private static final char WALL = '#';      // 墙
        private static final char PATH = ' ';      // 可行路径
        private static final char VISITED = '.';   // 已访问
        private static final char CURRENT = '@';   // 当前位置
        private static final char START = 'S';     // 起点
        private static final char END = 'E';       // 终点
        private static final char SOLUTION = '*';  // 最终路径
        
        /**
         * 构造函数
         * @param maze 迷宫字符数组
         * @param startRow 起点行
         * @param startCol 起点列
         * @param endRow 终点行
         * @param endCol 终点列
         */
        public MazeSolver(char[][] maze, int startRow, int startCol, int endRow, int endCol) {
            if (maze == null || maze.length == 0 || maze[0].length == 0) {
                throw new IllegalArgumentException("迷宫不能为空");
            }
            
            this.rows = maze.length;
            this.cols = maze[0].length;
            
            // 复制迷宫，避免修改原始数据
            this.maze = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    this.maze[i][j] = maze[i][j];
                }
            }
            
            // 初始化访问数组
            visited = new boolean[rows][cols];
            
            // 设置起点和终点
            validatePosition(startRow, startCol, "起点");
            validatePosition(endRow, endCol, "终点");
            
            this.start = new Position(startRow, startCol);
            this.end = new Position(endRow, endCol);
            
            // 设置起点和终点标记
            this.maze[startRow][startCol] = START;
            this.maze[endRow][endCol] = END;
        }
        
        /**
         * 验证位置是否有效
         */
        private void validatePosition(int row, int col, String posType) {
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw new IllegalArgumentException(posType + "位置超出迷宫范围");
            }
            if (maze[row][col] == WALL) {
                throw new IllegalArgumentException(posType + "位置不能是墙");
            }
        }
        
        /**
         * 使用基于栈的深度优先搜索解决迷宫
         * @param visualize 是否可视化解决过程
         * @return 如果找到路径返回true，否则返回false
         */
        public boolean solve(boolean visualize) {
            // 创建栈来存储路径
            ArrayListStack<Position> stack = new ArrayListStack<>();
            // 将起点加入栈
            stack.push(start);
            visited[start.getRow()][start.getCol()] = true;
            
            while (!stack.isEmpty()) {
                // 获取当前位置
                Position current = stack.peek();
                
                // 显示当前状态
                if (visualize) {
                    showCurrentState(current, stack);
                }
                
                // 检查是否到达终点
                if (current.equals(end)) {
                    // 将栈中的路径标记为解决路径
                    markSolution(stack);
                    return true;
                }
                
                // 尝试移动到下一个位置
                boolean foundNextPosition = false;
                
                for (int i = 0; i < DIRECTIONS.length; i++) {
                    int newRow = current.getRow() + DIRECTIONS[i][0];
                    int newCol = current.getCol() + DIRECTIONS[i][1];
                    
                    // 检查新位置是否有效且未访问
                    if (isValidMove(newRow, newCol)) {
                        // 标记为已访问并加入栈
                        visited[newRow][newCol] = true;
                        stack.push(new Position(newRow, newCol));
                        
                        if (visualize) {
                            System.out.println("移动方向: " + DIRECTION_NAMES[i]);
                        }
                        
                        foundNextPosition = true;
                        break;
                    }
                }
                
                // 如果没有找到下一个位置，弹出当前位置（回溯）
                if (!foundNextPosition) {
                    stack.pop();
                    if (visualize) {
                        System.out.println("无法继续前进，回溯...");
                    }
                }
            }
            
            // 栈为空，表示没有找到路径
            return false;
        }
        
        /**
         * 检查移动是否有效
         */
        private boolean isValidMove(int row, int col) {
            // 检查是否在迷宫范围内
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                return false;
            }
            
            // 检查是否是墙或已访问
            return (maze[row][col] != WALL && !visited[row][col]);
        }
        
        /**
         * 将解决路径标记在迷宫上
         */
        private void markSolution(ArrayListStack<Position> stack) {
            // 创建临时栈来反转顺序
            ArrayListStack<Position> tempStack = new ArrayListStack<>();
            while (!stack.isEmpty()) {
                tempStack.push(stack.pop());
            }
            
            // 将路径标记为解决路径
            while (!tempStack.isEmpty()) {
                Position p = tempStack.pop();
                int row = p.getRow();
                int col = p.getCol();
                
                // 保持起点和终点标记不变
                if ((row == start.getRow() && col == start.getCol()) ||
                    (row == end.getRow() && col == end.getCol())) {
                    continue;
                }
                
                maze[row][col] = SOLUTION;
                stack.push(p);  // 恢复原始栈
            }
        }
        
        /**
         * 在控制台显示迷宫的当前状态
         */
        private void showCurrentState(Position current, ArrayListStack<Position> stack) {
            // 创建当前状态的副本
            char[][] currentState = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (visited[i][j] && maze[i][j] != START && maze[i][j] != END) {
                        currentState[i][j] = VISITED;
                    } else {
                        currentState[i][j] = maze[i][j];
                    }
                }
            }
            
            // 标记当前位置
            if (!current.equals(start) && !current.equals(end)) {
                currentState[current.getRow()][current.getCol()] = CURRENT;
            }
            
            // 显示当前迷宫状态
            System.out.println("\n当前迷宫状态:");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.print(currentState[i][j] + " ");
                }
                System.out.println();
            }
            
            System.out.println("\n当前位置: " + current);
            System.out.println("已探索步数: " + (stack.size() - 1));
            System.out.println();
            
            // 增加延迟以便观察
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        /**
         * 获取解决后的迷宫表示
         */
        public String getMazeString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    sb.append(maze[i][j]).append(' ');
                }
                sb.append('\n');
            }
            return sb.toString();
        }
        
        /**
         * 获取图例说明
         */
        public static String getLegend() {
            return "图例: # = 墙, S = 起点, E = 终点, * = 路径, . = 已访问, @ = 当前位置";
        }
    }
    
    /**
     * 创建示例迷宫
     */
    private static char[][] createExampleMaze(int example) {
        switch (example) {
            case 1:
                // 简单迷宫
                return new char[][] {
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', '#', ' ', '#', '#', ' ', '#'},
                    {'#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', '#', '#', ' ', '#', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
                };
                
            case 2:
                // 复杂迷宫
                return new char[][] {
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#'},
                    {'#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
                };
                
            case 3:
                // 无解迷宫
                return new char[][] {
                    {'#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', '#', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', '#', '#', '#', '#'},
                    {'#', ' ', '#', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', '#', '#', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', '#', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#'}
                };
                
            default:
                // 默认迷宫
                return new char[][] {
                    {'#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', '#'},
                    {'#', ' ', '#', ' ', '#'},
                    {'#', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#'}
                };
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("基于栈的迷宫求解器");
        System.out.println("本程序使用深度优先搜索来解决迷宫问题");
        System.out.println("迷宫由字符表示: '#' 是墙, ' ' 是可通过的路径");
        System.out.println("'S' 是起点, 'E' 是终点, '*' 是解决路径\n");
        
        System.out.println("选择示例迷宫:");
        System.out.println("1. 简单迷宫");
        System.out.println("2. 复杂迷宫");
        System.out.println("3. 无解迷宫");
        System.out.print("请选择 (1-3): ");
        
        int choice = input.nextInt();
        input.nextLine();  // 消耗换行符
        
        char[][] mazeArray = createExampleMaze(choice);
        
        // 显示初始迷宫
        System.out.println("\n初始迷宫:");
        for (char[] row : mazeArray) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        
        // 获取起点和终点
        System.out.print("\n请输入起点行: ");
        int startRow = input.nextInt();
        System.out.print("请输入起点列: ");
        int startCol = input.nextInt();
        
        System.out.print("请输入终点行: ");
        int endRow = input.nextInt();
        System.out.print("请输入终点列: ");
        int endCol = input.nextInt();
        
        input.nextLine();  // 消耗换行符
        
        System.out.print("\n是否显示求解过程? (y/n): ");
        boolean visualize = input.nextLine().trim().toLowerCase().startsWith("y");
        
        try {
            // 创建迷宫求解器
            MazeSolver solver = new MazeSolver(mazeArray, startRow, startCol, endRow, endCol);
            
            System.out.println("\n开始求解迷宫...");
            System.out.println(MazeSolver.getLegend());
            
            // 求解迷宫
            boolean solutionFound = solver.solve(visualize);
            
            if (solutionFound) {
                System.out.println("\n找到路径! 最终迷宫:");
                System.out.println(solver.getMazeString());
            } else {
                System.out.println("\n没有找到路径!");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("错误: " + e.getMessage());
        }
        
        System.out.println("\n迷宫求解的实际应用:");
        System.out.println("1. 机器人导航和路径规划");
        System.out.println("2. 游戏中的寻路算法");
        System.out.println("3. 网络路由协议");
        System.out.println("4. 电路设计中的布线问题");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 