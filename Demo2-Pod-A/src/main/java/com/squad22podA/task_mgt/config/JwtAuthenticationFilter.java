package com.squad22podA.task_mgt.config;

import com.squad22podA.task_mgt.exception.InValidTokenError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;



        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // extract token from authHeader

        jwt = authHeader.substring(7);

        // extract the email from the jwt service

            userEmail = jwtService.extractUsername(jwt);


        if(userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(userEmail);

            if(jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}

/*
the corrected code.
1- error: conflicting states jwt or HttpBasic
2- anyRequest().permitAll() should be anyRequest().authenticated().

@Bean
public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
    security
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(CsrfConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(antMatcher(HttpMethod.POST, "/api/auth/**"),
                             antMatcher(HttpMethod.GET, "/api/**"))
            .permitAll()
            .anyRequest().authenticated()
        )
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(authenticationEntryPoint)
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

    security.authenticationProvider(authenticationProvider);

    return security.build();
}
 */