package com.qius.exit2;

/**
 * 问题6：重复前面的练习，但现在将Point作为一个类来实现，并将translate和scale方法作为修改器。
 */
public class exit2_6 {
    public static void main(String[] args) {
        System.out.println("问题6：实现Point类，translate和scale作为修改器");
        System.out.println("--------------------------------------------");
        
        // 创建一个初始点
        PointClass p = new PointClass(3, 4);
        System.out.println("初始点 p: " + p);
        
        // 测试translate方法
        p.translate(1, 3);
        System.out.println("p.translate(1, 3) 后: " + p);
        
        // 测试scale方法
        p.scale(0.5);
        System.out.println("p.scale(0.5) 后: " + p);
        
        // 测试方法链式调用
        PointClass p2 = new PointClass(3, 4);
        p2.translate(1, 3).scale(0.5);
        System.out.println("链式调用 new PointClass(3, 4).translate(1, 3).scale(0.5) 的结果: " + p2);
        
        // 验证计算结果
        System.out.println("\n验证计算过程:");
        System.out.println("1. 起始点 (3, 4)");
        System.out.println("2. 平移 (+1, +3) 得到 (4, 7)");
        System.out.println("3. 缩放 (×0.5) 得到 (2, 3.5)");
        System.out.println("4. 最终结果应为 (2, 3.5)");
        
        // 验证结果是否正确
        if (p.getX() == 2.0 && p.getY() == 3.5) {
            System.out.println("验证成功：计算结果正确！");
        } else {
            System.out.println("验证失败：计算结果错误！");
        }
        
        // 比较类和记录的实现方式
        System.out.println("\n类实现和记录实现的比较：");
        System.out.println("1. 类实现（可变）：");
        System.out.println("   - 修改器方法直接改变对象状态");
        System.out.println("   - 只需要一个对象实例");
        System.out.println("   - 可能需要注意线程安全问题");
        System.out.println("   - 对象的状态会随着方法调用而改变");
        
        System.out.println("2. 记录实现（不可变）：");
        System.out.println("   - 每次操作都会创建新对象");
        System.out.println("   - 原始对象保持不变");
        System.out.println("   - 天然线程安全");
        System.out.println("   - 可能会创建多个临时对象");
        
        System.out.println("\n根据应用场景选择：");
        System.out.println("- 如果需要跟踪对象变化历史或需要共享对象，使用不可变记录");
        System.out.println("- 如果性能关键且需要频繁修改，使用可变类");
    }
}

/**
 * Point的类实现版本，带有可变状态
 */
class PointClass {
    // 实例变量（可以修改）
    private double x;
    private double y;
    
    /**
     * 构造器
     * 
     * @param x 点的x坐标
     * @param y 点的y坐标
     */
    public PointClass(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 获取x坐标
     * 
     * @return x坐标
     */
    public double getX() {
        return x;
    }
    
    /**
     * 获取y坐标
     * 
     * @return y坐标
     */
    public double getY() {
        return y;
    }
    
    /**
     * 平移方法 - 沿x和y方向移动点（修改器）
     * 
     * @param dx x方向的偏移量
     * @param dy y方向的偏移量
     * @return 对象本身，用于链式调用
     */
    public PointClass translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        return this; // 返回this以支持链式调用
    }
    
    /**
     * 缩放方法 - 按给定因子缩放坐标（修改器）
     * 
     * @param factor 缩放因子
     * @return 对象本身，用于链式调用
     */
    public PointClass scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        return this; // 返回this以支持链式调用
    }
    
    /**
     * 返回点的字符串表示
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
} 
