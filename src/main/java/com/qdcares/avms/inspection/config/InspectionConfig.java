package com.qdcares.avms.inspection.config;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.URI;

@Configuration
@EnableScheduling
@EnableConfigurationProperties
public class InspectionConfig {

    private final JiraProperties jiraProperties;
    private final GitProperties gitProperties;

    @Autowired
    public InspectionConfig(JiraProperties jiraProperties, GitProperties gitProperties) {
        this.jiraProperties = jiraProperties;
        this.gitProperties = gitProperties;
    }

    @Bean
    public JiraRestClient jiraRestClient() {
        return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(URI.create(jiraProperties.getUrl()),
                jiraProperties.getUsername(), jiraProperties.getPassword());
    }
}
