package com.qius.exit5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 问题14：设计一个用于电子商务应用程序的完整异常类层次结构
 */
public class exit5_14 {
    
    //========== 异常层次结构 ==========
    
    /**
     * 电子商务系统异常的基类
     */
    public static class ECommerceException extends Exception {
        public ECommerceException(String message) {
            super(message);
        }
        
        public ECommerceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    //---------- 用户相关异常 ----------
    
    /**
     * 用户相关异常的基类
     */
    public static class UserException extends ECommerceException {
        public UserException(String message) {
            super(message);
        }
        
        public UserException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 用户认证失败异常
     */
    public static class AuthenticationException extends UserException {
        public AuthenticationException(String message) {
            super(message);
        }
        
        public AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 用户授权失败异常
     */
    public static class AuthorizationException extends UserException {
        private String requiredPermission;
        
        public AuthorizationException(String message, String requiredPermission) {
            super(message);
            this.requiredPermission = requiredPermission;
        }
        
        public String getRequiredPermission() {
            return requiredPermission;
        }
    }
    
    /**
     * 用户注册异常
     */
    public static class RegistrationException extends UserException {
        private List<String> validationErrors;
        
        public RegistrationException(String message) {
            super(message);
            this.validationErrors = new ArrayList<>();
        }
        
        public RegistrationException(String message, List<String> validationErrors) {
            super(message);
            this.validationErrors = validationErrors;
        }
        
        public List<String> getValidationErrors() {
            return validationErrors;
        }
        
        public void addValidationError(String error) {
            validationErrors.add(error);
        }
    }
    
    //---------- 商品相关异常 ----------
    
    /**
     * 商品相关异常的基类
     */
    public static class ProductException extends ECommerceException {
        private String productId;
        
        public ProductException(String message, String productId) {
            super(message);
            this.productId = productId;
        }
        
        public String getProductId() {
            return productId;
        }
    }
    
    /**
     * 商品不存在异常
     */
    public static class ProductNotFoundException extends ProductException {
        public ProductNotFoundException(String productId) {
            super("商品不存在: " + productId, productId);
        }
    }
    
    /**
     * 商品库存不足异常
     */
    public static class InsufficientStockException extends ProductException {
        private int requestedQuantity;
        private int availableQuantity;
        
        public InsufficientStockException(String productId, int requestedQuantity, int availableQuantity) {
            super("商品库存不足: 请求 " + requestedQuantity + ", 可用 " + availableQuantity, productId);
            this.requestedQuantity = requestedQuantity;
            this.availableQuantity = availableQuantity;
        }
        
        public int getRequestedQuantity() {
            return requestedQuantity;
        }
        
        public int getAvailableQuantity() {
            return availableQuantity;
        }
        
        public int getDeficit() {
            return requestedQuantity - availableQuantity;
        }
    }
    
    //---------- 订单相关异常 ----------
    
    /**
     * 订单相关异常的基类
     */
    public static class OrderException extends ECommerceException {
        private String orderId;
        
        public OrderException(String message, String orderId) {
            super(message);
            this.orderId = orderId;
        }
        
        public OrderException(String message, String orderId, Throwable cause) {
            super(message, cause);
            this.orderId = orderId;
        }
        
        public String getOrderId() {
            return orderId;
        }
    }
    
    /**
     * 订单不存在异常
     */
    public static class OrderNotFoundException extends OrderException {
        public OrderNotFoundException(String orderId) {
            super("订单不存在: " + orderId, orderId);
        }
    }
    
    /**
     * 订单状态异常
     */
    public static class InvalidOrderStateException extends OrderException {
        private String currentState;
        private String requiredState;
        
        public InvalidOrderStateException(String orderId, String currentState, String requiredState) {
            super("订单状态无效: 当前 " + currentState + ", 需要 " + requiredState, orderId);
            this.currentState = currentState;
            this.requiredState = requiredState;
        }
        
        public String getCurrentState() {
            return currentState;
        }
        
        public String getRequiredState() {
            return requiredState;
        }
    }
    
    //---------- 支付相关异常 ----------
    
    /**
     * 支付相关异常的基类
     */
    public static class PaymentException extends ECommerceException {
        private String paymentId;
        
        public PaymentException(String message, String paymentId) {
            super(message);
            this.paymentId = paymentId;
        }
        
        public PaymentException(String message, String paymentId, Throwable cause) {
            super(message, cause);
            this.paymentId = paymentId;
        }
        
        public String getPaymentId() {
            return paymentId;
        }
    }
    
    /**
     * 支付失败异常
     */
    public static class PaymentFailedException extends PaymentException {
        private String reason;
        
        public PaymentFailedException(String paymentId, String reason) {
            super("支付失败: " + reason, paymentId);
            this.reason = reason;
        }
        
        public PaymentFailedException(String paymentId, String reason, Throwable cause) {
            super("支付失败: " + reason, paymentId, cause);
            this.reason = reason;
        }
        
        public String getReason() {
            return reason;
        }
    }
    
    /**
     * 退款失败异常
     */
    public static class RefundFailedException extends PaymentException {
        private String reason;
        
        public RefundFailedException(String paymentId, String reason) {
            super("退款失败: " + reason, paymentId);
            this.reason = reason;
        }
        
        public String getReason() {
            return reason;
        }
    }
    
    //---------- 配送相关异常 ----------
    
    /**
     * 配送相关异常的基类
     */
    public static class ShippingException extends ECommerceException {
        private String shippingId;
        
        public ShippingException(String message, String shippingId) {
            super(message);
            this.shippingId = shippingId;
        }
        
        public String getShippingId() {
            return shippingId;
        }
    }
    
    /**
     * 无效地址异常
     */
    public static class InvalidAddressException extends ShippingException {
        private String address;
        
        public InvalidAddressException(String shippingId, String address) {
            super("无效的配送地址: " + address, shippingId);
            this.address = address;
        }
        
        public String getAddress() {
            return address;
        }
    }
    
    /**
     * 配送不可用异常
     */
    public static class ShippingUnavailableException extends ShippingException {
        private String region;
        
        public ShippingUnavailableException(String shippingId, String region) {
            super("该地区不支持配送: " + region, shippingId);
            this.region = region;
        }
        
        public String getRegion() {
            return region;
        }
    }
    
    //---------- 系统相关异常 ----------
    
    /**
     * 系统相关异常的基类
     */
    public static class SystemException extends ECommerceException {
        public SystemException(String message) {
            super(message);
        }
        
        public SystemException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 数据库异常
     */
    public static class DatabaseException extends SystemException {
        public DatabaseException(String message) {
            super(message);
        }
        
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 外部服务异常
     */
    public static class ExternalServiceException extends SystemException {
        private String serviceName;
        
        public ExternalServiceException(String message, String serviceName) {
            super(message);
            this.serviceName = serviceName;
        }
        
        public ExternalServiceException(String message, String serviceName, Throwable cause) {
            super(message, cause);
            this.serviceName = serviceName;
        }
        
        public String getServiceName() {
            return serviceName;
        }
    }
    
    //========== 示例服务实现 ==========
    
    /**
     * 用户服务
     */
    public static class UserService {
        
        public void login(String username, String password) throws AuthenticationException {
            if ("admin".equals(username) && "admin123".equals(password)) {
                System.out.println("登录成功");
            } else {
                throw new AuthenticationException("用户名或密码不正确");
            }
        }
        
        public void register(String username, String password, String email) throws RegistrationException {
            RegistrationException exception = new RegistrationException("注册信息验证失败");
            
            // 验证用户名
            if (username == null || username.length() < 3) {
                exception.addValidationError("用户名必须至少包含3个字符");
            }
            
            // 验证密码
            if (password == null || password.length() < 6) {
                exception.addValidationError("密码必须至少包含6个字符");
            }
            
            // 验证邮箱
            if (email == null || !email.contains("@")) {
                exception.addValidationError("邮箱格式不正确");
            }
            
            // 如果有验证错误，抛出异常
            if (!exception.getValidationErrors().isEmpty()) {
                throw exception;
            }
            
            System.out.println("注册成功");
        }
        
        public void performAdminAction() throws AuthorizationException {
            // 假设当前用户没有管理员权限
            throw new AuthorizationException("需要管理员权限才能执行此操作", "ADMIN");
        }
    }
    
    /**
     * 商品服务
     */
    public static class ProductService {
        
        public void getProductDetails(String productId) throws ProductNotFoundException {
            if ("P12345".equals(productId)) {
                System.out.println("商品信息: 高品质T恤，价格: ¥99");
            } else {
                throw new ProductNotFoundException(productId);
            }
        }
        
        public void checkStock(String productId, int quantity) throws ProductException {
            if ("P12345".equals(productId)) {
                int availableStock = 10;
                if (quantity > availableStock) {
                    throw new InsufficientStockException(productId, quantity, availableStock);
                }
                System.out.println("库存充足，可以购买");
            } else {
                throw new ProductNotFoundException(productId);
            }
        }
    }
    
    /**
     * 订单服务
     */
    public static class OrderService {
        
        public void placeOrder(String productId, int quantity) throws ECommerceException {
            try {
                // 检查库存
                ProductService productService = new ProductService();
                productService.checkStock(productId, quantity);
                
                // 如果库存充足，创建订单
                String orderId = "ORD-" + System.currentTimeMillis();
                System.out.println("订单已创建: " + orderId);
                
                // 假设这里调用了支付服务
                PaymentService paymentService = new PaymentService();
                try {
                    paymentService.processPayment(orderId, 99 * quantity);
                } catch (PaymentException e) {
                    // 如果支付失败，抛出订单异常，并包含原始支付异常作为原因
                    throw new OrderException("由于支付问题无法完成订单", orderId, e);
                }
                
            } catch (ProductNotFoundException e) {
                throw e;  // 直接重新抛出
            } catch (InsufficientStockException e) {
                throw e;  // 直接重新抛出
            }
        }
        
        public void cancelOrder(String orderId) throws OrderException {
            if (orderId.startsWith("ORD-")) {
                // 假设这是一个已完成的订单，不能取消
                throw new InvalidOrderStateException(orderId, "已完成", "待处理");
            } else {
                throw new OrderNotFoundException(orderId);
            }
        }
    }
    
    /**
     * 支付服务
     */
    public static class PaymentService {
        
        public void processPayment(String orderId, double amount) throws PaymentException {
            try {
                // 假设这里调用了外部支付网关
                if (amount > 500) {
                    throw new IOException("外部支付网关连接失败");
                }
                
                String paymentId = "PAY-" + System.currentTimeMillis();
                System.out.println("支付成功: " + paymentId + ", 金额: ¥" + amount);
                
            } catch (IOException e) {
                // 将底层IO异常包装为支付异常
                throw new PaymentFailedException("PAY-FAILED", "无法连接支付网关", e);
            }
        }
        
        public void refundPayment(String paymentId) throws RefundFailedException {
            if (paymentId.equals("PAY-INVALID")) {
                throw new RefundFailedException(paymentId, "无效的支付ID");
            }
            System.out.println("退款成功: " + paymentId);
        }
    }
    
    /**
     * 配送服务
     */
    public static class ShippingService {
        
        public void arrangeShipping(String orderId, String address, String region) throws ShippingException {
            String shippingId = "SHP-" + System.currentTimeMillis();
            
            // 验证地址
            if (address == null || address.trim().isEmpty()) {
                throw new InvalidAddressException(shippingId, address);
            }
            
            // 检查配送区域
            if ("国外".equals(region)) {
                throw new ShippingUnavailableException(shippingId, region);
            }
            
            System.out.println("配送已安排: " + shippingId + ", 地址: " + address);
        }
    }
    
    //========== 示例异常处理 ==========
    
    /**
     * 异常处理实用程序
     */
    public static class ExceptionHandler {
        
        public static void handleUserException(UserException e) {
            System.err.println("\n发生用户相关异常:");
            
            if (e instanceof AuthenticationException) {
                System.err.println("认证失败: " + e.getMessage());
                System.err.println("请检查您的用户名和密码，并重试");
                
            } else if (e instanceof AuthorizationException) {
                AuthorizationException authEx = (AuthorizationException) e;
                System.err.println("授权失败: " + e.getMessage());
                System.err.println("需要权限: " + authEx.getRequiredPermission());
                
            } else if (e instanceof RegistrationException) {
                RegistrationException regEx = (RegistrationException) e;
                System.err.println("注册失败: " + e.getMessage());
                System.err.println("验证错误:");
                for (String error : regEx.getValidationErrors()) {
                    System.err.println("- " + error);
                }
                
            } else {
                System.err.println("用户错误: " + e.getMessage());
            }
        }
        
        public static void handleProductException(ProductException e) {
            System.err.println("\n发生商品相关异常:");
            System.err.println("商品ID: " + e.getProductId());
            
            if (e instanceof ProductNotFoundException) {
                System.err.println("商品不存在，请检查商品ID是否正确");
                
            } else if (e instanceof InsufficientStockException) {
                InsufficientStockException stockEx = (InsufficientStockException) e;
                System.err.println("库存不足: ");
                System.err.println("- 请求数量: " + stockEx.getRequestedQuantity());
                System.err.println("- 可用数量: " + stockEx.getAvailableQuantity());
                System.err.println("- 缺少数量: " + stockEx.getDeficit());
                
            } else {
                System.err.println("商品错误: " + e.getMessage());
            }
        }
        
        public static void handleOrderException(OrderException e) {
            System.err.println("\n发生订单相关异常:");
            System.err.println("订单ID: " + e.getOrderId());
            
            if (e instanceof OrderNotFoundException) {
                System.err.println("订单不存在，请检查订单ID是否正确");
                
            } else if (e instanceof InvalidOrderStateException) {
                InvalidOrderStateException stateEx = (InvalidOrderStateException) e;
                System.err.println("订单状态无效: ");
                System.err.println("- 当前状态: " + stateEx.getCurrentState());
                System.err.println("- 需要状态: " + stateEx.getRequiredState());
                
            } else {
                System.err.println("订单错误: " + e.getMessage());
                
                // 检查是否有原因异常
                Throwable cause = e.getCause();
                if (cause != null) {
                    System.err.println("原因: " + cause.getMessage());
                }
            }
        }
        
        public static void handleECommerceException(ECommerceException e) {
            if (e instanceof UserException) {
                handleUserException((UserException) e);
            } else if (e instanceof ProductException) {
                handleProductException((ProductException) e);
            } else if (e instanceof OrderException) {
                handleOrderException((OrderException) e);
            } else if (e instanceof PaymentException) {
                System.err.println("\n发生支付相关异常: " + e.getMessage());
            } else if (e instanceof ShippingException) {
                System.err.println("\n发生配送相关异常: " + e.getMessage());
            } else if (e instanceof SystemException) {
                System.err.println("\n发生系统相关异常: " + e.getMessage());
            } else {
                System.err.println("\n发生电子商务异常: " + e.getMessage());
            }
        }
    }
    
    //========== 主程序 ==========
    
    public static void main(String[] args) {
        System.out.println("=== 电子商务异常层次结构演示 ===");
        
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        ShippingService shippingService = new ShippingService();
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n选择要演示的场景:");
                System.out.println("1. 用户登录（成功）");
                System.out.println("2. 用户登录（失败）");
                System.out.println("3. 用户注册（验证失败）");
                System.out.println("4. 执行管理员操作（授权失败）");
                System.out.println("5. 获取商品详情（成功）");
                System.out.println("6. 获取商品详情（不存在）");
                System.out.println("7. 检查库存（库存不足）");
                System.out.println("8. 下订单（成功）");
                System.out.println("9. 下订单（商品不存在）");
                System.out.println("10. 下订单（库存不足）");
                System.out.println("11. 取消订单（状态无效）");
                System.out.println("12. 安排配送（区域不可用）");
                System.out.println("0. 退出");
                
                System.out.print("请选择 (0-12): ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // 消耗换行符
                
                if (choice == 0) {
                    System.out.println("退出程序。");
                    break;
                }
                
                try {
                    switch (choice) {
                        case 1:
                            userService.login("admin", "admin123");
                            break;
                            
                        case 2:
                            userService.login("admin", "wrongpassword");
                            break;
                            
                        case 3:
                            userService.register("ab", "123", "invalidemail");
                            break;
                            
                        case 4:
                            userService.performAdminAction();
                            break;
                            
                        case 5:
                            productService.getProductDetails("P12345");
                            break;
                            
                        case 6:
                            productService.getProductDetails("INVALID");
                            break;
                            
                        case 7:
                            productService.checkStock("P12345", 15);
                            break;
                            
                        case 8:
                            orderService.placeOrder("P12345", 2);
                            break;
                            
                        case 9:
                            orderService.placeOrder("INVALID", 1);
                            break;
                            
                        case 10:
                            orderService.placeOrder("P12345", 15);
                            break;
                            
                        case 11:
                            orderService.cancelOrder("ORD-123456");
                            break;
                            
                        case 12:
                            shippingService.arrangeShipping("ORD-123456", "北京市海淀区", "国外");
                            break;
                            
                        default:
                            System.out.println("无效的选择，请输入0-12之间的数字。");
                            break;
                    }
                } catch (ECommerceException e) {
                    ExceptionHandler.handleECommerceException(e);
                }
            }
        } finally {
            scanner.close();
        }
    }
} 