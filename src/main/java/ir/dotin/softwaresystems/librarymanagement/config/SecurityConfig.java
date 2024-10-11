package ir.dotin.softwaresystems.librarymanagement.config;

import ir.dotin.softwaresystems.librarymanagement.service.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailService userDetailsService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeRequests()
                .antMatchers("/signup","/books/view").permitAll()  //this url available for all
                .anyRequest().authenticated().and() //all method when authenticated run
                .rememberMe(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .build();
    }
}
