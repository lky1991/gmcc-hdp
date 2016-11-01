package com.gmcc.hdp.demo.cli;

import com.gmcc.hdp.demo.hbase.HBaseDemo;
import com.gmcc.hdp.demo.hdfs.HDFSDemo;
import com.gmcc.hdp.demo.hive.HiveDemo;
import com.gmcc.hdp.demo.kafka.KafkaDemo;
import com.gmcc.hdp.demo.kafka.KafkaKrbConsumerDemo;
import com.gmcc.hdp.demo.kafka.KafkaKrbProducerDemo;
import com.gmcc.hdp.demo.mr.MRDemo;
import com.gmcc.hdp.demo.spark.SparkDemo;
import com.gmcc.hdp.demo.spark.SparkStreamingDemo;
import com.gmcc.hdp.demo.util.FileUtil;
import com.gmcc.hdp.demo.util.HDPSampleConfiguration;
import org.apache.commons.cli.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;


/**
 * cli类,用于解析输入的参数,执行相关的功能
 * Created by makun on 16/7/8.
 */
public class RunningCli {
    private Options options = new Options();
    private CommandLine commandLine = null;
    private HDPSampleConfiguration hdpSampleConfiguration=null;

    public RunningCli(String[] args) {
        initOpts();
        try {
            // 解析参数
            CommandLineParser parser = new PosixParser();
            commandLine = parser.parse(options, args);

            //加载配置文件
            String configPath=commandLine.getOptionValue("config");
            if(configPath.isEmpty()){
                System.out.println("please input config path");
                return;
            }else{
                hdpSampleConfiguration=new HDPSampleConfiguration(configPath);
            }

            String service = commandLine.getOptionValue("service");
            if (!service.equals("TestFile")) {
                produceTestFile(62);
            }

            if (service.equals("HBase")) {
                hbaseLearnSampleCli();
            } else if (service.equals("HDFS")) {
                hdfsLearnSampleCli();
            } else if (service.equals("MR")) {
                mrLearnSampleCli();
            } else if (service.equals("HIVE")) {
                hiveLearnSampleCli();
            } else if (service.equals("Spark")) {
                sparkLearnSampleCli();
            } else if (service.equals("TestFile")) {
                String size = commandLine.getOptionValue("size");
                if (size == null || size.length() <= 0) {
                    System.out.println("请输入文件大小");
                    return;
                } else {
                    int sizeNum = Integer.parseInt(size);
                    int times = (sizeNum * 1024) / 17+2;
                    FileUtil fileUtil = new FileUtil();
                    if (fileUtil.isFileExist(HDPSampleConfiguration.LOCAL_TMP_2G_USER_FILE)) {
                        fileUtil.deleteFile(HDPSampleConfiguration.LOCAL_TMP_2G_USER_FILE);
                    }
                    produceTestFile(times);
                }
            } else if (service.equals("Kafka")) {
                kafkaLearnSampleCli();
            }

        } catch (ParseException e) {
            System.out.println(e);
        }
    }

    public void initOpts() {
        options.addOption("service", true, "执行服务");
        options.addOption("type", true, "处理类型");
        options.addOption("tbName", true, "用户HBase表的创建");
        options.addOption("key", true, "用户HBase key的搜索");
        options.addOption("filepath", true, "用户HDFS文件的读写使用");
        options.addOption("input", true, "HDFS中2G用户维表的文件路径");
        options.addOption("output", true, "存放统计结果的HDFS的文件路径");
        options.addOption("krb", true, "是否在kerberos环境下执行");
        options.addOption("dbName", true, "数据库名称");
        options.addOption("princ", true, "kerberos对应的principle完整名称");
        options.addOption("keytab", true, "kerberos对应的keytab路径");
        options.addOption("topic", true, "kafka中的topic");
        options.addOption("size", true, "文件大小");
        options.addOption("config",true,"配置文件");
    }

    // 生成测试数据
    public void produceTestFile(int size) {
        FileUtil fileUtil = new FileUtil();
        String path = HDPSampleConfiguration.LOCAL_TMP_2G_USER_FILE;
        if (fileUtil.isFileExist(path) == false) {
            String csvPath = FileUtil.class.getClassLoader().getResource(HDPSampleConfiguration.LOCAL_2G_USER_FILE).getPath();
            String content = fileUtil.readToString(csvPath);
            for (int i = 0; i < size; i++) {
                fileUtil.appendFile(path, content);
            }
            System.out.println(new StringBuilder("测试文件将被放在").append(HDPSampleConfiguration.LOCAL_TMP_2G_USER_FILE).append("下"));
        }
    }

    public void hbaseLearnSampleCli() {
        String type = commandLine.getOptionValue("type");
        HBaseDemo hBaseLearnSample = new HBaseDemo();
        if (type.equals("createTb")) {
            String table = commandLine.getOptionValue("tbName");

            if (null == table) {
                System.out.println("请输入要创建的表名");
            } else {
                String[] array = {HDPSampleConfiguration.HBASE_COLUMNFAMILY_NAME};
                hBaseLearnSample.create(table, array);
            }

        } else if (type.equals("delTb")) {
            String table = commandLine.getOptionValue("tbName");

            if (table == null) {
                System.out.println("请输入要删除的表名");
            } else {
                try {
                    hBaseLearnSample.delete(table);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("put")) {
            String table = commandLine.getOptionValue("tbName");

            if (null == table) {
                System.out.println("请输入要插入的表名");
            } else {
                hBaseLearnSample.initTable(table);
            }

        } else if (type.equals("get")) {
            String key = commandLine.getOptionValue("key");
            String table = commandLine.getOptionValue("tbName");
            if (key == null || table == null) {
                System.out.println("请输入要查询的表名和主键名");
            } else {
                hBaseLearnSample.get(table, key);
            }
        } else {
            System.out.println("请输入要执行的操作类型:createTb,delTb,put,get");
        }
    }

    public void hdfsLearnSampleCli() {
        String type = commandLine.getOptionValue("type");
        String kerberos = commandLine.getOptionValue("krb");
        HDFSDemo hdfsLearnSample = null;
        hdfsLearnSample = new HDFSDemo();

        if (type.equals("write")) {
            String path = commandLine.getOptionValue("filepath");

            if (null == path) {
                System.out.println("请输入待写入的hdfs的文件路径");
            } else {
                hdfsLearnSample.upFileToHDFSFromLocal(HDPSampleConfiguration.LOCAL_TMP_2G_USER_FILE, path);
            }
        } else if (type.equals("read")) {
            String path = commandLine.getOptionValue("filepath");

            if (null == path) {
                System.out.println("请输入待读的hdfs的文件路径");
            } else {
                hdfsLearnSample.readHDFSFile(path);
            }
        }
    }

    public void mrLearnSampleCli() {
        String inputPath = commandLine.getOptionValue("input");
        String outputPath = commandLine.getOptionValue("output");

        MRDemo mrLearnSample = new MRDemo();

        if (null == inputPath || null == outputPath) {
            System.out.println("请输入待处理数据的输入路径和结果输出路径");
        } else {
            try {
                mrLearnSample.executeMR(inputPath, outputPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hiveLearnSampleCli() {
        String type = commandLine.getOptionValue("type");
        String dbName = commandLine.getOptionValue("dbName");
        String table = commandLine.getOptionValue("tbName");
        String key = commandLine.getOptionValue("key");
        if (dbName == null) {
            dbName = "default";
        }
        if (table == null) {
            System.out.println("请输入连接数据库的表名");
            return;
        }


        HiveDemo hiveLearnExample = new HiveDemo();
        if (type.equals("create")) {
            hiveLearnExample.createHiveTable(table);
        } else if (type.equals("read")) {
            hiveLearnExample.searchHiveTable(table, key);
        } else if (type.equals("insert")) {
            hiveLearnExample.insertHiveTable(table);
        } else if (type.equals("delete")) {
            hiveLearnExample.deleteHiveTable(table);
        }
    }

    public void sparkLearnSampleCli() {
        String input = commandLine.getOptionValue("input");
        String output = commandLine.getOptionValue("output");
        SparkConf conf = new SparkConf().setAppName("SparkExample");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        SparkDemo sparkLearnSample = new SparkDemo();
        sparkLearnSample.cityCount(sparkContext, input, output);
        sparkContext.stop();
    }

    public void sparkStreamingSampleCli() {
        SparkStreamingDemo sparkStreamingDemo = new SparkStreamingDemo();
        sparkStreamingDemo.runDemo();
    }

    public void kafkaLearnSampleCli() {
        String type = commandLine.getOptionValue("type");
        String topic = commandLine.getOptionValue("topic");
        if (type.equals("producer")) {
            KafkaDemo kafkaDemo = new KafkaDemo();
            kafkaDemo.producerData(topic);
        } else if (type.equals("consumer")) {
            KafkaDemo kafkaDemo = new KafkaDemo();
            kafkaDemo.consumerData(topic);
        }
    }

    public static void main(String[] args) {
        RunningCli runningCli = new RunningCli(args);
    }
}
