package com.qius.exit3;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题16：完全实现2.7.1小节中的Invoice类，提供一个打印发票的方法和一个构造和打印示例发票的演示程序。
 */
public class exit3_16 {
    public static void main(String[] args) {
        System.out.println("问题16：实现Invoice类及演示程序");
        System.out.println("----------------------------");
        
        // 创建演示发票
        Invoice invoice = createSampleInvoice();
        
        // 打印发票
        invoice.print();
    }
    
    /**
     * 创建一个示例发票，包含多个商品项目
     * 
     * @return 示例发票对象
     */
    public static Invoice createSampleInvoice() {
        System.out.println("创建示例发票...");
        
        // 创建发票对象
        Invoice invoice = new Invoice();
        
        // 添加多个商品项目
        invoice.addItem("笔记本电脑", 1, 5999.00);
        invoice.addItem("无线鼠标", 1, 129.00);
        invoice.addItem("USB-C转接器", 2, 69.50);
        invoice.addItem("保护膜", 1, 35.80);
        
        return invoice;
    }
}

/**
 * 发票类
 * <p>
 * 表示一个包含多个商品项目的发票。
 * 可以添加项目、计算总金额并打印发票内容。
 * </p>
 */
class Invoice {
    private List<Item> items; // 发票中的商品项目列表
    
    /**
     * 构造一个空发票
     */
    public Invoice() {
        items = new ArrayList<>();
    }
    
    /**
     * 向发票中添加一个商品项目
     * 
     * @param description 商品描述
     * @param quantity 商品数量
     * @param unitPrice 商品单价
     */
    public void addItem(String description, int quantity, double unitPrice) {
        Item newItem = new Item(description, quantity, unitPrice);
        items.add(newItem);
    }
    
    /**
     * 计算发票的总金额
     * 
     * @return 发票总金额
     */
    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }
    
    /**
     * 打印完整的发票信息
     */
    public void print() {
        System.out.println("\n===================================");
        System.out.println("            电子发票              ");
        System.out.println("===================================");
        System.out.println("描述                数量    单价    金额");
        System.out.println("-----------------------------------");
        
        // 打印每个商品项目
        for (Item item : items) {
            System.out.printf("%-20s %-7d ¥%-8.2f ¥%.2f\n", 
                    item.getDescription(), 
                    item.getQuantity(), 
                    item.getUnitPrice(), 
                    item.getPrice());
        }
        
        // 打印总计
        System.out.println("-----------------------------------");
        System.out.printf("总计:%31s ¥%.2f\n", "", getTotal());
        System.out.println("===================================");
        System.out.println("          感谢您的惠顾!           ");
        System.out.println("===================================");
    }
    
    /**
     * 获取发票中的所有项目（用于测试）
     * 
     * @return 项目列表
     */
    public List<Item> getItems() {
        return new ArrayList<>(items); // 返回副本，防止外部修改
    }
    
    /**
     * 项目类 - 表示发票中的一个商品项目
     */
    class Item {
        private String description; // 商品描述
        private int quantity;       // 商品数量
        private double unitPrice;   // 商品单价
        
        /**
         * 构造一个商品项目
         * 
         * @param description 商品描述
         * @param quantity 商品数量
         * @param unitPrice 商品单价
         */
        public Item(String description, int quantity, double unitPrice) {
            this.description = description;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
        
        /**
         * 获取商品描述
         * 
         * @return 商品描述
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * 获取商品数量
         * 
         * @return 商品数量
         */
        public int getQuantity() {
            return quantity;
        }
        
        /**
         * 获取商品单价
         * 
         * @return 商品单价
         */
        public double getUnitPrice() {
            return unitPrice;
        }
        
        /**
         * 计算商品项目的总价（数量 × 单价）
         * 
         * @return 商品项目总价
         */
        public double getPrice() {
            return quantity * unitPrice;
        }
    }
} 
