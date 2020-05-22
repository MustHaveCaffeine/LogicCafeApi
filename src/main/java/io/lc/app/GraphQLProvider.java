package io.lc.app;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.lc.app.resolvers.Mutation;
import io.lc.app.resolvers.ProblemResolver;
import io.lc.app.resolvers.Query;
import io.lc.app.resolvers.SubmissionResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.SchemaParser;

import javax.annotation.PostConstruct;

import java.io.IOException;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired
    private Query rootQueryResolver;
    @Autowired
    private Mutation rootMutationResolver;
    @Autowired
    private ProblemResolver problemResolver;
    @Autowired
    private SubmissionResolver submissionResolver;
    
    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("schema.graphql")
                .resolvers(
                    this.rootQueryResolver,
                    this.rootMutationResolver,
                    this.problemResolver,
                    this.submissionResolver)
                .build()
                .makeExecutableSchema();
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }
}
