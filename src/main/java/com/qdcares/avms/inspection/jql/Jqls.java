package com.qdcares.avms.inspection.jql;

public class Jqls {
    public static String fetchReopenIssues =
            "project = HXKSH AND issuetype = Bug AND cf[10700] > 0 ORDER BY cf[10700] ASC, priority DESC, updated DESC";
}
