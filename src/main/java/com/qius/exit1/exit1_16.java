package com.qius.exit1;

/**
 * 问题16："Quine"是一个程序，它不需要读取任何输入或文件，就可以打印自己的源代码。
 * 使用Java文本块编写这样的程序。
 */
public class exit1_16 {
    public static void main(String[] args) {
        // 定义一个字符串s，其中包含程序的源代码
        // 使用Java 17的文本块特性，更清晰地表示多行字符串
        String s = """
/**
 * 问题16："Quine"是一个程序，它不需要读取任何输入或文件，就可以打印自己的源代码。
 * 使用Java文本块编写这样的程序。
 */
public class exit1_16 {
    public static void main(String[] args) {
        // 定义一个字符串s，其中包含程序的源代码
        // 使用Java 17的文本块特性，更清晰地表示多行字符串
        String s = %s;
        // 使用String.format将s本身（带引号）插入到s中的%s占位符处
        System.out.println(String.format(s, quoteTextBlock(s)));
        
        // 解释Quine程序的原理
        System.out.println();
        System.out.println("// ---- Quine程序的原理解释（不是原程序的一部分） ----");
        System.out.println("// 1. 定义一个包含程序源代码的字符串s，使用Java 17的文本块特性");
        System.out.println("// 2. 在字符串s中使用%s作为占位符");
        System.out.println("// 3. 使用String.format将s本身插入到占位符处");
        System.out.println("// 4. 文本块使得源代码更易读，无需转义每行末尾的换行符");
        System.out.println("// 5. 这样就能生成并打印出完整的程序源代码");
    }
    
    // 辅助方法：将文本块转换为带引号的形式
    private static String quoteTextBlock(String s) {
        return "\"\"\"\n" + s + "\"\"\"";
    }
}""";
        // 使用String.format将s本身（带引号）插入到s中的%s占位符处
        System.out.println(String.format(s, quoteTextBlock(s)));
        
        // 解释Quine程序的原理
        System.out.println();
        System.out.println("// ---- Quine程序的原理解释（不是原程序的一部分） ----");
        System.out.println("// 1. 定义一个包含程序源代码的字符串s，使用Java 17的文本块特性");
        System.out.println("// 2. 在字符串s中使用%s作为占位符");
        System.out.println("// 3. 使用String.format将s本身插入到占位符处");
        System.out.println("// 4. 文本块使得源代码更易读，无需转义每行末尾的换行符");
        System.out.println("// 5. 这样就能生成并打印出完整的程序源代码");
    }
    
    // 辅助方法：将文本块转换为带引号的形式
    private static String quoteTextBlock(String s) {
        return "\"\"\"\n" + s + "\"\"\"";
    }
} 