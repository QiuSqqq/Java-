package com.qius.exit5;

/**
 * 问题9：设计一个异常层次结构，用于银行账户操作
 * 创建一个基本的BankException，然后是专门的子类
 */
public class exit5_9 {
    
    /**
     * 银行异常的基类
     */
    public static class BankException extends Exception {
        public BankException(String message) {
            super(message);
        }
        
        public BankException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    /**
     * 表示账户不存在的异常
     */
    public static class AccountNotFoundException extends BankException {
        private String accountId;
        
        public AccountNotFoundException(String accountId) {
            super("无法找到账户: " + accountId);
            this.accountId = accountId;
        }
        
        public String getAccountId() {
            return accountId;
        }
    }
    
    /**
     * 表示余额不足的异常
     */
    public static class InsufficientFundsException extends BankException {
        private String accountId;
        private double requestedAmount;
        private double availableBalance;
        
        public InsufficientFundsException(String accountId, double requestedAmount, double availableBalance) {
            super("账户 " + accountId + " 余额不足: 请求 " + requestedAmount + ", 可用 " + availableBalance);
            this.accountId = accountId;
            this.requestedAmount = requestedAmount;
            this.availableBalance = availableBalance;
        }
        
        public String getAccountId() {
            return accountId;
        }
        
        public double getRequestedAmount() {
            return requestedAmount;
        }
        
        public double getAvailableBalance() {
            return availableBalance;
        }
        
        public double getDeficit() {
            return requestedAmount - availableBalance;
        }
    }
    
    /**
     * 表示超过每日取款限额的异常
     */
    public static class DailyLimitExceededException extends BankException {
        private String accountId;
        private double requestedAmount;
        private double dailyLimit;
        private double dailyUsed;
        
        public DailyLimitExceededException(String accountId, double requestedAmount, 
                                          double dailyLimit, double dailyUsed) {
            super("账户 " + accountId + " 超过每日限额: 请求 " + requestedAmount + 
                  ", 限额 " + dailyLimit + ", 已使用 " + dailyUsed);
            this.accountId = accountId;
            this.requestedAmount = requestedAmount;
            this.dailyLimit = dailyLimit;
            this.dailyUsed = dailyUsed;
        }
        
        public String getAccountId() {
            return accountId;
        }
        
        public double getRequestedAmount() {
            return requestedAmount;
        }
        
        public double getDailyLimit() {
            return dailyLimit;
        }
        
        public double getDailyUsed() {
            return dailyUsed;
        }
        
        public double getAvailableLimit() {
            return dailyLimit - dailyUsed;
        }
    }
    
    /**
     * 表示账户已被冻结或不活跃的异常
     */
    public static class AccountInactiveException extends BankException {
        private String accountId;
        private String status;
        
        public AccountInactiveException(String accountId, String status) {
            super("账户 " + accountId + " 状态不活跃: " + status);
            this.accountId = accountId;
            this.status = status;
        }
        
        public String getAccountId() {
            return accountId;
        }
        
        public String getStatus() {
            return status;
        }
    }
    
    /**
     * 表示无效交易的异常
     */
    public static class InvalidTransactionException extends BankException {
        private String transactionId;
        private String reason;
        
        public InvalidTransactionException(String transactionId, String reason) {
            super("交易 " + transactionId + " 无效: " + reason);
            this.transactionId = transactionId;
            this.reason = reason;
        }
        
        public String getTransactionId() {
            return transactionId;
        }
        
        public String getReason() {
            return reason;
        }
    }
    
    /**
     * 示例银行账户类，使用我们的异常层次结构
     */
    public static class BankAccount {
        private String accountId;
        private double balance;
        private boolean active;
        private double dailyWithdrawalLimit;
        private double dailyWithdrawalUsed;
        
        public BankAccount(String accountId, double initialBalance, double dailyWithdrawalLimit) {
            this.accountId = accountId;
            this.balance = initialBalance;
            this.active = true;
            this.dailyWithdrawalLimit = dailyWithdrawalLimit;
            this.dailyWithdrawalUsed = 0.0;
        }
        
        /**
         * 存款到账户
         * @param amount 存款金额
         * @throws BankException 如果账户不活跃或存款金额为负
         */
        public void deposit(double amount) throws BankException {
            if (!active) {
                throw new AccountInactiveException(accountId, "冻结");
            }
            
            if (amount <= 0) {
                throw new InvalidTransactionException("DEPOSIT-" + System.currentTimeMillis(), 
                                                     "存款金额必须为正数");
            }
            
            // 执行存款
            balance += amount;
            System.out.println("存款成功: " + amount + " 到账户 " + accountId);
            System.out.println("当前余额: " + balance);
        }
        
        /**
         * 从账户取款
         * @param amount 取款金额
         * @throws BankException 如果取款无法完成
         */
        public void withdraw(double amount) throws BankException {
            if (!active) {
                throw new AccountInactiveException(accountId, "冻结");
            }
            
            if (amount <= 0) {
                throw new InvalidTransactionException("WITHDRAW-" + System.currentTimeMillis(), 
                                                     "取款金额必须为正数");
            }
            
            if (balance < amount) {
                throw new InsufficientFundsException(accountId, amount, balance);
            }
            
            if (dailyWithdrawalUsed + amount > dailyWithdrawalLimit) {
                throw new DailyLimitExceededException(accountId, amount, 
                                                     dailyWithdrawalLimit, dailyWithdrawalUsed);
            }
            
            // 执行取款
            balance -= amount;
            dailyWithdrawalUsed += amount;
            System.out.println("取款成功: " + amount + " 从账户 " + accountId);
            System.out.println("当前余额: " + balance);
            System.out.println("今日已取款: " + dailyWithdrawalUsed + " / " + dailyWithdrawalLimit);
        }
        
        /**
         * 转账到另一个账户
         * @param targetAccount 目标账户
         * @param amount 转账金额
         * @throws BankException 如果转账无法完成
         */
        public void transfer(BankAccount targetAccount, double amount) throws BankException {
            if (targetAccount == null) {
                throw new AccountNotFoundException("未知");
            }
            
            if (!active) {
                throw new AccountInactiveException(accountId, "冻结");
            }
            
            if (!targetAccount.isActive()) {
                throw new AccountInactiveException(targetAccount.getAccountId(), "冻结");
            }
            
            if (amount <= 0) {
                throw new InvalidTransactionException("TRANSFER-" + System.currentTimeMillis(), 
                                                     "转账金额必须为正数");
            }
            
            if (balance < amount) {
                throw new InsufficientFundsException(accountId, amount, balance);
            }
            
            // 执行转账（简化版本，真实世界应该使用事务）
            balance -= amount;
            targetAccount.receiveTransfer(amount);
            System.out.println("转账成功: " + amount + " 从账户 " + accountId + 
                              " 到账户 " + targetAccount.getAccountId());
            System.out.println("当前余额: " + balance);
        }
        
        /**
         * 接收转入的金额
         * @param amount 收到的金额
         */
        private void receiveTransfer(double amount) {
            balance += amount;
        }
        
        /**
         * 冻结或解冻账户
         * @param active 是否活跃
         */
        public void setActive(boolean active) {
            this.active = active;
            System.out.println("账户 " + accountId + " 已" + (active ? "激活" : "冻结"));
        }
        
        /**
         * 检查账户是否活跃
         * @return 账户状态
         */
        public boolean isActive() {
            return active;
        }
        
        /**
         * 获取账户ID
         * @return 账户ID
         */
        public String getAccountId() {
            return accountId;
        }
        
        /**
         * 获取当前余额
         * @return 当前余额
         */
        public double getBalance() {
            return balance;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 银行异常层次结构示例 ===\n");
        
        // 创建银行账户
        BankAccount account1 = new BankAccount("A-12345", 1000.0, 2000.0);
        BankAccount account2 = new BankAccount("B-67890", 500.0, 1000.0);
        
        try {
            // 示例1：成功存款
            System.out.println("\n== 示例1：成功存款 ==");
            account1.deposit(500.0);
            
            // 示例2：成功取款
            System.out.println("\n== 示例2：成功取款 ==");
            account1.withdraw(200.0);
            
            // 示例3：余额不足
            System.out.println("\n== 示例3：余额不足 ==");
            try {
                account2.withdraw(1000.0);
            } catch (InsufficientFundsException e) {
                System.err.println("错误: " + e.getMessage());
                System.err.println("缺口: " + e.getDeficit());
            }
            
            // 示例4：超过每日限额
            System.out.println("\n== 示例4：超过每日限额 ==");
            try {
                account1.withdraw(1500.0);
            } catch (DailyLimitExceededException e) {
                System.err.println("错误: " + e.getMessage());
                System.err.println("剩余可用限额: " + e.getAvailableLimit());
            }
            
            // 示例5：冻结账户后操作
            System.out.println("\n== 示例5：冻结账户后操作 ==");
            account2.setActive(false);
            try {
                account2.deposit(100.0);
            } catch (AccountInactiveException e) {
                System.err.println("错误: " + e.getMessage());
            }
            
            // 示例6：成功转账
            System.out.println("\n== 示例6：成功转账 ==");
            account2.setActive(true);  // 重新激活账户
            account1.transfer(account2, 300.0);
            
            // 示例7：无效交易金额
            System.out.println("\n== 示例7：无效交易金额 ==");
            try {
                account1.withdraw(-50.0);
            } catch (InvalidTransactionException e) {
                System.err.println("错误: " + e.getMessage());
            }
            
        } catch (BankException e) {
            // 捕获所有其他可能的银行异常
            System.err.println("银行操作错误: " + e.getMessage());
        }
    }
} 