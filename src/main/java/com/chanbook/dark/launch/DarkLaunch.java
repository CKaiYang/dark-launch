package com.chanbook.dark.launch;

import com.chanbook.dark.launch.rule.DarkRule;

import java.io.InputStream;
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
        },ruleUpdateTimeInterval,ruleUpdateTimeInterval, TimeUnit.SECONDS);
    }

    public DarkLaunch(){
        this(DEFAULT_RULE_UPDATE_TIME_INTERVAL);
    }

    private void loadRule() {
        InputStream in = null;
    }
}
