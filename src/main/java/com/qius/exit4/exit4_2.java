package com.qius.exit4;

import java.util.Objects;

/**
 * 问题2：为上一练习中的类定义toString、equals和hashCode方法。
 */
public class exit4_2 {
    public static void main(String[] args) {
        // 测试Point类的toString、equals和hashCode方法
        Point_2 p1 = new Point_2(3.0, 4.0);
        Point_2 p2 = new Point_2(3.0, 4.0);
        Point_2 p3 = new Point_2(5.0, 6.0);
        
        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p3: " + p3);
        
        System.out.println("p1.equals(p2): " + p1.equals(p2)); // 应该返回true
        System.out.println("p1.equals(p3): " + p1.equals(p3)); // 应该返回false
        
        System.out.println("p1的hashCode: " + p1.hashCode());
        System.out.println("p2的hashCode: " + p2.hashCode()); // 应该与p1相同
        System.out.println("p3的hashCode: " + p3.hashCode()); // 应该与p1不同
        
        // 测试LabeledPoint类的toString、equals和hashCode方法
        LabeledPoint_2 lp1 = new LabeledPoint_2("Origin", 0.0, 0.0);
        LabeledPoint_2 lp2 = new LabeledPoint_2("Origin", 0.0, 0.0);
        LabeledPoint_2 lp3 = new LabeledPoint_2("Center", 0.0, 0.0);
        
        System.out.println("lp1: " + lp1);
        System.out.println("lp2: " + lp2);
        System.out.println("lp3: " + lp3);
        
        System.out.println("lp1.equals(lp2): " + lp1.equals(lp2)); // 应该返回true
        System.out.println("lp1.equals(lp3): " + lp1.equals(lp3)); // 应该返回false
        
        System.out.println("lp1的hashCode: " + lp1.hashCode());
        System.out.println("lp2的hashCode: " + lp2.hashCode()); // 应该与lp1相同
        System.out.println("lp3的hashCode: " + lp3.hashCode()); // 应该与lp1不同
    }
}

/**
 * 表示二维平面上的点的类
 */
class Point_2 {
    private double x; // x坐标
    private double y; // y坐标
    
    /**
     * 构造一个具有指定坐标的点
     * @param x x坐标
     * @param y y坐标
     */
    public Point_2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 获取点的x坐标
     * @return x坐标
     */
    public double getX() {
        return x;
    }
    
    /**
     * 获取点的y坐标
     * @return y坐标
     */
    public double getY() {
        return y;
    }
    
    /**
     * 返回点的字符串表示形式
     * 格式为：Point[x=值, y=值]
     * @return 点的字符串表示
     */
    @Override
    public String toString() {
        // 使用getClass().getSimpleName()获取当前类名，避免硬编码
        return getClass().getSimpleName() + "[x=" + x + ", y=" + y + "]";
    }
    
    /**
     * 判断当前点是否与另一个对象相等
     * 如果另一个对象也是Point类且坐标相同，则认为相等
     * @param obj 要比较的对象
     * @return 如果相等则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        // 检查引用是否相同
        if (this == obj) return true;
        // 检查是否为null或类型不同
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // 转换为Point类型并比较坐标
        Point_2 other = (Point_2) obj;
        // 使用Double.compare避免浮点数精度问题
        return Double.compare(x, other.x) == 0 && Double.compare(y, other.y) == 0;
    }
    
    /**
     * 计算点的哈希码
     * @return 哈希码值
     */
    @Override
    public int hashCode() {
        // 使用Objects.hash方法计算哈希码
        return Objects.hash(x, y);
    }
}

/**
 * 带标签的点，继承自Point类
 */
class LabeledPoint_2 extends Point_2 {
    private String label; // 点的标签
    
    /**
     * 构造一个带标签的点
     * @param label 标签
     * @param x x坐标
     * @param y y坐标
     */
    public LabeledPoint_2(String label, double x, double y) {
        super(x, y); // 调用父类构造器初始化坐标
        this.label = label;
    }
    
    /**
     * 获取点的标签
     * @return 标签
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * 返回带标签点的字符串表示形式
     * 格式为：LabeledPoint[label=标签, x=值, y=值]
     * @return 带标签点的字符串表示
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[label=" + label + ", x=" + getX() + ", y=" + getY() + "]";
    }
    
    /**
     * 判断当前带标签点是否与另一个对象相等
     * 如果另一个对象也是LabeledPoint类且标签和坐标都相同，则认为相等
     * @param obj 要比较的对象
     * @return 如果相等则返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        // 使用父类的equals方法检查坐标是否相等
        if (!super.equals(obj)) return false;
        
        // 此时我们知道obj是Point类型，且坐标相等
        // 检查obj是否是LabeledPoint类型
        if (!(obj instanceof LabeledPoint_2)) return false;
        
        // 转换为LabeledPoint类型并比较标签
        LabeledPoint_2 other = (LabeledPoint_2) obj;
        return Objects.equals(label, other.label);
    }
    
    /**
     * 计算带标签点的哈希码
     * @return 哈希码值
     */
    @Override
    public int hashCode() {
        // 结合父类哈希码和标签的哈希码
        return Objects.hash(super.hashCode(), label);
    }
} 