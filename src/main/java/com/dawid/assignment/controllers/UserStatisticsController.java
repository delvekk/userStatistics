package com.dawid.assignment.controllers;

import com.dawid.assignment.domain.User;
import com.dawid.assignment.services.UserStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/statistics")
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    public UserStatisticsController(UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    @GetMapping("/{username}")
    public User getUserStatistics(@PathVariable String username) {
        try{
            return userStatisticsService.getUserStatistics(username);
        } catch (HttpClientErrorException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }
}
