package com.qdcares.avms.inspection.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "git")
@Component
@Data
public class GitProperties {
    private List<String> gitRepos =
            Lists.newArrayList("git@git.tao.qdcares:H/HXAVM/hxavm.git",
                    "git@git.tao.qdcares:apron-vis/apron.git");
    private String username = "xuguosheng";
    private String password = "XXgswindy019891";
}
