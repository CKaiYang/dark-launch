package com.chanbook.dark.launch;

import com.chanbook.dark.launch.dataSource.FileDarkRuleConfigSource;
import com.chanbook.dark.launch.dataSource.IDarkRuleConfigSource;
import com.chanbook.dark.launch.feature.IDarkFeature;
import com.chanbook.dark.launch.rule.DarkRule;
import com.chanbook.dark.launch.rule.DarkRuleConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DarkLaunch {
    private final static int DEFAULT_RULE_UPDATE_TIME_INTERVAL = 60;
    private DarkRule darkRule;
    private ScheduledExecutorService executor;

    public DarkLaunch(int ruleUpdateTimeInterval) {
        loadRule();
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleAtFixedRate(() -> {
            loadRule();
        }, ruleUpdateTimeInterval, ruleUpdateTimeInterval, TimeUnit.SECONDS);
    }

    public DarkLaunch() {
        this(DEFAULT_RULE_UPDATE_TIME_INTERVAL);
    }

    private void loadRule() {
        IDarkRuleConfigSource darkRuleConfigSource = new FileDarkRuleConfigSource();
        DarkRuleConfig ruleConfig = darkRuleConfigSource.loadDarkRule();
        if (ruleConfig == null) {
            throw new RuntimeException("Can not load dark rule.");
        }
        DarkRule darkRule = new DarkRule(ruleConfig);
        this.darkRule = darkRule;
    }

    public IDarkFeature getDarkFeature(String featureKey) {
        IDarkFeature darkFeature = this.darkRule.getDarkFeature(featureKey);
        return darkFeature;
    }
}
