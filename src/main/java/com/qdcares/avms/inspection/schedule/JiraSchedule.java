package com.qdcares.avms.inspection.schedule;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.google.common.base.Throwables;
import com.qdcares.avms.inspection.service.JiraService;
import io.atlassian.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JiraSchedule {
    private final JiraService jiraService;

    @Autowired
    public JiraSchedule(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    @Scheduled(cron = "1 * *  * * ?")
    public void fetchReopenIssues(){
        jiraService.fetchReopenIssues(null, null).then(new Promise.TryConsumer<SearchResult>() {
            @Override
            public void fail(Throwable throwable) {
                log.error(Throwables.getStackTraceAsString(throwable));
            }

            @Override
            public void accept(SearchResult searchResult) {
                for(Issue issue: searchResult.getIssues()){
                    log.info(issue.getSummary());
                }
            }
        });
    }

}
