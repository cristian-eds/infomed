package io.github.cristian_eds.InfoMed.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cristian_eds.InfoMed.controller.dto.TokenDTO;
import io.github.cristian_eds.InfoMed.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    public TokenDTO generateToken(User user, String accessCode) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return new TokenDTO(JWT.create()
                .withIssuer("infomed")
                .withSubject(user.getEmail())
                .withClaim("role",user.getRole().toString())
                .withClaim("accessCode", accessCode)
                .withExpiresAt(new Date(System.currentTimeMillis() + 14400000))
                .sign(algorithm));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return decodedJWT(algorithm, token).getSubject();
        } catch (JWTVerificationException excetion) {
            return "";
        }
    }

    private DecodedJWT decodedJWT(Algorithm algorithm, String token) {
        return JWT.require(algorithm)
                .withIssuer("infomed")
                .build()
                .verify(token);
    }

    public String validateClaimAccessCodeFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return decodedJWT(algorithm, token).getClaim("accessCode").asString();
        } catch (JWTVerificationException excetion) {
            return "";
        }
    }
}
