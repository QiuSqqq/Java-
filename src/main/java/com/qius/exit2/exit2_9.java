package exit2;

/**
 * 问题9：使用hour和minute组件实现一个TimeOfDay记录。将它们进行标准化处理，使小时介于0和23
 * 之间，分钟介于0和59之间。提供一个方法：TimeOfDay plusMinutes(int minutes)该方法根据给定
 * 的分钟数生成一个新的TimeOfDay对象。再实现一个方法：
 * int minutesFrom(TimeOfDay other)
 * 该方法生成该对象与给定的TimeOfDay实例之间的分钟数。
 */
public class exit2_9 {
    public static void main(String[] args) {
        System.out.println("问题9：实现TimeOfDay记录及相关方法");
        System.out.println("-----------------------------");
        
        /**
         * 基本创建和标准化测试：
         * - 标准时间: 9:30
         * - 超出范围时间: 25:70 (标准化为 2:10)
         * - 负数时间: -1:-15 (标准化为 23:45)
         */
        System.out.println("1. 基本创建和标准化测试");
        TimeOfDay time1 = new TimeOfDay(9, 30); // 标准时间 - 9:30
        System.out.println("   标准时间 9:30 -> " + time1);
        
        TimeOfDay time2 = new TimeOfDay(25, 70); // 超出范围 - 应标准化为 2:10
        System.out.println("   超出范围 25:70 -> " + time2);
        
        TimeOfDay time3 = new TimeOfDay(-1, -15); // 负数时间 - 应标准化为 23:45
        System.out.println("   负数时间 -1:-15 -> " + time3);
        
        /**
         * plusMinutes方法测试：
         * - 增加30分钟: 9:30 -> 10:00
         * - 增加90分钟: 9:30 -> 11:00
         * - 减少45分钟: 9:30 -> 8:45
         * - 减少600分钟: 9:30 -> 前一天 19:30
         */
        System.out.println("\n2. plusMinutes方法测试");
        System.out.println("   " + time1 + " 加 30分钟 -> " + time1.plusMinutes(30));
        System.out.println("   " + time1 + " 加 90分钟 -> " + time1.plusMinutes(90));
        System.out.println("   " + time1 + " 加 -45分钟 -> " + time1.plusMinutes(-45));
        System.out.println("   " + time1 + " 加 -600分钟 -> " + time1.plusMinutes(-600));
        
        /**
         * minutesFrom方法测试：
         * - 测试常规时间差计算
         * - 测试跨午夜的时间差计算
         */
        System.out.println("\n3. minutesFrom方法测试");
        TimeOfDay time4 = new TimeOfDay(10, 15);
        TimeOfDay time5 = new TimeOfDay(8, 45);
        
        System.out.println("   从 " + time5 + " 到 " + time4 + " 的分钟数: " + time4.minutesFrom(time5));
        System.out.println("   从 " + time4 + " 到 " + time5 + " 的分钟数: " + time5.minutesFrom(time4));
        
        // 测试跨午夜的情况
        TimeOfDay time6 = new TimeOfDay(23, 45);
        TimeOfDay time7 = new TimeOfDay(0, 15);
        
        System.out.println("   从 " + time6 + " 到 " + time7 + " 的分钟数: " + time7.minutesFrom(time6));
        System.out.println("   从 " + time7 + " 到 " + time6 + " 的分钟数: " + time6.minutesFrom(time7));
        
        /**
         * 总结：
         * 1. TimeOfDay记录能够表示一天中的任意时间点，并自动标准化
         * 2. plusMinutes方法可以计算给定时间加上或减去一定分钟后的新时间
         * 3. minutesFrom方法可以计算两个时间点之间的最短分钟差
         * 4. 标准化机制确保所有操作结果都是有效的时间表示
         */
        System.out.println("\n4. 总结");
    }
}

/**
 * 表示一天中的时间，由小时和分钟组成。
 * <p>
 * 所有输入值都会被自动标准化，使小时在0-23范围内，分钟在0-59范围内。
 * 此记录是不可变的，所有操作都会返回新的TimeOfDay实例。
 * </p>
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
