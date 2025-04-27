package com.qius.exit4;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 问题13：编写"Hello, World"程序，使用反射查找java.lang.System的out字段，
 * 并使用invoke调用println方法。
 */
public class exit4_13 {
    public static void main(String[] args) {
        try {
            // 直接方式打印（常规方式）
            System.out.println("常规方式打印: Hello, World!");
            
            // 使用反射方式打印
            reflectivePrint("使用反射方式打印: Hello, World!");
            
            // 更进一步：获取不同的方法重载版本并调用
            reflectivePrintWithDifferentTypes();
            
        } catch (Exception e) {
            System.err.println("发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 使用反射查找System.out字段并调用println方法
     * 
     * @param message 要打印的消息
     * @throws Exception 如果反射操作失败
     */
    public static void reflectivePrint(String message) throws Exception {
        // 1. 获取System类
        Class<?> systemClass = System.class;
        
        // 2. 获取out字段
        Field outField = systemClass.getDeclaredField("out");
        
        // 3. 获取out字段的值（PrintStream对象）
        PrintStream out = (PrintStream) outField.get(null); // 静态字段，所以传入null
        
        // 4. 获取PrintStream类的println方法（接受String参数的版本）
        Method printlnMethod = PrintStream.class.getMethod("println", String.class);
        
        // 5. 调用println方法
        printlnMethod.invoke(out, message);
    }
    
    /**
     * 使用反射方式测试PrintStream的不同println重载方法
     * 
     * @throws Exception 如果反射操作失败
     */
    public static void reflectivePrintWithDifferentTypes() throws Exception {
        // 获取System.out
        Field outField = System.class.getDeclaredField("out");
        PrintStream out = (PrintStream) outField.get(null);
        
        System.out.println("\n=== 使用反射调用不同类型参数的println方法 ===");
        
        // 1. 调用无参数的println方法（打印空行）
        Method printlnNoArgs = PrintStream.class.getMethod("println");
        printlnNoArgs.invoke(out);
        System.out.println("↑ 上面是反射调用无参数的println方法（打印空行）");
        
        // 2. 调用接受int参数的println方法
        Method printlnInt = PrintStream.class.getMethod("println", int.class);
        printlnInt.invoke(out, 42);
        System.out.println("↑ 上面是反射调用println(int)方法");
        
        // 3. 调用接受boolean参数的println方法
        Method printlnBoolean = PrintStream.class.getMethod("println", boolean.class);
        printlnBoolean.invoke(out, true);
        System.out.println("↑ 上面是反射调用println(boolean)方法");
        
        // 4. 调用接受char参数的println方法
        Method printlnChar = PrintStream.class.getMethod("println", char.class);
        printlnChar.invoke(out, 'A');
        System.out.println("↑ 上面是反射调用println(char)方法");
        
        // 5. 调用接受double参数的println方法
        Method printlnDouble = PrintStream.class.getMethod("println", double.class);
        printlnDouble.invoke(out, 3.14159);
        System.out.println("↑ 上面是反射调用println(double)方法");
        
        // 6. 调用接受Object参数的println方法
        Method printlnObject = PrintStream.class.getMethod("println", Object.class);
        printlnObject.invoke(out, new Person("张三", 30));
        System.out.println("↑ 上面是反射调用println(Object)方法");
    }
    
    /**
     * 探索PrintStream类中的所有方法
     * 
     * @throws Exception 如果反射操作失败
     */
    public static void explorePrintStreamMethods() throws Exception {
        System.out.println("\n=== PrintStream类的所有公共方法 ===");
        Method[] methods = PrintStream.class.getMethods();
        
        // 找出所有的println方法
        for (Method method : methods) {
            if (method.getName().equals("println")) {
                System.out.println(method);
            }
        }
    }
} 