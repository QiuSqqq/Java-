package com.qius.exit4;

/**
 * 问题5：为上一练习中的类定义clone方法。
 */
public class exit4_5 {
    public static void main(String[] args) {
        // 创建原始形状
        CloneablePoint p1 = new CloneablePoint(1, 1);
        CloneablePoint p2 = new CloneablePoint(4, 5);
        
        CloneableCircle circle = new CloneableCircle(p1, 3.0);
        CloneableRectangle rectangle = new CloneableRectangle(p1, 5.0, 3.0);
        CloneableLine line = new CloneableLine(p1, p2);
        
        try {
            // 克隆形状
            CloneableCircle clonedCircle = circle.clone();
            CloneableRectangle clonedRectangle = rectangle.clone();
            CloneableLine clonedLine = line.clone();
            
            // 比较原始形状和克隆形状
            System.out.println("原始圆: " + circle);
            System.out.println("克隆圆: " + clonedCircle);
            System.out.println("是同一个对象？ " + (circle == clonedCircle)); // 应该是false
            System.out.println("中心点是否相同？ " + (circle.getCenter() == clonedCircle.getCenter())); // 应该是false（深拷贝）
            
            System.out.println();
            
            System.out.println("原始矩形: " + rectangle);
            System.out.println("克隆矩形: " + clonedRectangle);
            System.out.println("是同一个对象？ " + (rectangle == clonedRectangle)); // 应该是false
            System.out.println("位置点是否相同？ " + (rectangle.getPoint() == clonedRectangle.getPoint())); // 应该是false（深拷贝）
            
            System.out.println();
            
            System.out.println("原始线段: " + line);
            System.out.println("克隆线段: " + clonedLine);
            System.out.println("是同一个对象？ " + (line == clonedLine)); // 应该是false
            System.out.println("起点是否相同？ " + (line.getFrom() == clonedLine.getFrom())); // 应该是false（深拷贝）
            System.out.println("终点是否相同？ " + (line.getTo() == clonedLine.getTo())); // 应该是false（深拷贝）
            
            // 修改原始形状，证明克隆是深拷贝
            System.out.println("\n移动原始形状后：");
            circle.moveBy(2, 2);
            rectangle.moveBy(2, 2);
            line.moveBy(2, 2);
            
            System.out.println("原始圆的中心点: " + circle.getCenter());
            System.out.println("克隆圆的中心点: " + clonedCircle.getCenter()); // 不应受影响
            
            System.out.println("原始矩形的中心点: " + rectangle.getCenter());
            System.out.println("克隆矩形的中心点: " + clonedRectangle.getCenter()); // 不应受影响
            
            System.out.println("原始线段的中心点: " + line.getCenter());
            System.out.println("克隆线段的中心点: " + clonedLine.getCenter()); // 不应受影响
            
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 可克隆的点
 */
class CloneablePoint implements Cloneable {
    private double x;
    private double y;
    
    public CloneablePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    @Override
    public CloneablePoint clone() throws CloneNotSupportedException {
        return (CloneablePoint) super.clone();
    }
    
    @Override
    public String toString() {
        return "Point[x=" + x + ", y=" + y + "]";
    }
}

/**
 * 表示可克隆形状的抽象类
 */
abstract class CloneableShape implements Cloneable {
    private CloneablePoint point;
    
    public CloneableShape(CloneablePoint point) {
        this.point = point;
    }
    
    public CloneablePoint getPoint() {
        return point;
    }
    
    public void moveBy(double dx, double dy) {
        point = new CloneablePoint(point.getX() + dx, point.getY() + dy);
    }
    
    public abstract CloneablePoint getCenter();
    
    /**
     * 克隆形状
     * 注意：这是一个深拷贝实现，会克隆所有可变字段
     */
    @Override
    public CloneableShape clone() throws CloneNotSupportedException {
        CloneableShape cloned = (CloneableShape) super.clone();
        // 深拷贝点对象
        cloned.point = this.point.clone();
        return cloned;
    }
}

/**
 * 表示可克隆的圆形
 */
class CloneableCircle extends CloneableShape {
    private double radius;
    
    public CloneableCircle(CloneablePoint center, double radius) {
        super(center);
        this.radius = radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    @Override
    public CloneablePoint getCenter() {
        return getPoint();
    }
    
    /**
     * 克隆圆形
     */
    @Override
    public CloneableCircle clone() throws CloneNotSupportedException {
        return (CloneableCircle) super.clone();
    }
    
    @Override
    public String toString() {
        return "Circle[center=" + getCenter() + ", radius=" + radius + "]";
    }
}

/**
 * 表示可克隆的矩形
 */
class CloneableRectangle extends CloneableShape {
    private double width;
    private double height;
    
    public CloneableRectangle(CloneablePoint topLeft, double width, double height) {
        super(topLeft);
        this.width = width;
        this.height = height;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    @Override
    public CloneablePoint getCenter() {
        return new CloneablePoint(
            getPoint().getX() + width / 2,
            getPoint().getY() + height / 2
        );
    }
    
    /**
     * 克隆矩形
     */
    @Override
    public CloneableRectangle clone() throws CloneNotSupportedException {
        return (CloneableRectangle) super.clone();
    }
    
    @Override
    public String toString() {
        return "Rectangle[topLeft=" + getPoint() + ", width=" + width + ", height=" + height + "]";
    }
}

/**
 * 表示可克隆的线段
 */
class CloneableLine extends CloneableShape {
    private CloneablePoint to;
    
    public CloneableLine(CloneablePoint from, CloneablePoint to) {
        super(from);
        this.to = to;
    }
    
    public CloneablePoint getFrom() {
        return getPoint();
    }
    
    public CloneablePoint getTo() {
        return to;
    }
    
    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy);
        to = new CloneablePoint(to.getX() + dx, to.getY() + dy);
    }
    
    @Override
    public CloneablePoint getCenter() {
        return new CloneablePoint(
            (getFrom().getX() + to.getX()) / 2,
            (getFrom().getY() + to.getY()) / 2
        );
    }
    
    /**
     * 克隆线段
     * 注意：这是一个深拷贝实现，会克隆终点
     */
    @Override
    public CloneableLine clone() throws CloneNotSupportedException {
        CloneableLine cloned = (CloneableLine) super.clone();
        // 深拷贝终点
        cloned.to = this.to.clone();
        return cloned;
    }
    
    @Override
    public String toString() {
        return "Line[from=" + getFrom() + ", to=" + to + "]";
    }
} 