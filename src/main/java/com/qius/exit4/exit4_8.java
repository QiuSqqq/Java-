package com.qius.exit4;

/**
 * 问题8：定义一个枚举类型，包含8种主要颜色的组合（BLACK、RED、BLUE、GREEN、CYAN、
 * MAGENTA、YELLOW和WHITE），并包含getRed、getGreen和getBlue方法。
 */
public class exit4_8 {
    public static void main(String[] args) {
        // 测试所有颜色
        System.out.println("=== 测试所有颜色的RGB值 ===");
        for (Color color : Color.values()) {
            System.out.printf("%s: RGB(%d, %d, %d)%n", 
                color.name(), color.getRed(), color.getGreen(), color.getBlue());
        }
        
        // 测试颜色混合
        System.out.println("\n=== 测试颜色混合 ===");
        
        // 红色 + 绿色 = 黄色
        Color red = Color.RED;
        Color green = Color.GREEN;
        Color mixed = red.mix(green);
        System.out.printf("%s + %s = %s%n", red, green, mixed);
        
        // 红色 + 蓝色 = 品红
        mixed = red.mix(Color.BLUE);
        System.out.printf("%s + %s = %s%n", red, Color.BLUE, mixed);
        
        // 绿色 + 蓝色 = 青色
        mixed = green.mix(Color.BLUE);
        System.out.printf("%s + %s = %s%n", green, Color.BLUE, mixed);
        
        // 红色 + 绿色 + 蓝色 = 白色
        mixed = red.mix(green).mix(Color.BLUE);
        System.out.printf("%s + %s + %s = %s%n", red, green, Color.BLUE, mixed);
        
        // 测试互补色
        System.out.println("\n=== 测试互补色 ===");
        for (Color color : Color.values()) {
            System.out.printf("%s的互补色是: %s%n", color, color.getComplement());
        }
    }
}

/**
 * 表示8种主要颜色的枚举
 * 
 * 包含以下颜色：BLACK, RED, BLUE, GREEN, CYAN, MAGENTA, YELLOW, WHITE
 * 
 * 每种颜色都有对应的RGB值，范围为0-255
 */
enum Color {
    // 定义8种主要颜色，使用RGB值初始化
    BLACK(0, 0, 0),        // 黑色 (0,0,0)
    RED(255, 0, 0),        // 红色 (255,0,0)
    GREEN(0, 255, 0),      // 绿色 (0,255,0)
    BLUE(0, 0, 255),       // 蓝色 (0,0,255)
    CYAN(0, 255, 255),     // 青色 (0,255,255)
    MAGENTA(255, 0, 255),  // 品红 (255,0,255)
    YELLOW(255, 255, 0),   // 黄色 (255,255,0)
    WHITE(255, 255, 255);  // 白色 (255,255,255)
    
    // RGB颜色分量
    private final int red;
    private final int green;
    private final int blue;
    
    /**
     * 构造一个颜色
     * @param red 红色分量 (0-255)
     * @param green 绿色分量 (0-255)
     * @param blue 蓝色分量 (0-255)
     */
    private Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    /**
     * 获取红色分量
     * @return 红色分量 (0-255)
     */
    public int getRed() {
        return red;
    }
    
    /**
     * 获取绿色分量
     * @return 绿色分量 (0-255)
     */
    public int getGreen() {
        return green;
    }
    
    /**
     * 获取蓝色分量
     * @return 蓝色分量 (0-255)
     */
    public int getBlue() {
        return blue;
    }
    
    /**
     * 获取颜色的互补色
     * 互补色是将RGB值取反(255-值)得到的颜色
     * @return 互补色
     */
    public Color getComplement() {
        // 计算互补色的RGB值
        int complementRed = 255 - red;
        int complementGreen = 255 - green;
        int complementBlue = 255 - blue;
        
        // 查找最接近计算出的RGB值的已定义颜色
        return findClosestColor(complementRed, complementGreen, complementBlue);
    }
    
    /**
     * 将两种颜色混合
     * 混合是通过取两个颜色RGB值的最大值来实现的
     * @param other 要混合的另一种颜色
     * @return 混合后的颜色
     */
    public Color mix(Color other) {
        // 计算混合颜色的RGB值（取两个颜色各分量的最大值）
        int mixedRed = Math.max(red, other.red);
        int mixedGreen = Math.max(green, other.green);
        int mixedBlue = Math.max(blue, other.blue);
        
        // 查找最接近计算出的RGB值的已定义颜色
        return findClosestColor(mixedRed, mixedGreen, mixedBlue);
    }
    
    /**
     * 查找最接近给定RGB值的预定义颜色
     * @param r 红色分量
     * @param g 绿色分量
     * @param b 蓝色分量
     * @return 最接近的预定义颜色
     */
    private static Color findClosestColor(int r, int g, int b) {
        // 对于标准的8色，只有0和255两个值，所以可以直接根据每个分量是否超过阈值(比如128)来确定最接近的颜色
        
        // 为简化处理，我们将RGB值二值化（小于128为0，否则为255）
        boolean redComponent = r >= 128;
        boolean greenComponent = g >= 128;
        boolean blueComponent = b >= 128;
        
        // 根据三个分量的二值组合确定颜色（共8种组合对应8种颜色）
        if (!redComponent && !greenComponent && !blueComponent) return BLACK;
        if (redComponent && !greenComponent && !blueComponent) return RED;
        if (!redComponent && greenComponent && !blueComponent) return GREEN;
        if (!redComponent && !greenComponent && blueComponent) return BLUE;
        if (!redComponent && greenComponent && blueComponent) return CYAN;
        if (redComponent && !greenComponent && blueComponent) return MAGENTA;
        if (redComponent && greenComponent && !blueComponent) return YELLOW;
        return WHITE; // 所有分量都为true
    }
} 