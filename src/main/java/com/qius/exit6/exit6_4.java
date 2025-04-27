package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题4：中缀表达式转后缀表达式
 * 使用栈实现中缀表达式转换为后缀表达式（逆波兰表示法）
 */
public class exit6_4 {
    
    /**
     * 获取操作符的优先级
     * @param operator 操作符
     * @return 优先级（数字越大优先级越高）
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
     * @param infix 中缀表达式
     * @return 后缀表达式
     */
    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        ArrayListStack<Character> stack = new ArrayListStack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            // 跳过空格
            if (c == ' ') {
                continue;
            }
            
            // 如果是数字或字母，直接加入后缀表达式
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c);
                
                // 如果是多位数，继续读取
                while (i + 1 < infix.length() && 
                      (Character.isLetterOrDigit(infix.charAt(i + 1)) || infix.charAt(i + 1) == '.')) {
                    postfix.append(infix.charAt(i + 1));
                    i++;
                }
                
                postfix.append(' '); // 用空格分隔不同的操作数
            }
            // 如果是左括号，入栈
            else if (c == '(') {
                stack.push(c);
            }
            // 如果是右括号，弹出栈中元素直到遇到左括号
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(' ');
                }
                
                // 弹出左括号（但不加入后缀表达式）
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    // 没有匹配的左括号
                    throw new IllegalArgumentException("表达式括号不匹配");
                }
            }
            // 如果是操作符
            else if (isOperator(c)) {
                // 弹出优先级大于等于当前操作符的所有操作符
                while (!stack.isEmpty() && stack.peek() != '(' && 
                       getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix.append(stack.pop()).append(' ');
                }
                // 当前操作符入栈
                stack.push(c);
            }
            else {
                throw new IllegalArgumentException("表达式包含无效字符: " + c);
            }
        }
        
        // 将栈中剩余的操作符加入后缀表达式
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new IllegalArgumentException("表达式括号不匹配");
            }
            postfix.append(stack.pop()).append(' ');
        }
        
        return postfix.toString().trim();
    }
    
    /**
     * 简单的后缀表达式计算器
     * 仅支持操作数为数字的情况
     */
    public static double evaluatePostfix(String postfix) {
        ArrayListStack<Double> stack = new ArrayListStack<>();
        String[] tokens = postfix.split("\\s+");
        
        for (String token : tokens) {
            // 如果是数字，入栈
            try {
                double number = Double.parseDouble(token);
                stack.push(number);
            } 
            // 如果是操作符，弹出两个操作数计算结果
            catch (NumberFormatException e) {
                if (token.length() != 1 || !isOperator(token.charAt(0))) {
                    throw new IllegalArgumentException("无效的标记: " + token);
                }
                
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("表达式格式错误：操作符 " + token + " 前没有足够的操作数");
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
                            throw new IllegalArgumentException("除数不能为零");
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
        
        System.out.println("中缀表达式转后缀表达式转换器");
        System.out.println("示例：A+B*C => A B C * +");
        System.out.println("示例：3+4*2 => 3 4 2 * +");
        System.out.println("输入'exit'退出程序");
        
        while (true) {
            System.out.print("\n请输入中缀表达式: ");
            String infix = input.nextLine().trim();
            
            if (infix.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                String postfix = infixToPostfix(infix);
                System.out.println("后缀表达式: " + postfix);
                
                // 尝试计算表达式的值（仅当表达式中只包含数字和操作符时）
                boolean containsOnlyDigitsAndOperators = true;
                for (char c : infix.toCharArray()) {
                    if (!(Character.isDigit(c) || isOperator(c) || c == '.' || c == ' ' || c == '(' || c == ')')) {
                        containsOnlyDigitsAndOperators = false;
                        break;
                    }
                }
                
                if (containsOnlyDigitsAndOperators) {
                    System.out.print("是否要计算表达式的值？(y/n): ");
                    String choice = input.nextLine().trim().toLowerCase();
                    
                    if (choice.equals("y") || choice.equals("yes")) {
                        try {
                            double result = evaluatePostfix(postfix);
                            System.out.println("计算结果: " + result);
                        } catch (Exception e) {
                            System.out.println("无法计算表达式值: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("表达式包含非数字字符，无法计算结果");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("错误: " + e.getMessage());
            }
        }
        
        System.out.println("程序结束");
        input.close();
    }
} 