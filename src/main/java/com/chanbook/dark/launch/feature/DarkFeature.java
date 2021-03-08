package com.chanbook.dark.launch.feature;

import com.chanbook.dark.launch.rule.DarkRuleConfig;
import lombok.Data;
import org.springframework.util.StringUtils;

public class DarkFeature {
    private final static String START_WITH = "{";
    private final static String END_WITH = "}";
    private final static String SPLIT = ",";
    private final static String PERCENT = "%";
    private final static String RANGE = "-";
    private String key;
    private Boolean enabled;
    private Long percentage;
    private RangeSet<Long> rangeSet = TreeRangeSet.create();

    public DarkFeature(DarkRuleConfig.DarkFeatureConfig darkFeatureConfig) {
        this.key = darkFeatureConfig.getKey();
        this.enabled = darkFeatureConfig.getEnabled();
        String darkRule = darkFeatureConfig.getRule().trim();
        parseDarkRule(darkRule);
    }

    private void parseDarkRule(String darkRule) {
        if (!darkRule.startsWith(START_WITH) || !darkRule.endsWith(END_WITH)) {
            throw new IllegalArgumentException("规则格式错误");
        }

        String[] split = darkRule.split(SPLIT);
        for (String s : split) {
            if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s.trim())) {
                continue;
            }

            if (s.startsWith(PERCENT)) {
                percentage = Long.parseLong(s.replace("%", ""));
            } else if (s.contains(RANGE)) {
                int index = s.indexOf("-");
                long start = Long.parseLong(s.substring(0, index));
                long end = Long.parseLong(s.substring(index + 1));
                rangeSet.closed(start, end);
            } else {
                long val = Long.parseLong(s);
                rangeSet.closed(val, val);
            }
        }
    }

    private static class TreeRangeSet {

        public static RangeSet<Long> create() {
            return new RangeSet();
        }
    }


    private static class RangeSet<T> {
        private T start;
        private T end;

        public void closed(T start, T end) {
            this.start = start;
            this.end = end;
        }
    }

    @Data
    private static class Range<T> {
        private T start;
        private T end;

        public void closed(T start, T end) {
            this.start = start;
            this.end = end;
        }
    }
}
