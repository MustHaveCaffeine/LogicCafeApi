package io.lc.app.fetchers;

import graphql.schema.*;
import io.lc.app.models.Problem;
import io.lc.app.repositories.ProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProblemFetcher
{
    private final ProblemRepository problemRepository;

    @Autowired
    ProblemFetcher(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }
    
    public DataFetcher<Problem> getBySlug() {
        return env -> {
            Map<String, Object> args = env.getArguments();
            String slug = String.valueOf(args.get("slug"));
            return this.problemRepository.findBySlug(slug);
        };
    }

    public DataFetcher<List<Problem>> getList() {
        return env -> {
            return this.problemRepository.findAll();
        };
    }
}