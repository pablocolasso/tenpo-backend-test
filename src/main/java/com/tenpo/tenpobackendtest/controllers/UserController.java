package com.tenpo.tenpobackendtest.controllers;

import com.tenpo.tenpobackendtest.entities.User;
import com.tenpo.tenpobackendtest.exeptions.BadRequestException;
import com.tenpo.tenpobackendtest.exeptions.UnautorizedException;
import com.tenpo.tenpobackendtest.exeptions.UserAlreadyRegisteredException;
import com.tenpo.tenpobackendtest.models.LoginRequest;
import com.tenpo.tenpobackendtest.models.UserResponse;
import com.tenpo.tenpobackendtest.services.AuditService;
import com.tenpo.tenpobackendtest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    @Autowired
    AuditService auditService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="User login")
    public ResponseEntity<String> login(@RequestBody LoginRequest user) {
        try {
            String authToken = userService.autenticateUser(user.getUser(), user.getPassword());
            auditService.saveAudit("/users/login");
            return new ResponseEntity<>(authToken, HttpStatus.OK);
        } catch (UnautorizedException e) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException.InternalServerError e) {
            return new ResponseEntity<>("Something were wrong. Please try again in a few minutes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="User logout")
    public ResponseEntity<String> logout(@RequestBody LoginRequest user) {
        try {
            auditService.saveAudit("/users/logout");
            userService.deleteToken(user.getUser(), user.getPassword());
            return new ResponseEntity<>("User token deleted", HttpStatus.OK);
        } catch (UnautorizedException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="User registration")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            auditService.saveAudit("/users/create");
            userService.createUser(user);
            return new ResponseEntity<>("User: "+user.getUserid()+" created successfully", HttpStatus.OK);
        } catch (UnautorizedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (UserAlreadyRegisteredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
