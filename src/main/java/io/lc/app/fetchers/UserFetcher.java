package io.lc.app.dataFetchers;

import graphql.schema.*;
import io.lc.app.models.User;
import io.lc.app.services.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserFetcher implements DataFetcher<User> 
{
    private final IUserService userService;

    @Autowired
    UserFetcher(IUserService userService){
        this.userService = userService;
    }

    @Override
    public User get(DataFetchingEnvironment env) {
        Map<String, Object> args = env.getArguments();
        String username = String.valueOf(args.get("username"));
        return this.userService.findByUsername(username);
    }
}