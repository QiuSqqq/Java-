package com.qius.exit6;

import java.util.Scanner;

/**
 * 使用栈检查括号匹配的程序
 */
public class BracketMatcher {
    
    /**
     * 检查表达式中的括号是否匹配
     * @param expression 要检查的表达式
     * @return 如果所有括号都匹配则返回true，否则返回false
     */
    public static boolean checkBrackets(String expression) {
        ArrayListStack<Character> stack = new ArrayListStack<>();
        
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            
            // 如果是左括号，则入栈
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            // 如果是右括号，则检查栈顶元素是否匹配
            else if (ch == ')' || ch == ']' || ch == '}') {
                // 如果栈为空，说明没有对应的左括号
                if (stack.isEmpty()) {
                    return false;
                }
                
                // 获取栈顶元素（左括号）
                char top = stack.pop();
                
                // 检查是否匹配
                if ((ch == ')' && top != '(') ||
                    (ch == ']' && top != '[') ||
                    (ch == '}' && top != '{')) {
                    return false;
                }
            }
        }
        
        // 如果栈为空，说明所有括号都匹配了
        // 如果栈不为空，说明有未匹配的左括号
        return stack.isEmpty();
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("括号匹配检查器");
        System.out.println("输入包含括号的表达式（输入'exit'退出）");
        
        while (true) {
            System.out.print("\n请输入表达式: ");
            String expression = input.nextLine();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            boolean isValid = checkBrackets(expression);
            
            if (isValid) {
                System.out.println("✓ 括号匹配正确");
            } else {
                System.out.println("✗ 括号不匹配");
            }
        }
        
        System.out.println("程序结束");
        input.close();
    }
} 