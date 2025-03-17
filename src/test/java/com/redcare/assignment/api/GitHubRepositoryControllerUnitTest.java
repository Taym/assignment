package com.redcare.assignment.api;


import com.redcare.assignment.dto.GitHubRepositoryDto;
import com.redcare.assignment.util.JavaObjectMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubRepositoryControllerUnitTest {

    // in this test class, the actual call to the GitHub API should be mocked as this is a unit testing class

    @Autowired
    private GitHubRepositoryController GitHubRepositoryController;

    @LocalServerPort
    private int port;

    @Autowired
    private HttpClient httpClient;

    @Test
    public void mustReturnGitHubRepositoriesSuccessfullyAccordingToCriteria(){
        try{
            // arrange
            final String expectedResponse = JavaObjectMapping.objectMapper.writeValueAsString(ALL_REPOS);
            final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/github/repositories?primary_programming_language=kotlin&created_after=2025-03-16T23:42:37"))
                .build();

            // act
            final String actualResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            // assert
            Assertions.assertEquals(expectedResponse, actualResponse);
        }
        catch (Exception ex){
            throw new RuntimeException("exception thrown form test",ex);
        }
    }

    @Test
    public void mustReturnEmptyResultOfNoRepositoriesMatchTheProvidedCriteria(){

    }

    @Test
    public void mustReturnALargeNumberOfRepositoriesWithoutHavingOutOfMemoryError(){

    }

    @Test
    public void mustFailToReturnRepositoriesIfGitHubApiIsDown(){
        // should handle the different cases of the api failures depending on the response status code.
        // here should check that the underlying failures are propagated properly in the endpoint response.
    }

    @Test
    public void mustFailWithTimeoutIfTheRequestTakesTooLong(){

    }

    @Test
    public void mustFailIfUnexpectedErrorHappened(){

    }

    @Test
    public void mustLoadRepositoriesFromDatabaseInsteadOfCallingGitHubApi(){
        // Depending on the real life requirements, we could see that storing the GitHub repositories data in database
        // and keep refreshing it might be helpful for performance or for using the data for other use cases.
        // And if the user, for example, repeated a request for data that is still fresh in the database, we could, in this
        // case, load the data from the database instead of calling the actual GitHub API. Of course, if we see an advantage
        // in doing that.

        // this test might be placed in a different test class in the future, depending on the concrete
        // caching and database code implementations.
    }

    @Test
    public void mustFailIfTheRequiredEndpointParametersAreNotSentInTheRequest(){

    }

    @Test
    public void mustFailIfTheRequestParametersAreSentInTheWrongFormat(){

    }

    private final static List<GitHubRepositoryDto> ALL_REPOS = List.of(
        new GitHubRepositoryDto("first repo",1.48),
        new GitHubRepositoryDto("second repo",368.0),
        new GitHubRepositoryDto("third repo",225.22),
        new GitHubRepositoryDto("fourth repo",1229.4)
    );
}
