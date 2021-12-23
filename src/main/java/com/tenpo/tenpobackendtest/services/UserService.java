package com.tenpo.tenpobackendtest.services;

import com.tenpo.tenpobackendtest.entities.User;
import com.tenpo.tenpobackendtest.exeptions.UnautorizedException;
import com.tenpo.tenpobackendtest.exeptions.UserAlreadyRegisteredException;

public interface UserService {

    User createUser(User user) throws RuntimeException;

    String autenticateUser(String user, String password) throws UnautorizedException;

    boolean validateJwt(String authHeader) throws RuntimeException;

    boolean isDuplicate(String user) throws UserAlreadyRegisteredException;

    void deleteToken(String usr, String password) throws UnautorizedException;

}
