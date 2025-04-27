package com.qius.exit4;

import java.util.Objects;

/**
 * 问题1：定义一个Point类，包括构造器public Point(double x，double y)和访问器方法getX、getY。
 * 定义一个子类LabeledPoint，包括构造器public LabeledPoint(String label, double x, double y)
 * 和一个访问器方法getLabel。
 */
public class exit4_1 {
    public static void main(String[] args) {
        // 测试Point类
        Point p = new Point(3.0, 4.0);
        System.out.println("Point坐标：(" + p.getX() + ", " + p.getY() + ")");
        
        // 测试LabeledPoint类
        LabeledPoint lp = new LabeledPoint("Origin", 0.0, 0.0);
        System.out.println("LabeledPoint标签：" + lp.getLabel());
        System.out.println("LabeledPoint坐标：(" + lp.getX() + ", " + lp.getY() + ")");
    }
}

/**
 * 表示二维平面上的点的类
 */
class Point {
    // 问题3将要求把这些变量设为protected，现在先用private
    private double x; // x坐标
    private double y; // y坐标
    
    /**
     * 构造一个具有指定坐标的点
     * @param x x坐标
     * @param y y坐标
     */
    public Point(double x, double y) {
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
}

/**
 * 带标签的点，继承自Point类
 */
class LabeledPoint extends Point {
    private String label; // 点的标签
    
    /**
     * 构造一个带标签的点
     * @param label 标签
     * @param x x坐标
     * @param y y坐标
     */
    public LabeledPoint(String label, double x, double y) {
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
} 