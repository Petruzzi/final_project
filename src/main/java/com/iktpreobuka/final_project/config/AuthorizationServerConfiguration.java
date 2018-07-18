package com.iktpreobuka.final_project.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
 
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
 
    private static String REALM="MY_OAUTH_REALM";
     
    @Autowired
    private TokenStore tokenStore;
 
    @Autowired
    private UserApprovalHandler userApprovalHandler;
 
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    
	@Autowired
	private DataSource dataSource;
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 
        clients.inMemory()
            .withClient("user")
            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust")
            .secret("{noop}pass")
            .accessTokenValiditySeconds(111120).//Access token is only valid for 2 minutes.
            refreshTokenValiditySeconds(600);//Refresh token is only valid for 10 minutes.
 
//    	clients.jdbc(dataSource)
//    				.passwordEncoder(new BCryptPasswordEncoder())
//                   .withClient("user")
//                   .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                   .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                //   .scopes("read", "write", "trust")
//                   .secret("pass")
//                   .autoApprove(true)
//                   .accessTokenValiditySeconds(1120).//Access token is only valid for 2 minutes.
//                   refreshTokenValiditySeconds(600).and().build();//Refresh token is only valid for 10 minutes.;
//    	
//    	clients.jdbc(dataSource);//.withClient("clientapp")
//    	.autoApprove(true)
//    	.scopes("read", "write")
//    	.authorities("USER")
//    	.authorizedGrantTypes("password", "refresh_token")
//    	.secret("123456")
//    	
//       
//        .resourceIds("test").and().build();
    	
    	
    }
 
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);
    }
 
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
  //      oauthServer.realm(REALM+"/client");
    	oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
    	
    	//.allowFormAuthenticationForClients();
    }

 
}
//
//CREATE TABLE oauth_access_token (
//  token_id varchar(255) DEFAULT NULL,
//  token blob,
//  authentication_id varchar(255) DEFAULT NULL,
//  user_name varchar(255) DEFAULT NULL,
//  client_id varchar(255) DEFAULT NULL,
//  authentication blob,
//  refresh_token varchar(255) DEFAULT NULL
//);
//
//CREATE TABLE oauth_refresh_token (
//  token_id varchar(256) DEFAULT NULL,
//  token blob,
//  authentication blob
//);
//
//
//
//CREATE TABLE oauth_client_details (
//  client_id               VARCHAR(255) PRIMARY KEY,
//  resource_ids            VARCHAR(255),
//  client_secret           VARCHAR(255),
//  scope                   VARCHAR(255),
//  authorized_grant_types  VARCHAR(255),
//  web_server_redirect_uri VARCHAR(255),
//  authorities             VARCHAR(255),
//  access_token_validity   INTEGER,
//  refresh_token_validity  INTEGER,
//  additional_information  VARCHAR(4096),
//  autoapprove             TINYINT
//);
//
//CREATE TABLE oauth_client_token (
//  token_id          VARCHAR(255),
//  token             BLOB,
//  authentication_id VARCHAR(255),
//  user_name         VARCHAR(255),
//  client_id         VARCHAR(255)
//);