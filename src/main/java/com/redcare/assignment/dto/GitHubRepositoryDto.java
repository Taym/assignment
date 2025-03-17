package com.redcare.assignment.dto;

import com.redcare.assignment.domain.GitHubRepository;

public record GitHubRepositoryDto(String fullName, Double popularityScore) {
    public static GitHubRepositoryDto of(GitHubRepository GitHubRepository) {
        return new GitHubRepositoryDto(GitHubRepository.fullName(), GitHubRepository.calculatePopularityScoreWithDefaultScoringStrategy());
    }
}
