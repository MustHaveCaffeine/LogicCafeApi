package io.lc.app.resolvers;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.lc.app.models.Problem;
import io.lc.app.models.Submission;
import io.lc.app.repositories.SubmissionRepository;

@Component
public class ProblemResolver implements GraphQLResolver<Problem> {
    @Autowired
    SubmissionRepository submissionRepository;

    public List<Submission> getSubmissions(Problem problem) {
        return this.submissionRepository.findByProblemId(problem.getId());
    }
}