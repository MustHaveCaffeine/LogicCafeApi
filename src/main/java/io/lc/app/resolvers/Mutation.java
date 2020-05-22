package io.lc.app.resolvers;

import io.lc.app.auth.AuthContext;
import io.lc.app.models.AuthData;
import io.lc.app.models.Problem;
import io.lc.app.models.SigninPayload;
import io.lc.app.models.Submission;
import io.lc.app.models.User;
import io.lc.app.repositories.ProblemRepository;
import io.lc.app.repositories.SubmissionRepository;
import io.lc.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

@Component
public class Mutation implements GraphQLMutationResolver {

    public static class ProblemInput {
        public String id;
        public String title;
        public String level;
        public List<String> tags;
        public String description;
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    SubmissionRepository submissionRepository;

    public SigninPayload signin(AuthData authData, DataFetchingEnvironment environment) throws IllegalAccessException {
        AuthContext context = environment.getContext();
        // User user = userRepository.findByEmail(authData.getEmail());
        User user = context.getUser();
        if (user.getEmail().equals(authData.getPassword())) {
            return new SigninPayload(user.getEmail(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }
    
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