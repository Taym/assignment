package com.redcare.assignment.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Function;

public record GitHubRepository(
    Integer id,
    String fullName,
    Integer forksCount,
    Integer starsCount,
    LocalDateTime lastUpdatedDateTime
) {

    public static Function<GitHubRepository, Double> DEFAULT_POPULARITY_SCORING_STRATEGY = GitHubRepository -> {
        final Long numOfDaysSinceRepoWasUpdated = Duration.between(GitHubRepository.lastUpdatedDateTime, LocalDateTime.now()).toDays();
        return GitHubRepository.forksCount * 0.4 + GitHubRepository.starsCount * 0.3 * numOfDaysSinceRepoWasUpdated * 0.3;
    };

    public Double calculatePopularityScore(Function<GitHubRepository, Double> popularityScoringStrategy) {
        return popularityScoringStrategy.apply(this);
    }

    public Double calculatePopularityScoreWithDefaultScoringStrategy() {
        return calculatePopularityScore(DEFAULT_POPULARITY_SCORING_STRATEGY);
    }
}
