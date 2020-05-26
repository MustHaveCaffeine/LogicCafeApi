package io.lc.app;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.language.Document;
import graphql.schema.idl.SchemaPrinter;
import graphql.introspection.IntrospectionQuery;
import graphql.introspection.IntrospectionResultToSchema;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.lc.app.models.User;
import io.lc.app.repositories.UserRepository;

@RestController
public class GraphQLController {

    @Value("${LC_SECRET_KEY:IYKnZNirCmaxteiDS030jiwTnw47NmvR}")
    String applicationKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GraphQL graphQL;

    @GetMapping(value = "/graphql", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String printSchema() {
        ExecutionResult result = this.graphQL.execute(IntrospectionQuery.INTROSPECTION_QUERY);
        Document document = new IntrospectionResultToSchema().createSchemaDefinition(result);
        return new SchemaPrinter().print(document);
    }

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExecutionResult execute(
        @RequestBody Map<String, Object> requestBody,
        @RequestHeader(value = "Authorization", required = false) String authHeader,
        HttpServletRequest request, HttpServletResponse response) {
        String query = (String)requestBody.get("query");
        Map<String, Object> variables = new HashMap<String, Object>();
        if(requestBody.containsKey("variables")) {
            variables = (Map<String, Object>)requestBody.get("variables");
        }
        String operationName = (String)requestBody.get("operationName");
        GraphQLContext context = new GraphQLContext(request, response);
        User authenticatedUser = getAuthenticatedUser(authHeader);
        context.setUser(authenticatedUser);
        return this.graphQL.execute(ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .operationName(operationName)
                .context(context)
                .build());
    }


    private User getAuthenticatedUser(String authHeader) {
        try {
            if(authHeader == null) {
                return null;
            }
            byte[] secretBytes = this.applicationKey.getBytes();
            Key key = Keys.hmacShaKeyFor(secretBytes);
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authHeader).getBody();
            String hashedPassword = claims.getSubject();
            return this.userRepository.findByPassword(hashedPassword);
        } catch (Exception e) {
        }
        return null;
    }
}