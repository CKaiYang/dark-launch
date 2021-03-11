package com.chanbook.dark.launch.feature;

import com.chanbook.dark.launch.rule.DarkRuleConfig;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class DarkFeature {
    private final static String START_WITH = "{";
    private final static String END_WITH = "}";
    private final static String SPLIT = ",";
    private final static String PERCENT = "%";
    private final static String RANGE = "-";
    @Getter
    private String key;
    private Boolean enabled;
    private Long percentage;
    private RangeSet rangeSet = TreeRangeSet.create();

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

        darkRule = darkRule.replace(START_WITH, "").replace(END_WITH, "");

        String[] split = darkRule.split(SPLIT);
        for (String s : split) {
            s = s.trim();
            if (StringUtils.isEmpty(s)) {
                continue;
            }

            if (s.contains(PERCENT)) {
                String s1 = s.replace(PERCENT, "").trim();
                percentage = parseLong(s1);
            } else if (s.contains(RANGE)) {
                String[] split1 = s.split(RANGE);
                if (split1.length != 2) {
                    throw new IllegalArgumentException("范围参数错误");
                }
                long start = parseLong(split1[0]);
                long end = parseLong(split1[1]);
                if (start > end) {
                    throw new IllegalArgumentException("范围参数错误");
                }
                rangeSet.add(start, end);
            } else {
                long val = parseLong(s);
                rangeSet.add(val, val);
            }
        }
    }

    private long parseLong(String s) {
        return Long.parseLong(s.trim());
    }

    public boolean enabled() {
        return this.enabled;
    }

    public boolean dark(long darkTarget) {
        boolean contains = this.rangeSet.contains(darkTarget);
        if (contains) {
            return true;
        }

        long reminder = darkTarget % 100;
        if (reminder >= 0 && reminder < this.percentage) {
            return true;
        }

        return false;
    }

    private static class TreeRangeSet {

        public static RangeSet create() {
            return new RangeSet();
        }
    }


    private static class RangeSet {
        Set<Range> ranges = new HashSet<>();

        public void add(long start, long end) {
            ranges.add(Range.closed(start, end));
        }

        public boolean contains(long darkTarget) {
            return this.ranges.stream().anyMatch(v -> v.getStart() <= darkTarget && v.getEnd() >= darkTarget);
        }
    }

    @Data
    private static class Range {
        private long start;
        private long end;

        public static Range closed(long start, long end) {
            Range range = new Range();
            range.start = start;
            range.end = end;
            return range;
        }
    }
}
