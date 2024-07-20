package com.Authentication.User.Services;

import com.Authentication.User.DTOs.UserDTO;
import com.Authentication.User.Modals.Roles;
import com.Authentication.User.Modals.Session;
import com.Authentication.User.Modals.SessionStatus;
import com.Authentication.User.Modals.Users;
import com.Authentication.User.Repositaries.SessionRepository;
import com.Authentication.User.Repositaries.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthService(UserRepository userRepository,SessionRepository sessionRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.sessionRepository=sessionRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    public ResponseEntity<UserDTO> login(String email, String password) {
        Optional<Users> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
//            return this.signUp(email, password);
            return null;
        }

        Users user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong username password");
//            return null;
        }

        String token = "";
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();
        Map<String, Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("createdAt", new Date());
        jsonForJwt.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));


        token = Jwts.builder()
                .claims(jsonForJwt)
                .signWith(key, alg)
                .compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDTO userDto = UserDTO.from(user);

//        Map<String, String> headers = new HashMap<>();
//        headers.put(HttpHeaders.SET_COOKIE, token);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);



        ResponseEntity<UserDTO> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
//        response.getHeaders().add(HttpHeaders.SET_COOKIE, token);

        return response;
    }

    public ResponseEntity<Void> logout(String token, UUID userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.ENDED);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public UserDTO signUp(String email, String password) {
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        Users savedUser = userRepository.save(user);

        return UserDTO.from(savedUser);
    }

    public SessionStatus validate(String token, UUID userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return SessionStatus.ENDED;
            //return null;
        }
        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return SessionStatus.ENDED;
        }
        Jws<Claims> claimsJwts=Jwts.parser()
                .build()
                .parseSignedClaims(token);
        String email=(String)claimsJwts.getPayload().get("email");
        List<Roles> roles=(List<Roles>)claimsJwts.getPayload().get("roles");
        Date createdAt=(Date)claimsJwts.getPayload().get("createdAt");
        if(createdAt.before(new Date())){
            return SessionStatus.ENDED;
        }


        return SessionStatus.ACTIVE;
    }


}
