package Zadanie.demo;

import org.kohsuke.github.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class AppController {

    private final GitHub gitHub;

    public AppController() throws IOException {
        this.gitHub = new GitHubBuilder()
                .withRateLimitHandler(RateLimitHandler.WAIT)
                .build();
    }
    @GetMapping("/{login}")
    public ResponseEntity<?> getInfo(@PathVariable String login) {
        try {
            GHUser user = gitHub.getUser(login);

            List<GHRepository> repos = user.listRepositories().toList();

            List<Record> result = new ArrayList<>();
            int repoLimit = 10;
            int count = 0;

            for (GHRepository repo : repos) {
                if (count++ >= repoLimit) break;
                String repoName = repo.getName();
                Map<String, GHBranch> branches = repo.getBranches();
                List<Branch> branchList = new ArrayList<>();
                for (Map.Entry<String, GHBranch> branchEntry : branches.entrySet()) {
                    GHBranch branch = branchEntry.getValue();
                    String sha = branch.getSHA1();
                    branchList.add(new Branch(branch.getName(), sha));
                }
                result.add(new Record(repoName, login, branchList));
            }
            return ResponseEntity.ok(result);
        } catch (GHFileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NoUser("There is no such user", 404));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("GitHub API not responding or network error");
        }
    }
}

