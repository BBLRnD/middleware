package com.agent.middleware.config;

import com.agent.middleware.dto.UserSession;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;


    private final JwtTokenProvider jwtTokenProvider;

    private UserSession userSession;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserSession userSession) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userSession = userSession;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //allowCrossOrigin(req,res);
        String header = req.getHeader(HEADER_STRING);
        String authToken;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
            try {
                userSession = jwtTokenProvider.getClaimFromToken(authToken);
                if (jwtTokenProvider.isTokenExpired(userSession.getExpirationDate())) {
                    logger.error("Jwt Token has Expired");
                    throw new AuthenticationException("Authentication failed");
                }else{
                    final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(userSession.getRoles().toArray(new String[0]))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userSession, "", authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
            } catch (Exception e) {
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        chain.doFilter(req, res);
    }
}