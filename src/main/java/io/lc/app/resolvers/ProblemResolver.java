package io.lc.app.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.lc.app.models.Problem;
import io.lc.app.models.Submission;
import io.lc.app.repositories.ProblemRepository;

@Component
public class ProblemResolver implements GraphQLResolver<Problem> {
    public List<Submission> getSubmissions(Problem problemId) {
        return new ArrayList<>();
    }
}