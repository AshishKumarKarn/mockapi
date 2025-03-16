package karn.mockapi.mockapi.security.model.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import karn.mockapi.mockapi.security.config.JwtSettings;
import karn.mockapi.mockapi.security.model.Scopes;
import karn.mockapi.mockapi.security.model.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 * 
 *
 */
@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     * 
     * @return JwtToken
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");


        Claims claims = Jwts.claims().add("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList())).subject(userContext.getUsername()).build();
        LocalDateTime currentTime = LocalDateTime.now();
        
        String token = Jwts.builder()
          .claims(claims)
          .issuer(settings.getTokenIssuer())
          .issuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .expiration(Date.from(currentTime
              .plusMinutes(settings.getTokenExpirationTime())
              .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().add("scopes", List.of(Scopes.REFRESH_TOKEN.authority())).subject(userContext.getUsername()).build();

        String token = Jwts.builder()
          .claims(claims)
          .issuer(settings.getTokenIssuer())
          .id(UUID.randomUUID().toString())
          .issuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .expiration(Date.from(currentTime
              .plusMinutes(settings.getRefreshTokenExpTime())
              .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
}
