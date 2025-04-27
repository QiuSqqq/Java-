package com.qius.exit6;

import java.util.Scanner;

/**
 * 第六章习题11：文本编辑器撤销/重做功能
 * 演示如何使用栈实现文本编辑器中的撤销和重做功能
 * 
 * 这是栈的经典应用之一：
 * 1. 撤销栈存储用户的每一步操作
 * 2. 重做栈存储被撤销的操作
 * 3. 每次执行新操作时，清空重做栈
 * 4. 撤销时，当前操作移到重做栈
 * 5. 重做时，操作从重做栈移回撤销栈
 */
public class exit6_11 {
    
    /**
     * 表示编辑操作的类
     */
    public static class EditOperation {
        public enum OperationType {
            INSERT,     // 插入文本
            DELETE,     // 删除文本
            REPLACE     // 替换文本
        }
        
        private OperationType type;   // 操作类型
        private String text;          // 操作涉及的文本
        private int position;         // 操作位置
        private String replacedText;  // 被替换的文本（仅在REPLACE操作中使用）
        
        // 构造函数 - 用于INSERT和DELETE操作
        public EditOperation(OperationType type, String text, int position) {
            this.type = type;
            this.text = text;
            this.position = position;
            this.replacedText = null;
        }
        
        // 构造函数 - 用于REPLACE操作
        public EditOperation(String newText, String oldText, int position) {
            this.type = OperationType.REPLACE;
            this.text = newText;
            this.position = position;
            this.replacedText = oldText;
        }
        
        public OperationType getType() {
            return type;
        }
        
        public String getText() {
            return text;
        }
        
        public int getPosition() {
            return position;
        }
        
        public String getReplacedText() {
            return replacedText;
        }
        
        @Override
        public String toString() {
            switch (type) {
                case INSERT:
                    return "插入 \"" + text + "\" 在位置 " + position;
                case DELETE:
                    return "删除 \"" + text + "\" 在位置 " + position;
                case REPLACE:
                    return "替换位置 " + position + " 的 \"" + replacedText + "\" 为 \"" + text + "\"";
                default:
                    return "未知操作";
            }
        }
    }
    
    /**
     * 简单文本编辑器类，支持撤销和重做功能
     */
    public static class SimpleTextEditor {
        private StringBuilder content;                    // 当前文本内容
        private ArrayListStack<EditOperation> undoStack;  // 撤销栈
        private ArrayListStack<EditOperation> redoStack;  // 重做栈
        
        /**
         * 构造函数，创建一个空文本编辑器
         */
        public SimpleTextEditor() {
            content = new StringBuilder();
            undoStack = new ArrayListStack<>();
            redoStack = new ArrayListStack<>();
        }
        
        /**
         * 获取当前文本内容
         */
        public String getContent() {
            return content.toString();
        }
        
        /**
         * 插入文本
         * @param text 要插入的文本
         * @param position 插入位置
         */
        public void insert(String text, int position) {
            if (position < 0 || position > content.length()) {
                throw new IllegalArgumentException("无效的插入位置");
            }
            
            // 记录操作
            EditOperation operation = new EditOperation(EditOperation.OperationType.INSERT, text, position);
            undoStack.push(operation);
            
            // 清空重做栈
            clearRedoStack();
            
            // 执行插入
            content.insert(position, text);
            System.out.println("执行: " + operation);
        }
        
        /**
         * 删除文本
         * @param position 删除起始位置
         * @param length 删除长度
         */
        public void delete(int position, int length) {
            if (position < 0 || position >= content.length() || position + length > content.length()) {
                throw new IllegalArgumentException("无效的删除范围");
            }
            
            // 获取要删除的文本
            String deletedText = content.substring(position, position + length);
            
            // 记录操作
            EditOperation operation = new EditOperation(EditOperation.OperationType.DELETE, deletedText, position);
            undoStack.push(operation);
            
            // 清空重做栈
            clearRedoStack();
            
            // 执行删除
            content.delete(position, position + length);
            System.out.println("执行: " + operation);
        }
        
        /**
         * 替换文本
         * @param position 替换位置
         * @param length 要替换的长度
         * @param newText 新文本
         */
        public void replace(int position, int length, String newText) {
            if (position < 0 || position >= content.length() || position + length > content.length()) {
                throw new IllegalArgumentException("无效的替换范围");
            }
            
            // 获取要替换的文本
            String replacedText = content.substring(position, position + length);
            
            // 记录操作
            EditOperation operation = new EditOperation(newText, replacedText, position);
            undoStack.push(operation);
            
            // 清空重做栈
            clearRedoStack();
            
            // 执行替换
            content.replace(position, position + length, newText);
            System.out.println("执行: " + operation);
        }
        
        /**
         * 撤销最后一次操作
         * @return 是否成功撤销
         */
        public boolean undo() {
            if (undoStack.isEmpty()) {
                System.out.println("没有操作可以撤销");
                return false;
            }
            
            EditOperation operation = undoStack.pop();
            redoStack.push(operation);
            
            // 根据操作类型执行相反的操作
            switch (operation.getType()) {
                case INSERT:
                    // 撤销插入 = 删除插入的文本
                    content.delete(operation.getPosition(), operation.getPosition() + operation.getText().length());
                    break;
                    
                case DELETE:
                    // 撤销删除 = 重新插入被删除的文本
                    content.insert(operation.getPosition(), operation.getText());
                    break;
                    
                case REPLACE:
                    // 撤销替换 = 将文本替换回原文本
                    content.replace(
                        operation.getPosition(),
                        operation.getPosition() + operation.getText().length(),
                        operation.getReplacedText()
                    );
                    break;
            }
            
            System.out.println("撤销: " + operation);
            return true;
        }
        
        /**
         * 重做最后一次被撤销的操作
         * @return 是否成功重做
         */
        public boolean redo() {
            if (redoStack.isEmpty()) {
                System.out.println("没有操作可以重做");
                return false;
            }
            
            EditOperation operation = redoStack.pop();
            undoStack.push(operation);
            
            // 重新执行操作
            switch (operation.getType()) {
                case INSERT:
                    content.insert(operation.getPosition(), operation.getText());
                    break;
                    
                case DELETE:
                    content.delete(operation.getPosition(), operation.getPosition() + operation.getText().length());
                    break;
                    
                case REPLACE:
                    content.replace(
                        operation.getPosition(),
                        operation.getPosition() + operation.getReplacedText().length(),
                        operation.getText()
                    );
                    break;
            }
            
            System.out.println("重做: " + operation);
            return true;
        }
        
        /**
         * 清空重做栈
         * 在执行新操作时调用
         */
        private void clearRedoStack() {
            while (!redoStack.isEmpty()) {
                redoStack.pop();
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SimpleTextEditor editor = new SimpleTextEditor();
        
        System.out.println("文本编辑器 - 使用栈实现撤销/重做功能");
        System.out.println("可用命令:");
        System.out.println("1. insert <text> <position> - 在指定位置插入文本");
        System.out.println("2. delete <position> <length> - 从指定位置删除指定长度的文本");
        System.out.println("3. replace <position> <length> <newText> - 替换指定位置的文本");
        System.out.println("4. undo - 撤销上一步操作");
        System.out.println("5. redo - 重做上一步被撤销的操作");
        System.out.println("6. show - 显示当前文本内容");
        System.out.println("7. exit - 退出程序");
        
        boolean running = true;
        while (running) {
            System.out.println("\n当前文本: \"" + editor.getContent() + "\"");
            System.out.print("请输入命令: ");
            String commandLine = input.nextLine().trim();
            String[] parts = commandLine.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            
            try {
                switch (command) {
                    case "insert":
                        if (parts.length < 2) {
                            System.out.println("错误: 缺少参数");
                            continue;
                        }
                        
                        String[] insertParts = parts[1].split("\\s+", 2);
                        if (insertParts.length < 2) {
                            System.out.println("错误: 缺少参数");
                            continue;
                        }
                        
                        String textToInsert = insertParts[0];
                        int insertPosition = Integer.parseInt(insertParts[1]);
                        editor.insert(textToInsert, insertPosition);
                        break;
                        
                    case "delete":
                        if (parts.length < 2) {
                            System.out.println("错误: 缺少参数");
                            continue;
                        }
                        
                        String[] deleteParts = parts[1].split("\\s+");
                        if (deleteParts.length < 2) {
                            System.out.println("错误: 缺少参数");
                            continue;
                        }
                        
                        int deletePosition = Integer.parseInt(deleteParts[0]);
                        int deleteLength = Integer.parseInt(deleteParts[1]);
                        editor.delete(deletePosition, deleteLength);
                        break;
                        
                    case "replace":
                        if (parts.length < 2) {
                            System.out.println("错误: 缺少参数");
                            continue;
                        }
                        
                        String[] replaceParts = parts[1].split("\\s+", 3);
                        if (replaceParts.length < 3) {
                            System.out.println("错误: 缺少参数");
                            continue;
                        }
                        
                        int replacePosition = Integer.parseInt(replaceParts[0]);
                        int replaceLength = Integer.parseInt(replaceParts[1]);
                        String newText = replaceParts[2];
                        editor.replace(replacePosition, replaceLength, newText);
                        break;
                        
                    case "undo":
                        editor.undo();
                        break;
                        
                    case "redo":
                        editor.redo();
                        break;
                        
                    case "show":
                        System.out.println("当前文本内容: \"" + editor.getContent() + "\"");
                        break;
                        
                    case "exit":
                        running = false;
                        break;
                        
                    default:
                        System.out.println("未知命令: " + command);
                }
            } catch (Exception e) {
                System.out.println("错误: " + e.getMessage());
            }
        }
        
        System.out.println("\n撤销/重做功能在以下场景中有广泛应用:");
        System.out.println("1. 文本编辑器（如Word、Notepad++等）的编辑操作");
        System.out.println("2. 图形编辑软件（如Photoshop、Illustrator等）的绘图操作");
        System.out.println("3. 游戏中的操作步骤记录与回放");
        System.out.println("4. 数据库事务管理中的回滚操作");
        
        System.out.println("\n程序结束");
        input.close();
    }
} 