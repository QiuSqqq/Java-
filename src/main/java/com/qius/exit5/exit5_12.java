package com.qius.exit5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 问题12：编写一个程序，捕获并处理各种常见的运行时异常
 */
public class exit5_12 {
    
    /**
     * 演示并处理常见的 ArrayIndexOutOfBoundsException
     */
    public static void demonstrateArrayIndexOutOfBounds() {
        System.out.println("\n=== ArrayIndexOutOfBoundsException 演示 ===");
        
        int[] numbers = {1, 2, 3, 4, 5};
        System.out.println("数组: " + Arrays.toString(numbers));
        
        try {
            System.out.println("尝试访问索引10的元素...");
            int value = numbers[10];  // 这将抛出异常
            System.out.println("值: " + value);  // 不会执行到这里
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是检查索引是否在有效范围内");
            
            // 安全的替代方案
            int index = 10;
            if (index >= 0 && index < numbers.length) {
                System.out.println("安全访问: 索引 " + index + " 的值是: " + numbers[index]);
            } else {
                System.out.println("安全访问: 索引 " + index + " 超出范围，数组长度为 " + numbers.length);
            }
        }
    }
    
    /**
     * 演示并处理 NullPointerException
     */
    public static void demonstrateNullPointer() {
        System.out.println("\n=== NullPointerException 演示 ===");
        
        String text = null;
        
        try {
            System.out.println("尝试在null对象上调用方法...");
            int length = text.length();  // 这将抛出异常
            System.out.println("长度: " + length);  // 不会执行到这里
        } catch (NullPointerException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是在调用方法前检查对象是否为null");
            
            // 安全的替代方案
            if (text != null) {
                System.out.println("安全访问: 文本长度是: " + text.length());
            } else {
                System.out.println("安全访问: 文本是null，无法获取长度");
            }
        }
    }
    
    /**
     * 演示并处理 NumberFormatException
     */
    public static void demonstrateNumberFormat() {
        System.out.println("\n=== NumberFormatException 演示 ===");
        
        String invalidNumber = "123abc";
        
        try {
            System.out.println("尝试将 \"" + invalidNumber + "\" 转换为整数...");
            int number = Integer.parseInt(invalidNumber);  // 这将抛出异常
            System.out.println("数字: " + number);  // 不会执行到这里
        } catch (NumberFormatException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是使用正则表达式验证字符串格式或使用try-catch");
            
            // 安全的替代方案
            try {
                String validNumber = "123";
                int number = Integer.parseInt(validNumber);
                System.out.println("安全转换: \"" + validNumber + "\" 转换为整数: " + number);
            } catch (NumberFormatException ex) {
                System.out.println("安全转换: 无法将字符串转换为整数");
            }
        }
    }
    
    /**
     * 演示并处理 ClassCastException
     */
    public static void demonstrateClassCast() {
        System.out.println("\n=== ClassCastException 演示 ===");
        
        Object objectValue = "这是一个字符串";
        
        try {
            System.out.println("尝试将String对象转换为Integer...");
            Integer number = (Integer) objectValue;  // 这将抛出异常
            System.out.println("数字: " + number);  // 不会执行到这里
        } catch (ClassCastException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是在转换前使用instanceof检查对象类型");
            
            // 安全的替代方案
            if (objectValue instanceof Integer) {
                Integer number = (Integer) objectValue;
                System.out.println("安全转换: 值是: " + number);
            } else {
                System.out.println("安全转换: 对象不是Integer类型，实际类型是: " + objectValue.getClass().getName());
            }
        }
    }
    
    /**
     * 演示并处理 ArithmeticException
     */
    public static void demonstrateArithmetic() {
        System.out.println("\n=== ArithmeticException 演示 ===");
        
        try {
            System.out.println("尝试除以零...");
            int result = 10 / 0;  // 这将抛出异常
            System.out.println("结果: " + result);  // 不会执行到这里
        } catch (ArithmeticException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是在执行除法前检查除数是否为零");
            
            // 安全的替代方案
            int divisor = 0;
            if (divisor != 0) {
                System.out.println("安全除法: 10 / " + divisor + " = " + (10 / divisor));
            } else {
                System.out.println("安全除法: 无法执行10 / 0");
            }
        }
    }
    
    /**
     * 演示并处理 IndexOutOfBoundsException
     */
    public static void demonstrateIndexOutOfBounds() {
        System.out.println("\n=== IndexOutOfBoundsException 演示 ===");
        
        List<String> nameList = new ArrayList<>(Arrays.asList("张三", "李四", "王五"));
        System.out.println("列表: " + nameList);
        
        try {
            System.out.println("尝试访问索引5的元素...");
            String name = nameList.get(5);  // 这将抛出异常
            System.out.println("名字: " + name);  // 不会执行到这里
        } catch (IndexOutOfBoundsException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是检查索引是否在有效范围内");
            
            // 安全的替代方案
            int index = 1;
            if (index >= 0 && index < nameList.size()) {
                System.out.println("安全访问: 索引 " + index + " 的值是: " + nameList.get(index));
            } else {
                System.out.println("安全访问: 索引 " + index + " 超出范围，列表大小为 " + nameList.size());
            }
        }
    }
    
    /**
     * 演示并处理 IllegalArgumentException
     */
    public static void demonstrateIllegalArgument() {
        System.out.println("\n=== IllegalArgumentException 演示 ===");
        
        try {
            System.out.println("尝试创建一个负容量的ArrayList...");
            List<String> list = new ArrayList<>(-10);  // 这将抛出异常
            System.out.println("列表: " + list);  // 不会执行到这里
        } catch (IllegalArgumentException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是确保参数值有效");
            
            // 安全的替代方案
            int capacity = -10;
            if (capacity > 0) {
                List<String> list = new ArrayList<>(capacity);
                System.out.println("安全创建: 成功创建容量为 " + capacity + " 的列表");
            } else {
                System.out.println("安全创建: 使用默认构造函数创建列表，因为容量 " + capacity + " 无效");
                List<String> list = new ArrayList<>();
            }
        }
    }
    
    /**
     * 演示并处理 UnsupportedOperationException
     */
    public static void demonstrateUnsupportedOperation() {
        System.out.println("\n=== UnsupportedOperationException 演示 ===");
        
        List<String> fixedList = Arrays.asList("a", "b", "c");
        System.out.println("固定大小列表: " + fixedList);
        
        try {
            System.out.println("尝试向固定大小列表添加元素...");
            fixedList.add("d");  // 这将抛出异常
            System.out.println("修改后的列表: " + fixedList);  // 不会执行到这里
        } catch (UnsupportedOperationException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是使用支持添加操作的列表实现");
            
            // 安全的替代方案
            List<String> modifiableList = new ArrayList<>(fixedList);
            modifiableList.add("d");
            System.out.println("安全操作: 创建可修改列表并添加元素: " + modifiableList);
        }
    }
    
    /**
     * 演示并处理 ConcurrentModificationException
     */
    public static void demonstrateConcurrentModification() {
        System.out.println("\n=== ConcurrentModificationException 演示 ===");
        
        List<String> fruits = new ArrayList<>(Arrays.asList("苹果", "香蕉", "橙子", "葡萄"));
        System.out.println("水果列表: " + fruits);
        
        try {
            System.out.println("尝试在迭代时修改列表...");
            for (String fruit : fruits) {
                System.out.println("处理: " + fruit);
                if (fruit.equals("香蕉")) {
                    fruits.remove(fruit);  // 这将抛出异常
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.err.println("捕获到异常: " + e.getClass().getName());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("正确的做法是使用迭代器的remove方法或在迭代后修改列表");
            
            // 安全的替代方案1：使用迭代器
            System.out.println("安全操作1: 使用迭代器的remove方法");
            List<String> list1 = new ArrayList<>(Arrays.asList("苹果", "香蕉", "橙子", "葡萄"));
            java.util.Iterator<String> iterator = list1.iterator();
            while (iterator.hasNext()) {
                String fruit = iterator.next();
                System.out.println("处理: " + fruit);
                if (fruit.equals("香蕉")) {
                    iterator.remove();  // 安全删除
                }
            }
            System.out.println("修改后的列表: " + list1);
            
            // 安全的替代方案2：创建要删除的项目列表
            System.out.println("安全操作2: 创建要删除的项目列表");
            List<String> list2 = new ArrayList<>(Arrays.asList("苹果", "香蕉", "橙子", "葡萄"));
            List<String> toRemove = new ArrayList<>();
            for (String fruit : list2) {
                System.out.println("处理: " + fruit);
                if (fruit.equals("香蕉")) {
                    toRemove.add(fruit);
                }
            }
            list2.removeAll(toRemove);
            System.out.println("修改后的列表: " + list2);
        }
    }
    
    /**
     * 演示如何处理用户输入中的运行时异常
     */
    public static void handleUserInput() {
        System.out.println("\n=== 处理用户输入中的异常 ===");
        
        Scanner scanner = new Scanner(System.in);
        
        // 安全获取整数输入
        int age = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.print("请输入您的年龄: ");
                age = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.err.println("无效的输入，请输入一个整数。");
                scanner.nextLine();  // 清除错误输入
            }
        }
        
        System.out.println("您输入的年龄是: " + age);
    }
    
    /**
     * 演示链式和嵌套异常处理
     */
    public static void demonstrateChainedExceptions() {
        System.out.println("\n=== 链式和嵌套异常处理 ===");
        
        try {
            System.out.println("尝试执行一系列操作...");
            try {
                Map<String, Integer> map = new HashMap<>();
                Integer value = map.get("key");  // 这将返回null
                System.out.println("值的长度: " + value.toString().length());  // 这将抛出NullPointerException
            } catch (NullPointerException e) {
                System.err.println("内部捕获到NullPointerException");
                // 抛出新的异常，并将原始异常设置为其原因
                throw new IllegalStateException("无法处理map值", e);
            }
        } catch (IllegalStateException e) {
            System.err.println("捕获到外部异常: " + e.getMessage());
            System.err.println("原因: " + e.getCause().getMessage());
            
            // 打印完整的堆栈跟踪
            System.err.println("\n完整的异常堆栈跟踪:");
            e.printStackTrace(System.err);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 运行时异常处理演示 ===");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n选择要演示的运行时异常:");
                System.out.println("1. ArrayIndexOutOfBoundsException");
                System.out.println("2. NullPointerException");
                System.out.println("3. NumberFormatException");
                System.out.println("4. ClassCastException");
                System.out.println("5. ArithmeticException");
                System.out.println("6. IndexOutOfBoundsException");
                System.out.println("7. IllegalArgumentException");
                System.out.println("8. UnsupportedOperationException");
                System.out.println("9. ConcurrentModificationException");
                System.out.println("10. 处理用户输入");
                System.out.println("11. 链式异常");
                System.out.println("0. 退出");
                
                System.out.print("请选择 (0-11): ");
                int choice;
                
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("无效的输入，请输入0-11之间的数字。");
                    scanner.nextLine();  // 清除错误输入
                    continue;
                }
                
                // 消耗掉输入流中的换行符
                scanner.nextLine();
                
                if (choice == 0) {
                    System.out.println("退出程序。");
                    break;
                }
                
                switch (choice) {
                    case 1:
                        demonstrateArrayIndexOutOfBounds();
                        break;
                    case 2:
                        demonstrateNullPointer();
                        break;
                    case 3:
                        demonstrateNumberFormat();
                        break;
                    case 4:
                        demonstrateClassCast();
                        break;
                    case 5:
                        demonstrateArithmetic();
                        break;
                    case 6:
                        demonstrateIndexOutOfBounds();
                        break;
                    case 7:
                        demonstrateIllegalArgument();
                        break;
                    case 8:
                        demonstrateUnsupportedOperation();
                        break;
                    case 9:
                        demonstrateConcurrentModification();
                        break;
                    case 10:
                        handleUserInput();
                        break;
                    case 11:
                        demonstrateChainedExceptions();
                        break;
                    default:
                        System.out.println("无效的选择，请输入0-11之间的数字。");
                        break;
                }
            }
        } finally {
            scanner.close();
        }
    }
} 