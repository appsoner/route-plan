package com.qdcares.avms.inspection.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.google.common.base.Throwables;
import com.qdcares.avms.inspection.jql.Jqls;
import io.atlassian.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JiraService {
    private final JiraRestClient jiraRestClient;

    private final ServerProperties serverProperties;

    @Autowired
    public JiraService(JiraRestClient jiraRestClient, ServerProperties serverProperties) {
        this.jiraRestClient = jiraRestClient;
        this.serverProperties = serverProperties;
    }

    public Promise<SearchResult> fetchReopenIssues(Date begin, Date end){
        return this.jiraRestClient.getSearchClient().searchJql(Jqls.fetchReopenIssues);
    }

}


