package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题10：简单计算器实现
 * 使用两个栈分别存储操作数和操作符，实现简单的算术表达式计算
 */
public class exit6_10 {
    
    /**
     * 简单计算器类，使用两个栈分别存储操作数和操作符
     */
    public static class SimpleCalculator {
        private ArrayListStack<Double> operandStack;  // 操作数栈
        private ArrayListStack<Character> operatorStack;  // 操作符栈
        
        public SimpleCalculator() {
            operandStack = new ArrayListStack<>();
            operatorStack = new ArrayListStack<>();
        }
        
        /**
         * 计算表达式的值
         * @param expression 算术表达式字符串
         * @return 计算结果
         * @throws IllegalArgumentException 如果表达式格式不正确
         */
        public double evaluate(String expression) {
            // 清空栈以便重新计算
            while (!operandStack.isEmpty()) {
                operandStack.pop();
            }
            while (!operatorStack.isEmpty()) {
                operatorStack.pop();
            }
            
            // 移除所有空格
            expression = expression.replaceAll("\\s+", "");
            
            for (int i = 0; i < expression.length(); i++) {
                char ch = expression.charAt(i);
                
                // 处理数字，包括小数点和多位数
                if (Character.isDigit(ch) || ch == '.') {
                    StringBuilder numBuilder = new StringBuilder();
                    int j = i;
                    while (j < expression.length() && 
                           (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                        numBuilder.append(expression.charAt(j));
                        j++;
                    }
                    
                    try {
                        double num = Double.parseDouble(numBuilder.toString());
                        operandStack.push(num);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("无效的数字: " + numBuilder.toString());
                    }
                    
                    i = j - 1; // 更新索引
                }
                // 处理左括号
                else if (ch == '(') {
                    operatorStack.push(ch);
                }
                // 处理右括号
                else if (ch == ')') {
                    // 计算括号内的所有操作
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        processOperation();
                    }
                    
                    // 弹出左括号
                    if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
                        operatorStack.pop();
                    } else {
                        throw new IllegalArgumentException("括号不匹配");
                    }
                }
                // 处理操作符
                else if (isOperator(ch)) {
                    // 处理一元负号
                    if (ch == '-' && (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1)))) {
                        operandStack.push(0.0); // 添加0，将一元负号转换为二元减法
                    }
                    
                    // 处理优先级，如果当前操作符优先级小于等于栈顶操作符，则先计算栈中的运算
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(' &&
                           getPrecedence(ch) <= getPrecedence(operatorStack.peek())) {
                        processOperation();
                    }
                    
                    // 将当前操作符压栈
                    operatorStack.push(ch);
                }
                else {
                    throw new IllegalArgumentException("无效的字符: " + ch);
                }
            }
            
            // 处理剩余的所有操作
            while (!operatorStack.isEmpty()) {
                if (operatorStack.peek() == '(') {
                    throw new IllegalArgumentException("括号不匹配");
                }
                processOperation();
            }
            
            // 最终栈中应该只剩下一个操作数，即计算结果
            if (operandStack.size() != 1) {
                throw new IllegalArgumentException("表达式格式错误");
            }
            
            return operandStack.pop();
        }
        
        /**
         * 执行一个操作，从操作符栈弹出一个操作符，从操作数栈弹出两个操作数，
         * 执行计算后将结果压回操作数栈
         */
        private void processOperation() {
            if (operatorStack.isEmpty()) {
                throw new IllegalArgumentException("表达式格式错误：缺少操作符");
            }
            
            char operator = operatorStack.pop();
            
            // 需要两个操作数
            if (operandStack.size() < 2) {
                throw new IllegalArgumentException("表达式格式错误：操作符 " + operator + " 缺少操作数");
            }
            
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            double result = 0;
            
            switch (operator) {
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
                default:
                    throw new IllegalArgumentException("不支持的操作符: " + operator);
            }
            
            // 将结果压回操作数栈
            operandStack.push(result);
        }
        
        /**
         * 检查字符是否是支持的操作符
         */
        private boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
        }
        
        /**
         * 获取操作符的优先级
         * @return 优先级值，数值越大优先级越高
         */
        private int getPrecedence(char operator) {
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
                    return 0;
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SimpleCalculator calculator = new SimpleCalculator();
        
        System.out.println("简单计算器 - 使用栈实现");
        System.out.println("支持的操作: +, -, *, /, ^, 以及括号");
        System.out.println("示例: 3 + 4 * 2 = 11");
        System.out.println("示例: (3 + 4) * 2 = 14");
        System.out.println("示例: -5 + 10 = 5");
        System.out.println("输入'exit'退出");
        
        while (true) {
            System.out.print("\n请输入算术表达式: ");
            String expression = input.nextLine().trim();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            if (expression.isEmpty()) {
                continue;
            }
            
            try {
                double result = calculator.evaluate(expression);
                System.out.println("计算结果: " + result);
            } catch (Exception e) {
                System.out.println("错误: " + e.getMessage());
            }
        }
        
        System.out.println("\n本计算器的优势:");
        System.out.println("1. 支持基本的算术运算和括号嵌套");
        System.out.println("2. 正确处理操作符优先级");
        System.out.println("3. 支持一元负号");
        System.out.println("4. 使用两个栈分别管理操作数和操作符，实现简洁清晰");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 