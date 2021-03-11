package com.chanbook.dark.launch.parser;

import com.chanbook.dark.launch.rule.DarkRuleConfig;

import java.io.InputStream;

public interface IDarkRuleConfigParser {
    /**
     *
     * @param input
     * @return
     */
    DarkRuleConfig parse(InputStream input);
    DarkRuleConfig parse(String filePath);
}
