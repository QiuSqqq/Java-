package com.qius.exit5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 问题13：编写一个程序演示异常记录和恢复策略
 */
public class exit5_13 {
    
    // 创建logger
    private static final Logger LOGGER = Logger.getLogger(exit5_13.class.getName());
    
    // 错误日志文件路径
    private static final String ERROR_LOG_FILE = "src/main/java/com/qius/exit5/error_log.txt";
    
    // 程序数据文件路径
    private static final String DATA_FILE = "src/main/java/com/qius/exit5/app_data.txt";
    
    /**
     * 初始化日志系统
     */
    private static void initLogger() {
        try {
            // 配置文件处理器，将日志写入文件
            Handler fileHandler = new FileHandler("src/main/java/com/qius/exit5/app_log.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            
            // 添加处理器到logger
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            
            LOGGER.info("日志系统初始化成功");
        } catch (IOException e) {
            System.err.println("无法初始化日志系统: " + e.getMessage());
            // 这里不抛出异常，因为日志系统不是应用程序的关键功能
        }
    }
    
    /**
     * 记录错误到日志文件
     * @param error 错误信息
     * @param exception 异常对象
     */
    private static void logError(String error, Throwable exception) {
        LOGGER.log(Level.SEVERE, error, exception);
        
        // 额外将错误写入专门的错误日志文件
        try (PrintWriter writer = new PrintWriter(new FileWriter(ERROR_LOG_FILE, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.println("=== 错误发生时间: " + dateFormat.format(new Date()) + " ===");
            writer.println("错误信息: " + error);
            if (exception != null) {
                writer.println("异常类型: " + exception.getClass().getName());
                writer.println("异常信息: " + exception.getMessage());
                writer.println("堆栈跟踪:");
                exception.printStackTrace(writer);
            }
            writer.println();
        } catch (IOException e) {
            System.err.println("无法写入错误日志: " + e.getMessage());
        }
    }
    
    /**
     * 读取数据文件，如果失败则使用默认值
     * @return 文件内容或默认值
     */
    public static String readDataWithFallback() {
        LOGGER.info("尝试读取数据文件: " + DATA_FILE);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            StringBuilder content = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            
            LOGGER.info("成功读取数据文件");
            return content.toString();
            
        } catch (IOException e) {
            // 记录错误
            logError("读取数据文件失败", e);
            
            // 返回默认值（故障恢复策略）
            LOGGER.warning("使用默认数据作为故障恢复");
            return "默认数据 - 这是在无法读取数据文件时使用的备用数据";
        }
    }
    
    /**
     * 写入数据到文件，使用重试机制
     * @param data 要写入的数据
     * @param maxRetries 最大重试次数
     * @return 是否成功
     */
    public static boolean writeDataWithRetry(String data, int maxRetries) {
        LOGGER.info("尝试写入数据到文件: " + DATA_FILE);
        
        int retryCount = 0;
        boolean success = false;
        
        while (retryCount <= maxRetries && !success) {
            if (retryCount > 0) {
                LOGGER.info("重试写入数据，尝试 #" + retryCount);
                // 如果不是第一次尝试，等待一段时间再重试
                try {
                    Thread.sleep(1000);  // 等待1秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logError("重试等待被中断", e);
                }
            }
            
            try (FileWriter writer = new FileWriter(DATA_FILE)) {
                writer.write(data);
                success = true;
                LOGGER.info("成功写入数据到文件");
                
            } catch (IOException e) {
                retryCount++;
                if (retryCount <= maxRetries) {
                    LOGGER.warning("写入失败，将进行重试 " + retryCount + "/" + maxRetries);
                } else {
                    logError("写入数据文件失败，已达到最大重试次数", e);
                }
            }
        }
        
        return success;
    }
    
    /**
     * 演示事务性操作：只有在所有操作成功时才提交
     * @param userData 用户数据
     * @param systemData 系统数据
     * @return 是否成功
     */
    public static boolean performTransactionalOperation(String userData, String systemData) {
        LOGGER.info("开始事务性操作");
        
        // 用于记录临时文件
        String userDataTempFile = DATA_FILE + ".user.tmp";
        String systemDataTempFile = DATA_FILE + ".system.tmp";
        
        boolean userDataSaved = false;
        boolean systemDataSaved = false;
        
        try {
            // 保存用户数据到临时文件
            try (FileWriter writer = new FileWriter(userDataTempFile)) {
                writer.write(userData);
                userDataSaved = true;
                LOGGER.info("用户数据已保存到临时文件");
            }
            
            // 保存系统数据到临时文件
            try (FileWriter writer = new FileWriter(systemDataTempFile)) {
                writer.write(systemData);
                systemDataSaved = true;
                LOGGER.info("系统数据已保存到临时文件");
            }
            
            // 如果两个操作都成功，则进行"提交"
            if (userDataSaved && systemDataSaved) {
                // 将两个临时文件合并到最终文件
                try (
                    FileWriter writer = new FileWriter(DATA_FILE);
                    BufferedReader userReader = new BufferedReader(new FileReader(userDataTempFile));
                    BufferedReader systemReader = new BufferedReader(new FileReader(systemDataTempFile))
                ) {
                    // 写入用户数据
                    String line;
                    while ((line = userReader.readLine()) != null) {
                        writer.write(line + "\n");
                    }
                    
                    // 写入系统数据
                    writer.write("--- 系统数据 ---\n");
                    while ((line = systemReader.readLine()) != null) {
                        writer.write(line + "\n");
                    }
                    
                    LOGGER.info("事务成功提交");
                    return true;
                }
            } else {
                throw new IOException("无法保存所有必需的数据");
            }
            
        } catch (IOException e) {
            // 记录错误
            logError("事务操作失败", e);
            
            // 进行回滚（删除临时文件）
            LOGGER.warning("事务回滚中...");
            return false;
            
        } finally {
            // 清理：删除临时文件
            deleteFileQuietly(userDataTempFile);
            deleteFileQuietly(systemDataTempFile);
        }
    }
    
    /**
     * 安静地删除文件（不抛出异常）
     * @param filePath 文件路径
     */
    private static void deleteFileQuietly(String filePath) {
        try {
            java.io.File file = new java.io.File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    LOGGER.fine("临时文件已删除: " + filePath);
                } else {
                    LOGGER.warning("无法删除临时文件: " + filePath);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "删除文件时发生错误", e);
        }
    }
    
    /**
     * 演示恢复检查点
     * @param data 数据列表
     */
    public static void processWithCheckpoints(String[] data) {
        LOGGER.info("开始处理带有检查点的数据");
        
        // 用于存储最后一个成功的检查点
        int lastCheckpoint = -1;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (int i = 0; i < data.length; i++) {
                try {
                    // 模拟处理每项数据
                    System.out.println("处理项目 " + (i + 1) + ": " + data[i]);
                    
                    // 模拟一个可能失败的操作
                    if (data[i].contains("错误")) {
                        throw new RuntimeException("处理'" + data[i] + "'时发生错误");
                    }
                    
                    // 写入处理后的数据
                    writer.println("已处理: " + data[i]);
                    
                    // 设置检查点
                    lastCheckpoint = i;
                    LOGGER.info("设置检查点: " + lastCheckpoint);
                    
                } catch (Exception e) {
                    // 记录错误但继续处理
                    logError("处理项目 " + (i + 1) + " 时发生错误", e);
                    System.err.println("跳过有问题的项目，继续处理下一个...");
                }
            }
            
            System.out.println("处理完成，最后一个检查点: " + lastCheckpoint);
            
        } catch (IOException e) {
            logError("写入输出文件失败", e);
            
            // 如果有检查点，可以从最后一个成功的检查点恢复
            if (lastCheckpoint >= 0) {
                System.out.println("可以从检查点 " + lastCheckpoint + " 恢复");
            }
        }
    }
    
    /**
     * 演示多级故障恢复策略
     * @param filename 要读取的文件名
     * @return 文件内容
     */
    public static String readWithMultiLevelRecovery(String filename) {
        LOGGER.info("尝试读取文件: " + filename);
        
        // 第一级：尝试读取主文件
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder content = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            
            LOGGER.info("成功读取文件");
            return content.toString();
            
        } catch (IOException primaryException) {
            // 记录主文件读取失败
            logError("读取主文件失败，尝试备份文件", primaryException);
            
            // 第二级：尝试读取备份文件
            String backupFile = filename + ".bak";
            LOGGER.info("尝试读取备份文件: " + backupFile);
            
            try (BufferedReader reader = new BufferedReader(new FileReader(backupFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                
                LOGGER.info("成功从备份文件恢复");
                return content.toString() + "\n[注：这些数据从备份文件恢复]";
                
            } catch (IOException backupException) {
                // 记录备份文件读取失败
                logError("读取备份文件也失败，使用内存缓存", backupException);
                
                // 第三级：使用内存中的缓存数据
                LOGGER.info("尝试使用内存缓存");
                String cachedData = retrieveCachedData();
                
                if (cachedData != null) {
                    LOGGER.info("成功从内存缓存恢复");
                    return cachedData + "\n[注：这些数据从内存缓存恢复]";
                } else {
                    // 第四级：所有恢复尝试都失败，使用默认值
                    logError("所有恢复尝试都失败，使用默认值", null);
                    LOGGER.warning("使用默认值");
                    return "默认数据 - 所有恢复尝试都失败\n[注：这是默认数据]";
                }
            }
        }
    }
    
    /**
     * 模拟从内存缓存获取数据
     * @return 缓存数据
     */
    private static String retrieveCachedData() {
        // 在实际应用中，这里会从内存缓存中检索数据
        // 这里只是模拟返回一些缓存数据
        return "这是从内存缓存中检索的数据";
    }
    
    public static void main(String[] args) {
        // 初始化日志系统
        initLogger();
        
        System.out.println("=== 异常记录和恢复策略演示 ===");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n选择要演示的策略:");
                System.out.println("1. 故障转移策略（使用默认值）");
                System.out.println("2. 重试策略");
                System.out.println("3. 事务性操作（全有或全无）");
                System.out.println("4. 检查点和增量处理");
                System.out.println("5. 多级恢复策略");
                System.out.println("0. 退出");
                
                System.out.print("请选择 (0-5): ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // 消耗换行符
                
                if (choice == 0) {
                    System.out.println("退出程序。");
                    break;
                }
                
                switch (choice) {
                    case 1:
                        System.out.println("\n=== 演示故障转移策略 ===");
                        String data = readDataWithFallback();
                        System.out.println("读取的数据: " + data);
                        break;
                        
                    case 2:
                        System.out.println("\n=== 演示重试策略 ===");
                        System.out.print("输入要写入的数据: ");
                        String inputData = scanner.nextLine();
                        boolean success = writeDataWithRetry(inputData, 3);
                        if (success) {
                            System.out.println("数据成功写入文件");
                        } else {
                            System.out.println("写入数据失败，即使经过多次重试");
                        }
                        break;
                        
                    case 3:
                        System.out.println("\n=== 演示事务性操作 ===");
                        System.out.print("输入用户数据: ");
                        String userData = scanner.nextLine();
                        System.out.print("输入系统数据: ");
                        String systemData = scanner.nextLine();
                        
                        boolean transactionSuccess = performTransactionalOperation(userData, systemData);
                        if (transactionSuccess) {
                            System.out.println("事务成功完成");
                        } else {
                            System.out.println("事务失败并已回滚");
                        }
                        break;
                        
                    case 4:
                        System.out.println("\n=== 演示检查点和增量处理 ===");
                        String[] items = {
                            "项目1 - 正常数据",
                            "项目2 - 包含错误的数据",
                            "项目3 - 正常数据",
                            "项目4 - 正常数据",
                            "项目5 - 包含错误的数据"
                        };
                        processWithCheckpoints(items);
                        break;
                        
                    case 5:
                        System.out.println("\n=== 演示多级恢复策略 ===");
                        System.out.print("输入要读取的文件名: ");
                        String filename = scanner.nextLine();
                        String fileContent = readWithMultiLevelRecovery(filename);
                        System.out.println("读取的内容: " + fileContent);
                        break;
                        
                    default:
                        System.out.println("无效的选择，请输入0-5之间的数字。");
                        break;
                }
            }
        } finally {
            scanner.close();
            
            // 关闭所有日志处理器
            for (Handler handler : LOGGER.getHandlers()) {
                handler.close();
            }
        }
    }
} 