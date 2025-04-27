package com.qius.exit6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * exit6_21 - 基于栈的代码优化器
 * 本程序演示如何使用栈实现简单的代码优化技术
 * 这是语法分析的后续步骤，对生成的抽象语法树进行优化处理
 */
public class exit6_21 {
    
    /**
     * 从exit6_20导入必要的AST相关类定义
     */
    public enum ASTNodeType {
        PROGRAM,
        VARIABLE_DECLARATION,
        IF_STATEMENT,
        WHILE_STATEMENT,
        EXPRESSION_STATEMENT,
        BINARY_EXPRESSION,
        IDENTIFIER,
        NUMBER_LITERAL,
        TYPE,
        CONSTANT_FOLDING_RESULT  // 新增：常量折叠结果节点类型
    }
    
    /**
     * 抽象语法树节点
     */
    public static class ASTNode {
        private ASTNodeType type;
        private String value;
        private List<ASTNode> children;
        
        public ASTNode(ASTNodeType type) {
            this.type = type;
            this.children = new ArrayList<>();
        }
        
        public ASTNode(ASTNodeType type, String value) {
            this.type = type;
            this.value = value;
            this.children = new ArrayList<>();
        }
        
        public void addChild(ASTNode child) {
            children.add(child);
        }
        
        public ASTNode replaceChild(int index, ASTNode newChild) {
            if (index >= 0 && index < children.size()) {
                return children.set(index, newChild);
            }
            return null;
        }
        
        public void removeChild(int index) {
            if (index >= 0 && index < children.size()) {
                children.remove(index);
            }
        }
        
        public ASTNodeType getType() { return type; }
        public String getValue() { return value; }
        public List<ASTNode> getChildren() { return children; }
        
        /**
         * 生成AST的打印表示，用于调试
         */
        public String toString() {
            return toString(0);
        }
        
        private String toString(int depth) {
            StringBuilder sb = new StringBuilder();
            String indent = "  ".repeat(depth);
            
            sb.append(indent).append(type);
            if (value != null && !value.isEmpty()) {
                sb.append(": ").append(value);
            }
            sb.append("\n");
            
            for (ASTNode child : children) {
                sb.append(child.toString(depth + 1));
            }
            
            return sb.toString();
        }
    }
    
    /**
     * 代码优化器 - 基于栈实现
     * 实现多种优化技术：常量折叠、死代码消除、表达式简化等
     */
    public static class Optimizer {
        private ASTNode ast;  // 抽象语法树
        private boolean modified; // 标记是否进行了修改
        
        public Optimizer(ASTNode ast) {
            this.ast = ast;
            this.modified = false;
        }
        
        /**
         * 执行所有可用的优化
         * @return 优化后的AST
         */
        public ASTNode optimize() {
            do {
                modified = false;
                
                // 应用各种优化技术
                ast = constantFolding(ast);
                ast = deadCodeElimination(ast);
                ast = algebraicSimplification(ast);
                
            } while (modified); // 如果有修改，继续优化
            
            return ast;
        }
        
        /**
         * 常量折叠 - 在编译时计算常量表达式
         * 例如：将 2 + 3 * 4 替换为 14
         */
        private ASTNode constantFolding(ASTNode node) {
            if (node == null) return null;
            
            // 首先递归处理所有子节点
            for (int i = 0; i < node.getChildren().size(); i++) {
                ASTNode child = node.getChildren().get(i);
                ASTNode optimizedChild = constantFolding(child);
                if (optimizedChild != child) {
                    node.replaceChild(i, optimizedChild);
                    modified = true;
                }
            }
            
            // 对二元表达式进行常量折叠
            if (node.getType() == ASTNodeType.BINARY_EXPRESSION) {
                String operator = node.getValue();
                List<ASTNode> children = node.getChildren();
                
                if (children.size() == 2 &&
                    children.get(0).getType() == ASTNodeType.NUMBER_LITERAL &&
                    children.get(1).getType() == ASTNodeType.NUMBER_LITERAL) {
                    
                    double leftValue = Double.parseDouble(children.get(0).getValue());
                    double rightValue = Double.parseDouble(children.get(1).getValue());
                    double result = 0;
                    
                    // 执行操作
                    switch (operator) {
                        case "+":
                            result = leftValue + rightValue;
                            break;
                        case "-":
                            result = leftValue - rightValue;
                            break;
                        case "*":
                            result = leftValue * rightValue;
                            break;
                        case "/":
                            if (rightValue == 0) {
                                // 除以零，不优化
                                return node;
                            }
                            result = leftValue / rightValue;
                            break;
                        default:
                            // 不支持的操作符，不优化
                            return node;
                    }
                    
                    // 创建一个新的常量节点
                    modified = true;
                    return new ASTNode(ASTNodeType.NUMBER_LITERAL, String.valueOf(result));
                }
            }
            
            return node;
        }
        
        /**
         * 死代码消除 - 移除永远不会执行的代码
         * 例如：if (false) { ... } 或 while (false) { ... }
         */
        private ASTNode deadCodeElimination(ASTNode node) {
            if (node == null) return null;
            
            // 首先递归处理所有子节点
            for (int i = 0; i < node.getChildren().size(); i++) {
                ASTNode child = node.getChildren().get(i);
                ASTNode optimizedChild = deadCodeElimination(child);
                if (optimizedChild != child) {
                    node.replaceChild(i, optimizedChild);
                    modified = true;
                }
            }
            
            // 处理 if 语句
            if (node.getType() == ASTNodeType.IF_STATEMENT) {
                List<ASTNode> children = node.getChildren();
                if (children.size() >= 2) {  // 至少有条件和if块
                    ASTNode condition = children.get(0);
                    
                    if (condition.getType() == ASTNodeType.NUMBER_LITERAL) {
                        double value = Double.parseDouble(condition.getValue());
                        
                        if (value != 0) {  // 条件永远为真
                            // 返回 if 块
                            modified = true;
                            return children.get(1);
                        } else {  // 条件永远为假
                            // 如果有 else 块，返回 else 块，否则返回空程序块
                            if (children.size() >= 3) {
                                modified = true;
                                return children.get(2);
                            } else {
                                // 创建空语句
                                modified = true;
                                return new ASTNode(ASTNodeType.PROGRAM);
                            }
                        }
                    }
                }
            }
            
            // 处理 while 语句
            if (node.getType() == ASTNodeType.WHILE_STATEMENT) {
                List<ASTNode> children = node.getChildren();
                if (children.size() >= 2) {  // 条件和循环体
                    ASTNode condition = children.get(0);
                    
                    if (condition.getType() == ASTNodeType.NUMBER_LITERAL) {
                        double value = Double.parseDouble(condition.getValue());
                        
                        if (value == 0) {  // 条件永远为假
                            // 循环永远不会执行，返回空程序块
                            modified = true;
                            return new ASTNode(ASTNodeType.PROGRAM);
                        }
                        // 注意：如果条件永远为真，这是一个无限循环，保持不变
                    }
                }
            }
            
            return node;
        }
        
        /**
         * 代数简化 - 应用代数法则简化表达式
         * 例如：x + 0 = x, x * 1 = x, x * 0 = 0 等
         */
        private ASTNode algebraicSimplification(ASTNode node) {
            if (node == null) return null;
            
            // 首先递归处理所有子节点
            for (int i = 0; i < node.getChildren().size(); i++) {
                ASTNode child = node.getChildren().get(i);
                ASTNode optimizedChild = algebraicSimplification(child);
                if (optimizedChild != child) {
                    node.replaceChild(i, optimizedChild);
                    modified = true;
                }
            }
            
            // 对二元表达式进行简化
            if (node.getType() == ASTNodeType.BINARY_EXPRESSION) {
                String operator = node.getValue();
                List<ASTNode> children = node.getChildren();
                
                if (children.size() == 2) {
                    ASTNode left = children.get(0);
                    ASTNode right = children.get(1);
                    
                    // x + 0 = x
                    if (operator.equals("+") && 
                        right.getType() == ASTNodeType.NUMBER_LITERAL &&
                        right.getValue().equals("0")) {
                        modified = true;
                        return left;
                    }
                    
                    // 0 + x = x
                    if (operator.equals("+") && 
                        left.getType() == ASTNodeType.NUMBER_LITERAL &&
                        left.getValue().equals("0")) {
                        modified = true;
                        return right;
                    }
                    
                    // x - 0 = x
                    if (operator.equals("-") && 
                        right.getType() == ASTNodeType.NUMBER_LITERAL &&
                        right.getValue().equals("0")) {
                        modified = true;
                        return left;
                    }
                    
                    // x * 1 = x
                    if (operator.equals("*") && 
                        right.getType() == ASTNodeType.NUMBER_LITERAL &&
                        right.getValue().equals("1")) {
                        modified = true;
                        return left;
                    }
                    
                    // 1 * x = x
                    if (operator.equals("*") && 
                        left.getType() == ASTNodeType.NUMBER_LITERAL &&
                        left.getValue().equals("1")) {
                        modified = true;
                        return right;
                    }
                    
                    // x * 0 = 0 或 0 * x = 0
                    if (operator.equals("*") && 
                        ((right.getType() == ASTNodeType.NUMBER_LITERAL && right.getValue().equals("0")) ||
                         (left.getType() == ASTNodeType.NUMBER_LITERAL && left.getValue().equals("0")))) {
                        modified = true;
                        return new ASTNode(ASTNodeType.NUMBER_LITERAL, "0");
                    }
                    
                    // x / 1 = x
                    if (operator.equals("/") && 
                        right.getType() == ASTNodeType.NUMBER_LITERAL &&
                        right.getValue().equals("1")) {
                        modified = true;
                        return left;
                    }
                    
                    // 0 / x = 0 (如果 x != 0)
                    if (operator.equals("/") && 
                        left.getType() == ASTNodeType.NUMBER_LITERAL &&
                        left.getValue().equals("0")) {
                        modified = true;
                        return new ASTNode(ASTNodeType.NUMBER_LITERAL, "0");
                    }
                }
            }
            
            return node;
        }
        
        /**
         * 获取优化统计信息
         */
        public String getOptimizationStats() {
            return "代码优化完成。";
        }
    }
    
    /**
     * 将表达式字符串转换为AST示例（简化版解析器）
     */
    public static ASTNode parseExpressionToAST(String expression) {
        // 这是一个非常简化的解析器，仅用于演示
        // 实际应用中应当使用exit6_20.java中的完整解析器
        
        // 创建一个Program根节点
        ASTNode program = new ASTNode(ASTNodeType.PROGRAM);
        
        // 对于简单表达式 "2 + 3 * 4"，手动构建AST
        if (expression.equals("2 + 3 * 4")) {
            // 创建表达式语句节点
            ASTNode exprStmt = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            
            // 创建加法表达式 2 + (3 * 4)
            ASTNode addExpr = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "+");
            
            // 左操作数: 2
            ASTNode leftNum = new ASTNode(ASTNodeType.NUMBER_LITERAL, "2");
            
            // 右操作数: 3 * 4
            ASTNode mulExpr = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "*");
            ASTNode threeNode = new ASTNode(ASTNodeType.NUMBER_LITERAL, "3");
            ASTNode fourNode = new ASTNode(ASTNodeType.NUMBER_LITERAL, "4");
            mulExpr.addChild(threeNode);
            mulExpr.addChild(fourNode);
            
            // 组装加法表达式
            addExpr.addChild(leftNum);
            addExpr.addChild(mulExpr);
            
            // 添加到表达式语句
            exprStmt.addChild(addExpr);
            
            // 添加到程序根节点
            program.addChild(exprStmt);
        }
        // 对于简单if语句 "if (0) { x = 10; } else { x = 20; }"，手动构建AST
        else if (expression.equals("if (0) { x = 10; } else { x = 20; }")) {
            // 创建if语句节点
            ASTNode ifStmt = new ASTNode(ASTNodeType.IF_STATEMENT);
            
            // 条件: 0 (假)
            ASTNode condition = new ASTNode(ASTNodeType.NUMBER_LITERAL, "0");
            
            // if块: x = 10;
            ASTNode ifBlock = new ASTNode(ASTNodeType.PROGRAM);
            ASTNode assignX10 = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            ASTNode assignExpr1 = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "=");
            assignExpr1.addChild(new ASTNode(ASTNodeType.IDENTIFIER, "x"));
            assignExpr1.addChild(new ASTNode(ASTNodeType.NUMBER_LITERAL, "10"));
            assignX10.addChild(assignExpr1);
            ifBlock.addChild(assignX10);
            
            // else块: x = 20;
            ASTNode elseBlock = new ASTNode(ASTNodeType.PROGRAM);
            ASTNode assignX20 = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            ASTNode assignExpr2 = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "=");
            assignExpr2.addChild(new ASTNode(ASTNodeType.IDENTIFIER, "x"));
            assignExpr2.addChild(new ASTNode(ASTNodeType.NUMBER_LITERAL, "20"));
            assignX20.addChild(assignExpr2);
            elseBlock.addChild(assignX20);
            
            // 组装if语句
            ifStmt.addChild(condition);
            ifStmt.addChild(ifBlock);
            ifStmt.addChild(elseBlock);
            
            // 添加到程序根节点
            program.addChild(ifStmt);
        }
        // 对于简单while语句 "while (0) { x = x + 1; }"，手动构建AST
        else if (expression.equals("while (0) { x = x + 1; }")) {
            // 创建while语句节点
            ASTNode whileStmt = new ASTNode(ASTNodeType.WHILE_STATEMENT);
            
            // 条件: 0 (假)
            ASTNode condition = new ASTNode(ASTNodeType.NUMBER_LITERAL, "0");
            
            // 循环体: x = x + 1;
            ASTNode loopBlock = new ASTNode(ASTNodeType.PROGRAM);
            ASTNode assignXplus1 = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            ASTNode assignExpr = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "=");
            assignExpr.addChild(new ASTNode(ASTNodeType.IDENTIFIER, "x"));
            
            ASTNode addExpr = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "+");
            addExpr.addChild(new ASTNode(ASTNodeType.IDENTIFIER, "x"));
            addExpr.addChild(new ASTNode(ASTNodeType.NUMBER_LITERAL, "1"));
            
            assignExpr.addChild(addExpr);
            assignXplus1.addChild(assignExpr);
            loopBlock.addChild(assignXplus1);
            
            // 组装while语句
            whileStmt.addChild(condition);
            whileStmt.addChild(loopBlock);
            
            // 添加到程序根节点
            program.addChild(whileStmt);
        }
        // 对于代数简化示例 "x + 0"，手动构建AST
        else if (expression.equals("x + 0")) {
            // 创建表达式语句节点
            ASTNode exprStmt = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            
            // 创建加法表达式 x + 0
            ASTNode addExpr = new ASTNode(ASTNodeType.BINARY_EXPRESSION, "+");
            
            // 左操作数: x
            ASTNode xNode = new ASTNode(ASTNodeType.IDENTIFIER, "x");
            
            // 右操作数: 0
            ASTNode zeroNode = new ASTNode(ASTNodeType.NUMBER_LITERAL, "0");
            
            // 组装加法表达式
            addExpr.addChild(xNode);
            addExpr.addChild(zeroNode);
            
            // 添加到表达式语句
            exprStmt.addChild(addExpr);
            
            // 添加到程序根节点
            program.addChild(exprStmt);
        }
        else {
            // 对于其他表达式，创建一个简单的错误节点
            ASTNode errorNode = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            errorNode.addChild(new ASTNode(ASTNodeType.IDENTIFIER, "不支持解析的表达式: " + expression));
            program.addChild(errorNode);
        }
        
        return program;
    }
    
    /**
     * 代码优化器演示类
     */
    public static class OptimizationDemo {
        /**
         * 运行演示
         */
        public void run() {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("基于栈的代码优化器演示");
            System.out.println("本程序展示了如何对抽象语法树进行优化");
            
            while (true) {
                System.out.println("\n请选择要演示的优化类型：");
                System.out.println("1. 常量折叠 (例如: 2 + 3 * 4)");
                System.out.println("2. 死代码消除 (例如: if (0) { x = 10; } else { x = 20; })");
                System.out.println("3. 代数简化 (例如: x + 0)");
                System.out.println("0. 退出");
                
                System.out.print("\n请选择 (0-3): ");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("无效输入，请输入数字。");
                    continue;
                }
                
                if (choice == 0) {
                    break;
                }
                
                String expression = "";
                switch (choice) {
                    case 1:
                        expression = "2 + 3 * 4";
                        break;
                    case 2:
                        expression = "if (0) { x = 10; } else { x = 20; }";
                        break;
                    case 3:
                        expression = "x + 0";
                        break;
                    default:
                        System.out.println("无效选择，请重试。");
                        continue;
                }
                
                // 解析表达式为AST
                ASTNode ast = parseExpressionToAST(expression);
                
                // 显示原始AST
                System.out.println("\n原始代码: " + expression);
                System.out.println("\n原始抽象语法树 (AST):");
                System.out.println(ast.toString());
                
                // 创建优化器并执行优化
                Optimizer optimizer = new Optimizer(ast);
                ASTNode optimizedAST = optimizer.optimize();
                
                // 显示优化后的AST
                System.out.println("\n优化后的抽象语法树 (AST):");
                System.out.println(optimizedAST.toString());
                
                // 显示优化统计
                System.out.println("\n" + optimizer.getOptimizationStats());
            }
            
            System.out.println("\n代码优化器的应用:");
            System.out.println("1. 编译器后端优化");
            System.out.println("2. 即时编译器（JIT）");
            System.out.println("3. 代码分析工具");
            System.out.println("4. 代码重构工具");
            System.out.println("5. 动态语言解释器");
            
            System.out.println("\n程序结束");
            scanner.close();
        }
    }
    
    public static void main(String[] args) {
        OptimizationDemo demo = new OptimizationDemo();
        demo.run();
    }
} 