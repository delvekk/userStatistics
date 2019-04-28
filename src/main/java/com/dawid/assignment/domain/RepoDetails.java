package com.dawid.assignment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoDetails {

    private Long id;
    private String name;
}
