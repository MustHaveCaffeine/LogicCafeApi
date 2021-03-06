package io.lc.app;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

import com.coxautodev.graphql.tools.GraphQLResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.SchemaParser;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired
    private ApplicationContext сontext;
    
    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        List<GraphQLResolver<?>> resolvers = new ArrayList<>();
        for (GraphQLResolver<?> resolver: this.сontext.getBeansOfType(GraphQLResolver.class).values()) {
            resolvers.add(resolver);
        }
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("schema.graphql")
                .resolvers(resolvers)
                .build()
                .makeExecutableSchema();
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }
}
