package com.qius.exit2;

/**
 * 问题5：向Point记录添加translate和scale方法。translate方法将点沿x和y方向移动给定的量。
 * scale方法按给定因子缩放两个坐标。实现这些方法，注意让它们返回带有结果的新点。
 * 例如，Point p = new Point(3, 4).translate(1, 3).scale(0.5);
 * 以上语句的结果是将p设置为一个坐标为(2, 3.5)的点。
 * 
 * 本程序展示了如何：
 * 1. 利用Java记录(record)创建不可变的点对象
 * 2. 实现方法链式调用模式
 * 3. 在不可变对象上实现变换操作（平移和缩放）
 * 4. 利用不可变设计保证对象状态的一致性和线程安全
 */
public class exit2_5 {
    public static void main(String[] args) {
        System.out.println("问题5：实现Point记录的translate和scale方法");
        System.out.println("----------------------------------------");
        
        // 创建一个初始点
        Point p1 = new Point(3, 4);
        System.out.println("初始点 p1: " + p1);
        
        // 测试translate方法
        Point p2 = p1.translate(1, 3);
        System.out.println("p1.translate(1, 3) 后的新点 p2: " + p2);
        System.out.println("原点 p1 保持不变: " + p1);
        
        // 测试scale方法
        Point p3 = p2.scale(0.5);
        System.out.println("p2.scale(0.5) 后的新点 p3: " + p3);
        System.out.println("p2 保持不变: " + p2);
        
        // 测试方法链式调用
        Point p4 = new Point(3, 4).translate(1, 3).scale(0.5);
        System.out.println("链式调用 new Point(3, 4).translate(1, 3).scale(0.5) 的结果 p4: " + p4);
        
        // 验证计算结果
        System.out.println("\n验证计算过程:");
        System.out.println("1. 起始点 (3, 4)");
        System.out.println("2. 平移 (+1, +3) 得到 (4, 7)");
        System.out.println("3. 缩放 (×0.5) 得到 (2, 3.5)");
        System.out.println("4. 最终结果应为 (2, 3.5)");
        
        // 验证链式调用的结果是否正确
        if (p4.x() == 2.0 && p4.y() == 3.5) {
            System.out.println("验证成功：计算结果正确！");
        } else {
            System.out.println("验证失败：计算结果错误！");
        }
        
        // 解释不可变对象的好处
        System.out.println("\n不可变Point记录的好处：");
        System.out.println("1. 线程安全 - 多个线程可以同时使用，无需同步");
        System.out.println("2. 防止意外修改 - 一旦创建就不能更改，减少错误");
        System.out.println("3. 可以安全地用作HashMap或HashSet的键");
        System.out.println("4. 方法链式调用清晰、直观");
        System.out.println("5. 对象状态始终一致，不会处于无效或部分修改的状态");
    }
}

/**
 * 使用Java记录(record)实现的Point类
 * records是Java 14引入的特性，它们自动提供构造器、getter、equals()、hashCode()和toString()方法
 * records默认是不可变的(immutable)，所有字段都是final的
 */
record Point(double x, double y) {
    /**
     * 平移方法 - 沿x和y方向移动点
     * 
     * @param dx x方向的偏移量
     * @param dy y方向的偏移量
     * @return 一个新的Point对象，表示平移后的点
     */
    public Point translate(double dx, double dy) {
        // 创建并返回一个新的Point对象，其坐标是当前点加上偏移量
        return new Point(x + dx, y + dy);
    }
    
    /**
     * 缩放方法 - 按给定因子缩放坐标
     * 
     * @param factor 缩放因子
     * @return 一个新的Point对象，表示缩放后的点
     */
    public Point scale(double factor) {
        // 创建并返回一个新的Point对象，其坐标是当前点乘以缩放因子
        return new Point(x * factor, y * factor);
    }
    
    /**
     * 返回点的字符串表示
     * 记录(record)会自动生成toString()方法，但此处我们重写它以获得更友好的格式
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
} 