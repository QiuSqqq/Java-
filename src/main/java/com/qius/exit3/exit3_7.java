package com.qius.exit3;

/**
 * 问题7：在前面的练习中，提供Point类的构造器和getter方法是有些重复的。
 * 大多数IDE都有编写样板代码的快捷方式。看看你的IDE都提供了什么功能？
 */
public class exit3_7 {
    public static void main(String[] args) {
        System.out.println("问题7：IDE中的样板代码生成功能");
        System.out.println("----------------------------");
        
        System.out.println("在现代集成开发环境(IDE)中，有很多自动生成样板代码的功能，以下是几个常见IDE的功能：");
        
        // IntelliJ IDEA的功能
        System.out.println("\n1. IntelliJ IDEA的功能：");
        System.out.println("   a) 生成构造器：");
        System.out.println("      - Alt+Insert (Windows/Linux) 或 Command+N (Mac)");
        System.out.println("      - 选择'Constructor'选项");
        System.out.println("      - 选择要包含的字段");
        
        System.out.println("   b) 生成getter/setter：");
        System.out.println("      - Alt+Insert (Windows/Linux) 或 Command+N (Mac)");
        System.out.println("      - 选择'Getter and Setter'选项");
        System.out.println("      - 选择要生成getter/setter的字段");
        
        System.out.println("   c) 重写equals/hashCode/toString：");
        System.out.println("      - Alt+Insert，然后选择相应选项");
        
        System.out.println("   d) 使用Lombok插件：");
        System.out.println("      - @Getter, @Setter 注解自动生成getter/setter");
        System.out.println("      - @Data 注解自动生成所有常用方法");
        System.out.println("      - @Builder 注解生成建造者模式代码");
        
        // Eclipse的功能
        System.out.println("\n2. Eclipse的功能：");
        System.out.println("   a) 生成构造器：");
        System.out.println("      - 右键菜单 -> Source -> Generate Constructor using Fields");
        
        System.out.println("   b) 生成getter/setter：");
        System.out.println("      - 右键菜单 -> Source -> Generate Getters and Setters");
        
        System.out.println("   c) 重写方法：");
        System.out.println("      - 右键菜单 -> Source -> Override/Implement Methods");
        System.out.println("      - 右键菜单 -> Source -> Generate hashCode() and equals()");
        System.out.println("      - 右键菜单 -> Source -> Generate toString()");
        
        // VS Code的功能
        System.out.println("\n3. Visual Studio Code的功能：");
        System.out.println("   a) Java扩展包：");
        System.out.println("      - 使用'Java Extension Pack'");
        System.out.println("      - 右键或命令面板中选择'Source Action'");
        System.out.println("      - 选择生成构造器、getter/setter等");
        
        System.out.println("   b) 代码片段（Snippets）：");
        System.out.println("      - 输入关键字如'ctor'生成构造器");
        System.out.println("      - 输入'gett'生成getter");
        
        // 代码示例 - Point类的不同生成方式
        System.out.println("\n代码示例 - Point类的不同生成方式：");
        
        System.out.println("\n1. 手动编写：");
        System.out.println("```java");
        System.out.println("class Point {");
        System.out.println("    private double x;");
        System.out.println("    private double y;");
        System.out.println("    ");
        System.out.println("    public Point(double x, double y) {");
        System.out.println("        this.x = x;");
        System.out.println("        this.y = y;");
        System.out.println("    }");
        System.out.println("    ");
        System.out.println("    public double getX() { return x; }");
        System.out.println("    public double getY() { return y; }");
        System.out.println("    // ...其他方法");
        System.out.println("}");
        System.out.println("```");
        
        System.out.println("\n2. 使用Java记录(record)（Java 14+）：");
        System.out.println("```java");
        System.out.println("record Point(double x, double y) {");
        System.out.println("    // 自动生成构造器、getter、equals、hashCode、toString");
        System.out.println("    // 只需添加自定义方法");
        System.out.println("}");
        System.out.println("```");
        
        System.out.println("\n3. 使用Lombok（需要Lombok库）：");
        System.out.println("```java");
        System.out.println("@Getter // 自动生成所有getter");
        System.out.println("@AllArgsConstructor // 自动生成带所有参数的构造器");
        System.out.println("class Point {");
        System.out.println("    private final double x;");
        System.out.println("    private final double y;");
        System.out.println("    // 无需手动编写构造器和getter");
        System.out.println("}");
        System.out.println("```");
        
        // 总结
        System.out.println("\n总结：");
        System.out.println("1. 现代IDE提供了丰富的代码生成功能，可以节省大量时间");
        System.out.println("2. Java记录(record)是简化不可变数据类的最佳方式（Java 14+）");
        System.out.println("3. Lombok库也是减少样板代码的流行选择");
        System.out.println("4. 熟练使用这些工具可以显著提高编程效率");
    }
} 
