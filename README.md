# <ins>GitHub Repositories Loading and Scoring - Coding Challenge</ins>

**Note:** I spent 6 hours and 20 minutes implementing the solution. After that, I wrote this README file to provide some additional notes.

### <ins>Problem Statement:</ins>
The objective of this project is to implement a backend application for scoring repositories on GitHub.

GitHub provides a public search endpoint which you can use for fetching repositories. You can find the documentation
<a href="https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories">here</a>.

The task is to develop a scoring algorithm that assigns a popularity score to each repository based on the factors: stars, forks
and recency of the updates.

The user should be able to configure the earliest created date and language of the repositories.

### <ins>Problem Solution & Notes:</ins>
- Due to the time constraint, I provided a basic implementation of the problem. However, I ensured that the code compiles and runs successfully.

- I created an endpoint that takes the programming language name and the earliest creation date as request parameters
and returns a JSON list of repositories that match the criteria. The response is streamed to the caller to avoid loading the entire response into memory.

- The endpoint calls the `GitHubApiClient` to retrieve repositories from the GitHub API, but I did not make actual API calls. Instead, I returned hardcoded data.

- I implemented one test case for the endpoint, which runs successfully. Additionally, I provided other empty test cases (not implemented)
to illustrate some scenarios that also need to be considered for this endpoint and the other classes in the project.

- With more time, the implementation can be improved in multiple ways, and I have added some comments in the code and the
tests that hint at some of these improvements.

- I configured a [GitHub Actions workflow](https://github.com/Taym/assignment/actions) that primarily builds and runs tests to verify that the application runs successfully.

- To run the tests locally you need to clone the project and install Java 21(using SDKMAN for example) then run the following command
in the terminal from the root directory of the project:
```
./gradlew test
```

### <ins>Example for calling the endpoint after running the server locally:</ins>
Go to any web browser and access this url:
```
http://localhost:8080/github/repositories?primary_programming_language=java&created_after=2025-03-05T23:42:37
```
The same response will always be returned because it's hardcoded:
```
[
    {
        "fullName": "first repo",
        "popularityScore": 1.48
    },
    {
        "fullName": "second repo",
        "popularityScore": 368.0
    },
    {
        "fullName": "third repo",
        "popularityScore": 225.22
    },
    {
        "fullName": "fourth repo",
        "popularityScore": 1229.4
    }
]
```
