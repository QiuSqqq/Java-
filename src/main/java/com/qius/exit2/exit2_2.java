package com.qius.exit2;

import java.util.Random;

/**
 * 问题2：请思考Scanner类的nextInt方法，它是一个访问器还是修改器？为什么？
 * 那么Random类的nextInt方法呢？
 */
public class exit2_2 {
    public static void main(String[] args) {
        System.out.println("问题2：Scanner类和Random类的nextInt方法分析");
        System.out.println("-------------------------------------------");
        
        // 1. Scanner类的nextInt方法分析
        System.out.println("1. Scanner类的nextInt方法：");
        System.out.println("   - Scanner.nextInt()方法是一个修改器(mutator)");
        System.out.println("   - 原因：调用nextInt()会改变Scanner对象的内部状态");
        System.out.println("   - 具体来说：");
        System.out.println("     * 它会消耗输入流中的数据");
        System.out.println("     * 它会移动Scanner的内部位置指针");
        System.out.println("     * 它会更改Scanner对象的状态（比如hasNext()的返回值可能改变）");
        System.out.println("     * 连续两次调用nextInt()会产生不同的结果（除非输入相同）");
        
        // 代码示例
        System.out.println("\n   代码示例演示：");
        System.out.println("   String input = \"123 456\";");
        System.out.println("   Scanner scanner = new Scanner(input);");
        System.out.println("   int first = scanner.nextInt(); // 返回123并移动指针");
        System.out.println("   int second = scanner.nextInt(); // 返回456，再次移动指针");
        System.out.println("   // 此时Scanner内部状态已经改变，不能再读取更多整数");
        
        // 2. Random类的nextInt方法分析
        System.out.println("\n2. Random类的nextInt方法：");
        System.out.println("   - Random.nextInt()方法也是一个修改器(mutator)");
        System.out.println("   - 原因：调用nextInt()会改变Random对象的内部状态");
        System.out.println("   - 具体来说：");
        System.out.println("     * Random使用伪随机数生成算法，需要维护内部种子状态");
        System.out.println("     * 每次调用nextInt()都会修改这个内部种子状态");
        System.out.println("     * 这种修改确保了连续调用能生成不同的随机数");
        System.out.println("     * 如果不修改内部状态，会一直产生相同的数字");
        
        // 代码示例
        System.out.println("\n   代码示例演示：");
        System.out.println("   Random random = new Random(42); // 固定种子为42");
        System.out.println("   int num1 = random.nextInt(100); // 产生第一个随机数，同时修改内部种子");
        System.out.println("   int num2 = random.nextInt(100); // 产生不同的第二个随机数");
        System.out.println("   // 如果nextInt不修改内部状态，num1和num2将总是相同");
        
        // 实际演示
        System.out.println("\n实际演示：");
        Random random = new Random(42); // 使用固定种子，便于重现结果
        System.out.println("Random(42)第一次调用nextInt(100): " + random.nextInt(100));
        System.out.println("Random(42)第二次调用nextInt(100): " + random.nextInt(100));
        
        // 重新创建相同种子的Random，再次调用会得到相同序列
        Random random2 = new Random(42);
        System.out.println("新的Random(42)第一次调用: " + random2.nextInt(100)); // 与第一个random的第一次调用结果相同
        
        // 总结
        System.out.println("\n总结：");
        System.out.println("1. 访问器(accessor)：不修改对象状态，只返回信息的方法");
        System.out.println("2. 修改器(mutator)：修改对象内部状态的方法");
        System.out.println("3. Scanner.nextInt()和Random.nextInt()都是修改器，因为它们都会改变对象的内部状态");
    }
} 
