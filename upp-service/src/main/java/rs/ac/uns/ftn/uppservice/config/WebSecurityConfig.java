package rs.ac.uns.ftn.uppservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.ac.uns.ftn.uppservice.security.auth.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.uppservice.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService jwtUserDetailsService;

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final SecurityConfigurer securityConfigurer;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder((passwordEncoder()));
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // TODO delete this and uncomment code below
        //http.authorizeRequests().antMatchers("/").permitAll();

        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/book/publish-start-process").hasRole("WRITER")
                .antMatchers("/complaints").hasAnyRole("CHIEF_EDITOR", "EDITOR", "BOARD_MEMBER")
                .antMatchers("/editorComplaints/**").hasRole("EDITOR")
                .antMatchers("/api/file/**").authenticated()
                .anyRequest().authenticated().and();
        http.apply(securityConfigurer);
        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) {
//        TODO delete this and uncomment code below
//        web.ignoring().antMatchers(HttpMethod.POST, "/**");
//        web.ignoring().antMatchers(HttpMethod.GET, "/**");
//        web.ignoring().antMatchers(HttpMethod.PUT, "/**");

        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers(HttpMethod.POST, "auth/login");
        web.ignoring().antMatchers(HttpMethod.POST, "auth/register");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico",
                "/**/*.html", "/**/*.css", "/**/*.js");


        web.ignoring().antMatchers(HttpMethod.GET, "/camunda/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/camunda/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/camunda/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/camunda/**");


        web.ignoring().antMatchers(HttpMethod.GET, "/**/public/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/**/public/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/**/public/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/**/public/**");
    }

}
