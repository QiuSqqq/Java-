package com.qius.exit3;

/**
 * 问题8：将javadoc注释添加到前面练习中两个版本的Point类中。
 * 
 * 本文件展示了如何对两个版本的Point类（不可变记录版本和可变类版本）添加恰当的javadoc注释。
 * 
 * @author 练习解答
 * @version 1.0
 */
public class exit3_8 {
    public static void main(String[] args) {
        System.out.println("问题8：为Point类添加javadoc注释");
        System.out.println("----------------------------");
        
        // 创建不可变Point记录的实例
        System.out.println("创建不可变Point记录的实例：");
        PointRecord p1 = new PointRecord(3, 4);
        PointRecord p2 = p1.translate(1, 3).scale(0.5);
        System.out.println("p1: " + p1);
        System.out.println("p2 = p1.translate(1, 3).scale(0.5): " + p2);
        
        // 创建可变Point类的实例
        System.out.println("\n创建可变Point类的实例：");
        MutablePoint mp1 = new MutablePoint(3, 4);
        System.out.println("mp1 初始值: " + mp1);
        mp1.translate(1, 3).scale(0.5);
        System.out.println("mp1.translate(1, 3).scale(0.5) 后: " + mp1);
        
        // 关于Javadoc的解释
        System.out.println("\nJavadoc注释说明：");
        System.out.println("1. 类/记录级注释：");
        System.out.println("   - 描述类/记录的整体目的和用途");
        System.out.println("   - 包含@author和@version等标签");
        
        System.out.println("2. 方法注释：");
        System.out.println("   - 描述方法的功能");
        System.out.println("   - 包含@param描述参数");
        System.out.println("   - 包含@return描述返回值");
        System.out.println("   - 包含@throws描述可能抛出的异常");
        
        System.out.println("3. 字段注释：");
        System.out.println("   - 描述字段的用途和含义");
        
        System.out.println("4. 生成Javadoc文档的命令：");
        System.out.println("   javadoc -d doc *.java");
    }
}

/**
 * 不可变的Point记录，表示二维平面上的一个点。
 * <p>
 * 该记录采用不可变设计，所有操作都会返回新的Point实例而不修改原对象。
 * 这种设计使得Point可以安全地在多线程环境中使用，并可以作为哈希表的键。
 * </p>
 * 
 * @author 练习解答
 * @version 1.0
 * @since 1.0
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
 * 可变的Point类，表示二维平面上的一个点。
 * <p>
 * 该类采用可变设计，操作方法会直接修改对象的状态。
 * 方法返回this引用以支持方法链式调用。
 * 注意：由于是可变对象，在多线程环境中使用时需要额外的同步措施。
 * </p>
 * 
 * @author 练习解答
 * @version 1.0
 * @since 1.0
 */
class MutablePoint {
    /** 
     * 点的x坐标 
     */
    private double x;
    
    /** 
     * 点的y坐标 
     */
    private double y;
    
    /**
     * 创建一个新的可变Point，指定其x和y坐标。
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
     * 沿x和y方向平移当前点。
     * <p>
     * 该方法会修改当前对象的状态，并返回this引用以支持方法链式调用。
     * </p>
     * 
     * @param dx x方向的位移量
     * @param dy y方向的位移量
     * @return 当前对象的引用，用于链式调用
     */
    public MutablePoint translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }
    
    /**
     * 按指定因子缩放当前点的坐标。
     * <p>
     * 缩放操作会按相同的因子同时缩放x和y坐标。
     * 该方法会修改当前对象的状态，并返回this引用以支持方法链式调用。
     * </p>
     * 
     * @param factor 缩放因子（例如：0.5表示缩小为原来的一半，2表示放大为原来的两倍）
     * @return 当前对象的引用，用于链式调用
     */
    public MutablePoint scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        return this;
    }
    
    /**
     * 将当前点设置为指定的x和y坐标。
     * <p>
     * 这是一个修改器方法，会改变对象的内部状态。
     * </p>
     * 
     * @param x 新的x坐标
     * @param y 新的y坐标
     * @return 当前对象的引用，用于链式调用
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
