package b172.challenging.auth.config;

import b172.challenging.auth.oauth.CustomAuthenticationEntryPoint;
import b172.challenging.auth.oauth.filter.JwtAuthenticationFilter;
import b172.challenging.auth.oauth.handler.Oauth2LoginFailureHandler;
import b172.challenging.auth.oauth.handler.Oauth2LoginSuccessHandler;
import b172.challenging.auth.service.CustomOauthService;
import io.swagger.v3.oas.models.PathItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOauthService customOauthService;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;


    @Bean
    @Profile(value = {"local","dev"})
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(new AntPathRequestMatcher("/")
                                , new AntPathRequestMatcher("/css/**")
                                , new AntPathRequestMatcher("/images/**")
                                , new AntPathRequestMatcher("/js/**")
                                , new AntPathRequestMatcher("/favicon.ico")
                                , new AntPathRequestMatcher("/login/**")
                                , new AntPathRequestMatcher("/oauth/**")
                                , new AntPathRequestMatcher("/oauth2/**")
                                , new AntPathRequestMatcher("/h2-console/**")
                                , new AntPathRequestMatcher("/swagger-ui/**")
                                , new AntPathRequestMatcher("/api-docs/**")
                                , new AntPathRequestMatcher("/example/**")
                                , new AntPathRequestMatcher("/error/**")
                        ).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v1/members/profile")).hasAnyRole("GUEST", "MEMBER", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/v1/**")).hasAnyRole("MEMBER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2) -> oauth2
                        .successHandler(oauth2LoginSuccessHandler)
                        .failureHandler(oauth2LoginFailureHandler)
                        .userInfoEndpoint((userInfoEndpoint -> userInfoEndpoint
                                .userService(customOauthService)))
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );
        http.addFilterAfter(jwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class);
        return http.build();
    }

}

