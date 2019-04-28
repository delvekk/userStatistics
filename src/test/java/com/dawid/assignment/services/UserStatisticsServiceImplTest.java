package com.dawid.assignment.services;

import com.dawid.assignment.domain.RepoDetails;
import com.dawid.assignment.domain.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class UserStatisticsServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private UserStatisticsServiceImpl userStatisticsService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserStatisticsTest() {
        //given
        ArrayList<RepoDetails> repoDetailsList = new ArrayList<>();
        RepoDetails repoDetails = new RepoDetails();
        repoDetails.setId(1L);
        repoDetails.setName("TestRepo");
        repoDetailsList.add(repoDetails);

        Map<String, Integer> languageMap = new HashMap<>();
        languageMap.put("Java", 12345);

        ResponseEntity<List<RepoDetails>> testEntity = new ResponseEntity<>(repoDetailsList,HttpStatus.ACCEPTED);
        Mockito.when(restTemplate.exchange(Mockito.anyString(),
                Mockito.<HttpMethod> eq(HttpMethod.GET),
                ArgumentMatchers.<HttpEntity<?>> any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepoDetails>>>any())).thenReturn(testEntity);
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), ArgumentMatchers.any(Class.class))).thenReturn(languageMap);

        //when
        User testUser = userStatisticsService.getUserStatistics("testUsername");

        //then
        assertNotNull(testUser);
        assertEquals(testUser.getRepositoriesCount(), 1);
        assertEquals(testUser.getLanguages().size(), 1);
    }
}