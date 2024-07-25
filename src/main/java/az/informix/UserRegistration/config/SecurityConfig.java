package az.informix.UserRegistration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    protected UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }


   @Bean
   protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.authenticationProvider(authenticationProvider());

       http.authorizeHttpRequests(auth -> {
           auth.requestMatchers("/admin/**").hasRole("ADMIN")
                   .requestMatchers("/user/**").hasRole("USER")
                   .requestMatchers("/**").permitAll();

           auth.anyRequest().authenticated();
       });

       http.formLogin(form->form.loginPage("/signin").loginProcessingUrl("/login").permitAll())
               .logout(logout-> {
                   logout.logoutUrl("/logout");
                   logout.logoutSuccessUrl("/");
               }).cors(Customizer.withDefaults())
               .csrf(csrf->csrf.disable());

       return http.build();
   }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
