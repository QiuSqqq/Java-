package com.qius.exit5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 问题8：编写一个程序展示如何使用断言来验证程序状态和参数
 * 
 * 注意：要启用断言，运行时需要使用 -ea 参数：
 * java -ea com.qius.exit5.exit5_8
 */
public class exit5_8 {
    
    /**
     * 使用断言检查参数合法性
     * @param value 要检查的值（应该为正数）
     * @return 平方根值
     */
    public static double squareRoot(double value) {
        // 使用断言检查参数是否为正数
        assert value >= 0 : "squareRoot: 参数必须为非负数，传入的值为 " + value;
        
        return Math.sqrt(value);
    }
    
    /**
     * 使用断言验证数组排序状态
     * @param arr 要排序的数组
     */
    public static void sortArrayWithAssertion(int[] arr) {
        System.out.println("原始数组: " + Arrays.toString(arr));
        
        // 排序前不做断言检查
        
        // 执行排序
        Arrays.sort(arr);
        
        // 排序后断言数组已经是有序的
        assert isSorted(arr) : "排序后的数组不是有序的";
        
        System.out.println("排序后: " + Arrays.toString(arr));
    }
    
    /**
     * 检查数组是否已排序
     * @param arr 要检查的数组
     * @return 如果数组已排序则返回true
     */
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 二分查找算法，使用断言验证前置条件
     * @param arr 要搜索的数组（必须已排序）
     * @param value 要查找的值
     * @return 找到值的索引，或-1表示未找到
     */
    public static int binarySearch(int[] arr, int value) {
        // 前置条件：数组必须已排序
        assert isSorted(arr) : "binarySearch: 数组必须已排序";
        
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            // 循环不变量：如果value在数组中，它位于索引[low..high]之间
            assert value > arr[low - 1] || low == 0 : "循环不变量失败：下限";
            assert value < arr[high + 1] || high == arr.length - 1 : "循环不变量失败：上限";
            
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        // 结果：value不在数组中
        return -1;
    }
    
    /**
     * 使用断言检查方法的后置条件
     * @param num1 第一个数
     * @param num2 第二个数
     * @return 两个数中的最大值
     */
    public static int findMax(int num1, int num2) {
        int result = (num1 > num2) ? num1 : num2;
        
        // 后置条件：结果必须大于或等于两个输入
        assert result >= num1 && result >= num2 : "findMax: 结果不是最大值";
        // 后置条件：结果必须等于其中一个输入
        assert result == num1 || result == num2 : "findMax: 结果不是输入值之一";
        
        return result;
    }
    
    /**
     * 演示类不变量断言
     */
    public static class Stack {
        private List<Object> elements = new ArrayList<>();
        private int size = 0;
        private static final int MAX_SIZE = 10;
        
        /**
         * 检查类不变量
         */
        private void checkInvariant() {
            assert size >= 0 : "size不能为负数";
            assert size <= elements.size() : "size不能大于elements.size()";
            assert size <= MAX_SIZE : "栈溢出";
        }
        
        /**
         * 将元素压入栈
         * @param element 要添加的元素
         */
        public void push(Object element) {
            checkInvariant();  // 前置条件检查
            
            if (size < MAX_SIZE) {
                if (size < elements.size()) {
                    elements.set(size, element);
                } else {
                    elements.add(element);
                }
                size++;
            } else {
                throw new IllegalStateException("栈已满");
            }
            
            checkInvariant();  // 后置条件检查
        }
        
        /**
         * 从栈中弹出元素
         * @return 弹出的元素
         */
        public Object pop() {
            checkInvariant();  // 前置条件检查
            
            if (size == 0) {
                throw new IllegalStateException("栈为空");
            }
            
            Object result = elements.get(size - 1);
            size--;
            
            checkInvariant();  // 后置条件检查
            return result;
        }
        
        /**
         * 获取栈的大小
         * @return 栈中的元素数量
         */
        public int size() {
            return size;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Stack [");
            for (int i = 0; i < size; i++) {
                sb.append(elements.get(i));
                if (i < size - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 断言示例程序 ===");
        System.out.println("注意：需要用 -ea 参数启用断言");
        System.out.println("例如：java -ea com.qius.exit5.exit5_8\n");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            // 1. 前置条件断言示例
            System.out.println("=== 前置条件断言示例 ===");
            System.out.print("输入一个数计算平方根（负数将触发断言错误）: ");
            double inputValue = scanner.nextDouble();
            
            try {
                double result = squareRoot(inputValue);
                System.out.println(inputValue + " 的平方根是: " + result);
            } catch (AssertionError e) {
                System.err.println("断言错误: " + e.getMessage());
            }
            System.out.println();
            
            // 2. 排序和状态验证示例
            System.out.println("=== 排序状态验证示例 ===");
            int[] arr = {5, 2, 9, 1, 5, 6};
            sortArrayWithAssertion(arr);
            System.out.println();
            
            // 3. 二分查找示例（使用了前置条件和循环不变量）
            System.out.println("=== 二分查找示例 ===");
            System.out.print("在已排序数组中要查找的值: ");
            int searchValue = scanner.nextInt();
            
            int foundIndex = binarySearch(arr, searchValue);
            if (foundIndex != -1) {
                System.out.println("找到值 " + searchValue + " 在索引 " + foundIndex);
            } else {
                System.out.println("未找到值 " + searchValue);
            }
            System.out.println();
            
            // 4. 后置条件断言示例
            System.out.println("=== 后置条件断言示例 ===");
            System.out.print("输入两个整数（空格分隔）找出最大值: ");
            int num1 = scanner.nextInt();
            int num2 = scanner.nextInt();
            
            int max = findMax(num1, num2);
            System.out.println(num1 + " 和 " + num2 + " 的最大值是: " + max);
            System.out.println();
            
            // 5. 类不变量断言示例
            System.out.println("=== 类不变量断言示例 ===");
            Stack stack = new Stack();
            
            // 压入几个元素
            System.out.println("压入元素到栈中...");
            for (int i = 1; i <= 5; i++) {
                stack.push("Item" + i);
                System.out.println("压入: Item" + i + ", 栈: " + stack);
            }
            
            // 弹出元素
            System.out.println("\n弹出元素...");
            for (int i = 0; i < 3; i++) {
                Object item = stack.pop();
                System.out.println("弹出: " + item + ", 栈: " + stack);
            }
            
        } finally {
            scanner.close();
        }
    }
} 