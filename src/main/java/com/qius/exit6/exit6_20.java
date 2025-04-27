package com.qius.exit6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * exit6_20 - 基于栈的简单语法解析器
 * 这个类实现了一个简单的语法解析器，用于处理词法分析器产生的标记序列
 * 它使用栈来处理表达式解析，包括运算符优先级和括号匹配
 */
public class exit6_20 {
    
    // 定义标记类型
    public enum TokenType {
        KEYWORD, IDENTIFIER, NUMBER, STRING, OPERATOR, SEPARATOR, COMMENT, WHITESPACE, EOF
    }
    
    // 定义标记类
    public static class Token {
        private TokenType type;
        private String value;
        private int line;
        private int column;
        
        public Token(TokenType type, String value, int line, int column) {
            this.type = type;
            this.value = value;
            this.line = line;
            this.column = column;
        }
        
        public TokenType getType() { return type; }
        public String getValue() { return value; }
        public int getLine() { return line; }
        public int getColumn() { return column; }
        
        @Override
        public String toString() {
            return String.format("Token{type=%s, value='%s', line=%d, column=%d}", 
                    type, value, line, column);
        }
    }
    
    /**
     * 简化的词法分析器，用于生成标记序列
     */
    public static class SimpleLexer {
        private String input;
        private int position;
        private int line;
        private int column;
        private List<Token> tokens;
        
        // 关键字列表
        private static final String[] KEYWORDS = {
            "if", "else", "while", "for", "return", "int", "double", "string", "void"
        };
        
        // 运算符列表
        private static final String[] OPERATORS = {
            "+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">=", "&&", "||", "!"
        };
        
        // 分隔符列表
        private static final String[] SEPARATORS = {
            "(", ")", "{", "}", "[", "]", ";", ",", "."
        };
        
        public SimpleLexer(String input) {
            this.input = input;
            this.position = 0;
            this.line = 1;
            this.column = 1;
            this.tokens = new ArrayList<>();
        }
        
        public List<Token> tokenize() {
            while (position < input.length()) {
                char currentChar = input.charAt(position);
                
                // 处理空白字符
                if (Character.isWhitespace(currentChar)) {
                    if (currentChar == '\n') {
                        line++;
                        column = 1;
                    } else {
                        column++;
                    }
                    position++;
                    continue;
                }
                
                // 处理数字
                if (Character.isDigit(currentChar)) {
                    scanNumber();
                    continue;
                }
                
                // 处理标识符和关键字
                if (Character.isLetter(currentChar) || currentChar == '_') {
                    scanIdentifierOrKeyword();
                    continue;
                }
                
                // 处理运算符和分隔符
                boolean found = false;
                for (String op : OPERATORS) {
                    if (input.startsWith(op, position)) {
                        tokens.add(new Token(TokenType.OPERATOR, op, line, column));
                        position += op.length();
                        column += op.length();
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    for (String sep : SEPARATORS) {
                        if (input.startsWith(sep, position)) {
                            tokens.add(new Token(TokenType.SEPARATOR, sep, line, column));
                            position += sep.length();
                            column += sep.length();
                            found = true;
                            break;
                        }
                    }
                }
                
                // 如果找不到匹配的标记类型，跳过当前字符
                if (!found) {
                    position++;
                    column++;
                }
            }
            
            // 添加EOF标记
            tokens.add(new Token(TokenType.EOF, "", line, column));
            
            return tokens;
        }
        
        private void scanNumber() {
            int startPos = position;
            int startColumn = column;
            
            while (position < input.length() && Character.isDigit(input.charAt(position))) {
                position++;
                column++;
            }
            
            // 处理小数点
            if (position < input.length() && input.charAt(position) == '.') {
                position++;
                column++;
                
                while (position < input.length() && Character.isDigit(input.charAt(position))) {
                    position++;
                    column++;
                }
            }
            
            String number = input.substring(startPos, position);
            tokens.add(new Token(TokenType.NUMBER, number, line, startColumn));
        }
        
        private void scanIdentifierOrKeyword() {
            int startPos = position;
            int startColumn = column;
            
            while (position < input.length() && 
                   (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
                position++;
                column++;
            }
            
            String word = input.substring(startPos, position);
            
            // 检查是否为关键字
            boolean isKeyword = false;
            for (String keyword : KEYWORDS) {
                if (keyword.equals(word)) {
                    tokens.add(new Token(TokenType.KEYWORD, word, line, startColumn));
                    isKeyword = true;
                    break;
                }
            }
            
            // 如果不是关键字，则为标识符
            if (!isKeyword) {
                tokens.add(new Token(TokenType.IDENTIFIER, word, line, startColumn));
            }
        }
    }
    
    /**
     * 语法解析器 - 基于栈的实现
     * 主要用于解析简单的算术表达式
     */
    public static class Parser {
        private List<Token> tokens;
        private int position;
        
        // 运算符优先级
        private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
        static {
            PRECEDENCE.put("+", 1);
            PRECEDENCE.put("-", 1);
            PRECEDENCE.put("*", 2);
            PRECEDENCE.put("/", 2);
            PRECEDENCE.put("=", 0);
            PRECEDENCE.put("==", 0);
            PRECEDENCE.put("!=", 0);
            PRECEDENCE.put("<", 0);
            PRECEDENCE.put(">", 0);
            PRECEDENCE.put("<=", 0);
            PRECEDENCE.put(">=", 0);
        }
        
        public Parser(List<Token> tokens) {
            this.tokens = tokens;
            this.position = 0;
        }
        
        /**
         * 解析表达式，返回解析树的根节点
         */
        public ASTNode parse() {
            // 程序由多个语句组成
            ASTNode program = new ASTNode(ASTNodeType.PROGRAM);
            
            while (position < tokens.size() && tokens.get(position).getType() != TokenType.EOF) {
                program.addChild(parseStatement());
            }
            
            return program;
        }
        
        /**
         * 解析语句
         */
        private ASTNode parseStatement() {
            Token token = tokens.get(position);
            
            // 变量声明语句，如：int x = 5;
            if (token.getType() == TokenType.KEYWORD && isTypeKeyword(token.getValue())) {
                return parseDeclaration();
            }
            
            // if语句
            if (token.getType() == TokenType.KEYWORD && token.getValue().equals("if")) {
                return parseIfStatement();
            }
            
            // while语句
            if (token.getType() == TokenType.KEYWORD && token.getValue().equals("while")) {
                return parseWhileStatement();
            }
            
            // 表达式语句，如：x = 5;
            ASTNode exprStmt = new ASTNode(ASTNodeType.EXPRESSION_STATEMENT);
            exprStmt.addChild(parseExpression());
            
            // 检查语句结束的分号
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.SEPARATOR && 
                tokens.get(position).getValue().equals(";")) {
                position++; // 消耗分号
            } else {
                throw new RuntimeException("Expected ';' at line " + tokens.get(position - 1).getLine());
            }
            
            return exprStmt;
        }
        
        /**
         * 检查是否为类型关键字
         */
        private boolean isTypeKeyword(String keyword) {
            return keyword.equals("int") || keyword.equals("double") || keyword.equals("string") || keyword.equals("void");
        }
        
        /**
         * 解析变量声明
         */
        private ASTNode parseDeclaration() {
            ASTNode declarationNode = new ASTNode(ASTNodeType.VARIABLE_DECLARATION);
            
            // 添加类型节点
            Token typeToken = tokens.get(position);
            declarationNode.addChild(new ASTNode(ASTNodeType.TYPE, typeToken.getValue()));
            position++; // 消耗类型标记
            
            // 添加变量名节点
            if (position < tokens.size() && tokens.get(position).getType() == TokenType.IDENTIFIER) {
                Token identifierToken = tokens.get(position);
                declarationNode.addChild(new ASTNode(ASTNodeType.IDENTIFIER, identifierToken.getValue()));
                position++; // 消耗标识符标记
            } else {
                throw new RuntimeException("Expected identifier at line " + tokens.get(position).getLine());
            }
            
            // 处理初始化表达式
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.OPERATOR && 
                tokens.get(position).getValue().equals("=")) {
                position++; // 消耗等号
                declarationNode.addChild(parseExpression());
            }
            
            // 检查语句结束的分号
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.SEPARATOR && 
                tokens.get(position).getValue().equals(";")) {
                position++; // 消耗分号
            } else {
                throw new RuntimeException("Expected ';' at line " + tokens.get(position - 1).getLine());
            }
            
            return declarationNode;
        }
        
        /**
         * 解析if语句
         */
        private ASTNode parseIfStatement() {
            ASTNode ifNode = new ASTNode(ASTNodeType.IF_STATEMENT);
            
            position++; // 消耗'if'标记
            
            // 检查左括号
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.SEPARATOR && 
                tokens.get(position).getValue().equals("(")) {
                position++; // 消耗左括号
            } else {
                throw new RuntimeException("Expected '(' at line " + tokens.get(position).getLine());
            }
            
            // 解析条件表达式
            ifNode.addChild(parseExpression());
            
            // 检查右括号
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.SEPARATOR && 
                tokens.get(position).getValue().equals(")")) {
                position++; // 消耗右括号
            } else {
                throw new RuntimeException("Expected ')' at line " + tokens.get(position).getLine());
            }
            
            // 解析if块
            ifNode.addChild(parseStatement());
            
            // 解析可选的else块
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.KEYWORD && 
                tokens.get(position).getValue().equals("else")) {
                position++; // 消耗'else'标记
                ifNode.addChild(parseStatement());
            }
            
            return ifNode;
        }
        
        /**
         * 解析while语句
         */
        private ASTNode parseWhileStatement() {
            ASTNode whileNode = new ASTNode(ASTNodeType.WHILE_STATEMENT);
            
            position++; // 消耗'while'标记
            
            // 检查左括号
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.SEPARATOR && 
                tokens.get(position).getValue().equals("(")) {
                position++; // 消耗左括号
            } else {
                throw new RuntimeException("Expected '(' at line " + tokens.get(position).getLine());
            }
            
            // 解析条件表达式
            whileNode.addChild(parseExpression());
            
            // 检查右括号
            if (position < tokens.size() && 
                tokens.get(position).getType() == TokenType.SEPARATOR && 
                tokens.get(position).getValue().equals(")")) {
                position++; // 消耗右括号
            } else {
                throw new RuntimeException("Expected ')' at line " + tokens.get(position).getLine());
            }
            
            // 解析循环体
            whileNode.addChild(parseStatement());
            
            return whileNode;
        }
        
        /**
         * 使用栈进行表达式解析（Shunting Yard算法）
         * 此算法使用两个栈：一个操作数栈和一个运算符栈
         */
        private ASTNode parseExpression() {
            Stack<ASTNode> valueStack = new Stack<>();
            Stack<Token> operatorStack = new Stack<>();
            
            while (position < tokens.size()) {
                Token token = tokens.get(position);
                
                // 终止条件：遇到分号或右括号等
                if ((token.getType() == TokenType.SEPARATOR && 
                    (token.getValue().equals(";") || token.getValue().equals(")") || 
                     token.getValue().equals("}") || token.getValue().equals(","))) ||
                    (token.getType() == TokenType.KEYWORD && token.getValue().equals("else"))) {
                    break;
                }
                
                // 处理数字、标识符等操作数
                if (token.getType() == TokenType.NUMBER) {
                    valueStack.push(new ASTNode(ASTNodeType.NUMBER_LITERAL, token.getValue()));
                    position++;
                }
                else if (token.getType() == TokenType.IDENTIFIER) {
                    valueStack.push(new ASTNode(ASTNodeType.IDENTIFIER, token.getValue()));
                    position++;
                }
                // 处理左括号
                else if (token.getType() == TokenType.SEPARATOR && token.getValue().equals("(")) {
                    operatorStack.push(token);
                    position++;
                }
                // 处理右括号
                else if (token.getType() == TokenType.SEPARATOR && token.getValue().equals(")")) {
                    // 弹出运算符直到遇到左括号
                    while (!operatorStack.isEmpty() && 
                          !(operatorStack.peek().getType() == TokenType.SEPARATOR && 
                            operatorStack.peek().getValue().equals("("))) {
                        processOperator(valueStack, operatorStack.pop());
                    }
                    
                    // 弹出左括号
                    if (!operatorStack.isEmpty() && 
                        operatorStack.peek().getType() == TokenType.SEPARATOR && 
                        operatorStack.peek().getValue().equals("(")) {
                        operatorStack.pop();
                    } else {
                        throw new RuntimeException("Mismatched parentheses at line " + token.getLine());
                    }
                    
                    position++;
                }
                // 处理运算符
                else if (token.getType() == TokenType.OPERATOR) {
                    // 处理优先级
                    while (!operatorStack.isEmpty() && 
                          operatorStack.peek().getType() == TokenType.OPERATOR &&
                          PRECEDENCE.get(operatorStack.peek().getValue()) >= PRECEDENCE.get(token.getValue())) {
                        processOperator(valueStack, operatorStack.pop());
                    }
                    
                    // 将当前运算符压入栈
                    operatorStack.push(token);
                    position++;
                }
                else {
                    // 未知标记类型
                    throw new RuntimeException("Unexpected token: " + token + " at line " + token.getLine());
                }
            }
            
            // 处理栈中剩余的运算符
            while (!operatorStack.isEmpty()) {
                Token op = operatorStack.pop();
                if (op.getType() == TokenType.SEPARATOR && op.getValue().equals("(")) {
                    throw new RuntimeException("Mismatched parentheses");
                }
                processOperator(valueStack, op);
            }
            
            // 栈顶应该只剩下一个节点，即表达式的根节点
            if (valueStack.size() != 1) {
                throw new RuntimeException("Invalid expression");
            }
            
            return valueStack.pop();
        }
        
        /**
         * 处理运算符，创建运算节点
         */
        private void processOperator(Stack<ASTNode> valueStack, Token operator) {
            ASTNode operatorNode = new ASTNode(ASTNodeType.BINARY_EXPRESSION, operator.getValue());
            
            // 二元运算符需要两个操作数
            if (valueStack.size() < 2) {
                throw new RuntimeException("Not enough operands for operator: " + operator.getValue());
            }
            
            // 弹出两个操作数（注意顺序：先弹出的是右操作数）
            ASTNode rightOperand = valueStack.pop();
            ASTNode leftOperand = valueStack.pop();
            
            operatorNode.addChild(leftOperand);
            operatorNode.addChild(rightOperand);
            
            // 将运算节点压回栈
            valueStack.push(operatorNode);
        }
    }
    
    /**
     * AST节点类型枚举
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
        TYPE
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
     * 语法树可视化器，用于以缩进格式显示解析树
     */
    public static class ASTVisualizer {
        public static void visualize(ASTNode root) {
            System.out.println("抽象语法树 (AST):");
            System.out.println(root.toString());
        }
    }
    
    /**
     * 输出解析树的应用示例
     */
    public static void showParserApplications() {
        System.out.println("\n语法解析器的应用：");
        System.out.println("1. 编译器前端 - 将源代码转换为抽象语法树，用于后续代码生成");
        System.out.println("2. 代码分析工具 - 检测代码中的潜在问题和优化机会");
        System.out.println("3. 代码格式化工具 - 根据语法结构重新格式化代码");
        System.out.println("4. 代码转换工具 - 将一种语言的代码转换为另一种语言");
        System.out.println("5. IDE功能 - 如代码补全、重构和导航");
        System.out.println("6. 查询语言解析器 - 如SQL解析器");
        System.out.println("7. 配置文件解析 - 解析结构化配置文件");
        System.out.println("8. 领域特定语言(DSL)实现");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("基于栈的语法解析器演示");
        System.out.println("请输入代码（多行输入，以'END'结束）：");
        
        StringBuilder inputBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            inputBuilder.append(line).append("\n");
        }
        
        String input = inputBuilder.toString();
        
        try {
            // 词法分析
            SimpleLexer lexer = new SimpleLexer(input);
            List<Token> tokens = lexer.tokenize();
            
            System.out.println("\n词法分析结果：");
            for (Token token : tokens) {
                if (token.getType() != TokenType.EOF) {
                    System.out.println(token);
                }
            }
            
            // 语法分析
            Parser parser = new Parser(tokens);
            ASTNode ast = parser.parse();
            
            // 可视化AST
            ASTVisualizer.visualize(ast);
            
            // 显示语法解析器的应用
            showParserApplications();
            
        } catch (Exception e) {
            System.err.println("解析错误: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }
} 