package io.lc.app;

import java.security.Key;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
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

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ExecutionResult execute(
        @RequestBody Map<String, Object> requestBody,
        @RequestHeader(value = "Authorization", required = false) String authHeader,
        HttpServletRequest request, HttpServletResponse response) {
        String query = (String)requestBody.get("query");
        @SuppressWarnings("unchecked")
        Map<String, Object> variables = (Map<String, Object>)requestBody.get("variables");
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