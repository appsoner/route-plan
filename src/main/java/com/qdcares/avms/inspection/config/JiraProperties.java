package com.qdcares.avms.inspection.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "jira")
@Component
public class JiraProperties {
    private String username = "xuguosheng";
    private String password = "XXgswindy019891";
    private String url = "http://yanfa.qdkaiya.com:8085/";
}
