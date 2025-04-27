package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题16：多栈管理系统
 * 本程序演示如何在一个数组上实现多个栈，并提供栈管理功能
 * 
 * 多栈系统在操作系统和内存管理中有重要应用。这个程序展示了：
 * 1. 如何在一个数组中高效地实现多个独立栈
 * 2. 如何动态扩展栈的容量
 * 3. 如何管理和操作多个栈
 */
public class exit6_16 {
    
    /**
     * 多栈管理器类
     * 在一个数组上实现多个栈
     */
    public static class MultiStackManager {
        private int[] array;           // 存储所有栈的数组
        private int[] topIndices;      // 每个栈的栈顶索引
        private int[] stackSizes;      // 每个栈的当前大小
        private int[] stackCapacities; // 每个栈的容量
        private int stackCount;        // 栈的数量
        private int totalCapacity;     // 总容量
        
        /**
         * 构造函数 - 创建具有指定数量栈和总容量的多栈管理器
         * @param numberOfStacks 栈的数量
         * @param totalCapacity 总容量
         */
        public MultiStackManager(int numberOfStacks, int totalCapacity) {
            if (numberOfStacks <= 0 || totalCapacity <= 0) {
                throw new IllegalArgumentException("栈数量和总容量必须为正数");
            }
            
            this.stackCount = numberOfStacks;
            this.totalCapacity = totalCapacity;
            
            array = new int[totalCapacity];
            topIndices = new int[numberOfStacks];
            stackSizes = new int[numberOfStacks];
            stackCapacities = new int[numberOfStacks];
            
            // 初始化每个栈
            int capacityPerStack = totalCapacity / numberOfStacks;
            int extraCapacity = totalCapacity % numberOfStacks;
            
            int startIndex = 0;
            for (int i = 0; i < numberOfStacks; i++) {
                // 分配容量，平均分配剩余容量
                int stackCapacity = capacityPerStack;
                if (i < extraCapacity) {
                    stackCapacity++;
                }
                
                // 设置每个栈的信息
                stackCapacities[i] = stackCapacity;
                topIndices[i] = startIndex - 1;  // 栈顶初始为-1
                
                startIndex += stackCapacity;
            }
        }
        
        /**
         * 判断指定栈是否为空
         * @param stackNum 栈编号（从1开始）
         * @return 如果栈为空返回true，否则返回false
         */
        public boolean isEmpty(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            return stackSizes[index] == 0;
        }
        
        /**
         * 判断指定栈是否已满
         * @param stackNum 栈编号（从1开始）
         * @return 如果栈已满返回true，否则返回false
         */
        public boolean isFull(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            return stackSizes[index] == stackCapacities[index];
        }
        
        /**
         * 向指定栈中压入元素
         * @param stackNum 栈编号（从1开始）
         * @param value 要压入的值
         * @throws RuntimeException 如果栈已满
         */
        public void push(int stackNum, int value) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            
            if (isFull(stackNum)) {
                if (!expandStack(stackNum)) {
                    throw new RuntimeException("栈 " + stackNum + " 已满，无法扩展");
                }
            }
            
            // 计算新的栈顶位置
            topIndices[index]++;
            array[topIndices[index]] = value;
            stackSizes[index]++;
        }
        
        /**
         * 从指定栈中弹出元素
         * @param stackNum 栈编号（从1开始）
         * @return 弹出的值
         * @throws RuntimeException 如果栈为空
         */
        public int pop(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            
            if (isEmpty(stackNum)) {
                throw new RuntimeException("栈 " + stackNum + " 为空");
            }
            
            int value = array[topIndices[index]];
            array[topIndices[index]] = 0;  // 清空位置（非必需）
            topIndices[index]--;
            stackSizes[index]--;
            
            return value;
        }
        
        /**
         * 查看指定栈的栈顶元素
         * @param stackNum 栈编号（从1开始）
         * @return 栈顶的值
         * @throws RuntimeException 如果栈为空
         */
        public int peek(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            
            if (isEmpty(stackNum)) {
                throw new RuntimeException("栈 " + stackNum + " 为空");
            }
            
            return array[topIndices[index]];
        }
        
        /**
         * 获取指定栈的大小
         * @param stackNum 栈编号（从1开始）
         * @return 栈中元素的数量
         */
        public int size(int stackNum) {
            validateStackNum(stackNum);
            return stackSizes[stackNum - 1];
        }
        
        /**
         * 获取指定栈的容量
         * @param stackNum 栈编号（从1开始）
         * @return 栈的容量
         */
        public int capacity(int stackNum) {
            validateStackNum(stackNum);
            return stackCapacities[stackNum - 1];
        }
        
        /**
         * 获取栈的总数
         * @return 栈的数量
         */
        public int getStackCount() {
            return stackCount;
        }
        
        /**
         * 获取所有栈的总容量
         * @return 总容量
         */
        public int getTotalCapacity() {
            return totalCapacity;
        }
        
        /**
         * 清空指定栈
         * @param stackNum 栈编号（从1开始）
         */
        public void clear(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            
            // 清空栈中的所有元素
            int startIndex = topIndices[index] - stackSizes[index] + 1;
            for (int i = startIndex; i <= topIndices[index]; i++) {
                array[i] = 0;  // 清空位置（非必需）
            }
            
            // 重置栈顶指针和大小
            topIndices[index] = startIndex - 1;
            stackSizes[index] = 0;
        }
        
        /**
         * 输出多栈的状态信息
         * @return 包含多栈状态的字符串
         */
        public String getStatusInfo() {
            StringBuilder sb = new StringBuilder();
            sb.append("多栈系统状态:\n");
            sb.append("总容量: ").append(totalCapacity).append("\n");
            sb.append("栈数量: ").append(stackCount).append("\n");
            
            for (int i = 0; i < stackCount; i++) {
                int stackNum = i + 1;
                sb.append("栈 ").append(stackNum).append(": ");
                sb.append("大小=").append(stackSizes[i]).append("/").append(stackCapacities[i]);
                sb.append(", 栈顶索引=").append(topIndices[i]);
                sb.append(", 是否为空=").append(isEmpty(stackNum));
                sb.append(", 是否已满=").append(isFull(stackNum)).append("\n");
            }
            
            return sb.toString();
        }
        
        /**
         * 打印指定栈的内容
         * @param stackNum 栈编号（从1开始）
         * @return 包含栈内容的字符串
         */
        public String printStack(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            
            StringBuilder sb = new StringBuilder();
            sb.append("栈 ").append(stackNum).append(" 内容 (从栈顶到栈底): [");
            
            if (!isEmpty(stackNum)) {
                for (int i = topIndices[index]; i > topIndices[index] - stackSizes[index]; i--) {
                    sb.append(array[i]);
                    if (i > topIndices[index] - stackSizes[index] + 1) {
                        sb.append(", ");
                    }
                }
            }
            
            sb.append("]");
            return sb.toString();
        }
        
        /**
         * 验证栈编号是否有效
         * @param stackNum 栈编号（从1开始）
         * @throws IllegalArgumentException 如果栈编号无效
         */
        private void validateStackNum(int stackNum) {
            if (stackNum < 1 || stackNum > stackCount) {
                throw new IllegalArgumentException("无效的栈编号: " + stackNum + "，有效范围: 1-" + stackCount);
            }
        }
        
        /**
         * 尝试扩展指定栈的容量
         * 通过从其他未满的栈"借用"空间
         * @param stackNum 栈编号（从1开始）
         * @return 是否成功扩展
         */
        private boolean expandStack(int stackNum) {
            validateStackNum(stackNum);
            int index = stackNum - 1;
            
            // 寻找有剩余空间的栈
            for (int i = 0; i < stackCount; i++) {
                if (i != index && stackSizes[i] < stackCapacities[i]) {
                    // 从这个栈借用一个单位的容量
                    stackCapacities[i]--;
                    stackCapacities[index]++;
                    
                    // 重新组织数组（实际应用中可能需要更复杂的移动逻辑）
                    int[] newArray = new int[totalCapacity];
                    
                    // 计算每个栈的起始位置
                    int[] startPositions = new int[stackCount];
                    int currentPos = 0;
                    for (int j = 0; j < stackCount; j++) {
                        startPositions[j] = currentPos;
                        currentPos += stackCapacities[j];
                    }
                    
                    // 移动每个栈的内容
                    for (int j = 0; j < stackCount; j++) {
                        int oldStart = topIndices[j] - stackSizes[j] + 1;
                        int newStart = startPositions[j];
                        
                        for (int k = 0; k < stackSizes[j]; k++) {
                            newArray[newStart + k] = array[oldStart + k];
                        }
                        
                        // 更新栈顶索引
                        topIndices[j] = newStart + stackSizes[j] - 1;
                    }
                    
                    // 更新数组
                    array = newArray;
                    return true;
                }
            }
            
            return false;  // 无法扩展
        }
    }
    
    /**
     * 多栈操作示例类
     * 演示如何使用多栈管理器
     */
    public static class MultiStackDemo {
        private MultiStackManager manager;
        private Scanner scanner;
        
        /**
         * 构造函数
         * @param stackCount 栈的数量
         * @param totalCapacity 总容量
         */
        public MultiStackDemo(int stackCount, int totalCapacity) {
            manager = new MultiStackManager(stackCount, totalCapacity);
            scanner = new Scanner(System.in);
        }
        
        /**
         * 运行演示
         */
        public void run() {
            boolean running = true;
            
            while (running) {
                printMenu();
                System.out.print("请选择操作: ");
                
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();  // 消耗换行符
                    
                    switch (choice) {
                        case 1:  // 压入元素
                            handlePush();
                            break;
                            
                        case 2:  // 弹出元素
                            handlePop();
                            break;
                            
                        case 3:  // 查看栈顶
                            handlePeek();
                            break;
                            
                        case 4:  // 检查栈是否为空
                            handleIsEmpty();
                            break;
                            
                        case 5:  // 检查栈是否已满
                            handleIsFull();
                            break;
                            
                        case 6:  // 获取栈大小
                            handleSize();
                            break;
                            
                        case 7:  // 获取栈容量
                            handleCapacity();
                            break;
                            
                        case 8:  // 清空栈
                            handleClear();
                            break;
                            
                        case 9:  // 打印栈的内容
                            handlePrintStack();
                            break;
                            
                        case 10:  // 显示多栈状态
                            System.out.println(manager.getStatusInfo());
                            break;
                            
                        case 0:  // 退出
                            running = false;
                            break;
                            
                        default:
                            System.out.println("无效的选择，请重试");
                    }
                } catch (Exception e) {
                    System.out.println("错误: " + e.getMessage());
                    scanner.nextLine();  // 清除输入
                }
                
                System.out.println();
            }
        }
        
        /**
         * 打印菜单
         */
        private void printMenu() {
            System.out.println("\n多栈管理系统 - 菜单");
            System.out.println("1. 压入元素");
            System.out.println("2. 弹出元素");
            System.out.println("3. 查看栈顶");
            System.out.println("4. 检查栈是否为空");
            System.out.println("5. 检查栈是否已满");
            System.out.println("6. 获取栈大小");
            System.out.println("7. 获取栈容量");
            System.out.println("8. 清空栈");
            System.out.println("9. 打印栈的内容");
            System.out.println("10. 显示多栈状态");
            System.out.println("0. 退出");
        }
        
        private void handlePush() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            System.out.print("要压入的值: ");
            int value = scanner.nextInt();
            
            manager.push(stackNum, value);
            System.out.println("成功将 " + value + " 压入栈 " + stackNum);
        }
        
        private void handlePop() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            int value = manager.pop(stackNum);
            System.out.println("从栈 " + stackNum + " 弹出: " + value);
        }
        
        private void handlePeek() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            int value = manager.peek(stackNum);
            System.out.println("栈 " + stackNum + " 的栈顶元素: " + value);
        }
        
        private void handleIsEmpty() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            boolean empty = manager.isEmpty(stackNum);
            System.out.println("栈 " + stackNum + " 是否为空: " + empty);
        }
        
        private void handleIsFull() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            boolean full = manager.isFull(stackNum);
            System.out.println("栈 " + stackNum + " 是否已满: " + full);
        }
        
        private void handleSize() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            int size = manager.size(stackNum);
            System.out.println("栈 " + stackNum + " 的大小: " + size);
        }
        
        private void handleCapacity() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            int capacity = manager.capacity(stackNum);
            System.out.println("栈 " + stackNum + " 的容量: " + capacity);
        }
        
        private void handleClear() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            manager.clear(stackNum);
            System.out.println("栈 " + stackNum + " 已清空");
        }
        
        private void handlePrintStack() {
            System.out.print("栈编号 (1-" + manager.getStackCount() + "): ");
            int stackNum = scanner.nextInt();
            
            System.out.println(manager.printStack(stackNum));
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("多栈管理系统 - 使用一个数组实现多个栈");
        System.out.print("请输入栈的数量: ");
        int stackCount = input.nextInt();
        
        System.out.print("请输入总容量: ");
        int totalCapacity = input.nextInt();
        
        MultiStackDemo demo = new MultiStackDemo(stackCount, totalCapacity);
        demo.run();
        
        System.out.println("\n多栈管理系统的应用:");
        System.out.println("1. 操作系统中的多线程栈管理");
        System.out.println("2. 编译器中的作用域栈管理");
        System.out.println("3. 内存分配和垃圾回收");
        System.out.println("4. 多用户系统中的资源分配");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 