package com.qius.exit1;

/**
 * 问题18：编写一个程序，打印一组彩票号码，选择1～49的6个不同的数字。为了选择6个不同的数
 * 字，请从填充由1到49的数组列表开始，然后随机选择一个索引并删除该元素。重复6次，最后
 * 按顺序打印结果。
 * 
 * 本程序展示了如何：
 * 1. 使用ArrayList创建和管理动态数字池
 * 2. 通过随机索引选择并删除元素来实现不重复抽样
 * 3. 使用Collections.sort()对抽取的数字进行排序
 * 4. 以格式化方式展示彩票号码
 */

import java.util.*;

public class exit1_18 {
    public static void main(String[] args) {
        /**
         * 初始化程序并创建用户交互界面
         * - 创建Scanner对象读取用户输入
         * - 显示程序说明
         * - 设置循环生成多组彩票号码
         */
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 欢迎信息
        System.out.println("彩票号码生成器 - 从1到49中随机选择6个不同的数字");
        
        // 循环生成多组彩票号码
        boolean continueGenerating = true;
        while (continueGenerating) {
            /**
             * 生成并展示彩票号码
             * - 调用generateLotteryNumbers方法获取一组彩票号码
             * - 使用printLotteryNumbers方法格式化输出结果
             */
            // 生成一组彩票号码
            List<Integer> lotteryNumbers = generateLotteryNumbers();
            
            // 打印结果
            System.out.println("\n生成的彩票号码是：");
            printLotteryNumbers(lotteryNumbers);
            
            // 询问用户是否要生成另一组号码
            System.out.print("\n是否要生成另一组彩票号码？(y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            continueGenerating = answer.equals("y") || answer.equals("yes");
        }
        
        // 关闭Scanner
        scanner.close();
        System.out.println("程序已退出。");
    }
    
    /**
     * 生成一组彩票号码（6个从1到49的不同随机数）
     * - 创建包含1-49的数字池
     * - 随机选择并移除数字
     * - 记录并展示选择过程
     * 
     * @return 包含6个不同随机数的列表
     */
    private static List<Integer> generateLotteryNumbers() {
        // 创建一个包含1到49的ArrayList
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }
        
        // 打印初始列表（可选）
        System.out.println("初始数字池（1-49）大小: " + numbers.size());
        
        // 创建Random对象
        Random random = new Random();
        
        // 创建一个列表来存储选中的6个数字
        ArrayList<Integer> selectedNumbers = new ArrayList<>();
        
        // 选择6个不同的数字
        System.out.println("\n选择过程：");
        for (int i = 0; i < 6; i++) {
            // 计算剩余数字的数量
            int remainingCount = numbers.size();
            
            // 随机选择一个索引
            int randomIndex = random.nextInt(remainingCount);
            
            // 获取该索引处的数字
            int selectedNumber = numbers.get(randomIndex);
            
            // 将选中的数字添加到结果列表
            selectedNumbers.add(selectedNumber);
            
            // 从原始列表中删除已选择的数字
            numbers.remove(randomIndex);
            
            // 打印每一步的选择过程
            System.out.println("第 " + (i + 1) + " 次选择: 从剩余的 " + remainingCount + 
                              " 个数字中选择了索引 " + randomIndex + " 处的数字 " + selectedNumber);
        }
        
        // 最后池中应该还剩43个数字
        System.out.println("\n选择完成后数字池大小: " + numbers.size());
        
        // 对选中的数字进行排序
        Collections.sort(selectedNumbers);
        
        // 返回选中的6个数字
        return selectedNumbers;
    }
    
    /**
     * 以格式化的方式打印彩票号码
     * - 验证输入的有效性
     * - 使用printf格式化输出
     * - 添加适当的分隔符
     * 
     * @param numbers 要打印的彩票号码列表
     */
    private static void printLotteryNumbers(List<Integer> numbers) {
        // 对数字进行基本验证
        if (numbers == null || numbers.size() != 6) {
            System.out.println("错误：无效的彩票号码列表");
            return;
        }
        
        // 打印格式化的彩票号码
        System.out.print("按顺序排列的号码: ");
        for (int i = 0; i < numbers.size(); i++) {
            // 为每个数字添加前导零（如果需要）
            System.out.printf("%02d", numbers.get(i));
            
            // 除了最后一个数字外，在每个数字后添加空格
            if (i < numbers.size() - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();
    }
} 
