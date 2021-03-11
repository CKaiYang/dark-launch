package com.chanbook.dark.launch.parser;

import com.chanbook.dark.launch.rule.DarkRuleConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class DarkRuleConfigParserYaml implements IDarkRuleConfigParser{
    @Override
    public DarkRuleConfig parse(InputStream input) {
        Yaml yaml = new Yaml();
        DarkRuleConfig darkRuleConfig = yaml.loadAs(input, DarkRuleConfig.class);
        return darkRuleConfig;
    }

    @Override
    public DarkRuleConfig parse(String filePath) {
        return null;
    }
}
