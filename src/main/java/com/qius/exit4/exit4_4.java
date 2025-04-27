package com.qius.exit4;

/**
 * 问题4：定义一个抽象类Shape，其中包括：
 * - Point类的实例变量
 * - 构造器
 * - 将点移动给定量的具体方法public void moveBy(double dx, double dy)
 * - 抽象方法public Point getCenter()
 * 
 * 提供具体子类Circle、Rectangle、Line，其构造器分别为：
 * - public Circle(Point center, double radius)
 * - public Rectangle(Point topLeft, double width, double height)
 * - public Line(Point from, Point to)
 */
public class exit4_4 {
    public static void main(String[] args) {
        // 创建各种形状
        Point p1 = new Point(1, 1);
        Point p2 = new Point(4, 5);
        
        Circle circle = new Circle(p1, 3.0);
        Rectangle rectangle = new Rectangle(p1, 5.0, 3.0);
        Line line = new Line(p1, p2);
        
        // 打印各形状的中心点
        System.out.println("圆的中心点: " + circle.getCenter());
        System.out.println("矩形的中心点: " + rectangle.getCenter());
        System.out.println("线段的中心点: " + line.getCenter());
        
        // 移动形状并再次打印中心点
        circle.moveBy(2, 2);
        rectangle.moveBy(2, 2);
        line.moveBy(2, 2);
        
        System.out.println("\n移动后：");
        System.out.println("圆的中心点: " + circle.getCenter());
        System.out.println("矩形的中心点: " + rectangle.getCenter());
        System.out.println("线段的中心点: " + line.getCenter());
    }
}

/**
 * 表示形状的抽象类
 */
abstract class Shape {
    // Point类的实例变量，用于保存形状的位置或基准点
    private Point point;
    
    /**
     * 构造一个形状
     * @param point 形状的位置或基准点
     */
    public Shape(Point point) {
        this.point = point;
    }
    
    /**
     * 获取形状的位置点
     * @return 位置点
     */
    public Point getPoint() {
        return point;
    }
    
    /**
     * 将形状移动给定的量
     * @param dx x方向的移动量
     * @param dy y方向的移动量
     */
    public void moveBy(double dx, double dy) {
        // 创建新的点来表示移动后的位置（因为Point是不可变的）
        point = new Point(point.getX() + dx, point.getY() + dy);
    }
    
    /**
     * 获取形状的中心点
     * @return 中心点
     */
    public abstract Point getCenter();
}

/**
 * 表示圆形的类
 */
class Circle extends Shape {
    private double radius; // 圆的半径
    
    /**
     * 构造一个圆
     * @param center 圆心
     * @param radius 半径
     */
    public Circle(Point center, double radius) {
        super(center); // 圆心作为形状的位置点
        this.radius = radius;
    }
    
    /**
     * 获取圆的半径
     * @return 半径
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * 获取圆的中心点
     * @return 圆心
     */
    @Override
    public Point getCenter() {
        return getPoint(); // 对于圆，位置点就是中心点
    }
    
    @Override
    public String toString() {
        return "Circle[center=" + getCenter() + ", radius=" + radius + "]";
    }
}

/**
 * 表示矩形的类
 */
class Rectangle extends Shape {
    private double width; // 矩形的宽
    private double height; // 矩形的高
    
    /**
     * 构造一个矩形
     * @param topLeft 左上角点
     * @param width 宽
     * @param height 高
     */
    public Rectangle(Point topLeft, double width, double height) {
        super(topLeft); // 左上角作为形状的位置点
        this.width = width;
        this.height = height;
    }
    
    /**
     * 获取矩形的宽
     * @return 宽
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * 获取矩形的高
     * @return 高
     */
    public double getHeight() {
        return height;
    }
    
    /**
     * 获取矩形的中心点
     * @return 中心点
     */
    @Override
    public Point getCenter() {
        // 矩形的中心点是左上角点加上宽和高的一半
        return new Point(
            getPoint().getX() + width / 2,
            getPoint().getY() + height / 2
        );
    }
    
    @Override
    public String toString() {
        return "Rectangle[topLeft=" + getPoint() + ", width=" + width + ", height=" + height + "]";
    }
}

/**
 * 表示线段的类
 */
class Line extends Shape {
    private Point to; // 线段的终点
    
    /**
     * 构造一个线段
     * @param from 起点
     * @param to 终点
     */
    public Line(Point from, Point to) {
        super(from); // 起点作为形状的位置点
        this.to = to;
    }
    
    /**
     * 获取线段的起点
     * @return 起点
     */
    public Point getFrom() {
        return getPoint();
    }
    
    /**
     * 获取线段的终点
     * @return 终点
     */
    public Point getTo() {
        return to;
    }
    
    /**
     * 将线段移动给定的量
     * 重写父类方法，因为需要同时移动起点和终点
     * @param dx x方向的移动量
     * @param dy y方向的移动量
     */
    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy); // 移动起点
        // 移动终点
        to = new Point(to.getX() + dx, to.getY() + dy);
    }
    
    /**
     * 获取线段的中心点
     * @return 中心点
     */
    @Override
    public Point getCenter() {
        // 线段的中心点是起点和终点的中点
        return new Point(
            (getFrom().getX() + to.getX()) / 2,
            (getFrom().getY() + to.getY()) / 2
        );
    }
    
    @Override
    public String toString() {
        return "Line[from=" + getFrom() + ", to=" + to + "]";
    }
} 