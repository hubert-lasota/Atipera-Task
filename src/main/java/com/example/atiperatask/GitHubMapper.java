package com.example.atiperatask;

import com.example.atiperatask.dto.BranchResponseDto;
import com.example.atiperatask.dto.GitHubBranchResponseDto;
import com.example.atiperatask.dto.GitHubRepoResponseDto;
import com.example.atiperatask.dto.RepoResponseDto;

import java.util.List;

public class GitHubMapper {

    private GitHubMapper() {
    }

    public static RepoResponseDto toRepoResponseDto(GitHubRepoResponseDto responseDto,
                                                    List<GitHubBranchResponseDto> branchDtos) {
        List<BranchResponseDto> branches = branchDtos
            .stream()
            .map(GitHubMapper::toBranchResponseDto)
            .toList();

        return new RepoResponseDto(responseDto.name(), responseDto.owner().login(), branches);
    }

    private static BranchResponseDto toBranchResponseDto(GitHubBranchResponseDto responseDto) {
        return new BranchResponseDto(responseDto.name(), responseDto.commit().sha());
    }

}
