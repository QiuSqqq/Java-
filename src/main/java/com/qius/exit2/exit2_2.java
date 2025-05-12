 package com.qius.exit2;

import java.util.Random;
import java.util.Scanner;

/**
 * 问题2：请思考Scanner类的nextInt方法，它是一个访问器还是修改器？为什么？
 * 那么Random类的nextInt方法呢？
 * 
 * 本程序展示了如何：
 * 1. 分析Scanner.nextInt()方法对Scanner对象状态的影响
 * 2. 分析Random.nextInt()方法对Random对象状态的影响
 * 3. 通过代码示例区分访问器和修改器的概念
 * 4. 讨论方法对对象内部状态的影响与其分类的关系
 */
public class exit2_2 {
    public static void main(String[] args) {
        /**
         * Scanner类的nextInt方法分析
         * - Scanner.nextInt()方法是一个修改器(mutator)
         * - 原因：调用nextInt()会改变Scanner对象的内部状态
         * - 具体来说：
         * * 它会消耗输入流中的数据
         * * 它会移动Scanner的内部位置指针
         * * 它会更改Scanner对象的状态（比如hasNext()的返回值可能改变）
         * * 连续两次调用nextInt()会产生不同的结果（除非输入相同）
         */
        // 代码示例
        String input = "123 456";
        Scanner scanner = new Scanner(input);
        int first = scanner.nextInt(); // 返回123并移动指针"
        System.out.println(first);
        int second = scanner.nextInt(); // 返回456，再次移动指针
        System.out.println(second);
        System.out.println("此时Scanner内部状态已经改变，不能再读取更多整数");

        /**
         * Random类的nextInt方法分析
         * - Random.nextInt()方法也是一个修改器(mutator)
         * - 原因：调用nextInt()会改变Random对象的内部状态
         * - 具体来说：
         * * Random使用伪随机数生成算法，需要维护内部种子状态
         * * 每次调用nextInt()都会修改这个内部种子状态
         * * 这种修改确保了连续调用能生成不同的随机数
         * * 如果不修改内部状态，会一直产生相同的数字
         */
        // 代码示例
        Random random = new Random(42); // 使用固定种子，便于重现结果
        System.out.println("Random(42)第一次调用nextInt(100): " + random.nextInt(100));
        System.out.println("Random(42)第二次调用nextInt(100): " + random.nextInt(100));

        // 重新创建相同种子的Random，再次调用会得到相同序列
        Random random2 = new Random(42);
        System.out.println("新的Random(42)第一次调用: " + random2.nextInt(100)); // 与第一个random的第一次调用结果相同

        /**
         * 总结对比修改器与访问器
         * - 访问器(accessor)：不修改对象状态，只返回信息的方法
         * - 修改器(mutator)：修改对象内部状态的方法
         * - Scanner.nextInt()和Random.nextInt()的比较分析
         */
        System.out.println("\n总结：");
        System.out.println("1. 访问器(accessor)：不修改对象状态，只返回信息的方法");
        System.out.println("2. 修改器(mutator)：修改对象内部状态的方法");
        System.out.println("3. Scanner.nextInt()和Random.nextInt()都是修改器，因为它们都会改变对象的内部状态");

        // 关闭Scanner
        scanner.close();
    }
}