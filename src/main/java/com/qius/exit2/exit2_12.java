package com.qius.exit2;

// 静态导入System和LocalDate类的静态成员

import java.time.LocalDate;
import java.time.Month;

import static java.lang.System.out;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

/**
 * 问题12：重写Cal类，静态导入System和LocalDate类。
 */
public class exit2_12 {
    public static void main(String[] args) {
        out.println("问题12：重写Cal类，使用静态导入");
        out.println("---------------------------");
        
        // 创建Cal对象并打印当前月份的日历
        Cal calendar = new Cal();
        calendar.printCurrentMonthCalendar();
        
        // 解释静态导入的用法
        out.println("\n关于静态导入的说明：");
        out.println("1. 'import static java.lang.System.*' 导入System类的所有静态成员");
        out.println("   - 可以直接使用out而不是System.out");
        out.println("   - 可以直接使用err而不是System.err");
        out.println("   - 可以直接调用exit()等System类的静态方法");
        
        out.println("\n2. 'import static java.time.LocalDate.*' 导入LocalDate类的所有静态成员");
        out.println("   - 可以直接使用now()而不是LocalDate.now()");
        out.println("   - 可以直接使用of()而不是LocalDate.of()");
        out.println("   - 可以直接访问MIN、MAX等静态常量");
        
        out.println("\n静态导入的注意事项：");
        out.println("1. 优点：可以减少代码中的重复类名前缀，使代码更简洁");
        out.println("2. 缺点：可能导致名称冲突和代码可读性降低");
        out.println("3. 最佳实践：只在确实需要频繁使用某个类的静态成员时使用静态导入");
    }
}

/**
 * Cal类 - 用于打印日历的实用工具类。
 * 使用静态导入简化了代码。
 */
class Cal {
    /**
     * 打印当前月份的日历。
     */
    public void printCurrentMonthCalendar() {
        // 使用静态导入的now()方法获取当前日期
        LocalDate date = now();
        printMonthCalendar(date);
    }
    
    /**
     * 打印指定日期所在月份的日历。
     * 
     * @param date 指定日期
     */
    public void printMonthCalendar(LocalDate date) {
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        
        // 打印月份和年份标题
        out.println("\n" + firstOfMonth.getMonth() + " " + firstOfMonth.getYear());
        
        // 打印星期几的标题
        out.println("Mon Tue Wed Thu Fri Sat Sun");
        
        // 计算第一天是星期几（1=星期一，7=星期日）
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        
        // 打印第一行的前导空格
        for (int i = 1; i < dayOfWeek; i++) {
            out.print("    ");
        }
        
        // 打印日期
        int daysInMonth = firstOfMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            out.printf("%3d ", day);
            
            // 计算当前日期是星期几
            LocalDate currentDate = firstOfMonth.withDayOfMonth(day);
            int currentDayOfWeek = currentDate.getDayOfWeek().getValue();
            
            // 如果是星期日或最后一天，则换行
            if (currentDayOfWeek == 7 || day == daysInMonth) {
                out.println();
            }
        }
    }
    
    /**
     * 打印整年的日历。
     * 
     * @param year 年份
     */
    public void printYearCalendar(int year) {
        out.println("\nCalendar for " + year);
        out.println("===================");
        
        for (Month month : Month.values()) {
            // 使用静态导入的of方法创建日期
            LocalDate date = of(year, month, 1);
            printMonthCalendar(date);
            out.println();
        }
    }
} 
