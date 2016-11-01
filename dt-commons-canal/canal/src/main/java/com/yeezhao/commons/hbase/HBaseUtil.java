package com.yeezhao.commons.hbase;

import com.yeezhao.commons.util.LoadConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by makun on 16/9/9.
 */
public class HBaseUtil {
    public Configuration cfg = null;
    protected final static org.slf4j.Logger logger = LoggerFactory.getLogger(HBaseUtil.class);

    public HBaseUtil() {
        Configuration hbase_config = new Configuration();

        // 添加HBase依赖的Zookeeper的通信端口
        hbase_config.set("hbase.zookeeper.property.clientPort", LoadConfig.HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT);

        // 添加HBase依赖的Zookeeper服务器地址
        hbase_config.set("hbase.zookeeper.quorum", LoadConfig.HBASE__ZOOKEEPER_QUORUM);

        // 添加HBase在其依赖的Zookeeper上的目录
        hbase_config.set("zookeeper.znode.parent", LoadConfig.HBASE_ZOOKEEPER_ZNODE_PARENT);
        cfg = HBaseConfiguration.create(hbase_config);
    }

    public boolean namespaceExists(String namespace) {
        HBaseAdmin admin = null;
        try {
            admin = new HBaseAdmin(cfg);
            NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();

            for (int i = 0; i < namespaceDescriptors.length; ++i) {
                if (namespaceDescriptors[i].getName().equals(namespace)) {
                    return true;
                }
            }
            admin.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeHBaseAdmin(admin);
        }
    }

    public void createNameSpace(String namespace) {
        HBaseAdmin admin = null;

        try {
            admin = new HBaseAdmin(cfg);
            if (!namespaceExists(namespace)) {
                admin.createNamespace(NamespaceDescriptor.create(namespace).build());
            }
            admin.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeHBaseAdmin(admin);
        }
    }

    /**
     * 创建表
     *
     * @param tableName
     * @throws Exception
     */
    public void create(String tableName) {
        HBaseAdmin admin = null;
        try {
            admin = new HBaseAdmin(cfg);
            String namespace = tableName.split(":")[0];

            if (!namespaceExists(namespace)) {
                createNameSpace(namespace);
            }

            if (admin.tableExists(tableName)) {
                logger.info("---this table exists---");
            } else {

                HTableDescriptor tableDesc = new HTableDescriptor(tableName.valueOf(tableName));
                tableDesc.addFamily(new HColumnDescriptor("Base"));

                admin.createTable(tableDesc);
                logger.info("---create table success---");
            }
            admin.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeHBaseAdmin(admin);
        }
    }

    /**
     * 在表中插入一行数据
     *
     * @param tableName
     * @param row
     * @param column
     * @param data
     * @throws Exception
     */
    public void putRow(String tableName, String row, List<String> column, List<String> data) {
        if (!isTableExists(tableName)) {
            create(tableName);
        }

        HTable table = null;
        try {
            table = new HTable(cfg, tableName);

            Put p1 = new Put(Bytes.toBytes(row));
            for (int i = 0; i < column.size(); ++i) {
                p1.add(Bytes.toBytes("Base"), Bytes.toBytes(column.get(i)), Bytes.toBytes(data.get(i)));
            }
            table.put(p1);
            table.close();
            logger.info("---update data success---");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeHTable(table);
        }
    }

    /**
     * 删除rowkey为row的数据
     *
     * @param tableName
     * @param row
     * @throws Exception
     */
    public void delRow(String tableName, String row) {
        if (!isTableExists(tableName)) {
            return;
        }

        HTable table = null;
        try {
            table = new HTable(cfg, tableName);
            Delete del = new Delete(Bytes.toBytes(row));
            table.delete(del);
            logger.info("---delete data success---");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeHTable(table);
        }
    }


    /**
     * 删除表中数据
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public boolean deleteTable(String tableName) {
        HBaseAdmin admin = null;
        if (!isTableExists(tableName)) {
            logger.error("--table doesn't create---");
            return false;
        }

        try {
            admin = new HBaseAdmin(cfg);
            if (admin.tableExists(tableName)) {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            }
            logger.info("---delete table success---");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeHBaseAdmin(admin);
        }
    }

    /**
     * 判断该表是否已经创建
     *
     * @param tableName
     * @return
     */
    public boolean isTableExists(String tableName) {

        HBaseAdmin admin = null;
        boolean flag = false;

        try {
            admin = new HBaseAdmin(cfg);

            if (admin.tableExists(tableName)) {
                flag = true;
            }

            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return flag;
        } finally {
            closeHBaseAdmin(admin);
        }

    }

    /**
     * 关闭HTable对象
     *
     * @param table
     */
    public void closeHTable(HTable table) {
        try {
            if (table != null) {
                table.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭HBaseAdmin对象
     *
     * @param admin
     */
    public void closeHBaseAdmin(HBaseAdmin admin) {
        try {
            if (admin != null) {
                admin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
