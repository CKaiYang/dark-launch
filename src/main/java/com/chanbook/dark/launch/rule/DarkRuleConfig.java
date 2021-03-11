package com.chanbook.dark.launch.rule;

import lombok.Data;

import java.util.List;

@Data
public class DarkRuleConfig {
    private List<DarkFeatureConfig> features;

    @Data
    public static class DarkFeatureConfig {
        private String key;
        private Boolean enabled;
        private String rule;
    }
}
