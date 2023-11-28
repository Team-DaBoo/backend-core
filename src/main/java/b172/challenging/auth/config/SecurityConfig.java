package b172.challenging.auth.config;

import b172.challenging.auth.Repository.MemberRepository;
import b172.challenging.auth.oauth.filter.JwtAuthenticationFilter;
import b172.challenging.auth.oauth.handler.Oauth2LoginSuccessHandler;
import b172.challenging.auth.service.CustomOauthService;
import b172.challenging.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final CustomOauthService customOauthService;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Bean
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
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
                                , new AntPathRequestMatcher("/h2-console/**")
                                , new AntPathRequestMatcher("/swagger-ui/**")
                                , new AntPathRequestMatcher("/api-docs/**")
                                , new AntPathRequestMatcher("/example/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2) -> oauth2
                        .successHandler(oauth2LoginSuccessHandler)
                        .userInfoEndpoint((userInfoEndpoint -> userInfoEndpoint
                                .userService(customOauthService))));

        return http.build();


    }
    @Bean
    public Oauth2LoginSuccessHandler loginSuccessHandler() {
        return new Oauth2LoginSuccessHandler(jwtService, customOauthService);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, memberRepository);
    }

}

