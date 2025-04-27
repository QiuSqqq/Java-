package com.qius.exit1; /**
 * 问题18：编写一个程序，打印一组彩票号码，选择1～49的6个不同的数字。为了选择6个不同的数
 * 字，请从填充由1到49的数组列表开始，然后随机选择一个索引并删除该元素。重复6次，最后
 * 按顺序打印结果。
 */

import java.util.*;

public class exit1_18 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        // 欢迎信息
        System.out.println("彩票号码生成器 - 从1到49中随机选择6个不同的数字");
        
        // 循环生成多组彩票号码
        boolean continueGenerating = true;
        while (continueGenerating) {
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