package ru.alex.burdovitsin.mesh.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex.burdovitsin.mesh.exception.UserAuthenticationException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static ru.alex.burdovitsin.mesh.common.Constants.MILLISECOND_IN_SECOND;
import static ru.alex.burdovitsin.mesh.config.security.JwtAuthenticationFilter.BEARER_PREFIX;
import static ru.alex.burdovitsin.mesh.config.security.JwtAuthenticationFilter.JWT_HEADER_NAME;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    public long tokenLifeTimeInSecond;

    public String generateToken(UserDetails userDetails) {
        Date expiredDate = new Date(System.currentTimeMillis() + tokenLifeTimeInSecond * MILLISECOND_IN_SECOND);

        return Jwts.builder()
                .claims()
                .issuedAt(new Date(System.currentTimeMillis()))
                .subject(userDetails.getUsername())
                .expiration(expiredDate)
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserName(HttpHeaders headers) {
        List<String> jwtTokens = headers.get(JWT_HEADER_NAME);
        if (jwtTokens != null && !jwtTokens.isEmpty()) {
            String token = jwtTokens.get(0);
            if (Strings.isNotBlank(token)) {
                String user = extractUserName(token.replace(BEARER_PREFIX, ""));
                if (Strings.isNotBlank(user)) {
                    return user;
                }
            }
        }
        throw new UserAuthenticationException();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
