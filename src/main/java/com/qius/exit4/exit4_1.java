package com.qius.exit4;

import java.util.Objects;

/**
 * 问题1：定义一个Point类，包括构造器public Point(double x，double y)和访问器方法getX、getY。
 * 定义一个子类LabeledPoint，包括构造器public LabeledPoint(String label, double x, double y)
 * 和一个访问器方法getLabel。
 * 
 * 本程序展示了如何：
 * 1. 定义基本的Point类，包含坐标和相关访问器方法
 * 2. 创建一个扩展Point的LabeledPoint子类，添加标签功能
 * 3. 展示类继承的基本使用方式和访问父类成员的方法
 */
public class exit4_1 {
    public static void main(String[] args) {
        /**
         * 测试Point类的基本功能
         * - 创建Point对象并初始化坐标
         * - 使用访问器方法获取坐标值
         */
        // 测试Point类
        Point p = new Point(3.0, 4.0);
        System.out.println("Point坐标：(" + p.getX() + ", " + p.getY() + ")");
        
        /**
         * 测试LabeledPoint类的功能
         * - 创建带标签的点并设置坐标
         * - 访问标签和继承自Point的坐标信息
         */
        // 测试LabeledPoint类
        LabeledPoint lp = new LabeledPoint("Origin", 0.0, 0.0);
        System.out.println("LabeledPoint标签：" + lp.getLabel());
        System.out.println("LabeledPoint坐标：(" + lp.getX() + ", " + lp.getY() + ")");
    }
}

/**
 * 表示二维平面上的点的类
 * 
 * 本类提供了：
 * 1. 存储和管理二维坐标 (x, y)
 * 2. 访问坐标的getter方法
 */
class Point {
    // 问题3将要求把这些变量设为protected，现在先用private
    private double x; // x坐标
    private double y; // y坐标
    
    /**
     * 构造一个具有指定坐标的点
     * - 接收x和y坐标参数并存储在对象中
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 获取点的x坐标
     * - 返回当前点的x坐标值
     */
    public double getX() {
        return x;
    }
    
    /**
     * 获取点的y坐标
     * - 返回当前点的y坐标值
     */
    public double getY() {
        return y;
    }
}

/**
 * 带标签的点，继承自Point类
 * 
 * 本类扩展了Point类，添加了：
 * 1. 为点添加文本标签的功能
 * 2. 保留Point类的所有坐标操作能力
 */
class LabeledPoint extends Point {
    private String label; // 点的标签
    
    /**
     * 构造一个带标签的点
     * - 接收标签和坐标参数
     * - 调用父类构造器初始化坐标部分
     */
    public LabeledPoint(String label, double x, double y) {
        super(x, y); // 调用父类构造器初始化坐标
        this.label = label;
    }
    
    /**
     * 获取点的标签
     * - 返回当前点的文本标签
     */
    public String getLabel() {
        return label;
    }
} 