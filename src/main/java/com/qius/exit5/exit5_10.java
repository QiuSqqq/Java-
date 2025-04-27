package com.qius.exit5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 问题10：编写一个程序模拟网络通信，处理可能发生的各种异常
 */
public class exit5_10 {
    
    // 连接超时（毫秒）
    private static final int CONNECTION_TIMEOUT = 5000;
    
    // 读取超时（毫秒）
    private static final int READ_TIMEOUT = 10000;
    
    /**
     * 网络连接客户端
     * 演示网络通信中的异常处理
     */
    public static class NetworkClient {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String serverAddress;
        private int serverPort;
        
        /**
         * 创建网络客户端
         * @param serverAddress 服务器地址
         * @param serverPort 服务器端口
         */
        public NetworkClient(String serverAddress, int serverPort) {
            this.serverAddress = serverAddress;
            this.serverPort = serverPort;
        }
        
        /**
         * 连接到服务器
         * @throws IOException 如果连接失败
         */
        public void connect() throws IOException {
            try {
                System.out.println("尝试连接到 " + serverAddress + ":" + serverPort + "...");
                
                // 创建套接字对象
                socket = new Socket();
                
                // 设置连接超时
                socket.connect(new InetSocketAddress(serverAddress, serverPort), CONNECTION_TIMEOUT);
                
                // 设置读取超时
                socket.setSoTimeout(READ_TIMEOUT);
                
                // 初始化输入输出流
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                System.out.println("成功连接到服务器");
                
            } catch (UnknownHostException e) {
                throw new IOException("无法解析主机名: " + serverAddress, e);
                
            } catch (ConnectException e) {
                throw new IOException("连接被拒绝: 服务器可能未运行或端口错误", e);
                
            } catch (SocketTimeoutException e) {
                throw new IOException("连接超时: 无法在 " + CONNECTION_TIMEOUT + " 毫秒内连接到服务器", e);
                
            } catch (IOException e) {
                throw new IOException("建立连接时发生IO错误: " + e.getMessage(), e);
            }
        }
        
        /**
         * 发送消息到服务器
         * @param message 要发送的消息
         * @throws IOException 如果发送失败
         */
        public void sendMessage(String message) throws IOException {
            checkConnection();
            
            try {
                System.out.println("发送消息: " + message);
                out.println(message);
                
                if (out.checkError()) {
                    throw new IOException("发送消息时发生错误");
                }
                
            } catch (Exception e) {
                throw new IOException("发送消息失败: " + e.getMessage(), e);
            }
        }
        
        /**
         * 从服务器接收响应
         * @return 接收到的响应
         * @throws IOException 如果接收失败
         */
        public String receiveResponse() throws IOException {
            checkConnection();
            
            try {
                System.out.println("等待服务器响应...");
                String response = in.readLine();
                
                if (response == null) {
                    throw new IOException("服务器关闭了连接");
                }
                
                System.out.println("收到响应: " + response);
                return response;
                
            } catch (SocketTimeoutException e) {
                throw new IOException("接收超时: 服务器在 " + READ_TIMEOUT + " 毫秒内未响应", e);
                
            } catch (SocketException e) {
                throw new IOException("接收消息时连接中断: " + e.getMessage(), e);
                
            } catch (IOException e) {
                throw new IOException("接收响应时发生IO错误: " + e.getMessage(), e);
            }
        }
        
        /**
         * 关闭连接
         */
        public void disconnect() {
            System.out.println("关闭连接...");
            
            try {
                if (out != null) {
                    out.close();
                }
                
                if (in != null) {
                    in.close();
                }
                
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                
                System.out.println("连接已关闭");
                
            } catch (IOException e) {
                System.err.println("关闭连接时发生错误: " + e.getMessage());
            }
        }
        
        /**
         * 检查连接是否建立
         * @throws IOException 如果连接未建立
         */
        private void checkConnection() throws IOException {
            if (socket == null || socket.isClosed() || !socket.isConnected()) {
                throw new IOException("未连接到服务器，请先调用connect()方法");
            }
        }
        
        /**
         * 检查连接是否仍然有效
         * @return 如果连接有效则返回true
         */
        public boolean isConnectionAlive() {
            if (socket == null || socket.isClosed()) {
                return false;
            }
            
            try {
                // 尝试发送一个空操作来检查连接
                socket.setSendBufferSize(socket.getSendBufferSize());
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
    
    /**
     * 模拟简单的回声服务器
     * 注意：这只是一个模拟，不是真实的服务器
     */
    public static class MockEchoServer {
        private final String address;
        private final int port;
        private boolean isRunning;
        
        public MockEchoServer(String address, int port) {
            this.address = address;
            this.port = port;
            this.isRunning = false;
        }
        
        public void start() {
            isRunning = true;
            System.out.println("模拟服务器已启动在 " + address + ":" + port);
        }
        
        public void stop() {
            isRunning = false;
            System.out.println("模拟服务器已停止");
        }
        
        public boolean isRunning() {
            return isRunning;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 网络通信异常处理演示 ===\n");
        
        Scanner scanner = new Scanner(System.in);
        NetworkClient client = null;
        MockEchoServer mockServer = null;
        
        try {
            // 创建模拟服务器
            System.out.println("=== 服务器配置 ===");
            System.out.print("输入服务器地址 (默认: localhost): ");
            String serverAddress = scanner.nextLine().trim();
            if (serverAddress.isEmpty()) {
                serverAddress = "localhost";
            }
            
            System.out.print("输入服务器端口 (默认: 8080): ");
            String portStr = scanner.nextLine().trim();
            int serverPort = portStr.isEmpty() ? 8080 : Integer.parseInt(portStr);
            
            mockServer = new MockEchoServer(serverAddress, serverPort);
            
            // 创建客户端
            client = new NetworkClient(serverAddress, serverPort);
            
            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== 网络客户端菜单 ===");
                System.out.println("1. 启动模拟服务器");
                System.out.println("2. 停止模拟服务器");
                System.out.println("3. 连接到服务器");
                System.out.println("4. 发送消息");
                System.out.println("5. 接收响应");
                System.out.println("6. 断开连接");
                System.out.println("7. 检查连接状态");
                System.out.println("8. 退出");
                System.out.print("请选择 (1-8): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // 消耗换行符
                
                switch (choice) {
                    case 1:
                        // 启动模拟服务器
                        mockServer.start();
                        break;
                        
                    case 2:
                        // 停止模拟服务器
                        mockServer.stop();
                        break;
                        
                    case 3:
                        // 连接到服务器
                        try {
                            client.connect();
                        } catch (IOException e) {
                            System.err.println("连接错误: " + e.getMessage());
                            if (e.getCause() != null) {
                                System.err.println("原因: " + e.getCause().getMessage());
                            }
                        }
                        break;
                        
                    case 4:
                        // 发送消息
                        try {
                            System.out.print("输入要发送的消息: ");
                            String message = scanner.nextLine();
                            client.sendMessage(message);
                        } catch (IOException e) {
                            System.err.println("发送错误: " + e.getMessage());
                        }
                        break;
                        
                    case 5:
                        // 接收响应
                        try {
                            // 在模拟服务器中，我们假装收到了一个响应
                            if (mockServer.isRunning()) {
                                System.out.println("模拟接收到响应: Echo: 您的消息");
                            } else {
                                throw new IOException("模拟服务器未运行，无法接收响应");
                            }
                        } catch (IOException e) {
                            System.err.println("接收错误: " + e.getMessage());
                        }
                        break;
                        
                    case 6:
                        // 断开连接
                        if (client != null) {
                            client.disconnect();
                        }
                        break;
                        
                    case 7:
                        // 检查连接状态
                        if (client != null) {
                            boolean isAlive = client.isConnectionAlive();
                            System.out.println("连接状态: " + (isAlive ? "有效" : "无效"));
                        } else {
                            System.out.println("客户端未初始化");
                        }
                        break;
                        
                    case 8:
                        // 退出
                        exit = true;
                        System.out.println("退出程序...");
                        break;
                        
                    default:
                        System.out.println("无效的选择，请输入1-8之间的数字");
                        break;
                }
            }
            
        } catch (NumberFormatException e) {
            System.err.println("输入的端口号无效: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("发生未预期的错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保资源被释放
            if (client != null) {
                client.disconnect();
            }
            
            if (mockServer != null && mockServer.isRunning()) {
                mockServer.stop();
            }
            
            scanner.close();
        }
    }
} 