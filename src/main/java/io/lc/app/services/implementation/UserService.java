package io.lc.app.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.lc.app.models.User;
import io.lc.app.repositories.UserRepository;
import io.lc.app.services.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}