package com.example.atiperatask;

import com.example.atiperatask.dto.GitHubBranchResponseDto;
import com.example.atiperatask.dto.GitHubRepoResponseDto;
import com.example.atiperatask.dto.RepoResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Component
public class GitHubClient {
    private final RestClient client;

    public GitHubClient(
        @Value("${github.base-url}")
        String baseUrl,
        @Value("${github.access-token}")
        String accessToken,
        @Value("${github.content-type}")
        String contentType,
        @Value("${github.api-version}")
        String apiVersion
    ) {
        this.client = RestClient
            .builder()
            .baseUrl(baseUrl)
            .defaultHeader("X-GitHub-Api-Version", apiVersion)
            .defaultHeader(HttpHeaders.AUTHORIZATION, accessToken)
            .defaultHeader(HttpHeaders.ACCEPT, contentType)
            .build();
    }


    public List<RepoResponseDto> findUserRepositories(String username) {
        List<GitHubRepoResponseDto> repos = client
            .get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .onStatus(statusCode -> statusCode.value() == 404,
                (request, response) -> {
                    throw new ResourceNotFoundException("Not found user(%s) repositories".formatted(username));
                })
            .body(new ParameterizedTypeReference<>() {
            });


        return repos == null ? List.of() : repos
            .stream()
            .filter(repo -> !repo.fork())
            .map(repo -> {
                List<GitHubBranchResponseDto> branches = findRepositoryBranches(username, repo.name());
                return GitHubMapper.toRepoResponseDto(repo, branches);
            })
            .toList();

    }


    private List<GitHubBranchResponseDto> findRepositoryBranches(String username, String repoName) {
        return client
            .get()
            .uri("/repos/{username}/{repo}/branches", username, repoName)
            .retrieve()
            .onStatus(statusCode -> statusCode.value() == 404,
                (request, response) -> {
                    throw new ResourceNotFoundException("Not found repo(%s) branches".formatted(repoName));
                })
            .body(new ParameterizedTypeReference<>() {
            });
    }

}
