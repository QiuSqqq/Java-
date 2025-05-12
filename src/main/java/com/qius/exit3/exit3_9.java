package com.qius.exit3;

/**
 * 问题9：使用hour和minute组件实现一个TimeOfDay记录。将它们进行标准化处理，
 * 使小时介于0和23之间，分钟介于0和59之间。提供一个方法：
 * TimeOfDay plusMinutes(int minutes)该方法根据给定的分钟数生成一个新的TimeOfDay对象。
 * 再实现一个方法：int minutesFrom(TimeOfDay other)该方法生成该对象与给定的TimeOfDay实例之间的分钟数。
 */
public class exit3_9 {
    public static void main(String[] args) {
        System.out.println("问题9：实现TimeOfDay记录及相关方法");
        System.out.println("--------------------------------");
        
        // 创建TimeOfDay实例
        TimeOfDay time1 = new TimeOfDay(9, 30);
        System.out.println("time1: " + time1);
        
        // 测试标准化处理
        TimeOfDay time2 = new TimeOfDay(25, 70);
        System.out.println("new TimeOfDay(25, 70) 经标准化处理后: " + time2);
        
        // 测试处理负值的情况
        TimeOfDay time3 = new TimeOfDay(-1, -15);
        System.out.println("new TimeOfDay(-1, -15) 经标准化处理后: " + time3);
        
        // 测试plusMinutes方法
        TimeOfDay time4 = time1.plusMinutes(75);
        System.out.println("time1.plusMinutes(75): " + time4);
        
        TimeOfDay time5 = time1.plusMinutes(-45);
        System.out.println("time1.plusMinutes(-45): " + time5);
        
        // 测试minutesFrom方法
        System.out.println("\n测试minutesFrom方法：");
        TimeOfDay morning = new TimeOfDay(8, 30);
        TimeOfDay afternoon = new TimeOfDay(15, 45);
        
        int minutesBetween1 = afternoon.minutesFrom(morning);
        System.out.println("从 " + morning + " 到 " + afternoon + " 的分钟数: " + minutesBetween1);
        
        int minutesBetween2 = morning.minutesFrom(afternoon);
        System.out.println("从 " + afternoon + " 到 " + morning + " 的分钟数: " + minutesBetween2);
        
        // 跨天的情况
        TimeOfDay earlyMorning = new TimeOfDay(1, 15);
        TimeOfDay lateEvening = new TimeOfDay(22, 45);
        
        int minutesBetween3 = earlyMorning.minutesFrom(lateEvening);
        System.out.println("从 " + lateEvening + " 到次日 " + earlyMorning + " 的分钟数: " + minutesBetween3);
        
        // 说明时间标准化的实现
        System.out.println("\n时间标准化的实现说明：");
        System.out.println("1. 小时标准化：将小时限制在0-23范围内");
        System.out.println("   - 如果小时为负数，加上24直到变为非负数");
        System.out.println("   - 如果小时≥24，取模运算得到0-23范围内的值");
        
        System.out.println("2. 分钟标准化：将分钟限制在0-59范围内");
        System.out.println("   - 如果分钟为负数，先计算需要减去多少小时使分钟变为非负数");
        System.out.println("   - 如果分钟≥60，计算需要增加多少小时，然后取模得到0-59范围内的值");
        
        System.out.println("\n这种设计的优点：");
        System.out.println("- 时间总是有效的，不需要额外的验证");
        System.out.println("- 简化了时间计算，特别是跨天的情况");
        System.out.println("- 记录是不可变的，所有操作返回新对象，原对象不变");
    }
}

/**
 * 表示一天中的时间点，使用24小时制。
 * <p>
 * 这是一个不可变记录，所有字段在构造后不能修改。
 * 时间值会自动标准化，使小时在0-23范围内，分钟在0-59范围内。
 * </p>
 * 
 * @param hour 小时(0-23)
 * @param minute 分钟(0-59)
 */
record TimeOfDay(int hour, int minute) {
    
    /**
     * 创建一个新的TimeOfDay对象，自动将时间标准化。
     * 
     * @param hour 小时值，将被标准化到0-23范围
     * @param minute 分钟值，将被标准化到0-59范围
     */
    public TimeOfDay {
        // 标准化分钟，计算溢出的小时
        int additionalHours = 0;
        
        // 处理负分钟值
        if (minute < 0) {
            // 计算需要减去多少小时
            additionalHours = (minute / 60) - 1;
            // 调整分钟到0-59范围
            minute = 60 + (minute % 60);
            if (minute == 60) {
                minute = 0;
                additionalHours++;
            }
        } else {
            // 处理分钟>=60的情况
            additionalHours = minute / 60;
            minute %= 60;
        }
        
        // 调整小时，加上分钟溢出的小时数
        hour += additionalHours;
        
        // 标准化小时到0-23范围
        if (hour < 0) {
            // 处理负小时值
            hour = (hour % 24 + 24) % 24;
        } else {
            // 处理小时>=24的情况
            hour %= 24;
        }
    }
    
    /**
     * 在当前时间上增加指定的分钟数，返回一个新的TimeOfDay对象。
     * <p>
     * 可以添加正分钟数或负分钟数。结果会自动标准化。
     * </p>
     * 
     * @param minutes 要增加的分钟数，可以是正数或负数
     * @return 一个新的TimeOfDay对象，表示增加分钟后的时间
     */
    public TimeOfDay plusMinutes(int minutes) {
        // 计算增加分钟后的总分钟数
        int totalMinutes = this.hour * 60 + this.minute + minutes;
        
        // 将总分钟数转换回小时和分钟，并创建新的TimeOfDay对象
        // 由于构造函数会自动标准化时间，所以不需要额外处理
        int newHour = totalMinutes / 60;
        int newMinute = totalMinutes % 60;
        
        // 处理负分钟数的情况
        if (newMinute < 0) {
            newMinute += 60;
            newHour--;
        }
        
        return new TimeOfDay(newHour, newMinute);
    }
    
    /**
     * 计算从other时间到当前时间的分钟数。
     * <p>
     * 结果总是当天内的最短分钟差，不考虑跨天的情况。
     * 如果other在当前时间之后，返回的分钟数为正；
     * 如果other在当前时间之前，返回的分钟数为负。
     * </p>
     * 
     * @param other 另一个TimeOfDay对象
     * @return 从other到当前时间的分钟数
     */
    public int minutesFrom(TimeOfDay other) {
        // 计算两个时间点的总分钟数
        int thisMinutes = this.hour * 60 + this.minute;
        int otherMinutes = other.hour * 60 + other.minute;
        
        // 计算分钟差
        int diff = thisMinutes - otherMinutes;
        
        // 处理跨天的情况，取最短的分钟差
        if (diff > 12 * 60) {            // 如果差值大于12小时，选择另一个方向（跨天）
            diff = diff - 24 * 60;
        } else if (diff < -12 * 60) {    // 如果差值小于-12小时，选择另一个方向（跨天）
            diff = diff + 24 * 60;
        }
        
        return diff;
    }
    
    /**
     * 返回此时间点的字符串表示，格式为"HH:MM"。
     * 
     * @return 格式化的时间字符串
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
} 
