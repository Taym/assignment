package com.redcare.assignment.client;

import com.redcare.assignment.domain.GitHubRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class GitHubApiClient {

    private static final Integer MAX_API_PAGE_SIZE = 100; // max page size allowed by GitHub

    public List<GitHubRepository> loadMaxNumOfRepositories(GitHubRepositoryApiCriteria criteria){
        // this can be improved using the builder pattern for example
        final GitHubRepositoryApiCriteria fullPageSizeCritera = new GitHubRepositoryApiCriteria(
            criteria.programmingLanguage,
            criteria.createdAfterDateTime,
            criteria.pageNumber,
            MAX_API_PAGE_SIZE
        );

        // Simulating calling an actual API
        if (fullPageSizeCritera.pageNumber == 1){
            return getFirstPage();
        } else if (fullPageSizeCritera.pageNumber == 2){
            return getSecondPage();
        } else {
            return Collections.emptyList();
        }
    }

    // this method will be deleted after using GitHub API
    private List<GitHubRepository> getFirstPage(){
        return List.of(
            new GitHubRepository(1,"first repo",1,4, LocalDateTime.now().minusDays(3)),
            new GitHubRepository(2,"second repo",20,400, LocalDateTime.now().minusDays(10)),
            new GitHubRepository(3,"third repo",100,2058, LocalDateTime.now().minusDays(1))
        );
    }

    // this method will be deleted after using GitHub API
    private List<GitHubRepository> getSecondPage(){
        return List.of(
            new GitHubRepository(1,"fourth repo",36,135, LocalDateTime.now().minusDays(100))
        );
    }

    public record GitHubRepositoryApiCriteria(
        String programmingLanguage,
        LocalDateTime createdAfterDateTime,
        Integer pageNumber,
        Integer pageSize
    ){}
}
