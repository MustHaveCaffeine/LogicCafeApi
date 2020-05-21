package io.lc.app.resolvers;

import io.lc.app.models.Problem;
import io.lc.app.models.Submission;
import io.lc.app.repositories.ProblemRepository;
import io.lc.app.repositories.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

@Component
public class Mutation implements GraphQLRootResolver {

    public static class ProblemInput {
        public String id;
        public String title;
        public String level;
        public List<String> tags;
        public String description;
    }

    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    SubmissionRepository submissionRepository;
    
    public Problem createProblem(ProblemInput input) {
        Problem problem = new Problem();
        problem.setTitle(input.title);
        problem.setLevel(input.level);
        problem.setTags(input.tags);
        return this.problemRepository.insert(problem);
    }

    public Submission submitSolution(String problemSlug, String snippet, DataFetchingEnvironment env) {
        Submission submission = new Submission();
        submission.problemId = this.problemRepository.findBySlug(problemSlug).getId();
        // submission.snippetId = snippet;
        return this.submissionRepository.insert(submission);
    }
}