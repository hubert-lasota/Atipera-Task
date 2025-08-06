package com.example.atiperatask.dto;

public record GitHubRepoResponseDto(
    String name,
    GitHubOwnerResponseDto owner,
    boolean fork
) {
}
