package com.qius.exit2;

/**
 * 问题7：在前面的练习中，提供Point类的构造器和getter方法是有些重复的。
 * 大多数IDE都有编写样板代码的快捷方式。看看你的IDE都提供了什么功能？
 */
public class exit2_7 {
    public static void main(String[] args) {
        System.out.println("问题7：IDE中的样板代码生成功能");
        System.out.println("----------------------------");
        
        /**
         * 在现代集成开发环境(IDE)中，有很多自动生成样板代码的功能，以下是几个常见IDE的功能：
         */
        
        /**
         * 1. IntelliJ IDEA的功能：
         *    a) 生成构造器：
         *       - Alt+Insert (Windows/Linux) 或 Command+N (Mac)
         *       - 选择'Constructor'选项
         *       - 选择要包含的字段
         *    
         *    b) 生成getter/setter：
         *       - Alt+Insert (Windows/Linux) 或 Command+N (Mac)
         *       - 选择'Getter and Setter'选项
         *       - 选择要生成getter/setter的字段
         *    
         *    c) 重写equals/hashCode/toString：
         *       - Alt+Insert，然后选择相应选项
         *    
         *    d) 使用Lombok插件：
         *       - @Getter, @Setter 注解自动生成getter/setter
         *       - @Data 注解自动生成所有常用方法
         *       - @Builder 注解生成建造者模式代码
         */
        System.out.println("\n1. IntelliJ IDEA功能概览");
        
        /**
         * 2. Eclipse的功能：
         *    a) 生成构造器：
         *       - 右键菜单 -> Source -> Generate Constructor using Fields
         *    
         *    b) 生成getter/setter：
         *       - 右键菜单 -> Source -> Generate Getters and Setters
         *    
         *    c) 重写方法：
         *       - 右键菜单 -> Source -> Override/Implement Methods
         *       - 右键菜单 -> Source -> Generate hashCode() and equals()
         *       - 右键菜单 -> Source -> Generate toString()
         */
        System.out.println("\n2. Eclipse功能概览");
        
        /**
         * 3. Visual Studio Code的功能：
         *    a) Java扩展包：
         *       - 使用'Java Extension Pack'
         *       - 右键或命令面板中选择'Source Action'
         *       - 选择生成构造器、getter/setter等
         *    
         *    b) 代码片段（Snippets）：
         *       - 输入关键字如'ctor'生成构造器
         *       - 输入'gett'生成getter
         */
        System.out.println("\n3. VS Code功能概览");
        
        /**
         * 代码示例 - Point类的不同生成方式：
         * 
         * 1. 手动编写：
         * ```java
         * class Point {
         *     private double x;
         *     private double y;
         *     
         *     public Point(double x, double y) {
         *         this.x = x;
         *         this.y = y;
         *     }
         *     
         *     public double getX() { return x; }
         *     public double getY() { return y; }
         *     // ...其他方法
         * }
         * ```
         * 
         * 2. 使用Java记录(record)（Java 14+）：
         * ```java
         * record Point(double x, double y) {
         *     // 自动生成构造器、getter、equals、hashCode、toString
         *     // 只需添加自定义方法
         * }
         * ```
         * 
         * 3. 使用Lombok（需要Lombok库）：
         * ```java
         * @Getter // 自动生成所有getter
         * @AllArgsConstructor // 自动生成带所有参数的构造器
         * class Point {
         *     private final double x;
         *     private final double y;
         *     // 无需手动编写构造器和getter
         * }
         * ```
         */
        System.out.println("\n4. Point类实现示例概览");
        
        /**
         * 总结：
         * 1. 现代IDE提供了丰富的代码生成功能，可以节省大量时间
         * 2. Java记录(record)是简化不可变数据类的最佳方式（Java 14+）
         * 3. Lombok库也是减少样板代码的流行选择
         * 4. 熟练使用这些工具可以显著提高编程效率
         */
        System.out.println("\n5. 总结");
    }
} 
