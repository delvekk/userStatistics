package com.dawid.assignment.controllers;

import com.dawid.assignment.domain.User;
import com.dawid.assignment.services.UserStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserStatisticsService userStatisticsService;

    @Test
    public void getUserStatistics() throws Exception{
        Map<String, Integer> languageMap = new HashMap<>();
        languageMap.put("Java", 12345);
        User user = new User();
        user.setLanguages(languageMap);
        user.setRepositoriesCount(1);
        Mockito.when(userStatisticsService.getUserStatistics(anyString())).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/statistics/delvekk").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.repositoriesCount", is(1))).andReturn();
    }
}