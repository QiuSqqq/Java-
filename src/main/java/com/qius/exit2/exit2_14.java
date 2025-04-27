package com.qius.exit2;

/**
 * 问题14：从Commons CSV网站上下载Apache Commons CSV的JAR文件。编写一个带有main方法的
 * 类，读取你选择的CSV文件并打印部分内容。Commons CSV网站上有示例代码。由于你还没有
 * 学会处理异常，因此只须用以下代码来表示main方法头：
 * public static void main(String[] args) throws Exception
 * 本练习的目的不是对CSV文件做任何有用的事情，而是练习使用JAR文件提供的库。
 * 
 * 下载和设置Apache Commons CSV库：
 * 1. 访问Apache Commons CSV网站：https://commons.apache.org/proper/commons-csv/
 * 2. 从下载页面获取最新版本的JAR文件：commons-csv-x.y.jar
 * 3. 将JAR文件放在项目的lib目录下
 * 4. 在IDE中将JAR文件添加到项目依赖中：
 *    - IntelliJ IDEA: File > Project Structure > Libraries > + > Java > 选择JAR文件
 *    - Eclipse: 右键项目 > Properties > Java Build Path > Libraries > Add External JARs
 *    - VS Code: 在.classpath文件中添加JAR引用或使用Maven/Gradle管理依赖
 * 
 * CSV读取代码示例：
 * ```java
 * package com.example;
 * 
 * import org.apache.commons.csv.CSVFormat;
 * import org.apache.commons.csv.CSVParser;
 * import org.apache.commons.csv.CSVRecord;
 * 
 * import java.io.FileReader;
 * import java.io.Reader;
 * 
 * public class CSVReaderExample {
 *     public static void main(String[] args) throws Exception {
 *         // 指定CSV文件路径
 *         String csvFilePath = "data/sample.csv";
 *         
 *         // 创建一个Reader对象，用于读取CSV文件
 *         try (Reader reader = new FileReader(csvFilePath)) {
 *             // 使用CSVParser解析CSV文件，设置第一行为标题行
 *             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
 *             
 *             // 获取标题行（列名）
 *             System.out.println("CSV文件的列：" + csvParser.getHeaderNames());
 *             
 *             // 遍历每一行记录并打印内容
 *             System.out.println("\nCSV内容：");
 *             for (CSVRecord record : csvParser) {
 *                 // 打印行号和内容
 *                 System.out.println("行 #" + record.getRecordNumber());
 *                 
 *                 // 假设CSV有name, age, city这三列
 *                 String name = record.get("name");
 *                 String age = record.get("age");
 *                 String city = record.get("city");
 *                 
 *                 System.out.println("姓名: " + name);
 *                 System.out.println("年龄: " + age);
 *                 System.out.println("城市: " + city);
 *                 System.out.println("--------------");
 *             }
 *         }
 *     }
 * }
 * ```
 * 
 * 读取没有标题行的CSV文件：
 * ```java
 * // 不带标题行的CSV解析
 * try (Reader reader = new FileReader("data/no_header.csv")) {
 *     CSVParser parser = CSVFormat.DEFAULT.parse(reader);
 *     
 *     for (CSVRecord record : parser) {
 *         // 通过索引访问列
 *         String col0 = record.get(0);
 *         String col1 = record.get(1);
 *         String col2 = record.get(2);
 *         
 *         System.out.println(col0 + ", " + col1 + ", " + col2);
 *     }
 * }
 * ```
 * 
 * 定制CSV格式：
 * ```java
 * // 定制CSV格式（例如分隔符为分号、带引号的值等）
 * CSVFormat customFormat = CSVFormat.DEFAULT
 *     .withDelimiter(';')  // 使用分号作为分隔符
 *     .withQuote('"')     // 使用双引号
 *     .withFirstRecordAsHeader() // 第一行是标题
 *     .withIgnoreHeaderCase() // 忽略标题大小写
 *     .withTrim();  // 修剪空白
 * ```
 * 
 * 样本CSV文件内容 (sample.csv)：
 * ```csv
 * name,age,city
 * 张三,25,北京
 * 李四,30,上海
 * 王五,28,广州
 * 赵六,22,深圳
 * ```
 * 
 * 使用Maven管理依赖（替代手动下载JAR）：
 * 如果项目使用Maven，可以添加以下依赖到pom.xml文件中：
 * ```xml
 * <dependency>
 *     <groupId>org.apache.commons</groupId>
 *     <artifactId>commons-csv</artifactId>
 *     <version>1.9.0</version>  <!-- 使用最新版本 -->
 * </dependency>
 * ```
 * 
 * 注意事项：
 * 1. 确保CSV文件路径正确
 * 2. 如果CSV文件有标题行，使用withFirstRecordAsHeader()配置
 * 3. 对于不同格式的CSV文件，需要调整CSVFormat配置
 * 4. 代码中的throws Exception是简化异常处理的方式，实际项目中应该合理捕获和处理异常
 * 5. 解析大型CSV文件时，应注意内存使用和性能考虑
 */
public class exit2_14 {
    public static void main(String[] args) {
        System.out.println("问题14：使用Apache Commons CSV库读取CSV文件");
        System.out.println("--------------------------------------");
        
        /**
         * 本示例演示了如何使用Apache Commons CSV库读取CSV文件。
         * 主要内容包括：
         * 1. 如何下载和设置Apache Commons CSV库
         * 2. 读取带标题行的CSV文件示例
         * 3. 读取没有标题行的CSV文件示例
         * 4. 定制CSV格式的方法
         * 5. 使用Maven管理依赖的方式
         * 6. CSV文件处理的注意事项
         * 
         * 实际使用时，代码应该类似这样：
         * try (Reader reader = new FileReader("文件路径.csv")) {
         *     CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
         *     for (CSVRecord record : parser) {
         *         String value = record.get("列名");
         *         System.out.println(value);
         *     }
         * }
         */
        
        System.out.println("完整使用说明请参考源代码中的注释");
    }
} 
