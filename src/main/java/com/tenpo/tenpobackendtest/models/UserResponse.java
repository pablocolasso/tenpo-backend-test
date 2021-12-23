package com.tenpo.tenpobackendtest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tenpo.tenpobackendtest.entities.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private User user;
    private Error error;

    public UserResponse(User user, Error error) {
        this.user = user;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}
