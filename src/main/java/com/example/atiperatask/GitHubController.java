package com.example.atiperatask;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubClient gitHubClient;

    public GitHubController(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @RequestMapping("/users/{username}/repos")
    public ResponseEntity<?> findUserRepositories(@PathVariable String username) {
        return ResponseEntity.ok(gitHubClient.findUserRepositories(username));
    }

}
