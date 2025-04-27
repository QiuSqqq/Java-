package com.qius.exit4;

/**
 * 问题7：定义方法Object add(Object first, Object second)。
 * 如果参数是Number的实例，则将这两个值相加。
 * 如果参数是Boolean的实例，则如果其中一个为真，则返回Boolean.TRUE。
 * 否则，将它们拼接为字符串。
 * 使用instanceof运算符与模式匹配。
 */
public class exit4_7 {
    public static void main(String[] args) {
        // 测试数字加法
        System.out.println("=== 测试数字加法 ===");
        System.out.println("add(10, 20): " + add(10, 20)); // 应该返回30
        System.out.println("add(10.5, 20.7): " + add(10.5, 20.7)); // 应该返回31.2
        System.out.println("add(Integer.valueOf(5), Double.valueOf(10.5)): " + add(Integer.valueOf(5), Double.valueOf(10.5))); // 应该返回15.5
        
        // 测试布尔值逻辑或
        System.out.println("\n=== 测试布尔值逻辑或 ===");
        System.out.println("add(true, false): " + add(true, false)); // 应该返回true
        System.out.println("add(false, false): " + add(false, false)); // 应该返回false
        System.out.println("add(Boolean.TRUE, Boolean.FALSE): " + add(Boolean.TRUE, Boolean.FALSE)); // 应该返回true
        
        // 测试字符串拼接
        System.out.println("\n=== 测试字符串拼接 ===");
        System.out.println("add(\"Hello, \", \"World!\"): " + add("Hello, ", "World!")); // 应该返回"Hello, World!"
        System.out.println("add(\"Java \", 17): " + add("Java ", 17)); // 应该返回"Java 17"
        System.out.println("add(123, \" is a number\"): " + add(123, " is a number")); // 应该返回"123 is a number"
        
        // 测试null值处理
        System.out.println("\n=== 测试null值处理 ===");
        System.out.println("add(null, \"World!\"): " + add(null, "World!")); // 应该返回"nullWorld!"
        System.out.println("add(\"Hello, \", null): " + add("Hello, ", null)); // 应该返回"Hello, null"
        System.out.println("add(null, null): " + add(null, null)); // 应该返回"nullnull"
    }
    
    /**
     * 根据参数类型执行不同的操作：
     * - 如果参数是Number的实例，则将这两个值相加
     * - 如果参数是Boolean的实例，则如果其中一个为真，则返回Boolean.TRUE
     * - 否则，将它们拼接为字符串
     * 
     * 使用模式匹配简化类型检查和转换
     * 
     * @param first 第一个对象
     * @param second 第二个对象
     * @return 根据类型计算的结果
     */
    public static Object add(Object first, Object second) {
        // 先检查两个参数是否都是Number的实例
        if (first instanceof Number firstNum && second instanceof Number secondNum) {
            // 如果两个参数都是Number的实例，则进行加法运算
            
            // 决定结果的类型：如果有双精度浮点数，返回双精度结果
            if (first instanceof Double || second instanceof Double) {
                return firstNum.doubleValue() + secondNum.doubleValue();
            }
            // 如果有单精度浮点数，返回单精度结果
            else if (first instanceof Float || second instanceof Float) {
                return firstNum.floatValue() + secondNum.floatValue();
            }
            // 如果有长整型，返回长整型结果
            else if (first instanceof Long || second instanceof Long) {
                return firstNum.longValue() + secondNum.longValue();
            }
            // 默认返回整型结果
            else {
                return firstNum.intValue() + secondNum.intValue();
            }
        }
        // 检查两个参数是否都是Boolean的实例
        else if (first instanceof Boolean firstBool && second instanceof Boolean secondBool) {
            // 如果两个参数都是Boolean的实例，则进行逻辑或运算
            // 如果其中一个为真，则返回Boolean.TRUE，否则返回Boolean.FALSE
            return firstBool || secondBool ? Boolean.TRUE : Boolean.FALSE;
        }
        // 其他情况，将两个参数转换为字符串并拼接
        else {
            return String.valueOf(first) + String.valueOf(second);
        }
    }
} 