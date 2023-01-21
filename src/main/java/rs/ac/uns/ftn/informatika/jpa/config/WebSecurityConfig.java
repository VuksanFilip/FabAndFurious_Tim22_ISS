//package rs.ac.uns.ftn.informatika.jpa.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import rs.ac.uns.ftn.informatika.jpa.security.RestAuthenticationEntryPoint;
//import rs.ac.uns.ftn.informatika.jpa.security.TokenAuthenticationFilter;
//import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;
//import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class WebSecurityConfig {
//
//    private final IUserService userService;
//
//    @Autowired
//    public WebSecurityConfig(IUserService userService){
//        this.userService = userService;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return (UserDetailsService) userService;
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }
//
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Autowired
//    private TokenUtils tokenUtils;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
//        http.authorizeRequests().antMatchers("/auth/**").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/api/foo").permitAll()
//                .anyRequest().authenticated().and()
//                .cors().and()
//                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), BasicAuthenticationFilter.class);
//
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers(HttpMethod.POST, "/auth/login")
//                .antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico",
//                        "/**/*.html", "/**/*.css", "/**/*.js");
//
//    }
//
//}
