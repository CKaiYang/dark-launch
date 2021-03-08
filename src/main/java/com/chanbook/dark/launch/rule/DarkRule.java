package com.chanbook.dark.launch.rule;

import com.chanbook.dark.launch.feature.DarkFeature;

import java.util.HashMap;
import java.util.List;

public class DarkRule {
    private HashMap<String, DarkFeature> darkRuleMap = new HashMap<>();

    public DarkRule(DarkRuleConfig ruleConfig) {
        List<DarkRuleConfig.DarkFeatureConfig> darkFeatureConfigs = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig darkFeatureConfig : darkFeatureConfigs) {
            darkRuleMap.put(darkFeatureConfig.getKey(), new DarkFeature(darkFeatureConfig));
        }
    }

    public DarkFeature getDarkFeature(String featureKey) {
        return darkRuleMap.get(featureKey);
    }
}
