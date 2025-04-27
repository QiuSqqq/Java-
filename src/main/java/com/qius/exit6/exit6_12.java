package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题12：函数调用跟踪
 * 使用栈模拟函数调用和返回过程，实现函数调用跟踪功能
 * 
 * 这个程序模拟了计算机系统中函数调用的工作原理：
 * 1. 每次函数调用时，调用信息压入栈中
 * 2. 函数返回时，从栈顶弹出调用信息
 * 3. 栈顶始终是当前正在执行的函数
 * 4. 栈的深度反映了函数调用的嵌套层级
 * 
 * 实际上，这正是计算机内部处理函数调用的方式，称为"调用栈"
 */
public class exit6_12 {
    
    /**
     * 函数调用信息
     */
    public static class FunctionCall {
        private String functionName;       // 函数名称
        private Object[] parameters;       // 函数参数
        private int callLineNumber;        // 调用行号
        private Object returnValue;        // 返回值
        
        /**
         * 构造函数
         * @param functionName 函数名称
         * @param parameters 函数参数
         * @param callLineNumber 调用行号
         */
        public FunctionCall(String functionName, Object[] parameters, int callLineNumber) {
            this.functionName = functionName;
            this.parameters = parameters;
            this.callLineNumber = callLineNumber;
            this.returnValue = null;
        }
        
        /**
         * 设置返回值
         * @param returnValue 函数返回值
         */
        public void setReturnValue(Object returnValue) {
            this.returnValue = returnValue;
        }
        
        /**
         * 获取函数名
         */
        public String getFunctionName() {
            return functionName;
        }
        
        /**
         * 获取调用行号
         */
        public int getCallLineNumber() {
            return callLineNumber;
        }
        
        /**
         * 获取参数数组
         */
        public Object[] getParameters() {
            return parameters;
        }
        
        /**
         * 获取返回值
         */
        public Object getReturnValue() {
            return returnValue;
        }
        
        /**
         * 生成参数的字符串表示
         */
        private String getParametersString() {
            if (parameters == null || parameters.length == 0) {
                return "()";
            }
            
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; i < parameters.length; i++) {
                sb.append(parameters[i]);
                if (i < parameters.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        
        @Override
        public String toString() {
            return functionName + getParametersString();
        }
    }
    
    /**
     * 函数调用追踪器
     */
    public static class FunctionTracer {
        private ArrayListStack<FunctionCall> callStack;  // 调用栈
        private boolean traceEnabled;                   // 是否启用跟踪
        
        /**
         * 构造函数
         */
        public FunctionTracer() {
            callStack = new ArrayListStack<>();
            traceEnabled = true;
        }
        
        /**
         * 设置是否启用跟踪
         */
        public void setTraceEnabled(boolean enabled) {
            traceEnabled = enabled;
        }
        
        /**
         * 记录函数调用
         * @param functionName 函数名
         * @param parameters 参数列表
         * @param lineNumber 调用行号
         */
        public void traceCall(String functionName, Object[] parameters, int lineNumber) {
            FunctionCall call = new FunctionCall(functionName, parameters, lineNumber);
            callStack.push(call);
            
            if (traceEnabled) {
                System.out.println("-> " + getIndentation() + "调用: " + call);
            }
        }
        
        /**
         * 记录函数返回
         * @param returnValue 返回值
         */
        public void traceReturn(Object returnValue) {
            if (callStack.isEmpty()) {
                System.out.println("错误: 调用栈为空，无法记录返回");
                return;
            }
            
            FunctionCall call = callStack.pop();
            call.setReturnValue(returnValue);
            
            if (traceEnabled) {
                System.out.println("<- " + getIndentation() + "返回: " + call + " => " + returnValue);
            }
        }
        
        /**
         * 获取当前函数调用的缩进，用于显示调用层次
         */
        private String getIndentation() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < callStack.size(); i++) {
                sb.append("  ");
            }
            return sb.toString();
        }
        
        /**
         * 获取当前调用栈状态
         */
        public void printCallStack() {
            if (callStack.isEmpty()) {
                System.out.println("调用栈为空");
                return;
            }
            
            System.out.println("当前调用栈 (从栈顶到栈底):");
            
            // 使用临时栈来遍历并保持原始栈不变
            ArrayListStack<FunctionCall> tempStack = new ArrayListStack<>();
            while (!callStack.isEmpty()) {
                tempStack.push(callStack.pop());
            }
            
            int level = tempStack.size();
            while (!tempStack.isEmpty()) {
                FunctionCall call = tempStack.pop();
                System.out.println(level + ": " + call + " (在第 " + call.getCallLineNumber() + " 行调用)");
                callStack.push(call);
                level--;
            }
        }
    }
    
    /**
     * 模拟递归计算阶乘的函数，使用跟踪器记录调用
     */
    public static class FactorialCalculator {
        private FunctionTracer tracer;
        
        public FactorialCalculator(FunctionTracer tracer) {
            this.tracer = tracer;
        }
        
        public long factorial(int n) {
            // 记录函数调用
            tracer.traceCall("factorial", new Object[]{n}, Thread.currentThread().getStackTrace()[1].getLineNumber());
            
            long result;
            if (n <= 1) {
                result = 1;
            } else {
                result = n * factorial(n - 1);
            }
            
            // 记录函数返回
            tracer.traceReturn(result);
            return result;
        }
    }
    
    /**
     * 模拟计算斐波那契数列的函数，使用跟踪器记录调用
     */
    public static class FibonacciCalculator {
        private FunctionTracer tracer;
        
        public FibonacciCalculator(FunctionTracer tracer) {
            this.tracer = tracer;
        }
        
        public long fibonacci(int n) {
            // 记录函数调用
            tracer.traceCall("fibonacci", new Object[]{n}, Thread.currentThread().getStackTrace()[1].getLineNumber());
            
            long result;
            if (n <= 0) {
                result = 0;
            } else if (n == 1) {
                result = 1;
            } else {
                result = fibonacci(n - 1) + fibonacci(n - 2);
            }
            
            // 记录函数返回
            tracer.traceReturn(result);
            return result;
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        FunctionTracer tracer = new FunctionTracer();
        
        System.out.println("函数调用跟踪 - 使用栈实现");
        System.out.println("1. 跟踪阶乘函数调用");
        System.out.println("2. 跟踪斐波那契函数调用");
        System.out.print("请选择示例(1-2): ");
        
        int choice = input.nextInt();
        
        switch (choice) {
            case 1:
                System.out.print("请输入要计算阶乘的数: ");
                int factN = input.nextInt();
                
                FactorialCalculator factCalculator = new FactorialCalculator(tracer);
                System.out.println("\n开始跟踪阶乘计算:");
                long factResult = factCalculator.factorial(factN);
                System.out.println("\n计算结果: " + factN + "! = " + factResult);
                break;
                
            case 2:
                System.out.print("请输入要计算的斐波那契数的位置: ");
                int fibN = input.nextInt();
                
                // 对于较大的n，斐波那契计算会产生大量调用，可以选择关闭详细跟踪
                if (fibN > 10) {
                    System.out.println("注意: n较大时会产生大量调用跟踪信息");
                    System.out.print("是否显示详细跟踪信息？(y/n): ");
                    input.nextLine(); // 消耗换行符
                    String enableTrace = input.nextLine().trim().toLowerCase();
                    tracer.setTraceEnabled(enableTrace.equals("y") || enableTrace.equals("yes"));
                }
                
                FibonacciCalculator fibCalculator = new FibonacciCalculator(tracer);
                System.out.println("\n开始跟踪斐波那契数列计算:");
                long fibResult = fibCalculator.fibonacci(fibN);
                System.out.println("\n计算结果: F(" + fibN + ") = " + fibResult);
                break;
                
            default:
                System.out.println("无效的选择");
        }
        
        System.out.println("\n函数调用栈的应用:");
        System.out.println("1. 程序调试和性能分析");
        System.out.println("2. 异常处理和错误追踪");
        System.out.println("3. 程序执行流程可视化");
        System.out.println("4. 函数调用关系的分析和理解");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 