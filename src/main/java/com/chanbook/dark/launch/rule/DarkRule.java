package com.chanbook.dark.launch.rule;

import com.chanbook.dark.launch.feature.DarkFeatureDefault;
import com.chanbook.dark.launch.feature.IDarkFeature;

import java.util.HashMap;
import java.util.List;

public class DarkRule {
    private HashMap<String, IDarkFeature> darkRuleMap = new HashMap<>();

    public DarkRule(DarkRuleConfig ruleConfig) {
        List<DarkRuleConfig.DarkFeatureConfig> darkFeatureConfigs = ruleConfig.getFeatures();
        for (DarkRuleConfig.DarkFeatureConfig darkFeatureConfig : darkFeatureConfigs) {
            darkRuleMap.put(darkFeatureConfig.getKey(), new DarkFeatureDefault(darkFeatureConfig));
        }
    }

    public IDarkFeature getDarkFeature(String featureKey) {
        return darkRuleMap.get(featureKey);
    }
}
