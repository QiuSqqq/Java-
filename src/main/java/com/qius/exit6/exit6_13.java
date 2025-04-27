package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题13：表达式平衡检查器
 * 使用栈检查各种表达式的平衡性，包括括号匹配和HTML标签匹配
 * 
 * 这个程序实现了两种常见的平衡性检查应用：
 * 1. 多种括号的匹配检查：()、[]、{}
 * 2. HTML标签的匹配检查：<tag>...</tag>
 * 
 * 平衡检查是栈的经典应用，通过入栈和出栈操作可以很容易地识别
 * 是否所有的开始符号都有对应的结束符号，且它们的嵌套顺序正确。
 */
public class exit6_13 {
    
    /**
     * 检查括号表达式是否平衡
     * @param expression 包含括号的表达式
     * @return 如果括号平衡返回true，否则返回false
     */
    public static boolean checkBrackets(String expression) {
        if (expression == null || expression.isEmpty()) {
            return true;  // 空表达式视为平衡
        }
        
        ArrayListStack<Character> stack = new ArrayListStack<>();
        
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            
            // 如果是开括号，入栈
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            // 如果是闭括号，检查与栈顶括号是否匹配
            else if (ch == ')' || ch == ']' || ch == '}') {
                // 如果栈为空，说明没有对应的开括号
                if (stack.isEmpty()) {
                    return false;
                }
                
                // 获取栈顶的开括号
                char top = stack.pop();
                
                // 检查括号是否匹配
                if ((ch == ')' && top != '(') ||
                    (ch == ']' && top != '[') ||
                    (ch == '}' && top != '{')) {
                    return false;
                }
            }
        }
        
        // 如果栈为空，说明所有括号都已匹配
        // 如果栈不为空，说明有未匹配的开括号
        return stack.isEmpty();
    }
    
    /**
     * 检查HTML标签是否平衡
     * @param htmlContent HTML内容
     * @return 如果HTML标签平衡返回true，否则返回false
     */
    public static boolean checkHtmlTags(String htmlContent) {
        if (htmlContent == null || htmlContent.isEmpty()) {
            return true;  // 空内容视为平衡
        }
        
        ArrayListStack<String> stack = new ArrayListStack<>();
        int i = 0;
        
        while (i < htmlContent.length()) {
            // 寻找开始标签 <
            int openBracket = htmlContent.indexOf('<', i);
            if (openBracket == -1) {
                break;  // 没有更多的标签
            }
            
            // 寻找结束标签 >
            int closeBracket = htmlContent.indexOf('>', openBracket);
            if (closeBracket == -1) {
                return false;  // 有开始但没有结束，不平衡
            }
            
            // 提取标签内容
            String tag = htmlContent.substring(openBracket + 1, closeBracket).trim();
            
            // 处理自闭合标签 <tag/>
            if (tag.endsWith("/")) {
                i = closeBracket + 1;
                continue;  // 自闭合标签，无需入栈
            }
            
            // 处理注释 <!-- comment -->
            if (tag.startsWith("!--")) {
                // 寻找注释结束
                int commentEnd = htmlContent.indexOf("-->", closeBracket);
                if (commentEnd == -1) {
                    return false;  // 注释没有结束
                }
                i = commentEnd + 3;
                continue;  // 注释不影响标签匹配
            }
            
            // 处理结束标签 </tag>
            if (tag.startsWith("/")) {
                // 提取标签名
                String endTag = tag.substring(1);
                
                // 如果栈为空，说明没有对应的开始标签
                if (stack.isEmpty()) {
                    return false;
                }
                
                // 获取栈顶的开始标签
                String startTag = stack.pop();
                
                // 检查标签是否匹配
                if (!startTag.equals(endTag)) {
                    return false;
                }
            }
            // 处理开始标签 <tag>
            else {
                // 提取标签名，忽略属性
                String tagName = tag.split("\\s+")[0];
                stack.push(tagName);
            }
            
            i = closeBracket + 1;
        }
        
        // 如果栈为空，说明所有标签都已匹配
        return stack.isEmpty();
    }
    
    /**
     * 检查表达式中的所有平衡标记，包括括号和引号
     * 可以检查代码片段中的括号和引号是否平衡
     * @param expression 要检查的表达式
     * @return 如果所有标记都平衡返回true，否则返回false和错误描述
     */
    public static String checkAllBalancedMarkers(String expression) {
        if (expression == null || expression.isEmpty()) {
            return "平衡: 表达式为空";
        }
        
        ArrayListStack<Character> stack = new ArrayListStack<>();
        boolean inSingleQuote = false;  // 是否在单引号字符串中
        boolean inDoubleQuote = false;  // 是否在双引号字符串中
        
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            
            // 处理转义字符
            if (ch == '\\' && i + 1 < expression.length()) {
                i++;  // 跳过下一个字符
                continue;
            }
            
            // 处理引号
            if (ch == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;  // 切换单引号状态
                continue;
            }
            if (ch == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;  // 切换双引号状态
                continue;
            }
            
            // 如果在引号中，忽略括号
            if (inSingleQuote || inDoubleQuote) {
                continue;
            }
            
            // 处理括号
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return "不平衡: 位置 " + i + " 的 '" + ch + "' 没有对应的开括号";
                }
                
                char top = stack.pop();
                
                if ((ch == ')' && top != '(') ||
                    (ch == ']' && top != '[') ||
                    (ch == '}' && top != '{')) {
                    return "不平衡: 位置 " + i + " 的 '" + ch + "' 与 '" + top + "' 不匹配";
                }
            }
        }
        
        // 检查引号是否都已关闭
        if (inSingleQuote) {
            return "不平衡: 单引号未关闭";
        }
        if (inDoubleQuote) {
            return "不平衡: 双引号未关闭";
        }
        
        // 检查是否所有括号都已匹配
        if (!stack.isEmpty()) {
            StringBuilder unmatched = new StringBuilder("不平衡: 未匹配的开括号: ");
            while (!stack.isEmpty()) {
                unmatched.append(stack.pop()).append(" ");
            }
            return unmatched.toString();
        }
        
        return "平衡: 所有括号和引号都匹配";
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("表达式平衡检查器 - 使用栈实现");
        System.out.println("1. 括号匹配检查");
        System.out.println("2. HTML标签匹配检查");
        System.out.println("3. 代码表达式完整检查（括号和引号）");
        System.out.print("请选择检查类型(1-3): ");
        
        int choice = input.nextInt();
        input.nextLine();  // 消耗换行符
        
        switch (choice) {
            case 1:
                System.out.println("\n括号匹配检查");
                System.out.println("示例: {[()]} 是平衡的");
                System.out.println("示例: {[(])} 是不平衡的");
                
                while (true) {
                    System.out.print("\n请输入表达式 (输入'exit'退出): ");
                    String expression = input.nextLine().trim();
                    
                    if (expression.equalsIgnoreCase("exit")) {
                        break;
                    }
                    
                    boolean isBalanced = checkBrackets(expression);
                    System.out.println("结果: " + (isBalanced ? "平衡" : "不平衡"));
                }
                break;
                
            case 2:
                System.out.println("\nHTML标签匹配检查");
                System.out.println("示例: <html><body>内容</body></html> 是平衡的");
                System.out.println("示例: <html><body>内容</html> 是不平衡的");
                
                while (true) {
                    System.out.print("\n请输入HTML内容 (输入'exit'退出): ");
                    String html = input.nextLine().trim();
                    
                    if (html.equalsIgnoreCase("exit")) {
                        break;
                    }
                    
                    boolean isBalanced = checkHtmlTags(html);
                    System.out.println("结果: " + (isBalanced ? "平衡" : "不平衡"));
                }
                break;
                
            case 3:
                System.out.println("\n代码表达式完整检查");
                System.out.println("示例: if (x > 0) { console.log(\"正数\"); } 是平衡的");
                System.out.println("示例: if (x > 0) { console.log(\"正数\"; } 是不平衡的");
                
                while (true) {
                    System.out.print("\n请输入代码表达式 (输入'exit'退出): ");
                    String code = input.nextLine().trim();
                    
                    if (code.equalsIgnoreCase("exit")) {
                        break;
                    }
                    
                    String result = checkAllBalancedMarkers(code);
                    System.out.println("结果: " + result);
                }
                break;
                
            default:
                System.out.println("无效的选择");
        }
        
        System.out.println("\n表达式平衡检查的应用:");
        System.out.println("1. 编程语言编译器和解释器中的语法检查");
        System.out.println("2. HTML/XML文档的有效性验证");
        System.out.println("3. 数学表达式的有效性验证");
        System.out.println("4. 代码编辑器中的实时语法检查");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 