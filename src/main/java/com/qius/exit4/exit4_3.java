package com.qius.exit4;

/**
 * 问题3：将练习1中Point类的实例变量x和y设置成protected。
 * 证明LabeledPoint类只能在LabeledPoint实例中访问这些变量。
 */
public class exit4_3 {
    public static void main(String[] args) {
        // 测试Point类
        PointWithProtected p = new PointWithProtected(3.0, 4.0);
        System.out.println("Point坐标：(" + p.getX() + ", " + p.getY() + ")");
        
        // 尝试直接访问protected变量（在同一个包中是允许的）
        System.out.println("直接访问Point的protected变量（在同包中可以）：(" + p.x + ", " + p.y + ")");
        
        // 测试LabeledPoint类
        LabeledPointWithProtected lp = new LabeledPointWithProtected("Origin", 0.0, 0.0);
        System.out.println("LabeledPoint标签：" + lp.getLabel());
        System.out.println("LabeledPoint坐标：(" + lp.getX() + ", " + lp.getY() + ")");
        
        // 直接访问LabeledPoint对象的protected变量（在同一个包中是允许的）
        System.out.println("直接访问LabeledPoint的protected变量（在同包中可以）：(" + lp.x + ", " + lp.y + ")");
        
        // 证明LabeledPoint只能访问自己实例中的protected变量
        lp.demonstrateProtectedAccess(p);
    }
}

/**
 * 带有protected实例变量的Point类
 */
class PointWithProtected {
    protected double x; // protected的x坐标
    protected double y; // protected的y坐标
    
    /**
     * 构造一个具有指定坐标的点
     * @param x x坐标
     * @param y y坐标
     */
    public PointWithProtected(double x, double y) {
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
 * 带有protected实例变量访问的LabeledPoint类
 */
class LabeledPointWithProtected extends PointWithProtected {
    private String label; // 点的标签
    
    /**
     * 构造一个带标签的点
     * @param label 标签
     * @param x x坐标
     * @param y y坐标
     */
    public LabeledPointWithProtected(String label, double x, double y) {
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
     * 演示protected访问权限的方法
     * @param other 另一个Point对象
     */
    public void demonstrateProtectedAccess(PointWithProtected other) {
        System.out.println("\n=== 演示protected访问权限 ===");
        
        // 访问自己的protected变量（合法）
        System.out.println("1. LabeledPoint可以访问自己的protected变量：");
        System.out.println("   this.x = " + this.x);
        System.out.println("   this.y = " + this.y);
        
        // 尝试访问另一个Point对象的protected变量
        System.out.println("\n2. LabeledPoint尝试访问另一个Point对象的protected变量：");
        
        // 注意：在Java中，子类只能访问自己实例中从父类继承的protected成员
        // 不能访问父类其他实例中的protected成员
        // 下面的代码在编译时不会报错，但概念上是错误的，应该避免这样做
        
        if (other instanceof LabeledPointWithProtected) {
            System.out.println("   other是LabeledPoint的实例，可以通过转型后的引用访问：");
            LabeledPointWithProtected otherLP = (LabeledPointWithProtected) other;
            System.out.println("   otherLP.x = " + otherLP.x);
            System.out.println("   otherLP.y = " + otherLP.y);
        } else {
            System.out.println("   other只是Point的实例，在概念上不应该直接访问它的protected变量");
            System.out.println("   但在同一个包中，以下访问在Java中是允许的：");
            System.out.println("   other.x = " + other.x);
            System.out.println("   other.y = " + other.y);
            
            System.out.println("\n3. 证明：如果Point和LabeledPoint在不同的包中，则无法访问");
            System.out.println("   LabeledPoint只能访问从父类继承来的自己实例中的protected变量");
            System.out.println("   不能访问其他Point实例的protected变量");
        }
        
        System.out.println("\n结论：");
        System.out.println("1. protected成员可以被同一包中的其他类访问");
        System.out.println("2. protected成员可以被子类访问，但只能访问自己实例中继承的protected成员");
        System.out.println("3. 如果在不同包中，子类无法访问父类其他实例的protected成员");
    }
} 