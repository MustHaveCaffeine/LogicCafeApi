package io.lc.app.resolvers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.lc.app.auth.AuthRequest;
import io.lc.app.auth.AuthResponse;
import io.lc.app.models.Problem;
import io.lc.app.models.Submission;
import io.lc.app.models.User;
import io.lc.app.repositories.ProblemRepository;
import io.lc.app.repositories.SubmissionRepository;
import io.lc.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetchingEnvironment;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @Value("${LC_SECRET_KEY:mysupersecret}")
    String applicationKey;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    SubmissionRepository submissionRepository;

    private String hashPassword(String passwordToHash, String saltString) {
        try {
            byte[] salt = saltString.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(passwordToHash.getBytes());
            return new String(hashedPassword);
        }
        catch(NoSuchAlgorithmException exception) {
            //log message
        }
        return null;
    }

    public AuthResponse signup(AuthRequest authData, DataFetchingEnvironment environment) throws IllegalAccessException {
        //TODO: Email Validity
        //TODO: Password Validity
        User user = new User();
        user.setEmail(authData.getEmail());
        user.setPassword(hashPassword(authData.getPassword(), authData.getEmail()));
        User insertedUser = this.userRepository.insert(user);
        byte[] secretBytes = this.applicationKey.getBytes();
        Key key = Keys.hmacShaKeyFor(secretBytes);
        String token = Jwts.builder().setSubject(user.getPassword()).signWith(key).compact();
        return new AuthResponse(token, insertedUser);
    }

    public AuthResponse signin(AuthRequest authData, DataFetchingEnvironment environment) throws IllegalAccessException {
        String passwordHash = hashPassword(authData.getPassword(), authData.getEmail());
        User user = this.userRepository.findByPassword(passwordHash);
        if(user == null) {
            throw new IllegalAccessException("Invalid Creds");
        }
        byte[] secretBytes = this.applicationKey.getBytes();
        Key key = Keys.hmacShaKeyFor(secretBytes);
        String token = Jwts.builder().setSubject(user.getPassword()).signWith(key).compact();
        return new AuthResponse(token, user);
    }
    
    public Problem createProblem(ProblemInput input) {
        Problem problem = new Problem();
        problem.setTitle(input.title);
        problem.setLevel(input.level);
        problem.setTags(input.tags);
        return this.problemRepository.insert(problem);
    }

    public Submission submitSolution(String problemSlug, String snippet, DataFetchingEnvironment environment) {
        Submission submission = new Submission();
        submission.problemId = this.problemRepository.findBySlug(problemSlug).getId();
        // submission.snippetId = snippet;
        return this.submissionRepository.insert(submission);
    }
}