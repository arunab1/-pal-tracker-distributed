package io.pivotal.pal.tracker.timesheets;


import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeRequests()
                .requestMatchers(EndpointRequest.to("info")).permitAll()
                .anyRequest().hasRole("ACTUATOR") // Any other endpoint
                .and()
                .httpBasic();
    }
}