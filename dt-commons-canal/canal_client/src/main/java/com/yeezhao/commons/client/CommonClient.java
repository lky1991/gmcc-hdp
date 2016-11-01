package com.yeezhao.commons.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.yeezhao.commons.entity.HBaseEntity;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by makun on 16/9/9.
 */
public class CommonClient {
    private String destination = null;
    private CanalConnector connector = null;
    private ArrayList<String> hBaseEntities = new ArrayList<String>();
    protected final static Logger logger = LoggerFactory.getLogger(CommonClient.class);
    protected static final String SEP = SystemUtils.LINE_SEPARATOR;
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected static String context_format = null;


    public CommonClient(String destination, String ipList, int canalType, String filter) {
        CommonConnector commonConnector = new CommonConnector();
        this.destination = destination;
        if (canalType == 1) {
            this.connector = commonConnector.getSingleConnector(ipList, destination);
        } else if (canalType == 2) {
            this.connector = commonConnector.getClusterConnector(ipList, destination);
        }

        this.connector.connect();
        this.connector.subscribe(filter);
    }

    public void process(int batchSize) {
        this.hBaseEntities.clear();
        Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
        long batchId = message.getId();
        int size = message.getEntries().size();
        if (batchId == -1 || size == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        } else {
            printSummary(message, batchId, size);
            printEntry(message.getEntries());
        }

        connector.ack(batchId); // 提交确认
//        connector.rollback(batchId); // 处理失败, 回滚数据
    }

    public ArrayList<String> gethBaseEntities() {
        return hBaseEntities;
    }

    public void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry,
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            logger.info(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            String dbName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();
            if (eventType == CanalEntry.EventType.QUERY) {
                HBaseEntity hBaseEntity = new HBaseEntity();
                hBaseEntity.setType(eventType.name());
                hBaseEntity.setTable(dbName);

                this.hBaseEntities.add(JSON.toJSONString(hBaseEntity));
                continue;
            }

            if (eventType == CanalEntry.EventType.ERASE || eventType == CanalEntry.EventType.CREATE) {
                HBaseEntity hBaseEntity = new HBaseEntity();
                hBaseEntity.setType(eventType.name());
                hBaseEntity.setDb(dbName);
                hBaseEntity.setTable(tableName);

                this.hBaseEntities.add(JSON.toJSONString(hBaseEntity));
                continue;
            }

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                HBaseEntity hBaseEntity = new HBaseEntity();

                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                    getColumnMsg(rowData.getBeforeColumnsList(), hBaseEntity);
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                    getColumnMsg(rowData.getAfterColumnsList(), hBaseEntity);
                } else {
                    printColumn(rowData.getAfterColumnsList());
                    getColumnMsg(rowData.getAfterColumnsList(), hBaseEntity);
                }

                hBaseEntity.setType(eventType.name());
                hBaseEntity.setDb(dbName);
                hBaseEntity.setTable(tableName);

                this.hBaseEntities.add(JSON.toJSONString(hBaseEntity));
            }
        }
    }

    protected void getColumnMsg(List<CanalEntry.Column> columns, HBaseEntity hBaseEntity) {
        String tmpKey = "";
        ArrayList<String> columnList = new ArrayList<String>();
        ArrayList<String> dataList = new ArrayList<String>();
        String rowKey = null;

        for (CanalEntry.Column column : columns) {
            if (column.getIsKey()) {
                rowKey = new StringBuilder().append(column.getName()).append("_").append(column.getValue()).toString();
            }
            if (column.getIndex() == 0) {
                tmpKey = new StringBuilder().append(column.getName()).append("_").append(column.getValue()).append("_").append(String.valueOf(System.currentTimeMillis())).toString();
            }

            columnList.add(column.getName());
            dataList.add(column.getValue());
        }
        if (rowKey == null) {
            rowKey = tmpKey;
        }

        hBaseEntity.setColumnList(columnList);
        hBaseEntity.setDataList(dataList);
        hBaseEntity.setRowKey(rowKey);
    }

    private void printSummary(Message message, long batchId, int size) {
        long memsize = 0;
        for (CanalEntry.Entry entry : message.getEntries()) {
            memsize += entry.getHeader().getEventLength();
        }

        String startPosition = null;
        String endPosition = null;
        if (!CollectionUtils.isEmpty(message.getEntries())) {
            startPosition = buildPositionForDump(message.getEntries().get(0));
            endPosition = buildPositionForDump(message.getEntries().get(message.getEntries().size() - 1));
        }

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        logger.info(context_format, new Object[]{batchId, size, memsize, format.format(new Date()), startPosition,
                endPosition});
    }

    protected String buildPositionForDump(CanalEntry.Entry entry) {
        long time = entry.getHeader().getExecuteTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
                + entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
    }

    protected void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            StringBuilder builder = new StringBuilder();
            builder.append(column.getName() + " : " + column.getValue() + " : " + column.getIsKey() + " : " + column.getIndex());
            builder.append("    type=" + column.getMysqlType());
            if (column.getUpdated()) {
                builder.append("    update=" + column.getUpdated());
            }
            builder.append(SEP);
            logger.info(builder.toString());
//            System.out.println("print:"+builder.toString());
        }
    }
}
