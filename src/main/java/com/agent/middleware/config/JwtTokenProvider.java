package com.agent.middleware.config;

import com.agent.middleware.dto.UserSession;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements Serializable {

    private UserSession userSession;

    @Value("${jwt.token.validity}")
    public long JWT_TOKEN_VALIDITY;

    @Value("${jwt.signing.key}")
    public String SECRET;

    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    public JwtTokenProvider(UserSession userSession) {
        this.userSession = userSession;
    }

//    public UserSession getUsernameFromToken(String token) {
//        return getClaimFromToken(token);
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token).getExpirationDate();
//    }

    public UserSession getClaimFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        userSession.setRoles(Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).toList());
        userSession.setUsername(claims.get("username").toString());
        userSession.setFullName(claims.get("fullName").toString());
        userSession.setModules(claims.get("modules").toString());
        userSession.setUserApplId(claims.get("userApplId").toString());
        userSession.setPrefLangCode(claims.get("prefLanguageCode").toString());
        userSession.setSessionId(claims.get("sessionId").toString());
        userSession.setUserId(claims.get("userId").toString());
        userSession.setSaltValue(claims.get("saltValue").toString());
        userSession.setSecurityToken(claims.get("securityToken").toString());
        userSession.setLoginTimeSuc(claims.get("loginTimeSuc").toString());
        userSession.setLoginTimeSuc(claims.get("loginTimeFai").toString());
        userSession.setExpirationDate(claims.getExpiration());
        return userSession;
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(userSession.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("username", userSession.getUsername())
                .claim("fullName", userSession.getFullName())
                .claim("modules", userSession.getModules())
                .claim("userApplId", userSession.getUserApplId())
                .claim("prefLanguageCode", userSession.getPrefLangCode())
                .claim("sessionId", userSession.getSessionId())
                .claim("userId", userSession.getUserId())
                .claim("saltValue", userSession.getSaltValue())
                .claim("securityToken", userSession.getSecurityToken())
                .claim("loginTimeSuc", userSession.getLoginTimeSuc())
                .claim("loginTimeFai", userSession.getLoginTimeFai())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token).getUsername();
//        return (username.equals(userDetails.getUsername()) && );
//    }

//    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token) {
//
//        final Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
//        final Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
//    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*public DecodedJWT getDecodedJWT(String authToken){
        return JWT.require(Algorithm.HMAC256(getSignKey())).build().verify(authToken);
    }*/

}