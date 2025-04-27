package com.qius.exit4;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 问题11：编写一个通用的toString方法，该方法使用反射生成一个包含对象所有实例变量的字符串。
 * 如果能处理循环引用，则将获得加分。
 */
public class exit4_11 {
    public static void main(String[] args) {
        // 测试简单对象
        Person person = new Person("张三", 30);
        System.out.println("简单对象:");
        System.out.println(reflectiveToString(person));
        
        // 测试包含数组的对象
        Student student = new Student("李四", 20, new int[] {90, 85, 92});
        System.out.println("\n包含数组的对象:");
        System.out.println(reflectiveToString(student));
        
        // 测试嵌套对象
        Department department = new Department("计算机科学");
        Employee employee = new Employee("王五", 35, department);
        department.setManager(employee); // 设置循环引用
        
        System.out.println("\n包含循环引用的对象:");
        System.out.println(reflectiveToString(employee));
        
        // 测试自引用
        Node node = new Node("根节点");
        node.setNext(new Node("子节点"));
        node.getNext().setNext(node); // 创建循环链表
        
        System.out.println("\n自引用的对象:");
        System.out.println(reflectiveToString(node));
    }
    
    /**
     * 使用反射生成包含对象所有实例变量的字符串表示
     * 
     * @param obj 要转换为字符串的对象
     * @return 包含所有实例变量的字符串表示
     */
    public static String reflectiveToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        
        // 处理循环引用的对象集合
        Set<Object> visited = new HashSet<>();
        StringBuilder result = new StringBuilder();
        
        try {
            buildString(obj, result, visited, 0);
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
        
        return result.toString();
    }
    
    /**
     * 递归构建对象的字符串表示
     * 
     * @param obj 当前处理的对象
     * @param result 结果字符串
     * @param visited 已访问过的对象集合，用于检测循环引用
     * @param depth 当前递归深度，用于缩进
     * @throws IllegalAccessException 如果无法访问字段
     */
    private static void buildString(Object obj, StringBuilder result, Set<Object> visited, int depth) 
            throws IllegalAccessException {
        // 处理null值
        if (obj == null) {
            result.append("null");
            return;
        }
        
        // 处理基本类型和字符串
        Class<?> objClass = obj.getClass();
        if (isPrimitiveOrWrapper(objClass) || obj instanceof String) {
            result.append(formatPrimitiveValue(obj));
            return;
        }
        
        // 处理数组
        if (objClass.isArray()) {
            buildArrayString(obj, result, visited, depth);
            return;
        }
        
        // 检查循环引用
        if (visited.contains(obj)) {
            result.append(objClass.getSimpleName()).append("@").append(Integer.toHexString(System.identityHashCode(obj)))
                  .append(" (循环引用)");
            return;
        }
        
        // 添加到已访问集合
        visited.add(obj);
        
        // 构建对象的字符串表示
        result.append(objClass.getSimpleName()).append(" {");
        
        // 获取所有字段，包括父类的
        List<Field> fields = getAllFields(objClass);
        boolean first = true;
        
        for (Field field : fields) {
            // 跳过静态字段
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            
            // 确保可以访问私有字段
            field.setAccessible(true);
            
            // 添加分隔符
            if (first) {
                first = false;
                result.append("\n");
            } else {
                result.append(",\n");
            }
            
            // 添加缩进
            indent(result, depth + 1);
            
            // 添加字段名
            result.append(field.getName()).append(": ");
            
            // 添加字段值
            buildString(field.get(obj), result, visited, depth + 1);
        }
        
        if (!first) {
            result.append("\n");
            indent(result, depth);
        }
        
        result.append("}");
        
        // 从已访问集合中移除当前对象，允许在其他路径中再次访问
        visited.remove(obj);
    }
    
    /**
     * 构建数组的字符串表示
     */
    private static void buildArrayString(Object array, StringBuilder result, Set<Object> visited, int depth) 
            throws IllegalAccessException {
        result.append("[");
        boolean first = true;
        
        int length = getArrayLength(array);
        
        for (int i = 0; i < length; i++) {
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }
            
            Object element = getArrayElement(array, i);
            buildString(element, result, visited, depth);
        }
        
        result.append("]");
    }
    
    /**
     * 获取数组的长度
     */
    private static int getArrayLength(Object array) {
        if (array instanceof Object[]) return ((Object[]) array).length;
        if (array instanceof boolean[]) return ((boolean[]) array).length;
        if (array instanceof byte[]) return ((byte[]) array).length;
        if (array instanceof char[]) return ((char[]) array).length;
        if (array instanceof short[]) return ((short[]) array).length;
        if (array instanceof int[]) return ((int[]) array).length;
        if (array instanceof long[]) return ((long[]) array).length;
        if (array instanceof float[]) return ((float[]) array).length;
        if (array instanceof double[]) return ((double[]) array).length;
        return 0;
    }
    
    /**
     * 获取数组的元素
     */
    private static Object getArrayElement(Object array, int index) {
        if (array instanceof Object[]) return ((Object[]) array)[index];
        if (array instanceof boolean[]) return ((boolean[]) array)[index];
        if (array instanceof byte[]) return ((byte[]) array)[index];
        if (array instanceof char[]) return ((char[]) array)[index];
        if (array instanceof short[]) return ((short[]) array)[index];
        if (array instanceof int[]) return ((int[]) array)[index];
        if (array instanceof long[]) return ((long[]) array)[index];
        if (array instanceof float[]) return ((float[]) array)[index];
        if (array instanceof double[]) return ((double[]) array)[index];
        return null;
    }
    
    /**
     * 判断类是否为基本类型或其包装类
     */
    private static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
               clazz == Boolean.class ||
               clazz == Character.class ||
               clazz == Byte.class ||
               clazz == Short.class ||
               clazz == Integer.class ||
               clazz == Long.class ||
               clazz == Float.class ||
               clazz == Double.class;
    }
    
    /**
     * 格式化基本类型或其包装类的值
     */
    private static String formatPrimitiveValue(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }
    
    /**
     * 获取类的所有字段，包括父类的字段
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        
        // 收集当前类及所有父类的字段
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }
        
        return fields;
    }
    
    /**
     * 添加缩进到字符串
     */
    private static void indent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append("    "); // 4个空格的缩进
        }
    }
}

/**
 * 测试类：人
 */
class Person {
    private String name;
    private int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
}

/**
 * 测试类：学生，继承自人
 */
class Student extends Person {
    private int[] scores; // 数组字段
    
    public Student(String name, int age, int[] scores) {
        super(name, age);
        this.scores = scores;
    }
    
    public int[] getScores() {
        return scores;
    }
}

/**
 * 测试类：部门
 */
class Department {
    private String name;
    private Employee manager; // 循环引用：部门引用员工，员工引用部门
    
    public Department(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Employee getManager() {
        return manager;
    }
    
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

/**
 * 测试类：员工，继承自人
 */
class Employee extends Person {
    private Department department; // 循环引用：员工引用部门，部门引用员工
    
    public Employee(String name, int age, Department department) {
        super(name, age);
        this.department = department;
    }
    
    public Department getDepartment() {
        return department;
    }
}

/**
 * 测试类：节点，用于测试自引用
 */
class Node {
    private String value;
    private Node next; // 自引用
    
    public Node(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public Node getNext() {
        return next;
    }
    
    public void setNext(Node next) {
        this.next = next;
    }
} 