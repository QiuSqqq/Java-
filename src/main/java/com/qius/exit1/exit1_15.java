package com.qius.exit1; /**
 * 问题15：提出一个有用的场景，其中直通行为对于switch表达式或switch语句是有益的。大多数网络
 * 搜索的结果都是针对C或C++的示例，其中执行会从case A跳转到case B，而不执行任何操作。在
 * Java中，这样的操作并没有什么意义，因为可以直接使用case A, B。
 */

import java.util.Scanner;

public class exit1_15 {
    public static void main(String[] args) {
        // 创建Scanner对象
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("问题15：提出一个有用的场景，其中直通行为对于switch表达式或switch语句是有益的。");
        System.out.println("----------------------------------------------------------------------");
        
        // 解释直通行为
        System.out.println("直通行为(fall through)指的是在switch语句中，如果没有break语句，");
        System.out.println("执行完一个case后会继续执行下一个case的代码，而不是退出switch结构。");
        System.out.println();
        
        // 场景1：计算某月的天数
        System.out.println("有益场景1：计算月份的天数");
        System.out.println("在这个场景中，我们使用直通行为来处理具有相同天数的月份：");
        
        while (true) {
            System.out.print("\n请输入月份(1-12)，输入0退出: ");
            int month = scanner.nextInt();
            
            if (month == 0) {
                break;
            }
            
            if (month < 1 || month > 12) {
                System.out.println("无效的月份，请输入1-12之间的数字。");
                continue;
            }
            
            System.out.print("请输入年份: ");
            int year = scanner.nextInt();
            
            // 使用直通行为的switch语句
            int days = 0;
            System.out.println("使用switch语句计算" + year + "年" + month + "月的天数：");
            switch (month) {
                case 2: // 二月
                    // 检查是否为闰年
                    boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                    days = isLeapYear ? 29 : 28;
                    break;
                case 4: // 四月
                case 6: // 六月
                case 9: // 九月
                case 11: // 十一月
                    // 这些月份都有30天
                    days = 30;
                    break;
                case 1: // 一月
                case 3: // 三月
                case 5: // 五月
                case 7: // 七月
                case 8: // 八月
                case 10: // 十月
                case 12: // 十二月
                    // 这些月份都有31天
                    days = 31;
                    break;
                default:
                    System.out.println("无效的月份");
                    days = 0;
            }
            
            System.out.println(year + "年" + month + "月有" + days + "天。");
            System.out.println("\n这个例子展示了直通行为的好处：相同天数的月份可以共享代码。");
            System.out.println("注意：在Java中，也可以用 'case 4, 6, 9, 11:' 这样的语法来代替直通，但在较旧的Java版本中不支持。");
            
            // 询问是否继续
            System.out.print("\n是否查看另一个有益场景？(y/n): ");
            scanner.nextLine(); // 消耗换行符
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
        }
        
        // 场景2：状态机实现
        System.out.println("\n有益场景2：实现简单的状态机");
        System.out.println("直通行为在实现状态机时非常有用。例如，模拟一个简单的自动售货机状态：");
        System.out.println("1. 初始状态：等待投币");
        System.out.println("2. 已投币：等待选择");
        System.out.println("3. 已选择：出货");
        System.out.println("4. 完成：找零并返回初始状态");
        
        System.out.println("\n以下是使用switch直通行为实现状态转换的代码示例（注：不会实际执行）：");
        System.out.println("void processVendingMachine(int state, int action) {");
        System.out.println("    switch (state) {");
        System.out.println("        case WAITING_FOR_COIN:");
        System.out.println("            if (action != INSERT_COIN) break;");
        System.out.println("            collectCoin();");
        System.out.println("            state = WAITING_FOR_SELECTION;");
        System.out.println("            // 故意不加break，直接进入下一个状态处理");
        System.out.println("        case WAITING_FOR_SELECTION:");
        System.out.println("            if (action != MAKE_SELECTION) break;");
        System.out.println("            processSelection();");
        System.out.println("            state = DISPENSING;");
        System.out.println("            // 故意不加break，直接进入下一个状态处理");
        System.out.println("        case DISPENSING:");
        System.out.println("            dispenseProduct();");
        System.out.println("            state = RETURNING_CHANGE;");
        System.out.println("            // 故意不加break，直接进入下一个状态处理");
        System.out.println("        case RETURNING_CHANGE:");
        System.out.println("            returnChange();");
        System.out.println("            state = WAITING_FOR_COIN;");
        System.out.println("            break;");
        System.out.println("    }");
        System.out.println("}");
        
        System.out.println("\n这个例子中，直通行为使得状态可以连续转换，一次调用可能会触发多个状态的处理逻辑。");
        System.out.println("在实际状态机实现中，这样的设计可以减少重复代码和显式的状态转换调用。");
        
        System.out.println("\n结论：");
        System.out.println("1. 在Java中，直通行为主要用于具有共享代码的多个case，如第一个例子所示。");
        System.out.println("2. 在实现状态机或需要连续执行多个case的逻辑时，直通行为也很有用。");
        System.out.println("3. 现代Java（Java 14+）中的switch表达式和增强的switch语句支持多个case共享一个处理块，");
        System.out.println("   使用 'case A, B, C ->' 语法，这在某些情况下可以替代传统的直通行为。");
        System.out.println("4. 直通行为应该谨慎使用，并通过注释明确表明这是有意为之，以避免误解。");
        
        // 关闭Scanner
        scanner.close();
    }
} 