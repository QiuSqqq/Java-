package com.qius.exit5;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 问题6：实现处理NullPointerExceptions的示例程序
 * 展示如何避免和处理空指针异常
 */
public class exit5_6 {
    
    private String name;
    private ArrayList<String> items;
    
    /**
     * 使用可能为null的值创建对象的构造函数
     * @param name 名称，可能为null
     */
    public exit5_6(String name) {
        // 使用三元运算符避免将null赋值给name
        this.name = (name != null) ? name : "默认名称";
        
        // 确保items列表不为null
        this.items = new ArrayList<>();
    }
    
    /**
     * 添加项目到列表中，安全处理可能为null的输入
     * @param item 要添加的项目
     * @return 是否成功添加
     */
    public boolean addItem(String item) {
        // 检查参数是否为null
        if (item == null) {
            System.out.println("警告: 尝试添加null项目，已忽略");
            return false;
        }
        
        return items.add(item);
    }
    
    /**
     * 使用Objects.requireNonNull确保参数不为null
     * @param itemName 项目名称，不能为null
     * @throws NullPointerException 如果参数为null
     */
    public void processItem(String itemName) {
        // 使用Objects.requireNonNull验证参数
        Objects.requireNonNull(itemName, "项目名称不能为null");
        
        // 处理项目...
        System.out.println("处理项目: " + itemName);
    }
    
    /**
     * 使用Optional处理可能返回null的情况
     * @param index 要获取的项目索引
     * @return 项目，如果索引无效则返回默认值
     */
    public String getItemSafely(int index) {
        // 检查索引是否有效
        if (index < 0 || index >= items.size()) {
            return "未找到项目";
        }
        
        // 获取项目并检查是否为null
        String item = items.get(index);
        return (item != null) ? item : "空项目";
    }
    
    /**
     * 使用防御性检查避免NPE
     * @param obj 要操作的对象，可能为null
     * @return 处理结果
     */
    public static String processObject(Object obj) {
        // 防御性检查
        if (obj == null) {
            return "对象为null，无法处理";
        }
        
        // 安全地调用toString()
        return "处理结果: " + obj.toString();
    }
    
    /**
     * 处理可能抛出NullPointerException的代码
     * @param value 要处理的值，可能为null
     */
    public static void handleNullPointerException(String value) {
        try {
            // 尝试对可能为null的值调用方法
            int length = value.length();
            System.out.println("字符串长度: " + length);
            
        } catch (NullPointerException e) {
            // 捕获并处理NullPointerException
            System.err.println("捕获到NullPointerException: " + e.getMessage());
            System.err.println("尝试访问null对象的方法或属性");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== NullPointerException 处理示例 ===\n");
        
        // 1. 使用构造函数处理null
        System.out.println("1. 构造函数处理null值:");
        exit5_6 example1 = new exit5_6(null);
        exit5_6 example2 = new exit5_6("测试对象");
        System.out.println("example1 名称: " + example1.name);
        System.out.println("example2 名称: " + example2.name);
        System.out.println();
        
        // 2. 添加项目时处理null
        System.out.println("2. 添加项目时处理null:");
        example1.addItem("项目1");
        example1.addItem(null);
        example1.addItem("项目2");
        System.out.println("项目列表: " + example1.items);
        System.out.println();
        
        // 3. 使用Objects.requireNonNull
        System.out.println("3. 使用Objects.requireNonNull:");
        try {
            example1.processItem("有效项目");
            example1.processItem(null); // 会抛出异常
        } catch (NullPointerException e) {
            System.err.println("捕获到异常: " + e.getMessage());
        }
        System.out.println();
        
        // 4. 安全获取项目
        System.out.println("4. 安全获取项目:");
        System.out.println("索引0的项目: " + example1.getItemSafely(0));
        System.out.println("索引999的项目: " + example1.getItemSafely(999));
        System.out.println();
        
        // 5. 防御性检查
        System.out.println("5. 使用防御性检查:");
        System.out.println(processObject("测试字符串"));
        System.out.println(processObject(null));
        System.out.println();
        
        // 6. 捕获NullPointerException
        System.out.println("6. 捕获NullPointerException:");
        handleNullPointerException("测试字符串");
        handleNullPointerException(null);
    }
} 