package com.qius.exit2;

/**
 * 问题8：将javadoc注释添加到前面练习中两个版本的Point类中。
 */
public class exit2_8 {
    public static void main(String[] args) {
        System.out.println("问题8：为Point类添加javadoc注释");
        System.out.println("----------------------------");
        
        /**
         * 测试不可变Point记录
         */
        System.out.println("\n1. 不可变Point记录示例");
        PointRecord p1 = new PointRecord(3, 4);
        System.out.println("   原始点: " + p1);
        
        PointRecord p2 = p1.translate(1, 3).scale(0.5);
        System.out.println("   变换后: " + p2);
        
        /**
         * 测试可变Point类
         */
        System.out.println("\n2. 可变Point类示例");
        MutablePoint mp1 = new MutablePoint(3, 4);
        System.out.println("   原始点: " + mp1);
        
        mp1.translate(1, 3).scale(0.5);
        System.out.println("   变换后: " + mp1);
        
        /**
         * javadoc概述
         */
        System.out.println("\n3. Javadoc注释概述");
        
        // 以下是Javadoc的主要特点
        // 1. Javadoc是Java的官方文档格式
        // 2. 以/** 开始，以 */ 结束
        // 3. 支持HTML标记和特殊标签如@param, @return等
        // 4. 可以使用javadoc工具生成HTML文档
        
        // 运行javadoc命令示例:
        // javadoc -d docs PointRecord.java MutablePoint.java
    }
}

/**
 * 点的不可变记录实现。
 * <p>
 * 该记录表示二维平面上的一个点，由x和y坐标定义。
 * 作为记录，Point是不可变的，所有操作都会返回新的Point实例而不修改原始实例。
 * </p>
 *
 * @author 您的名字
 * @version 1.0
 * @since 2023-05-01
 */
record PointRecord(double x, double y) {
    
    /**
     * 创建一个新的Point，指定其x和y坐标。
     * <p>
     * 这是由Java自动为记录生成的标准构造器。
     * </p>
     * 
     * @param x 点的x坐标
     * @param y 点的y坐标
     */
    // 构造器由record自动生成
    
    /**
     * 返回该点的x坐标。
     * <p>
     * 这是由Java自动为记录生成的访问器方法。
     * </p>
     * 
     * @return 点的x坐标
     */
    // x()方法由record自动生成
    
    /**
     * 返回该点的y坐标。
     * <p>
     * 这是由Java自动为记录生成的访问器方法。
     * </p>
     * 
     * @return 点的y坐标
     */
    // y()方法由record自动生成
    
    /**
     * 创建并返回一个新点，该点是当前点沿x和y方向平移后的结果。
     * <p>
     * 该方法不会修改当前对象，而是返回一个新的Point实例。
     * </p>
     * 
     * @param dx x方向的位移量
     * @param dy y方向的位移量
     * @return 平移后的新Point对象
     */
    public PointRecord translate(double dx, double dy) {
        return new PointRecord(x + dx, y + dy);
    }
    
    /**
     * 创建并返回一个新点，该点是当前点按指定因子缩放后的结果。
     * <p>
     * 缩放操作会按相同的因子同时缩放x和y坐标。
     * 该方法不会修改当前对象，而是返回一个新的Point实例。
     * </p>
     * 
     * @param factor 缩放因子（例如：0.5表示缩小为原来的一半，2表示放大为原来的两倍）
     * @return 缩放后的新Point对象
     */
    public PointRecord scale(double factor) {
        return new PointRecord(x * factor, y * factor);
    }
    
    /**
     * 返回表示此点的字符串，格式为"(x, y)"。
     * <p>
     * 这个方法重写了record自动生成的toString方法，提供更友好的格式。
     * </p>
     * 
     * @return 格式化的字符串表示
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

/**
 * 表示二维平面上的一个可变点。
 * <p>
 * 该类提供了修改点坐标的方法，允许通过translate和scale操作直接改变对象状态。
 * 与不可变Point记录不同，这个类的方法会修改原始对象而不是创建新实例。
 * </p>
 *
 * @author 您的名字
 * @version 1.0
 * @since 2023-05-01
 */
class MutablePoint {
    /**
     * 点的X坐标。
     */
    private double x;
    
    /**
     * 点的Y坐标。
     */
    private double y;
    
    /**
     * 创建一个新的MutablePoint对象，指定其x和y坐标。
     *
     * @param x 点的x坐标
     * @param y 点的y坐标
     */
    public MutablePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 获取点的x坐标。
     *
     * @return 点的x坐标
     */
    public double getX() {
        return x;
    }
    
    /**
     * 获取点的y坐标。
     *
     * @return 点的y坐标
     */
    public double getY() {
        return y;
    }
    
    /**
     * 沿x和y方向移动此点。
     * <p>
     * 这是一个修改器方法，会直接更改当前对象的状态。
     * 方法返回this引用，允许链式调用。
     * </p>
     *
     * @param dx x方向的位移量
     * @param dy y方向的位移量
     * @return 此点自身，允许方法链调用
     */
    public MutablePoint translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }
    
    /**
     * 按指定因子缩放此点的坐标。
     * <p>
     * 这是一个修改器方法，会直接更改当前对象的状态。
     * 方法返回this引用，允许链式调用。
     * </p>
     *
     * @param factor 缩放因子
     * @return 此点自身，允许方法链调用
     */
    public MutablePoint scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        return this;
    }
    
    /**
     * 直接设置此点的位置。
     * <p>
     * 这是一个修改器方法，会直接更改当前对象的状态。
     * 方法返回this引用，允许链式调用。
     * </p>
     *
     * @param x 新的x坐标
     * @param y 新的y坐标
     * @return 此点自身，允许方法链调用
     */
    public MutablePoint setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    /**
     * 返回表示此点的字符串，格式为"(x, y)"。
     *
     * @return 格式化的字符串表示
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
} 
