package com.scc.solr;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SolrApi {
    public static HttpSolrServer solr = null;

   /* static{
        solr = new HttpSolrServer(urlString);
    }*/

    public static void main(String[] args) throws IOException, SolrServerException {
/*        File parentFile = new File("E:/document/");
        if (parentFile.exists()) {
            File[] files = parentFile.listFiles();
//            int i = 0;
            for (File file : files) {
                try {
                    indexFilesSolrCell(file.getName(), file.getPath(), UUID.randomUUID().toString(),"","");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("该文件无法处理，请检查文本内容或格式，filename==="+file.getName());
                }
            }
        }*/
//        atomUpdate("0","tag","测试tag");
//        atomUpdate("23423f90-69f5-45de-b002-751ea6c45801","leader","高大尚");
        search("京东",1,2);

    }

    public static List<HashMap<String,Object>> search(String content, int pageNum, int pageSize)throws IOException, SolrServerException {
        solr = SolrConnectionPool.getConnection();
        //连接solr服务
        SolrQuery query = new SolrQuery();
        query.setFields("path","id","attr_filename","attr_content","leader","tag","date");
        List<HashMap<String,Object>> list = new ArrayList<>();

//        query.addFilterQuery("attr_content:"+content+" OR leader:刘桂平/纪志宏");
//        query.addFilterQuery("attr_content:"+content);

//        QueryResponse rsp = solr.query(new SolrQuery("attr_content:"+content));
//        QueryResponse rsp = solr.query(new SolrQuery("attr_content:"+content+" OR leader:"+content+" OR tag:"+content+" OR attr_filename:"+content));

//        query.set("q", "attr_content:"+content);
        query.set("q", "attr_content:"+content+" OR leader:"+content+" OR tag:"+content+" OR attr_filename:"+content);//拼接要查询的范围
        query.set("hl.fl", "attr_content");
        query.setSort("date", SolrQuery.ORDER.asc);//排序
        //参数hl,设置高亮
        query.setHighlight(true);
//        query.set("hl.highlightMultiTerm","true");//启用多字段高亮
        //设置高亮的字段
//        query.addHighlightField("attr_content");
        //设置每个分片的最大长度，默认为100
        query.setHighlightFragsize(100);
        //设置高亮的样式
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //设置分页参数
        query.setStart((pageNum-1)*pageSize);
        query.setRows(pageSize);//每一页多少值

        QueryResponse rsp = solr.query(query);

        SolrDocumentList solrDocumentList = rsp.getResults();
        long numFound = solrDocumentList.getNumFound();//查询的总条数

        Map<String, Map<String, List<String>>> highlighting = rsp.getHighlighting();

        HashMap<String,Object> result = null;
        ListIterator<SolrDocument> listIterator = solrDocumentList.listIterator();
        while (listIterator.hasNext()) {
            SolrDocument solrDocument = listIterator.next();
            result = new HashMap<String, Object>();
            //高亮
            List<String> contentList = highlighting.get(solrDocument.get("id")).get("attr_content");
            if( contentList != null && contentList.size() > 0 ){
                //如果有高亮显示  显示名称
                result.put("content",contentList.get(0));
            }else{
                //如果没有正常显示
                result.put("content",solrDocument.getFieldValue("attr_content").toString());
            }
            result.put("tag",StringUtils.strip(solrDocument.getFieldValue("tag").toString(),"[]"));//去除所有“[]”字符
            result.put("leader",StringUtils.strip(String.valueOf(solrDocument.getFieldValue("leader")),"[]"));
            list.add(result);//代表每一行的列的值
        }
        SolrConnectionPool.close(solr);
        return list;
    }

    /**
     * 根据id和key原子更新索引内容
     */
    public static void atomUpdate(String id, String key, String value) throws IOException, SolrServerException {
        solr = SolrConnectionPool.getConnection();
        //连接solr服务
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", id);//根据id唯一标识
        HashMap<String,String> map = new HashMap<String, String>();
//        map.put("add", value);//放入支持的操作,对多值域管用
        map.put("set", value);//放入支持的操作，对单值和多值域管用
//        map.put("inc", value);//放入支持的操作，对数值域管用
        solrInputDocument.addField(key, map);
        solr.add(solrInputDocument);
        solr.commit();
        SolrConnectionPool.close(solr);
        System.out.println("原子更新成功！");
    }


    /**删除：通过Id删除,我的Id是zzbm**/
    public void deleteDocById(String id){
        solr = SolrConnectionPool.getConnection();
        try {
            UpdateResponse response = solr.deleteById(id);
            solr.commit();
            SolrConnectionPool.close(solr);
            System.out.println("删除的结果："+response.getStatus());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件创建索引 <功能详细描述>
     *
     * @param fileName
     * @param parentPath
     * @see [类、类#方法、类#成员]
     */
    public static void createIndex(String fileName, String parentPath, String id, String tag, String leader) throws Exception {
        solr = SolrConnectionPool.getConnection();
        //连接solr服务
        File pfile = new File(parentPath);
        if (pfile.exists()) {
            File[] files = pfile.listFiles();
            for (File file : files) {
                ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
                String contentType = getFileContentType(fileName);
                up.addFile(new File(file.getPath()), contentType);
                up.setParam("literal.id", id);
                up.setParam("literal.path", file.getPath());
                up.setParam("literal.fileName", fileName);
                up.setParam("literal.tag", tag);
                up.setParam("literal.leader", leader);
                up.setParam("literal.date", getCurrentDate());
                up.setParam("fmap.content", "attr_content");
                up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
                solr.request(up);
                SolrConnectionPool.close(solr);
            }
        }
    }

    /**
     * 根据文件名获取文件的ContentType类型
     *
     * @param filename
     * @return
     */
    public static String getFileContentType(String filename) {
        String contentType = "";
        String prefix = filename.substring(filename.lastIndexOf(".") + 1);
        if (prefix.equals("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (prefix.equals("pdf")) {
            contentType = "application/pdf";
        } else if (prefix.equals("doc")) {
            contentType = "application/msword";
        } else if (prefix.equals("txt")) {
            contentType = "text/plain";
        } else if (prefix.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (prefix.equals("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (prefix.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (prefix.equals("pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }

        else {
            contentType = "othertype";
        }

        return contentType;
    }

    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
