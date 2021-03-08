package com.chanbook.dark.launch;

import com.chanbook.dark.launch.feature.DarkFeature;
import com.chanbook.dark.launch.rule.DarkRule;
import com.chanbook.dark.launch.rule.DarkRuleConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
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
        }, ruleUpdateTimeInterval, ruleUpdateTimeInterval, TimeUnit.SECONDS);
    }

    public DarkLaunch() {
        this(DEFAULT_RULE_UPDATE_TIME_INTERVAL);
    }

    private void loadRule() {
        InputStream in = null;
        DarkRuleConfig ruleConfig;
        try {
            in = this.getClass().getResourceAsStream("/dark-rule.yaml");

            Yaml yaml = new Yaml();
            ruleConfig = yaml.loadAs(in, DarkRuleConfig.class);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ruleConfig == null) {
            throw new RuntimeException("Can not load dark rule.");
        }
        DarkRule darkRule = new DarkRule(ruleConfig);
        this.darkRule = darkRule;
    }

    public DarkFeature getDarkFeature(String featureKey) {
        DarkFeature darkFeature = this.darkRule.getDarkFeature(featureKey);
        return darkFeature;
    }
}
