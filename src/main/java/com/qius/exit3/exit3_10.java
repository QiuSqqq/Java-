package com.qius.exit3;

/**
 * 问题10：实现一个Car类，该类模拟沿x轴行驶的汽车，车行驶需要消耗汽油。
 * 在构造器中指定燃油效率（英里/加仑）。再提供一个按给定英里数行驶的方法，
 * 当给油箱加给定加仑的汽油，得到当前距离原点的距离和当前的燃油水平。
 * 这应该是一个不可变的类吗？为什么？
 */
public class exit3_10 {
    public static void main(String[] args) {
        System.out.println("问题10：实现Car类");
        System.out.println("-----------------");
        
        // 创建汽车实例 - 燃油效率为25英里/加仑
        Car car = new Car(25);
        System.out.println("创建燃油效率为25英里/加仑的汽车");
        
        // 添加燃油
        car.addGas(20);
        System.out.println("添加20加仑汽油");
        System.out.println("当前位置: " + car.getPosition() + " 英里");
        System.out.println("当前燃油: " + car.getFuelLevel() + " 加仑");
        
        // 行驶一段距离
        car.drive(100);
        System.out.println("\n行驶100英里后");
        System.out.println("当前位置: " + car.getPosition() + " 英里");
        System.out.println("当前燃油: " + car.getFuelLevel() + " 加仑");
        System.out.println("消耗燃油: " + (100 / 25.0) + " 加仑");
        
        // 再行驶，测试燃油不足的情况
        car.drive(500);
        System.out.println("\n尝试再行驶500英里");
        System.out.println("当前位置: " + car.getPosition() + " 英里");
        System.out.println("当前燃油: " + car.getFuelLevel() + " 加仑");
        
        // 加油并继续行驶
        car.addGas(10);
        System.out.println("\n添加10加仑汽油");
        System.out.println("当前燃油: " + car.getFuelLevel() + " 加仑");
        
        car.drive(200);
        System.out.println("\n行驶200英里后");
        System.out.println("当前位置: " + car.getPosition() + " 英里");
        System.out.println("当前燃油: " + car.getFuelLevel() + " 加仑");
        
        // 讨论不可变性
        System.out.println("\n关于Car类的不可变性讨论：");
        System.out.println("Car类不应该是不可变的，原因如下：");
        System.out.println("1. 汽车的本质是可变的 - 位置和燃油水平随时间变化");
        System.out.println("2. 汽车需要维护状态（位置、燃油量）以跟踪其运行情况");
        System.out.println("3. 方法如drive()和addGas()需要修改汽车状态");
        System.out.println("4. 如果设计为不可变类，每次操作都需要创建新对象，不符合现实模型");
        
        System.out.println("\n如果要设计为不可变类，每次操作都需要：");
        System.out.println("- 创建新的Car对象");
        System.out.println("- 返回新对象给调用者");
        System.out.println("- 调用者必须使用返回的新对象");
        System.out.println("例如：car = car.drive(100)");
        
        System.out.println("\n虽然不可变设计有一些好处（线程安全、防止意外修改），");
        System.out.println("但对于汽车这种本质上是可变的对象，设计为可变类更加自然和直观。");
    }
}

/**
 * 汽车类，模拟在x轴上行驶的汽车。
 * <p>
 * 这是一个可变类，因为汽车的位置和燃油水平会随着行驶和加油而改变。
 * </p>
 */
class Car {
    /** 燃油效率（英里/加仑），这是不变的属性 */
    private final double fuelEfficiency;
    
    /** 当前位置（距离原点的英里数） */
    private double position;
    
    /** 当前油箱中的燃油量（加仑） */
    private double fuelLevel;
    
    /**
     * 创建一个新的汽车对象，设置其燃油效率。
     * <p>
     * 新创建的汽车初始位置在原点（0英里），油箱为空（0加仑）。
     * </p>
     * 
     * @param fuelEfficiency 燃油效率（英里/加仑）
     */
    public Car(double fuelEfficiency) {
        if (fuelEfficiency <= 0) {
            throw new IllegalArgumentException("燃油效率必须为正数");
        }
        this.fuelEfficiency = fuelEfficiency;
        this.position = 0;
        this.fuelLevel = 0;
    }
    
    /**
     * 向油箱添加指定量的燃油。
     * 
     * @param gas 要添加的燃油量（加仑）
     */
    public void addGas(double gas) {
        if (gas < 0) {
            throw new IllegalArgumentException("添加的燃油量不能为负数");
        }
        this.fuelLevel += gas;
    }
    
    /**
     * 驾驶汽车行驶指定的英里数。
     * <p>
     * 如果燃油不足以行驶请求的距离，汽车会尽可能行驶，直到燃油耗尽。
     * </p>
     * 
     * @param miles 要行驶的英里数
     */
    public void drive(double miles) {
        if (miles < 0) {
            throw new IllegalArgumentException("行驶距离不能为负数");
        }
        
        // 计算行驶所需的燃油量
        double gasNeeded = miles / fuelEfficiency;
        
        // 如果燃油不足，调整实际行驶距离
        if (gasNeeded > fuelLevel) {
            // 只能行驶当前燃油可支持的距离
            miles = fuelLevel * fuelEfficiency;
            gasNeeded = fuelLevel;
            System.out.println("警告：燃油不足，只能行驶 " + miles + " 英里");
        }
        
        // 更新位置和燃油水平
        position += miles;
        fuelLevel -= gasNeeded;
    }
    
    /**
     * 获取汽车当前位置（距离原点的英里数）。
     * 
     * @return 当前位置（英里）
     */
    public double getPosition() {
        return position;
    }
    
    /**
     * 获取当前油箱中的燃油水平。
     * 
     * @return 燃油水平（加仑）
     */
    public double getFuelLevel() {
        return fuelLevel;
    }
    
    /**
     * 获取汽车的燃油效率。
     * 
     * @return 燃油效率（英里/加仑）
     */
    public double getFuelEfficiency() {
        return fuelEfficiency;
    }
} 
