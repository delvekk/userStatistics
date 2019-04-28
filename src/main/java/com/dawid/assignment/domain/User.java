package com.dawid.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    private int repositoriesCount;
    private Map<String, Integer> languages;
}
