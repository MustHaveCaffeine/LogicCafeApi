package io.lc.app.resolvers;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetchingEnvironment;
import io.lc.app.GraphQLContext;
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

    public User getUser(DataFetchingEnvironment environment) {
        GraphQLContext context = environment.getContext();
        return context.getUser();
    }

    public Problem getProblem(String slug) {
        return this.problemRepository.findBySlug(slug);
    }

    public List<Problem> getProblems() {
        return this.problemRepository.findAll();
    }
}