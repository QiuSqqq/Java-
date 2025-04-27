package com.qius.exit3;

import java.util.ArrayList;
import java.util.Random;

/**
 * 问题11：在RandomNumbers类中，提供两个静态randomElement方法，分别从整数的数组或数组列表中获取随机元素
 * （如果数组或数组表为空，则返回零）。为什么不能将这些方法转换为int[]或ArrayList<Integer>？
 */
public class exit3_11 {
    public static void main(String[] args) {
        System.out.println("问题11：实现RandomNumbers类的静态randomElement方法");
        System.out.println("-------------------------------------------");
        
        // 测试从数组中获取随机元素
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("从数组 [1, 2, ..., 10] 中获取随机元素:");
        
        // 多次调用以展示随机性
        for (int i = 0; i < 5; i++) {
            int element = RandomNumbers.randomElement(numbers);
            System.out.println("随机元素: " + element);
        }
        
        // 测试从ArrayList中获取随机元素
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 11; i <= 20; i++) {
            numberList.add(i);
        }
        
        System.out.println("\n从ArrayList [11, 12, ..., 20] 中获取随机元素:");
        for (int i = 0; i < 5; i++) {
            int element = RandomNumbers.randomElement(numberList);
            System.out.println("随机元素: " + element);
        }
        
        // 测试空数组和空ArrayList
        int[] emptyArray = {};
        ArrayList<Integer> emptyList = new ArrayList<>();
        
        System.out.println("\n测试空数组和空ArrayList:");
        System.out.println("空数组返回: " + RandomNumbers.randomElement(emptyArray));
        System.out.println("空ArrayList返回: " + RandomNumbers.randomElement(emptyList));
        
        // 解释为什么不能将这些方法转换为int[]或ArrayList<Integer>
        System.out.println("\n为什么不能将这些方法转换为int[]或ArrayList<Integer>的实例方法？");
        System.out.println("1. 静态方法与实例方法的区别：");
        System.out.println("   - 静态方法属于类，可以直接通过类名调用");
        System.out.println("   - 实例方法属于对象，需要先创建对象才能调用");
        
        System.out.println("\n2. 方法转换为int[]的实例方法的问题：");
        System.out.println("   a) 违反了数组的本质：");
        System.out.println("      - 数组是Java的内置类型，不是类");
        System.out.println("      - 不能向数组类型添加新方法");
        System.out.println("      - 数组类型无法被扩展或修改");
        
        System.out.println("\n   b) 语法上不可行：");
        System.out.println("      - Java不允许为内置类型如int[]添加方法");
        System.out.println("      - 无法修改Java标准库中的类型定义");
        
        System.out.println("\n3. 方法转换为ArrayList<Integer>的实例方法的问题：");
        System.out.println("   a) 修改标准库类：");
        System.out.println("      - ArrayList是Java标准库中的类");
        System.out.println("      - 无法直接修改标准库类来添加新方法");
        
        System.out.println("\n   b) 继承的局限：");
        System.out.println("      - 可以创建ArrayList的子类，但这会改变类型");
        System.out.println("      - 使用时需要显式使用子类类型，而非ArrayList");
        System.out.println("      - 无法对现有的ArrayList实例应用新方法");
        
        System.out.println("\n4. 设计上的考虑：");
        System.out.println("   a) RandomNumbers作为工具类：");
        System.out.println("      - 工具方法通常设计为静态方法，方便调用");
        System.out.println("      - 随机选择是对数组或列表的操作，不是它们的内在行为");
        
        System.out.println("\n   b) 职责分离：");
        System.out.println("      - 数组和ArrayList专注于数据存储和基本操作");
        System.out.println("      - 随机选择等额外功能适合放在独立的工具类中");
        
        System.out.println("\n5. 最佳实践：");
        System.out.println("   - 使用静态工具方法适合处理这种跨多种类型的通用操作");
        System.out.println("   - Java标准库中的Collections和Arrays类也采用这种设计模式");
    }
}

/**
 * 提供从集合中随机选择元素的工具方法。
 */
class RandomNumbers {
    // 用于生成随机数的Random实例
    private static final Random random = new Random();
    
    /**
     * 从整数数组中随机选择一个元素。
     * 
     * @param array 整数数组
     * @return 随机选择的元素，如果数组为空则返回0
     */
    public static int randomElement(int[] array) {
        // 检查数组是否为空
        if (array == null || array.length == 0) {
            return 0; // 如果数组为空，则返回0
        }
        
        // 生成随机索引
        int randomIndex = random.nextInt(array.length);
        
        // 返回随机位置的元素
        return array[randomIndex];
    }
    
    /**
     * 从整数ArrayList中随机选择一个元素。
     * 
     * @param list 整数ArrayList
     * @return 随机选择的元素，如果列表为空则返回0
     */
    public static int randomElement(ArrayList<Integer> list) {
        // 检查列表是否为空
        if (list == null || list.isEmpty()) {
            return 0; // 如果列表为空，则返回0
        }
        
        // 生成随机索引
        int randomIndex = random.nextInt(list.size());
        
        // 返回随机位置的元素
        return list.get(randomIndex);
    }
} 
