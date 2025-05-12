package com.qius.exit2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 问题4：为什么不能通过一个Java方法实现交换两个int类型变量内容的功能？
 * 编写一个方法来交换两个IntHolder对象的内容（请在API文档中查找这个相当晦涩的类）。
 * 可以交换两个Integer对象的内容吗？
 *
 * 本程序展示了如何：
 * 1. 探索Java的值传递机制及其对基本类型交换的限制
 * 2. 实现可变对象内容的交换（使用AtomicInteger和自定义IntHolder）
 * 3. 分析不可变对象（如Integer）的内容交换限制
 * 4. 对比不同对象类型在交换操作中的行为差异
 */
public class exit2_4 {
    public static void main(String[] args) {

        // 1. 尝试交换两个int类型变量
        System.out.println("1. 尝试交换两个int类型变量:");
        int a = 10;
        int b = 20;
        System.out.println("   交换前: a = " + a + ", b = " + b);

        // 调用交换方法
        swapInts(a, b);
        System.out.println("   调用swapInts()后: a = " + a + ", b = " + b + " (并未交换)");

        System.out.println("\n   原因:");
        System.out.println("   - Java中的基本类型（如int）是按值传递的");
        System.out.println("   - 传递给方法的是变量值的副本，而不是变量本身");
        System.out.println("   - 方法内的交换只影响这些副本，不影响原始变量");
        System.out.println("   - 方法结束后，局部副本被销毁，原始变量保持不变");

        // 2. 交换两个IntHolder对象的内容
        System.out.println("\n2. 交换两个IntHolder对象的内容:");
        // 使用java.util.concurrent.atomic.AtomicInteger代替IntHolder
        // IntHolder是一个晦涩的类，现代Java开发中很少使用，AtomicInteger功能类似且更常用
        AtomicInteger holder1 = new AtomicInteger(10);
        AtomicInteger holder2 = new AtomicInteger(20);

        System.out.println("   交换前: holder1 = " + holder1.get() + ", holder2 = " + holder2.get());

        // 调用交换方法
        swapAtomicIntegers(holder1, holder2);
        System.out.println("   调用swapAtomicIntegers()后: holder1 = " + holder1.get() + ", holder2 = " + holder2.get() + " (成功交换)");

        System.out.println("\n   成功原因:");
        System.out.println("   - 对象引用在Java中是按引用传递的");
        System.out.println("   - 传递给方法的是对象引用（内存地址）而不是对象副本");
        System.out.println("   - 方法内可以通过这些引用修改原始对象的内容");
        System.out.println("   - AtomicInteger提供了set()方法来修改其内部值");

        // 3. 尝试交换两个Integer对象的内容
        System.out.println("\n3. 尝试交换两个Integer对象的内容:");
        Integer i1 = 30;
        Integer i2 = 40;

        System.out.println("   交换前: i1 = " + i1 + ", i2 = " + i2);

        // 调用交换方法
        swapIntegers(i1, i2);
        System.out.println("   调用swapIntegers()后: i1 = " + i1 + ", i2 = " + i2 + " (并未交换)");

        System.out.println("\n   原因:");
        System.out.println("   - 虽然Integer是对象，但它是不可变的(immutable)");
        System.out.println("   - Integer没有提供修改其内部值的方法");
        System.out.println("   - 每次给Integer赋新值，都会创建新的Integer对象");
        System.out.println("   - 方法内部的交换只改变了局部引用，原始引用不变");

        // 4. 使用自定义的IntHolder类
        System.out.println("\n4. 使用自定义的IntHolder类交换内容:");
        IntHolder myHolder1 = new IntHolder(50);
        IntHolder myHolder2 = new IntHolder(60);

        System.out.println("   交换前: myHolder1 = " + myHolder1.value + ", myHolder2 = " + myHolder2.value);

        // 调用交换方法
        swapIntHolders(myHolder1, myHolder2);
        System.out.println("   调用swapIntHolders()后: myHolder1 = " + myHolder1.value + ", myHolder2 = " + myHolder2.value + " (成功交换)");

        // 总结
        System.out.println("\n总结:");
        System.out.println("1. Java中基本类型是按值传递的，方法无法交换原始变量的值");
        System.out.println("2. 可变对象（如AtomicInteger, IntHolder）可以通过方法交换内容，因为可以修改对象状态");
        System.out.println("3. 不可变对象（如Integer）不能交换内容，因为其状态不能修改");
        System.out.println("4. 如果要交换两个int值，可以：");
        System.out.println("   - 使用包装类如AtomicInteger或IntHolder");
        System.out.println("   - 将值放入数组，并传递数组");
        System.out.println("   - 创建一个自定义的可变包装类");
        System.out.println("   - 或者直接在调用代码处使用临时变量进行交换");
    }

    /**
     * 尝试交换两个int值（这不会成功）
     * 这个方法演示了为什么无法在Java中交换基本类型变量的内容
     */
    public static void swapInts(int x, int y) {
        System.out.println("   swapInts方法内部 - 交换前: x = " + x + ", y = " + y);

        int temp = x;
        x = y;
        y = temp;

        System.out.println("   swapInts方法内部 - 交换后: x = " + x + ", y = " + y);
        // 方法结束时，x和y的新值会丢失，不会影响调用者的变量
    }

    /**
     * 交换两个AtomicInteger对象的内容
     * 这个方法成功地交换了两个对象的内部值
     */
    public static void swapAtomicIntegers(AtomicInteger holder1, AtomicInteger holder2) {
        int temp = holder1.get();
        holder1.set(holder2.get());
        holder2.set(temp);
        // 方法结束时，两个AtomicInteger对象的值已经被交换
    }

    /**
     * 尝试交换两个Integer对象的内容（这不会成功）
     * 这个方法演示了为什么无法交换不可变对象的内容
     */
    public static void swapIntegers(Integer a, Integer b) {
        System.out.println("   swapIntegers方法内部 - 交换前: a = " + a + ", b = " + b);

        Integer temp = a;
        a = b;
        b = temp;

        System.out.println("   swapIntegers方法内部 - 交换后: a = " + a + ", b = " + b);
        // 方法结束时，a和b的新引用会丢失，不会影响调用者的变量
    }

    /**
     * 自定义的IntHolder类，类似于Java中的IntHolder
     * 现代Java开发很少使用IntHolder类，我们通常使用AtomicInteger或自定义类
     */
    static class IntHolder {
        public int value;

        public IntHolder(int value) {
            this.value = value;
        }
    }

    /**
     * 交换两个自定义IntHolder对象的内容
     */
    public static void swapIntHolders(IntHolder holder1, IntHolder holder2) {
        int temp = holder1.value;
        holder1.value = holder2.value;
        holder2.value = temp;
    }
}