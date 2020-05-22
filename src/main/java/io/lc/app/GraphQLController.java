package io.lc.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import io.lc.app.auth.AuthContext;

@RestController
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExecutionResult execute(@RequestBody Map<String, Object> requestBody, @RequestHeader("Authorization") String authHeader) {
        AuthContext authContext = new AuthContext();
        String query = (String)requestBody.get("query");
        @SuppressWarnings("unchecked")
        Map<String, Object> variables = (Map<String, Object>)requestBody.get("variables");
        String operationName = (String)requestBody.get("operationName");
        return graphQL.execute(ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName(operationName)
                .context(authContext)
                .build());
    }
}