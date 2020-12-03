package com.scc.log.annotation;

import lombok.Data;

@Data
public class OperaterLogVo {
    private String remarks;
    private String logContent;
    private String systemName;
    private String moduleName;
    private String menuLv1;
    private String menuLv2;
    private String url;
    private String userCode;
    private String userName;
    private String ip;
}
