package com.qius.exit2;

/**
 * 问题10：实现一个Car类，该类模拟沿 x 轴行驶的汽车，车行驶需要消耗汽油。
 * 在构造器中指定燃油效率（英里/加仑）。再提供一个按给定英里数行驶的方法，
 * 当给油箱加给定加仑的汽油，得到当前距离原点的距离和当前的燃油水平。
 * 这应该是一个不可变的类吗？为什么？
 */
public class exit2_10 {
    public static void main(String[] args) {
        System.out.println("问题10：实现Car类，模拟沿x轴行驶的汽车");
        System.out.println("------------------------------------");
        
        // 创建一个燃油效率为25英里/加仑的汽车
        Car car = new Car(25);
        System.out.println("创建了一辆燃油效率为25英里/加仑的汽车");
        System.out.println("初始位置: " + car.getPosition() + " 英里");
        System.out.println("初始油量: " + car.getFuelLevel() + " 加仑");
        
        // 加油10加仑
        car.addGas(10);
        System.out.println("\n加油10加仑后：");
        System.out.println("位置: " + car.getPosition() + " 英里");
        System.out.println("油量: " + car.getFuelLevel() + " 加仑");
        
        // 行驶100英里
        car.drive(100);
        System.out.println("\n行驶100英里后：");
        System.out.println("位置: " + car.getPosition() + " 英里");
        System.out.println("油量: " + car.getFuelLevel() + " 加仑");
        System.out.println("消耗了 " + (100 / car.getFuelEfficiency()) + " 加仑汽油");
        
        // 尝试行驶超过剩余油量所能支持的距离
        System.out.println("\n尝试再行驶200英里（超过剩余油量所能支持的距离）：");
        car.drive(200);
        System.out.println("位置: " + car.getPosition() + " 英里");
        System.out.println("油量: " + car.getFuelLevel() + " 加仑");
        
        // 论述是否应该将Car类实现为不可变类
        System.out.println("\n关于Car类是否应该实现为不可变类的讨论：");
        
        System.out.println("Car类不应该是不可变的类，原因如下：");
        System.out.println("1. 汽车的状态会随时间变化：");
        System.out.println("   - 位置会随着行驶而改变");
        System.out.println("   - 油量会因行驶而减少，加油而增加");
        
        System.out.println("2. 不可变类的特点与汽车建模不符：");
        System.out.println("   - 不可变类的所有实例变量都是final的，操作会创建新对象");
        System.out.println("   - 如果将Car实现为不可变类，每次行驶或加油都会创建新Car对象");
        System.out.println("   - 这与现实世界中汽车的概念不符（行驶时不会变成另一辆车）");
        
        System.out.println("3. 性能考虑：");
        System.out.println("   - 对于频繁变化的对象，创建不可变版本会产生大量临时对象");
        System.out.println("   - 这会导致不必要的内存消耗和垃圾收集开销");
        
        System.out.println("4. 对象标识：");
        System.out.println("   - 保持同一辆车的身份很重要，这符合域模型的概念");
        System.out.println("   - 通过修改现有对象而不是创建新对象来反映状态变化更合理");
        
        System.out.println("\n结论：Car类应该是可变的，因为它模拟的是一个随时间变化状态的实体，");
        System.out.println("而不可变设计更适合那些从本质上就不会变化的概念，如日期、颜色或点等。");
    }
}

/**
 * 表示沿x轴行驶的汽车，具有燃油效率、位置和燃油水平。
 */
class Car {
    /**
     * 汽车的燃油效率（英里/加仑）
     */
    private final double fuelEfficiency;
    
    /**
     * 汽车当前位置（原点为0）
     */
    private double position;
    
    /**
     * 汽车当前的燃油水平（加仑）
     */
    private double fuelLevel;
    
    /**
     * 创建一辆新车，指定其燃油效率。
     * 初始位置为0（原点），初始燃油水平为0。
     * 
     * @param fuelEfficiency 燃油效率，单位为英里/加仑
     */
    public Car(double fuelEfficiency) {
        if (fuelEfficiency <= 0) {
            throw new IllegalArgumentException("燃油效率必须为正值");
        }
        this.fuelEfficiency = fuelEfficiency;
        this.position = 0;
        this.fuelLevel = 0;
    }
    
    /**
     * 向汽车油箱中添加指定量的汽油。
     * 
     * @param gas 要添加的汽油量，单位为加仑
     * @throws IllegalArgumentException 如果gas为负值
     */
    public void addGas(double gas) {
        if (gas < 0) {
            throw new IllegalArgumentException("加油量不能为负值");
        }
        fuelLevel += gas;
    }
    
    /**
     * 使车行驶指定的英里数。
     * 如果燃油不足以行驶这么远，则车只行驶到燃油耗尽为止。
     * 
     * @param miles 要行驶的英里数
     * @throws IllegalArgumentException 如果miles为负值
     */
    public void drive(double miles) {
        if (miles < 0) {
            throw new IllegalArgumentException("行驶距离不能为负值");
        }
        
        // 计算可以行驶的最大距离（基于当前燃油水平）
        double maxDistance = fuelLevel * fuelEfficiency;
        
        // 计算实际行驶距离（取最小值）
        double actualDistance = Math.min(miles, maxDistance);
        
        // 更新位置和燃油水平
        position += actualDistance;
        fuelLevel -= actualDistance / fuelEfficiency;
        
        // 确保燃油水平不会出现微小的负值（浮点计算可能导致）
        if (fuelLevel < 0) {
            fuelLevel = 0;
        }
        
        // 如果燃油耗尽而无法完成预期行程，输出警告
        if (actualDistance < miles) {
            System.out.println("警告: 燃油不足，只行驶了 " + actualDistance + " 英里");
        }
    }
    
    /**
     * 获取汽车当前位置（距离原点的距离）。
     * 
     * @return 当前位置，单位为英里
     */
    public double getPosition() {
        return position;
    }
    
    /**
     * 获取当前油箱中的燃油水平。
     * 
     * @return 当前燃油水平，单位为加仑
     */
    public double getFuelLevel() {
        return fuelLevel;
    }
    
    /**
     * 获取汽车的燃油效率。
     * 
     * @return 燃油效率，单位为英里/加仑
     */
    public double getFuelEfficiency() {
        return fuelEfficiency;
    }
} 
