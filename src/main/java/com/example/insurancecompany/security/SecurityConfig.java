package com.example.insurancecompany.security;

import com.example.insurancecompany.filter.JWTAuthenticationFilter;
import com.example.insurancecompany.filter.JWTAuthorizationFilter;
import com.example.insurancecompany.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;
        private final PasswordEncoder passwordEncoder;
        private final long expirationTime;
        private final String secretKey;

        public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                              PasswordEncoder passwordEncoder,
                              @Value("${jwt.expirationTime}") long expirationTime,
                              @Value("${jwt.secretKey}") String secretKey) {
                this.customUserDetailsService = customUserDetailsService;
                this.passwordEncoder = passwordEncoder;
                this.expirationTime = expirationTime;
                this.secretKey = secretKey;
        }

        private static final String[] AUTH_WHITELIST = {
                "**/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
        };

        // Provide AuthenticationManager from AuthenticationConfiguration
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        // Configure DaoAuthenticationProvider with our CustomUserDetailsService and PasswordEncoder
        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(customUserDetailsService);
                provider.setPasswordEncoder(passwordEncoder);
                return provider;
        }

        // Define the SecurityFilterChain instead of extending WebSecurityConfigurerAdapter
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                       AuthenticationManager authenticationManager) throws Exception {
                http.csrf(csrf -> csrf.disable());
                http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http.authenticationProvider(authenticationProvider());

                http.authorizeHttpRequests(requests -> requests
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users/**").hasAnyAuthority("ROLE_MANAGER")
                                .requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ROLE_MANAGER")
                                .requestMatchers("/calculation/**", "/buying/**", "/policies/**").hasAnyAuthority("ROLE_USER")
                                .requestMatchers("/registration/**").permitAll()
                                .anyRequest().authenticated()
                );

                http.httpBasic(basic -> basic.authenticationEntryPoint(swaggerAuthenticationEntryPoint()));
                http.logout(logout -> logout.logoutSuccessUrl("/login"));

                // Add JWT filters
                http.addFilter(new JWTAuthenticationFilter(authenticationManager, expirationTime, secretKey));
                http.addFilterBefore(new JWTAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
                BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
                entryPoint.setRealmName("Swagger Realm");
                return entryPoint;
        }
}
