package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题8：历史记录管理器
 * 使用栈实现浏览器历史记录的前进和后退功能
 */
public class exit6_8 {
    /**
     * 历史记录管理器类
     * 使用两个栈分别存储"后退栈"和"前进栈"
     */
    public static class HistoryManager {
        private ArrayListStack<String> backStack;    // 存储可以后退的网页
        private ArrayListStack<String> forwardStack; // 存储可以前进的网页
        private String currentPage;                 // 当前网页
        
        /**
         * 构造函数，初始化历史记录管理器
         * @param homePage 主页地址
         */
        public HistoryManager(String homePage) {
            backStack = new ArrayListStack<>();
            forwardStack = new ArrayListStack<>();
            currentPage = homePage;
        }
        
        /**
         * 访问新页面
         * 当访问新页面时，当前页面入后退栈，前进栈清空
         * @param url 新页面的URL
         */
        public void visit(String url) {
            if (currentPage != null) {
                backStack.push(currentPage);
            }
            currentPage = url;
            // 清空前进栈，因为访问新页面后不能再前进
            while (!forwardStack.isEmpty()) {
                forwardStack.pop();
            }
            System.out.println("访问页面: " + url);
        }
        
        /**
         * 后退到上一页
         * @return 如果能后退返回true，否则返回false
         */
        public boolean back() {
            if (backStack.isEmpty()) {
                System.out.println("已经是第一个页面，无法后退");
                return false;
            }
            
            // 当前页面入前进栈
            forwardStack.push(currentPage);
            // 从后退栈取出上一个页面
            currentPage = backStack.pop();
            System.out.println("后退到页面: " + currentPage);
            return true;
        }
        
        /**
         * 前进到下一页
         * @return 如果能前进返回true，否则返回false
         */
        public boolean forward() {
            if (forwardStack.isEmpty()) {
                System.out.println("已经是最后一个页面，无法前进");
                return false;
            }
            
            // 当前页面入后退栈
            backStack.push(currentPage);
            // 从前进栈取出下一个页面
            currentPage = forwardStack.pop();
            System.out.println("前进到页面: " + currentPage);
            return true;
        }
        
        /**
         * 获取当前页面
         * @return 当前页面的URL
         */
        public String getCurrentPage() {
            return currentPage;
        }
        
        /**
         * 显示历史记录状态
         */
        public void displayHistory() {
            System.out.println("\n=== 历史记录状态 ===");
            System.out.println("当前页面: " + currentPage);
            
            System.out.print("可后退页面: ");
            if (backStack.isEmpty()) {
                System.out.println("无");
            } else {
                displayStack(backStack);
            }
            
            System.out.print("可前进页面: ");
            if (forwardStack.isEmpty()) {
                System.out.println("无");
            } else {
                displayStack(forwardStack);
            }
            System.out.println("==================\n");
        }
        
        /**
         * 打印栈内容，不改变栈
         * @param stack 要打印的栈
         */
        private void displayStack(ArrayListStack<String> stack) {
            ArrayListStack<String> tempStack = new ArrayListStack<>();
            StringBuilder sb = new StringBuilder();
            
            // 将元素从原栈移到临时栈
            while (!stack.isEmpty()) {
                tempStack.push(stack.pop());
            }
            
            // 将元素从临时栈移回原栈，同时打印
            while (!tempStack.isEmpty()) {
                String url = tempStack.pop();
                sb.append(url);
                if (!tempStack.isEmpty()) {
                    sb.append(" <- "); // 后退栈是先进后出，所以用左箭头
                }
                stack.push(url);
            }
            
            System.out.println(sb.toString());
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("浏览器历史记录管理器 - 使用栈实现");
        System.out.println("可以模拟浏览器的前进和后退功能");
        
        System.out.print("请输入主页地址: ");
        String homePage = input.nextLine().trim();
        
        // 创建历史记录管理器
        HistoryManager historyManager = new HistoryManager(homePage);
        historyManager.displayHistory();
        
        // 命令菜单
        boolean running = true;
        while (running) {
            System.out.println("请选择操作:");
            System.out.println("1. 访问新页面");
            System.out.println("2. 后退");
            System.out.println("3. 前进");
            System.out.println("4. 显示历史记录");
            System.out.println("0. 退出程序");
            
            System.out.print("请输入选项(0-4): ");
            int choice = input.nextInt();
            input.nextLine(); // 消耗换行符
            
            switch (choice) {
                case 1:
                    System.out.print("请输入要访问的页面URL: ");
                    String url = input.nextLine().trim();
                    historyManager.visit(url);
                    break;
                case 2:
                    historyManager.back();
                    break;
                case 3:
                    historyManager.forward();
                    break;
                case 4:
                    historyManager.displayHistory();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("无效的选项，请重新输入");
            }
        }
        
        System.out.println("\n历史记录管理的应用场景:");
        System.out.println("1. 浏览器的前进后退功能");
        System.out.println("2. 文本编辑器的撤销和重做功能");
        System.out.println("3. 应用程序的状态管理");
        System.out.println("4. 游戏中的移动历史记录");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 