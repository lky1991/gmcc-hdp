package com.yeezhao.commons.entity;

import java.util.ArrayList;

/**
 * Created by makun on 16/9/9.
 */
public class HBaseEntity {
    private String db, table, rowKey;
    private ArrayList<String> columnList, dataList;
    private String type;

    public void setColumnList(ArrayList<String> columnList) {
        this.columnList = columnList;
    }

    public void setDataList(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDb() {
        return db;
    }

    public String getTable() {
        return table;
    }

    public String getType() {
        return type;
    }

    public String getRowKey() {
        return rowKey;
    }

    public ArrayList<String> getColumnList() {
        return columnList;
    }

    public ArrayList<String> getDataList() {
        return dataList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HBaseEntity{").append("\n");
        if (type != null) sb.append("type:").append(type).append("\n");
        if (db != null) sb.append("db:").append(db).append("\n");
        if (table != null) sb.append("table:").append(table).append("\n");
        if (rowKey != null) sb.append("rowkey:").append(rowKey).append("\n");
        if (columnList != null) sb.append("columnList:").append(arrayToString(columnList.toArray())).append("\n");
        if (columnList != null) sb.append("dataList:").append(arrayToString(dataList.toArray())).append("\n");
        sb.append("}");

        return sb.toString();
    }

    public String arrayToString(Object[] objects) {
        String s = "";
        for (Object object : objects) {
            String tmp = (String) object;
            s += tmp + ",";
        }
        return s;
    }
}
