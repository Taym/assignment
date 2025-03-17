package com.redcare.assignment.api;

import com.redcare.assignment.client.GitHubApiClient;
import com.redcare.assignment.dto.GitHubRepositoryDto;
import com.redcare.assignment.util.JavaObjectMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("github/repositories")
public class GitHubRepositoryController {

    private final GitHubApiClient GitHubApiClient;

    @Autowired
    public GitHubRepositoryController(GitHubApiClient GitHubApiClient){
        this.GitHubApiClient = GitHubApiClient;
    }

    // I streamed the response to the endpoint caller, so that the user will be able to receive a big amount of data
    // without having memory issues and without the need to send multiple requests. This approach is to provide
    // scalability and performance.
    // Other endpoints can be added accordingly to fit user preferences.
    @GetMapping
    public ResponseEntity<StreamingResponseBody> loadRepositoriesWithPopularityScores(
        @RequestParam(value = "primary_programming_language") String primaryProgrammingLanguage,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(value = "created_after") LocalDateTime createdAfter
    ){
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(outputStream -> writeJsonArrayToOutputStream(primaryProgrammingLanguage,createdAfter,outputStream));
    }


    private String getJsonResponseChunk(String primaryProgrammingLanguage, LocalDateTime createdAfter, Integer pageNum){
        final GitHubApiClient.GitHubRepositoryApiCriteria criteria =  new GitHubApiClient.GitHubRepositoryApiCriteria(
            primaryProgrammingLanguage,createdAfter,pageNum,null
        );
        final List<GitHubRepositoryDto> repositoriesPage = GitHubApiClient.loadMaxNumOfRepositories(criteria)
            .stream()
            .map(GitHubRepositoryDto::of)
            .toList();

        final String json = JavaObjectMapping.convertListAsJsonArrayWithoutArrayBrackets(repositoriesPage);

        if (pageNum > 1 && !repositoriesPage.isEmpty()){
            return "," + json;
        } else {
            return json;
        }
    }

    private void writeJsonArrayToOutputStream(String primaryProgrammingLanguage, LocalDateTime createdAfter, OutputStream os){
        try {
            os.write("[".getBytes(StandardCharsets.UTF_8)); // json array opening bracket
            IntStream.range(1, Integer.MAX_VALUE)
                .boxed()
                .map(pageNum -> getJsonResponseChunk(primaryProgrammingLanguage,createdAfter,pageNum))
                .takeWhile(json -> !json.isEmpty())
                .forEach(json -> writeJsonChunkToOutputStream(json, os));
            os.write("]".getBytes(StandardCharsets.UTF_8)); // json array closing bracket
        } catch (Exception ex){
            throw new RuntimeException("Error while writing to OutputStream", ex);
        }
    }

    private void writeJsonChunkToOutputStream(String jsonChunk, OutputStream os){
        try{
            os.write(jsonChunk.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex){
            throw new RuntimeException("Error while writing to OutputStream", ex);
        }
    }

}
