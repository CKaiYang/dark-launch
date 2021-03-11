package com.chanbook.dark.launch.dataSource;

import com.chanbook.dark.launch.parser.DarkRuleConfigParserJson;
import com.chanbook.dark.launch.parser.DarkRuleConfigParserYaml;
import com.chanbook.dark.launch.parser.IDarkRuleConfigParser;
import com.chanbook.dark.launch.rule.DarkRuleConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FileDarkRuleConfigSource implements IDarkRuleConfigSource {
    private final static String FILE_NAME = "dark-rule";
    public static final String YAML_EXTENSION = "yaml";
    public static final String YML_EXTENSION = "yml";
    public static final String JSON_EXTENSION = "json";

    private static final String[] SUPPORT_EXTENSIONS = new String[]{YAML_EXTENSION, YML_EXTENSION, JSON_EXTENSION};

    private static final Map<String, IDarkRuleConfigParser> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put(YAML_EXTENSION, new DarkRuleConfigParserYaml());
        PARSER_MAP.put(YML_EXTENSION, new DarkRuleConfigParserYaml());
        PARSER_MAP.put(JSON_EXTENSION, new DarkRuleConfigParserJson());
    }

    @Override
    public DarkRuleConfig loadDarkRule() {
        for (String extension : SUPPORT_EXTENSIONS) {
            InputStream in = null;
            try {
                in = this.getClass().getResourceAsStream("/" + getFileNameByExt(FILE_NAME, extension));
                if (in != null) {
                    IDarkRuleConfigParser parser = PARSER_MAP.get(extension);
                    return parser.parse(in);
                }
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private String getFileNameByExt(String configName, String extension) {
        return String.format("%s.%s", configName, extension);
    }
}
