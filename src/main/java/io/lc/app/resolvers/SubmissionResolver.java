package io.lc.app.resolvers;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.lc.app.models.Problem;
import io.lc.app.models.Submission;
import io.lc.app.models.User;
import io.lc.app.repositories.ProblemRepository;
import io.lc.app.repositories.SubmissionRepository;
import io.lc.app.repositories.UserRepository;


@Component
public class SubmissionResolver implements GraphQLResolver<Submission> {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProblemRepository problemRepository;

    public Problem getProblem(Submission submission) {
        return this.problemRepository.findById(submission.problemId).orElse(null);
    }

    public User getSubmitter(Submission submission) {
        return this.userRepository.findById(submission.submitterId).orElse(null);
    }

    public String getSnippet(Submission submission) {
        return "Sample Snippet";
    }

    @Autowired
    SubmissionRepository submissionRepository;

    public List<Submission> getSubmissions(Problem problem) {
        return this.submissionRepository.findByProblemId(problem.getId());
    }
}