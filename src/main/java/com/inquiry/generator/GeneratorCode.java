package com.inquiry.generator;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;


/**
 * @author mky
 * @Description
 * @date 2018/9/6 23:01
 * @lastModifier
 */
public class GeneratorCode {

    //项目在硬盘上的基础路径
    private static String PROJECT_PATH = System.getProperty("user.dir");
    //自动去除表前缀
    public static String AUTO_REMOVE_PRE = "true";
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_DATABASE = "onlineSurvey";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + JDBC_DATABASE;
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "852456130";
    //    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + JDBC_DATABASE;
//    private static final String JDBC_USERNAME = "root";
//    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";


    public static void main(String[] args) throws Exception {
        genCodeByTableNames("admin", "base_user","inquiry","inquiry_record");
    }

    public static void genCodeByTableNames(String platformUrl, String... tableNames) throws Exception {
        for (String tableName : tableNames) {
            //根据需求生成，不需要的注掉，模板有问题的话可以自己修改。
            genModelAndMapper(platformUrl, tableName); // 生成model和mapper
            genService(platformUrl, tableName); // 生成service
//            genController(platformUrl, tableName); // 生成controller
        }
    }

    public static void genService(String platformUrl, String tableName) throws Exception {
        List<String> templates = new ArrayList<String>();
        templates.add("generator/Service.java.vm");
        templates.add("generator/ServiceImpl.java.vm");
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        Map table = getTable(tableName);
        tableDO.setTableName(tableName);
        tableDO.setComments((String) table.get("tableComment"));
        //表名转换成Java类名
        String className = tableName;
        tableDO.setClassName(StringUtil.snakeToCapHump(className));
        tableDO.setClassname(StringUtils.uncapitalize(StringUtil.snakeToCapHump(className)));

        //列信息
        List<Map<String, String>> columns = getColumnsByTableName(tableName);
        List<ColumnDO> columnsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = StringUtil.snakeToCapHump(columnDO.getColumnName());
            // String attrName = columnDO.getColumnName();
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columnsList.add(columnDO);
        }
        tableDO.setColumns(columnsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("pathName", config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("platformUrl", platformUrl);
        map.put("platFormUrl", columnToJava(platformUrl));
        map.put("datetime", DateUtils.format(new java.util.Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //渲染模板
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            // System.out.println(sw.toString());

            String fileName = getFileName(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package"), platformUrl);

            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Files.write(Paths.get(fileName), sw.toString().getBytes());

            System.out.println(
                    tableDO.getTableName() + "Service.java 生成成功\r\n" + tableDO.getTableName() + "ServiceImpl.java 生成成功"
            );
        }
    }

    public static void genModelAndMapper(String platformUrl, String tableName) throws Exception {
        List<String> templates = new ArrayList<String>();
        templates.add("generator/domain.java.vm");
        templates.add("generator/Dao.java.vm");
        templates.add("generator/Mapper.xml.vm");
        //配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        Map table = getTable(tableName);
        tableDO.setTableName(tableName);
        tableDO.setComments((String) table.get("tableComment"));
        //表名转换成Java类名
        String className = tableName;
        tableDO.setClassName(StringUtil.snakeToCapHump(className));
        tableDO.setClassname(StringUtils.uncapitalize(StringUtil.snakeToHump(className)));
        tableDO.setTableName(tableName);

        //列信息
        List<Map<String, String>> columns = getColumnsByTableName(tableName);
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("columnName"));
            columnDO.setDataType(column.get("dataType"));
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));
            columnDO.setTableField(column.get("columnName"));

            //列名转换成Java属性名
            String attrName = StringUtil.snakeToCapHump(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            // columnDO.setAttrname(StringUtils.uncapitalize(attrName));
            columnDO.setAttrname(StringUtil.snakeToHump(columnDO.getColumnName()));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
        }
        tableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("pathName", config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("platformUrl", platformUrl);
        map.put("datetime", DateUtils.format(new java.util.Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //渲染模板
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            // System.out.println(sw.toString());

            String fileName = getFileName(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package"), platformUrl);

            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Files.write(Paths.get(fileName), sw.toString().getBytes());

            System.out.println(
                    tableDO.getTableName() + ".java生成成功\r\n" +
                            tableDO.getTableName() + "Mapper.java生成成功\r\n" +
                            tableDO.getTableName() + "Mapper.xml生成成功\r\n"
            );
        }

    }


    /**
     * 获取单个表
     */
    public static Map<String, Object> getTable(String tableName) throws SQLException, ClassNotFoundException {
        Map<String, Object> item = new HashMap<String, Object>();

        //调用Class.forName()方法加载驱动程序
        Class.forName(JDBC_DIVER_CLASS_NAME);
        System.out.println("成功加载MySQL驱动！");

        Connection conn;

        conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        Statement stmt = conn.createStatement(); //创建Statement对象
        System.out.println("成功连接到数据库！");

        //查询数据的代码
        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_name = \'" + tableName + "\' and table_schema = (select database())";    //要执行的SQL
        ResultSet rs = stmt.executeQuery(sql);//创建数据对象

        while (rs.next()) {
            item.put("tableName", rs.getString(1));
            item.put("engine", rs.getString(2));
            item.put("tableComment", rs.getString(3));
            item.put("createTime", rs.getString(4));
        }

        rs.close();
        stmt.close();
        conn.close();

        return item;
    }

    /**
     * 获取所有的表
     */
    public static Map getTables() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> tables = new ArrayList<>();
        List<String> tablesName = new ArrayList<>();

        //调用Class.forName()方法加载驱动程序
        Class.forName(JDBC_DIVER_CLASS_NAME);
        System.out.println("成功加载MySQL驱动！");

        Connection conn;

        conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        Statement stmt = conn.createStatement(); //创建Statement对象
        System.out.println("成功连接到数据库！");

        //查询数据的代码
        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database())";    //要执行的SQL
        ResultSet rs = stmt.executeQuery(sql);//创建数据对象

        while (rs.next()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("tableName", rs.getString(1));
            item.put("engine", rs.getString(2));
            item.put("tableComment", rs.getString(3));
            item.put("createTime", rs.getString(4));
            tables.add(item);
            tablesName.add(rs.getString(1));
        }

        rs.close();
        stmt.close();
        conn.close();

        Map resp = new HashMap();
        resp.put("tables", tables);
        resp.put("tablesName", tablesName);
        return resp;
    }


    /**
     * 获取表中所有的字段
     */
    public static List getColumnsByTableName(String tableName) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> columns = new ArrayList<>();

        //调用Class.forName()方法加载驱动程序
        Class.forName(JDBC_DIVER_CLASS_NAME);
        System.out.println("成功加载MySQL驱动！");

        Connection conn;

        conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        Statement stmt = conn.createStatement(); //创建Statement对象
        System.out.println("成功连接到数据库！");

        //查询数据的代码
        String sql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns\r\n" + " where table_name = \'" + tableName + "\' and table_schema = (select database()) order by ordinal_position";    //要执行的SQL
        ResultSet rs = stmt.executeQuery(sql);//创建数据对象

        while (rs.next()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("columnName", rs.getString(1));
            item.put("dataType", rs.getString(2));
            item.put("columnComment", rs.getString(3));
            item.put("columnKey", rs.getString(4));
            item.put("extra", rs.getString(5));
            columns.add(item);
        }

        rs.close();
        stmt.close();
        conn.close();

        return columns;
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator/generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("读取配置错误");
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        // return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
        return columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length());
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if (AUTO_REMOVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className, String packageName, String platformUrl) {

        String packagePath = PROJECT_PATH + File.separator;

        if (template.contains("domain.java.vm")) {
            packagePath += "src/main/java" + File.separator + packageName.replace(".", File.separator) + File.separator;
            return packagePath + "model" + File.separator + className + ".java";
        }

        if (template.contains("Dao.java.vm")) {
            packagePath = "src/main/java" + File.separator + packageName.replace(".", File.separator) + File.separator;
            return packagePath + "dao" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            packagePath += "src/main/java" + File.separator + packageName.replace(".", File.separator) + File.separator;
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            packagePath += "src/main/java" + File.separator + packageName.replace(".", File.separator) + File.separator;
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            packagePath +=  "src/main/java" + File.separator + packageName.replace(".", File.separator) + File.separator;
            return packagePath + File.separator + "controller" + File.separator + columnToJava(platformUrl) + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            packagePath += "src/main/";
            return packagePath + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }


        return null;
    }

}
