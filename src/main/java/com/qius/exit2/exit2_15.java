package com.qius.exit2;

/**
 * 问题15：编译Network类。注意，内部类的类文件名称为Network$Member.class。使用javap程序监视
 * 生成的代码。命令javap -private Classname 将显示方法和实例变量。在哪里可以看到对封闭类
 * 的引用？（在Linux/Mac OS系统中，运行javap时，需要在$符号前加一个 \。）
 */
public class exit2_15 {
    public static void main(String[] args) {
        // 打印标题
        System.out.println("问题15：分析Network类及其内部类Member的编译和反编译结果");
        System.out.println("--------------------------------------------------");
        
        /**
         * Network类的定义：
         * 
         * public class Network {
         *     // Network类的成员
         *     private String name;                  // 网络名称
         *     private Member[] members = new Member[100];  // 网络中的成员
         *     private int memberCount;              // 成员数量
         *     
         *     public Network(String name) {
         *         this.name = name;
         *     }
         *     
         *     // 内部类Member定义
         *     public class Member {
         *         private String name;
         *         private int id;
         *         
         *         public Member(String name) {
         *             this.name = name;
         *             id = memberCount;
         *             members[memberCount] = this;  // 使用封闭类的成员
         *             memberCount++;
         *         }
         *         
         *         public String getName() {
         *             return name;
         *         }
         *     }
         *     
         *     // Network类的其他方法
         *     public Member enroll(String name) {
         *         return new Member(name);
         *     }
         *     
         *     public String getNetworkName() {
         *         return name;
         *     }
         * }
         */
        System.out.println("1. 分析Network类的定义");
        
        /**
         * 编译结果文件：
         * 编译Network.java后，会生成两个类文件：
         * - Network.class（外部类）
         * - Network$Member.class（内部类）
         * 这体现了Java中内部类的命名规则：外部类名$内部类名.class
         */
        System.out.println("2. 查看编译后生成的类文件");
        
        /**
         * javap命令用法：
         * javap是Java自带的反编译工具，用于查看类文件的结构
         * - javap ClassName：显示类的公共成员
         * - javap -private ClassName：显示类的所有成员（包括私有成员）
         * - 在Linux/Mac OS中查看内部类时需要转义：javap -private Network\\$Member
         */
        System.out.println("3. 使用javap工具反编译类文件");
        
        /**
         * 执行 javap -private Network 的结果：
         * 
         * Compiled from "Network.java"
         * public class Network {
         *   private java.lang.String name;
         *   private Network$Member[] members;
         *   private int memberCount;
         *   public Network(java.lang.String);
         *   public Network$Member enroll(java.lang.String);
         *   public java.lang.String getNetworkName();
         * }
         */
        System.out.println("4. 分析Network类的反编译结果");
        
        /**
         * 执行 javap -private Network$Member 的结果：
         * 
         * Compiled from "Network.java"
         * public class Network$Member {
         *   private java.lang.String name;
         *   private int id;
         *   final Network this$0;  // 对封闭类的引用
         *   public Network$Member(Network, java.lang.String);
         *   public java.lang.String getName();
         * }
         */
        System.out.println("5. 分析Member内部类的反编译结果");
        
        /**
         * 对封闭类的引用分析：
         * 在内部类的反编译结果中，可以看到一个特殊的字段：
         * final Network this$0;  // this$0是内部类对外部类实例的引用
         * 
         * 这个字段有以下特点：
         * - 名为'this$0'，是编译器自动生成的
         * - 类型是外部类Network
         * - 被声明为final，不能被修改
         * - 在源代码中不可见，由编译器自动维护
         */
        System.out.println("6. 找到并分析对封闭类的引用");
        
        /**
         * 内部类构造器的转换：
         * 原始构造器： public Member(String name)
         * 编译后变为： public Network$Member(Network, java.lang.String)
         * 
         * 注意多了一个Network参数，这就是外部类实例的引用。
         * 当调用new Member(name)时，编译器实际上传入了this作为第一个参数：
         * new Member(name) → new Network$Member(Network.this, name)
         */
        System.out.println("7. 分析内部类构造器的变化");
        
        // 输出总结
        System.out.println("\n总结：");
        System.out.println("1. 非静态内部类的类文件名称为'外部类名$内部类名.class'");
        System.out.println("2. 内部类持有对外部类实例的引用(this$0)，这使得内部类能访问外部类的私有成员");
        System.out.println("3. 内部类构造器被编译器修改，增加了外部类引用参数");
        System.out.println("4. 静态内部类没有this$0字段，因为它不依赖外部类实例");
    }
} 
