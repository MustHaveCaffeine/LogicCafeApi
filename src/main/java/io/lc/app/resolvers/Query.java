package io.lc.app.resolvers;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.lc.app.models.Problem;
import io.lc.app.models.User;
import io.lc.app.repositories.ProblemRepository;
import io.lc.app.repositories.UserRepository;

@Component
public class Query implements GraphQLRootResolver {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemRepository problemRepository;

    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    public Problem getProblem(String slug) {
        return this.problemRepository.findBySlug(slug);
    }

    public List<Problem> getProblems() {
        return this.problemRepository.findAll();
    }
}