package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题7：回文检测器
 * 使用栈实现回文字符串检测
 */
public class exit6_7 {
    
    /**
     * 检测字符串是否是回文
     * 回文是指正读和反读都相同的字符序列，如"level"、"radar"、"12321"
     * @param str 要检测的字符串
     * @return 如果是回文返回true，否则返回false
     */
    public static boolean isPalindrome(String str) {
        if (str == null || str.length() <= 1) {
            return true; // 空字符串或只有一个字符的字符串都是回文
        }
        
        // 将字符串转换为小写并移除所有非字母数字字符
        String processed = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        if (processed.isEmpty()) {
            return true; // 如果处理后字符串为空，视为回文
        }
        
        // 使用栈检测回文
        ArrayListStack<Character> stack = new ArrayListStack<>();
        
        // 计算字符串的中点
        int midPoint = processed.length() / 2;
        
        // 将前半部分字符入栈
        for (int i = 0; i < midPoint; i++) {
            stack.push(processed.charAt(i));
        }
        
        // 跳过奇数长度字符串的中间字符
        int startIdx = (processed.length() % 2 == 0) ? midPoint : midPoint + 1;
        
        // 比较后半部分与栈中字符是否匹配
        for (int i = startIdx; i < processed.length(); i++) {
            if (stack.isEmpty() || stack.pop() != processed.charAt(i)) {
                return false;
            }
        }
        
        // 如果栈为空，说明所有字符都匹配
        return stack.isEmpty();
    }
    
    /**
     * 使用双栈检测回文
     * 这种方法将整个字符串都入栈，更加直观但效率较低
     * @param str 要检测的字符串
     * @return 如果是回文返回true，否则返回false
     */
    public static boolean isPalindromeUsingTwoStacks(String str) {
        if (str == null || str.length() <= 1) {
            return true;
        }
        
        // 将字符串转换为小写并移除所有非字母数字字符
        String processed = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        
        if (processed.isEmpty()) {
            return true;
        }
        
        // 创建两个栈
        ArrayListStack<Character> originalStack = new ArrayListStack<>();
        ArrayListStack<Character> reversedStack = new ArrayListStack<>();
        
        // 将处理后的字符串字符入栈
        for (int i = 0; i < processed.length(); i++) {
            char c = processed.charAt(i);
            originalStack.push(c);
        }
        
        // 创建原始栈的副本，并逆序
        ArrayListStack<Character> tempStack = new ArrayListStack<>();
        while (!originalStack.isEmpty()) {
            tempStack.push(originalStack.pop());
        }
        
        // 还原原始栈，并构建逆序栈
        while (!tempStack.isEmpty()) {
            char c = tempStack.pop();
            originalStack.push(c);
            reversedStack.push(c);
        }
        
        // 逆序栈
        tempStack = new ArrayListStack<>();
        while (!reversedStack.isEmpty()) {
            tempStack.push(reversedStack.pop());
        }
        reversedStack = tempStack;
        
        // 逐个比较两个栈中的元素
        while (!originalStack.isEmpty() && !reversedStack.isEmpty()) {
            if (!originalStack.pop().equals(reversedStack.pop())) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("回文检测器 - 使用栈实现");
        System.out.println("回文是指正读和反读都相同的字符序列");
        System.out.println("示例：'level', 'A man, a plan, a canal: Panama', '12321'");
        System.out.println("输入'exit'退出程序");
        
        while (true) {
            System.out.print("\n请输入字符串: ");
            String str = input.nextLine().trim();
            
            if (str.equalsIgnoreCase("exit")) {
                break;
            }
            
            // 使用第一种方法检测
            boolean result1 = isPalindrome(str);
            
            // 使用第二种方法检测
            boolean result2 = isPalindromeUsingTwoStacks(str);
            
            // 输出结果
            if (result1 && result2) {
                System.out.println("'" + str + "' 是回文");
            } else if (!result1 && !result2) {
                System.out.println("'" + str + "' 不是回文");
            } else {
                System.out.println("两种检测方法结果不一致，请检查代码逻辑");
            }
            
            // 解释处理后的字符串
            String processed = str.toLowerCase().replaceAll("[^a-z0-9]", "");
            System.out.println("处理后的字符串: '" + processed + "'");
        }
        
        System.out.println("\n回文检测的应用:");
        System.out.println("1. 文字游戏和益智游戏");
        System.out.println("2. 自然语言处理中的文本分析");
        System.out.println("3. DNA序列分析中的回文序列检测");
        System.out.println("4. 某些字符串算法的基础");
        
        input.close();
    }
} 