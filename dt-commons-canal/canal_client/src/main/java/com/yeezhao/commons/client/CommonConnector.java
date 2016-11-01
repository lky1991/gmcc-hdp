package com.yeezhao.commons.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

import java.net.InetSocketAddress;

/**
 * Created by makun on 16/9/9.
 */
public class CommonConnector {
    public CanalConnector connector = null;

    public CanalConnector getSingleConnector(String ipList, String destination) {
        connector = CanalConnectors.newSingleConnector(new InetSocketAddress(ipList,
                11111), destination, "", "");
        return connector;
    }

    public CanalConnector getClusterConnector(String zkList, String destination) {
        connector = CanalConnectors.newClusterConnector(zkList, destination, "", "");
        return connector;
    }
}
