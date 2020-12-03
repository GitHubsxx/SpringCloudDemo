package com.scc.solr;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

import java.util.List;
import java.util.Vector;

/**
 * solr连接池
 */
public class SolrConnectionPool {
    // 空闲连接集合
    private static List<HttpSolrServer> freeConnection = new Vector<HttpSolrServer>();
    // 活动连接集合
    private static List<HttpSolrServer> activeConnection = new Vector<HttpSolrServer>();
    // 记录连接总数
    private static int connCount;
    // solr连接地址
    private static final String url = "http://11.63.178.1:8085/solr/core0";
    // 初始化连接数
    private static final int initialSize = 5;
    // 最大空闲连接数
    private static final int maxIdleSize = 5;
    // 最大活动连接数
    private static final int maxActiveSize = 10;
    // 等待时间
    private static final int connTimeOut = 60;

    // 初始化
    public void init() {
        try {
            for (int i = 0; i < initialSize; i++) {
                HttpSolrServer newConnection = newConnection();
                if (newConnection != null) {
                    // 添加到空闲连接中...
                    freeConnection.add(newConnection);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            throw new RuntimeException("初始化Solr失败,请检查配置参数!");
        }
    }

    // 创建新的Connection
    private static HttpSolrServer newConnection() {
        HttpSolrServer client = null;
        try {
            client = new HttpSolrServer(url);
        } catch (Exception e) {
            e.getStackTrace();
            throw new RuntimeException("创建Solr客户端失败!");
        }
        connCount++;
        return client;
    }

    public static HttpSolrServer getConnection() {
        HttpSolrServer connection = null;
        try {
            if (connCount < maxActiveSize) {
                // 还有活动连接可以使用
                if (freeConnection.size() > 0) {
                    connection = freeConnection.remove(0);
                } else {
                    // 创建新的连接
                    connection = newConnection();
                }
                if (isAvailable(connection)) {
                    activeConnection.add(connection);
                } else {
                    connCount--;
                    connection = getConnection();
                }
            } else {
                synchronized (SolrConnectionPool.class) {
                    // 大于最大活动连接,进行等待,重新获取连接
                    SolrConnectionPool.class.wait(connTimeOut);
                }
                connection = getConnection();// 递归调用getConnection方法
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 判断连接是否可用
    public static boolean isAvailable(HttpSolrServer connection) {
        // 此处没想好怎么判断连接是否可用
        if (connection == null) {
            return false;
        }
        return true;
    }

    // 关闭连接
    public static void close(HttpSolrServer connection) {
        try {
            if (isAvailable(connection)) {
                // 判断空闲连接集合是否大于最大空闲连接数
                if (freeConnection.size() < maxIdleSize) {
                    freeConnection.add(connection);
                } else {
                    // 空闲连接数已经满了
                    connection.shutdown();
                    connCount--;
                }
                activeConnection.remove(connection);
                synchronized (SolrConnectionPool.class) {
                    SolrConnectionPool.class.notifyAll();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除连接出错!");
        }

    }
}
