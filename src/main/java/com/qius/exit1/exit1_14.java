package com.qius.exit1;

/**
 * 问题14：能在switch语句中使用break而不使用直通吗？在switch表达式中呢？为什么？
 * 
 * 本程序将解释在switch语句和switch表达式中break的使用。
 * 
 * 1. 在传统的switch语句中：
 *    - 可以使用break而不使用直通(fall-through)
 *    - 在实际中，这是最常见的用法
 *    - break的作用是中断switch执行，防止直通到下一个case
 *    - 如果省略break，执行会"直通"到下一个case
 * 
 * 2. 在switch表达式中（Java 14及以上）：
 *    - 不能使用break
 *    - switch表达式必须产生一个值
 *    - 使用'->'语法时，每个case自动终止，不会直通
 *    - 使用传统的case:语法时，必须使用yield返回值，而不是break
 * 
 * 结论：
 * 1. 在传统的switch语句中，可以使用break而不使用直通，这是常见的做法。
 * 2. 在switch表达式中，使用->语法时不需要break，也不存在直通行为。
 * 3. 在switch表达式中，使用传统case:语法时需要使用yield来返回值，而不是break。
 * 4. break用于中断控制流，而yield用于提供返回值。
 */
public class exit1_14 {
    public static void main(String[] args) {
        System.out.println("问题14：在switch中使用break与直通的分析");
        System.out.println("-----------------------------------------------------------------");
        
        /**
         * 示例1：带break的switch语句
         */
        String day = "星期三";
        System.out.println("\n示例1：带break的switch语句 (day = " + day + ")");
        
        switch (day) {
            case "星期一":
                System.out.println("今天是星期一");
                break;
            case "星期二":
                System.out.println("今天是星期二");
                break;
            case "星期三":
                System.out.println("今天是星期三");
                break; // 遇到break，跳出switch
            case "星期四":
                System.out.println("今天是星期四");
                break;
            default:
                System.out.println("是其他日子");
                break;
        }
        
        /**
         * 示例2：不带break的switch语句（直通行为）
         */
        System.out.println("\n示例2：不带break的switch语句（直通行为）");
        switch (day) {
            case "星期一":
                System.out.println("今天是星期一");
                // 没有break，会直通到下一个case
            case "星期二":
                System.out.println("今天是星期二");
                // 没有break，会直通到下一个case
            case "星期三":
                System.out.println("今天是星期三");
                // 没有break，会直通到下一个case
            case "星期四":
                System.out.println("今天是星期四");
                // 没有break，会直通到下一个case
            default:
                System.out.println("处理完毕");
                // 即使这里没有break也没关系，因为已经是最后一个case
        }
        
        /**
         * 示例3：使用箭头语法的switch表达式
         */
        System.out.println("\n示例3：使用箭头语法的switch表达式");
        
        String result1 = switch (day) {
            case "星期一" -> "今天是工作日";
            case "星期二" -> "今天是工作日";
            case "星期三" -> "今天是工作日";
            case "星期四", "星期五" -> "今天是工作日";
            case "星期六", "星期日" -> "今天是周末";
            default -> "未知日子";
        };
        
        System.out.println("结果: " + result1);
        
        /**
         * 示例4：使用传统语法的switch表达式
         */
        System.out.println("\n示例4：使用传统语法的switch表达式");
        
        String result2 = switch (day) {
            case "星期一":
            case "星期二":
            case "星期三":
            case "星期四":
            case "星期五":
                yield "今天是工作日"; // 使用yield而不是break
            case "星期六":
            case "星期日":
                yield "今天是周末"; // 使用yield而不是break
            default:
                yield "未知日子"; // 使用yield而不是break
        };
        
        System.out.println("结果: " + result2);
        
        /**
         * 示例5：使用带表达式的箭头语法
         */
        System.out.println("\n示例5：带表达式的箭头语法");
        int dayValue = switch (day) {
            case "星期一" -> 1;
            case "星期二" -> 2;
            case "星期三" -> 3;
            case "星期四" -> 4;
            case "星期五" -> 5;
            case "星期六" -> 6;
            case "星期日" -> 7;
            default -> -1;
        };
        
        System.out.println(day + "对应的数值是: " + dayValue);
        
        /**
         * 示例6：使用多行代码块的箭头语法
         */
        System.out.println("\n示例6：带代码块的箭头语法");
        String message = switch (day) {
            case "星期一", "星期二", "星期三", "星期四", "星期五" -> {
                String period = "上半周";
                if (day.equals("星期四") || day.equals("星期五")) {
                    period = "下半周";
                }
                yield period + "的工作日";
            }
            case "星期六", "星期日" -> {
                yield "放松的周末";
            }
            default -> "未知日子";
        };
        
        System.out.println("详细信息: " + message);
        
        /**
         * 总结：
         * 1. 在传统switch语句中，break用于防止直通行为，这是常见的用法
         * 2. switch表达式使用->语法时，自动防止直通，不需要break
         * 3. switch表达式使用传统case:语法时，使用yield返回值，而不是break
         * 4. switch表达式总是要产生一个值，是表达式而非语句
         */
        System.out.println("\n总结: 请查看源码注释获取完整解释");
    }
} 