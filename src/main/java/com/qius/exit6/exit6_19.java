package com.qius.exit6;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * 第六章习题19：基于栈的词法分析器
 * 本程序实现一个简单的词法分析器，将源代码分解为标记(Token)
 * 
 * 词法分析是编译器的第一阶段，将源代码转换为标记序列
 * 本程序使用栈来处理嵌套结构（如注释、字符串、括号等）
 * 栈在这里的应用体现了处理嵌套结构的优势
 */
public class exit6_19 {
    
    /**
     * 标记类型枚举
     */
    public enum TokenType {
        KEYWORD,        // 关键字
        IDENTIFIER,     // 标识符
        OPERATOR,       // 运算符
        SEPARATOR,      // 分隔符
        LITERAL,        // 字面量（数字、字符串等）
        COMMENT,        // 注释
        WHITESPACE,     // 空白字符
        ERROR           // 错误标记
    }
    
    /**
     * 标记类
     */
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
        
        public TokenType getType() {
            return type;
        }
        
        public String getValue() {
            return value;
        }
        
        public int getLine() {
            return line;
        }
        
        public int getColumn() {
            return column;
        }
        
        @Override
        public String toString() {
            return String.format("%-12s %-20s (行:%d, 列:%d)", 
                               type.toString(), 
                               value.length() > 15 ? value.substring(0, 12) + "..." : value,
                               line,
                               column);
        }
    }
    
    /**
     * 词法分析器类
     */
    public static class Lexer {
        private String sourceCode;
        private List<Token> tokens;
        private int pos;
        private int line;
        private int column;
        
        // 关键字列表
        private static final String[] KEYWORDS = {
            "if", "else", "while", "for", "switch", "case", "break", "continue",
            "return", "int", "float", "double", "char", "void", "boolean", "string",
            "class", "public", "private", "protected", "static", "final", "new"
        };
        
        // 单字符运算符
        private static final String SIMPLE_OPERATORS = "+-*/=<>!&|^%~";
        
        // 分隔符
        private static final String SEPARATORS = "(){}[];,.:";
        
        /**
         * 构造函数
         * @param sourceCode 源代码
         */
        public Lexer(String sourceCode) {
            this.sourceCode = sourceCode;
            this.tokens = new ArrayList<>();
            this.pos = 0;
            this.line = 1;
            this.column = 1;
        }
        
        /**
         * 执行词法分析
         * @return 标记列表
         */
        public List<Token> analyze() {
            tokens.clear();
            pos = 0;
            line = 1;
            column = 1;
            
            while (pos < sourceCode.length()) {
                char c = sourceCode.charAt(pos);
                
                if (Character.isWhitespace(c)) {
                    scanWhitespace();
                } else if (c == '/' && pos + 1 < sourceCode.length()) {
                    // 可能是注释（// 或 /*）或除法运算符
                    if (sourceCode.charAt(pos + 1) == '/' || sourceCode.charAt(pos + 1) == '*') {
                        scanComment();
                    } else {
                        scanOperator();
                    }
                } else if (Character.isLetter(c) || c == '_') {
                    scanIdentifierOrKeyword();
                } else if (Character.isDigit(c)) {
                    scanNumber();
                } else if (c == '"' || c == '\'') {
                    scanStringOrChar();
                } else if (SIMPLE_OPERATORS.indexOf(c) != -1) {
                    scanOperator();
                } else if (SEPARATORS.indexOf(c) != -1) {
                    scanSeparator();
                } else {
                    // 未知字符，报错
                    addToken(TokenType.ERROR, Character.toString(c));
                    advance();
                }
            }
            
            return tokens;
        }
        
        /**
         * 扫描空白字符
         */
        private void scanWhitespace() {
            int startPos = pos;
            int startColumn = column;
            
            while (pos < sourceCode.length() && Character.isWhitespace(sourceCode.charAt(pos))) {
                if (sourceCode.charAt(pos) == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
                pos++;
            }
            
            String value = sourceCode.substring(startPos, pos);
            addToken(TokenType.WHITESPACE, value, line, startColumn);
        }
        
        /**
         * 扫描注释
         */
        private void scanComment() {
            int startPos = pos;
            int startLine = line;
            int startColumn = column;
            
            // 跳过 / 字符
            advance();
            
            if (sourceCode.charAt(pos) == '/') {
                // 单行注释
                advance();  // 跳过第二个 /
                
                // 读取直到行尾
                while (pos < sourceCode.length() && sourceCode.charAt(pos) != '\n') {
                    advance();
                }
                
            } else if (sourceCode.charAt(pos) == '*') {
                // 多行注释
                advance();  // 跳过 *
                
                // 使用栈记录嵌套注释
                ArrayListStack<Integer> commentStack = new ArrayListStack<>();
                commentStack.push(1);
                
                while (pos < sourceCode.length() && !commentStack.isEmpty()) {
                    if (pos + 1 < sourceCode.length() && sourceCode.charAt(pos) == '/' && sourceCode.charAt(pos + 1) == '*') {
                        // 嵌套注释开始
                        advance();
                        advance();
                        commentStack.push(1);
                    } else if (pos + 1 < sourceCode.length() && sourceCode.charAt(pos) == '*' && sourceCode.charAt(pos + 1) == '/') {
                        // 注释结束
                        advance();
                        advance();
                        commentStack.pop();
                    } else {
                        if (sourceCode.charAt(pos) == '\n') {
                            line++;
                            column = 1;
                        }
                        advance();
                    }
                }
                
                if (!commentStack.isEmpty()) {
                    // 未闭合的注释
                    addToken(TokenType.ERROR, "Unterminated comment", startLine, startColumn);
                    return;
                }
            }
            
            String commentText = sourceCode.substring(startPos, pos);
            addToken(TokenType.COMMENT, commentText, startLine, startColumn);
        }
        
        /**
         * 扫描标识符或关键字
         */
        private void scanIdentifierOrKeyword() {
            int startPos = pos;
            int startColumn = column;
            
            while (pos < sourceCode.length() && 
                  (Character.isLetterOrDigit(sourceCode.charAt(pos)) || sourceCode.charAt(pos) == '_')) {
                advance();
            }
            
            String value = sourceCode.substring(startPos, pos);
            
            // 检查是否是关键字
            boolean isKeyword = false;
            for (String keyword : KEYWORDS) {
                if (keyword.equals(value)) {
                    addToken(TokenType.KEYWORD, value, line, startColumn);
                    isKeyword = true;
                    break;
                }
            }
            
            if (!isKeyword) {
                addToken(TokenType.IDENTIFIER, value, line, startColumn);
            }
        }
        
        /**
         * 扫描数字字面量
         */
        private void scanNumber() {
            int startPos = pos;
            int startColumn = column;
            
            // 扫描整数部分
            while (pos < sourceCode.length() && Character.isDigit(sourceCode.charAt(pos))) {
                advance();
            }
            
            // 处理小数点
            if (pos < sourceCode.length() && sourceCode.charAt(pos) == '.') {
                advance();  // 跳过小数点
                
                // 扫描小数部分
                while (pos < sourceCode.length() && Character.isDigit(sourceCode.charAt(pos))) {
                    advance();
                }
            }
            
            // 处理科学计数法（如 1.23e+10）
            if (pos < sourceCode.length() && (sourceCode.charAt(pos) == 'e' || sourceCode.charAt(pos) == 'E')) {
                advance();  // 跳过 e/E
                
                // 处理可能的符号
                if (pos < sourceCode.length() && (sourceCode.charAt(pos) == '+' || sourceCode.charAt(pos) == '-')) {
                    advance();
                }
                
                // 扫描指数部分
                if (pos < sourceCode.length() && Character.isDigit(sourceCode.charAt(pos))) {
                    while (pos < sourceCode.length() && Character.isDigit(sourceCode.charAt(pos))) {
                        advance();
                    }
                } else {
                    // 指数部分必须有数字
                    addToken(TokenType.ERROR, sourceCode.substring(startPos, pos) + "?", line, startColumn);
                    return;
                }
            }
            
            String value = sourceCode.substring(startPos, pos);
            addToken(TokenType.LITERAL, value, line, startColumn);
        }
        
        /**
         * 扫描字符串或字符字面量
         */
        private void scanStringOrChar() {
            int startPos = pos;
            int startLine = line;
            int startColumn = column;
            
            char quoteChar = sourceCode.charAt(pos);
            advance();  // 跳过引号
            
            ArrayListStack<Character> stringStack = new ArrayListStack<>();
            stringStack.push(quoteChar);
            
            while (pos < sourceCode.length() && !stringStack.isEmpty()) {
                char c = sourceCode.charAt(pos);
                
                if (c == '\\' && pos + 1 < sourceCode.length()) {
                    // 转义字符
                    advance();  // 跳过反斜杠
                    advance();  // 跳过转义的字符
                } else if (c == quoteChar) {
                    // 引号结束
                    stringStack.pop();
                    advance();
                    if (stringStack.isEmpty()) {
                        break;
                    }
                } else {
                    if (c == '\n') {
                        // 字符串不能跨行（除非使用转义）
                        addToken(TokenType.ERROR, "Unterminated string", startLine, startColumn);
                        return;
                    }
                    advance();
                }
            }
            
            if (!stringStack.isEmpty()) {
                // 未闭合的字符串
                addToken(TokenType.ERROR, "Unterminated string", startLine, startColumn);
                return;
            }
            
            String value = sourceCode.substring(startPos, pos);
            addToken(TokenType.LITERAL, value, startLine, startColumn);
        }
        
        /**
         * 扫描运算符
         */
        private void scanOperator() {
            int startPos = pos;
            int startColumn = column;
            
            char c = sourceCode.charAt(pos);
            advance();
            
            // 处理复合运算符（如 +=, ==, !=, >=, <= 等）
            if (pos < sourceCode.length()) {
                char nextChar = sourceCode.charAt(pos);
                String op = c + "" + nextChar;
                
                if ((c == '+' && nextChar == '+') || (c == '-' && nextChar == '-') ||   // ++ --
                    (c == '=' && nextChar == '=') || (c == '!' && nextChar == '=') ||   // == !=
                    (c == '<' && nextChar == '=') || (c == '>' && nextChar == '=') ||   // <= >=
                    (c == '&' && nextChar == '&') || (c == '|' && nextChar == '|') ||   // && ||
                    (c == '+' && nextChar == '=') || (c == '-' && nextChar == '=') ||   // += -=
                    (c == '*' && nextChar == '=') || (c == '/' && nextChar == '=') ||   // *= /=
                    (c == '%' && nextChar == '=') || (c == '&' && nextChar == '=') ||   // %= &=
                    (c == '|' && nextChar == '=') || (c == '^' && nextChar == '=') ||   // |= ^=
                    (c == '<' && nextChar == '<') || (c == '>' && nextChar == '>')) {    // << >>
                    
                    advance();  // 接受复合运算符的第二个字符
                    
                    // 处理三字符运算符 (如 >>= 和 <<=)
                    if (pos < sourceCode.length()) {
                        if ((op.equals(">>") || op.equals("<<")) && sourceCode.charAt(pos) == '=') {
                            advance();
                        }
                    }
                }
            }
            
            String value = sourceCode.substring(startPos, pos);
            addToken(TokenType.OPERATOR, value, line, startColumn);
        }
        
        /**
         * 扫描分隔符
         */
        private void scanSeparator() {
            int startColumn = column;
            char c = sourceCode.charAt(pos);
            
            addToken(TokenType.SEPARATOR, Character.toString(c));
            advance();
        }
        
        /**
         * 前进一个字符
         */
        private void advance() {
            if (pos < sourceCode.length()) {
                if (sourceCode.charAt(pos) == '\n') {
                    line++;
                    column = 1;
                } else {
                    column++;
                }
                pos++;
            }
        }
        
        /**
         * 添加标记到结果列表
         */
        private void addToken(TokenType type, String value) {
            addToken(type, value, line, column - value.length());
        }
        
        /**
         * 添加标记到结果列表（指定行和列）
         */
        private void addToken(TokenType type, String value, int line, int column) {
            tokens.add(new Token(type, value, line, column));
        }
    }
    
    /**
     * 对括号进行匹配检查
     * 演示如何使用栈验证代码中的括号平衡
     */
    public static class BracketChecker {
        private List<Token> tokens;
        
        public BracketChecker(List<Token> tokens) {
            this.tokens = tokens;
        }
        
        /**
         * 检查括号是否匹配
         * @return 如果所有括号都匹配返回true，否则返回false
         */
        public boolean checkBrackets() {
            ArrayListStack<Token> stack = new ArrayListStack<>();
            
            for (Token token : tokens) {
                if (token.getType() == TokenType.SEPARATOR) {
                    String value = token.getValue();
                    
                    if (value.equals("(") || value.equals("[") || value.equals("{")) {
                        // 左括号入栈
                        stack.push(token);
                    } else if (value.equals(")") || value.equals("]") || value.equals("}")) {
                        // 右括号，检查是否匹配
                        if (stack.isEmpty()) {
                            System.out.println("错误: 在第 " + token.getLine() + " 行第 " + token.getColumn() + 
                                             " 列发现未匹配的右括号 '" + value + "'");
                            return false;
                        }
                        
                        Token open = stack.pop();
                        String openValue = open.getValue();
                        
                        if ((value.equals(")") && !openValue.equals("(")) ||
                            (value.equals("]") && !openValue.equals("[")) ||
                            (value.equals("}") && !openValue.equals("{"))) {
                            System.out.println("错误: 在第 " + token.getLine() + " 行第 " + token.getColumn() + 
                                             " 列发现括号不匹配: 期望 '" + getMatchingBracket(openValue) + 
                                             "', 但找到 '" + value + "'");
                            return false;
                        }
                    }
                }
            }
            
            // 检查是否有未闭合的左括号
            if (!stack.isEmpty()) {
                Token token = stack.pop();
                System.out.println("错误: 在第 " + token.getLine() + " 行第 " + token.getColumn() + 
                                 " 列发现未闭合的左括号 '" + token.getValue() + "'");
                return false;
            }
            
            return true;
        }
        
        /**
         * 获取匹配的右括号
         */
        private String getMatchingBracket(String bracket) {
            switch (bracket) {
                case "(": return ")";
                case "[": return "]";
                case "{": return "}";
                default: return "";
            }
        }
    }
    
    /**
     * 使用栈识别代码块
     * 演示如何使用栈来划分代码块的层次结构
     */
    public static class BlockAnalyzer {
        private List<Token> tokens;
        
        public BlockAnalyzer(List<Token> tokens) {
            this.tokens = tokens;
        }
        
        /**
         * 分析代码块结构
         */
        public void analyzeBlocks() {
            ArrayListStack<Integer> blockStack = new ArrayListStack<>();
            int blockLevel = 0;
            
            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                
                if (token.getType() == TokenType.SEPARATOR && token.getValue().equals("{")) {
                    blockLevel++;
                    blockStack.push(blockLevel);
                    
                    // 找到代码块开始的关键词
                    String blockType = findBlockType(i);
                    String indent = getIndentation(blockLevel - 1);
                    System.out.println(indent + "发现 " + blockType + " 代码块 (级别 " + blockLevel + ") 在第 " + 
                                     token.getLine() + " 行第 " + token.getColumn() + " 列");
                    
                } else if (token.getType() == TokenType.SEPARATOR && token.getValue().equals("}")) {
                    if (!blockStack.isEmpty()) {
                        int level = blockStack.pop();
                        String indent = getIndentation(level - 1);
                        System.out.println(indent + "代码块结束 (级别 " + level + ") 在第 " + 
                                         token.getLine() + " 行第 " + token.getColumn() + " 列");
                    }
                }
            }
            
            // 检查是否有未闭合的代码块
            if (!blockStack.isEmpty()) {
                System.out.println("警告: 存在 " + blockStack.size() + " 个未闭合的代码块");
            }
        }
        
        /**
         * 查找代码块的类型（如if、for、while、function、class等）
         */
        private String findBlockType(int braceIndex) {
            // 向前查找关键字
            for (int i = braceIndex - 1; i >= 0; i--) {
                Token token = tokens.get(i);
                
                if (token.getType() == TokenType.KEYWORD) {
                    String value = token.getValue();
                    if (value.equals("if") || value.equals("else") || 
                        value.equals("for") || value.equals("while") || 
                        value.equals("switch") || value.equals("case") || 
                        value.equals("class")) {
                        return value;
                    }
                } else if (token.getType() == TokenType.SEPARATOR && token.getValue().equals(";")) {
                    // 遇到语句结束符，停止向前查找
                    break;
                }
            }
            
            return "未知类型";
        }
        
        /**
         * 生成缩进字符串
         */
        private String getIndentation(int level) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < level; i++) {
                sb.append("  ");
            }
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("基于栈的词法分析器");
        System.out.println("请输入一段代码（输入 'EOF' 表示结束）：");
        
        StringBuilder sourceBuilder = new StringBuilder();
        String line;
        while (!(line = input.nextLine()).equals("EOF")) {
            sourceBuilder.append(line).append("\n");
        }
        
        String sourceCode = sourceBuilder.toString();
        
        // 执行词法分析
        Lexer lexer = new Lexer(sourceCode);
        List<Token> tokens = lexer.analyze();
        
        System.out.println("\n词法分析结果：");
        System.out.println("--------------------------------------");
        System.out.printf("%-12s %-20s %-8s\n", "类型", "值", "位置");
        System.out.println("--------------------------------------");
        
        int tokenCount = 0;
        for (Token token : tokens) {
            if (token.getType() != TokenType.WHITESPACE) {  // 不显示空白标记
                System.out.println(token);
                tokenCount++;
            }
        }
        
        System.out.println("--------------------------------------");
        System.out.println("共找到 " + tokenCount + " 个非空白标记");
        
        // 括号匹配检查
        System.out.println("\n括号匹配检查：");
        BracketChecker bracketChecker = new BracketChecker(tokens);
        boolean bracketsMatch = bracketChecker.checkBrackets();
        System.out.println(bracketsMatch ? "所有括号匹配正确" : "括号匹配有误");
        
        // 代码块分析
        System.out.println("\n代码块结构分析：");
        BlockAnalyzer blockAnalyzer = new BlockAnalyzer(tokens);
        blockAnalyzer.analyzeBlocks();
        
        System.out.println("\n词法分析器的应用：");
        System.out.println("1. 编译器和解释器的前端");
        System.out.println("2. 语法高亮和代码编辑器");
        System.out.println("3. 静态代码分析和检查");
        System.out.println("4. 代码重构和转换工具");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 