package com.qius.exit1;

/**
 * 问题10：编写一个程序，通过生成一个随机的long值并将其以36进制输出，
 * 生成一个随机的字母和数字字符串。
 * 
 * 本程序展示了如何：
 * 1. 使用Random类生成随机的long值
 * 2. 将long值转换为36进制字符串表示
 * 3. 实现一个自定义方法生成指定长度的随机字母数字字符串
 */
import java.util.Random;

public class exit1_10 {
    public static void main(String[] args) {
        /**
         * 介绍36进制的组成和对应关系
         * - 包括数字部分(0-9)和字母部分(a-z)
         * - 共36个字符，对应值为0-35
         */
        // 创建Random对象
        Random random = new Random();
        
        // 解释36进制
        System.out.println("36进制由数字0-9和字母a-z组成，共36个字符：");
        System.out.println("数字部分: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9");
        System.out.println("字母部分: a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z");
        System.out.println("对应值为: 0, 1, 2, ... , 35");
        System.out.println();
        
        /**
         * 生成随机字符串的第一种方式：使用Random.nextLong()和Long.toString()
         * - 生成随机long值
         * - 转换为36进制字符串
         * - 去除可能的负号
         */
        // 生成几个随机的字符串
        System.out.println("生成5个随机的字母和数字字符串：");
        
        for (int i = 1; i <= 5; i++) {
            // 生成一个随机的long值
            long randomValue = random.nextLong();
            
            // 将随机值转换为36进制字符串
            String randomString = Long.toString(randomValue, 36);
            
            // 去掉可能的负号
            if (randomString.startsWith("-")) {
                randomString = randomString.substring(1);
            }
            
            // 打印结果
            System.out.println("随机字符串 " + i + ": " + randomString);
            System.out.println("长度: " + randomString.length());
            System.out.println();
        }
        
        /**
         * 生成随机字符串的第二种方式：使用自定义方法控制长度
         * - 调用自定义方法生成固定长度的字符串
         * - 展示多个生成结果
         */
        // 演示生成固定长度的随机字符串
        System.out.println("生成固定长度的随机字符串（长度为10）：");
        for (int i = 1; i <= 3; i++) {
            String fixedLengthString = generateRandomString(10);
            System.out.println("固定长度随机字符串 " + i + ": " + fixedLengthString);
        }
    }
    
    /**
     * 生成指定长度的随机字母和数字字符串
     * - 逐个生成随机字符并构建字符串
     * - 使用StringBuilder提高字符串构建效率
     * - 确保生成的字符串具有指定的长度
     */
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            // 生成一个0到35之间的随机数
            int randomValue = random.nextInt(36);
            
            // 将随机数转换为字符
            char c;
            if (randomValue < 10) {
                // 0-9转换为字符'0'-'9'
                c = (char) ('0' + randomValue);
            } else {
                // 10-35转换为字符'a'-'z'
                c = (char) ('a' + (randomValue - 10));
            }
            
            // 添加到StringBuilder
            sb.append(c);
        }
        
        return sb.toString();
    }
} 