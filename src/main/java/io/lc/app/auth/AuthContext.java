package io.lc.app.auth;

import io.lc.app.models.User;

public class AuthContext {
    public User getUser() {
        return new User();
    }
}