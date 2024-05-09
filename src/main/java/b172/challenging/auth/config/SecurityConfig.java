package b172.challenging.auth.config;

import b172.challenging.auth.oauth.filter.JwtAuthenticationFilter;
import b172.challenging.auth.oauth.handler.Oauth2LoginFailureHandler;
import b172.challenging.auth.oauth.handler.Oauth2LoginSuccessHandler;
import b172.challenging.auth.service.CustomOauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;


@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOauthService customOauthService;
    private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final Oauth2LoginFailureHandler oauth2LoginFailureHandler;


    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${app.cors.allowedOrigins.local}")
    private String corsLocal;

    @Value("${app.cors.allowedOrigins.dev}")
    private String corsDev;

    @Value("${app.cors.allowedOrigins.prod}")
    private String corsProd;

    @Profile(value = {"local"})
    @Bean
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    // ⭐️ CORS 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        String corsUrl = switch (activeProfile) {
            case "dev" -> corsDev;
            case "prod" -> corsProd;
            default -> corsLocal;
        };

        List<String> corsList = List.of(corsUrl.split(","));

        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(corsList); // ⭐️ 허용할 origin
            config.setAllowCredentials(true);
            config.setMaxAge(3600L);

            config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
            config.setExposedHeaders(Collections.singletonList("ACCESS_TOKEN"));
            return config;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(corsConfiguration -> corsConfiguration.configurationSource(corsConfigurationSource()))
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
//                                , new AntPathRequestMatcher("/v1/**")
//                                , new AntPathRequestMatcher("/admin/**")
                        ).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v1/members/profile")).hasAnyRole("GUEST", "MEMBER", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/v1/**")).hasAnyRole("MEMBER", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2) -> oauth2
                        .successHandler(oauth2LoginSuccessHandler)
                        .failureHandler(oauth2LoginFailureHandler)
                        .userInfoEndpoint((userInfoEndpoint -> userInfoEndpoint
                                .userService(customOauthService)))
                )
        ;
        http.addFilterAfter(jwtAuthenticationFilter, OAuth2LoginAuthenticationFilter.class);
        return http.build();
    }

}

