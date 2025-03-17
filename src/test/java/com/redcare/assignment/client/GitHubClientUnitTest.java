package com.redcare.assignment.client;

import org.junit.jupiter.api.Test;

public class GitHubClientUnitTest {

    // in this test class, the actual call to the GitHub API should be mocked as this is a unit testing class

    @Test
    public void mustLoadOnePageSuccessfully(){
        // we should be testing multiple scenarios to cover the different query properties like language and earliest
        // date
    }

    @Test
    public void mustFailToLoadPageIfGitHubApiIsDown(){
        // test the different scenarios of API failures and check if the right exception is thrown of the right Exception
        // wrapper is returned in case you are using a functional data types to represent exceptions like Either<T,R> or
        // Try<T>.
        // Here we should consider all the different statuses the API could return.
    }

    @Test
    public void mustFailIfGitHubApiCallTimedOut(){

    }

    @Test
    public void mustFailFastIfGitHubApiWasFailingRecentlyDueCircuitBreakerIsOpen(){
        // for example the circuit breaker will be useful in the case of the GitHub API was timing out a lot recently
        // then circuit breaker should get open when failures are above a certain threshold,
        // which allows us to respond faster to the user without waiting until the API call times out and we don't hold
        // the servlet thread longer than needed.
    }
}
