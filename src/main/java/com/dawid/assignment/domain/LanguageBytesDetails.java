package com.dawid.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageBytesDetails {

    private Map<String, Integer> languageList;
}
