package com.bee.beedoc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.Objects;

/**
 * @author weixin
 */
public class JacksonUtil {
    private final static JsonMapper JSON_MAPPER;

    static {
        JSON_MAPPER = JsonMapper.builder()
                .build();
    }

    private JacksonUtil() {
    }

    public static String serialize(final Object value) {
        if (Objects.nonNull(value)) {
            try {
                if (value instanceof CharSequence) {
                    return value.toString();
                } else {
                    return JSON_MAPPER.writeValueAsString(value);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
