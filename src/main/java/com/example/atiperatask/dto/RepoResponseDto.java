package com.example.atiperatask.dto;

import java.util.List;

public record RepoResponseDto(
    String name,
    String ownerLogin,
    List<BranchResponseDto> branches
) {
}
