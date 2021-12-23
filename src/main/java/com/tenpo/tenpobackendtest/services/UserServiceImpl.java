package com.tenpo.tenpobackendtest.services;

import com.tenpo.tenpobackendtest.entities.User;
import com.tenpo.tenpobackendtest.exeptions.BadRequestException;
import com.tenpo.tenpobackendtest.exeptions.NotFoundException;
import com.tenpo.tenpobackendtest.exeptions.UnautorizedException;
import com.tenpo.tenpobackendtest.exeptions.UserAlreadyRegisteredException;
import com.tenpo.tenpobackendtest.repositories.UserRepository;
import com.tenpo.tenpobackendtest.statics.Static;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) throws RuntimeException {
        //varias de estas validaciones se podrian hacer a nivel front como la del email, campos vacios y passwords que no coinciden en la creacion del usuario
        if (!checkValidUser(user)) {
            throw new BadRequestException("User field is missing");
        }
        if (isDuplicate(user.getUserid())) {
            throw new BadRequestException("The selected user is already registered");
        }
        if (!user.getPassword().equals(user.getConfirmationPassword())) {
            throw new BadRequestException("Password and confirmation password don´t match");
        }
        Matcher matcher = Static.VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
        if (!matcher.find()) {
            throw new BadRequestException("Invalid email format");
        }
        return userRepository.save(user);
    }

    @Override
    public String autenticateUser(String user, String password) throws UnautorizedException {
        if (user == null) {
            throw new BadRequestException("User is empty.");
        }
        if (password == null) {
            throw new BadRequestException("Password is empty.");
        }
        User userFromRepo = userRepository.findByUserid(user);
        if (userFromRepo == null) {
            throw new NotFoundException("User was not found. Please enter a valid one.");
        }
        if (userFromRepo.getPassword().equals(password)) {
            User userFromDb = userRepository.findByUserid(user);
            String jwt = createJWT(user, password);
            Claims claims = Jwts.parser().setSigningKey(Static.SECRET).parseClaimsJws(jwt).getBody();
            userFromDb.setTokenVersion(claims.getId());
            userRepository.save(userFromDb);
            return jwt;
        } else {
            throw new UnautorizedException("Invalid password.");
        }
    }

    public String createJWT (String usr, String password) {

        long timeStamp = System.currentTimeMillis();
        String jwtToken = Jwts.builder()
                .claim("user", usr)
                .claim("password", password)
                .setSubject(usr)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp + 500000L))
                .signWith(SignatureAlgorithm.HS256, Static.SECRET)
                .compact();
        return jwtToken;
    }

    @Override
    public boolean validateJwt(String authHeader) throws RuntimeException {
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");

            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    Claims claims = Jwts.parser().setSigningKey(Static.SECRET).parseClaimsJws(token).getBody();
                    String usrFromToken = claims.getSubject();
                    User user = userRepository.findByUserid(usrFromToken);
                    if (!user.getTokenVersion().equals(claims.getId())) {
                        return false;
                    }
                    if (claims.getExpiration().getTime() >= System.currentTimeMillis()) {
                        return true;
                    }
                } catch (Exception e) {
                    throw new UnautorizedException("Invalid Authorization Header");
                }
            } else {
                throw new UnautorizedException("Invalid Authorization Header. Desired format is 'Bearer token'");
            }
        } else {
            throw new UnautorizedException("Authorization Header is missing");
        }
        return false;
    }

    @Override
    public boolean isDuplicate(String user) throws UserAlreadyRegisteredException {
        return userRepository.findByUserid(user) != null;
    }

    @Override
    public void deleteToken(String user, String password) throws UnautorizedException {
        if (user == null) {
            throw new BadRequestException("User is empty.");
        }
        if (password == null) {
            throw new BadRequestException("Password is empty.");
        }
        User userFromRepo = userRepository.findByUserid(user);
        if (userFromRepo == null) {
            throw new NotFoundException("User was not found. Please enter a valid one.");
        }
        if (userFromRepo.getPassword().equals(password)) {
            User userFromDb = userRepository.findByUserid(user);
            userFromDb.setTokenVersion("");
            userRepository.save(userFromDb);
        } else {
            throw new UnautorizedException("Invalid password.");
        }

    }

    public boolean checkValidUser(User user) {
        return user.getUserid() != null && user.getPassword() != null && user.getFullName() != null && user.getEmail() != null && user.getAddress() != null;
    }



}
