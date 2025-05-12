package com.qius.exit2;

import java.time.LocalDate;

/**
 * 问题1：更改日历的打印程序，把星期天作为一周的第一天。
 * 在末尾打印一个换行符（但只能打印一个换行符）。
 * 
 * 本程序展示了如何：
 * 1. 获取当前日期并找到当月第一天
 * 2. 调整日历显示，使星期天成为一周的第一天
 * 3. 打印格式化的日历，并确保末尾只有一个换行符
 */
public class exit2_1 {
    public static void main(String[] args) {
        /**
         * 获取当前日期信息并计算当月第一天是星期几
         * - 使用LocalDate获取日期信息
         * - 调整星期计算，使星期天为一周的第一天
         */
        // 获取当前日期
        LocalDate date = LocalDate.now();
        
        // 获取当前月份的第一天
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        
        // 获取当月第一天是星期几（注意：Java中DayOfWeek.MONDAY是1，SUNDAY是7）
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        // 调整使星期天为一周的第一天（星期天的值变为0，其他天+1）
        dayOfWeek = dayOfWeek % 7;
        
        // 获取当月的总天数
        int daysInMonth = firstOfMonth.lengthOfMonth();
        
        /**
         * 打印日历表头信息
         * - 显示当前月份和年份
         * - 打印星期标题，从星期天开始
         */
        // 打印月份和年份
        System.out.println(firstOfMonth.getMonth() + " " + firstOfMonth.getYear());
        
        // 打印星期几的标题，使星期天为第一天
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");
        
        /**
         * 打印日历内容
         * - 首先打印正确数量的前导空格
         * - 按照格式打印每一天，并在适当位置换行
         * - 确保末尾只有一个换行符
         */
        // 打印前导空格
        for (int i = 0; i < dayOfWeek; i++) {
            System.out.print("    ");
        }
        
        // 打印日期
        for (int day = 1; day <= daysInMonth; day++) {
            // 右对齐打印日期
            System.out.printf("%3d ", day);
            
            // 计算当前是星期几（星期天为0）
            int weekDay = (dayOfWeek + day - 1) % 7;
            
            // 如果是星期六（6），则换行
            if (weekDay == 6) {
                System.out.println();
            }
        }
        
        // 确保末尾只有一个换行符
        if ((dayOfWeek + daysInMonth - 1) % 7 != 6) {
            // 如果最后一天不是星期六，则需要添加一个换行符
            System.out.println();
        }
    }
} 
