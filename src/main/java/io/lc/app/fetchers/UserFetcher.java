package io.lc.app.fetchers;

import graphql.schema.*;
import io.lc.app.models.User;
import io.lc.app.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserFetcher implements DataFetcher<User> 
{
    private final UserRepository userRepository;

    @Autowired
    UserFetcher(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User get(DataFetchingEnvironment env) {
        Map<String, Object> args = env.getArguments();
        String username = String.valueOf(args.get("username"));
        return this.userRepository.findByUsername(username);
    }
}