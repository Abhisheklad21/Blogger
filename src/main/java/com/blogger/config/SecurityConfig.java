package com.blogger.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //    @Autowired
//    private BloggerApplication bloggerApplication;
    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public static final String[] PUBLIC_URLS={
            "/v2/api-docs","/api/**",
            "/swagger-resources/**","/swagger-ui/**",
            "/webjars/**","/v2/api-docs","/v3/api-docs"

    };
    @Override
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/**").hasRole("ADMIN")
                 .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        //Object's consists of Username and password
//
//        UserDetails user = User.builder().username("Abhi").password(passwordEncoder.encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getEncodedPassword());

    }

    @Bean
    public PasswordEncoder getEncodedPassword() {
        return new BCryptPasswordEncoder();
    }

}
