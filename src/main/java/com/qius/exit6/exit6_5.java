package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题5：基于栈的简单计算器
 * 实现一个支持基本算术运算的计算器，包括括号的处理
 */
public class exit6_5 {
    
    /**
     * 计算算术表达式的值
     * @param expression 要计算的表达式
     * @return 计算结果
     */
    public static double calculate(String expression) {
        // 1. 将中缀表达式转换为后缀表达式
        String postfix = infixToPostfix(expression);
        // 2. 计算后缀表达式的值
        return evaluatePostfix(postfix);
    }
    
    /**
     * 获取操作符的优先级
     */
    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }
    
    /**
     * 检查字符是否是操作符
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
    
    /**
     * 将中缀表达式转换为后缀表达式
     */
    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        ArrayListStack<Character> stack = new ArrayListStack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            // 跳过空格
            if (c == ' ') {
                continue;
            }
            
            // 处理数字
            if (Character.isDigit(c)) {
                postfix.append(c);
                
                // 处理多位数和小数点
                while (i + 1 < infix.length() && 
                      (Character.isDigit(infix.charAt(i + 1)) || infix.charAt(i + 1) == '.')) {
                    postfix.append(infix.charAt(i + 1));
                    i++;
                }
                
                postfix.append(' '); // 数字之间用空格分隔
            }
            // 处理左括号
            else if (c == '(') {
                stack.push(c);
            }
            // 处理右括号
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(' ');
                }
                
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // 弹出左括号但不加入输出
                } else {
                    throw new IllegalArgumentException("括号不匹配");
                }
            }
            // 处理操作符
            else if (isOperator(c)) {
                // 处理一元负号
                if (c == '-' && (i == 0 || infix.charAt(i - 1) == '(' || isOperator(infix.charAt(i - 1)))) {
                    postfix.append("0 "); // 将一元负号转换为 0-x
                }
                
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix.append(stack.pop()).append(' ');
                }
                
                stack.push(c);
            }
            else {
                throw new IllegalArgumentException("表达式包含无效字符: " + c);
            }
        }
        
        // 处理栈中剩余的操作符
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new IllegalArgumentException("括号不匹配");
            }
            postfix.append(stack.pop()).append(' ');
        }
        
        return postfix.toString().trim();
    }
    
    /**
     * 计算后缀表达式的值
     */
    private static double evaluatePostfix(String postfix) {
        ArrayListStack<Double> stack = new ArrayListStack<>();
        String[] tokens = postfix.split("\\s+");
        
        for (String token : tokens) {
            // 如果是数字，直接入栈
            try {
                double number = Double.parseDouble(token);
                stack.push(number);
            } 
            // 如果是操作符，弹出操作数进行计算
            catch (NumberFormatException e) {
                if (token.length() != 1 || !isOperator(token.charAt(0))) {
                    throw new IllegalArgumentException("无效的标记: " + token);
                }
                
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("表达式格式错误");
                }
                
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = 0;
                
                switch (token.charAt(0)) {
                    case '+':
                        result = operand1 + operand2;
                        break;
                    case '-':
                        result = operand1 - operand2;
                        break;
                    case '*':
                        result = operand1 * operand2;
                        break;
                    case '/':
                        if (operand2 == 0) {
                            throw new ArithmeticException("除数不能为零");
                        }
                        result = operand1 / operand2;
                        break;
                    case '^':
                        result = Math.pow(operand1, operand2);
                        break;
                }
                
                stack.push(result);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("表达式格式错误");
        }
        
        return stack.pop();
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("基于栈的计算器");
        System.out.println("支持的操作: +, -, *, /, ^, 以及括号()");
        System.out.println("示例: 3 + 4 * 2 = 11");
        System.out.println("示例: (3 + 4) * 2 = 14");
        System.out.println("输入'exit'退出");
        
        while (true) {
            System.out.print("\n请输入表达式: ");
            String expression = input.nextLine().trim();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            if (expression.isEmpty()) {
                continue;
            }
            
            try {
                double result = calculate(expression);
                System.out.println("结果: " + result);
            } catch (Exception e) {
                System.out.println("错误: " + e.getMessage());
            }
        }
        
        System.out.println("程序结束");
        input.close();
    }
} 