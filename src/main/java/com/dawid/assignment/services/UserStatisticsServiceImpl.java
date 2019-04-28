package com.dawid.assignment.services;

import com.dawid.assignment.domain.RepoDetails;
import com.dawid.assignment.domain.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private static final String USERS_URL = "https://api.github.com/users/";
    private static final String REPOS_URL = "https://api.github.com/repos/";
    private final RestTemplate restTemplate;

    public UserStatisticsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User getUserStatistics(String username) {
        List<RepoDetails> userRepoDetails = listUserRepositories(username);
        Map<String, Integer> languageMap = getBytesWrittenInEachLanguage(userRepoDetails, username);
        return new User(userRepoDetails.size(), languageMap);
    }

    private List<RepoDetails> listUserRepositories(String username) {
        String url = USERS_URL + username + "/repos";
        return restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<RepoDetails>>() {}).getBody();
    }

    private Map<String, Integer> getBytesWrittenInEachLanguage(List<RepoDetails> repoDetails, String username) {
        Map<String, Integer> languageMap = new HashMap<>();
        for (RepoDetails repository : repoDetails) {
            String url = REPOS_URL + username + "/" + repository.getName() + "/languages";
            Map<String, Integer> languageBytesDetails = restTemplate.getForObject(url, Map.class);
            for (Map.Entry<String, Integer> entry : languageBytesDetails.entrySet()) {
                if (languageMap.containsKey(entry.getKey())) {
                    languageMap.put(entry.getKey(), languageMap.get(entry.getKey()) + entry.getValue());
                } else {
                    languageMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return languageMap;
    }
}
