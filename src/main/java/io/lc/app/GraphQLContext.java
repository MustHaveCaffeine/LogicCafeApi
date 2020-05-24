package io.lc.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.lc.app.models.User;
import lombok.Getter;
import lombok.Setter;

public class GraphQLContext {
    @Getter
    private final HttpServletRequest request;
    @Getter
    private final HttpServletResponse response;
    @Getter @Setter
    private User user;

    public GraphQLContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}