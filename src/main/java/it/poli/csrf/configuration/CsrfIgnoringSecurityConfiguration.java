package it.poli.csrf.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Profile("secure-ignoring")
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@Slf4j
public class CsrfIgnoringSecurityConfiguration {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);

    log.debug("Spring actuator endpoints - no filtering");
    http.authorizeHttpRequests(
        authorize ->
            authorize
                .requestMatchers(
                    EndpointRequest.to(
                        HealthEndpoint.class,
                        InfoEndpoint.class,
                        LoggersEndpoint.class,
                        MetricsEndpoint.class,
                        PrometheusScrapeEndpoint.class))
                .permitAll());

    log.info("Oauth2 resource server jwt token");
    http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .oauth2ResourceServer(rs -> rs.jwt(withDefaults()));

    log.debug("Csrf is enabled");
    http.csrf(
        csrf ->
            csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(EndpointRequest.to(LoggersEndpoint.class)));

    return http.build();
  }
}
