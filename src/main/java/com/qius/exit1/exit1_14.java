package com.qius.exit1;

/**
 * 问题14：能在switch语句中使用break而不使用直通吗？在switch表达式中呢？为什么？
 * 
 * 本程序展示了如何：
 * 1. 比较传统switch语句中break的使用与直通(fall-through)行为
 * 2. 展示Java 14引入的switch表达式的新语法特性
 * 3. 解释switch表达式中yield关键字的使用方式和目的
 * 4. 通过多个示例演示不同switch用法的优缺点
 */
public class exit1_14 {
    public static void main(String[] args) {
        /**
         * 程序介绍
         * - 说明程序目的
         * - 建立问题背景
         */
        System.out.println("问题14：在switch中使用break与直通的分析");
        System.out.println("-----------------------------------------------------------------");
        
        /**
         * 示例1：带break的switch语句
         * - 展示传统switch语句的标准用法
         * - 每个case后使用break防止直通
         * - 演示控制流如何在匹配case后终止
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
         * - 展示省略break时的直通行为
         * - 说明执行流程如何继续到后续case块
         * - 突出这种用法的特殊性和潜在风险
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
         * - 展示Java 14引入的箭头语法
         * - 演示表达式形式如何简化代码结构
         * - 说明箭头语法如何自动防止直通
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
         * - 展示在表达式中使用传统case:语法
         * - 演示yield用于返回值，取代break
         * - 突出表达式必须产生值的特点
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
         * - 展示如何返回不同类型的值
         * - 演示表达式语法的灵活性
         * - 说明类型推断如何工作
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
         * - 展示带代码块的箭头语法
         * - 演示如何在代码块内执行多条语句
         * - 说明如何在代码块中使用yield返回值
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
         * 总结部分
         * - 对比不同switch使用方式的区别
         * - 解释break和yield的不同用途
         * - 强调switch表达式的语法改进
         */
        System.out.println("\n总结: 请查看源码注释获取完整解释");
    }
} 