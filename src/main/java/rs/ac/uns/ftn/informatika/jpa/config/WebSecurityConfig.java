package rs.ac.uns.ftn.informatika.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.uns.ftn.informatika.jpa.auth.CustomOAuth2User;
import rs.ac.uns.ftn.informatika.jpa.auth.CustomOAuth2UserService;
import rs.ac.uns.ftn.informatika.jpa.security.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.informatika.jpa.security.TokenAuthenticationFilter;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private CustomOAuth2UserService oauth2UserService;

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private RestAuthenticationEntryPoint entryPoint;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private IUserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers("/api/login/*").permitAll()
//                .antMatchers("/api/unregisteredUser/**").permitAll()
//                .antMatchers("/api/user/login").permitAll()
//                .antMatchers("/api/user/*/resetPassword").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/api/passenger/**").permitAll()
//                .antMatchers("/api/ride/*").permitAll()
//                .antMatchers("/api/ride/**").permitAll()
//                .antMatchers("/api/driver/**").permitAll()
//                .antMatchers("/api/user/*/resetPasswordByEmail").permitAll()
//                .antMatchers("/api/user/*/changePassword").permitAll()
//                .antMatchers("/socket/websocket").permitAll()
//                .antMatchers("api/passenger/exist/*").permitAll()
//                .antMatchers("api/websocket/send/**").permitAll()
//                .antMatchers("/socket").permitAll()
//                .antMatchers("/send/messsage").permitAll()
//                .antMatchers("**").permitAll()
//                .antMatchers("/list").permitAll()
//                .antMatchers("/api/driver/*/vehicle").permitAll()
                .antMatchers("/**").permitAll()//ovo kasnije izbrisati
//                .antMatchers("/**").authenticated()
                .and()
                .headers().frameOptions().disable().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint) //umesto authenticationEntryPoint
                .and()
                    .oauth2Login()
                    .loginPage("/registration")
                    .userInfoEndpoint()
                    .userService(oauth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                        userService.processOAuthPostLogin(oauthUser.getEmail(), oauthUser.getName());
                    }
                });

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
