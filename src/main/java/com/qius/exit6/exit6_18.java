package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题18：基于栈的四则运算计算器
 * 本程序实现一个支持四则运算（加减乘除）和括号的计算器
 * 
 * 这个计算器采用两个栈来实现表达式求值：
 * 1. 数字栈：存储操作数
 * 2. 操作符栈：存储操作符
 * 
 * 处理流程：
 * 1. 从左到右扫描表达式
 * 2. 遇到数字直接压入数字栈
 * 3. 遇到操作符，与操作符栈顶元素比较优先级：
 *    - 如果优先级高于栈顶，直接压入
 *    - 如果优先级低于或等于栈顶，弹出栈顶操作符执行计算，然后重复比较
 * 4. 最终结果保存在数字栈顶
 */
public class exit6_18 {
    
    /**
     * 基于栈的计算器类
     */
    public static class StackCalculator {
        private ArrayListStack<Double> numberStack;     // 数字栈
        private ArrayListStack<Character> operatorStack;  // 操作符栈
        private String currentExpression;               // 当前表达式
        private StringBuilder logBuilder;               // 日志记录
        private boolean verbose;                        // 是否显示详细日志
        
        /**
         * 构造函数
         * @param verbose 是否显示详细日志
         */
        public StackCalculator(boolean verbose) {
            numberStack = new ArrayListStack<>();
            operatorStack = new ArrayListStack<>();
            logBuilder = new StringBuilder();
            this.verbose = verbose;
        }
        
        /**
         * 计算表达式
         * @param expression 要计算的表达式
         * @return 计算结果
         * @throws RuntimeException 如果表达式无效
         */
        public double calculate(String expression) {
            // 清空栈和日志
            numberStack = new ArrayListStack<>();
            operatorStack = new ArrayListStack<>();
            logBuilder = new StringBuilder();
            
            currentExpression = expression.replaceAll("\\s+", "");  // 移除所有空白字符
            if (currentExpression.isEmpty()) {
                throw new RuntimeException("表达式不能为空");
            }
            
            int i = 0;
            while (i < currentExpression.length()) {
                char c = currentExpression.charAt(i);
                
                if (Character.isDigit(c) || c == '.') {
                    // 解析数字
                    StringBuilder numBuilder = new StringBuilder();
                    while (i < currentExpression.length() && 
                           (Character.isDigit(currentExpression.charAt(i)) || currentExpression.charAt(i) == '.')) {
                        numBuilder.append(currentExpression.charAt(i));
                        i++;
                    }
                    double num = Double.parseDouble(numBuilder.toString());
                    numberStack.push(num);
                    log("数字栈压入: " + num);
                    
                } else if (c == '(') {
                    // 左括号直接压入操作符栈
                    operatorStack.push(c);
                    log("操作符栈压入: (");
                    i++;
                    
                } else if (c == ')') {
                    // 处理右括号：计算括号内的所有运算，直到遇到左括号
                    boolean foundLeftParen = false;
                    while (!operatorStack.isEmpty()) {
                        char op = operatorStack.pop();
                        if (op == '(') {
                            foundLeftParen = true;
                            break;
                        } else {
                            calculateTop(op);
                        }
                    }
                    
                    if (!foundLeftParen) {
                        throw new RuntimeException("括号不匹配");
                    }
                    
                    log("右括号处理完成");
                    i++;
                    
                } else if (isOperator(c)) {
                    // 处理操作符
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(' && 
                          compareOperatorPrecedence(operatorStack.peek(), c) >= 0) {
                        char op = operatorStack.pop();
                        calculateTop(op);
                    }
                    
                    operatorStack.push(c);
                    log("操作符栈压入: " + c);
                    i++;
                    
                } else {
                    // 无效字符
                    throw new RuntimeException("无效字符: " + c);
                }
            }
            
            // 处理剩余的操作符
            while (!operatorStack.isEmpty()) {
                char op = operatorStack.pop();
                if (op == '(') {
                    throw new RuntimeException("括号不匹配");
                }
                calculateTop(op);
            }
            
            // 结果应该在数字栈顶
            if (numberStack.isEmpty()) {
                throw new RuntimeException("表达式无效");
            }
            
            double result = numberStack.pop();
            if (!numberStack.isEmpty()) {
                throw new RuntimeException("表达式无效");
            }
            
            log("计算结果: " + result);
            return result;
        }
        
        /**
         * 执行栈顶的一次运算
         * @param operator 操作符
         */
        private void calculateTop(char operator) {
            if (numberStack.size() < 2) {
                throw new RuntimeException("表达式无效，缺少操作数");
            }
            
            double num2 = numberStack.pop();
            double num1 = numberStack.pop();
            
            double result = 0;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new RuntimeException("除数不能为零");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new RuntimeException("无效操作符: " + operator);
            }
            
            numberStack.push(result);
            log("计算: " + num1 + " " + operator + " " + num2 + " = " + result);
        }
        
        /**
         * 比较两个操作符的优先级
         * @param op1 第一个操作符
         * @param op2 第二个操作符
         * @return -1如果op1优先级低于op2，0如果相等，1如果高于
         */
        private int compareOperatorPrecedence(char op1, char op2) {
            return getOperatorPrecedence(op1) - getOperatorPrecedence(op2);
        }
        
        /**
         * 获取操作符的优先级
         * @param operator 操作符
         * @return 优先级值（越大优先级越高）
         */
        private int getOperatorPrecedence(char operator) {
            switch (operator) {
                case '+':
                case '-':
                    return 1;
                case '*':
                case '/':
                    return 2;
                default:
                    return 0;  // 左括号优先级最低
            }
        }
        
        /**
         * 判断字符是否是操作符
         * @param c 要检查的字符
         * @return 如果是操作符返回true，否则返回false
         */
        private boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/';
        }
        
        /**
         * 记录日志
         * @param message 日志消息
         */
        private void log(String message) {
            logBuilder.append(message).append("\n");
            if (verbose) {
                System.out.println(message);
            }
        }
        
        /**
         * 获取计算过程日志
         * @return 日志字符串
         */
        public String getLog() {
            return logBuilder.toString();
        }
        
        /**
         * 显示当前栈状态
         */
        public void showStacks() {
            System.out.println("\n当前栈状态:");
            System.out.println("数字栈: " + numberStackToString());
            System.out.println("操作符栈: " + operatorStackToString());
        }
        
        /**
         * 将数字栈转换为字符串
         */
        private String numberStackToString() {
            if (numberStack.isEmpty()) {
                return "[]";
            }
            
            StringBuilder sb = new StringBuilder("[");
            
            // 使用临时栈反转顺序以正确显示
            ArrayListStack<Double> tempStack = new ArrayListStack<>();
            while (!numberStack.isEmpty()) {
                tempStack.push(numberStack.pop());
            }
            
            while (!tempStack.isEmpty()) {
                double value = tempStack.pop();
                sb.append(value);
                numberStack.push(value);
                
                if (!tempStack.isEmpty()) {
                    sb.append(", ");
                }
            }
            
            sb.append("]");
            return sb.toString();
        }
        
        /**
         * 将操作符栈转换为字符串
         */
        private String operatorStackToString() {
            if (operatorStack.isEmpty()) {
                return "[]";
            }
            
            StringBuilder sb = new StringBuilder("[");
            
            // 使用临时栈反转顺序以正确显示
            ArrayListStack<Character> tempStack = new ArrayListStack<>();
            while (!operatorStack.isEmpty()) {
                tempStack.push(operatorStack.pop());
            }
            
            while (!tempStack.isEmpty()) {
                char value = tempStack.pop();
                sb.append(value);
                operatorStack.push(value);
                
                if (!tempStack.isEmpty()) {
                    sb.append(", ");
                }
            }
            
            sb.append("]");
            return sb.toString();
        }
    }
    
    /**
     * 表达式验证类
     */
    public static class ExpressionValidator {
        /**
         * 验证表达式是否有效
         * @param expression 要验证的表达式
         * @return 如果有效返回true，否则返回false
         */
        public static boolean isValid(String expression) {
            if (expression == null || expression.trim().isEmpty()) {
                return false;
            }
            
            String expr = expression.trim();
            
            // 检查括号是否匹配
            if (!checkBrackets(expr)) {
                return false;
            }
            
            // 检查操作符和操作数
            return checkOperatorsAndOperands(expr);
        }
        
        /**
         * 检查括号是否匹配
         */
        private static boolean checkBrackets(String expression) {
            ArrayListStack<Character> stack = new ArrayListStack<>();
            
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false;
                    }
                }
            }
            
            return stack.isEmpty();
        }
        
        /**
         * 检查操作符和操作数是否有效
         */
        private static boolean checkOperatorsAndOperands(String expression) {
            // 简单检查：不能以操作符结束，且操作符不能相邻
            char lastChar = ' ';
            boolean expectingOperand = true;
            
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                
                if (Character.isDigit(c) || c == '.') {
                    expectingOperand = false;
                } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                    if (expectingOperand && (lastChar != '(' || (c != '+' && c != '-'))) {
                        return false;  // 操作符不能出现在表达式开始或左括号后（除非是正负号）
                    }
                    expectingOperand = true;
                } else if (c == '(') {
                    expectingOperand = true;
                } else if (c == ')') {
                    if (expectingOperand) {
                        return false;  // 右括号前不能是操作符
                    }
                    expectingOperand = false;
                } else if (!Character.isWhitespace(c)) {
                    return false;  // 无效字符
                }
                
                lastChar = c;
            }
            
            return !expectingOperand;  // 不能以操作符结束
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("基于栈的四则运算计算器");
        System.out.println("支持的操作: +, -, *, /, 括号");
        System.out.println("输入 'exit' 退出程序");
        System.out.println("输入 'log' 显示上次计算的详细过程");
        
        System.out.print("是否显示计算过程? (y/n): ");
        boolean verbose = input.nextLine().trim().toLowerCase().startsWith("y");
        
        StackCalculator calculator = new StackCalculator(verbose);
        
        while (true) {
            System.out.print("\n请输入表达式: ");
            String expression = input.nextLine().trim();
            
            if (expression.equalsIgnoreCase("exit")) {
                break;
            }
            
            if (expression.equalsIgnoreCase("log")) {
                System.out.println("\n上次计算过程:\n" + calculator.getLog());
                continue;
            }
            
            try {
                if (!ExpressionValidator.isValid(expression)) {
                    System.out.println("表达式无效，请重新输入");
                    continue;
                }
                
                double result = calculator.calculate(expression);
                System.out.println("结果: " + result);
                
                if (verbose) {
                    calculator.showStacks();
                }
                
            } catch (Exception e) {
                System.out.println("计算错误: " + e.getMessage());
            }
        }
        
        System.out.println("\n四则运算计算器的实际应用:");
        System.out.println("1. 科学计算器和电子表格");
        System.out.println("2. 编译器和解释器中的表达式求值");
        System.out.println("3. 公式编辑器和数学软件");
        System.out.println("4. 财务和统计分析工具");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 