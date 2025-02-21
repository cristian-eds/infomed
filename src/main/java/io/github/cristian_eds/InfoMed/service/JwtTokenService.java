package io.github.cristian_eds.InfoMed.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    public String generateToken(String login) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withIssuer("infomed")
                .withSubject(login)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .sign(algorithm);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("infomed")
                    .build()
                    .verify(token)
                    .getSubject();


        } catch (JWTVerificationException excetionp) {
            return "";
        }
    }
}
