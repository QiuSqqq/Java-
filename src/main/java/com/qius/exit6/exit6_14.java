package com.qius.exit6;

import java.util.Scanner;
import java.util.Arrays;

/**
 * 第六章习题14：基于栈的排序算法
 * 使用栈实现排序算法，演示栈在排序中的应用
 * 
 * 本程序展示了三种基于栈的排序方法：
 * 1. 栈排序：利用临时栈将元素从原始栈中排序
 * 2. 合并排序：使用栈实现归并排序的一种变体
 * 3. 基数排序：使用栈实现基数排序
 * 
 * 虽然这些不是最常见的排序实现方式，但它们展示了栈在排序算法中的潜在应用，
 * 并展示了如何利用栈的LIFO特性来实现各种排序策略。
 */
public class exit6_14 {
    
    /**
     * 使用一个临时栈对原始栈进行排序
     * 基本思想：将原始栈中的元素依次弹出，然后按顺序插入到临时栈中
     * 时间复杂度：O(n²)
     * 空间复杂度：O(n)
     * 
     * @param stack 要排序的栈
     */
    public static void stackSort(ArrayListStack<Integer> stack) {
        // 如果栈为空或只有一个元素，已经是有序的
        if (stack == null || stack.isEmpty() || stack.size() == 1) {
            return;
        }
        
        // 创建临时栈
        ArrayListStack<Integer> tempStack = new ArrayListStack<>();
        
        while (!stack.isEmpty()) {
            // 从原始栈中弹出一个元素
            int temp = stack.pop();
            
            // 将临时栈中所有大于temp的元素移回原始栈
            while (!tempStack.isEmpty() && tempStack.peek() > temp) {
                stack.push(tempStack.pop());
            }
            
            // 将temp放入临时栈
            tempStack.push(temp);
        }
        
        // 将排序后的元素从临时栈移回原始栈（此时顺序会反转）
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        
        // 再次反转栈，使其按升序排列
        ArrayListStack<Integer> reverseStack = new ArrayListStack<>();
        while (!stack.isEmpty()) {
            reverseStack.push(stack.pop());
        }
        
        // 将排序后的元素放回原始栈
        while (!reverseStack.isEmpty()) {
            stack.push(reverseStack.pop());
        }
    }
    
    /**
     * 使用栈实现归并排序的变体
     * 基本思想：将原始数组分成两部分，分别排序后再合并
     * 时间复杂度：O(n log n)
     * 空间复杂度：O(n)
     * 
     * @param array 要排序的数组
     */
    public static void stackMergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        // 创建两个栈，分别存储左半部分和右半部分
        ArrayListStack<Integer> leftStack = new ArrayListStack<>();
        ArrayListStack<Integer> rightStack = new ArrayListStack<>();
        
        // 分割数组
        int mid = array.length / 2;
        for (int i = 0; i < mid; i++) {
            leftStack.push(array[i]);
        }
        for (int i = mid; i < array.length; i++) {
            rightStack.push(array[i]);
        }
        
        // 对左右两个栈分别进行排序
        stackSort(leftStack);
        stackSort(rightStack);
        
        // 创建临时栈用于合并
        ArrayListStack<Integer> mergedStack = new ArrayListStack<>();
        
        // 合并两个已排序的栈
        while (!leftStack.isEmpty() && !rightStack.isEmpty()) {
            if (leftStack.peek() <= rightStack.peek()) {
                mergedStack.push(leftStack.pop());
            } else {
                mergedStack.push(rightStack.pop());
            }
        }
        
        // 处理剩余的元素
        while (!leftStack.isEmpty()) {
            mergedStack.push(leftStack.pop());
        }
        while (!rightStack.isEmpty()) {
            mergedStack.push(rightStack.pop());
        }
        
        // 将合并后的元素放回原始数组
        for (int i = array.length - 1; i >= 0; i--) {
            if (!mergedStack.isEmpty()) {
                array[i] = mergedStack.pop();
            }
        }
    }
    
    /**
     * 使用栈实现基数排序
     * 基本思想：按位数字依次排序，从最低位到最高位
     * 时间复杂度：O(d * (n + b))，其中d是最大数的位数，b是基数（这里是10）
     * 空间复杂度：O(n + b)
     * 
     * @param array 要排序的非负整数数组
     */
    public static void stackRadixSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        
        // 找出最大值，确定最大位数
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        
        // 创建10个栈，对应数字0-9
        @SuppressWarnings("unchecked")
        ArrayListStack<Integer>[] digitStacks = new ArrayListStack[10];
        for (int i = 0; i < 10; i++) {
            digitStacks[i] = new ArrayListStack<>();
        }
        
        // 从最低位开始，逐位排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            // 将数字按当前位分配到对应的栈
            for (int i = 0; i < array.length; i++) {
                int digit = (array[i] / exp) % 10;
                digitStacks[digit].push(array[i]);
            }
            
            // 收集各栈中的数字，放回数组
            int index = 0;
            for (int i = 0; i < 10; i++) {
                // 使用临时栈反转顺序，保持稳定性
                ArrayListStack<Integer> tempStack = new ArrayListStack<>();
                while (!digitStacks[i].isEmpty()) {
                    tempStack.push(digitStacks[i].pop());
                }
                
                while (!tempStack.isEmpty() && index < array.length) {
                    array[index++] = tempStack.pop();
                }
            }
        }
    }
    
    /**
     * 使用栈进行选择排序
     * 基本思想：每次从栈中找出最大值，依次放入新栈
     * 时间复杂度：O(n²)
     * 空间复杂度：O(n)
     * 
     * @param stack 要排序的栈
     */
    public static void stackSelectionSort(ArrayListStack<Integer> stack) {
        if (stack == null || stack.isEmpty() || stack.size() == 1) {
            return;
        }
        
        ArrayListStack<Integer> sortedStack = new ArrayListStack<>();
        
        while (!stack.isEmpty()) {
            // 找出栈中的最大值
            int max = findMax(stack);
            
            // 移除最大值外的所有元素到临时栈
            ArrayListStack<Integer> tempStack = new ArrayListStack<>();
            while (!stack.isEmpty()) {
                int current = stack.pop();
                if (current != max) {
                    tempStack.push(current);
                }
            }
            
            // 将最大值放入排序栈
            sortedStack.push(max);
            
            // 将临时栈中的元素放回原始栈
            while (!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }
        }
        
        // 将排序后的元素放回原始栈
        while (!sortedStack.isEmpty()) {
            stack.push(sortedStack.pop());
        }
    }
    
    /**
     * 查找栈中的最大值（不改变栈的内容）
     * @param stack 要查找的栈
     * @return 栈中的最大值
     */
    private static int findMax(ArrayListStack<Integer> stack) {
        if (stack.isEmpty()) {
            throw new IllegalStateException("栈为空，无法找到最大值");
        }
        
        int max = Integer.MIN_VALUE;
        
        // 使用临时栈遍历所有元素
        ArrayListStack<Integer> tempStack = new ArrayListStack<>();
        
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (current > max) {
                max = current;
            }
            tempStack.push(current);
        }
        
        // 还原原始栈
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        
        return max;
    }
    
    /**
     * 创建一个新的包含指定元素的栈
     * @param elements 要添加到栈中的元素
     * @return 包含指定元素的栈
     */
    public static ArrayListStack<Integer> createStackFromArray(int[] elements) {
        ArrayListStack<Integer> stack = new ArrayListStack<>();
        for (int element : elements) {
            stack.push(element);
        }
        return stack;
    }
    
    /**
     * 将栈中的元素转换为数组
     * @param stack 要转换的栈
     * @return 包含栈中元素的数组
     */
    public static int[] stackToArray(ArrayListStack<Integer> stack) {
        if (stack.isEmpty()) {
            return new int[0];
        }
        
        // 使用临时栈，不改变原始栈
        ArrayListStack<Integer> tempStack = new ArrayListStack<>();
        while (!stack.isEmpty()) {
            tempStack.push(stack.pop());
        }
        
        int[] array = new int[tempStack.size()];
        int index = 0;
        
        while (!tempStack.isEmpty()) {
            array[index++] = tempStack.peek();
            stack.push(tempStack.pop());
        }
        
        return array;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("基于栈的排序算法 - 演示");
        System.out.println("1. 栈排序");
        System.out.println("2. 栈实现的归并排序");
        System.out.println("3. 栈实现的基数排序");
        System.out.println("4. 栈选择排序");
        System.out.print("请选择排序算法(1-4): ");
        
        int choice = input.nextInt();
        
        System.out.print("请输入要排序的整数个数: ");
        int count = input.nextInt();
        
        int[] array = new int[count];
        System.out.print("请依次输入" + count + "个整数: ");
        for (int i = 0; i < count; i++) {
            array[i] = input.nextInt();
        }
        
        System.out.println("原始数组: " + Arrays.toString(array));
        
        long startTime = System.nanoTime();
        
        switch (choice) {
            case 1:
                System.out.println("使用栈排序...");
                ArrayListStack<Integer> stack1 = createStackFromArray(array);
                stackSort(stack1);
                int[] sortedArray1 = stackToArray(stack1);
                System.out.println("排序后数组: " + Arrays.toString(sortedArray1));
                break;
                
            case 2:
                System.out.println("使用栈实现的归并排序...");
                int[] arrayCopy2 = Arrays.copyOf(array, array.length);
                stackMergeSort(arrayCopy2);
                System.out.println("排序后数组: " + Arrays.toString(arrayCopy2));
                break;
                
            case 3:
                System.out.println("使用栈实现的基数排序...");
                int[] arrayCopy3 = Arrays.copyOf(array, array.length);
                stackRadixSort(arrayCopy3);
                System.out.println("排序后数组: " + Arrays.toString(arrayCopy3));
                break;
                
            case 4:
                System.out.println("使用栈选择排序...");
                ArrayListStack<Integer> stack4 = createStackFromArray(array);
                stackSelectionSort(stack4);
                int[] sortedArray4 = stackToArray(stack4);
                System.out.println("排序后数组: " + Arrays.toString(sortedArray4));
                break;
                
            default:
                System.out.println("无效的选择");
        }
        
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("排序耗时: " + duration + " 毫秒");
        
        System.out.println("\n基于栈的排序算法特点:");
        System.out.println("1. 栈排序 - 简单直观，但效率较低，适合小数据量");
        System.out.println("2. 栈归并排序 - 效率较高，适合大数据量，但实现较复杂");
        System.out.println("3. 栈基数排序 - 适合整数排序，对于大范围整数非常高效");
        System.out.println("4. 栈选择排序 - 演示了如何使用栈实现传统的选择排序算法");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 