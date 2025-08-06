package com.example.atiperatask;

import com.example.atiperatask.dto.BranchResponseDto;
import com.example.atiperatask.dto.RepoResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GitHubControllerIntegrationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnListOfRepositoriesForUser() throws Exception {
        String username = "hubert-lasota";
        MvcResult result = mockMvc
            .perform(get("/api/github/users/%s/repos".formatted(username)))
            .andExpect(status().isOk())
            .andReturn();

        String json = result.getResponse().getContentAsString();
        List<RepoResponseDto> repos = objectMapper.readValue(
            json,
            new TypeReference<>() {
            }
        );

        assertThat(repos).isNotNull();

        for (RepoResponseDto repo : repos) {
            assertThat(repo.name()).isNotBlank();
            assertThat(repo.ownerLogin()).isNotBlank();

            List<BranchResponseDto> branches = repo.branches();
            assertThat(branches).isNotNull();

            for (BranchResponseDto branch : branches) {
                assertThat(branch.name()).isNotBlank();
                assertThat(branch.lastCommitSha()).isNotBlank();
            }
        }
    }
}
