package br.com.itec.rifa.services;

import br.com.itec.rifa.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

@Service
public class JwtService {

    private static String secret = "secreto6@";
    Algorithm algorithm = Algorithm.HMAC256(secret);
    static Algorithm algorithm2 = Algorithm.HMAC256(secret);

    public String encode(User user) throws JWTCreationException {
        return JWT.create().withIssuer("auth0")
                .withClaim("name", user.getName())
                .withClaim("email", user.getEmail())
                .withClaim("id", user.getId())
                .sign(algorithm);

    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        return jwtVerifier.verify(token);
    }

    public static Authentication verifyRequest(HttpServletRequest request) throws IllegalAccessException {
        String token = request.getHeader("Authorization");

        if (token != null) {
            JWTVerifier jwtVerifier = JWT.require(algorithm2)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token.replace("Bearer ", ""));

            if (decodedJWT != null) return new UsernamePasswordAuthenticationToken(decodedJWT.getClaim("email"), null, Collections.emptyList());
        }
//        else {
//            throw new IllegalArgumentException("Token não pode ser nulo!");
//        }
        return null;
    }
}
