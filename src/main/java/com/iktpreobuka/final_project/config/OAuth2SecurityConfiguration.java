package com.iktpreobuka.final_project.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
    private ClientDetailsService clientDetailsService;
     
	@Autowired
	private DataSource dataSource;
	
	
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//        .withUser("bill").password("{noop}abc123").roles("ADMIN").and()
//        .withUser("bob").password("{noop}abc123").roles("USER");

		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(rolesQuery)
		.passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        //.cors().and()
        .csrf().disable()
        .anonymous().disable()//.httpBasic().disable()
        .authorizeRequests()
        .antMatchers("/oauth/token").permitAll();
        
    }
//    protected class WebSecurityCorsFilter implements Filter {
//	    @Override
//	    public void init(FilterConfig filterConfig) throws ServletException {
//	    }
//	    @Override
//	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//	        HttpServletResponse res = (HttpServletResponse) response;
//	        res.setHeader("Access-Control-Allow-Origin", "*");
//	        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//	        //res.setHeader("Access-Control-Max-Age", "3600");
//	        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
//
//	        chain.doFilter(request, res);
//	    }
//	    @Override
//	    public void destroy() {
//	    }
//	}
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//      http
//    	.requestMatchers().antMatchers("/login", "/logout", "/oauth/authorize", "/oauth/confirm_access")
//      .and()
//        .cors().configurationSource(configurationSource())
//        ...
//    }
//
//    private CorsConfigurationSource configurationSource() {
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//      CorsConfiguration config = new CorsConfiguration();
//      config.addAllowedOrigin("*");
//      config.setAllowCredentials(true);
//      config.addAllowedHeader("*");
//     // config.addAllowedHeader("Content-Type");
//      config.addAllowedMethod("*");
//      source.registerCorsConfiguration("/**", config);
//      return source;
//    }
    
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//		configuration.setAllowedMethods(Arrays.asList("*"));
//	//configuration.addAllowedHeader("*");
//		configuration.setAllowedHeaders(Arrays.asList("*"));
//		configuration.setAllowCredentials(true);
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
 
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
 
 
    @Bean
    public TokenStore tokenStore() {
        //return new InMemoryTokenStore();
    	 return new JdbcTokenStore(dataSource);
    }
 
    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }
     
    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
    
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder(){
////		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return new BCryptPasswordEncoder();
//	}
//     
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
    

}
