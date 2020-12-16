package rs.ac.uns.ftn.uppservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.uppservice.security.auth.TokenAuthenticationFilter;

@Component
@RequiredArgsConstructor
public class SecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final TokenAuthenticationFilter securityFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                securityFilter, BasicAuthenticationFilter.class
        );
    }
}
