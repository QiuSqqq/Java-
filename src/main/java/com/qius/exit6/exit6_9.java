package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题9：使用栈消除递归
 * 演示如何将递归算法转换为非递归算法
 */
public class exit6_9 {
    
    /**
     * 递归计算阶乘
     * @param n 要计算阶乘的数
     * @return n的阶乘
     */
    public static long factorialRecursive(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorialRecursive(n - 1);
    }
    
    /**
     * 使用栈消除递归计算阶乘
     * @param n 要计算阶乘的数
     * @return n的阶乘
     */
    public static long factorialWithStack(int n) {
        if (n <= 1) {
            return 1;
        }
        
        // 用于存储中间计算值的栈
        ArrayListStack<Integer> stack = new ArrayListStack<>();
        
        // 将所有值入栈
        for (int i = n; i >= 1; i--) {
            stack.push(i);
        }
        
        // 计算阶乘
        long result = 1;
        while (!stack.isEmpty()) {
            result *= stack.pop();
        }
        
        return result;
    }
    
    /**
     * 递归计算斐波那契数列
     * @param n 第n个斐波那契数
     * @return 斐波那契数列的第n个数
     */
    public static long fibonacciRecursive(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
    
    /**
     * 使用栈消除递归计算斐波那契数列
     * @param n 第n个斐波那契数
     * @return 斐波那契数列的第n个数
     */
    public static long fibonacciWithStack(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        
        // 定义元组类保存状态信息
        class FibState {
            int n;         // 当前计算的斐波那契数的位置
            int status;    // 状态：0表示初始状态，1表示已计算左子树，2表示已完成
            long result;   // 计算结果
            long leftResult; // 左子树结果
            
            FibState(int n) {
                this.n = n;
                this.status = 0;
                this.result = 0;
                this.leftResult = 0;
            }
        }
        
        ArrayListStack<FibState> stack = new ArrayListStack<>();
        stack.push(new FibState(n));
        
        long finalResult = 0;
        
        while (!stack.isEmpty()) {
            FibState current = stack.peek();
            
            if (current.n <= 0) {
                current.result = 0;
                stack.pop();
            } else if (current.n == 1) {
                current.result = 1;
                stack.pop();
            } else if (current.status == 0) {
                // 计算左子树 F(n-1)
                current.status = 1;
                stack.push(new FibState(current.n - 1));
            } else if (current.status == 1) {
                // 保存左子树结果，开始计算右子树 F(n-2)
                current.leftResult = finalResult;
                current.status = 2;
                stack.push(new FibState(current.n - 2));
            } else {
                // 合并左右子树结果
                current.result = current.leftResult + finalResult;
                finalResult = current.result;
                stack.pop();
            }
            
            if (stack.isEmpty() && current.status == 2) {
                finalResult = current.result;
            }
        }
        
        return finalResult;
    }
    
    /**
     * 递归计算汉诺塔问题的移动步骤
     * @param n 圆盘数量
     * @param source 源柱子
     * @param auxiliary 辅助柱子
     * @param target 目标柱子
     */
    public static void hanoiRecursive(int n, char source, char auxiliary, char target) {
        if (n == 1) {
            System.out.println("将圆盘 1 从 " + source + " 移动到 " + target);
            return;
        }
        
        // 将n-1个圆盘从source移动到auxiliary
        hanoiRecursive(n - 1, source, target, auxiliary);
        
        // 将第n个圆盘从source移动到target
        System.out.println("将圆盘 " + n + " 从 " + source + " 移动到 " + target);
        
        // 将n-1个圆盘从auxiliary移动到target
        hanoiRecursive(n - 1, auxiliary, source, target);
    }
    
    /**
     * 使用栈消除递归解决汉诺塔问题
     * @param n 圆盘数量
     * @param source 源柱子
     * @param auxiliary 辅助柱子
     * @param target 目标柱子
     */
    public static void hanoiWithStack(int n, char source, char auxiliary, char target) {
        if (n <= 0) {
            return;
        }
        
        // 定义汉诺塔问题的状态
        class HanoiState {
            int n;         // 当前处理的圆盘数
            char source;   // 源柱子
            char auxiliary; // 辅助柱子
            char target;   // 目标柱子
            int status;    // 状态：0表示初始状态，1表示移动了n-1个盘子到辅助柱，2表示已完成
            
            HanoiState(int n, char source, char auxiliary, char target) {
                this.n = n;
                this.source = source;
                this.auxiliary = auxiliary;
                this.target = target;
                this.status = 0;
            }
        }
        
        ArrayListStack<HanoiState> stack = new ArrayListStack<>();
        stack.push(new HanoiState(n, source, auxiliary, target));
        
        while (!stack.isEmpty()) {
            HanoiState current = stack.peek();
            
            if (current.n == 1) {
                // 基本情况：只有一个圆盘
                System.out.println("将圆盘 1 从 " + current.source + " 移动到 " + current.target);
                stack.pop();
            } else if (current.status == 0) {
                // 将n-1个圆盘从source移动到auxiliary
                current.status = 1;
                stack.push(new HanoiState(current.n - 1, current.source, current.target, current.auxiliary));
            } else if (current.status == 1) {
                // 将第n个圆盘从source移动到target
                System.out.println("将圆盘 " + current.n + " 从 " + current.source + " 移动到 " + current.target);
                current.status = 2;
                stack.push(new HanoiState(current.n - 1, current.auxiliary, current.source, current.target));
            } else {
                // 已完成
                stack.pop();
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("递归算法转非递归算法 - 使用栈消除递归");
        System.out.println("1. 阶乘计算");
        System.out.println("2. 斐波那契数列");
        System.out.println("3. 汉诺塔问题");
        System.out.print("请选择演示的算法(1-3): ");
        
        int choice = input.nextInt();
        
        switch (choice) {
            case 1:
                System.out.print("请输入要计算阶乘的数: ");
                int factN = input.nextInt();
                
                System.out.println("\n使用递归计算 " + factN + "! = " + factorialRecursive(factN));
                System.out.println("使用栈消除递归计算 " + factN + "! = " + factorialWithStack(factN));
                break;
                
            case 2:
                System.out.print("请输入要计算的斐波那契数的位置: ");
                int fibN = input.nextInt();
                
                System.out.println("\n使用递归计算 F(" + fibN + ") = " + fibonacciRecursive(fibN));
                System.out.println("使用栈消除递归计算 F(" + fibN + ") = " + fibonacciWithStack(fibN));
                break;
                
            case 3:
                System.out.print("请输入汉诺塔的圆盘数量: ");
                int hanoiN = input.nextInt();
                
                System.out.println("\n使用递归解决汉诺塔问题:");
                hanoiRecursive(hanoiN, 'A', 'B', 'C');
                
                System.out.println("\n使用栈消除递归解决汉诺塔问题:");
                hanoiWithStack(hanoiN, 'A', 'B', 'C');
                break;
                
            default:
                System.out.println("无效的选择");
        }
        
        System.out.println("\n递归消除的优势:");
        System.out.println("1. 避免大量递归调用导致的栈溢出");
        System.out.println("2. 在某些情况下可以提高性能");
        System.out.println("3. 将递归算法转换为迭代算法，便于理解和优化");
        System.out.println("4. 减少函数调用的开销");
        
        input.close();
    }
} 