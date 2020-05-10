package io.lc.app.services;

import io.lc.app.models.User;

public interface IUserService {
    User findByUsername(String userName);
}